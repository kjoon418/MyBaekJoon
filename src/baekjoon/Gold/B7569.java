package baekjoon.Gold;

import java.io.*;
import java.util.*;

public class B7569 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[][][] tomatoes;
    private static int[][][] testTomatoes;

    private static int unRipedTomatoCounter = 0;
    private static final Set<TomatoPoint> ripedTomatoes = new HashSet<>();

    private static int width;
    private static int height;
    private static int boxAmount;

    public static void main(String[] args) throws IOException {
        init();

        out.write(controller()+"");

        out.close();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        width = Integer.parseInt(input[0]);
        height = Integer.parseInt(input[1]);
        boxAmount = Integer.parseInt(input[2]);

        tomatoes = new int[boxAmount][height][width];
        testTomatoes = new int[boxAmount][height][width];

        for (int z = 0; z < boxAmount; z++) {
            for (int y = 0; y < height; y++) {
                String[] line = in.readLine().split(" ");
                for (int x = 0; x < width; x++) {
                    int value = Integer.parseInt(line[x]);
                    tomatoes[z][y][x] = value;
                    testTomatoes[z][y][x] = value;

                    if (value == 1) {
                        ripedTomatoes.add(new TomatoPoint(x, y, z));
                        continue;
                    }
                    if (value == 0) {
                        unRipedTomatoCounter++;
                    }
                }
            }
        }
    }

    private static int controller() {
        if (!isCanRipe()) {
            return -1;
        }

        int date = 0;

        while (unRipedTomatoCounter > 0) {
            List<TomatoPoint> copyList = new ArrayList<>(ripedTomatoes);

            for (TomatoPoint tomatoPoint : copyList) {
                ripeAndAddList(tomatoes, tomatoPoint.x, tomatoPoint.y, tomatoPoint.z);
                ripedTomatoes.remove(tomatoPoint);
            }

            date++;
        }

        return date;
    }

    /**
     * 모든 토마토를 익힐 수 있는지 확인하는 메서드
     */
    private static boolean isCanRipe() {
        for (TomatoPoint tomatoPoint : ripedTomatoes) {
            spreadRipe(testTomatoes, tomatoPoint.x, tomatoPoint.y, tomatoPoint.z);
        }

        // 안익은 토마토가 하나라도 있는지 확인함
        for (int z = 0; z < boxAmount; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (testTomatoes[z][y][x] == 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 인접한 토마토를 재귀적으로 모두 익히는 메서드
     */
    private static void spreadRipe(int[][][] tomatoes, int x, int y, int z) {
        // case 1: 왼쪽 토마토
        if (x > 0) {
            if (tomatoes[z][y][x - 1] == 0) {
                tomatoes[z][y][x - 1] = 1;
                spreadRipe(tomatoes, x - 1, y, z);
            }
        }

        // csae 2: 오른쪽 토마토
        if (x + 1 < width) {
            if (tomatoes[z][y][x + 1] == 0) {
                tomatoes[z][y][x + 1] = 1;
                spreadRipe(tomatoes, x + 1, y, z);
            }
        }

        // case 3: 위쪽 토마토
        if (y > 0) {
            if (tomatoes[z][y - 1][x] == 0) {
                tomatoes[z][y - 1][x] = 1;
                spreadRipe(tomatoes, x, y - 1, z);
            }
        }

        // case 4: 아래쪽 토마토
        if (y + 1 < height) {
            if (tomatoes[z][y + 1][x] == 0) {
                tomatoes[z][y + 1][x] = 1;
                spreadRipe(tomatoes, x, y + 1, z);
            }
        }

        // case 5: 이전 박스의 토마토
        if (z > 0) {
            if (tomatoes[z-1][y][x] == 0) {
                tomatoes[z-1][y][x] = 1;
                spreadRipe(tomatoes, x, y, z-1);
            }
        }

        // case 6: 다음 박스의 토마토
        if (z + 1 < boxAmount) {
            if (tomatoes[z+1][y][x] == 0) {
                tomatoes[z+1][y][x] = 1;
                spreadRipe(tomatoes, x, y, z+1);
            }
        }
    }

    /**
     * 인접한 토마토를 익히는 메서드
     * 안익은 토마토를 없앨 때 마다 unRipedTomatoCounter를 1 감소시킴
     */
    private static void ripeAndAddList(int[][][] tomatoes, int x, int y, int z) {
        // case 1: 왼쪽 토마토
        if (x > 0) {
            if (tomatoes[z][y][x - 1] == 0) {
                tomatoes[z][y][x - 1] = 1;
                ripedTomatoes.add(new TomatoPoint(x-1, y, z));
                unRipedTomatoCounter--;
            }
        }

        // csae 2: 오른쪽 토마토
        if (x + 1 < width) {
            if (tomatoes[z][y][x + 1] == 0) {
                tomatoes[z][y][x + 1] = 1;
                ripedTomatoes.add(new TomatoPoint(x+1, y, z));
                unRipedTomatoCounter--;
            }
        }

        // case 3: 위쪽 토마토
        if (y > 0) {
            if (tomatoes[z][y - 1][x] == 0) {
                tomatoes[z][y - 1][x] = 1;
                ripedTomatoes.add(new TomatoPoint(x, y-1, z));
                unRipedTomatoCounter--;
            }
        }

        // case 4: 아래쪽 토마토
        if (y + 1 < height) {
            if (tomatoes[z][y + 1][x] == 0) {
                tomatoes[z][y + 1][x] = 1;
                ripedTomatoes.add(new TomatoPoint(x, y+1, z));
                unRipedTomatoCounter--;
            }
        }

        // case 5: 이전 박스의 토마토
        if (z > 0) {
            if (tomatoes[z-1][y][x] == 0) {
                tomatoes[z-1][y][x] = 1;
                ripedTomatoes.add(new TomatoPoint(x, y, z-1));
                unRipedTomatoCounter--;
            }
        }

        // case 6: 다음 박스의 토마토
        if (z + 1 < boxAmount) {
            if (tomatoes[z+1][y][x] == 0) {
                tomatoes[z+1][y][x] = 1;
                ripedTomatoes.add(new TomatoPoint(x, y, z+1));
                unRipedTomatoCounter--;
            }
        }
    }

    private static class TomatoPoint {
        int x, y, z;
        public TomatoPoint(int x, int y, int z) {
            this.x = x; this.y = y; this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TomatoPoint that = (TomatoPoint) o;
            return x == that.x && y == that.y && z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
