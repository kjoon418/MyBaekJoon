package BronzeSilver;
import java.io.*;

public class M2908 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] str = in.readLine().split(" ");
		int a = Integer.parseInt(str[0]);
		int b = Integer.parseInt(str[1]);
		a = reverse(a);
		b = reverse(b);
		if (a > b) {
			System.out.println(a);
		}
		else {
			System.out.println(b);
		}
	}

	static int reverse(int n) {
		String str = Integer.toString(n);
		String num = "";
		for (int i = str.length()-1; i >= 0; i--) {
			num += str.charAt(i);
		}
		return Integer.parseInt(num);
	}
}