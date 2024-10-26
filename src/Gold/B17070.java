package Gold;

import java.io.*;

public class B17070 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final int HORIZONTAL = -1; // 가로
    private static final int VERTICAL = -2; // 세로
    private static final int DIAGONAL = -3; // 대각선

    private static int result;

    private static int[][] board;
    private static int size;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        size = Integer.parseInt(in.readLine());
        board = new int[size][size];

        for (int y = 0; y < size; y++) {
            String[] input = in.readLine().split(" ");
            for (int x = 0; x < size; x++) {
                board[y][x] = Integer.parseInt(input[x]);
            }
        }

        // (0,0), (0,1)은 파이프가 놓여 있음
        board[0][0] = 2;
        board[0][1] = 2;
    }

    private static void controller() throws IOException {
        move(1, 0, HORIZONTAL);
    }

    /**
     * 상태에 따라 경우의 수를 나눠서 움직이게 함<br>
     * 움직이고 나서 파이프가 [size-1][size-1]에 도착하면 반복 종료
     * @param status 가로 = -1, 세로 = -2, 대각선 = -3
     */
    private static void move(int x, int y, int status) {
        if (x == size-1 && y == size-1) {
            result++;
            return;
        }

        // 가로
        if (status == VERTICAL) {
            moveWhenVertical(x, y);
            return;
        }

        // 세로
        if (status == HORIZONTAL) {
            moveWhenHorizontal(x, y);
            return;
        }

        // 대각선
        if (status == DIAGONAL) {
            moveWhenDiagonal(x, y);
            return;
        }
    }

    /**
     * 가로인 상태에서 움직임을 담당하는 메서드
     * 가로로 한칸 움직이거나, 대각선으로 한칸 움직일 수 있음
     */
    private static void moveWhenHorizontal(int x, int y) {
        // 가로로 움직일 수 있는지 확인하고, 가능하다면 움직임
        if (canMoveHorizontal(x, y)) {
            move(x+1, y, HORIZONTAL);
        }
        // 대각선으로 움직일 수 있는지 확인하고, 가능하다면 움직임
        if (canMoveDiagonal(x, y)) {
            move(x+1, y+1, DIAGONAL);
        }
    }

    /**
     * 세로인 상태에서 움직임을 담당하는 메서드
     * 세로로 한칸 움직이거나, 대각선으로 한칸 움직일 수 있음
     */
    private static void moveWhenVertical(int x, int y) {
        if (canMoveVertical(x, y)) {
            move(x, y+1, VERTICAL);
        }
        if (canMoveDiagonal(x, y)) {
            move(x+1, y+1, DIAGONAL);
        }
    }

    /**
     * 대각선인 상태에서 움직임을 담당하는 메서드
     * 가로, 세로, 대각선으로 움직일 수 있음
     */
    private static void moveWhenDiagonal(int x, int y) {
        if (canMoveHorizontal(x, y)) {
            move(x+1, y, HORIZONTAL);
        }
        if (canMoveVertical(x, y)) {
            move(x, y+1, VERTICAL);
        }
        if (canMoveDiagonal(x, y)) {
            move(x+1, y+1, DIAGONAL);
        }
    }

    /**
     * 가로로 움직이려 할 때 벽이 없는지 확인하는 메서드
     */
    private static boolean canMoveHorizontal(int x, int y) {
        if (x+1 >= size || board[y][x+1] == 1) {
            return false;
        }

        return true;
    }

    /**
     * 세로로 움직이려 할 때 벽이 없는지 확인하는 메서드
     */
    private static boolean canMoveVertical(int x, int y) {
        if (y+1 >= size || board[y+1][x] == 1) {
            return false;
        }

        return true;
    }

    /**
     * 대각선으로 움직이려 할 때 벽이 없는지 확인하는 메서드
     */
    private static boolean canMoveDiagonal(int x, int y) {
        if (x+1 >= size || y+1 >= size) {
            return false;
        }
        if (board[y][x+1] == 1 || board[y+1][x] == 1 || board[y+1][x+1] == 1) {
            return false;
        }

        return true;
    }

    private static void printer() throws IOException {
        out.write(result+"");

        out.close();
    }


}
