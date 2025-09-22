package baekjoon.gold;

import java.io.*;

public class B10026 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[][] rgbBoard;
    private static int[][] rbBoard;

    private static int size;

    private static int rgbBoardCounter = 0;
    private static int rbBoardCounter = 0;

    public static void main(String[] args) throws IOException {
        init();

//        testPrint(rgbBoard);
//        System.out.println();
//        testPrint(rbBoard);

        controller();

        out.write(rgbBoardCounter+" "+rbBoardCounter);
        out.close();
    }

    private static void testPrint(int[][] board) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(board[y][x]+" ");
            }
            System.out.println();
        }
    }

    /**
     * R = 0, G = 1, B = 2
     * 적록색약의 경우 G가 없어서 0과 2뿐이 없게 됨
     */
    private static void init() throws IOException {
        size = Integer.parseInt(in.readLine());
        rgbBoard = new int[size][size];
        rbBoard = new int[size][size];

        for (int y = 0; y < size; y++) {
            String line = in.readLine();

            for (int x = 0; x < size; x++) {
                char value = line.charAt(x);

                if (value == 'R') {
                    rgbBoard[y][x] = 0;
                    rbBoard[y][x] = 0;
                    continue;
                }
                if (value == 'G') {
                    rgbBoard[y][x] = 1;
                    rbBoard[y][x] = 0; // 초록을 빨강으로 취급함
                    continue;
                }
                if (value == 'B') {
                    rgbBoard[y][x] = 2;
                    rbBoard[y][x] = 2;
                }
            }
        }
    }

    private static void controller() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (rgbBoard[y][x] != -1) {
                    rgbBoardCounter++;
                    grouping(rgbBoard, rgbBoard[y][x], x, y);
                }
                if (rbBoard[y][x] != -1) {
                    rbBoardCounter++;
                    grouping(rbBoard, rbBoard[y][x], x, y);
                }
            }
        }
    }

    /**
     * 본인의 값을 -1로 바꾸고, groupId가 같은 인접 타일에 재귀호출함
     */
    private static void grouping(int[][] board, int groupId, int x, int y) {
        board[y][x] = -1;

        // 왼쪽 타일
        if (x > 0) {
            if (board[y][x-1] == groupId) {
                grouping(board, groupId, x-1, y);
            }
        }

        // 오른쪽 타일
        if (x + 1 < size) {
            if (board[y][x+1] == groupId) {
                grouping(board, groupId, x+1, y);
            }
        }

        // 위쪽 타일
        if (y > 0) {
            if (board[y-1][x] == groupId) {
                grouping(board, groupId, x, y-1);
            }
        }

        // 위쪽 타일
        if (y + 1 < size) {
            if (board[y+1][x] == groupId) {
                grouping(board, groupId, x, y+1);
            }
        }
    }


}
