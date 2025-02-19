package baekjoon.BronzeSilver;

import java.io.*;

public class B1010_unsolved {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int westAmount;
    private static int eastAmount;

    public static void main(String[] args) throws IOException {
        int roofCount = Integer.parseInt(in.readLine());

        for (int i = 0; i < roofCount; i++) {
            String[] s = in.readLine().split(" ");
            westAmount = Integer.parseInt(s[0]);
            eastAmount = Integer.parseInt(s[1]);

            long count = getCase(westAmount, eastAmount);

            out.write(count + System.lineSeparator());
        }

        out.close();
    }

    // 경우의 수를 계산하는 메소드
    private static long getCase(int leftWest, int leftEast) {
        // 현재 다리가 놓을 수 있는 경우의 수는 east - west + 1
        long max = leftEast - leftWest + 1;

        if (max == 1) return max;

        long sum = 0;

        for (int i = 1; i <= max; i++) {
            System.out.println("west = " + westAmount + " sum = " + sum);
            sum += i * max;
        }

        return sum;
    }

    // 팩토리얼을 구하는 함수
    private static long getFactorial(long l) {
        long f = 1L;
        if (l == 0L) return 0;
        for (int i = 1; i <= l; i++) {
            f *= i;
        }

        return f;
    }
}
