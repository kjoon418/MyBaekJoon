package BronzeSilver;

import java.io.*;

public class B1669_unsolved {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static long myHeight;
    private static long targetHeight;
    private static long dateCount = 0L;
    private static long increasAmount = 1L;

    public static void main(String[] args) throws IOException {

        // 입력을 받음
        String[] input = in.readLine().split(" ");
        myHeight = Long.parseLong(input[0]);
        targetHeight = Long.parseLong(input[1]);


        controller();

        out.close();
    }

    private static void controller() throws IOException {
        // 키가 딱 맞는 경우 바로 종료
        if (myHeight == targetHeight) {
            out.write("0");
            return;
        }

        // 반복하며 최고점을 찾음
        while(true) {
            // n + (n-1) + (n-2) + ... + 1을 하면 목표량을 딱 만족하는 경우
            if (isPeak(increasAmount)) {
                long result = dateCount + increasAmount;
                out.write(result+"");
                return;
            }
            // (n+1) + n + (n-1) + ... + 1을 하면 목표량을 딱 만족하는 경우
            if (isPeak(increasAmount + 1)) {
                long result = dateCount + increasAmount + 1;
                out.write(result+"");
                return;
            }


            myHeight += increasAmount; // 46
            dateCount++;
            increasAmount++; // 2
        }
    }

    // 지금이 최고점인지 반환하는 함수
    private static boolean isPeak(long height) {
        long sum = 0;
        for (int i = 1; i <= height; i++) {
            sum += i;
        }
        return (sum + myHeight) == targetHeight;
    }

    //
}
