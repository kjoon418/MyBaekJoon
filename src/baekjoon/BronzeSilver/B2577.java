package baekjoon.BronzeSilver;
import java.util.*;

public class B2577 {
	static int[] ary = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		int c = sc.nextInt();
		String mul = Integer.toString(a*b*c);
		for (int i = 0; i < mul.length(); i++) {
			switch(mul.charAt(i)) {
			case '0':
				ary[0]++;
				break;
			case '1':
				ary[1]++;
				break;
			case '2':
				ary[2]++;
				break;
			case '3':
				ary[3]++;
				break;
			case '4':
				ary[4]++;
				break;
			case '5':
				ary[5]++;
				break;
			case '6':
				ary[6]++;
				break;
			case '7':
				ary[7]++;
				break;
			case '8':
				ary[8]++;
				break;
			case '9':
				ary[9]++;
				break;
			}
		}
		for (int i = 0; i < ary.length; i++) {
			System.out.println(ary[i]);
		}
	}

}
