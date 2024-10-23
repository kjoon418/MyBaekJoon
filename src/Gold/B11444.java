package Gold;

import java.io.*;

public class B11444 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static long N;

    private static long fibo;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        N = Long.parseLong(in.readLine());
    }

    private static void controller() throws IOException {
        long last1 = 1;
        long last2 = 0;

        for (long i = 2; i < N; i++) {
            fibo = last1 + last2;
            last2 = last1;
            last1 = fibo;
        }
    }

    private static void printer() throws IOException {
        out.write((fibo % 1_000_000_007)+System.lineSeparator());

        out.close();
    }


}
