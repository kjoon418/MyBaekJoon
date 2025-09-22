package baekjoon.bronzesilver;
import java.io.*;

public class B1085 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int minLength = Integer.MAX_VALUE;
		String[] str = in.readLine().split(" ");
		int x = Integer.parseInt(str[0]);
		int y = Integer.parseInt(str[1]);
		int w = Integer.parseInt(str[2]);
		int h = Integer.parseInt(str[3]);
		
		int toRight = w - x;
		int toLeft = x;
		int toTop = h - y;
		int toBottom = y;
		
		if (toRight < minLength) minLength = toRight;
		if (toLeft < minLength) minLength = toLeft;
		if (toTop < minLength) minLength = toTop;
		if (toBottom < minLength) minLength = toBottom;
		System.out.println(minLength);
	}

}
