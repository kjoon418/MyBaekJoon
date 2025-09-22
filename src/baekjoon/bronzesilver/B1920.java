package baekjoon.bronzesilver;
import java.io.*;
import java.util.*;

public class B1920 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static HashSet<Integer> nSet = new HashSet<>();
	static int nCount = 0;

	public static void main(String[] args) throws Exception {
		nCount = Integer.parseInt(in.readLine());
		String[] nStr = in.readLine().split(" ");
		in.readLine();
		String[] xStr = in.readLine().split(" ");
		
		for (int i = 0; i < nStr.length; i++) {
			nSet.add(Integer.parseInt(nStr[i]));
		}
		for (int i = 0; i < xStr.length; i++) {
			if (nSet.contains(Integer.parseInt(xStr[i]))) {
				System.out.println(1);
			}
			else System.out.println(0);
		}
		
	}

}
