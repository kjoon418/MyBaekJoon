package Gold;

import java.io.*;

public class B17144 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[][] board;
    private static int[][] memoryBoard;
    private static final AirPurifier ap = new AirPurifier();

    private static int width;
    private static int height;
    private static int time;

    private static int result;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void testPrint() {
        System.out.println("==========================");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value = board[y][x];
                if (value < 10 && value >= 0) {
                    System.out.print(" "+value+" ");
                } else {
                    System.out.print(value+" ");
                }
            }
            System.out.println();
        }
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        height = Integer.parseInt(input[0]);
        width = Integer.parseInt(input[1]);
        time = Integer.parseInt(input[2]);
        board = new int[height][width];
        memoryBoard = new int[height][width];

        int purifierTop = -999;
        int purifierBottom = -999;
        for (int y = 0; y < height; y++) {
            String[] line = in.readLine().split(" ");
            for (int x = 0; x < width; x++) {
                int value = Integer.parseInt(line[x]);
                board[y][x] = value;

                if (value == -1) {
                    if (purifierTop == -999) {
                        purifierTop = y;
                    } else {
                        purifierBottom = y;
                    }
                }
            }
        }
        ap.topY = purifierTop;
        ap.bottomY = purifierBottom;
    }

    private static void controller() throws IOException {
        //System.out.println("start");
        //testPrint();
        for (int i = 0; i < time; i++) {
            spreadController();

            //System.out.println("beforePure");
            //testPrint();

            ap.topPure(board);
            ap.bottomPure(board);

            //System.out.println("afterPure");
            //testPrint();
        }

        result = getTotalDust();
    }

    private static void printer() throws IOException {
        out.write(result+"");

        out.close();
    }

    /**
     * 미세먼지의 전체 확장을 책임지는 메서드
     */
    private static void spreadController() {
        // memoryBoard에 먼지의 변화량을 입력함
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 값이 0 이상일 때만 확장되게 해서, 공기청정기를 제외함
                if (board[y][x] > 0) {
                    spread(x, y);
                }
            }
        }

        // memoryBoard의 데이터를 board에 반영함
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[y][x] += memoryBoard[y][x];
            }
        }

        // momoryBoard를 초기화함
        memoryBoard = new int[height][width];
    }

    /**
     * 미세먼지가 확산되는 메서드
     * 해당 타일의 미세먼지 변화량을 memoryBoard에 저장함
     */
    private static void spread(int x, int y) {
        int spreadCounter = 0;
        int shareAmount = board[y][x] / 5;

        // 왼쪽
        if (x > 0 && board[y][x-1] != -1) {
            spreadCounter++;
            memoryBoard[y][x-1] += shareAmount;
        }
        // 오른쪽
        if (x+1 < width) {
            spreadCounter++;
            memoryBoard[y][x+1] += shareAmount;
        }
        // 위쪽
        if (y > 0 && board[y-1][x] != -1) {
            spreadCounter++;
            memoryBoard[y-1][x] += shareAmount;
        }
        // 아래쪽
        if (y+1 < height && board[y+1][x] != -1) {
            spreadCounter++;
            memoryBoard[y+1][x] += shareAmount;
        }

        // 나눠준 양 만큼 현재 위치의 먼지를 감소시킴
        memoryBoard[y][x] -= spreadCounter * shareAmount;
    }

    /**
     * 남아있는 미세먼지의 총 갯수를 세는 메서드
     */
    private static int getTotalDust() {
        int sum = 0;

        // 공기청정기를 제외한 위치의 합을 구함
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board[y][x] > 0) {
                    sum += board[y][x];
                }
            }
        }

        return sum;
    }

    private static class AirPurifier {
        int topY, bottomY;

        /**
         * 위쪽 공기 순환 메서드
         * 공기청정기 쪽으로 반시계로 한칸씩 이동시킴
         */
        public void topPure(int[][] board) {
            // 1. 공치정정기 위의 공기를 당김
            for (int y = topY-2; y >= 0; y--) {
                board[y+1][0] = board[y][0];
            }

            // 2. 위 벽의 공기를 한 칸씩 왼쪽으로 당김
            for (int x = 1; x < width; x++) {
                board[0][x-1] = board[0][x];
            }

            // 3. 오른쪽 벽의 공기를 한 칸씩 위쪽으로 당김
            for (int y = 1; y <= topY; y++) {
                board[y-1][width-1] = board[y][width-1];
            }

            // 4. 공기청정기 오른쪽 공기를 한 칸씩 오른쪽으로 밈
            for (int x = width-1; x > 1; x--) {
                board[topY][x] = board[topY][x-1];
            }
            // 5. 공기청정기 오른쪽 칸을 비움
            board[topY][1] = 0;
        }

        /**
         * 아래쪽 공기 순환 메서드
         * 공기청정기 쪽으로 시계로 한칸씩 이동시킴
         */
        public void bottomPure(int[][] board) {
            // 1. 공치정정기 아래의 공기를 당김
            for (int y = bottomY+2; y < height; y++) {
                board[y-1][0] = board[y][0];
            }

            // 2. 아래 벽의 공기를 한 칸씩 왼쪽으로 당김
            for (int x = 1; x < width; x++) {
                board[height-1][x-1] = board[height-1][x];
            }

            // 3. 오른쪽 벽의 공기를 한 칸씩 아래로 당김
            for (int y = height-1; y > bottomY; y--) {
                board[y][width-1] = board[y-1][width-1];
            }

            // 4. 공기청정기 오른쪽 공기를 한 칸씩 오른쪽으로 밈
            for (int x = width-1; x > 1; x--) {
                board[bottomY][x] = board[bottomY][x-1];
            }
            // 5. 공기청정기 오른쪽 칸을 비움
            board[bottomY][1] = 0;
        }
    }


}
