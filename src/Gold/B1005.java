package Gold;

import java.io.*;
import java.util.*;

public class B1005 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int testAmount;

    private static final Map<Integer, Building> buildings = new HashMap<>();

    private static final StringBuilder result = new StringBuilder();

    private static int finalBuildingIndex;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        testAmount = Integer.parseInt(in.readLine());
    }

    private static void initTest() throws IOException {
        StringTokenizer amounts = new StringTokenizer(in.readLine(), " ");
        int buildingAmount = Integer.parseInt(amounts.nextToken());
        int linkAmount = Integer.parseInt(amounts.nextToken());

        StringTokenizer times = new StringTokenizer(in.readLine(), " ");
        int index = 1;
        while (times.hasMoreTokens()) {
            int time = Integer.parseInt(times.nextToken());
            Building building = new Building(time);
            buildings.put(index, building);
            index++;
        }

        for (int i = 0; i < linkAmount; i++) {
            StringTokenizer buildingInput = new StringTokenizer(in.readLine(), " ");
            int prevBuildingIndex = Integer.parseInt(buildingInput.nextToken());
            int nextBuildingIndex = Integer.parseInt(buildingInput.nextToken());
            Building prevBuilding = buildings.get(prevBuildingIndex);
            Building nextBuilding = buildings.get(nextBuildingIndex);
            nextBuilding.addPrevBuilding(prevBuilding);
        }

        finalBuildingIndex = Integer.parseInt(in.readLine());
    }

    private static void clearInputs() {
        buildings.clear();
    }

    private static void controller() throws IOException {
        for (int i = 0; i < testAmount; i++) {
            clearInputs();
            initTest();

            Building finalBuilding = buildings.get(finalBuildingIndex);

            int totalTime = finalBuilding.getTotalTime();

            result.append(totalTime).append(System.lineSeparator());
        }
    }

    private static void printer() throws IOException {
        out.write(result.toString());

        out.close();
    }

    private static class Building {
        private int time;
        private int totalTime = -1;
        private List<Building> prevBuidings = new ArrayList<>();

        public Building(int time) {
            this.time = time;
        }

        public void addPrevBuilding(Building prevBuilding) {
            this.prevBuidings.add(prevBuilding);
        }

        public int getTotalTime() {
            if (this.totalTime >= 0) {
                return this.totalTime;
            }
            if (prevBuidings.isEmpty()) {
                this.totalTime = time;
                return this.totalTime;
            }

            int max = Integer.MIN_VALUE;
            for (Building prevBuilding : prevBuidings) {
                if (prevBuilding.getTotalTime() > max) {
                    max = prevBuilding.getTotalTime();
                }
            }

            this.totalTime = max + this.time;

            return this.totalTime;
        }

        public int getTime() {
            return this.time;
        }
    }
}
