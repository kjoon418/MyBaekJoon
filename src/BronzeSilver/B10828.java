package BronzeSilver;
import java.util.*;
import java.io.*;

public class B10828 {
	static Vector<Integer> v = new Vector<>();
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		int n = Integer.parseInt(in.readLine());
		
		for (int i = 0; i < n; i++) {
			String str = in.readLine();
			switch(str) {
			case "pop":
				if (v.isEmpty()) {
					System.out.println(-1);
				}
				else {
					System.out.println(v.lastElement());
					v.remove(v.size()-1);
				}
				break;
			case "size":
				System.out.println(v.size());
				break;
			case "empty":
				if (v.isEmpty()) System.out.println(1);
				else System.out.println(0);
				break;
			case "top":
				if (v.isEmpty()) System.out.println(-1);
				else System.out.println(v.lastElement());
				break;
			default:
				if (str.contains("push")) {
					int value = Integer.parseInt(str.split(" ")[1]);
					v.add(value);
				}
			}
		}
	}

}