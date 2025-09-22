package baekjoon.bronzesilver;

import java.io.*;
import java.util.HashSet;
import java.util.TreeSet;

public class B1764 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final HashSet<String> cantSee = new HashSet<>();
    private static final HashSet<String> cantListen = new HashSet<>();
    private static final TreeSet<String> result = new TreeSet<>();

    public static void main(String[] args) throws IOException {
        init();

        save();

        out.write(result.size()+System.lineSeparator());
        for (String name : result) {
            out.write(name+System.lineSeparator());
        }

        out.close();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");

        for (int i = 0; i < Integer.parseInt(input[0]); i++) {
            cantListen.add(in.readLine());
        }
        for (int i = 0; i < Integer.parseInt(input[1]); i++) {
            cantSee.add(in.readLine());
        }
    }

    private static void save() {
        for (String name : cantSee) {
            if (cantListen.contains(name)) {
                result.add(name);
            }
        }
    }
    
}
