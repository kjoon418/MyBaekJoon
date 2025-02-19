package baekjoon.Gold;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class B16946 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final int NO_GROUP = -1;

    private static PointInformation[][] board;
    private static int width, height;
    private static int id = 0;
    private static final HashMap<Integer, GroupSize> groupSizes = new HashMap<>();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        StringTokenizer size = new StringTokenizer(in.readLine(), " ");
        height = Integer.parseInt(size.nextToken());
        width = Integer.parseInt(size.nextToken());
        board = new PointInformation[height][width];

        for (int y = 0; y < height; y++) {
            String line = in.readLine();
            for (int x = 0; x < width; x++) {
                char word = line.charAt(x);
                Status status = word == '1' ? Status.WALL : Status.EMPTY;
                board[y][x] = new PointInformation(status);
            }
        }
    }

    private static void controller() throws IOException {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 빈 공간이면서 아직 그룹에 속해있지 않는 칸에 한해서 그룹화한다
                PointInformation info = board[y][x];
                if (info.isEmpty() && !info.hasGroup()) {
                    joinGroup(x, y, getId());
                }
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board[y][x].isWall()) {
                    board[y][x].result = getTotalMoveCount(x, y);
                }
            }
        }
    }

    private static void printer() throws IOException {
        StringBuilder resultBuilder = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                resultBuilder.append(board[y][x].result);
            }
            resultBuilder.append(System.lineSeparator());
        }
        out.write(resultBuilder.toString());

        out.close();
    }

    private static int getId() {
        groupSizes.put(id, new GroupSize());
        return id++;
    }

    private static void joinGroup(int x, int y, int groupId) {
        // 같은 그룹으로 만든다
        board[y][x].groupId = groupId;
        groupSizes.get(groupId).addSize();

        // 벽이 없는 상/하/좌/우로 재귀한다
        if (canGrouping(x, y, Direction.UP, groupId)) {
            joinGroup(x, y-1, groupId);
        }
        if (canGrouping(x, y, Direction.DOWN, groupId)) {
            joinGroup(x, y+1, groupId);
        }
        if (canGrouping(x, y, Direction.LEFT, groupId)) {
            joinGroup(x-1, y, groupId);
        }
        if (canGrouping(x, y, Direction.RIGHT, groupId)) {
            joinGroup(x+1, y, groupId);
        }
    }

    private static boolean canGrouping(int x, int y, Direction direction, int groupId) {
        return switch (direction) {
            case UP -> (y > 0) && board[y-1][x].canGrouping(groupId);
            case DOWN -> (y < height-1) && board[y+1][x].canGrouping(groupId);
            case LEFT -> (x > 0) && board[y][x-1].canGrouping(groupId);
            case RIGHT -> (x < width-1) && board[y][x+1].canGrouping(groupId);
        };
    }

    private static boolean canMove(int x, int y, Direction direction) {
        return switch (direction) {
            case UP -> (y > 0) && board[y - 1][x].canMove();
            case DOWN -> (y < height - 1) && board[y + 1][x].canMove();
            case LEFT -> (x > 0) && board[y][x - 1].canMove();
            case RIGHT -> (x < width - 1) && board[y][x + 1].canMove();
        };
    }

    private static int getTotalMoveCount(int x, int y) {
        // 해당 자리를 포함하기 위해 1로 시작한다
        int count = 1;
        HashSet<Integer> id = new HashSet<>();

        // 상/하/좌/우에 인접한 빈 공간의 그룹을 계산한다
        if (canMove(x, y, Direction.UP)) {
            int groupId = board[y-1][x].groupId;
            id.add(groupId);
            count += groupSizes.get(groupId).size;
        }
        if (canMove(x, y, Direction.DOWN)) {
            int groupId = board[y+1][x].groupId;
            if (!id.contains(groupId)) {
                id.add(groupId);
                count += groupSizes.get(groupId).size;
            }
        }
        if (canMove(x, y, Direction.LEFT)) {
            int groupId = board[y][x-1].groupId;
            if (!id.contains(groupId)) {
                id.add(groupId);
                count += groupSizes.get(groupId).size;
            }
        }
        if (canMove(x, y, Direction.RIGHT)) {
            int groupId = board[y][x+1].groupId;
            if (!id.contains(groupId)) {
                id.add(groupId);
                count += groupSizes.get(groupId).size;
            }
        }

        return count % 10;
    }

    private static class PointInformation {
        Status status; // true=wall, false=empty
        int groupId = NO_GROUP;
        int result = 0;

        public PointInformation(Status status) {
            this.status = status;
        }

        public boolean isWall() {
            return this.status == Status.WALL;
        }

        public boolean isEmpty() {
            return this.status == Status.EMPTY;
        }

        public boolean hasGroup() {
            return groupId != NO_GROUP;
        }

        public boolean canGrouping(int groupId) {
            return (this.status == Status.EMPTY) && (this.groupId != groupId);
        }

        public boolean canMove() {
            return this.status == Status.EMPTY;
        }
    }

    private static class GroupSize {
        int size = 0;

        public void addSize() {
            this.size++;
        }
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private enum Status {
        WALL, EMPTY
    }
}
