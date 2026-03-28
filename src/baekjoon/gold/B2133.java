package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 백준 2133번 문제 - 타일 채우기(골드 4)
 * DP
 */
public class B2133 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[] patternCount;

    public static void main(String[] args) throws IOException {
        int width = Integer.parseInt(in.readLine());
        patternCount = new int[width + 1];

        for (int currentColumn = 1; currentColumn < patternCount.length; currentColumn++) {
            patternCount[currentColumn] = calculateCount(currentColumn);
        }

        out.write(patternCount[patternCount.length - 1] + "");
        out.close();
    }

    private static int calculateCount(int currentColumn) {
        if (currentColumn % 2 != 0) {
            return 0;
        }

        int totalCount = 0;
        int rightmostWallWidth = 2;
        int remainWallWidth = currentColumn - 2;
        while (remainWallWidth >= 0) {
            int rightmostUniquePattern = uniquePatternCount(rightmostWallWidth);
            int remainWallPatternCount = patternCount[remainWallWidth];

            totalCount += rightmostUniquePattern * remainWallPatternCount;

            rightmostWallWidth += 2;
            remainWallWidth -= 2;
        }

        return totalCount + uniquePatternCount(currentColumn);
    }

    private static int uniquePatternCount(int column) {
        if (column % 2 != 0) {
            return 0;
        }
        if (column == 2) {
            return 3;
        }

        return 2;
    }
}
