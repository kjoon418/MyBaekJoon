package BronzeSilver;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class B11047 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final Deque<Integer> moneyStack = new ArrayDeque<>();
    private static int targetMoney;


    public static void main(String[] args) throws IOException {
        init();

        out.write(""+controller());
        out.close();
    }

    private static int controller() {
        int count = 0;
        while (!moneyStack.isEmpty()) {
            int money = moneyStack.pop();

            count += targetMoney / money;
            targetMoney = targetMoney % money;
        }

        return count;
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        int loop = Integer.parseInt(input[0]);
        targetMoney = Integer.parseInt(input[1]);

        for (int i = 0; i < loop; i++) {
            moneyStack.push(Integer.parseInt(in.readLine()));
        }
    }
    
}
