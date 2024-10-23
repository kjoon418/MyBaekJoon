package BronzeSilver;
import java.util.*;

public class B2439 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		var sc = new Scanner(System.in);
		int n = sc.nextInt();
		for (int i = 1; i <= n; i++) {
			// 공백 추가
			for (int j = 0; j < n-i; j++) {
				System.out.print(" ");
			}
			// 별 추가
			for (int j = 0; j < i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}

}
