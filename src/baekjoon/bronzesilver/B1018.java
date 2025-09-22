package baekjoon.bronzesilver;
import java.util.*;

public class B1018 {
	static Scanner sc = new Scanner(System.in);
	static Vector<String> table = new Vector<String>();
	static int min = 999;
	
	public static void main(String[] args) {
		// 전체 테이블과, 높이와 넓이를 받아옴
		int height = sc.nextInt();
		int width = sc.nextInt();
		sc.nextLine();
		for (int i = 0; i < height; i++) {
			table.add(sc.nextLine());
		}
		
		// 가능한 가로 반복, 세로 반복 횟수를 구함
		int wAble = width-7;
		int hAble = height-7;
		
		// 전체 테이블에서 8x8 테이블을 쪼갤 위치를 지정함
		for (int i = 0; i < hAble; i++) { // 쪼갤 범위를아래로 이동
			for (int j = 0; j < wAble; j++) { // 쪼갤 범위를 오른쪽으로 이동
				// 테이블당 저장해야 할 정보
				char first = table.get(i).charAt(j); // 맨 왼쪽 위의 색을 받아옴
				int wrong = 0; // 고쳐야 할 타일의 수를 저장함
				int reverse = 0; // 왼쪽위를 반대 색으로 할 때 고쳐야 할 타일의 수를 저장함
				// 해당 위치에서부터 8x8만큼의 영역을 읽음
				
				for (int h = i; h < i+8; h++) {
					for (int w = j; w < j+8; w++) {
						
						if (h%2 == 0) { // 0 2 4 6 ... 짝수번째 라인
							if (w%2 == 0) { // 짝수번째 인덱스
								if(table.get(h).charAt(w) != first) wrong++;
								else reverse++;
							}
							else { // 홀수번째 인덱스
								if(table.get(h).charAt(w) == first) wrong++;
								else reverse++;
							}
						}
						else { // 1 3 5 7 ... 홀수번째 라인
							if (w%2 == 0) { // 짝수번째 인덱스
								if(table.get(h).charAt(w) == first) wrong++;
								else reverse++;
							}
							else { // 홀수번째 인덱스
								if(table.get(h).charAt(w) != first) wrong++;
								else reverse++;
							}
						}
					}
				}
				// 고쳐야 할 개수와 최소개수를 비교해서 저장함
				if (wrong < reverse) {
					if (wrong < min) min = wrong;
				}
				else if (reverse < min) min = reverse;
			}
		}
		System.out.println(min);
	}
}
