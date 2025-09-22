package baekjoon.bronzesilver;

import java.io.*;

public class B1393_unsolved {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int trainX, trainY, stopX, stopY;
    private static int increaseX, increaseY;

    private static double minimum = Double.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(getLength(0, -10, 10, 10));
        System.out.println(getLength(10, -15, 10, 10));
        System.out.println("increaseX = " + increaseX);
        System.out.println("increaseY = " + increaseY);

        int[] result = findMinimum();

        out.write(result[0]+" "+result[1]);
        out.close();
    }

    private static int[] findMinimum() {
        int nowX = trainX;
        int nowY = trainY;

        if (increaseX == 0 && increaseY == 0) {
            return new int[] {nowX, nowY};
        }

        while(true) {
            double length = getLength(nowX, nowY, stopX, stopY);
            if (length > minimum && minimum != Double.MAX_VALUE) {
                return new int[] {nowX - increaseX, nowY - increaseY};
            }
            minimum = length;
            nowX += increaseX;
            nowY += increaseY;
        }
    }

    private static double getLength(int fromX, int fromY, int toX, int toY) {
        return Math.sqrt(Math.pow(Math.abs(fromX - toX), 2) + Math.pow(Math.abs(fromY - toY), 2));
    }

    private static void init() throws IOException {
        String[] stop = in.readLine().split(" ");
        stopX = Integer.parseInt(stop[0]);
        stopY = Integer.parseInt(stop[1]);

        String[] input = in.readLine().split(" ");
        trainX = Integer.parseInt(input[0]);
        trainY = Integer.parseInt(input[1]);
        int tempIncreaseX = Integer.parseInt(input[2]);
        int tempIncreaseY = Integer.parseInt(input[3]);
        int absTempX = Math.abs(tempIncreaseX);
        int absTempY = Math.abs(tempIncreaseY);


        int less = Math.min(absTempX, absTempY);

        // 만일 한 값이 0이라면 최대공약수를 구하지 않고 바로 대입함
        if (less == 0) {
            increaseX = tempIncreaseX;
            increaseY = tempIncreaseY;
        }
        // 최대공약수를 구해서, 실질적으로 증가하는 값 찾기
        for (int i = less; i > 0; i--) {
            if (absTempX % i == 0 && absTempY % i == 0) {
                increaseX = tempIncreaseX / i;
                increaseY = tempIncreaseY / i;
                break;
            }
        }
    }

}
