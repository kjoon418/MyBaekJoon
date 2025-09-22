package baekjoon.bronzesilver;

import java.io.*;

public class B5525_failedByRabinKarp {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static String IO;
    private static int N;
    private static int M;
    private static String inputString;
    private static int IoLength;
    private static int inputStringLength;

    public static void main(String[] args) throws IOException {
        init();
        out.write(findIO()+"");
        out.close();
    }

    private static void init() throws IOException {
        N = Integer.parseInt(in.readLine());
        N = Math.min(N, 100);
        M = Integer.parseInt(in.readLine());
        M = Math.min(M, 10000);
        inputString = in.readLine();
        if (inputString.length() > M) {
            inputString = inputString.substring(0, M);
        }
        makeIO();

        IoLength = IO.length();
        inputStringLength = inputString.length();
    }

    private static void makeIO() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            sb.append("IO");
        }
        sb.append("I");

        IO = sb.toString();
    }

    private static long findIO() {
        long sum = 0;
        int index = 0;

        while(true) {
            if (index + IoLength > inputStringLength) {
                break;
            }

            if (inputString.charAt(index) == 0) {
                index++;
                continue;
            }
            String compareString = inputString.substring(index, index + IoLength);
            if (rabinKarp(IO, compareString)) {
                sum++;
            }
            index++;
        }

        return sum;
    }

    private static boolean rabinKarp(String s1, String s2) {
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();

        long s1Hash = 0;
        long s2Hash = 0;

        int power = 1;
        for (int i = 0; i < c1.length; i++) {
            s1Hash += (long) Math.pow(c1[i], power);
            s2Hash += (long) Math.pow(c2[i], power);
        }

        if (s1Hash == s2Hash) {
            return s1.equals(s2);
        }
        return false;
    }
    
}
