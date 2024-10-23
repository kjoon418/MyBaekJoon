package Gold;

import java.io.*;

public class B1074 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static long N;
    private static long targetRow;
    private static long targetColumn;

    public static void main(String[] args) throws IOException {
        init();

        out.write(controller()+"");
        out.close();
    }

    /**
     * 전체 로직을 수행하고, 방문 순서를 반환함
     * @return 방문 순서
     */
    private static long controller() {
        long maxOrder = (long) Math.pow(2, N) * (long) Math.pow(2, N);
        Point start = new Point(0, 0);
        Point end = new Point((long) Math.pow(2, N), (long) Math.pow(2, N));
        Point target = new Point(targetRow, targetColumn);

        int area = whereIsIt(target, start, end);
        Point[] points = resize(area, start, end);
        start = points[0];
        end = points[1];
        long[] orders = getOrder(area, 0, maxOrder);
        long startOrder = orders[0];
        long endOrder = orders[1];

        while (endOrder - startOrder != 1) {
            area = whereIsIt(target, start, end);
            points = resize(area, start, end);
            start = points[0];
            end = points[1];
            orders = getOrder(area, startOrder, endOrder);
            startOrder = orders[0];
            endOrder = orders[1];
        }
        return startOrder;
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        N = Long.parseLong(input[0]);
        targetRow = Long.parseLong(input[1]);
        targetColumn = Long.parseLong(input[2]);
    }

    /**
     * 0구역, 1구역, 2구역, 3구역 중 어디에 속하는지 알아내고 반환하는 메서드
     * @return 구역(0 ~ 3)
     */
    private static int whereIsIt(Point target, Point start, Point end) {
        // 행과 열의 중간 부분
        double rowHalf = (double) (start.row + end.row) / 2;
        double columnHalf = (double) (start.column + end.column) / 2;


        if (target.row < rowHalf) {
            // 행과 열이 모두 중간 부분보다 작다면, 왼쪽 위인 0구역에 해당함
            if (target.column < columnHalf) {
                return 0;
            }
            // 행은 작지만 열은 크다면, 오른쪽 위인 1구역에 해당함
            return 1;
        }
        // 행은 크지만 열은 작다면, 왼쪽 아래인 2구역에 해당함
        if (target.column < columnHalf) {
            return 2;
        }
        // 행도 크고 열도 크다면, 오른쪽 아래인 3구역에 해당함
        return 3;
    }

    /**
     * 어느 구역인지에 따라 행과 열의 최소치와 최대치를 재조정하는 메서드
     * @return Point[] {최소치, 최대치}
      */
    private static Point[] resize(int area, Point start, Point end) {
        // 행과 열의 중간 부분
        double rowHalf = (double) (start.row + end.row) / 2;
        double columnHalf = (double) (start.column + end.column) / 2;

        // 새로 전달할 포인트
        Point newStart = new Point(start.row, start.column);
        Point newEnd = new Point(end.row, end.column);
        Point[] points = new Point[] {newStart, newEnd};

        // 왼쪽 위인 0번 구역이라면, 행과 열 모두 최대치를 줄여야 함
        if (area == 0) {
            newEnd.row = (long) Math.floor(rowHalf);
            newEnd.column = (long) Math.floor(columnHalf);
            return points;
        }

        // 오른쪽 위인 1번 구역이라면, 행은 줄이고 열은 늘려야 함
        if (area == 1) {
            newEnd.row = (long) Math.floor(rowHalf);
            newStart.column = (long) Math.ceil(columnHalf);
            return points;
        }

        // 왼쪽 아래인 2번 구역이라면, 행은 늘리고 열은 줄여야 함
        if (area == 2) {
            newStart.row = (long) Math.ceil(rowHalf);
            newEnd.column = (long) Math.floor(columnHalf);
            return points;
        }

        // 오른쪽 아래인 3번 구역이라면, 행과 열을 모두 늘려야 함
        newStart.row = (long) Math.ceil(rowHalf);
        newStart.column = (long) Math.ceil(columnHalf);
        return points;
    }

    /**
     * 해당 영역의 각 칸들이 몇번부터 몇번까지의 탐색 순위를 지니는지 반환함
     * 최소치 <= n < 최대치 구간을 지님
     * @return int[] {최소차, 최대치}
     */
    private static long[] getOrder(int area, long startOrder, long endOrder) {
        long gap = (endOrder - startOrder) / 4;

        // 왼쪽 위 구역이라면, start <= n < start + gap의 탐색순서를 지님
        if (area == 0) {
            return new long[] {startOrder, startOrder + gap};
        }

        // 오른쪽 위 구역이라면, start + gap <= n < start + gap*2의 탐색순서를 지님
        if (area == 1) {
            return new long[] {startOrder+gap, startOrder + gap*2};
        }

        // 왼쪽 아래 구역이라면, start + gap*2 <= n < start + gap*3의 탐색순서를 지님
        if (area == 2) {
            return new long[] {startOrder + gap*2, startOrder + gap*3};
        }

        // 오른쪽 아래 구역이라면, start + gap*3 <= n < end의 탐색순서를 지님
        return new long[] {startOrder + gap*3, endOrder};
    }

    static class Point {
        long row;
        long column;

        public Point() {}

        public Point(long row, long column) {
            this.row = row; this.column = column;
        }
    }
}
