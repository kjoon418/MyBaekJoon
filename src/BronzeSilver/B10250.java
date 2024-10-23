package BronzeSilver;
import java.io.*;

public class B10250 {
	static int testCount;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		testCount = Integer.parseInt(in.readLine());
		for (int i = 0; i < testCount; i++) {
			int height, width, custNum, roomHeight, roomWidth, roomNum;
			String[] str = in.readLine().split(" ");
			height = Integer.parseInt(str[0]);
			width = Integer.parseInt(str[1]);
			custNum = Integer.parseInt(str[2]);
			
			// 손님이 묵을 방의 번호 계산
			if (custNum % height == 0) {
				roomNum = ((height)*100)+(custNum/height);
			}
			else {
				roomNum = ((custNum % height)*100+(custNum/height)+1);
			}

			
			// 방 번호 출력
			System.out.println(roomNum);
		}
	}

}
