package BronzeSilver;
import java.util.*;

public class B4673 {

	public static void main(String[] args) {
		var v = nonSelf();
		boolean itSelf;
		for (int i = 1; i <= 10000; i++) {
			itSelf = true;
			for (var n : v) {
				if(n == i) {
					itSelf = false;
					break;
				}
			}
			if (itSelf) System.out.println(i);
		}
	}

	static Vector<Integer> nonSelf() {
		var v = new Vector<Integer>();
		for (int i = 1; i <= 10000; i++) {
			String s = Integer.toString(i);
			int sum = i;
			for (int j = 0; j < s.length(); j++) {
				sum += Character.getNumericValue(s.charAt(j));
			}
			v.add(sum);
		}
		
		return v;
	}
}

