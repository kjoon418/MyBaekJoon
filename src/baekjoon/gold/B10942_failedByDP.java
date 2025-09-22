package baekjoon.gold;

import java.io.*;
import java.util.*;

public class B10942_failedByDP {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static String[] array;
    private static final List<String> testCases = new ArrayList<>();

    private static final StringBuilder results = new StringBuilder();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        int length = Integer.parseInt(in.readLine());
        array = new String[length];

        StringTokenizer input = new StringTokenizer(in.readLine(), " ");
        int loop = 0;
        while (input.hasMoreElements()) {
            array[loop] = input.nextToken();
            loop++;
        }

        int testAmount = Integer.parseInt(in.readLine());
        for (int i = 0; i < testAmount; i++) {
            testCases.add(in.readLine());
        }
    }

    private static void controller() throws IOException {
        Deque<String> stack = new ArrayDeque<>();
        for (String testCase : testCases) {
            StringTokenizer st = new StringTokenizer(testCase, " ");
            int startIndex = Integer.parseInt(st.nextToken()) -1;
            int endIndex = Integer.parseInt(st.nextToken());

            addInStack(startIndex, endIndex, stack);
            if (compareWithStack(startIndex, endIndex, stack)) {
                results.append("1\n");
            } else {
                results.append("0\n");
            }
            stack.clear();
        }
    }

    private static void addInStack(int startIndex, int endIndex, Deque<String> stack) {
        // 만약 목표 배열이 홀수 칸이라면 가운데를 버림
        int length = (endIndex - startIndex) / 2;

        for (int i = startIndex; i < startIndex + length; i++) {
            stack.push(array[i]);
        }
    }

    private static boolean compareWithStack(int startIndex, int endIndex, Deque<String> stack) {
        // Java 15 호환 문제로 인해 아래처럼 작성
        int length = (int) Math.ceil(((double) endIndex - (double) startIndex) / 2);

        for (int i = startIndex+length; i < endIndex; i++) {
            String pop = stack.pop();
            if (!pop.equals(array[i])) {
                return false;
            }
        }
        return true;
    }

    private static void printer() throws IOException {
        out.write(results.toString());

        out.close();
    }


}
