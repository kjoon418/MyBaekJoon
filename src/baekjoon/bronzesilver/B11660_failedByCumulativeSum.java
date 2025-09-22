package baekjoon.bronzesilver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class B11660_failedByCumulativeSum {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[][] board;

    private static int size;
    private static int loop;

    private static final List<TargetPoint> targets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        size = Integer.parseInt(input[0]);
        loop = Integer.parseInt(input[1]);
        board = new int[size][size];

        for (int y = 0; y < size; y++) {
            String[] line = in.readLine().split(" ");
            for (int x = 0; x < size; x++) {
                board[y][x] = Integer.parseInt(line[x]);
            }
        }

        for (int i = 0; i < loop; i++) {
            String[] points = in.readLine().split(" ");
            int fromY = Integer.parseInt(points[0]);
            int fromX = Integer.parseInt(points[1]);
            int toY = Integer.parseInt(points[2]);
            int toX = Integer.parseInt(points[3]);
            targets.add(new TargetPoint(fromX, fromY, toX, toY));
        }
    }

    private static void controller() throws IOException {
        for (int i = 0; i < targets.size(); i++) {
            int sum = getSum(targets.get(i));
            out.write(sum+System.lineSeparator());
        }
    }

    private static void printer() throws IOException {
        out.close();
    }

    private static int getSum(TargetPoint targetPoint) {
        int sum = 0;
        int fromX = targetPoint.fromX - 1;
        int fromY = targetPoint.fromY - 1;
        int toX = targetPoint.toX - 1;
        int toY = targetPoint.toY - 1;

        for (int y = fromY; y <= toY; y++) {
            for (int x = fromX; x <= toX; x++) {
                sum += board[y][x];
            }
        }

        return sum;
    }

    private static class TargetPoint {
        int fromX, fromY, toX, toY;
        public TargetPoint(int fromX, int fromY, int toX, int toY) {
            this.fromX = fromX; this.fromY = fromY;
            this.toX = toX; this.toY = toY;
        }

        public int[] getFrom() {
            return new int[] {fromX, fromY};
        }

        public int[] getTo() {
            return new int[] {toX, toY};
        }
    }


}
