package baekjoon.Gold;

import java.io.*;
import java.util.*;

/**
 * 백준 1103 문제 - 게임
 * DP, 그래프, 깊이 우선 탐색
 */
public class B1103 {
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final int HOLE = Integer.MIN_VALUE;

    private static int[][] board;
    private static int width;
    private static int height;

    public static void main(String[] args) throws IOException {
        init();

        Graph graph = new Graph();

        if (graph.hasCycle()) {
            out.write("-1");
        } else {
            out.write(graph.getMaxDepth() + "");
        }

        out.close();
    }

    private static void init() throws IOException {
        StringTokenizer size = new StringTokenizer(in.readLine(), " ");
        height = Integer.parseInt(size.nextToken());
        width = Integer.parseInt(size.nextToken());
        board = new int[height][width];

        for (int y = 0; y < height; y++) {
            String line = in.readLine();
            for (int x = 0; x < width; x++) {
                String word = line.substring(x, x + 1);
                if (word.equals("H")) {
                    board[y][x] = HOLE;
                } else {
                    board[y][x] = Integer.parseInt(word);
                }
            }
        }
    }

    private static class Graph {
        Edge startEdge;
        final Map<Point, Edge> edges = new HashMap<>();

        public Graph() {
            startEdge = new Edge(board[0][0], 0, 0);
            edges.put(startEdge.point, startEdge);

            ArrayDeque<Edge> edgeQueue = new ArrayDeque<>();
            edgeQueue.offer(startEdge);
            while (!edgeQueue.isEmpty()) {
                Edge edge = edgeQueue.poll();
                Point point = edge.point;
                int jump = edge.jump;

                // 왼쪽 전이
                Point leftPoint = new Point(point.x - jump, point.y);
                if (leftPoint.x >= 0 && board[leftPoint.y][leftPoint.x] != HOLE) {
                    // 이미 생성한 정점이라면 넘어가고, 아니라면 새로 추가함
                    if (edges.containsKey(leftPoint)) {
                        edge.adjacentEdges.add(edges.get(leftPoint));
                    } else {
                        Edge newEdge = new Edge(board[leftPoint.y][leftPoint.x], leftPoint);
                        edge.adjacentEdges.add(newEdge);
                        edges.put(leftPoint, newEdge);
                        edgeQueue.offer(newEdge);
                    }
                }

                // 오른쪽 전이
                Point rightPoint = new Point(point.x + jump, point.y);
                if (rightPoint.x < width && board[rightPoint.y][rightPoint.x] != HOLE) {
                    // 이미 생성한 정점이라면 넘어가고, 아니라면 새로 추가함
                    if (edges.containsKey(rightPoint)) {
                        edge.adjacentEdges.add(edges.get(rightPoint));
                    } else {
                        Edge newEdge = new Edge(board[rightPoint.y][rightPoint.x], rightPoint);
                        edge.adjacentEdges.add(newEdge);
                        edges.put(rightPoint, newEdge);
                        edgeQueue.offer(newEdge);
                    }
                }

                // 위쪽 전이
                Point upperPoint = new Point(point.x, point.y - jump);
                if (upperPoint.y >= 0 && board[upperPoint.y][upperPoint.x] != HOLE) {
                    // 이미 생성한 정점이라면 넘어가고, 아니라면 새로 추가함
                    if (edges.containsKey(upperPoint)) {
                        edge.adjacentEdges.add(edges.get(upperPoint));
                    } else {
                        Edge newEdge = new Edge(board[upperPoint.y][upperPoint.x], upperPoint);
                        edge.adjacentEdges.add(newEdge);
                        edges.put(upperPoint, newEdge);
                        edgeQueue.offer(newEdge);
                    }
                }

                // 아래쪽 전이
                Point lowerPoint = new Point(point.x, point.y + jump);
                if (lowerPoint.y < height && board[lowerPoint.y][lowerPoint.x] != HOLE) {
                    // 이미 생성한 정점이라면 넘어가고, 아니라면 새로 추가함
                    if (edges.containsKey(lowerPoint)) {
                        edge.adjacentEdges.add(edges.get(lowerPoint));
                    } else {
                        Edge newEdge = new Edge(board[lowerPoint.y][lowerPoint.x], lowerPoint);
                        edge.adjacentEdges.add(newEdge);
                        edges.put(lowerPoint, newEdge);
                        edgeQueue.offer(newEdge);
                    }
                }
            }
        }

        public boolean hasCycle() {
            for (Edge edge : edges.values()) {
                edge.visitStatus = VisitStatus.NOT_VISITED;
            }

            return startEdge.hasCycle();
        }

        public int getMaxDepth() {
            return startEdge.getMaxDepth();
        }
    }

    private static class Edge {
        private static final int NOT_CALCULATED = -1;

        final int jump;
        int maxDepth = NOT_CALCULATED;
        final Point point;
        VisitStatus visitStatus = VisitStatus.NOT_VISITED;
        final Set<Edge> adjacentEdges = new HashSet<>();

        public Edge(int jump, int x, int y) {
            this.jump = jump;
            this.point = new Point(x, y);
        }

        public Edge(int jump, Point point) {
            this.jump = jump;
            this.point = point;
        }

        public boolean hasCycle() {
            visitStatus = VisitStatus.VISITED;

            for (Edge adjacentEdge : adjacentEdges) {
                if (adjacentEdge.visitStatus == VisitStatus.FINISHED) {
                    continue;
                }

                if (adjacentEdge.visitStatus == VisitStatus.VISITED) {
                    return true;
                }

                if (adjacentEdge.hasCycle()) {
                    return true;
                }
            }

            visitStatus = VisitStatus.FINISHED;
            return false;
        }

        public int getMaxDepth() {
            if (maxDepth != NOT_CALCULATED) {
                return maxDepth;
            }

            List<Integer> adjacentDepths = new ArrayList<>();
            for (Edge adjacentEdge : adjacentEdges) {
                adjacentDepths.add(adjacentEdge.getMaxDepth());
            }

            maxDepth = adjacentDepths.stream()
                    .max(Integer::compare)
                    .orElse(0)
                    + 1;

            return maxDepth;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return Objects.equals(point, edge.point);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(point);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "jump=" + jump +
                    ", point=" + point +
                    ", maxDepth=" + maxDepth +
                    ", adjacentEdges=" + adjacentEdges +
                    '}';
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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private enum VisitStatus {
        NOT_VISITED, VISITED, FINISHED
    }
}
