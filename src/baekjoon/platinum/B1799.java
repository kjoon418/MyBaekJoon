package baekjoon.platinum;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class B1799 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static boolean[][] originalBoard;
    private static int size;
    private static int maxResult = 0;
    private static final int[] direction1 = {1, 1, -1, -1};
    private static final int[] direction2 = {1, -1, 1, -1};
    private static final Deque<boolean[][]> recycleBoards = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    // 입력을 담당
    private static void init() throws IOException {
        size = Integer.parseInt(in.readLine());
        originalBoard = new boolean[size][size];

        StringTokenizer line;
        for (int y = 0; y < size; y++) {
            line = new StringTokenizer(in.readLine(), " ");
            for (int x = 0; x < size; x++) {
                String word = line.nextToken();
                originalBoard[y][x] = word.equals("1");
            }
        }
    }

    // 메인 로직을 담당
    private static void controller() throws IOException {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (originalBoard[y][x]) {
                    maxResult = Math.max(getCase(x, y, 1, copyBoard(originalBoard)), maxResult);
                }
            }
        }
    }

    // 출력을 담당
    private static void printer() throws IOException {
        out.write(Integer.toString(maxResult));

        out.close();
    }

    // 각 상황에 비숍을 놓는 것에 대헤 최대 비숍 수를 반환
    private static int getCase(int prevX, int prevY, int count, boolean[][] board) {
        int maxCount = count;
        boolean firstLoop = true;
        putBishop(prevX, prevY, board);
        boolean[][] savePoint = copyBoard(board);

        for (int y = prevY; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (firstLoop) {
                    x = prevX;
                    firstLoop = false;
                    continue;
                }
                if (board[y][x]) {
                    maxCount = Math.max(getCase(x, y, count + 1, board), maxCount);
                    // 메모리 문제를 해결하기 위해, 사용한 배열은 큐에 보관함
                    recycleBoards.offer(board);
                    board = copyBoard(savePoint);
                }
            }
        }
        recycleBoards.offer(savePoint);

        return maxCount;
    }

    // 파라미터로 전달받은 배열과 똑같은 내용을 지닌 배열을 반환
    private static boolean[][] copyBoard(boolean[][] board) {
        boolean[][] tempBoard;
        // 메모리 문제를 해결하기 위해 큐에서 꺼내 씀
        if (recycleBoards.isEmpty()) {
            tempBoard = new boolean[size][size];
        } else {
            tempBoard = recycleBoards.poll();
        }

        for (int y = 0; y < size; y++) {
            System.arraycopy(board[y], 0, tempBoard[y], 0, size);
        }

        return tempBoard;
    }

    // 비숍을 놓아서 배열에 상황을 반영하는 메서드
    private static void putBishop(int prevX, int prevY, boolean[][] board) {
        for (int i = 0; i < direction1.length; i++) {
            int x = prevX;
            int y = prevY;
            while (x >= 0 && y >= 0 && x < size && y < size) {
                board[y][x] = false;
                x += direction1[i]; // 1, 1, -1, -1
                y += direction2[i]; // 1, -1, 1, -1
            }
        }
    }
}
