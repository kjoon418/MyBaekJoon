package baekjoon.bronzesilver;

import java.io.*;

public class B1012 {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    private static int width;
    private static int height;
    private static int[][] lands;


    public static void main(String[] args) throws IOException {

        // 입력을 받음
        int testCase = Integer.parseInt(in.readLine());

        // 로직 시작
        for (int i = 0; i < testCase; i++) {
            // 초기화
            String[] string = in.readLine().split(" ");
            width = Integer.parseInt(string[0]);
            height = Integer.parseInt(string[1]);
            int cabbages = Integer.parseInt(string[2]);
            lands = new int[width][height];

            for (int j = 0; j < cabbages; j++) {
                String[] spots = in.readLine().split(" ");
                int x = Integer.parseInt(spots[0]);
                int y = Integer.parseInt(spots[1]);

                // 배추를 심음
                plant(x, y);
            }

            // 로직 시작
            out.write(calculate()+System.lineSeparator());
        }
        out.close();

    }

    // 배열의 특정 위치에 배추를 심는 메서드
    private static void plant(int x, int y) {
        lands[x][y] = 1;
    }

    // 로직을 수행하고 필요한 벌레의 수를 반환하는 메서드
    private static int calculate() {
        int count = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (lands[x][y] == 1) {
                    expand(x, y);
                    count++;
                }
            }
        }

        return count;
    }

    // 상하좌우의 배추를 찾아 그루핑하는 메서드
    private static void expand(int x, int y) {
        // 만약 인덱스를 초과했다면 메서드를 바로 종료함
        if (x >= width || y >= height || x < 0 || y < 0) {
            return;
        }

        // 현재 인덱스의 값이 1이라면 상하좌우에 메서드를 다시 호출함
        if (lands[x][y] == 1) {
            lands[x][y] = 0;
            expand(x + 1, y); // 상
            expand(x - 1, y); // 하
            expand(x, y - 1); // 좌
            expand(x, y + 1); // 우
        }
    }
}
