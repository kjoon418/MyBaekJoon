package baekjoon.bronzesilver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class B1942 {

    // 3의 배수 카운트
    private static int count = 0;
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {

        // 1. 입력을 세번 받기 위한 List를 만듦
        List<String> input = new ArrayList<String>();
        // 2. 입력을 받아 시작시간과 끝시간을 구함
        for (int i = 0; i < 3; i++){
            input.add(in.readLine());
        }

        // 3. 총 3번 반복하며 계산을 시작함
        for (int i = 0; i < input.size(); i++) {
            // 4. 시작 시간과 끝나는 시간을 분리함
            String[] strTimes = input.get(i).split(" ");
            String strStartTime = strTimes[0];
            String strEndTime = strTimes[1];

            // 5. 분리한 각 시간을 시계 정수로 변환함
            int startTime = getTimeInt(strStartTime);
            int endTime = getTimeInt(strEndTime);

            // 6. 반복문을 돌리며, 시작시간과 끝시간이 일치하는지 확인함
            while(startTime != endTime) {
                addCount(startTime);
                startTime = getNextTime(startTime);
            }
            addCount(endTime);

            out.write(count+System.lineSeparator());
            count = 0;
        }

        out.close();

    }

    // 시계 정수를 구하는 메소드
    private static int getTimeInt(String time) {
        String[] timeSplit = time.split(":");
        int intTime = 0;
        intTime += Integer.parseInt(timeSplit[0]) * 10000;
        intTime += Integer.parseInt(timeSplit[1]) * 100;
        intTime += Integer.parseInt(timeSplit[2]);

        return intTime;
    }

    // 다음 시간으로 넘기는 메소드
    private static int getNextTime(int time) {
        time++;
        int second = time % 100;
        int minute = ((time - second) % 10000) / 100;
        int hour = time / 10000;
        if (second >= 60) {
            second = 0;
            minute++;
        }
        if (minute >= 60) {
            minute = 0;
            hour++;
        }
        if (hour >= 24) {
            hour = 0;
        }
//        System.out.println("hour = " + hour + ", minute = " + minute + ", second = " + second);
        return ((hour * 10000) + (minute * 100) + second);
    }

    // 3의 배수라면 카운트를 증가시킬 메소드
    private static void addCount(int time) {
        if (time % 3 == 0) count++;
    }
}
