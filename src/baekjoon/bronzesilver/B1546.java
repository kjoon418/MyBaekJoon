package baekjoon.bronzesilver;
import java.io.*;
import java.util.*;

public class B1546 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static Vector<Integer> v = new Vector<Integer>();
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int count = Integer.parseInt(in.readLine());
		String[] str = in.readLine().split(" ");
		for (int i = 0; i < str.length; i++) {
			v.add(Integer.parseInt(str[i]));
		}
		Collections.sort(v, new Comparator<Integer>() {
			public int compare(Integer v1, Integer v2) {
				return v2 - v1;
			}
		});
		double max = v.get(0);
		double sum = 0;
		for (int i = 0; i < v.size(); i++) {
			sum += (v.get(i) / max) * 100;
		}
		System.out.println(sum/count);
	}

}
