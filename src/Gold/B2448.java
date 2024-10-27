package Gold;

import java.io.*;
import java.util.*;

public class B2448 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int height;

    private static String result;

    private static StarPoints starpoints;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        height = Integer.parseInt(in.readLine());
        starpoints = new StarPoints(height);
    }

    private static void controller() throws IOException {
        result = starpoints.getResult();
    }

    private static void printer() throws IOException {
        out.write(result);

        out.close();
    }

    private static class StarPoints {
        TreeSet<Integer> stars = new TreeSet<>();
        int space;
        int phase;
        int size;

        public StarPoints(int height) {
            this.space = height-1;
            stars.add(space);
            this.phase = 0;
            this.size = (height / 3) * 6 - 1;
        }

        public String getResult() {
            StringBuilder sb = new StringBuilder();

            while (space >= 0) {
                sb.append(getLine()).append(System.lineSeparator());

                space--;
            }

            return sb.toString();
        }

        public String getLine() {
            TreeSet<Integer> nextStars = new TreeSet<>();
            char[] line = new char[size];
            Arrays.fill(line, ' ');

            if (phase == 0) {
                for (Integer star : stars) {
                    line[star] = '*';
                    nextStars.add(star-1);
                    nextStars.add(star+1);
                }
            }

            if (phase == 1) {
                for (Integer star : stars) {
                    line[star] = '*';
                    nextStars.add(star-1);
                    nextStars.add(star);
                    nextStars.add(star+1);
                }
            }

            if (phase == 2) {
                for (Integer star : stars) {
                    line[star] = '*';

                    if (!stars.contains(star-1) && !stars.contains(star-2)) {
                        nextStars.add(star-1);
                    }
                    if (!stars.contains(star+1) && !stars.contains(star+2)) {
                        nextStars.add(star+1);
                    }
                }
            }

            phase = (phase + 1) % 3;
            stars = nextStars;

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < line.length; i++) {
                sb.append(line[i]);
            }

            return sb.toString();
        }
    }
}
