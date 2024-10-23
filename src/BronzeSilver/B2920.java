package BronzeSilver;
import java.util.*;

public class B2920 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[] str = sc.nextLine().split(" ");
		boolean ascending = true;
		boolean decending = true;
		if(str[0].equals("1")) {
			decending = false;
			for (int i = 0; i < str.length-1; i++) {
				if (Integer.parseInt(str[i]) > Integer.parseInt(str[i+1])) {
					ascending = false;
					break;
				}
			}
		}
		else if(str[0].equals("8")) {
			ascending = false;
			for (int i = 0; i < str.length-1; i++) {
				if (Integer.parseInt(str[i]) < Integer.parseInt(str[i+1])) {
					decending = false;
					break;
				}
			}
		}
		else {
			ascending = false;
			decending = false;
		}
		
		if (ascending) {
			System.out.println("ascending");
		}
		else if (decending) {
			System.out.println("descending");
		}
		else System.out.println("mixed");
	}

}
