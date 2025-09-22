package baekjoon.gold;

import java.io.*;

public class B9935_unsovled {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static String original;
    private static String result;
    private static String bomb;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        original = in.readLine();
        bomb = in.readLine();
    }

    private static void controller() throws IOException {
        StringBuilder temp = new StringBuilder();
        int size = 10000;
        int startIndex = 0;
        int endIndex = size;

        while (true) {
            temp.append(original.substring(startIndex, endIndex));
            startIndex = endIndex;
            endIndex += size;

            int index = temp.indexOf(bomb);
            if (index < 0) {
                break;
            }
            temp.delete(index, index+bomb.length());
        }



        if (temp.isEmpty()) {
            result = "FRULA";
        } else {
            result = temp.toString();
        }
    }

    private static void printer() throws IOException {
        out.write(result);

        out.close();
    }


}
