package BronzeSilver;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class B11723 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final Set<Integer> store = new HashSet<>();

    private static int loop;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        out.close();
    }

    private static void controller() throws IOException {
        for (int i = 0; i < loop; i++) {
            String[] input = in.readLine().split(" ");

            if (input[0].equals("add")) {
                add(Integer.parseInt(input[1]));
                continue;
            }

            if (input[0].equals("remove")) {
                remove(Integer.parseInt(input[1]));
                continue;
            }

            if (input[0].equals("check")) {
                check(Integer.parseInt(input[1]));
                continue;
            }

            if (input[0].equals("toggle")) {
                toggle(Integer.parseInt(input[1]));
                continue;
            }

            if (input[0].equals("all")) {
                all();
                continue;
            }

            if (input[0].equals("empty")) {
                empty();
                continue;
            }
        }
    }

    private static void init() throws IOException {
        loop = Integer.parseInt(in.readLine());
    }

    private static void add(int n) {
        store.add(n);
    }

    private static void remove(int n) {
        store.remove(n);
    }

    private static void check(int n) throws IOException {
        if (store.contains(n)) {
            out.write("1"+System.lineSeparator());
        } else {
            out.write("0"+System.lineSeparator());
        }
    }

    private static void toggle(int n) {
        if (store.contains(n)) {
            store.remove(n);
        } else {
            store.add(n);
        }
    }

    private static void all() {
        for (int i = 1; i <= 20; i++) {
            store.add(i);
        }
    }

    private static void empty() {
        store.clear();
    }
}
