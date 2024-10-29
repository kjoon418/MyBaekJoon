package Gold;

import java.io.*;
import java.util.*;

public class B1865_failedByMemory {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int testCase;

    private static int id; // 기본키로 사용할 중복되지 않는 아이디

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        testCase = Integer.parseInt(in.readLine());
    }

    private static void controller() throws IOException {
        Map<Integer, City> cities = new HashMap<>();
        Map<Integer, Road> roads = new HashMap<>();
        Map<Integer, Wormhole> wormholes = new HashMap<>();

        for (int i = 0; i < testCase; i++) {
            StringTokenizer input = new StringTokenizer(in.readLine(), " ");
            int cityAmount = Integer.parseInt(input.nextToken());
            int roadAmount = Integer.parseInt(input.nextToken());
            int wormholeAmount = Integer.parseInt(input.nextToken());

            for (int j = 0; j < roadAmount; j++) {
                StringTokenizer st = new StringTokenizer(in.readLine(), " ");
                int cityId1 = Integer.parseInt(st.nextToken());
                int cityId2 = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());

                registerRoad(cities, roads, cityId1, cityId2, time);
            }

            for (int j = 0; j < wormholeAmount; j++) {
                StringTokenizer st = new StringTokenizer(in.readLine()," ");
                int startCity = Integer.parseInt(st.nextToken());
                int endCity = Integer.parseInt(st.nextToken());
                int reverseTime = Integer.parseInt(st.nextToken());

                registerWormhole(cities, wormholes, startCity, endCity, reverseTime);
           }

            boolean isFind = false;
            for (Map.Entry<Integer, Wormhole> entry : wormholes.entrySet()) {
                if (findLoop(cities, entry.getValue(), roads, wormholes)) {
                    out.write("YES" + System.lineSeparator());
                    isFind = true;
                    break;
                }
            }

            if (!isFind) {
                out.write("NO" + System.lineSeparator());
            }

            cities.clear();
            roads.clear();
            wormholes.clear();
        }
    }

    /**
     * 웜홀을 저장하고, 지점과 웜홀을 연결하는 메서드
     * @param cities 지점을 저장하는 Map
     * @param wormholes 웜홀을 저장하는 리스트
     * @param startCityId 첫 번째 지점의 아이디
     * @param endCityId 두 번째 지점의 아이디
     * @param reverseTime 도로를 통해 이동하는데 걸리는 시간
     */
    private static void registerWormhole(Map<Integer, City> cities, Map<Integer, Wormhole> wormholes, int startCityId, int endCityId, int reverseTime) {
        City city1, city2;
        if (cities.containsKey(startCityId)) {
            city1 = cities.get(startCityId);
        } else {
            city1 = new City(startCityId);
            cities.put(startCityId, city1);
        }
        if (cities.containsKey(endCityId)) {
            city2 = cities.get(endCityId);
        } else {
            city2 = new City(endCityId);
            cities.put(endCityId, city2);
        }
        int wormholeId = getId();

        Wormhole wormhole = new Wormhole(startCityId, endCityId, reverseTime, wormholeId);
        city1.addWormhole(wormhole);
        city2.addWormhole(wormhole);
        wormholes.put(wormholeId, wormhole);
    }

    /**
     * 도로를 저장하고, 지점과 도로를 연결하는 메서드
     * @param cities 지점을 저장하는 Map
     * @param roads 도로를 저장하는 리스트
     * @param cityId1 첫 번째 지점의 아이디
     * @param cityId2 두 번째 지점의 아이디
     * @param time 도로를 통해 이동하는데 걸리는 시간
     */
    private static void registerRoad(Map<Integer, City> cities, Map<Integer, Road> roads, int cityId1, int cityId2, int time) {
        City city1, city2;
        if (cities.containsKey(cityId1)) {
            city1 = cities.get(cityId1);
        } else {
            city1 = new City(cityId1);
            cities.put(cityId1, city1);
        }
        if (cities.containsKey(cityId2)) {
            city2 = cities.get(cityId2);
        } else {
            city2 = new City(cityId2);
            cities.put(cityId2, city2);
        }
        int roadId = getId();

        Road road = new Road(cityId1, cityId2, time, roadId);
        city1.addRoad(road);
        city2.addRoad(road);
        roads.put(roadId, road);
    }

    /**
     * 해당 웜홀에서 시작해서 다시 돌아올 수 있으면서, 시간이 음수가 되는 경우의 수가 있는지 판단함
     */
    private static boolean findLoop(Map<Integer, City> cities, Wormhole wormhole, Map<Integer, Road> roads, Map<Integer, Wormhole> wormholes) {
        int startCityId = wormhole.startCityId;
        int endCityId = wormhole.endCityId;

        City startCity = cities.get(startCityId);
        City endCity = cities.get(endCityId);

        Set<Integer> ids = new HashSet<>();
        ids.add(wormhole.wormholeId);

        return move(-wormhole.reverseTime, endCity, startCityId, ids, cities, roads, wormholes);
    }

    /**
     *
     */
    private static boolean move(int time,
                                City startCity,
                                int originalCityId,
                                Set<Integer> ids,
                                Map<Integer, City> cities,
                                Map<Integer, Road> roads,
                                Map<Integer, Wormhole> wormholes) {
        // 한바퀴 돌아오는데 성공하면, 시간이 음수인지 찾음
        if (startCity.cityId == originalCityId) {
            if (time < 0) {
                return true;
            }
            return false;
        }

        Set<Integer> savePoint = new HashSet<>(ids);

        for (Integer wormholeId : startCity.linkedWormholeIds) {
            // 이미 가본 웜홀은 재귀하지 않음
            if (ids.contains(wormholeId)) {
                continue;
            }

            Wormhole wormhole = wormholes.get(wormholeId);

            int nextTime = time - wormhole.reverseTime;
            City nextCity = cities.get(wormhole.endCityId);
            ids.add(wormhole.wormholeId);
            if (move(nextTime, nextCity, originalCityId, ids, cities, roads, wormholes)) {
                return true;
            }
            ids = savePoint;
        }

        for (Integer roadId : startCity.linkedRoadsIds) {
            // 이미 가본 도로는 재귀하지 않음
            if (ids.contains(roadId)) {
                continue;
            }

            Road road = roads.get(roadId);

            int nextTime = time + road.time;
            City nextCity = cities.get(road.findOppositeCityId(startCity.cityId));
            ids.add(roadId);
            if (move(nextTime, nextCity, originalCityId, ids, cities, roads, wormholes)) {
                return true;
            }

            ids = savePoint;
        }

        // 한바퀴 돌아오지도 못했고, 길이 없다면 false를 반환함
        return false;
    }

    private static int getId() {
        return id++;
    }

    private static void printer() throws IOException {
        out.close();
    }

    private static class City {
        int cityId;
        List<Integer> linkedRoadsIds = new ArrayList<>();
        List<Integer> linkedWormholeIds = new ArrayList<>();

        public City(int cityId) {
            this.cityId = cityId;
        }

        public void addRoad(Road road) {
            linkedRoadsIds.add(road.roadId);
        }

        /**
         * 이 지점에서 탈 수 있는 웜홀을 저장함
         */
        public void addWormhole(Wormhole wormhole) {
            linkedWormholeIds.add(wormhole.wormholeId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            City city = (City) o;
            return cityId == city.cityId;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(cityId);
        }
    }

    private static class Road {
        int cityId1;
        int cityId2;
        int time;
        int roadId;

        public Road(int cityId1, int cityId2, int time, int roadId) {
            this.cityId1 = cityId1;
            this.cityId2 = cityId2;
            this.time = time;
            this.roadId = roadId;
        }

        public int findOppositeCityId(int cityId) {
            if (cityId == cityId1) {
                return cityId2;
            } else {
                return cityId1;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Road road = (Road) o;
            return Objects.equals(roadId, road.roadId);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(roadId);
        }
    }

    private static class Wormhole {
        int startCityId, endCityId;
        int reverseTime;
        int wormholeId;

        public Wormhole(int startCityId, int endCityId, int reverseTime, int wormholeId) {
            this.startCityId = startCityId;
            this.endCityId = endCityId;
            this.reverseTime = reverseTime;
            this.wormholeId = wormholeId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wormhole wormhole = (Wormhole) o;
            return Objects.equals(wormholeId, wormhole.wormholeId);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(wormholeId);
        }
    }
}
