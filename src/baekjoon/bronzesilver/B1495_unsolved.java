package baekjoon.bronzesilver;

import java.io.*;

public class B1495_unsolved {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int startVolume;
    private static int maxVolume;

    private static int[] songs;

    private static int max = -1;

    private static long testCounter = 0;

    public static void main(String[] args) throws IOException {
        init();

        calculate(startVolume, 0);

        System.out.println("testCounter = " + testCounter);

        out.write(max+"");
        out.close();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        songs = new int[Integer.parseInt(input[0])];
        startVolume = Integer.parseInt(input[1]);
        maxVolume = Integer.parseInt(input[2]);

        String[] songInput = in.readLine().split(" ");
        for (int i = 0; i < songs.length; i++) {
            songs[i] = Integer.parseInt(songInput[i]);
        }
    }

    private static void calculate(int nowVolume, int index) {
        testCounter++;

        if (nowVolume < 0 || index >= songs.length) {
            return;
        }

        int sum = nowVolume;
        for (int i = index; i < songs.length; i++) {
            calculate(sum - songs[i], i + 1);
            sum += songs[i];
            if (sum > maxVolume) {
                return;
            }
        }

        if (sum > max) {
            max = sum;
        }
    }
    
}
