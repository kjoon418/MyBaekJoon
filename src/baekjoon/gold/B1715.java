package baekjoon.gold;

import java.io.*;
import java.util.TreeSet;

public class B1715 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final TreeSet<Card> cards = new TreeSet<>();
    private static long result;
    private static int id;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        int loop = Integer.parseInt(in.readLine());

        for (int i = 0; i < loop; i++) {
            int value = Integer.parseInt(in.readLine());
            cards.add(new Card(getId(), value));
        }
    }

    private static void controller() throws IOException {
        while(cards.size() > 1) {
            Card existCard1 = cards.first();
            cards.remove(existCard1);
            result += existCard1.value;
            Card existCard2 = cards.first();
            cards.remove(existCard2);
            result += existCard2.value;
            Card newCard = new Card(getId(), existCard1, existCard2);

            cards.add(newCard);
        }
    }

    private static void printer() throws IOException {
        out.write(Long.toString(result));

        out.close();
    }

    private static int getId() {
        return id++;
    }

    private static class Card implements Comparable<Card> {
        private int id;
        private int value;

        public Card(int id, int value) {
            this.id = id;
            this.value = value;
        }

        public Card(int id, Card card1, Card card2) {
            this.id = id;
            this.value = card1.value + card2.value;
        }

        @Override
        public int compareTo(Card o) {
            return this.value == o.value ? this.id - o.id : this.value - o.value;
        }
    }


}
