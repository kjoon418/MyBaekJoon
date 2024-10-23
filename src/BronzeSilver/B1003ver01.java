package BronzeSilver;
import java.io.*;

public class B1003ver01 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int count0 = 0;
	static int count1 = 0;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		int testCount = Integer.parseInt(in.readLine());
		for (int i = 0; i < testCount; i++) {
			int n = Integer.parseInt(in.readLine());
			fibo(n);
			System.out.println(count0+" "+count1);
			count0 = 0;
			count1 = 0;
		}
		
		
		
	}
	public static void fibo(int n) {
		int add0 = count0;
		int add1 = count1;
		switch(n) {
		case 0:
			count0++;
			break;
		case 1:
			count1++;
			break;
		case 2:
			count0++;
			count1++;
			break;
		case 3:
			count0 += 1;
			count1 += 2;
			break;
		case 4:
			count0 += 2;
			count1 += 3;
			break;
		case 5:
			count0 += 3;
			count1 += 5;
			break;
		case 6:
			count0 += 5;
			count1 += 8;
			break;
		case 7:
			count0 += 8;
			count1 += 13;
			break;
		default:
			fibo(n-1);
			fibo(n-2);
		}
	}
}
