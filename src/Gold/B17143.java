package Gold;

import java.io.*;
import java.util.*;

public class B17143 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int height;
    private static int width;
    private static int id = 1;
    private static int result = 0;

    // 아직 남아있는 상어 전체를 저장하는 컬렉션
    private static final HashMap<Integer, Shark> sharks = new HashMap<>();
    // 상어의 이동 결과를 담는 컬렉션
    private static final HashMap<Point, Shark> movedSharks = new HashMap<>();
    // 낚시왕이 상어를 잡기 위해, 같은 열에 존재하는 상어를 저장하는 컬렉션
    private static final TreeSet<Shark> sameColumnSharks = new TreeSet<>();
    // 이동 후 위치가 같은 상어를 제거하기 위한 컬렉션
    private static final HashMap<Point, TreeSet<Shark>> removeSharks = new HashMap<>();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        StringTokenizer input = new StringTokenizer(in.readLine(), " ");
        height = Integer.parseInt(input.nextToken());
        width = Integer.parseInt(input.nextToken());
        int sharkAmount = Integer.parseInt(input.nextToken());

        for (int i = 0; i < sharkAmount; i++) {
            StringTokenizer sharkInput = new StringTokenizer(in.readLine(), " ");
            int id = generateId();
            int y = Integer.parseInt(sharkInput.nextToken());
            int x = Integer.parseInt(sharkInput.nextToken());
            int speed = Integer.parseInt(sharkInput.nextToken());
            int directionInt = Integer.parseInt(sharkInput.nextToken());
            int size = Integer.parseInt(sharkInput.nextToken());
            Direction direction;
            switch (directionInt) {
                case 1:
                    direction = Direction.UP;
                    break;
                case 2:
                    direction = Direction.DOWN;
                    break;
                case 3:
                    direction = Direction.RIGHT;
                    break;
                case 4:
                    direction = Direction.LEFT;
                    break;
                default:
                    throw new InvalidObjectException("DIRECTION 입력 오류");
            }
            Shark shark = new Shark(id, size, speed, direction, new Point(x, y));
            sharks.put(id, shark);

            if (shark.getPoint().getX() == 1) {
                sameColumnSharks.add(shark);
            }
        }
    }

    private static void controller() throws IOException {
        // 낚시왕이 제일 오른쪽으로 갈 때까지 반복
        for (int i = 1; i <= width; i++) {
            // 가장 가까운 상어를 잡음
            catchShark(i);

            movedSharks.clear();
            removeSharks.clear();
            sameColumnSharks.clear();

            // 모든 상어를 이동시키고, 그 결과를 저장함
            moveEveryShark(i+1);

            // 같은 위치에 여러 상어가 저장되어 있는 곳의 상어를 1개만 남김
            eatShark();
        }
    }

    /**
     * 모든 상어를 이동시키고, 그 결과를 저장하는 메서드
     */
    private static void moveEveryShark(int nextX) {
        for (Map.Entry<Integer, Shark> entry : sharks.entrySet()) {
            Shark shark = entry.getValue();
            shark.move();
            Point point = shark.getPoint().copy();
            int x = point.getX();

            // 상어를 컬럼별로 저장하는 컬렉션에 이동 결과를 반영함
            if (x == nextX) {
                sameColumnSharks.add(shark);
            }

            // 이미 같은 위치에 상어가 저장되어 있다면, 나중에 삭제하기 위해 저장함
            if (movedSharks.containsKey(point)) {
                Shark existShark = movedSharks.get(point);

                if (removeSharks.containsKey(point)) {
                    TreeSet<Shark> removeSharkSet = removeSharks.get(point);
                    removeSharkSet.add(shark);
                    removeSharkSet.add(existShark);
                } else {
                    TreeSet<Shark> removeSharkSet = new TreeSet<>((s1, s2) -> s2.size - s1.size);
                    removeSharkSet.add(shark);
                    removeSharkSet.add(existShark);
                    removeSharks.put(point, removeSharkSet);
                }
            } else {
                movedSharks.put(point, shark);
            }
        }
    }

    /**
     * 한 자리에 2마리 이상의 상어가 있는 곳이 있다면, 가장 큰 상어 하나만 남기는 메서드
     */
    private static void eatShark() {
        for (Map.Entry<Point, TreeSet<Shark>> entry : removeSharks.entrySet()) {
            TreeSet<Shark> sharkSet = entry.getValue();

            int index = 0;
            for (Shark shark : sharkSet) {
                if (index > 0) {
                    // 가장 큰 상어를 제외하고 모두 sharks에서 제거함
                    sharks.remove(shark.getId());
                    shark.die();
                }
                index++;
            }
        }
    }

    private static void printer() throws IOException {
        out.write(result+"");

        out.close();
    }

    /**
     * 같은 열에 있는 상어 중 가장 가까운 상어를 잡는 메서드
     */
    private static void catchShark(int x) {
        for (Shark shark : sameColumnSharks) {
            // 먹히지 않은 상어라면 잡음
            if (shark.isAlive()) {
                result += shark.getSize();
                sharks.remove(shark.getId());
                return;
            }
            // 이미 먹힌 상어라면 다음 상어를 찾음
        }
    }

    /**
     * 유일성을 보장하는 id를 반환하는 메서드
     */
    private static int generateId() {
        return id++;
    }

    private static class Shark implements Comparable<Shark> {
        private final int size, speed, id;
        private Direction direction;
        private final Point point;
        private boolean alive = true;

        public Shark(int id, int size, int speed, Direction direction, Point point) {
            this.id = id;
            this.size = size;
            this.speed = speed;
            this.direction = direction;
            this.point = point;
        }

        public void die() {
            this.alive = false;
        }

        public boolean isAlive() {
            return alive;
        }

        public int getId() {
            return this.id;
        }

        public int getSize() {
            return this.size;
        }

        @Override
        public int compareTo(Shark o) {
            if (this.point.y == o.point.y) {
                return this.id - o.id;
            }
            return this.point.y - o.point.y;
        }

        public void move() {
            // 벽 끝까지 갈 수 있는지 확인
            int leftLength = getLeftLengthAfterReachEnd();
            // 벽 끝까지 못 간다면 단순히 이동시킴
            if (leftLength < 0) {
                simpleMove(speed);
                return;
            }
            if (leftLength == 0) {
                simpleMove(speed);
                changeDirection();
                return;
            }

            // 벽의 끝으로 이동한 후 방향을 바꿈
            setPositionToOpposite();

            // 왕복 가능한 횟수를 구함
            int loopAmount = getLoopAmount(leftLength);
            // 왕복하고 남은 길이를 구함
            int finalLeftLength = getFinalLeftLength(leftLength);

            // 짝수번 왕복했으면, 현재 위치에서부터 나머지만큼 이동하면 됨
            if (loopAmount % 2 == 0) {
                simpleMove(finalLeftLength);
            } else {
                // 홀수번 반복했으면, 반대편으로 이동시킨 후 나머지만큼 이동하면 됨
                setPositionToOpposite();
                simpleMove(finalLeftLength);
            }
        }

        /**
         * 반대편으로 이동시키고 방향을 바꾸는 메서드
         */
        private void setPositionToOpposite() {
            switch (direction) {
                case UP:
                    point.y = 1;
                    break;
                case DOWN:
                    point.y = height;
                    break;
                case LEFT:
                    point.x = 1;
                    break;
                case RIGHT:
                    point.x = width;
                    break;
                default:
                    throw new IllegalStateException("방향 오류");
            }
            changeDirection();
        }

        /**
         * 왕복하고 남은 길이를 구하는 메서드
         */
        private int getFinalLeftLength(int leftLength) {
            switch (direction) {
                case UP:
                case DOWN:
                    return leftLength % (height - 1);
                case LEFT:
                case RIGHT:
                    return leftLength % (width - 1);
                default:
                    throw new IllegalStateException("방향 오류");
            }
        }

        /**
         * 반대편 끝으로 갈 수 있는 횟수를 구하는 메서드
         */
        private int getLoopAmount(int leftLength) {
            switch (direction) {
                case UP:
                case DOWN:
                    return leftLength / (height - 1);
                case LEFT:
                case RIGHT:
                    return leftLength / (width - 1);
                default:
                    throw new IllegalStateException("방향 오류");
            }
        }

        /**
         * 방향만큼 단순히 움직이는 메서드
         */
        private void simpleMove(int length) {
            switch (direction) {
                case UP:
                    point.y -= length;
                    break;
                case DOWN:
                    point.y += length;
                    break;
                case LEFT:
                    point.x -= length;
                    break;
                case RIGHT:
                    point.x += length;
                    break;
                default:
                    throw new IllegalStateException("방향 오류");
            }
        }

        /**
         * 벽 끝까지 가고 남은 길이를 반환하는 메서드
         */
        private int getLeftLengthAfterReachEnd() {
            switch (direction) {
                case UP:
                    return speed - (point.y - 1);
                case DOWN:
                    return speed - (height - point.y);
                case LEFT:
                    return speed - (point.x - 1);
                case RIGHT:
                    return speed - (width - point.x);
                default:
                    throw new IllegalStateException("방향 오류");
            }
        }

        private void changeDirection() {
            switch (direction) {
                case UP:
                    this.direction = Direction.DOWN;
                    return;
                case DOWN:
                    this.direction = Direction.UP;
                    return;
                case LEFT:
                    this.direction = Direction.RIGHT;
                    return;
                case RIGHT:
                    this.direction = Direction.LEFT;
                    return;
            }
        }

        private void changePoint() {
            switch (direction) {
                case UP:
                    point.decreaseY();
                    return;
                case DOWN:
                    point.increaseY();
                    return;
                case LEFT:
                    point.decreaseX();
                    return;
                case RIGHT:
                    point.increaseX();
                    return;
            }
        }

        private Point getPoint() {
            return this.point;
        }

        @Override
        public boolean equals(Object o) {
            Shark shark = (Shark) o;
            return id == shark.id;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

    private static class Point {
        private int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void increaseX() {
            this.x++;
        }

        public void increaseY() {
            this.y++;
        }

        public void decreaseX() {
            this.x--;
        }

        public void decreaseY() {
            this.y--;
        }

        public Point copy() {
            return new Point(this.x, this.y);
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
