package baekjoon.bronzesilver;

import java.io.*;
import java.util.*;

public class B11279 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static List<Integer> inputs = new ArrayList<>();

    private static List<Integer> prints = new ArrayList<>();

    private static MaxHeap heap = new MaxHeap();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void controller() {
        MaxHeap maxHeap = new MaxHeap();

        for (int input : inputs) {
            if (input == 0) {
                int out = maxHeap.out();
                prints.add(out);
                continue;
            }

            maxHeap.add(input);
        }
    }

    private static void printer() throws IOException {
        for (Integer print : prints) {
            out.write(print+System.lineSeparator());
        }

        out.close();
    }

    private static void init() throws IOException {
        int loop = Integer.parseInt(in.readLine());

        for (int i = 0; i < loop; i++) {
            inputs.add(Integer.parseInt(in.readLine()));
        }
    }

    static class MaxHeap {

        TreeMap<Integer, List<Integer>> valuesMap = new TreeMap<>((i1, i2) ->
            i2 - i1);

        public void add(int input) {
            if (valuesMap.containsKey(input)) {
                valuesMap.get(input).add(input);
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(input);
                valuesMap.put(input, list);
            }
        }

        public int out() {
            if (valuesMap.isEmpty()) {
                return 0;
            }

            Map.Entry<Integer, List<Integer>> entry = valuesMap.firstEntry();

            List<Integer> list = entry.getValue();
            Integer result = list.remove(0);
            if (list.isEmpty()) {
                valuesMap.remove(entry.getKey());
            }

            return result;
        }

    }
    
}
