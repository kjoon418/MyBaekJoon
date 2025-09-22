package baekjoon.bronzesilver;
import java.io.*;

public class B1547 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		boolean[] cups = {true, false, false};
		int count = Integer.parseInt(in.readLine());
		for (int i = 0; i < count; i++) {
			// 1, 2, 3중 2개를 입력받음
			String[] str = in.readLine().split(" ");
			int x = Integer.parseInt(str[0])-1;
			int y = Integer.parseInt(str[1])-1;
			// x인덱스와 y인덱스의 값을 바꿈
			boolean temp = cups[x];
			cups[x] = cups[y];
			cups[y] = temp;
 		}
		for (int i = 0; i < cups.length; i++) {
			if (cups[i]) {
				System.out.println(i+1);
			}
		}
	}

}
