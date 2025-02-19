package baekjoon.BronzeSilver;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class B1900 {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static ArrayList<Wrestler> wrestlers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        // Collections.sort(wrestlers);

        wrestlers.stream()
                .sorted()
                .forEach((wrestler -> {
                    try {
                        out.write(wrestler+System.lineSeparator());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }));

        out.close();
    }

    private static void init() throws IOException {
        int amount = Integer.parseInt(in.readLine());

        for (int i = 0; i < amount; i++) {
            String[] input = in.readLine().split(" ");
            int number = i + 1;
            int power = Integer.parseInt(input[0]);
            int magicRing = Integer.parseInt(input[1]);
            Wrestler thisWrestler = new Wrestler(number, power, magicRing);

            for (Wrestler wrestler : wrestlers) {
                thisWrestler.match(wrestler);
            }

            wrestlers.add(thisWrestler);
        }
    }

    private static class Wrestler implements Comparable<Wrestler> {

        int number;
        int power;
        int magicRing;
        long winCount = 0;

        Wrestler(int number, int power, int magicRing) {
            this.number = number;
            this.power = power;
            this.magicRing = magicRing;
        }

        public void match(Wrestler other) {
            int thisPower = this.power + (other.power * this.magicRing);
            int otherPower = other.power + (this.power * other.magicRing);

            if (thisPower > otherPower) {
                this.winCount++;
                return;
            }
            other.winCount++;
        }

        @Override
        public int compareTo(Wrestler o) {
            return Long.compare(o.winCount, this.winCount);
        }

        @Override
        public String toString() {
            return this.number+"";
        }
    }
}
