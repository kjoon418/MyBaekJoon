package BronzeSilver;
import java.io.*;

public class B1463_unsolved {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int original = Integer.parseInt(in.readLine());
		int n = original;
		int[] count = new int[2];
		count[0] = 0;
		count[1] = 0;
		
		while (n != 1) {
			if (n % 3 == 0) {
				n /= 3;
				count[0]++;
				continue;
			}
			if ((n-1)%3 == 0) {
				n--;
				count[0]++;
				continue;
			}
			if (n % 2 == 0) {
				n /= 2;
				count[0]++;
				continue;
			}
			if ((n-1)%2 == 0) {
				n--;
				count[0]++;
				continue;
			}
			n -= 1;
			count[0]++;
		}
		n = original;
		while (n != 1) {
			if (n % 2 == 0) {
				n /= 2;
				count[1]++;
				continue;
			}
			if ((n-1)%2 == 0) {
				n--;
				count[1]++;
				continue;
			}
			if (n % 3 == 0) {
				n /= 3;
				count[1]++;
				continue;
			}
			if ((n-1)%3 == 0) {
				n--;
				count[1]++;
				continue;
			}
			n -= 1;
			count[0]++;
		}
		if (count[0] < count[1]) System.out.println(count[0]);
		else System.out.println(count[1]);
	}

}
