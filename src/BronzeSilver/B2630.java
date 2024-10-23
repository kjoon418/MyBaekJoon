package BronzeSilver;

import java.io.*;

public class B2630 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static boolean[][] originalPaper;

    private static int whitePaperCounter = 0;
    private static int bluePaperCounter = 0;

    public static void main(String[] args) throws IOException {
        init();

        // testPrint();

        cutPaper(originalPaper);

        out.write(whitePaperCounter+System.lineSeparator());
        out.write(bluePaperCounter+System.lineSeparator());
        out.close();
    }

    /**
     * 제대로 입력됐는지 테스트하기 위한 메서드
     */
    private static void testPrint() {
        for (int y = 0; y < originalPaper.length; y++) {
            for (int x = 0; x < originalPaper[y].length; x++) {
                System.out.print(originalPaper[y][x] + " ");
            }
            System.out.println();
        }
    }

    private static void init() throws IOException {
        int size = Integer.parseInt(in.readLine());
        originalPaper = new boolean[size][size];
        for (int y = 0; y < size; y++) {
            String[] input = in.readLine().split(" ");
            for (int x = 0; x < size; x++) {
                if (input[x].equals("1")) {
                    originalPaper[y][x] = true;
                } else {
                    originalPaper[y][x] = false;
                }
            }
        }
    }

    /**
     * 현재 종이가 모두 파란지 혹은 하얀지를 반환하는 메서드
     * 또한, 모두 파랗거나 하얄 경우 자동으로 카운트도 증가시킴
     */
    private static boolean isAllTheSame(boolean[][] paper) {
        // 0, 0칸이 true인지 false인지 찾음
        boolean first = paper[0][0];

        // 0, 0칸과 다른 것이 하나라도 있으면 false를 반환함
        for (int y = 0; y < paper.length; y++) {
            for (int x = 0; x < paper[y].length; x++) {
                if (paper[y][x] != first) {
                    return false;
                }
            }
        }

        // 모두 같다면 카운트를 증가시키고 true를 반환함
        if (first) {
            // true는 파란색
            bluePaperCounter++;
        } else {
            whitePaperCounter++;
        }
        return true;
    }

    /**
     * 종이를 나누는 메서드
     * 더 이상 나눌 수 없을 때(isAllTheSame() -> true) 까지 재귀호출함
     */
    private static void cutPaper(boolean[][] paper) {
        if (!isAllTheSame(paper)) {
            // 종이의 중간을 찾음
            double half = paper.length / 2.0;
            int halfSize = (int) Math.ceil(half);

            // 중간을 기준으로 나눔
            // case 1: 왼쪽 위
            boolean[][] slicePaper1 = new boolean[halfSize][halfSize];
            for (int y = 0; y < halfSize; y++) {
                for (int x = 0; x < halfSize; x++) {
                    slicePaper1[y][x] = paper[y][x];
                }
            }
            cutPaper(slicePaper1);

            // case 2: 오른쪽 위
            boolean[][] slicePaper2 = new boolean[halfSize][halfSize];
            for (int y = 0; y < halfSize; y++) {
                for (int x = 0; x < halfSize; x++) {
                    slicePaper2[y][x] = paper[y][halfSize + x];
                }
            }
            cutPaper(slicePaper2);

            // case 3: 왼쪽 아래
            boolean[][] slicePaper3 = new boolean[halfSize][halfSize];
            for (int y = 0; y < halfSize; y++) {
                for (int x = 0; x < halfSize; x++) {
                    slicePaper3[y][x] = paper[halfSize + y][x];
                }
            }
            cutPaper(slicePaper3);

            // case 4: 오른쪽 아래
            boolean[][] slicePaper4 = new boolean[halfSize][halfSize];
            for (int y = 0; y < halfSize; y++) {
                for (int x = 0; x < halfSize; x++) {
                    slicePaper4[y][x] = paper[halfSize + y][halfSize + x];
                }
            }
            cutPaper(slicePaper4);

        }
    }

}
