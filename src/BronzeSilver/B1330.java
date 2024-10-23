package BronzeSilver;
import java.util.Scanner;

public class B1330 {

	public static void main(String[] args) {
		var sc = new Scanner(System.in);
		String[] str = sc.nextLine().split(" ");
		int a = Integer.parseInt(str[0]);
		int b = Integer.parseInt(str[1]);
		
		if (a > b) {
			System.out.println(">");
		}
		else if (a < b) {
			System.out.println("<");
		}
		else System.out.println("==");
		
	}

}
