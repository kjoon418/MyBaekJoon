package baekjoon.bronzesilver;
import java.io.*;
import java.util.*;

public class B1181 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int n = Integer.parseInt(in.readLine());
		Vector<String> v = new Vector<>();
		HashSet<String> set = new HashSet<String>();
		for (int i = 0; i < n; i++) {
			set.add(in.readLine());
		}
		v.addAll(set);
		Collections.sort(v, new Comparator<String>() {
			public int compare(String s1, String s2) {
				if (s1.length()==s2.length()) return s1.compareTo(s2);
				if (s1.length() < s2.length()) return -1;
				else return 1;
			}
		});
		
		for (var str : v) {
			System.out.println(str);
		}
	}

}
