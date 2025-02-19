package baekjoon.BronzeSilver;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class B1929 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int start;
    private static int end;

    public static void main(String[] args) throws IOException {
        init();

        Set<Integer> primes = new HashSet<>();

        for (int i = 3; i <= end; i += 2) {
            if (isPrime(primes, i)) {
                primes.add(i);
            }
        }

        // 짝수면 의미 없으니 1 증가시켜 홀수로 만듦
        int temp = start;
        if (temp % 2 == 0) {
            temp++;
        }

        for (; temp <= end; temp += 2) {
            if (primes.contains(temp)) {
                out.write(temp+System.lineSeparator());
            }
        }

        out.close();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        start = Integer.parseInt(input[0]);
        end = Integer.parseInt(input[1]);
    }

    private static boolean isPrime(Set<Integer> primes, int number) {
        for (int prime : primes) {
            if (number % prime == 0) {
                return false;
            }
        }
        return true;
    }
    
}
