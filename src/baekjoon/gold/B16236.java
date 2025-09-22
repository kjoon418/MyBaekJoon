package baekjoon.gold;

import java.io.*;
import java.util.*;

public class B16236 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int size;
    private static int[][] board;

    private static BabyShark shark;
    private static Map<Point, Fish> fishes = new HashMap<>();
    private static TreeSet<Fish> eatableFishes = new TreeSet<>();

    private static int time = 0;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void testPrint() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(board[y][x]+" ");
            }
            System.out.println();
        }
        System.out.println("=======================");
    }

    private static void init() throws IOException {
        size = Integer.parseInt(in.readLine());
        board = new int[size][size];

        for (int y = 0; y < size; y++) {
            String[] line = in.readLine().split(" ");
            for (int x = 0; x < size; x++) {
                int value = Integer.parseInt(line[x]);
                if (value == 9) {
                    shark = new BabyShark(x, y);
                    board[y][x] = 0;
                    continue;
                }
                if (value != 0) {
                    Fish fish = new Fish(x, y, value);
                    Point point = new Point(x, y);
                    fishes.put(point, fish);
                }

                board[y][x] = value;
            }
        }
    }

    private static void printer() throws IOException {
        out.write(time+"");

        out.close();
    }

    private static void controller() throws IOException {
        while(true) {
            // testPrint();

            // 먹을 수 있는 생선들의 eatable을 true로 함
            reachableController();

            // 먹을 수 있는 생선이 없다면 반복을 종료함
            if (!isEatable()) {
                break;
            }

            // 먹을 수 있는 생선들의 거리를 계산하고 Set에 추가함
            eatableFishes.clear(); // 초기화
            initGap(); // 모든 생선들의 gap을 최대치로 초기화
            calculateGap();

            // 먹을 수 있는 생선 중 가장 가까운 생선을 찾음
            Fish eatFish = eatableFishes.first();
            int fishX = eatFish.x;
            int fishY = eatFish.y;

            // 생선을 먹었으니 0으로 변경함
            Point fishPoint = new Point(fishX, fishY);
            fishes.remove(fishPoint);
            board[fishY][fishX] = 0;

            // 생선을 먹은 위치로 상어를 이동시킴
            shark.x = fishX;
            shark.y = fishY;

            // 상어를 성장시킴
            shark.grow();

            // 생선을 먹는데 소모된 시간을 반영함
            time += eatFish.gap;
        }
    }

    /**
     * 모든 생선들의 gap을 초기화하는 메서드
     */
    private static void initGap() {
        for (Map.Entry<Point, Fish> entry : fishes.entrySet()) {
            entry.getValue().gap = Integer.MAX_VALUE;
        }
    }

    /**
     * 먹을 수 있는 생선들의 gap을 계산하고, eatableFishes에 추가함
     */
    private static void calculateGap() {
        // 거리 계산용 board를 만듦
        int[][] gapBoard = new int[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                gapBoard[y][x] = Integer.MAX_VALUE;
            }
        }

        gapSpread(shark.x, shark.y, 0, gapBoard);

        for (Map.Entry<Point, Fish> entry : fishes.entrySet()) {
            Fish fish = entry.getValue();
            if (fish.eatable) {
                eatableFishes.add(fish);
            }
        }
    }

    private static void gapSpread(int x, int y, int gap, int[][] gapBoard) {
        gapBoard[y][x] = gap;

        // 현재 위치에 먹을 수 있는 생선이 존재하면, 생선의 gap을 설정함
        Point nowPoint = new Point(x, y);
        if (fishes.containsKey(nowPoint)) {
            Fish fish = fishes.get(nowPoint);
            if (fish.size < shark.size) {
                fish.gap = Math.min(fish.gap, gap);
                return;
            }
        }

        // 이동을 위해 gap을 1 증가시킴
        gap++;

        // 위쪽 전이
        if (isCanMoveUp(board, x, y)) {
            // 이동할 곳의 gap이, 내 gap보다 클 때에만 이동하도록 함
            if (gapBoard[y-1][x] > gap) {
                gapSpread(x, y-1, gap, gapBoard);
            }
        }

        // 아래쪽 전이
        if (isCanMoveDown(board, x, y)) {
            if (gapBoard[y+1][x] > gap) {
                gapSpread(x, y+1, gap, gapBoard);
            }
        }

        // 오른쪽 전이
        if (isCanMoveRight(board, x, y)) {
            if (gapBoard[y][x+1] > gap) {
                gapSpread(x+1, y, gap, gapBoard);
            }
        }

        // 왼쪽 전이
        if (isCanMoveLeft(board, x, y)) {
            if (gapBoard[y][x-1] > gap) {
                gapSpread(x-1, y, gap, gapBoard);
            }
        }
    }

    private static void reachableController() {
        int sharkX = shark.x;
        int sharkY = shark.y;

        int[][] testBoard = new int[size][size];

        // board를 카피한 새로운 배열 생성
        for (int y = 0; y < size; y++) {
            System.arraycopy(board[y], 0, testBoard[y], 0, size);
        }

        reachableSpread(testBoard, sharkX, sharkY);
    }

    /**
     * 먹을 수 있는 물고기를 갱신하는 메서드
     * 크기가 상어보다 작은 물고기만 먹을 수 있다
     * 크기가 상어보다 큰 물고기 자리는 지나갈 수 없다
     */
    private static void reachableSpread(int[][] testBoard, int x, int y) {
        // 이미 도착했던 곳이라면 재귀를 종료한다
        if (testBoard[y][x] == -1) {
            return;
        }

        int value = testBoard[y][x];
        // 상어보다 작은 물고기는 먹을 수 있다
        if (value > 0 && value < shark.size) {
            Fish fish = fishes.get(new Point(x, y));
            fish.eatable = true;
        }
        testBoard[y][x] = -1;

        // 왼쪽 전이
        if (isCanMoveLeft(testBoard, x, y)) {
            reachableSpread(testBoard, x-1, y);
        }

        // 오른쪽 전이
        if (isCanMoveRight(testBoard, x, y)) {
            reachableSpread(testBoard, x+1, y);
        }

        // 위쪽 전이
        if (isCanMoveUp(testBoard, x, y)) {
            reachableSpread(testBoard, x, y-1);
        }

        // 아래쪽 전이
        if (isCanMoveDown(testBoard, x, y)) {
            reachableSpread(testBoard, x, y+1);
        }
    }

    /**
     * 먹을 수 있는 물고기가 있는지를 반환하는 메서드
     */
    private static boolean isEatable() {
        for (Map.Entry<Point, Fish> entry : fishes.entrySet()) {
            if (entry.getValue().eatable) {
                return true;
            }
        }

        return false;
    }

    /**
     * 위로 갈 수 있는지 검사하는 메서드
     */
    private static boolean isCanMoveUp(int[][] board, int x, int y) {
        return (y > 0 && board[y-1][x] <= shark.size);
    }

    /**
     * 아래로 갈 수 있는지 검사하는 메서드
     */
    private static boolean isCanMoveDown(int[][] board, int x, int y) {
        return (y+1 < size && board[y+1][x] <= shark.size);
    }

    /**
     * 오른쪽으로 갈 수 있는지 검사하는 메서드
     */
    private static boolean isCanMoveRight(int[][] board, int x, int y) {
        return (x+1 < size && board[y][x+1] <= shark.size);
    }

    /**
     * 왼쪽으로 갈 수 있는지 검사하는 메서드
     */
    private static boolean isCanMoveLeft(int[][] board, int x, int y) {
        return (x > 0 && board[y][x-1] <= shark.size);
    }

    private static class BabyShark {
        int x, y;
        int size;
        int growCounter;

        public BabyShark(int x, int y) {
            this.x = x;
            this.y = y;
            this.size = 2;
            this.growCounter = 0;
        }

        public void grow() {
            growCounter++;
            if (size <= growCounter) {
                growCounter = 0;
                size++;
            }
        }
    }

    private static class Fish implements Comparable<Fish> {
        int x, y;
        int size;
        int gap;
        boolean eatable; // 닿을 수 있고 먹을 수 있는지 값을 지님

        public Fish(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }

        @Override
        public int compareTo(Fish o) {
            if (this.gap != o.gap) {
                return this.gap - o.gap;
            }

            if (this.y != o.y) {
                return this.y - o.y;
            }

            return this.x - o.x;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Fish fish = (Fish) o;
            return x == fish.x && y == fish.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Point {
        int x, y;
        public Point(int x, int y) { this.x = x; this.y = y; }

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

}
