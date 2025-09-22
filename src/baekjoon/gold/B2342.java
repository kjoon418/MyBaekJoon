package baekjoon.gold;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Dance Dance Revolution - DP
 */
public class B2342 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final int CENTER = 0;
    private static final int TOP = 1;
    private static final int LEFT = 2;
    private static final int BOTTOM = 3;
    private static final int RIGHT = 4;

    private static int[] commands;

    private static final int[][] costBoard = new int[5][5];
    private static final int[][] subCostBoard = new int[5][5];

    public static void main(String[] args) throws IOException {
        init();

        firstStep();

        if (commands.length <= 1) {
            // 입력의 길이가 딱 1일 때는 예외적으로 처리
            int result = Integer.MAX_VALUE;
            for (int[] costs : costBoard) {
                for (int cost : costs) {
                    result = Math.min(result, cost);
                }
            }

            out.write(result + "");
            out.close();
            return;
        }

        for (int i = 1; i < commands.length - 1; i++) {
            step(commands[i]);
        }

        int result = getMinimumCostForLastStep(commands[commands.length - 1]);

        out.write(result + "");
        out.close();
    }

    private static void init() throws IOException {
        StringTokenizer commandsInput = new StringTokenizer(in.readLine(), " ");

        // 마지막으로 입력되는 0은 제외함
        commands = new int[commandsInput.countTokens() - 1];
        for (int i = 0; i < commands.length; i++) {
            commands[i] = Integer.parseInt(commandsInput.nextToken());
        }

        fillBoardWithMaxValue(costBoard);
        fillBoardWithMaxValue(subCostBoard);
    }

    private static void firstStep() {
        int firstCommand = commands[0];
        int cost = calculateCostOfStep(CENTER, firstCommand);

        // 왼발
        costBoard[firstCommand][CENTER] = cost;
        subCostBoard[firstCommand][CENTER] = cost;

        // 오른발
        costBoard[CENTER][firstCommand] = cost;
        subCostBoard[CENTER][firstCommand] = cost;
    }

    private static void step(int commandFoot) {
        fillBoardWithMaxValue(subCostBoard);

        for (int leftFoot = 0; leftFoot < 5; leftFoot++) {
            for (int rightFoot = 0; rightFoot < 5; rightFoot++) {
                // 양 발은 같은 자리에 있을 수 없음
                if (leftFoot == rightFoot) {
                    continue;
                }

                // 이전 값이 무한대라면 계산하지 않음
                int prevTotalCost = costBoard[leftFoot][rightFoot];
                if (prevTotalCost == Integer.MAX_VALUE) {
                    continue;
                }

                // 왼발을 움직이는 경우 계산
                if (rightFoot != commandFoot) {
                    int cost = calculateCostOfStep(leftFoot, commandFoot);
                    int nextTotalCost = prevTotalCost + cost;

                    // 이완
                    subCostBoard[commandFoot][rightFoot] = Math.min(subCostBoard[commandFoot][rightFoot], nextTotalCost);
                }

                // 오른발을 움직이는 경우 계산
                if (leftFoot != commandFoot) {
                    int cost = calculateCostOfStep(rightFoot, commandFoot);
                    int nextTotalCost = prevTotalCost + cost;

                    // 이완
                    subCostBoard[leftFoot][commandFoot] = Math.min(subCostBoard[leftFoot][commandFoot], nextTotalCost);
                }
            }
        }

        copyBoard(subCostBoard, costBoard);
    }

    private static int getMinimumCostForLastStep(int commandFoot) {
        int minimumCost = Integer.MAX_VALUE;

        for (int leftFoot = 0; leftFoot < 5; leftFoot++) {
            for (int rightFoot = 0; rightFoot < 5; rightFoot++) {
                // 양 발은 같은 자리에 있을 수 없음
                if (leftFoot == rightFoot) {
                    continue;
                }

                // 이전 값이 무한대라면 계산하지 않음
                int prevTotalCost = costBoard[leftFoot][rightFoot];
                if (prevTotalCost == Integer.MAX_VALUE) {
                    continue;
                }

                // 왼발을 움직이는 경우 계산
                if (rightFoot != commandFoot) {
                    int cost = calculateCostOfStep(leftFoot, commandFoot);
                    int nextTotalCost = prevTotalCost + cost;

                    minimumCost = Math.min(minimumCost, nextTotalCost);
                }

                // 오른발을 움직이는 경우 계산
                if (leftFoot != commandFoot) {
                    int cost = calculateCostOfStep(rightFoot, commandFoot);
                    int nextTotalCost = prevTotalCost + cost;

                    minimumCost = Math.min(minimumCost, nextTotalCost);
                }
            }
        }

        return minimumCost;
    }

    private static int calculateCostOfStep(int from, int to) {
        if (from == CENTER) {
            return 2;
        }
        if (from == to) {
            return 1;
        }
        if (from == TOP) {
            switch (to) {
                case LEFT, RIGHT:
                    return 3;
                case BOTTOM:
                    return 4;
            }
        }
        if (from == LEFT) {
            switch (to) {
                case TOP, BOTTOM:
                    return 3;
                case RIGHT:
                    return 4;
            }
        }
        if (from == RIGHT) {
            switch (to) {
                case TOP, BOTTOM:
                    return 3;
                case LEFT:
                    return 4;
            }
        }
        if (from == BOTTOM) {
            switch (to) {
                case LEFT, RIGHT:
                    return 3;
                case TOP:
                    return 4;
            }
        }

        throw new IllegalStateException("케이스 누락: [from: " + from + "] [to: " + to + "]");
    }

    private static void copyBoard(int[][] from, int[][] to) {
        for (int i = 0; i < from.length; i++) {
            System.arraycopy(from[i], 0, to[i], 0, to[i].length);
        }
    }

    private static void fillBoardWithMaxValue(int[][] board) {
        for (int[] elements : board) {
            Arrays.fill(elements, Integer.MAX_VALUE);
        }
    }

    private static void printBoardForTest() {
        System.out.println("===BOARD===");
        for (int[] costs : subCostBoard) {
            for (int cost : costs) {
                if (cost == Integer.MAX_VALUE) {
                    System.out.print("- ");
                } else {
                    System.out.print(cost + " ");
                }
            }
            System.out.println();
        }
    }

}
