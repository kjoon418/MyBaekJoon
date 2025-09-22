package baekjoon.bronzesilver;
import java.util.*;

public class B2753 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		var sc = new Scanner(System.in);
		int year = sc.nextInt();
		if (year%400 == 0) {
			System.out.println(1);
		}
		else if (year%100 != 0 && year%4 == 0) {
			System.out.println(1);
		}
		else System.out.println(0);
	}

}
