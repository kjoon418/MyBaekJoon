package baekjoon.bronzesilver;
import java.util.*;

public class B10809 {
	// ascii 97 ~ 122

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		var sc = new Scanner(System.in);
		String str = sc.nextLine();
		HashMap<Character, Integer> map = new HashMap<>();
		for (int i = 97; i <= 122; i++) {
			map.put((char)i, -1);
		}
		
		
		for (int i = 0; i < str.length(); i++) {
			map.put(str.charAt(i), str.indexOf(str.charAt(i)));
		}
		var keys = new Vector<Character>();
		for (var key : map.keySet()) {
			keys.add(key);
		}
		Collections.sort(keys);
		
		for (char c : keys) {
			System.out.print(map.get(c)+" ");
		}
	}

}
