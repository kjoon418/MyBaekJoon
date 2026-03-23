package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 25682번 문제 - 체스판 다시 칠하기(골드 4)
 */
public class B25682 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final boolean WHITE = true;
    private static final boolean BLACK = false;

    private static boolean[][] board;
    private static int boardWidth;
    private static int boardHeight;

    public static void main(String[] args) throws IOException {
        StringTokenizer sizeInput = new StringTokenizer(in.readLine(), " ");
        boardHeight = Integer.parseInt(sizeInput.nextToken());
        boardWidth = Integer.parseInt(sizeInput.nextToken());
        int targetSize = Integer.parseInt(sizeInput.nextToken()) - 1;

        board = new boolean[boardHeight][boardWidth];
        for (int y = 0; y < boardHeight; y++) {
            String[] colors = in.readLine().split("");
            for (int x = 0; x < boardWidth; x++) {
                String colorLabel = colors[x];
                boolean color = colorLabel.equals("W");
                board[y][x] = color;
            }
        }

        int[][] whiteStartSubtotals = calculateSubtotals(WHITE);
        int[][] blackStartSubtotals = calculateSubtotals(BLACK);

        int minimumResult = Integer.MAX_VALUE;
        for (int startY = 0; startY < boardHeight - targetSize; startY++) {
            for (int startX = 0; startX < boardWidth - targetSize; startX++) {
                int endY = startY + targetSize;
                int endX = startX + targetSize;

                int blackSubtotal = calculateSubTotal(startX, startY, endX, endY, blackStartSubtotals);
                int whiteSubtotal = calculateSubTotal(startX, startY, endX, endY, whiteStartSubtotals);
                minimumResult = Math.min(minimumResult, blackSubtotal);
                minimumResult = Math.min(minimumResult, whiteSubtotal);
            }
        }

        out.write(minimumResult + "");
        out.close();
    }

    private static int[][] calculateSubtotals(boolean startColor) {
        int[][] subtotals = new int[boardHeight][boardWidth];

        for (int y = 0; y < boardHeight; y++) {
            // 짝수라면 맨 왼쪽은 시작 색깔과 같아야 함
            boolean correctColor = (y % 2 == 0) ? startColor : !startColor;
            for (int x = 0; x < boardWidth; x++) {
                int sum = 0;
                if (board[y][x] != correctColor) {
                    sum++;
                }
                if (y > 0) {
                    sum += subtotals[y - 1][x];
                }
                if (x > 0) {
                    sum += subtotals[y][x - 1];
                }
                if (x > 0 && y > 0) {
                    sum -= subtotals[y - 1][x - 1];
                }
                subtotals[y][x] = sum;

                correctColor = !correctColor;
            }
        }

        return subtotals;
    }

    private static int calculateSubTotal(int startX, int startY, int endX, int endY, int[][] subtotals) {
        int result = subtotals[endY][endX];

        if (startX > 0) {
            result -= subtotals[endY][startX - 1];
        }
        if (startY > 0) {
            result -= subtotals[startY - 1][endX];
        }
        if (startX > 0 && startY > 0) {
            result += subtotals[startY - 1][startX - 1];
        }

        return result;
    }
}
