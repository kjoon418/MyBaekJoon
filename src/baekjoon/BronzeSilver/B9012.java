package baekjoon.BronzeSilver;

import java.io.*;
import java.util.ArrayDeque;

public class B9012 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int loop;

    public static void main(String[] args) throws IOException {
        init();

        for (int i = 0; i < loop; i++) {
            String input = in.readLine();
            ArrayDeque<Character> stack = new ArrayDeque<>();

            boolean isYes = true;

            for (int j = 0; j < input.length(); j++) {
                char word = input.charAt(j);
                if (word == '(') {
                    stack.push(word);
                    continue;
                }
                if (word == ')') {
                    if (stack.isEmpty() || stack.pop() != '(') {
                        isYes = false;
                        break;
                    }
                }
            }

            if (isYes && stack.isEmpty()) {
                out.write("YES"+System.lineSeparator());
            } else {
                out.write("NO"+System.lineSeparator());
            }
        }

        out.close();
    }

    private static void init() throws IOException {
        loop = Integer.parseInt(in.readLine());
    }
    
}
