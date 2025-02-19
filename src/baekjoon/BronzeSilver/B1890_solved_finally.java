package baekjoon.BronzeSilver;

import java.io.*;

public class B1890_solved_finally {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    // 게임 판의 크기와, 배열의 끝
    private static int size;
    private static int maxIndex;

    // 게임판을 표현할 2중 배열
    private static int[][] board;

    // 각 칸의 경우의 수를 표현할 2중 배열
    private static long[][] caseBoard;

    public static void main(String[] args) throws IOException {
        // 입력 및 초기화
        init();

        // 맨 오른쪽 열의 경우의 수를 계산함
        setRight();

        // 맨 아래 행의 경우의 수를 계산함
        setBottom();

        // 남은 오른쪽 아래에서부터 차근차근 경우의 수를 계산함
        setRest();

        out.write(caseBoard[0][0]+"");
        out.close();

        // showBoard(board, caseBoard);
    }

    // 맨 오른쪽과 맨 아래를 제외한 나머지를 계산함
    private static void setRest() {
        for (int y = maxIndex - 1; y >= 0; y--) {
            for (int x = maxIndex - 1; x >= 0; x--) {
                int number = board[x][y];
                // 아래 경우의 수를 얻음
                long caseBottom = 0;
                if (number + x <= maxIndex) { // 인덱스 범위를 초과하지 않아야 함
                    caseBottom = caseBoard[x + number][y];
                }

                // 오른쪽 경우의 수를 얻음
                long caseRight = 0;
                if (number + y <= maxIndex) { // 인덱스 범위를 초과하지 않아야 함
                    caseRight = caseBoard[x][y + number];
                }

                caseBoard[x][y] = caseBottom + caseRight;
            }
        }
    }

    private static void setRight() {
        // 맨 오른쪽 열은, 아래로 내려가는 경우의 수 밖에 없음
        for (int y = maxIndex; y >= 0; y--) {
            int number = board[maxIndex][y];

            // 인덱스의 범위를 초과하는 경우
            if (y + number > maxIndex) {
                caseBoard[maxIndex][y] = 0;
                continue;
            }

            // 내가 거쳐가는 땅이 목적지에 도착할 수 있는 경우
            if (caseBoard[maxIndex][y + number] > 0) {
                caseBoard[maxIndex][y] = 1;
                continue;
            }

            // 딱 목적지에 도착하는 경우
            if (y + number == maxIndex) {
                caseBoard[maxIndex][y] = 1;
                continue;
            }
        }
    }

    private static void setBottom() {
        // 맨 아래 행은, 오른쪽으로 가는 경우의 수 밖에 없음
        for (int x = maxIndex; x >= 0; x--) {
            int number = board[x][maxIndex];

            // 인덱스의 범위를 초과하는 경우
            if (x + number > maxIndex) {
                caseBoard[x][maxIndex] = 0;
                continue;
            }

            // 내가 거쳐가는 땅이 목적지에 도착할 수 있는 경우
            if (caseBoard[x + number][maxIndex] > 0) {
                caseBoard[x][maxIndex] = 1;
                continue;
            }

            // 딱 목적지에 도착하는 경우
            if (x + number == maxIndex) {
                caseBoard[x][maxIndex] = 1;
                continue;
            }
        }
    }

    private static void init() throws IOException {
        size = Integer.parseInt(in.readLine());
        maxIndex = size - 1;
        board = new int[size][size];
        caseBoard = new long[size][size];

        // 계산하지 않은 모든 인덱스는 0으로 초기화함
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                caseBoard[x][y] = -0L;
            }
        }
        caseBoard[maxIndex][maxIndex] = 0L;

        for (int y = 0; y < size; y++) {
            String str = in.readLine();
            String[] strs = str.split(" ");
            for (int x = 0; x < size; x++) {
                board[x][y] = Integer.parseInt(strs[x]);
            }
        }
    }

    // 테스트를 위해 보드를 출력하는 메서드
    private static void showBoard(int[][] board, long[][] caseBoard) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                System.out.print(board[y][x]+" ");
            }
            System.out.println();
        }

        System.out.println("=================");

        for (int x = 0; x < caseBoard.length; x++) {
            for (int y = 0; y < caseBoard[x].length; y++) {
                System.out.print(caseBoard[y][x]+" ");
            }
            System.out.println();
        }

    }
}
