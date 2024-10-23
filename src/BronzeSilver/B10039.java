package BronzeSilver;
import java.util.*;

public class B10039 {
	static final int MEMBER = 5;
	public static void main(String[] args) {
		var sc = new Scanner(System.in);
		int sum = 0;
		for (int i = 0; i < MEMBER; i++) {
			int score = sc.nextInt();
			if (score < 40) score = 40;
			sum += score;
		}
		
		System.out.println(sum/MEMBER);
	}

}
