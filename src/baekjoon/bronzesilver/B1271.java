package baekjoon.bronzesilver;

import java.io.*;
import java.math.BigInteger;

public class B1271 {

    private static BigInteger money;
    private static BigInteger creatures;
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String input = in.readLine();
        String[] str = input.split(" ");
        money = new BigInteger(str[0]);
        creatures = new BigInteger(str[1]);

        BigInteger moneyByOne = money.divide(creatures);
        BigInteger leftMoney = money.remainder(creatures);

        out.write(moneyByOne+System.lineSeparator());
        out.write(leftMoney+System.lineSeparator());

        out.close();
    }
}
