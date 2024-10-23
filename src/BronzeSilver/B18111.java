package BronzeSilver;

import java.io.*;
import java.util.*;

public class B18111 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final TreeSet<Case> cases = new TreeSet<>();

    private static int[][] board;

    private static int width;
    private static int height;
    private static int blocks;

    private static int minHeight = Integer.MAX_VALUE;
    private static int maxHeight = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        init();

        Case bestCase = controller();
        out.write(bestCase.time + " " + bestCase.height);

        out.close();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        height = Integer.parseInt(input[0]);
        width = Integer.parseInt(input[1]);
        blocks = Integer.parseInt(input[2]);

        board = new int[height][width];
        for (int y = 0; y < height; y++) {
            String[] line = in.readLine().split(" ");
            for (int x = 0; x < width; x++) {
                int value = Integer.parseInt(line[x]);
                board[y][x] = value;

                if (value > maxHeight) { maxHeight = value; }
                if (value < minHeight) { minHeight = value; }
            }
        }
    }

    /**
     * 1. 평탄화가 됐는가?(될 경우 경우의 수로 저장하고 반복 종료)
     * 2. 블록을 놓아서 평탄화할수 있는가?(될 경우 경우의 수로 저장하고, 그대로 진행)
     * 3. 최상층 블록을 모두 깎고 다시 1로 돌아감
     */
    private static Case controller() {
        int time = 0;

        while (true) {
            // 평탄화가 됐는지 확인함
            if (isFlat()) {
                cases.add(new Case(time, maxHeight));
                break;
            }

            // 블록을 놓아서 평탄화할 수 있는지 확인함
            int addBlockTime = addBlock();
            if (addBlockTime >= 0) {
                cases.add(new Case(time + addBlockTime, maxHeight));
            }

            // 최상층을 깎고, 그만큼 시간을 더함
            time += cutBlock();
        }

        return cases.first();
    }

    /**
     * 블록의 최상층을 모두 깎고, 그에 필요한 시간을 반환함
     * 또한, maxHeight를 1 낮춤
     */
    private static int cutBlock() {
        int time = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board[y][x] == maxHeight) {
                    board[y][x]--;
                    blocks++;
                    time += 2;
                }
            }
        }

        maxHeight--;
        return time;
    }

    private static boolean isFlat() {
        return maxHeight == minHeight;
    }

    /**
     * 현재 상태에서 블록을 쌓아서 평탄화 할 수 있는지 확인함
     * 불가능할 경우 -1을 반환
     * 가능할 경우, 평탄화에 드는 시간을 반환함
     */
    private static int addBlock() {
        int need = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                need += maxHeight - board[y][x];

                if (need > blocks) {
                    return -1;
                }
            }
        }

        return need;
    }

    private static class Case implements Comparable<Case> {

        int time;
        int height;

        public Case(int time, int height) {
            this.time = time; this.height = height;
        }

        @Override
        public int compareTo(Case o) {
            return this.time < o.time ? -1 :
                    (this.time == o.time ? o.height - this.height : 1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Case aCase = (Case) o;
            return time == aCase.time && height == aCase.height;
        }

        @Override
        public int hashCode() {
            return Objects.hash(time, height);
        }
    }
    
}
