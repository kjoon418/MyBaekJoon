package baekjoon.bronzesilver;

import java.io.*;

public class B11659_unsolved {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[] numbers;
    private static int allSum = 0;

    private static int loop;

    public static void main(String[] args) throws IOException {
        init();

        for (int i = 0; i < loop; i++) {
            controller();
        }

        out.close();
    }

    private static void controller() throws IOException {
        String[] input = in.readLine().split(" ");

        int sum = sum(Integer.parseInt(input[0]) - 1, Integer.parseInt(input[1]) - 1);

        out.write(sum+System.lineSeparator());
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        int amount = Integer.parseInt(input[0]);
        loop = Integer.parseInt(input[1]);
        numbers = new int[amount];

        String[] numberInput = in.readLine().split(" ");
        for (int i = 0; i < amount; i++) {
            int value = Integer.parseInt(numberInput[i]);
            numbers[i] = value;
            allSum += value;
        }
    }

    private static int sum(int startIndex, int endIndex) {
        int sum = 0;

        // 더해야 할게 별로 없으면 더해서 구함
        if (endIndex - startIndex < numbers.length) {
            for (int i = startIndex; i <= endIndex; i++) {
                sum += numbers[i];
            }

            return sum;
        }

        // 더해야 할게 너무 많으면 기존 배열의 총 합에서 빼서 구함
        sum = allSum;
        for (int i = 0; i < startIndex; i++) {
            sum -= numbers[i];
        }
        for (int i = endIndex + 1; i < numbers.length; i++) {
            sum -= numbers[i];
        }

        return sum;
    }
    
}
