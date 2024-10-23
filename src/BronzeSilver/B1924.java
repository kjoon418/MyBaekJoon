package BronzeSilver;
import java.io.*;

public class B1924 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	static final String[] days = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] str = in.readLine().split(" ");
		int month = Integer.parseInt(str[0]);
		int date = Integer.parseInt(str[1]);
		int pastDays = date;
		for (int i = 0; i < month-1; i++) {
			pastDays += daysOfMonth[i];
		}
		int dayCount = 0;
		String day = null;
		for (int i = 0; i < pastDays; i++) {
			day = days[dayCount];
			
			dayCount++;
			if (dayCount >= days.length) dayCount = 0;
		}
		System.out.println(day);
	}

}
