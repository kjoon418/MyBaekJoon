package BronzeSilver;
import java.io.*;

public class B1654_unsolved {

	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

	private static long originCount;
	private static long goalCount;
	private static long[] lines;
	private static long maxCutLength;
	private static long minCutLength = 0;

	public static void main(String[] args) throws IOException {

		// 입력받기
		String str = in.readLine();
		String[] split = str.split(" ");
		originCount = Integer.parseInt(split[0]);
		goalCount = Integer.parseInt(split[1]);
		lines = new long[(int)originCount];
		for (int i = 0; i < originCount; i++) {
			lines[i] = Integer.parseInt(in.readLine());
		}

		// 자를 수 있는 최대 길이를 초기화
		setMaxCutLength();

		// 이진 탐색으로 최적값을 찾아 출력함
		System.out.println(binarySearch());

	}

	// 길이 1부터, maxCutLength 길이까지 이진 탐색을 하는 메서드
	private static long binarySearch() {
		// 찾고 싶은 것은, 목표갯수를 충족하면서 최대한 길게 자르는 것
		// 목표갯수를 충족 못하면 더 작게 잘라야 함
		// 목표갯수를 충족하면 더 길게 잘라야 함
		// 목표갯수를 충족했으나, 더 길게 자르는 순간 목표갯수를 만족하지 못하면 반복문 종료

		// 1. 이진탐색을 시작해야 하므로 중간지점에서 시작함
		long length = maxCutLength / 2;

		// 2. 반복문 시작
		while(true) {
			// 3. 현재 위치가 최고점인지 계산함
			if (isBest(length)) return length;

			// 4. 최고점이 아니면서, 목표 갯수를 넘길 수 있으면 더 길게 잘라야 함
			if (isFulfilled(length)) {
				// 또한, 최소 길이를 이 길이로 한정지어야 함
				minCutLength = length;
				length = getNextLength(minCutLength, maxCutLength);

			} else { // 5. 최고점이 아니면서, 목표 갯수를 넘길 수 없으면 더 짧게 잘라야 함
				// 또한, 최대 길이를 이 길이로 한정지어야 함
				maxCutLength = length;
				length = getNextLength(minCutLength, maxCutLength);
			}

		}


	}

	// start와 end의 중간 값을 반환함
	private static long getNextLength(long start, long end) {
		return (start + end) / 2;
	}

	// 현재 위치가 최고점인지 계산하는 메서드
	private static boolean isBest(long length) {
		System.out.println(length+"에서 연산 시작");
		// 1. 현재 위치가 목표 갯수를 넘기는지 확인
		if (!isFulfilled(length)) return false;

		// 2. 더 길게 잘라도 목표 갯수를 넘기는지 확인
		if (isFulfilled(length + 1)) return false;

		// 둘 다 만족하면 현재 위치가 최고점
		return true;
	}


	// 해당 길이로 잘라서 목표 갯수를 넘기는지 확인하는 메서드
	private static boolean isFulfilled(long length) {
		int sum = 0;
		for (long line : lines) {
			sum += line / length;
		}
		return sum >= goalCount;
	}

	// 자를 수 있는 최대 길이를 초기화
	private static void setMaxCutLength() {
		long max = Integer.MIN_VALUE;
		for (long length : lines) {
			if (length > max) {
				max = length;
			}
		}
		maxCutLength = max;
	}


}
