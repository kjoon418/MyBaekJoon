package baekjoon.gold;

import java.io.*;
import java.util.StringTokenizer;

public class B17404 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static House[] houses;
    private static int size;
    private static int result;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        size = Integer.parseInt(in.readLine());
        houses = new House[size];

        for (int i = 0; i < houses.length; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine(), " ");
            int costRed = Integer.parseInt(st.nextToken());
            int costGreen = Integer.parseInt(st.nextToken());
            int costBlue = Integer.parseInt(st.nextToken());

            houses[i] = new House(costRed, costGreen, costBlue);
        }
    }

    private static void controller() throws IOException {
        result = houses[0].getTotalCostFirstIndex();
    }

    private static void printer() throws IOException {
        out.write(result+"");

        out.close();
    }

    private static class House {
        private int costRed, costGreen, costBlue;
        private int[] totalCostWhenRed = new int[9];
        private int[] totalCostWhenGreen = new int[9];
        private int[] totalCostWhenBlue = new int[9];
        boolean calculateFirstRed = false;
        boolean calculateFirstGreen = false;
        boolean calculateFirstBlue = false;

        public House(int costRed, int costGreen, int costBlue) {
            this.costRed = costRed;
            this.costGreen = costGreen;
            this.costBlue = costBlue;

            for (int i = 0; i < 9; i++) {
                totalCostWhenRed[i] = -1;
                totalCostWhenGreen[i] = -1;
                totalCostWhenBlue[i] = -1;
            }
        }

        public int getTotalCostFirstIndex() {
            int result = Integer.MAX_VALUE;
            result = Math.min(result, getTotalCostWhenRed(0,  'R', 'R'));
            result = Math.min(result, getTotalCostWhenGreen(0, 'G', 'G'));
            result = Math.min(result, getTotalCostWhenBlue(0, 'B', 'B'));

            return result;
        }

        public static int getTotalCost(int index, char beforeColor, char firstColor) {
            if (index+1 >= houses.length) {
                return getTotalCostLastIndex(beforeColor, firstColor);
            }

            House house = houses[index];
            if (beforeColor == 'R') {
                return Math.min(house.getTotalCostWhenGreen(index, beforeColor, firstColor), house.getTotalCostWhenBlue(index, beforeColor, firstColor));
            }
            if (beforeColor == 'G') {
                return Math.min(house.getTotalCostWhenRed(index, beforeColor, firstColor), house.getTotalCostWhenBlue(index, beforeColor, firstColor));
            }
            if (beforeColor == 'B') {
                return Math.min(house.getTotalCostWhenRed(index, beforeColor, firstColor), house.getTotalCostWhenGreen(index, beforeColor, firstColor));
            }

            throw new IllegalArgumentException("Color가 R G B 중 하나가 아닙니다");
        }

        public static int getTotalCostLastIndex(char beforeColor, char firstColor) {
            House house = houses[houses.length - 1];

            int result = Integer.MAX_VALUE;

            if (beforeColor != 'R' && firstColor != 'R') {
                result = Math.min(result, house.costRed);
            }
            if (beforeColor != 'G' && firstColor != 'G') {
                result = Math.min(result, house.costGreen);
            }
            if (beforeColor != 'B' && firstColor != 'B') {
                result = Math.min(result, house.costBlue);
            }

            return result;
        }

        public int getTotalCostWhenRed(int index, char beforeColor, char firstColor) {
            int colorIndex = getIndexByColor(beforeColor, firstColor);
            if (totalCostWhenRed[colorIndex] >= 0) {
                return totalCostWhenRed[colorIndex];
            }
            totalCostWhenRed[colorIndex] = costRed + getTotalCost(index+1, 'R', firstColor);

            return totalCostWhenRed[colorIndex];
        }

        public int getTotalCostWhenGreen(int index, char beforeColor, char firstColor) {
            int colorIndex = getIndexByColor(beforeColor, firstColor);
            if (totalCostWhenGreen[colorIndex] >= 0) {
                return totalCostWhenGreen[colorIndex];
            }
            totalCostWhenGreen[colorIndex] = costGreen + getTotalCost(index+1, 'G', firstColor);

            return totalCostWhenGreen[colorIndex];
        }

        public int getTotalCostWhenBlue(int index, char beforeColor, char firstColor) {
            int colorIndex = getIndexByColor(beforeColor, firstColor);
            if (totalCostWhenBlue[colorIndex] >= 0) {
                return totalCostWhenBlue[colorIndex];
            }
            totalCostWhenBlue[colorIndex] = costBlue + getTotalCost(index+1, 'B', firstColor);

            return totalCostWhenBlue[colorIndex];
        }

        private int getIndexByColor(char beforeColor, char firstColor) {
            if (beforeColor == 'R') {
                switch (firstColor) {
                    case 'R':
                        return 0;
                    case 'G':
                        return 1;
                    case 'B':
                        return 2;
                    default:
                        throw  new IllegalArgumentException();
                }
            }
            if (beforeColor == 'G') {
                switch (firstColor) {
                    case 'R':
                        return 3;
                    case 'G':
                        return 4;
                    case 'B':
                        return 5;
                    default:
                        throw  new IllegalArgumentException();
                }
            }
            if (beforeColor == 'B') {
                switch (firstColor) {
                    case 'R':
                        return 6;
                    case 'G':
                        return 7;
                    case 'B':
                        return 8;
                    default:
                        throw  new IllegalArgumentException();
                }
            }

            throw new IllegalArgumentException("색상이 R G B 중에 하나가 아닙니다");
        }
    }


}
