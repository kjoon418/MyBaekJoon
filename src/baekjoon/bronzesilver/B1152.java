package baekjoon.bronzesilver;
import java.util.*;

public class B1152 {

	public static void main(String[] args) {
		var sc = new Scanner(System.in);
		String str = sc.nextLine();
		if (str.equals(" ")) {
			System.out.println(0);
		}
		else {
			System.out.println(str.trim().split(" ").length);
		}
	}

}
