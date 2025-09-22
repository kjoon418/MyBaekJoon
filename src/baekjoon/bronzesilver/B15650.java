package baekjoon.bronzesilver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class B15650 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int endNumber;
    private static int size;

    private static final List<String> results = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        endNumber = Integer.parseInt(input[0]);
        size = Integer.parseInt(input[1]);
    }

    private static void controller() throws IOException {
        for (int i = 1; i <= endNumber; i++) {
            makeResult(i, "");
        }
    }

    private static void makeResult(int n, String str) {
        String newStr;
        if (str.isEmpty()) {
            newStr = Integer.toString(n);
        } else {
            newStr = str + " " + n;
        }

        if (newStr.length() == 1 + (2*(size-1))) {
            results.add(newStr);
            return;
        }

        if (n > endNumber) {
            return;
        }

        for (int i = n+1; i <= endNumber; i++) {
            makeResult(i, newStr);
        }

    }

    private static void printer() throws IOException {
        for (String result : results) {
            out.write(result+System.lineSeparator());
        }

        out.close();
    }


}
