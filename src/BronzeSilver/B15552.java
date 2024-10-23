package BronzeSilver;
import java.io.*;

public class B15552 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
	

	public static void main(String[] args) throws Exception {
		int count = Integer.parseInt(in.readLine());
		for (int i = 0; i < count; i++) {
			String[] str = in.readLine().split(" ");
			int a = Integer.parseInt(str[0]);
			int b = Integer.parseInt(str[1]);
			out.write(Integer.toString(a+b)+System.lineSeparator());
		}
		out.flush();
	}

}
