package baekjoon.BronzeSilver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class B11399 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final List<Integer> people = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        people.sort((p1, p2) -> p1 - p2);

        int sum = 0;
        int stack = 0;
        for (int time : people) {
            stack += time;
            sum += stack;
        }

        out.write(sum+"");
        out.close();
    }

    private static void init() throws IOException {
        int loop = Integer.parseInt(in.readLine());
        String[] input = in.readLine().split(" ");
        for (int i = 0; i < loop; i++) {
            int person = Integer.parseInt(input[i]);
            people.add(person);
        }
    }
}
