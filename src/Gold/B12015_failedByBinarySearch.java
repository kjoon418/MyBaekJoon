package Gold;

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class B12015_failedByBinarySearch {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[] array;
    private static int[] resultArray;

    private static int result;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        int size = Integer.parseInt(in.readLine());
        array = new int[size];
        resultArray = new int[size];
        StringTokenizer st = new StringTokenizer(in.readLine(), " ");

        int index = 0;
        while(st.hasMoreTokens()) {
            array[index] = Integer.parseInt(st.nextToken());
            index++;
        }
    }

    private static void controller() throws IOException {
        result = getMaxAscendingLength(0);
    }

    private static void printer() throws IOException {
        out.write(Integer.toString(result));

        out.close();
    }

    private static int getMaxAscendingLength(int index) {
        if (index+1 >= array.length) {
            return 1;
        }
        if (resultArray[index] > 0) {
            return resultArray[index];
        }

        int thisValue = array[index];
        int maxLength = Integer.MIN_VALUE;
        TreeSet<Integer> numbers = new TreeSet<>(((o1, o2) -> o2 - o1));

        for (int i = index+1; i < array.length; i++) {
            if (array[i] > thisValue) {
                if (numbers.isEmpty() || array[i] < numbers.first()) {
                    maxLength = Math.max(maxLength, getMaxAscendingLength(i));
                    numbers.add(array[i]);
                }
            }
        }

        resultArray[index] = maxLength+1;
        return resultArray[index];
    }


}
