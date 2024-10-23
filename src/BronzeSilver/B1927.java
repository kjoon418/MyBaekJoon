package BronzeSilver;

import java.io.*;
import java.util.*;

public class B1927 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final TreeMap<Integer, List<Integer>> heap = new TreeMap<>();

    private static int loop;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        out.close();
    }

    private static void init() throws IOException {
        loop = Integer.parseInt(in.readLine());
    }

    private static void controller() throws IOException {
        for (int i = 0; i < loop; i++) {
            int input = Integer.parseInt(in.readLine());

            if (input == 0) {
                out.write(pop()+System.lineSeparator());
                continue;
            }

            put(input);
        }
    }

    private static int pop() {
        if (heap.isEmpty()) {
            return 0;
        }

        Map.Entry<Integer, List<Integer>> entry = heap.firstEntry();

        List<Integer> list = entry.getValue();
        int value = list.get(0);
        list.remove(0);

        if (list.isEmpty()) {
            heap.remove(entry.getKey());
        }

        return value;
    }

    private static void put(int value) {
        if (heap.containsKey(value)) {
            heap.get(value).add(value);
        }
        else {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(value);
            heap.put(value, list);
        }
    }
}
