package BronzeSilver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class B2667 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[][] board;

    private static int size;

    private static ArrayList<Counter> counters = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();

        out.close();
    }

    private static void init() throws IOException {
        size = Integer.parseInt(in.readLine());
        board = new int[size][size];

        for (int y = 0; y < size; y++) {
            String line = in.readLine();
            for (int x = 0; x < size; x++) {
                board[y][x] = Character.getNumericValue(line.charAt(x));
            }
        }
    }

    private static void controller() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (board[y][x] == 1) {
                    Counter counter = new Counter();
                    counters.add(counter);
                    grouping(x, y, counter);
                }
            }
        }
    }

    private static void printer() throws IOException {
        out.write(counters.size()+System.lineSeparator());

        List<Counter> result = counters.stream()
                .sorted((c1, c2) -> c1.value - c2.value)
                .collect(Collectors.toList());

        for (Counter counter : result) {
            out.write(counter.value+System.lineSeparator());
        }
    }

    /**
     * 값이 1인 타일에서만 실행해야 함
     */
    private static void grouping(int x, int y, Counter counter) {
        board[y][x] = 0;
        counter.value++;

        // 오른쪽 타일
        if (x+1 < size) {
            if (board[y][x+1] == 1) {
                grouping(x+1, y, counter);
            }
        }

        // 왼쪽 타일
        if (x > 0) {
            if (board[y][x-1] == 1) {
                grouping(x-1, y, counter);
            }
        }

        // 위쪽 타일
        if (y > 0) {
            if (board[y-1][x] == 1) {
                grouping(x, y-1, counter);
            }
        }

        // 아래쪽 타일
        if (y+1 < size) {
            if (board[y+1][x] == 1) {
                grouping(x, y+1, counter);
            }
        }
    }

    static class Counter {
        int value;
    }
    
}
