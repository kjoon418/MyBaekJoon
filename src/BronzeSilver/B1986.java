package BronzeSilver;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class B1986 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int width; // 체스판(배열)의 너비
    private static int height; // 체스판(배열)의 높이

    private static char[][] board; // 체스판

    private static List<Point> queenPoints = new ArrayList<>();
    private static List<Point> knightPoints = new ArrayList<>();

    private static int count; // 카운트

    

    public static void main(String[] args) throws IOException {
        init();

        calculate();

        out.write(count+"");
        out.close();
    }

    private static void init() throws IOException {
        // 체스판 초기화('\u0000'로 초기화됨)
        String[] boardSize = in.readLine().split(" ");
        width = Integer.parseInt(boardSize[0]);
        height = Integer.parseInt(boardSize[1]);
        board = new char[height][width];

        // 카운트 초기화
        count = width*height;

        // Queen의 위치 초기화 + 카운트 계산
        String[] queens = in.readLine().split(" ");
        int queenAmount = queens.length;
        for (int i = 1; i < queenAmount; i += 2) {
            int x = Integer.parseInt(queens[i]) - 1;
            int y = Integer.parseInt(queens[i+1]) - 1;
            queenPoints.add(new Point(x, y));
            board[y][x] = 'Q';
            count--;
        }

        // Kinght의 위치 초기화 + 카운트 계산
        String[] knights = in.readLine().split(" ");
        int knightAmount = knights.length;
        for (int i = 1; i < knightAmount; i += 2) {
            int x = Integer.parseInt(knights[i]) - 1;
            int y = Integer.parseInt(knights[i+1]) - 1;
            knightPoints.add(new Point(x, y));
            board[y][x] = 'K';
            count--;
        }

        // Pawn의 위치 초기화 + 카운트 계산
        String[] pawns = in.readLine().split(" ");
        int pawnAmount = pawns.length;
        for (int i = 1; i < pawnAmount; i += 2) {
            int x = Integer.parseInt(pawns[i]) - 1;
            int y = Integer.parseInt(pawns[i+1]) - 1;
            board[y][x] = 'P';
            count--;
        }
    }

    private static void calculate() {
        // 리스트에 저장된 포인트마다 계산을 시작함
        for (Point p : queenPoints) {
            calculateQueen(p.x, p.y);
        }

        for (Point p : knightPoints) {
            calculateKnight(p.x, p.y);
        }
    }

    private static void calculateQueen(int fromX, int fromY) {
        int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};

        for (int i = 0; i < 8; i++) {
            int x = fromX + dx[i];
            int y = fromY + dy[i];
            while (true) {
                if (x < 0 || x >= width || y < 0 || y >= height) {
                    break;
                }
                if (board[y][x] == '\u0000') {
                    count--;
                    board[y][x] = 'N';
                    continue;
                }
                if (board[y][x] != 'N') {
                    break;
                }
                x += dx[i];
                y += dy[i];
            }

        }
    }

    private static void calculateKnight(int fromX, int fromY) {
        int[] dx = {2, 2, 1, 1, -2, -2, -1, -1};
        int[] dy = {1, -1, 2, -2, 1, -1, 2, -2};

        for (int i = 0; i < 8; i++) {
            int x = fromX + dx[i];
            int y = fromY + dy[i];

            if (x >= 0 && x < width && y >= 0 && y < height) {
                if (board[y][x] == '\u0000') {
                    count--;
                    board[y][x] = 'N';
                }
            }
        }
    }

    private static class Point {
        int x;
        int y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
