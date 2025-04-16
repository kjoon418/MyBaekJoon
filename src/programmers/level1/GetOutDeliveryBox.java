package programmers.level1;

import java.util.*;

/**
 * 택배 상자 꺼내기
 */
public class GetOutDeliveryBox {

    public int solution(int n, int w, int num) {
        Lines lines = new Lines(n, w);
        // lines.printLines();

        int result = lines.calculatePullOutCount(num);
        // lines.printLines();

        return result;
    }

}

class Lines {

    private final List<Line> lines;

    public Lines(int boxAmount, int lineWidth) {
        List<Line> lines = new ArrayList<Line>();

        int lineAmount = (boxAmount / lineWidth) + 1;
        int boxIndex = 1;
        StartFrom startFrom = StartFrom.LEFT;
        for (int i = 0; i < lineAmount; i++) {
            List<Integer> boxValues = new ArrayList<>();
            while (boxIndex <= boxAmount && boxValues.size() < lineWidth) {
                boxValues.add(boxIndex);
                boxIndex++;
            }
            lines.add(new Line(boxValues, lineWidth, startFrom));

            startFrom = toggle(startFrom);
        }

        this.lines = List.copyOf(lines);
    }

    public int calculatePullOutCount(int boxValue) {
        Point boxPoint = findPointOfBox(boxValue);
        int x = boxPoint.getX();
        int y = boxPoint.getY();
        int pullOutCount = 0;

        int topY = lines.size() - 1;
        int upperY = topY;
        while (!isPullOutable(x, y)) {
            Box upperBox = getBox(x, upperY);

            if (upperBox.isExists()) {
                upperBox.pullOut();
                pullOutCount++;
            }

            upperY--;
        }

        Box targetBox = getBox(x, y);
        targetBox.pullOut();
        pullOutCount++;

        return pullOutCount;
    }

    private StartFrom toggle(StartFrom startFrom) {
        return startFrom == startFrom.LEFT ? StartFrom.RIGHT : StartFrom.LEFT;
    }

    private Point findPointOfBox(int value) {
        int y = -1;
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            if (line.contains(value)) {
                y = i;
                break;
            }
        }

        Line line = lines.get(y);
        int x = line.indexOf(value);

        return new Point(x, y);
    }

    private boolean isPullOutable(int x, int y) {
        List<Box> upperBoxes = new ArrayList<>();
        for (int i = y + 1; i < lines.size(); i++) {
            Box upperBox = getBox(x, i);
            upperBoxes.add(upperBox);
        }

        return upperBoxes.stream()
                .filter(box -> box.isExists())
                .findAny()
                .isEmpty();
    }

    private Box getBox(int x, int y) {
        return lines.get(y).getBox(x);
    }

    public void printLines() {
        List<Line> copy = new ArrayList<>(lines);
        Collections.reverse(copy);
        for (Line line : copy) {
            System.out.println(line);
        }
        System.out.println();
    }

}

class Line {

    private final List<Box> boxes;

    public Line(List<Integer> boxValues, int width, StartFrom startFrom) {
        List<Box> boxes = new ArrayList<>();

        for (Integer boxValue : boxValues) {
            boxes.add(new Box(boxValue));
        }

        while (boxes.size() < width) {
            boxes.add(Box.getEmptyBox());
        }

        if (startFrom == StartFrom.RIGHT) {
            Collections.reverse(boxes);
        }

        this.boxes = List.copyOf(boxes);
    }

    public boolean contains(int value) {
        return boxes.contains(new Box(value));
    }

    public int indexOf(int value) {
        return boxes.indexOf(new Box(value));
    }

    public Box getBox(int index) {
        return boxes.get(index);
    }

    @Override
    public String toString() {
        return boxes.toString();
    }

}

class Box {

    private int value;
    private boolean exists;

    public Box(int value) {
        this.value = value;
        this.exists = true;
    }

    public static Box getEmptyBox() {
        Box emptyBox = new Box(0);
        emptyBox.exists = false;

        return emptyBox;
    }

    public void pullOut() {
        this.exists = false;
    }


    public int getValue() {
        return value;
    }

    public boolean isExists() {
        return exists;
    }

    @Override
    public boolean equals(Object other) {
        Box otherBox = (Box) other;

        return this.value == otherBox.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        if (!exists) {
            return "X";
        }

        return value + "";
    }

}

class Point {

    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}

enum StartFrom {
    LEFT, RIGHT
}
