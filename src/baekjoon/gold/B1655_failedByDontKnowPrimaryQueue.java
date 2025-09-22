package baekjoon.gold;

import java.io.*;
import java.util.ArrayList;

public class B1655_failedByDontKnowPrimaryQueue {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static ArrayList<Integer> numbers;

    private static int size = 0;

    private static int loop;

    private static void testPrint() {
        System.out.println("List = " + numbers.toString());
    }

    public static void main(String[] args) throws IOException {
        init();

        controller();

        // testPrint();

        printer();
    }

    private static void init() throws IOException {
        loop = Integer.parseInt(in.readLine());
        numbers = new ArrayList<>(loop);
    }

    private static void controller() throws IOException {
        for (int i = 0; i < loop; i++) {
            int input = Integer.parseInt(in.readLine());
            int index = indexSearch(input);
            numbers.add(index, input);

            printHalfNumber();
        }
    }

    private static void printHalfNumber() throws IOException {
        if (numbers.size() % 2 != 0) {
            out.write(numbers.get(numbers.size() / 2)+System.lineSeparator());
        } else {
            out.write(numbers.get(numbers.size() / 2 - 1)+System.lineSeparator());
        }

    }

    private static int indexSearch(int n) {
        if (numbers.isEmpty() || numbers.get(0) >= n) {
            return 0;
        }
        if (numbers.size() == 1) {
            if (numbers.get(0) >= n) {
                return 0;
            }
            return 1;
        }
        if (numbers.get(1) >= n) {
            return 1;
        }

        if (numbers.get(numbers.size() - 1) <= n) {
            return numbers.size();
        }

        int floor = 0;
        int ceil = numbers.size();
        while(true) {
            int index = (ceil + floor) / 2;

            if (index >= numbers.size()) {
                return numbers.size();
            }

            int nowValue = numbers.get(index);
            int beforeValue = numbers.get(index-1);
            boolean isSame = nowValue == n;
            boolean isSmaller = nowValue > n; // 현재 인덱스의 값보다 작은지 검사
            boolean isBigger = beforeValue < n; // 이전 인덱스의 값보다 큰지 검사

            if (isSame) {
                return index;
            }

            if (isSmaller && isBigger) {
                return index;
            }

            if (isSmaller) {
                ceil = index - 1;
                continue;
            }
            if (isBigger) {
                floor = index;
                continue;
            }
        }
    }

    private static void printer() throws IOException {
        out.close();
    }


}
