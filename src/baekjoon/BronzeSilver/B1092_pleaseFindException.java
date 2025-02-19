package baekjoon.BronzeSilver;

import java.io.*;
import java.util.*;

public class B1092_pleaseFindException {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter out = new BufferedWriter(new OutputStreamWriter((System.out)));

    private static List<Integer> boxes = new ArrayList<>();
    private static TreeMap<Integer, Crane> cranes = new TreeMap<>();
    private static List<Crane> craneList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        if (isAble()) {
            // 무게가 낮은 크레인부터 가능한 화물을 모두 저장한다
            save();

            // 무게가 적은 크레인으로부터, 높은 크레인으로 화물을 옮겨서 평탄화함
            share();

            // 가장 많이 저장한 크레인의 값을 반환함
            out.write(getMaxCrane()+"");

        } else {
            out.write("-1");
        }

        out.close();
    }

    private static void init() throws IOException {
        // 크레인 저장
        int craneAmount = Integer.parseInt(in.readLine());
        String[] craneInputs = in.readLine().split(" ");
        for (int i = 0; i < craneAmount; i++) {
            int weight = Integer.parseInt(craneInputs[i]);
            if (cranes.containsKey(weight)) {
                cranes.get(weight).addCrane();
                continue;
            }

            cranes.put(weight, new Crane());
        }

        // 박스 저장
        int boxAmount = Integer.parseInt(in.readLine());
        String[] boxInputs = in.readLine().split(" ");
        for (int i = 0; i < boxAmount; i++) {
            boxes.add(Integer.parseInt(boxInputs[i]));
        }
    }

    private static boolean isAble() {
        int maxCrane = Collections.max(cranes.keySet());
        int maxBox = Collections.max(boxes);

        return maxCrane >= maxBox;
    }

    private static void save() {
        ArrayList<Integer> removeBoxes = new ArrayList<>();
        for (Map.Entry<Integer, Crane> craneEntry : cranes.entrySet()) {
            // 적은 무게의 크레인부터 가능한 화물을 저장함
            for (Integer box : boxes) {
                if (box <= craneEntry.getKey()) {
                    craneEntry.getValue().addBox();
                    removeBoxes.add(box);
                }
            }
            // 저장된 화물은 리스트에서 제거함
            for (Integer removeBox : removeBoxes) {
                boxes.remove(removeBox);
            }
            removeBoxes.clear();
        }
    }

    // 무게가 낮은 크레인이 더 많은 화물을 가지고 있다면, 더 상위 크레인들에게 골고루 분배함
    private static void share() {
        List<Crane> craneStream = new ArrayList<>();

        for (Crane crane : cranes.values()) {
            craneStream.add(crane);
        }

        // System.out.println(craneStream);

        for (int i = craneStream.size()-2; i >= 0; i--) {
            Crane lowerCrane = craneStream.get(i);
            while(true) {
                // 가장 작은 녀석을 찾아서 넘겨주어야 함
                int index = -1;
                int minimum = Integer.MAX_VALUE;
                for (int j = i+1; j < craneStream.size(); j++) {
                    Crane biggerCrane = craneStream.get(j);
                    if (biggerCrane.getEachMinAmountAfterAddBox() < minimum) {
                        minimum = biggerCrane.getEachMinAmountAfterAddBox();
                        index = j;
                    }
                }
                if (index == -1) { break; }

                // 가장 작은 녀석을 찾았는데, 넘겨줄 경우 내꺼보다 더 커져버린다면 넘겨주면 안되고, 반복문을 깨고 나가야 함
                if (craneStream.get(index).getEachMaxAmountAfterAddBox() <= lowerCrane.getEachMaxAmountAfterRemoveBox()) {
                    lowerCrane.removeBox();
                    craneStream.get(index).addBox();
                } else {
                    break;
                }
            }
        }

        for (int i = 0; i+1 < craneStream.size(); i++) {
            Crane lowerCrane = craneStream.get(i);
            while(true) {
                // 가장 작은 녀석을 찾아서 넘겨주어야 함
                int index = -1;
                int minimum = Integer.MAX_VALUE;
                for (int j = i+1; j < craneStream.size(); j++) {
                    Crane biggerCrane = craneStream.get(j);
                    if (biggerCrane.getEachMinAmountAfterAddBox() < minimum) {
                        minimum = biggerCrane.getEachMinAmountAfterAddBox();
                        index = j;
                    }
                }
                if (index == -1) { break; }

                // 가장 작은 녀석을 찾았는데, 넘겨줄 경우 내꺼보다 더 커져버린다면 넘겨주면 안되고, 반복문을 깨고 나가야 함
                if (craneStream.get(index).getEachMaxAmountAfterAddBox() <= lowerCrane.getEachMaxAmountAfterRemoveBox()) {
                    lowerCrane.removeBox();
                    craneStream.get(index).addBox();
                } else {
                    break;
                }
            }
        }


        // System.out.println(craneStream);

        craneList = craneStream;


    }

    private static int getMaxCrane() {
        Crane max = Collections.max(craneList, (o1, o2) -> {
            return o1.getEachMaxAmount() - o2.getEachMaxAmount();
        });
        return max.getEachMaxAmount();
    }


    private static class Crane {
        int boxAmount;
        int craneAmount;

        public Crane() {
            craneAmount = 1;
        }

        public void addCrane() {
            craneAmount++;
        }

        public void addBox() {
            boxAmount++;
        }

        public void removeBox() {
            boxAmount--;
        }

        public int getEachMinAmount() {
            return (int) Math.floor((double) boxAmount / (double) craneAmount);
        }

        public int getEachMinAmountAfterAddBox() {
            return (int) Math.floor((double) (boxAmount+1) / (double) craneAmount);
        }

        public int getEachMaxAmount() {
            return (int) Math.ceil((double) boxAmount / (double) craneAmount);
        }

        public int getEachMaxAmountAfterRemoveBox() {
            return (int) Math.ceil((double) (boxAmount-1) / (double) craneAmount);
        }

        public int getEachMaxAmountAfterAddBox() {
            return (int) Math.ceil((double) (boxAmount+1) / (double) craneAmount);
        }

        @Override
        public String toString() {
            return "총 화물: " + boxAmount + ",총 크레인: " + craneAmount + ", 한 크레인이 저장한 양: " + getEachMaxAmount() + System.lineSeparator();
        }
    }
}
