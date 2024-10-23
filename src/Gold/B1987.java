package Gold;

import java.io.*;
import java.util.HashSet;

public class B1987 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static String[][] board;

    private static int width;
    private static int height;

    private static int result;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        height = Integer.parseInt(input[0]);
        width = Integer.parseInt(input[1]);
        board = new String[height][width];

        for(int y = 0; y < height; y++) {
            String line = in.readLine();
            for (int x = 0; x < width; x++) {
                board[y][x] = Character.toString(line.charAt(x));
            }
        }
    }

    private static void controller() throws IOException {
        movingHorse(0, 0, "");
    }

    private static void printer() throws IOException {
        out.write(result+"");

        out.close();
    }

    private static void movingHorse(int x, int y, String set) {
        String mySet = set + board[y][x];

        // 왼쪽 칸 검사
        if (x > 0) {
            String value = board[y][x-1];
            if (!mySet.contains(value)) {
                movingHorse(x-1, y, mySet);
            }
        }

        // 오른쪽 칸 검사
        if (x+1 < width) {
            String value = board[y][x+1];
            if (!mySet.contains(value)) {
                movingHorse(x+1, y, mySet);
            }
        }

        // 위쪽 칸 검사
        if (y > 0) {
            String value = board[y-1][x];
            if (!mySet.contains(value)) {
                movingHorse(x, y-1, mySet);
            }
        }

        // 아래쪽 칸 검사
        if (y+1 < height) {
            String value = board[y+1][x];
            if (!mySet.contains(value)) {
                movingHorse(x, y+1, mySet);
            }
        }

        result = Math.max(result, mySet.length());
    }


}
