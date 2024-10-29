package BronzeSilver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class B1149 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final List<House> houses = new ArrayList<>();

    private static int result;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        int loop = Integer.parseInt(in.readLine());

        for (int i = 0; i < loop; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine(), " ");
            int costRed = Integer.parseInt(st.nextToken());
            int costBlue = Integer.parseInt(st.nextToken());
            int costGreen = Integer.parseInt(st.nextToken());

            houses.add(new House(costRed, costGreen, costBlue));
        }
    }

    private static void controller() throws IOException {
        House house = houses.get(0);
        int whenRed = house.getTotalCostWhenRed(houses, 0);
        int whenGreen = house.getTotalCostWhenGreen(houses, 0);
        int whenBlue = house.getTotalCostWhenBlue(houses, 0);

        result = Math.min(whenRed, whenGreen);
        result = Math.min(result, whenBlue);
    }

    private static void printer() throws IOException {
        out.write(result+"");

        out.close();
    }

    private static class House {
        int costRed, costGreen, costBlue;
        int totalCostWhenRed = -1;
        int totalCostWhenGreen = -1;
        int totalCostWhenBlue = -1;

        public House(int costRed, int costGreen, int costBlue) {
            this.costRed = costRed;
            this.costGreen = costGreen;
            this.costBlue = costBlue;
        }

        public int getTotalCostWhenRed(List<House> houses, int index) {
            if (totalCostWhenRed < 0) {
                totalCostWhenRed = this.costRed + getMinCost(houses, index+1, 'R');
            }

            return totalCostWhenRed;
        }

        public int getTotalCostWhenGreen(List<House> houses, int index) {
            if (totalCostWhenGreen < 0) {
                totalCostWhenGreen = this.costGreen + getMinCost(houses, index+1, 'G');
            }
            return totalCostWhenGreen;
        }

        public int getTotalCostWhenBlue(List<House> houses, int index) {
            if (totalCostWhenBlue < 0) {
                totalCostWhenBlue = this.costBlue + getMinCost(houses, index+1, 'B');
            }
            return totalCostWhenBlue;
        }

        public static int getMinCost(List<House> houses, int index, char color) {
            if (index >= houses.size()) {
                return 0;
            }

            House house = houses.get(index);

            if (color == 'R') {
                return Math.min(house.getTotalCostWhenBlue(houses, index), house.getTotalCostWhenGreen(houses, index));
            }

            if (color == 'G') {
                return Math.min(house.getTotalCostWhenRed(houses, index), house.getTotalCostWhenBlue(houses, index));
            }

            if (color == 'B') {
                return Math.min(house.getTotalCostWhenRed(houses, index), house.getTotalCostWhenGreen(houses, index));
            }

            throw new IllegalStateException();
        }

    }


}
