package baekjoon.Gold;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.StringTokenizer;

public class B16724 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int width;
    private static int height;
    private static char[][] board;
    private static int[][] groupBoard;
    private static final HashMap<Integer, HashSet<Point>> groups = new HashMap<>();
    private static int groupId;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        StringTokenizer size = new StringTokenizer(in.readLine(), " ");
        height = Integer.parseInt(size.nextToken());
        width = Integer.parseInt(size.nextToken());
        board = new char[height][width];
        groupBoard = new int[height][width];

        for (int y = 0; y < height; y++) {
            String line = in.readLine();
            for (int x = 0; x < width; x++) {
                char value = line.charAt(x);
                board[y][x] = value;
                groupBoard[y][x] = -1;
            }
        }
    }

    private static void controller() throws IOException {
        HashSet<Point> points = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (groupBoard[y][x] == -1) {
                    grouping(x, y, points);
                    points.clear();
                }
            }
        }
    }

    private static void printer() throws IOException {
        int result = groups.size();
        out.write(Integer.toString(result));

        out.close();
    }

    private static void grouping(int x, int y, HashSet<Point> points) {
        int groupId = groupBoard[y][x];
        // 도착한 곳이 이미 그룹화되어있다면, 기존 그룹에 합류하도록 함
        if (groupId != -1) {
            joinGroup(groupId, points);
            return;
        }

        // 도착한 곳을 이미 가봤다면, 한 그룹으로 묶음
        Point nowPoint = new Point(x, y);
        if (points.contains(nowPoint)) {
            makeGroup(points);
            return;
        }

        // 둘 다 아니라면 다음 타일로 이동하고, 현재 위치를 points에 추가함
        points.add(nowPoint);
        char command = board[y][x];
        switch (command) {
            case 'U':
                grouping(x, y-1, points);
                break;
            case 'D':
                grouping(x, y+1, points);
                break;
            case 'L':
                grouping(x-1, y, points);
                break;
            case 'R':
                grouping(x+1, y, points);
                break;
            default:
                throw new IllegalStateException("커맨드 입력이 이상합니다.");
        }
    }

    /**
     * 기존 그룹에 합류시키는 메서드
     */
    private static void joinGroup(int groupId, HashSet<Point> points) {
        HashSet<Point> existGroup = groups.get(groupId);

        for (Point point : points) {
            groupBoard[point.y][point.x] = groupId;
        }

        existGroup.addAll(points);
    }

    /**
     * 새로운 그룹을 만드는 메서드
     */
    private static void makeGroup(HashSet<Point> points) {
        int groupId = getGroupId();
        groups.put(groupId, points);

        for (Point point : points) {
            groupBoard[point.y][point.x] = groupId;
        }
    }

    /**
     * 그룹의 PK를 생성하는 메서드
     */
    private static int getGroupId() {
        return groupId++;
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
}
