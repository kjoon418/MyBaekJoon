package baekjoon.bronzesilver;

import java.io.*;
import java.math.BigInteger;

public class B1629_unsolved {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static long a;
    private static int b;
    private static long c;

    public static void main(String[] args) throws IOException {
        String[] s = in.readLine().split(" ");
        a = Long.parseLong(s[0]);
        b = Integer.parseInt(s[1]);
        c = Long.parseLong(s[2]);

        if (b <= 0) {
            out.write((Math.pow(a, b) % c)+"");
        } else if (c <= 0) {
            out.write("0");
        } else {
            out.write(BigInteger.valueOf(a).pow(b).remainder(BigInteger.valueOf(c)).toString());
        }
        out.close();
    }
}
