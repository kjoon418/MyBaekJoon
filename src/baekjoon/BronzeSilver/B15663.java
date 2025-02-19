package baekjoon.BronzeSilver;

import java.io.*;
import java.util.*;

public class B15663 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static ArrayList<String> inputs;

    private static int size;

    private static final TreeSet<String> results = new TreeSet<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            StringTokenizer st1 = new StringTokenizer(o1," ");
            StringTokenizer st2 = new StringTokenizer(o2, " ");

            int i1;
            int i2;
            while(st1.hasMoreTokens() && st2.hasMoreTokens()) {
                i1 = Integer.parseInt(st1.nextToken());
                i2 = Integer.parseInt(st2.nextToken());
                if (i1 != i2) {
                    return i1 - i2;
                }
            }

            return 0;
        }
    });

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        StringTokenizer st1 = new StringTokenizer(in.readLine(), " ");
        inputs = new ArrayList<>(Integer.parseInt(st1.nextToken()));
        size = Integer.parseInt(st1.nextToken());

        StringTokenizer st2 = new StringTokenizer(in.readLine(), " ");
        while (st2.hasMoreTokens()) {
            inputs.add(st2.nextToken());
        }
     }

    private static void controller() throws IOException {
        for (int i = 0; i < inputs.size(); i++) {
            ArrayList<Integer> list = new ArrayList<>();
            makeResult(inputs.get(i), i, list);
        }
    }

    private static void printer() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String result : results) {
            sb.append(result).append(System.lineSeparator());
        }
        out.write(sb.toString());

        out.close();
    }

    private static void makeResult(String input, int index, List<Integer> indexes) {
        indexes.add(index);
        if (indexes.size() >= size) {
            results.add(input);
            return;
        }

        for (int i = 0; i < inputs.size(); i++) {
            if (indexes.contains(i)) {
                continue;
            }

            makeResult(input+" "+inputs.get(i), i, new ArrayList<>(indexes));
        }
    }


}
