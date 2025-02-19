package baekjoon.Gold;

import java.io.*;
import java.util.*;

public class B2638 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[][] board;
    private static int width;
    private static int height;

    private static Set<AirPoint> outAirs = new HashSet<>();
    private static Map<Point, CheesePoint> deleteCheeses = new HashMap<>();

    private static Set<AirPoint> removeAirSet = new HashSet<>();
    private static Set<AirPoint> addAirSet = new HashSet<>();

    private static int cheeseCounter;
    private static int time;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void testPrint() {
        System.out.println("========================");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board[y][x] >= 0) {
                    System.out.print(" " + board[y][x] + " ");
                    continue;
                }
                System.out.print(board[y][x] + " ");
            }
            System.out.println();
        }
    }

    private static void init() throws IOException {
        String[] size = in.readLine().split(" ");
        height = Integer.parseInt(size[0]);
        width = Integer.parseInt(size[1]);
        board = new int[height][width];

        for (int y = 0; y < height; y++) {
            String[] line = in.readLine().split(" ");
            for (int x = 0; x < width; x++) {
                int value = Integer.parseInt(line[x]);
                board[y][x] = value;
                if (value == 1) {
                    cheeseCounter++;
                }
            }
        }
    }

    private static void controller() throws IOException {
        time = 0;

        // 모눈종이의 맨 가장자리에는 치즈가 놓이지 않으므로, 바깥 공기 리스트에 등록시킴
        outAirs.add(new AirPoint(0, 0));

        while (cheeseCounter > 0) {
            for (AirPoint airPoint : outAirs) {
                spreadAir(airPoint);
            }

//            testPrint();

            for (AirPoint airPoint : removeAirSet) {
                outAirs.remove(airPoint);
            }
            for (AirPoint airPoint : addAirSet) {
                outAirs.add(airPoint);
            }
            addAirSet.clear();
            removeAirSet.clear();

            for (AirPoint airPoint : outAirs) {
                findCheese(airPoint);
            }

            Set<Map.Entry<Point, CheesePoint>> entries = deleteCheeses.entrySet();

            for (Map.Entry<Point, CheesePoint> entry : entries) {
                CheesePoint value = entry.getValue();

                if (value.hasToDelete()) {
                    board[value.y][value.x] = 0;
                    cheeseCounter--;
                }
            }

//            testPrint();

            deleteCheeses.clear();

            time++;
        }
    }

    private static void printer() throws IOException {
        out.write(time + System.lineSeparator());

        out.close();
    }

    /**
     * 내부 공기를 바깥 공기로 전염시킴
     */
    private static void spreadAir(AirPoint airPoint) {
        int surroundCounter = 0;
        int x = airPoint.x;
        int y = airPoint.y;
        board[y][x] = -1;

        // 왼쪽 공기 전염
        if (x > 0) {
            // 왼쪽 공기가 내부 공기였다면, 외부 공기로 전염시킴
            if (board[y][x-1] == 0) {
                spreadAir(new AirPoint(x-1, y));
                surroundCounter++;
            } else if (board[y][x-1] == -1) {
                // 이미 바깥 공기라면 surroundCounter를 증가시킴
                surroundCounter++;
            }
        } else {
            // 왼쪽이 배열 바깥이면 증가
            surroundCounter++;
        }

        // 오른쪽 공기 전염
        if (x+1 < width) {
            // 오른쪽 공기가 내부 공기였다면, 외부 공기로 전염시킴
            if (board[y][x+1] == 0) {
                spreadAir(new AirPoint(x+1, y));
                surroundCounter++;
            } else if (board[y][x+1] == -1) {
                // 이미 바깥 공기라면 surroundCounter를 증가시킴
                surroundCounter++;
            }
        } else {
            // 오른쪽이 배열 바깥이거나, 이미 바깥 공기라면 surroundCounter를 증가시킴
            surroundCounter++;
        }

        // 위쪽 공기 전염
        if (y > 0) {
            // 위쪽 공기가 내부 공기였다면, 외부 공기로 전염시킴
            if (board[y-1][x] == 0) {
                spreadAir(new AirPoint(x, y-1));
                surroundCounter++;
            } else if (board[y-1][x] == -1) {
                // 이미 바깥 공기라면 surroundCounter를 증가시킴
                surroundCounter++;
            }
        } else {
            // 위쪽이 배열 바깥이거나, 이미 바깥 공기라면 surroundCounter를 증가시킴
            surroundCounter++;
        }

        // 아래쪽 공기 전염
        if (y+1 < height) {
            // 위쪽 공기가 내부 공기였다면, 외부 공기로 전염시킴
            if (board[y+1][x] == 0) {
                spreadAir(new AirPoint(x, y+1));
                surroundCounter++;
            } else if (board[y+1][x] == -1) {
                // 이미 바깥 공기라면 surroundCounter를 증가시킴
                surroundCounter++;
            }
        } else {
            // 위쪽이 배열 바깥이거나, 이미 바깥 공기라면 surroundCounter를 증가시킴
            surroundCounter++;
        }

        // 사방이 바깥 공기로 둘러쌓여 있다면 더 이상 쓸모가 없으니 리스트에서 삭제시킴
        if (surroundCounter >= 4) {
            removeAirSet.add(airPoint);
        } else {
            addAirSet.add(airPoint);
        }
    }

    /**
     * 바깥 공기와 맞닿아있는 치즈를 계산함
     */
    private static void findCheese(AirPoint airPoint) {
        int x = airPoint.x;
        int y = airPoint.y;

        // 왼쪽에 치즈가 있는지 검사함
        if (x > 0) {
            if (board[y][x-1] == 1) {
               Point point = new Point(x-1, y);
               if (deleteCheeses.containsKey(point)) {
                   deleteCheeses.get(point).addStack();
               } else {
                   CheesePoint cp = new CheesePoint(point);
                   cp.addStack();
                   deleteCheeses.put(point, cp);
               }
            }
        }

        // 오른쪽에 치즈가 있는지 검사함
        if (x+1 < width) {
            if (board[y][x+1] == 1) {
                Point point = new Point(x+1, y);
                if (deleteCheeses.containsKey(point)) {
                    deleteCheeses.get(point).addStack();
                } else {
                    CheesePoint cp = new CheesePoint(point);
                    cp.addStack();
                    deleteCheeses.put(point, cp);
                }
            }
        }

        // 위쪽에 치즈가 있는지 검사함
        if (y > 0) {
            if (board[y-1][x] == 1) {
                Point point = new Point(x, y-1);
                if (deleteCheeses.containsKey(point)) {
                    deleteCheeses.get(point).addStack();
                } else {
                    CheesePoint cp = new CheesePoint(point);
                    cp.addStack();
                    deleteCheeses.put(point, cp);
                }
            }
        }

        // 아래쪽에 치즈가 있는지 검사함
        if (y+1 < height) {
            if (board[y+1][x] == 1) {
                Point point = new Point(x, y+1);
                if (deleteCheeses.containsKey(point)) {
                    deleteCheeses.get(point).addStack();
                } else {
                    CheesePoint cp = new CheesePoint(point);
                    cp.addStack();
                    deleteCheeses.put(point, cp);
                }
            }
        }
    }

    private static class AirPoint {
        int x, y;
        public AirPoint(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AirPoint point = (AirPoint) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class CheesePoint {
        int x, y, stack;
        public CheesePoint(Point p) { this.x = p.x; this.y = p.y; }
        public void addStack() { stack++; }
        public boolean hasToDelete() { return stack >= 2; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CheesePoint that = (CheesePoint) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Point {
        int x, y;
        public Point(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


}
