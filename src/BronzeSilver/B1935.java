package Unrated;

import java.io.*;
import java.util.*;

public class B1935 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static String input;

    private static final Map<Character, Double> valueMapper = new HashMap<>();

    private static final List<Character> alphabets = new ArrayList<>();

    private static final Deque<Double> stack = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        init();

        // testPrint();

        controller();

        printer();
    }

    private static void testPrint() {
        for (Map.Entry<Character, Double> entry : valueMapper.entrySet()) {
            System.out.println("entry.getKey() + entry.getValue() = " + entry.getKey() + " " + entry.getValue());
        }
    }

    private static void init() throws IOException {
        int loop = Integer.parseInt(in.readLine());

        input = in.readLine();

        saveAlphabet(loop);

        for (int i = 0; i < loop; i++) {
            double value = Double.parseDouble(in.readLine());
            valueMapper.put(alphabets.get(i), value);
        }
    }

    private static void saveAlphabet(int loop) {
        for (int i = 65; i < 65+loop; i++) {
            alphabets.add(Character.valueOf((char)i));
        }
    }

    private static void controller() throws IOException {
        for (int i = 0; i < input.length(); i++) {
            char word = input.charAt(i);

            // 피연산자면 스택에 저장
            if (Character.isAlphabetic(word)) {
                stack.push(valueMapper.get(word));
                continue;
            }

            // 연산자면 연산 후 스택에 저장
            double value1 = stack.pop();
            double value2 = stack.pop();
            double result = operate(value2, value1, word);
            stack.push(result);
        }
    }

    private static double operate(double n1, double n2, char operator) {
        if (operator == '+') {
            return n1 + n2;
        }
        if (operator == '-') {
            return n1 - n2;
        }
        if (operator == '*') {
            return n1 * n2;
        }
        if (operator == '/') {
            return n1 / n2;
        }
        if (operator == '^') {
            return Math.pow(n1, n2);
        }
        if (operator == '%') {
            return n1 % n2;
        }

        // 오류 검출을 위한 에러
        throw new IllegalStateException();
    }

    private static void printer() throws IOException {
        out.write(String.format("%.2f", stack.pop()));

        out.close();
    }

}
