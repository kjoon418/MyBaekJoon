package baekjoon.BronzeSilver;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class B2164 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final Deque<Integer> deck = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        init();

        shuffle();

        out.write(deck.poll()+"");
        out.close();
    }

    private static void init() throws IOException {
        int loop = Integer.parseInt(in.readLine());

        for (int i = 1; i <= loop; i++) {
            deck.offer(i);
        }
    }

    private static void shuffle() {
        while(deck.size() > 1) {
            deck.poll();
            deck.offer(deck.poll());
        }
    }
    
}
