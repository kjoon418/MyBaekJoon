package BronzeSilver;
import java.io.*;

public class B1110 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(getCycle(Integer.parseInt(in.readLine())));
	}

	static int getCycle(int n) {
		int cycle = 0;
		int newN = n;
		do {
			int teen = newN/10; 
			int one = newN%10;
			int plused = teen+one;
			newN = (one*10)+(plused%10);
			cycle++;
		} while (newN != n);
		
		
		return cycle;
	}
}
