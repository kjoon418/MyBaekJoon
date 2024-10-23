package BronzeSilver;
import java.util.*;
import java.io.*;

public class B1620 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
	// 포켓몬 이름을 통해 인덱스 번호를 낼 도감(dicNumber)을 채움
	static HashMap<String, Integer> dicNumber = new HashMap<String, Integer>();
	
	public static void main(String[] args) throws Exception {
		// 도감을 채울 횟수, 질문 횟수를 받음
		String[] str = in.readLine().split(" ");
		int putCount = Integer.parseInt(str[0]);
		int askCount = Integer.parseInt(str[1]);
		
		// 도감을 채울만큼의 길이를 지닌 배열 생성
		// 인덱스 번호를 통해 포켓몬 이름을 반환할 배열
		String[] dicName = new String[putCount];
		
		for (int i = 0; i < putCount; i++) {
			String name = in.readLine();
			dicName[i] = name;
			dicNumber.put(name, i+1);
		}
		
		// 질문에 대답함
		for (int i = 0; i < askCount; i++) {
			String ask = in.readLine();
			boolean isInt = true;
			for (int j = 0; j < ask.length(); j++) {
				if (!Character.isDigit(ask.charAt(j))){
					isInt = false;
					break;
				}
			}
			if (isInt) {
				int index = Integer.parseInt(ask);
				out.write(dicName[index-1]+System.lineSeparator());
			}
			else {
				out.write(Integer.toString(dicNumber.get(ask))+System.lineSeparator());
			}
		}
		out.flush();
	}

}
