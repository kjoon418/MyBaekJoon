package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 백준 16946번 문제 - 벽 부수고 이동하기 4(골드2)
 */
public class B16946 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] size = in.readLine().split(" ");
        int height = Integer.parseInt(size[0]);
        int width = Integer.parseInt(size[1]);

        boolean[][] wallExists = new boolean[height][width];
        for (int y = 0; y < height; y++) {
            String line = in.readLine();
            for (int x = 0; x < width; x++) {
                wallExists[y][x] = line.charAt(x) == '1';
            }
        }
        Board board = new Board(wallExists);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int movableAmount = board.getMovableAmount(x, y);
                out.write((movableAmount % 10) + "");
            }
            out.write(System.lineSeparator());
        }

        out.close();
    }

    private static class Board {

        private final boolean[][] wallExists;
        private final int width;
        private final int height;
        private final int[][] movableAmounts;
        private final Cluster[][] clusters;

        public Board(boolean[][] wallExists) {
            this.wallExists = wallExists;
            width = wallExists[0].length;
            height = wallExists.length;
            movableAmounts = new int[height][width];
            clusters = new Cluster[height][width];

            calculateClusters();
            calculateMovableAmounts();
        }

        public int getMovableAmount(int x, int y) {
            return movableAmounts[y][x];
        }

        private void calculateClusters() {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (!isOriginallyEmpty(x, y)) {
                        continue;
                    }
                    if (clusters[y][x] != null) {
                        continue;
                    }
                    Set<Point> clusteredPoints = new HashSet<>();
                    calculateCluster(x, y, clusteredPoints);
                    saveCluster(clusteredPoints);
                }
            }
        }

        private void calculateMovableAmounts() {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (isOriginallyEmpty(x, y)) {
                        continue;
                    }
                    Set<Cluster> adjacentClusters = getAdjacentClusters(x, y);

                    int clustersSum = adjacentClusters.stream()
                            .mapToInt(Cluster::size)
                            .sum();
                    movableAmounts[y][x] = clustersSum + 1;
                }
            }
        }

        private boolean isOriginallyEmpty(int x, int y) {
            return !wallExists[y][x];
        }

        private void calculateCluster(int x, int y, Set<Point> clusteredPoints) {
            Point now = new Point(x, y);
            if (isOutOfBoard(x, y)
                    || clusteredPoints.contains(now)
                    || !isOriginallyEmpty(x, y)) {
                return;
            }

            clusteredPoints.add(now);
            calculateCluster(x, y - 1, clusteredPoints);
            calculateCluster(x, y + 1, clusteredPoints);
            calculateCluster(x - 1, y, clusteredPoints);
            calculateCluster(x + 1, y, clusteredPoints);
        }

        private void saveCluster(Set<Point> clusteredPoints) {
            Cluster cluster = new Cluster(clusteredPoints);

            for (Point point : cluster.getPoints()) {
                clusters[point.y][point.x] = cluster;
            }
        }

        private Set<Cluster> getAdjacentClusters(int x, int y) {
            HashSet<Cluster> adjacentClusters = new HashSet<>();

            if (isInBoard(x, y - 1) && clusters[y - 1][x] != null) {
                adjacentClusters.add(clusters[y - 1][x]);
            }
            if (isInBoard(x, y + 1) && clusters[y + 1][x] != null) {
                adjacentClusters.add(clusters[y + 1][x]);
            }
            if (isInBoard(x - 1, y) && clusters[y][x - 1] != null) {
                adjacentClusters.add(clusters[y][x - 1]);
            }
            if (isInBoard(x + 1, y) && clusters[y][x + 1] != null) {
                adjacentClusters.add(clusters[y][x + 1]);
            }

            return adjacentClusters;
        }

        public boolean isOutOfBoard(int x, int y) {
            return x < 0
                    || y < 0
                    || x >= width
                    || y >= height;
        }

        public boolean isInBoard(int x, int y) {
            return !isOutOfBoard(x, y);
        }
    }

    private static class Cluster {

        private final Set<Point> points;

        public Cluster(Set<Point> points) {
            this.points = points;
        }

        public int size() {
            return points.size();
        }

        public Set<Point> getPoints() {
            return points;
        }
    }

    private static class Point {

        final int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
