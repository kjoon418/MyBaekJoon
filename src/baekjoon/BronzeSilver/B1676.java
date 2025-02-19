package baekjoon.BronzeSilver;
import java.io.*;
import java.math.BigInteger;

public class B1676 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		int n = Integer.parseInt(in.readLine());
		int countZero = 0;
		BigInteger fact = fact(n);
		String factStr = fact.toString();
		for (int i = factStr.length()-1; i >= 0; i--) {
			if (factStr.charAt(i) == '0') countZero++;
			else break;
		}
		out.write(countZero+System.lineSeparator());
		out.flush();
	}
	
	static BigInteger fact(int n) {
		BigInteger fact = new BigInteger("1");
				
		for (int i = 1; i <= n; i++) {
			fact = fact.multiply(new BigInteger(Integer.toString(i)));
		}
		return fact;
	}

}
