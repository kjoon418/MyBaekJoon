package baekjoon.gold;

import java.io.*;
import java.util.*;

public class B2206_failedByMemory {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static boolean[][] board;
    private static int width;
    private static int height;

    private static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(in.readLine(), " ");
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());
        board = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            String line = in.readLine();
            for (int x = 0; x < width; x++) {
                char value = line.charAt(x);
                // 지나갈 수 있는 곳은 true, 벽은 false
                board[y][x] = value == '0';
            }
        }
    }

    private static void controller() throws IOException {
        Deque<MoveStatus> moveStatuses = new ArrayDeque<>();
        moveStatuses.offer(new MoveStatus(0, 0, 1, true, new HashSet<>()));

        while(!moveStatuses.isEmpty()) {
            move(moveStatuses.poll(), moveStatuses);
        }
    }

    /**
     * 움직임을 담당하는 메서드
     * 이미 갔던 곳은 다시 돌아가지 않는다
     */
    private static void move(MoveStatus moveStatus, Deque<MoveStatus> moveStatuses) {

        int x = moveStatus.x;
        int y = moveStatus.y;
        boolean canBreak = moveStatus.canBreak;
        HashSet<Point> visitedPoints = moveStatus.visitedPoints;
        int length = moveStatus.length;

        // 도착했다면 결과를 반영함
        if (x == width-1 && y == height-1) {
            result = Math.min(result, length);
            return;
        }
        length++;

        visitedPoints.add(new Point(x, y));

        // 위쪽으로 이동
        if (canMove(moveStatus, Direction.UP)) {
            // 벽이 있을 경우, 벽을 깨고 이동해야 함
            if (isWall(x, y-1)) {
                moveStatuses.offer(new MoveStatus(x, y-1, length, false, visitedPoints));
            } else {
                moveStatuses.offer(new MoveStatus(x, y-1, length, canBreak, visitedPoints));
            }
        }

        // 아래쪽으로 이동
        if (canMove(moveStatus, Direction.DOWN)) {
            // 벽이 있을 경우, 벽을 깨고 이동해야 함
            if (isWall(x, y+1)) {
                moveStatuses.offer(new MoveStatus(x, y+1, length, false, visitedPoints));
            } else {
                moveStatuses.offer(new MoveStatus(x, y+1, length, canBreak, visitedPoints));
            }
        }

        // 왼쪽으로 이동
        if (canMove(moveStatus, Direction.LEFT)) {
            // 벽이 있을 경우, 벽을 깨고 이동해야 함
            if (isWall(x-1, y)) {
                moveStatuses.offer(new MoveStatus(x-1, y, length, false, visitedPoints));
            } else {
                moveStatuses.offer(new MoveStatus(x-1, y, length, canBreak, visitedPoints));
            }
        }

        // 오른쪽으로 이동
        if (canMove(moveStatus, Direction.RIGHT)) {
            // 벽이 있을 경우, 벽을 깨고 이동해야 함
            if (isWall(x+1, y)) {
                moveStatuses.offer(new MoveStatus(x+1, y, length, false, visitedPoints));
            } else {
                moveStatuses.offer(new MoveStatus(x+1, y, length, canBreak, visitedPoints));
            }
        }
    }

    private static void printer() throws IOException {
        if (result == Integer.MAX_VALUE) {
            out.write("-1");
        } else {
            out.write(result+"");
        }

        out.close();
    }

    /**
     * 이중 배열을 복사하는 메서드
     */
    private static boolean[][] copyBoard(boolean[][] board) {
        boolean[][] newBoard = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            System.arraycopy(board[y], 0, newBoard[y], 0, width);
        }

        return newBoard;
    }

    /**
     * 움직일 수 있는지 확인하는 메서드
     */
    private static boolean canMove(MoveStatus moveStatus, Direction direction) {
        int x = moveStatus.x;
        int y = moveStatus.y;
        boolean canBreak = moveStatus.canBreak;
        HashSet<Point> visitedPoints = moveStatus.visitedPoints;

        return switch (direction) {
            case UP -> {
                if (y <= 0) {
                    yield false;
                }
                if (visitedPoints.contains(new Point(x, y-1))) {
                    yield false;
                }
                if (!canBreak && isWall(x, y-1)) {
                    yield false;
                }
                yield true;
            }
            case DOWN -> {
                if (y+1 >= height) {
                    yield false;
                }
                if (visitedPoints.contains(new Point(x, y+1))) {
                    yield false;
                }
                if (!canBreak && isWall(x, y+1)) {
                    yield false;
                }
                yield true;
            }
            case LEFT -> {
                if (x <= 0) {
                    yield false;
                }
                if (visitedPoints.contains(new Point(x-1, y))) {
                    yield false;
                }
                if (!canBreak && isWall(x-1, y)) {
                    yield false;
                }
                yield true;
            }
            case RIGHT -> {
                if (x+1 >= width) {
                    yield false;
                }
                if (visitedPoints.contains(new Point(x+1, y))) {
                    yield false;
                }
                if (!canBreak && isWall(x+1, y)) {
                    yield false;
                }
                yield true;
            }
        };
    }

    private static boolean isWall(int x, int y) {
        return !board[y][x];
    }

    private static class MoveStatus {
        private final int x, y;
        private final int length;
        private final boolean canBreak;
        private final HashSet<Point> visitedPoints;

        public MoveStatus(int x, int y, int length, boolean canBreak, HashSet<Point> visitedPoints) {
            this.x = x;
            this.y = y;
            this.length = length;
            this.canBreak = canBreak;
            this.visitedPoints = new HashSet<>(visitedPoints);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MoveStatus moveStatus = (MoveStatus) o;
            return x == moveStatus.x && y == moveStatus.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Point {
        int x, y;

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
        LEFT, RIGHT, UP, DOWN
    }
}
