package BronzeSilver;
import java.util.Scanner;

public class B8393 {

	public static void main(String[] args) {
		var sc = new Scanner(System.in);
		int i = sc.nextInt();
		int sum = 0;
		for (int j = 0; j <= i; j++) {
			sum += j;
		}
		System.out.println(sum);
	}

}
