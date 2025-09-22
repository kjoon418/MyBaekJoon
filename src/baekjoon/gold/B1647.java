package baekjoon.gold;

import java.io.*;
import java.util.*;

/**
 * 도시 분할 계획 - 그래프(최소 신장 트리)
 */
public class B1647 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static Vertex[] vertexes;
    private static List<Edge> edges;

    public static void main(String[] args) throws IOException {
        init();

        Edge[] spanningTree = getSpanningTree(edges, vertexes);

        printResult(spanningTree);
    }

    private static void init() throws IOException {
        StringTokenizer amounts = new StringTokenizer(in.readLine(), " ");
        int vertexAmount = Integer.parseInt(amounts.nextToken());
        int edgeAmount = Integer.parseInt(amounts.nextToken());
        
        vertexes = new Vertex[vertexAmount];
        edges = new LinkedList<>();
        
        for (int i = 0; i < vertexAmount; i++) {
            vertexes[i] = new Vertex();
        }
        for (int i = 0; i < edgeAmount; i++) {
            StringTokenizer edgeInput = new StringTokenizer(in.readLine(), " ");
            Vertex vertex1 = vertexes[(Integer.parseInt(edgeInput.nextToken()) - 1)];
            Vertex vertex2 = vertexes[(Integer.parseInt(edgeInput.nextToken()) - 1)];
            int weight = Integer.parseInt(edgeInput.nextToken());

            edges.add(new Edge(vertex1, vertex2, weight));
        }
    }

    private static Edge[] getSpanningTree(List<Edge> edges, Vertex[] vertexes) {
        edges.sort(null);
        Edge[] resultSpanningTree = new Edge[vertexes.length - 1];
        int treeSize = 0;

        while (treeSize < vertexes.length - 1) {
            Edge edge = edges.get(0);
            edges.remove(0);
            Vertex vertex1 = edge.vertex1;
            Vertex vertex2 = edge.vertex2;

            if (vertex1.findSet() == vertex2.findSet()) {
                continue;
            }

            resultSpanningTree[treeSize++] = edge;
            vertex1.union(vertex2);
        }

        return resultSpanningTree;
    }

    private static void printResult(Edge[] spanningTree) throws IOException {
        // 비용이 가장 큰 간선(마지막 간선)을 제거해서 마을을 2개로 분리
        Edge[] resultSpanningTree = new Edge[spanningTree.length - 1];
        System.arraycopy(spanningTree, 0, resultSpanningTree, 0, resultSpanningTree.length);

        int result = Arrays.stream(resultSpanningTree)
                .mapToInt(edge -> edge.weight)
                .sum();

        out.write(result + "");
        out.close();
    }

    private static class Vertex {

        Vertex parent = this;
        int rank = 0;

        // 경로 압축 findSet
        public Vertex findSet() {
            if (parent != this) {
                parent = parent.findSet();
            }

            return parent;
        }

        // Rank union
        public void union(Vertex other) {
            Vertex thisSet = findSet();
            Vertex otherSet = other.findSet();

            if (thisSet.rank > otherSet.rank) {
                otherSet.parent = thisSet;
                return;
            }
            if (thisSet.rank < otherSet.rank) {
                thisSet.parent = otherSet;
                return;
            }

            otherSet.parent = thisSet;
            thisSet.rank++;
        }

    }

    private static class Edge implements Comparable<Edge> {

        final Vertex vertex1, vertex2;
        final int weight;

        public Edge(Vertex vertex1, Vertex vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }

    }

}
