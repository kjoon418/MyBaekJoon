package baekjoon.Gold;

import java.io.*;
import java.util.*;

public class B2623 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static Map<Integer, Vertex> vertexMap;

    public static void main(String[] args) throws IOException {
        init();

        if (hasCycle()) {
            out.write(0+"");
        } else {
            List<Vertex> vertexes = getTopologicalSortedVertexes();

            for (Vertex vertex : vertexes) {
                out.write(vertex.id + System.lineSeparator());
            }
        }

        out.close();
    }

    private static void init() throws IOException {
        StringTokenizer countInfo = new StringTokenizer(in.readLine(), " ");

        int vertexCount = Integer.parseInt(countInfo.nextToken());
        vertexMap = new HashMap<>(vertexCount);
        for (int i = 1; i <= vertexCount; i++) {
            vertexMap.put(i, new Vertex(i));
        }

        int edgeInputCount = Integer.parseInt(countInfo.nextToken());
        for (int i = 0; i < edgeInputCount; i++) {
            StringTokenizer edgeInput = new StringTokenizer(in.readLine(), " ");
            int edgeCount = Integer.parseInt(edgeInput.nextToken());

            Vertex fromVertex = vertexMap.get(Integer.parseInt(edgeInput.nextToken()));
            while (edgeInput.hasMoreTokens()) {
                Vertex toVertex = vertexMap.get(Integer.parseInt(edgeInput.nextToken()));
                fromVertex.adjacentVertexes.add(toVertex);
                fromVertex = toVertex;
            }
        }
    }

    private static boolean hasCycle() {
        initVisitedStatus();

        for (Vertex vertex : vertexMap.values()) {
            if (vertex.visited == Visited.NO) {
                boolean hasCycle = dfsForCycleCheck(vertex);

                if (hasCycle) {
                    return true;
                }
            }
        }

        return false;
    }

    private static void initVisitedStatus() {
        for (Vertex vertex : vertexMap.values()) {
            vertex.visited = Visited.NO;
        }
    }

    private static boolean dfsForCycleCheck(Vertex vertex) {
        vertex.visited = Visited.YES;

        boolean hasCycle = false;
        for (Vertex adjacentVertex : vertex.adjacentVertexes) {
            switch (adjacentVertex.visited) {
                case NO:
                    hasCycle = dfsForCycleCheck(adjacentVertex);
                    break;
                case YES:
                    return true;
                case FINISHED:
                    continue;
            }

            if (hasCycle) return true;
        }

        vertex.visited = Visited.FINISHED;
        return hasCycle;
    }

    private static List<Vertex> getTopologicalSortedVertexes() {
        initVisitedStatus();

        List<Vertex> sortedVertexes = new LinkedList<>();
        for (Vertex vertex : vertexMap.values()) {
            if (vertex.visited == Visited.NO) {
                dfsForTopologicalSort(vertex, sortedVertexes);
            }
        }

        return sortedVertexes;
    }

    private static void dfsForTopologicalSort(Vertex vertex, List<Vertex> sortedVertexes) {
        vertex.visited = Visited.YES;

        for (Vertex adjacentVertex : vertex.adjacentVertexes) {
            if (adjacentVertex.visited == Visited.NO) {
                dfsForTopologicalSort(adjacentVertex, sortedVertexes);
            }
        }

        sortedVertexes.add(0, vertex);
    }

    private static class Vertex {
        final int id;
        final LinkedHashSet<Vertex> adjacentVertexes = new LinkedHashSet<>();
        Visited visited = Visited.NO;

        public Vertex(int id) {
            this.id = id;
        }
    }

    private enum Visited {
        NO, YES, FINISHED
    }

}
