package baekjoon.bronzesilver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class B11053 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final List<Input> inputs = new ArrayList<>();

    private static int result;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        int size = Integer.parseInt(in.readLine());

        StringTokenizer st = new StringTokenizer(in.readLine(), " ");
        while (st.hasMoreTokens()) {
            inputs.add(new Input(Integer.parseInt(st.nextToken())));
        }
    }

    private static void controller() throws IOException {
        for (int i = 0; i < inputs.size(); i++) {
            Input input = inputs.get(i);
            result = Math.max(input.getLengthOfAsc(i, inputs), result);
        }
    }

    private static void printer() throws IOException {
        out.write(result+"");

        out.close();
    }

    private static class Input {
        int value;
        int lengthOfAsc = 0;

        public Input(int value) { this.value = value; }

        public int getLengthOfAsc(int index, List<Input> inputs) {
            if (lengthOfAsc > 0) {
                return this.lengthOfAsc;
            }

            int tempLength = 0;
            for (int i = index+1; i < inputs.size(); i++) {
                Input nextInput = inputs.get(i);
                if (nextInput.value > this.value) {
                    tempLength = Math.max(tempLength, nextInput.getLengthOfAsc(i, inputs));
                }
            }

            this.lengthOfAsc = tempLength + 1;
            return lengthOfAsc;
        }
    }


}
