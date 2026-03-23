package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 14499번 문제 - 주사위 굴리기(골드 4)
 */
public class B14499 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int mapHeight;
    private static int mapWidth;
    private static Point[][] map;

    public static void main(String[] args) throws IOException {
        StringTokenizer information = new StringTokenizer(in.readLine(), " ");
        mapHeight = Integer.parseInt(information.nextToken());
        mapWidth = Integer.parseInt(information.nextToken());
        int diceStartY = Integer.parseInt(information.nextToken());
        int diceStartX = Integer.parseInt(information.nextToken());
        int commandCount = Integer.parseInt(information.nextToken());

        map = new Point[mapHeight][mapWidth];
        for (int y = 0; y < mapHeight; y++) {
            StringTokenizer pointInput = new StringTokenizer(in.readLine(), " ");
            for (int x = 0; x < mapWidth; x++) {
                int pointValue = Integer.parseInt(pointInput.nextToken());
                map[y][x] = new Point(pointValue);
            }
        }

        StringTokenizer commandInput = new StringTokenizer(in.readLine(), " ");
        Dice dice = new Dice(diceStartX, diceStartY);
        for (int i = 0; i < commandCount; i++) {
            switch (commandInput.nextToken()) {
                case "1" -> handleGoEast(dice);
                case "2" -> handleGoWest(dice);
                case "3" -> handleGoNorth(dice);
                case "4" -> handleGoSouth(dice);
            }
        }

        out.close();
    }

    private static void handleGoNorth(Dice dice) throws IOException {
        if (!dice.canMoveNorth()) {
            return;
        }
        Point north = map[dice.y - 1][dice.x];
        dice.moveNorth(north);

        out.write(dice.top + System.lineSeparator());
    }

    private static void handleGoSouth(Dice dice) throws IOException {
        if (!dice.canMoveSouth(mapHeight)) {
            return;
        }
        Point south = map[dice.y + 1][dice.x];
        dice.moveSouth(south);

        out.write(dice.top + System.lineSeparator());
    }

    private static void handleGoEast(Dice dice) throws IOException {
        if (!dice.canMoveEast(mapWidth)) {
            return;
        }
        Point east = map[dice.y][dice.x + 1];
        dice.moveEast(east);

        out.write(dice.top + System.lineSeparator());
    }

    private static void handleGoWest(Dice dice) throws IOException {
        if (!dice.canMoveWest()) {
            return;
        }
        Point west = map[dice.y][dice.x - 1];
        dice.moveWest(west);

        out.write(dice.top + System.lineSeparator());
    }

    private static class Dice {
        private int x, y;
        private int top, bottom, north, south, east, west;

        public Dice(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void moveNorth(Point point) {
            int temp = top;
            top = south;
            south = bottom;
            bottom = north;
            north = temp;

            y--;

            changeValueWith(point);
        }

        public void moveSouth(Point point) {
            int temp = top;
            top = north;
            north = bottom;
            bottom = south;
            south = temp;

            y++;

            changeValueWith(point);
        }

        public void moveEast(Point point) {
            int temp = top;
            top = west;
            west = bottom;
            bottom = east;
            east = temp;

            x++;

            changeValueWith(point);
        }

        public void moveWest(Point point) {
            int temp = top;
            top = east;
            east = bottom;
            bottom = west;
            west = temp;

            x--;

            changeValueWith(point);
        }

        public boolean canMoveNorth() {
            return y - 1 >= 0;
        }

        public boolean canMoveSouth(int mapHeight) {
            return y + 1 < mapHeight;
        }

        public boolean canMoveWest() {
            return x - 1 >= 0;
        }

        public boolean canMoveEast(int mapWidth) {
            return x + 1 < mapWidth;
        }

        private void changeValueWith(Point point) {
            if (point.value == 0) {
                point.value = bottom;
            } else {
                bottom = point.value;
                point.value = 0;
            }
        }
    }

    private static class Point {
        private int value;

        public Point(int value) {
            this.value = value;
        }
    }
}
