package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.UnaryOperator;

/**
 * 백준 3190번 문제 - 뱀(골드)
 */
public class B3190 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int boardSize;
    private static final Set<Point> applePoints = new HashSet<>();
    private static final Map<Integer, TurnCommand> turnCommands = new HashMap<>();

    public static void main(String[] args) throws IOException {
        readInput();

        Snake snake = new Snake(new Point(0, 0), new Right());
        int time = 0;
        while (snake.canMove()) {
            Point nextPoint = snake.getNextPoint();
            snake.move(applePoints.contains(nextPoint));
            applePoints.remove(nextPoint);

            time++;
            if (turnCommands.containsKey(time)) {
                TurnCommand command = turnCommands.get(time);
                snake.turn(command);
            }
        }
        // 이동에 실패했을 때에도 시간은 가기 때문에 +1
        time++;

        out.write(time + "");
        out.close();
    }

    private static void readInput() throws IOException {
        boardSize = Integer.parseInt(in.readLine());

        int appleCount = Integer.parseInt(in.readLine());
        for (int i = 0; i < appleCount; i++) {
            StringTokenizer appleInput = new StringTokenizer(in.readLine(), " ");
            int appleY = Integer.parseInt(appleInput.nextToken()) - 1;
            int appleX = Integer.parseInt(appleInput.nextToken()) - 1;

            applePoints.add(new Point(appleX, appleY));
        }

        int commandCount = Integer.parseInt(in.readLine());
        for (int i = 0; i < commandCount; i++) {
            StringTokenizer commandInput = new StringTokenizer(in.readLine(), " ");
            int time = Integer.parseInt(commandInput.nextToken());
            String rawCommand = commandInput.nextToken();
            TurnCommand command = TurnCommand.from(rawCommand);

            turnCommands.put(time, command);
        }
    }

    private static class Snake {
        private final Set<Point> pointSet = new HashSet<>();
        private final Deque<Point> pointQueue = new ArrayDeque<>();
        private Direction direction;

        public Snake(Point startPoint, Direction startDirection) {
            addPoint(startPoint);
            direction = startDirection;
        }

        public void turn(TurnCommand command) {
            this.direction = command.operate(direction);
        }

        public boolean canMove() {
            Point nextPoint = getNextPoint();

            return !nextPoint.isOutOfBoard() && !pointSet.contains(nextPoint);
        }

        public void move(boolean hasApple) {
            // 전진
            addPoint(getNextPoint());

            // 사과가 없다면 꼬리 자르기
            if (!hasApple) {
                removeTail();
            }
        }

        public Point getNextPoint() {
            return direction.nextPoint(getCurrentPoint());
        }

        private void addPoint(Point point) {
            pointSet.add(point);
            pointQueue.offerLast(point);
        }

        private void removeTail() {
            Point tail = pointQueue.pollFirst();
            pointSet.remove(tail);
        }

        private Point getCurrentPoint() {
            return pointQueue.peekLast();
        }
    }

    private static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isOutOfBoard() {
            return x >= boardSize
                    || y >= boardSize
                    || x < 0
                    || y < 0;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private interface Direction {
        Direction turnRight();
        Direction turnLeft();
        Point nextPoint(Point currentPoint);
    }

    private enum TurnCommand {
        TURN_LEFT(
                "L",
                Direction::turnLeft
        ),
        TURN_RIGHT(
                "D",
                Direction::turnRight
        );

        private final String label;
        private final UnaryOperator<Direction> operator;

        TurnCommand(String label, UnaryOperator<Direction> operator) {
            this.label = label;
            this.operator = operator;
        }

        public static TurnCommand from(String label) {
            return Arrays.stream(values())
                    .filter(command -> command.label.equals(label))
                    .findAny()
                    .orElseThrow();
        }

        public Direction operate(Direction direction) {
            return operator.apply(direction);
        }
    }

    private static class Up implements Direction {
        @Override
        public Direction turnRight() {
            return new Right();
        }

        @Override
        public Direction turnLeft() {
            return new Left();
        }

        @Override
        public Point nextPoint(Point currentPoint) {
            return new Point(currentPoint.x, currentPoint.y - 1);
        }
    }

    private static class Down implements Direction {
        @Override
        public Direction turnRight() {
            return new Left();
        }

        @Override
        public Direction turnLeft() {
            return new Right();
        }

        @Override
        public Point nextPoint(Point currentPoint) {
            return new Point(currentPoint.x, currentPoint.y + 1);
        }
    }

    private static class Left implements Direction {
        @Override
        public Direction turnRight() {
            return new Up();
        }

        @Override
        public Direction turnLeft() {
            return new Down();
        }

        @Override
        public Point nextPoint(Point currentPoint) {
            return new Point(currentPoint.x - 1, currentPoint.y);
        }
    }

    private static class Right implements Direction {
        @Override
        public Direction turnRight() {
            return new Down();
        }

        @Override
        public Direction turnLeft() {
            return new Up();
        }

        @Override
        public Point nextPoint(Point currentPoint) {
            return new Point(currentPoint.x + 1, currentPoint.y);
        }
    }
}
