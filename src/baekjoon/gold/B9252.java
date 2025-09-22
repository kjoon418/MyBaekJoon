package baekjoon.gold;

import java.io.*;

/**
 * LCS 2 - DP
 */
public class B9252 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static String stringA;
    private static String stringB;

    private static int[][] lcsDp;

    public static void main(String[] args) throws IOException {
        init();

        recordLcsDp();
        int lengthOfLcs = findLengthOfLcs();
        String lcs = findLcs();

        printResult(lengthOfLcs, lcs);
    }

    private static void init() throws IOException {
        stringA = in.readLine();
        stringB = in.readLine();
    }

    private static void recordLcsDp() {
        lcsDp = new int[stringA.length() + 1][stringB.length() + 1];

        for (int i = 0; i < stringA.length(); i++) {
            lcsDp[i][0] = 0;
        }
        for (int i = 1; i < stringB.length(); i++) {
            lcsDp[0][i] = 0;
        }

        for (int a = 1; a <= stringA.length(); a++) {
            for (int b = 1; b <= stringB.length(); b++) {
                char wordA = stringA.charAt(a - 1);
                char wordB = stringB.charAt(b - 1);

                if (wordA == wordB) {
                    lcsDp[a][b] = lcsDp[a-1][b-1] + 1;
                } else {
                    lcsDp[a][b] = Math.max(lcsDp[a][b-1], lcsDp[a-1][b]);
                }
            }
        }
    }

    private static int findLengthOfLcs() {
        return lcsDp[stringA.length()][stringB.length()];
    }

    private static String findLcs() {
        StringBuilder lcs = new StringBuilder();

        int a = stringA.length();
        int b = stringB.length();

        while (a > 0 && b > 0) {
            int currentLength = lcsDp[a][b];
            if (lcsDp[a][b-1] == currentLength) {
                b--;
                continue;
            }
            if (lcsDp[a-1][b] == currentLength) {
                a--;
                continue;
            }

            lcs.insert(0, stringA.charAt(a - 1));
            a--;
            b--;
        }

        return lcs.toString();
    }

    private static void printResult(int lengthOfLcs, String lcs) throws IOException {
        out.write(lengthOfLcs + System.lineSeparator());
        if (lengthOfLcs > 0) {
            out.write(lcs);
        }

        out.close();
    }

}
