package baekjoon.bronzesilver;
import java.util.*;
import java.io.*;

public class B1003ver02 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	// 0~40동안 0의 출력 횟수를 담을 벡터
	static Vector<Integer> Fibo0 = new Vector<>(41);
	// 0~40동안 1의 출력 횟수를 담을 벡터
	static Vector<Integer> Fibo1 = new Vector<>(41);
	
	static public void main(String[] args) throws Exception {
		makeVector();
		int n = Integer.parseInt(in.readLine());
		for (int i = 0; i < n; i++) {
			int index = Integer.parseInt(in.readLine());
			System.out.println(Fibo0.get(index)+" "+Fibo1.get(index));
		}
		
	}
	
	static void makeVector() {
		Fibo0.add(1);
		Fibo0.add(0);
		Fibo1.add(0);
		Fibo1.add(1);
		int[] index = new int[2];
		index[0] = 0;
		index[1] = 1;
		
		for (int i = 2; i <= 40; i++) {
			Fibo0.add(Fibo0.get(index[0])+Fibo0.get(index[1]));
			Fibo1.add(Fibo1.get(index[0])+Fibo1.get(index[1]));
			index[0]++;
			index[1]++;
		}
		
	}
}
