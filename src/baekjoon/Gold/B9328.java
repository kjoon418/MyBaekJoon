package baekjoon.Gold;

import java.io.*;
import java.util.*;

public class B9328 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    // 건물의 구조를 저장할 배열
    private static char[][] building;
    // 건물의 크기, 테스트 케이스의 개수
    private static int width, height, loop;
    // 훔칠 수 있는 문서의 개수
    private static int totalDocumentsAmount;
    // 지금까지 훔친 문서의 개수
    private static int documentsAmount;
    // 이미 가본 지점을 저장할 컬렉션
    private static final HashSet<Point> visitedPoints = new HashSet<>();
    // 건물의 진입점을 저장할 컬렉션
    private static final List<Point> enterPoints = new ArrayList<>();
    // 열지 못한 문을 저장할 컬렉션
    private static final HashMap<Character, Set<Point>> doors = new HashMap<>();
    // 사용하지 않은 열쇠를 저장할 컬렉션
    private static final HashSet<Character> keys = new HashSet<>();
    // 결과
    private static final StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        loop = Integer.parseInt(in.readLine());
    }

    private static void beforeEach() throws IOException {
        StringTokenizer size = new StringTokenizer(in.readLine(), " ");
        height = Integer.parseInt(size.nextToken());
        width = Integer.parseInt(size.nextToken());
        building = new char[height][width];

        totalDocumentsAmount = 0;
        documentsAmount = 0;

        visitedPoints.clear();
        enterPoints.clear();
        doors.clear();
        keys.clear();

        for (int y = 0; y < height; y++) {
            String line = in.readLine();
            for (int x = 0; x < width; x++) {
                char word = line.charAt(x);
                building[y][x] = word;
                if (word == '*') {
                    continue;
                }
                if (word == '$') {
                    totalDocumentsAmount++;
                }
                if ((word == '.' || word == '$' || Character.isLowerCase(word)) && (y * x == 0 || y + 1 == height || x + 1 == width)) {
                    // 열쇠 혹은 . 혹은 $이 빌딩의 가장자리일 경우 출입로에 저장함
                    enterPoints.add(new Point(x, y));
                    continue;
                }
                // 문인데 빌딩의 가장자리라면 문 컬렉션에 저장함
                if (Character.isUpperCase(word) && (y * x == 0 || y + 1 == height || x + 1 == width)) {
                    addDoor(word, new Point(x, y));
                    continue;
                }
            }
        }

        // 처음부터 가지고 있는 열쇠를 저장한다
        String keyInput = in.readLine();

        // 만약 처음에 가지고 있는 열쇠가 없다면 넘어간다
        if (keyInput.equals("0")) {
            return;
        }

        for (int i = 0; i < keyInput.length(); i++) {
            keys.add(Character.toUpperCase(keyInput.charAt(i)));
        }

        // 만약 가장자리에 있는 문을 열 수 있다면, 진입점으로 저장한다
        for (Character key : keys) {
            if (doors.containsKey(key)) {
                Set<Point> doorPoints = doors.remove(key);
                enterPoints.addAll(doorPoints);
            }
        }
    }

    /**
     * 열지 못한 문을 저장하는 메서드
     */
    private static void addDoor(Character key, Point point) {
        if (doors.containsKey(key)) {
            doors.get(key).add(point);
        } else {
            HashSet<Point> doorPoints = new HashSet<>();
            doorPoints.add(point);
            doors.put(key, doorPoints);
        }
    }

    /**
     * 열쇠를 통해 연 문을 제거하고, 제거한 문을 반환하는 메서드
     */
    private static Set<Point> removeDoor(Character key) {
        return doors.remove(key);
    }

    private static void controller() throws IOException {
        for (int i = 0; i < loop; i++) {
            beforeEach();

            // 모든 진입점부터 탐색을 시작한다
            for (Point enterPoint : enterPoints) {
//                System.out.println("진입점에서 탐색 시작: " + enterPoint.x + ", " + enterPoint.y);
                move(enterPoint.x, enterPoint.y);
            }

            result.append(documentsAmount).append(System.lineSeparator());
        }
    }

    private static void move(int x, int y) {
//        System.out.println("move: " + x + ", " + y);

        // 더 이상 찾을 문서가 없다면 재귀를 종료한다
        if (totalDocumentsAmount <= 0) {
//            System.out.println("더 찾을 문서가 없음");
            return;
        }

        char now = building[y][x];
        visitedPoints.add(new Point(x, y));

        // 여기가 문 혹은 열쇠인지 우선 확인한다
        if (Character.isAlphabetic(now)) {
            // case 문:
            if (Character.isUpperCase(now)) {
                // 이 문에 맞는 열쇠가 있는지 확인한다
                if (keys.contains(now)) {
//                    System.out.println("문 발견 -> 열쇠로 따기: " + now);
                } else {
//                    System.out.println("문 저장: " + now);
                    // 없다면 문 컬렉션에 지금 문을 저장한 후 탐색을 종료한다
                    addDoor(now, new Point(x, y));
                    return;
                }
            } else { // case 열쇠:
                // 열쇠(소문자)를 대문자로 변환한다
                char key = Character.toUpperCase(now);
                // 우선 열쇠를 컬렉션에 저장한다
                keys.add(key);
//                System.out.println("열쇠 저장: " + key);

                // 이 열쇠로 열 수 있는 문이 있는지 확인한다
                if (doors.containsKey(key)) {
//                    System.out.println("열쇠 발견 -> 문 따기: " + key);
                    // 있다면 해당 문에서 탐색을 시작한 후 여기서 탐색하도록 한다
                    Set<Point> points = removeDoor(key);
                    for (Point point : points) {
                        move(point.x, point.y);
                    }
                }
            }
        } else {
            // 여기가 문서($)인지 확인한다
            if (now == '$') {
//                System.out.println("문서 획득: " + x + ", " + y);
                documentsAmount++;
                totalDocumentsAmount--;
            }
        }

        // 이동 연산을 수행한다
        if (canMove(x, y, Direction.UP)) {
            move(x, y-1);
        }
        if (canMove(x, y, Direction.DOWN)) {
            move(x, y+1);
        }
        if (canMove(x, y, Direction.LEFT)) {
            move(x-1, y);
        }
        if (canMove(x, y, Direction.RIGHT)) {
            move(x+1, y);
        }
    }

    private static boolean canMove(int x, int y, Direction direction) {
        switch (direction) {
            case UP -> {
                return y > 0 && building[y-1][x] != '*' && !visitedPoints.contains(new Point(x, y-1));
            }
            case DOWN -> {
                return y+1 < height && building[y+1][x] != '*' && !visitedPoints.contains(new Point(x, y+1));
            }
            case LEFT -> {
                return x > 0 && building[y][x-1] != '*' && !visitedPoints.contains(new Point(x-1, y));
            }
            case RIGHT -> {
                return x+1 < width && building[y][x+1] != '*' && !visitedPoints.contains(new Point(x+1, y));
            }
        }

        throw new IllegalStateException("canMove(): 방향 오류");
    }

    private static void printer() throws IOException {
        out.write(result.toString());

        out.close();
    }

    private static class Point {
        private final int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
