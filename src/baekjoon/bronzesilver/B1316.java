package baekjoon.bronzesilver;
import java.util.*;
import java.io.*;

public class B1316 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static Vector<Character> v = new Vector<Character>();
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int n = Integer.parseInt(in.readLine());
		int count = 0;
		for (int i = 0; i < n; i++) {
			boolean isGroup = true;
			String str = in.readLine();
			v.add(str.charAt(0));
			for (int j = 1; j < str.length(); j++) {
				if (str.charAt(j) != str.charAt(j-1)) {
					if (v.contains(str.charAt(j))) {
						isGroup = false;
						break;
					}
					v.add(str.charAt(j));
				}
				
			}
			v.clear();
			if (isGroup) count++;
		}
		System.out.println(count);
	}

}
