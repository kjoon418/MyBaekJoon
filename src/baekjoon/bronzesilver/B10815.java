package baekjoon.bronzesilver;
import java.io.*;
import java.util.*;

public class B10815 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
	static HashSet<Integer> player = new HashSet<Integer>();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		in.readLine();
		String[] pStr = in.readLine().split(" ");
		for (int i = 0; i < pStr.length; i++) {
			player.add(Integer.parseInt(pStr[i]));
		}
		in.readLine();
		String[] cStr = in.readLine().split(" ");
		for (int i = 0; i < cStr.length; i++) {
			if (player.contains(Integer.parseInt(cStr[i])))
				out.write(1+" ");
			else out.write(0+" ");
		}
		out.flush();
	}

}
