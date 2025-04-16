package programmers.level2;

import java.util.*;

/**
 * 지게차와 크레인
 */
class ForkliftsAndCranes {
    
    public int solution(String[] storage, String[] requests) {
        Containers containers = new Containers(storage);
        
        for (String request : requests) {
            doRequest(containers, request);
        }
        
        return containers.getExistsBoxCount();
    }
    
    private void doRequest(Containers containers, String request) {
        RequestType type = RequestType.deductTypeOfRequest(request);
        char requestedValue = request.charAt(0);
        
        containers.pullOut(requestedValue, type);
    }
    
    enum RequestType {
        FORKLIFT, CRANE;
        
        public static RequestType deductTypeOfRequest(String request) {
            return request.length() == 1 ? FORKLIFT : CRANE;
        }
    }
    
    static class Containers {
        
        private final int width;
        private final int height;
        private final Box[][] boxes;
        
        public Containers(String[] inputs) {
            this.width = inputs[0].length();
            this.height = inputs.length;
            
            this.boxes = new Box[height][width];
        
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    boxes[y][x] = new Box(inputs[y].charAt(x));
                }
            }
            
            initOutsideBoxStatuses();
        }
        
        public void pullOut(char requestedValue, RequestType type) {
            switch (type) {
                case FORKLIFT -> pullOutWithForkLift(requestedValue);
                case CRANE -> pullOutWithCrane(requestedValue);
            }
            
            refreshBoxStatuses();
            refreshOutdated();
            //testPrint(requestedValue, type);
        }
        
        public int getExistsBoxCount() {
            Set<Box> allBoxes = getAllBoxes();
            
            int count = 0;
            for (Box box : allBoxes) {
                if (box.isExists()) {
                    count++;
                }
            }
            
            return count;
        }
        
        private void pullOutWithForkLift(char requestedValue) {
            Set<Box> pullableBoxes = getConnectedOutsideBoxes();
            
            for (Box box : pullableBoxes) {
                if (hasSameValue(box, requestedValue)) {
                    box.pullOut();
                }
            }
        }
        
        private void pullOutWithCrane(char requestedValue) {
            Set<Box> allBoxes = getAllBoxes();
            
            for (Box box : allBoxes) {
                if (hasSameValue(box, requestedValue)) {
                    box.pullOut();
                }
            }
        }
        
        private void initOutsideBoxStatuses() {
            Set<Box> outsideBoxes = getOutsideBoxes();
            
            for (Box outsideBox : outsideBoxes) {
                outsideBox.setConnectedWithOutside(true);
            }
        }
        
        private Set<Box> getOutsideBoxes() {
            Set<Box> outsideBoxes = new HashSet<>();
            
            for (int x = 0; x < width; x++) {
                outsideBoxes.add(boxes[0][x]);
                outsideBoxes.add(boxes[height - 1][x]);
            }
            
            for (int y = 0; y < height; y++) {
                outsideBoxes.add(boxes[y][0]);
                outsideBoxes.add(boxes[y][width - 1]);
            }
            
            return Set.copyOf(outsideBoxes);
        }
        
        private Set<Box> getConnectedOutsideBoxes() {
            Set<Box> connectedOutsideBoxes = new HashSet<>();
            
            for (Box box : getAllBoxes()) {
                if (box.isConnectedWithOutside()) {
                    connectedOutsideBoxes.add(box);
                }
            }
            
            return Set.copyOf(connectedOutsideBoxes);
        }
        
        private Set<Box> getAllBoxes() {
            Set<Box> allBoxes = new HashSet<>();
            
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    allBoxes.add(boxes[y][x]);
                }
            }
            
            return Set.copyOf(allBoxes);
        }
        
        private void refreshBoxStatuses() {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Box box = boxes[y][x];
                    if (box.isConnectedWithOutside()) {
                        spreadConnectedWithOutside(x, y);
                    }
                }
            }
        }
        
        private void spreadConnectedWithOutside(int x, int y) {
            Box box = boxes[y][x];
            
            // 이미 한번 검사한 적 있는 상자는 넘어간다
            if (box.isOutdated()) {
                return;
            }
            box.setOutdated(true);
            
            box.setConnectedWithOutside(true);
            
            // 비어있지 않다면 전파를 멈춘다
            if (box.isExists()) {
                return;
            }
            
            // 비어있다면 주변 상자들도 외부와 연결된것으로 전파한다
            if (canGoToLeft(x)) {
                spreadConnectedWithOutside(x - 1, y);
            }
            if (canGoToRight(x)) {
                spreadConnectedWithOutside(x + 1, y);
            }
            if (canGoToUp(y)) {
                spreadConnectedWithOutside(x, y - 1);
            }
            if (canGoToDown(y)) {
                spreadConnectedWithOutside(x, y + 1);
            }
        }
        
        private void refreshOutdated() {
            Set<Box> allBoxes = getAllBoxes();
            
            for (Box box : allBoxes) {
                box.setOutdated(false);
            }
        }
        
        private boolean canGoToLeft(int x) {
            return x > 0;
        }
        
        private boolean canGoToRight(int x) {
            return x < width - 1;
        }
        
        private boolean canGoToUp(int y) {
            return y > 0;
        }
        
        private boolean canGoToDown(int y) {
            return y < height - 1;
        }
        
        private boolean hasSameValue(Box box, char requestedValue) {
            return box.value == requestedValue;
        }
        
        private void testPrint(char requestedValue, RequestType type) {
            System.out.printf("=====%c:%s=====%n", requestedValue, type.toString());
            
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Box box = boxes[y][x];
                    String value = box.value == ' ' ? "!" : Character.toString(box.value);
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        }
        
    }
    
    static class Box {
        
        private char value;
        private boolean exists = true;
        private boolean connectedWithOutside = false;
        private boolean outdated = false;
        
        public Box(char value) {
            this.value = value;
        }
        
        public void pullOut() {
            exists = false;
            value = ' ';
        }
        
        public boolean isExists() {
            return exists;
        }
        
        public boolean isConnectedWithOutside() {
            return connectedWithOutside;
        }
        
        public boolean isOutdated() {
            return outdated;
        }
        
        public void setOutdated(boolean value) {
            this.outdated = value;
        } 
        
        public void setConnectedWithOutside(boolean value) {
            this.connectedWithOutside = value;
        }
        
    }
}
