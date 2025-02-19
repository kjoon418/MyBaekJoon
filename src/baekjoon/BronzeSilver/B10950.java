package baekjoon.BronzeSilver;
import java.util.*;

public class B10950 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		var v = new Vector<Integer>();
		int max = sc.nextInt();
		sc.nextLine();
		for (int i = 0; i < max; i++) {
			String[] str = sc.nextLine().split(" ");
			v.add(Integer.parseInt(str[0])+Integer.parseInt(str[1]));
		}
		for (var sum : v) {
			System.out.println(sum);
		}
	}

}
