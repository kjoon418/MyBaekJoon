package BronzeSilver;

import java.io.*;
import java.util.*;

public class B15654 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final TreeSet<Integer> inputs = new TreeSet<>();
    private static int size;

    private static final List<String> results = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        size = Integer.parseInt(input[1]);

        String[] numbers = in.readLine().split(" ");
        for (String number : numbers) {
            inputs.add(Integer.parseInt(number));
        }
    }

    private static void controller() throws IOException {
        for (int input : inputs) {
            makeResult(input, "");
        }
    }

    private static void printer() throws IOException {
        for (String result : results) {
            out.write(result+System.lineSeparator());
        }

        out.close();
    }

    private static void makeResult(int n, String str) {
        String newStr;
        if (str.isEmpty()) {
            newStr = Integer.toString(n);
        } else {
            newStr = str + " " + n;
        }

        if (newStr.split(" ").length == size) {
            results.add(newStr);
            return;
        }

        for (int input : inputs) {
            HashSet<String> testSet = new HashSet<>(Arrays.asList(newStr.split(" ")));
            if (!testSet.contains(Integer.toString(input))) {
                makeResult(input, newStr);
            }
        }
    }


}
