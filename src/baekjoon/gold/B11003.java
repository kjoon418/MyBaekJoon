package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * 백준 11003번 문제 - 최솟값 찾기(골드)
 * 슬라이딩 윈도우 최솟값 알고리즘
 */
public class B11003 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] informationInput = in.readLine().split(" ");
        int numberCount = Integer.parseInt(informationInput[0]);
        int partialLength = Integer.parseInt(informationInput[1]);

        IndexedNumber[] numbers = readNumbers(numberCount);
        Deque<IndexedNumber> priorityQueue = new ArrayDeque<>();

        for (int i = 0; i < numbers.length; i++) {
            IndexedNumber newValue = numbers[i];
            removeTrashValues(priorityQueue, newValue);
            if (i >= partialLength) {
                priorityQueue.remove(numbers[i - partialLength]);
            }
            priorityQueue.add(newValue);
            int minimumValue = priorityQueue.peekFirst().value;

            out.write(minimumValue + " ");
        }

        out.close();
    }
    
    private static IndexedNumber[] readNumbers(int numberCount) throws IOException {
        StringTokenizer numbersInput = new StringTokenizer(in.readLine(), " ");
        IndexedNumber[] numbers = new IndexedNumber[numberCount];

        for (int i = 0; i < numberCount; i++) {
            int value = Integer.parseInt(numbersInput.nextToken());
            numbers[i] = new IndexedNumber(i, value);
        }

        return numbers;
    }

    private static void removeTrashValues(Deque<IndexedNumber> priorityQueue, IndexedNumber newValue) {
        while (!priorityQueue.isEmpty() && priorityQueue.peekLast().value >= newValue.value) {
            priorityQueue.removeLast();
        }
    }

    private static class IndexedNumber {
        final int index;
        final int value;

        public IndexedNumber(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            IndexedNumber that = (IndexedNumber) o;
            return index == that.index && value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, value);
        }
    }
}
