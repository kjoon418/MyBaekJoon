package baekjoon.BronzeSilver;
import java.io.*;

public class B8958 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int n = Integer.parseInt(in.readLine());
		int[] score = new int[n];
		for (int i = 0; i < n; i++) {
			score[i] = 0;
			int bonus = 0;
			String str = in.readLine();
			for (int j = 0; j < str.length(); j++) {
				if (str.charAt(j) == 'O') {
					bonus++;
					score[i] += bonus;
				}
				else {
					bonus = 0;
				}
			}
		}
		for (int i = 0; i < score.length; i++) {
			System.out.println(score[i]);
		}
	}

}
