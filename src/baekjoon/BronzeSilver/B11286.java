package baekjoon.BronzeSilver;

import java.io.*;
import java.util.*;

public class B11286 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final TreeMap<Integer, AbsoluteList> heap = new TreeMap<>();

    private static final List<Integer> inputs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        out.close();
    }

    private static void controller() throws IOException {
        for (int input : inputs) {
            if (input == 0) {
                out.write(smallestPop()+System.lineSeparator());
                continue;
            }

            int absInput = Math.abs(input);

            if (heap.containsKey(absInput)) {
                heap.get(absInput).put(input);
            } else {
                AbsoluteList absoluteList = new AbsoluteList();
                absoluteList.put(input);
                heap.put(absInput, absoluteList);
            }
        }
    }

    private static int smallestPop() {
        while(!heap.isEmpty()) {
            Map.Entry<Integer, AbsoluteList> entry = heap.firstEntry();
            int result = entry.getValue().pop();
            if (result == 0) {
                heap.remove(entry.getKey());
                continue;
            }

            return result;
        }

        return 0;
    }

    private static void init() throws IOException {
        int loop = Integer.parseInt(in.readLine());

        for (int i = 0; i < loop; i++) {
            inputs.add(Integer.parseInt(in.readLine()));
        }
    }

    private static class AbsoluteList {

        List<Integer> positiveValues;
        List<Integer> negativeValues;

        public AbsoluteList() {
            this.positiveValues = new ArrayList<>();
            this.negativeValues = new ArrayList<>();
        }

        public void put(int value) {
            if (value > 0) {
                positiveValues.add(value);
            } else {
                negativeValues.add(value);
            }
        }

        public int pop() {
            if (!negativeValues.isEmpty()) {
                return negativeValues.remove(0);
            }
            if (!positiveValues.isEmpty()) {
                return positiveValues.remove(0);
            }

            return 0;
        }


    }
    
}
