package Gold;

import java.io.*;
import java.util.*;

public class B15681 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final HashMap<Integer, Vertex> vertexes = new HashMap<>();
    private static int rootNumber;
    private static int queryAmount;
    private static final StringBuilder resultBuilder = new StringBuilder();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        StringTokenizer info = new StringTokenizer(in.readLine(), " ");
        int vertexAmount = Integer.parseInt(info.nextToken());
        rootNumber = Integer.parseInt(info.nextToken());
        queryAmount = Integer.parseInt(info.nextToken());

        StringTokenizer edgeInfo;
        for (int i = 0; i < vertexAmount-1; i++) {
            edgeInfo = new StringTokenizer(in.readLine(), " ");
            int idA = Integer.parseInt(edgeInfo.nextToken());
            int idB = Integer.parseInt(edgeInfo.nextToken());

            Vertex vertexA;
            if (vertexes.containsKey(idA)) {
                vertexA = vertexes.get(idA);
            } else {
                vertexA = new Vertex(idA);
                vertexes.put(idA, vertexA);
            }
            Vertex vertexB;
            if (vertexes.containsKey(idB)) {
                vertexB = vertexes.get(idB);
            } else {
                vertexB = new Vertex(idB);
                vertexes.put(idB, vertexB);
            }

            Edge edge = new Edge(vertexA, vertexB);
            vertexA.addEdge(edge);
            vertexB.addEdge(edge);
        }
    }

    private static void controller() throws IOException {
        Vertex root = vertexes.get(rootNumber);
        root.setRoot();

        for (int i = 0; i < queryAmount; i++) {
            int query = Integer.parseInt(in.readLine());

            int result = vertexes.get(query).getTotalVertexAmount();
            resultBuilder.append(result).append(System.lineSeparator());
        }
    }

    private static void printer() throws IOException {
        out.write(resultBuilder.toString());

        out.close();
    }

    private static class Vertex {
        int id;
        Vertex parent;
        HashSet<Edge> edges = new HashSet<>();
        List<Vertex> children = new ArrayList<>();
        int totalVertexAmount = -1;

        public Vertex(int id) {
            this.id = id;
        }

        public void addEdge(Edge edge) {
            edges.add(edge);
        }

        public void setRoot() {
            for (Edge edge : edges) {
                Vertex child;
                if (edge.vertexA == this) {
                    child = edge.vertexB;
                } else {
                    child = edge.vertexA;
                }
                children.add(child);
                child.setParent(this, edge);
            }

            edges.clear();
        }

        public void setParent(Vertex parent, Edge removeEdge) {
            this.parent = parent;
            edges.remove(removeEdge);

            for (Edge edge : edges) {
                Vertex child;
                if (edge.vertexA == this) {
                    child = edge.vertexB;
                } else {
                    child = edge.vertexA;
                }
                children.add(child);
                child.setParent(this, edge);
            }

            edges.clear();
        }

        public int getTotalVertexAmount() {
            if (totalVertexAmount > 0) {
                return totalVertexAmount;
            }

            int sum = 1; // 자신을 포함
            for (Vertex child : children) {
                sum += child.getTotalVertexAmount();
            }

            totalVertexAmount = sum;
            return totalVertexAmount;
        }
    }

    private static class Edge {
        Vertex vertexA;
        Vertex vertexB;

        public Edge(Vertex vertexA, Vertex vertexB) {
            this.vertexA = vertexA;
            this.vertexB = vertexB;
        }
    }
}
