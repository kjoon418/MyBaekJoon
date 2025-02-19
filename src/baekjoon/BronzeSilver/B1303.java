package baekjoon.BronzeSilver;

import java.io.*;

public class B1303 {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static long countW = 0;
    private static long countB = 0;

    private static char[][] board;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        out.write(countW + " " + countB);
        out.close();

        // print();
    }

    // 초기화 담당
    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        int x = Integer.parseInt(input[0]);
        int y = Integer.parseInt(input[1]);

        board = new char[y][x];

        for (int i = 0; i < y; i++) {
            String line = in.readLine();
            for (int j = 0; j < x; j++) {
                board[i][j] = line.charAt(j);
            }
        }
    }

    // 로직 컨트롤러
    private static void controller() {
        // 배열을 순차적으로 탐색
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // 'W'를 만나면 W 팀의 위력을 계산
                if (board[i][j] == 'W') {
                    countW += (long) Math.pow(getAmount(j, i, 'W'), 2);
                    continue;
                }

                // 'B'를 만나면 B팀의 위력을 계산
                if (board[i][j] == 'B') {
                    countB += (long) Math.pow(getAmount(j, i, 'B'), 2);
                }
            }
        }
    }

    // 탐색 재귀 함수
    private static int getAmount(int x, int y, int team) {
        // 한번 탐색한 타일의 값을 'N' 으로 변경해야 함
        board[y][x] = 'N';

        int count = 1;

        // 인접한 타일이 같은 팀이면 추가로 탐색
        // 1. 위쪽 타일 검사
        if (y - 1 >= 0 && board[y - 1][x] == team) {
            count += getAmount(x, y - 1, team);
        }
        // 2. 아래쪽 타일 검사
        if (y + 1 < board.length && board[y + 1][x] == team) {
            count += getAmount(x, y + 1, team);
        }
        // 3. 왼쪽 타일 검사
        if (x - 1 >= 0 && board[y][x - 1] == team) {
            count += getAmount(x - 1, y, team);
        }
        // 4. 오른쪽 타일 검사
        if (x + 1 < board[x].length && board[y][x + 1] == team) {
            count += getAmount(x + 1, y, team);
        }

        return count;
    }

    // 테스트용 출력 메서드
    private static void print() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
