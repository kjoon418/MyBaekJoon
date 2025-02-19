package baekjoon.BronzeSilver;
import java.util.*;

public class B1065 {

	public static void main(String[] args) {
		var sc = new Scanner(System.in);
		System.out.println(han(sc.nextInt()));
	}

	static int han(int n){
		int count = 0;
		int preGap = 0;
		boolean isHan = true;
		
		for (int i = 1; i <= n; i++) {
			Vector<Integer> v = new Vector<>();
			String str = Integer.toString(i);
			for (int j = 0; j < str.length(); j++) {
				v.add(Character.getNumericValue(str.charAt(j)));
			}
			if (v.size() <= 2) {
				count++;
				continue;
			}
			for (int j = 0; j < v.size()-1; j++) {
				isHan = true;
				int gap = v.get(j) - v.get(j+1);
				if (j > 0) {
					if (gap != preGap) {
						isHan = false;
						continue;
					}
					preGap = gap;
				}
				else {
					preGap = gap;
				}
			}
			if (isHan) {
				count++;
			}
		}
		
		return count;
	}
}
