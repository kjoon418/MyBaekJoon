package baekjoon.Gold;

import java.io.*;
import java.util.*;

public class B7576 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[][] tomatoes;

    private static int width;
    private static int height;

    private static final Set<TomatoPoint> ripedTomatoes = new HashSet<>();

    private static int unRipedTomatoCounter = 0;

    public static void main(String[] args) throws IOException {
        init();

        out.write(controller()+System.lineSeparator());

        out.close();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        width = Integer.parseInt(input[0]);
        height = Integer.parseInt(input[1]);
        tomatoes = new int[height][width];

        for (int y = 0; y < height; y++) {
            String[] tomatoInput = in.readLine().split(" ");

            for (int x = 0; x < width; x++) {
                if (tomatoInput[x].equals("0")) {
                    unRipedTomatoCounter++;
                } else if (tomatoInput[x].equals("1")) {
                    ripedTomatoes.add(new TomatoPoint(x, y));
                }
                tomatoes[y][x] = Integer.parseInt(tomatoInput[x]);
            }
        }
    }

    private static int controller() {
        if (!isCanRiped()) {
            return -1;
        }

        int date = 0;
        while (unRipedTomatoCounter > 0) {
            ArrayList<TomatoPoint> copyList = new ArrayList<>(ripedTomatoes);

            for (TomatoPoint tomatoPoint : copyList) {
                ripeAndAddList(tomatoPoint.x, tomatoPoint.y);
                ripedTomatoes.remove(tomatoPoint);
            }

            date++;
        }

        return date;
    }

    /**
     * 모든 토마토가 익어있는지 검사하는 메서드
     * 안익은 토마토가 하나도 없으면 모두 익었다고 판단함
     */
    private static boolean everyRiped(int[][] tomatoes) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tomatoes[y][x] == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 절대 익지 못하는 안익은 토마토가 있는지 검사하는 메서드
     * 모든 토마토가 결국 익을 수 있다면 true를 반환함
     */
    private static boolean isCanRiped() {
        int[][] copyTomatoes = copyTomatoes(tomatoes);

        for (TomatoPoint ripedTomato : ripedTomatoes) {
            spreadRipe(copyTomatoes, ripedTomato.x, ripedTomato.y);
        }

        return everyRiped(copyTomatoes);
    }

    /**
     * 인접한 토마토를 재귀적으로 모두 익히는 메서드
     */
    private static void spreadRipe(int[][] tomatoes, int x, int y) {
        // case 1: 왼쪽 토마토
        if (x > 0) {
            if (tomatoes[y][x - 1] == 0) {
                tomatoes[y][x - 1] = 1;
                spreadRipe(tomatoes, x - 1, y);
            }
        }

        // csae 2: 오른쪽 토마토
        if (x + 1 < width) {
            if (tomatoes[y][x + 1] == 0) {
                tomatoes[y][x + 1] = 1;
                spreadRipe(tomatoes, x + 1, y);
            }
        }

        // case 3: 위쪽 토마토
        if (y > 0) {
            if (tomatoes[y - 1][x] == 0) {
                tomatoes[y - 1][x] = 1;
                spreadRipe(tomatoes, x, y - 1);
            }
        }

        // case 3: 아래쪽 토마토
        if (y + 1 < height) {
            if (tomatoes[y + 1][x] == 0) {
                tomatoes[y + 1][x] = 1;
                spreadRipe(tomatoes, x, y + 1);
            }
        }
    }

    /**
     * 인접한 토마토를 익히고, 리스트에 추가하는 메서드
     */
    private static void ripeAndAddList(int x, int y) {
        // case 1: 왼쪽 토마토
        if (x > 0) {
            if (tomatoes[y][x - 1] == 0) {
                tomatoes[y][x - 1] = 1;
                ripedTomatoes.add(new TomatoPoint(x - 1, y));
                unRipedTomatoCounter--;
            }
        }

        // csae 2: 오른쪽 토마토
        if (x + 1 < width) {
            if (tomatoes[y][x + 1] == 0) {
                tomatoes[y][x + 1] = 1;
                ripedTomatoes.add(new TomatoPoint(x + 1, y));
                unRipedTomatoCounter--;
            }
        }

        // case 3: 위쪽 토마토
        if (y > 0) {
            if (tomatoes[y - 1][x] == 0) {
                tomatoes[y - 1][x] = 1;
                ripedTomatoes.add(new TomatoPoint(x, y - 1));
                unRipedTomatoCounter--;
            }
        }

        // case 3: 아래쪽 토마토
        if (y + 1 < height) {
            if (tomatoes[y + 1][x] == 0) {
                tomatoes[y + 1][x] = 1;
                ripedTomatoes.add(new TomatoPoint(x, y + 1));
                unRipedTomatoCounter--;
            }
        }
    }

    /**
     * 인접한 토마토 중 익은 토마토가 있는지 검사하는 메서드
     */
    private static boolean isCloseWithRiped(int[][] tomatoes, int x, int y) {
        // 왼쪽 확인
        if (x > 0) {
            if (tomatoes[y][x - 1] == 1) {
                return true;
            }
        }

        // 오른쪽 확인
        if (x + 1 < width) {
            if (tomatoes[y][x + 1] == 1) {
                return true;
            }
        }

        // 위쪽 확인
        if (y > 0) {
            if (tomatoes[y - 1][x] == 1) {
                return true;
            }
        }

        // 아래쪽 확인
        if (y + 1 < height) {
            if (tomatoes[y + 1][x] == 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * 토마토 배열을 복사하는 메서드
     */
    private static int[][] copyTomatoes(int[][] tomatoes) {
        int[][] copyTomatoes = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                copyTomatoes[y][x] = tomatoes[y][x];
            }
        }

        return copyTomatoes;
    }

    static class TomatoPoint {
        int x, y;
        public TomatoPoint(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TomatoPoint that = (TomatoPoint) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
