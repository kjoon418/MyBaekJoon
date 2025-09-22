package baekjoon.bronzesilver;
import java.util.Scanner;
import java.util.Vector;

public class B1259 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		var sc = new Scanner(System.in);
		var v = new Vector<String>();
		while(true) {
			String s = sc.nextLine();
			if (s.equals("0")) break;
			v.add(s);
		}
		
		for (var s : v) {
			boolean pal = true;
			for (int i = 0; i < s.length()/2; i++) {
				if (s.charAt(i) != s.charAt(s.length()-1-i)){
					pal = false;
					break;
				}
			}
			if (pal) {
				System.out.println("yes");
			}
			else System.out.println("no");
		}
	}

}
