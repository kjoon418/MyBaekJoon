package Gold;

import java.io.*;
import java.util.*;

public class B2206_failedByMemory {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static boolean[][] board;
    private static int width;
    private static int height;

    private static int result = Integer.MAX_VALUE;

    private static Set<Point> points = new HashSet<>();

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
        move(0, 0, 1, true);
    }

    /**
     * 움직임을 담당하는 메서드
     * 이미 갔던 곳은 다시 돌아가지 않는다
     */
    private static void move(int x, int y, int length, boolean canBreak) {
        // 도착했다면 결과를 반영함
        if (x == width-1 && y == height-1) {
            result = Math.min(result, length);
            points = null;
            return;
        }

        points.add(new Point(x, y));
        length++;

        // 이전 상태로 돌아갈 수 있도록 저장함
        Set<Point> savePoint = new HashSet<>(points);

        // 위쪽으로 이동
        if (canUp(x, y, points)) {
            // 벽이 있을 경우, 벽을 깨고 이동해야 함
            if (!board[y-1][x]) {
                if (canBreak) {
                    move(x, y-1, length, false);
                    points = savePoint;
                }
            } else {
                move(x, y-1, length, canBreak);
                points = savePoint;
            }
        }

        // 아래쪽으로 이동
        if (canDown(x, y, points)) {
            // 벽이 있을 경우, 벽을 깨고 이동해야 함
            if (!board[y+1][x]) {
                if (canBreak) {
                    move(x, y+1, length, false);
                    points = savePoint;
                }
            } else {
                move(x, y+1, length, canBreak);
                points = savePoint;
            }
        }

        // 왼쪽으로 이동
        if (canLeft(x, y, points)) {
            // 벽이 있을 경우, 벽을 깨고 이동해야 함
            if (!board[y][x-1]) {
                if (canBreak) {
                    move(x-1, y, length, false);
                    points = savePoint;
                }
            } else {
                move(x-1, y, length, canBreak);
                points = savePoint;
            }
        }

        // 오른쪽으로 이동
        if (canRight(x, y, points)) {
            // 벽이 있을 경우, 벽을 깨고 이동해야 함
            if (!board[y][x+1]) {
                if (canBreak) {
                    move(x+1, y, length, false);
                }
            } else {
                move(x+1, y, length, canBreak);
            }
        }

        points = null;
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
     * 위로 올라갈 수 있는지 확인하는 메서드
     * 벽은 반영하지 않음
     */
    private static boolean canUp(int x, int y, Set<Point> points) {
        if (y <= 0) {
            return false;
        }
        Point point = new Point(x, y-1);
        if (points.contains(point)) {
            return false;
        }

        return true;
    }

    /**
     * 아래로 내려갈 수 있는지 확인하는 메서드
     * 벽은 반영하지 않음
     */
    private static boolean canDown(int x, int y, Set<Point> points) {
        if (y+1 >= height) {
            return false;
        }
        Point point = new Point(x, y+1);
        if (points.contains(point)) {
            return false;
        }

        return true;
    }

    /**
     * 오른쪽으로 이동할 수 있는지 확인하는 메서드
     * 벽은 반영하지 않음
     */
    private static boolean canRight(int x, int y, Set<Point> points) {
        if (x+1 >= width) {
            return false;
        }
        Point point = new Point(x+1, y);
        if (points.contains(point)) {
            return false;
        }

        return true;
    }

    /**
     * 왼쪽으로 이동할 수 있는지 확인하는 메서드
     * 벽은 반영하지 않음
     */
    private static boolean canLeft(int x, int y, Set<Point> points) {
        if (x <= 0) {
            return false;
        }
        Point point = new Point(x-1, y);
        if (points.contains(point)) {
            return false;
        }

        return true;
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
