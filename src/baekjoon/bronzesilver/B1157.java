package baekjoon.bronzesilver;
import java.io.*;
import java.util.*;

public class B1157 {
	static HashMap<Character, Integer> map = new HashMap<>();
	static Vector<Integer> vec = new Vector<>();
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws Exception {
		// 영문자열 받아오기
		String str = in.readLine().trim();
		// 최소값을 저장할 변수
		int max = Integer.MIN_VALUE;
		// 최소값이 어떤 문자인지 저장할 변수
		char maxChar = '?';
		
		// 문자열에서 한 문자씩 Map에 추가함
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ') continue;
			char c = Character.toUpperCase(str.charAt(i));
			addMap(c);
		}
		
		// map의 원소들을 하나씩 비교해서 최소값에 해당하는 문자 찾음
		for (var key : map.keySet()) {
			vec.add(map.get(key));
			if (max < map.get(key)) {
				max = map.get(key);
				maxChar = key;
			}
		}
		
		// 벡터를 찾아서 max와 같은 값이 2개 이상 있는지 검사함
		int dup = 0;
		boolean isDup = false;
		for (int i = 0; i < vec.size(); i++) {
			if (max == vec.get(i)) {
				dup++;
				if (dup > 1) {
					isDup = true;
					break;
				}
			}
		}
		// 중복되지 않으면 문자 출력, 중복되면 ? 출력
		if (!isDup) {
			System.out.println(maxChar);
		}
		else {
			System.out.println('?');
		}
	}
	
	// map에 문자가 새로 들어오면 1, 이미 있으면 +1 해주는 메소드
	static void addMap(char c) {
		if (c == ' ') return;
		if (map.containsKey(c)) {
			map.put(c, map.get(c)+1);
		}
		else {
			map.put(c, 1);
		}
	}
}
