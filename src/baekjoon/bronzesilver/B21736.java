package baekjoon.bronzesilver;

import java.io.*;
import java.util.StringTokenizer;

public class B21736 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static char[][] board;
    private static boolean[][] visitedBoard;
    private static int width, height;
    private static int result = 0;
    private static int startX, startY;

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
        visitedBoard = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            String line = in.readLine();
            for (int x = 0; x < width; x++) {
                char value = line.charAt(x);
                board[y][x] = value;
                visitedBoard[y][x] = false;

                if (value == 'I') {
                    startX = x;
                    startY = y;
                }
            }
        }
    }

    private static void controller() throws IOException {
        move(startX, startY);
    }

    private static void move(int x, int y) {
        visitedBoard[y][x] = true;

        if (board[y][x] == 'P') {
            result++;
        }

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
        return switch (direction) {
            case UP -> y > 0 && !visitedBoard[y-1][x] && board[y-1][x] != 'X';
            case DOWN -> y+1 < height && !visitedBoard[y+1][x] && board[y+1][x] != 'X';
            case LEFT -> x > 0 && !visitedBoard[y][x-1] && board[y][x-1] != 'X';
            case RIGHT -> x+1 < width && !visitedBoard[y][x+1] && board[y][x+1] != 'X';
        };
    }

    private static void printer() throws IOException {
        if (result <= 0) {
            out.write("TT");
        } else {
            out.write(Integer.toString(result));
        }

        out.close();
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
