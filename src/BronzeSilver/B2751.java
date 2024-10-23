package BronzeSilver;
import java.io.*;
import java.util.*;

public class B2751 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static HashSet<Integer> set = new HashSet<Integer>();
	static Vector<Integer> vec = new Vector<Integer>();
	static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int count = Integer.parseInt(in.readLine());
		for (int i = 0; i < count; i++) {
			set.add(Integer.parseInt(in.readLine()));
		}
		vec.addAll(set);
		Collections.sort(vec);
		for (var v : vec) {
			out.write(Integer.toString(v)+System.lineSeparator());
		}
		out.flush();
	}

}
