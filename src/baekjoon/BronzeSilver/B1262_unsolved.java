package baekjoon.BronzeSilver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class B1262_unsolved {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final String[] alphabets = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    private static final List<String> tiles = new ArrayList<>();

    private static int x1, x2, y1, y2;

    private static int n;

    public static void main(String[] args) throws IOException {
        init();

        int startHeight = x1 % (2 * n - 1);
        int startWidth = y1 % n;

        String[] tile = getTile(n);

    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        x1 = Integer.parseInt(input[1]);
        y1 = Integer.parseInt(input[2]);
        x2 = Integer.parseInt(input[3]);
        y2 = Integer.parseInt(input[4]);
    }

    private static String[] getTile(int n) {
        String[] result = new String[n*2 - 1];

        List<String> getAlphabet = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            getAlphabet.add(alphabets[i]);
        }
        List<String> alphaList = getAlphabet.reversed();

        for (int i = 1; i <= n; i++) {
            StringBuilder sb = new StringBuilder();

            // ....
            for (int j = 0; j < n-i; j++) {
                sb.append(".");
            }

            // edcba
            for (int j = 0; j < i; j++) {
                String alphabet = alphaList.get(j);
                sb.append(alphabet);
            }
            // bcde
            for (int j = i - 2; j >= 0; j--) {
                String alphabet = alphaList.get(j);
                sb.append(alphabet);
            }

            // ....
            for (int j = 0; j < n-i; j++) {
                sb.append(".");
            }

            result[i-1] = sb.toString();
        }

        for (int i = 0; i < result.length / 2; i++) {
            result[result.length - i - 1] = result[i];
        }

        return result;
    }
}
