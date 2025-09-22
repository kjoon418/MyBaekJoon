package baekjoon.bronzesilver;

import java.io.*;

public class B16953 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int start;
    private static int end;

    private static int possible;

    private static int result;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        start = Integer.parseInt(input[0]);
        end = Integer.parseInt(input[1]);
    }

    private static void controller() throws IOException {
        int temp = end;
        int count = 1;
        while (temp > start) {
            if (temp % 2 == 0) {
                temp /= 2;
            } else if (temp % 10 == 1) {
                temp /= 10;
            } else {
                break;
            }

            count++;
        }

        if (temp == start) {
            result = count;
        } else {
            result = -1;
        }
    }

    private static void printer() throws IOException {
        out.write(result+"");

        out.close();
    }


}
