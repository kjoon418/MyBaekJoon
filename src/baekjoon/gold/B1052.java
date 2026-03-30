package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 백준 1052번 문제 - 물병(골드 5)
 * 그리디, 비트마스킹
 */
public class B1052 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] input = in.readLine().split(" ");
        int bottleBinary = Integer.parseInt(input[0]);
        int maximumBottleCount = Integer.parseInt(input[1]);
        int buyCount = 0;

        while (Integer.bitCount(bottleBinary) > maximumBottleCount) {
            bottleBinary++;
            buyCount++;
        }

        out.write(buyCount + "");
        out.close();
    }
}
