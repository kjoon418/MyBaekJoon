package baekjoon.bronzesilver;
import java.util.*;

public class M2609 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		var sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		
		cal.GCD(n, m);
		cal.LCM(n, m);
	}

}

class cal {
	static void GCD(int n, int m) { // 최대공약수, Greatest common divisor
		int minNum = m;
		if (n < m) minNum = n;
		int gcd = 1;
		
		
		for (int i = minNum; i > 1; i--) {
			if ((n%(double)i == 0) && (m%(double)i == 0)) {
				gcd = i;
				break;
			}
		}
		System.out.println(gcd);
	}
	static void LCM(int n, int m) { // 최소공배수, Least common multiple
		int lcm;
		int maxNum = m;
		if (n > m) maxNum = n;
		int i = maxNum;
		
		while(true) {
			if (i%(double)n == 0 && i%(double)m == 0) {
				lcm = i;
				break;
			}
			i++;
		}
		System.out.println(lcm);
	}
}
