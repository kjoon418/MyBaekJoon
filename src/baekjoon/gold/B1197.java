package baekjoon.gold;

import java.io.*;
import java.util.*;

public class B1197 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final int MAX_VALUE = 1_000_000;
    private static final int NO_AFFILIATION = -1;

    private static int id;
    private static long result;

    private static Node[] nodes;
    private static Edge[] edges;
    private static Edge[] chosenEdges;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        StringTokenizer size = new StringTokenizer(in.readLine(), " ");
        nodes = new Node[Integer.parseInt(size.nextToken())];
        edges = new Edge[Integer.parseInt(size.nextToken())];
        chosenEdges = new Edge[nodes.length - 1];

        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node();
        }

        StringTokenizer input;
        for (int i = 0; i < edges.length; i++) {
            input = new StringTokenizer(in.readLine(), " ");
            Node nodeA = nodes[Integer.parseInt(input.nextToken()) - 1];
            Node nodeB = nodes[Integer.parseInt(input.nextToken()) - 1];
            edges[i] = new Edge(nodeA, nodeB, Integer.parseInt(input.nextToken()));
        }
    }

    private static void controller() throws IOException {
        // 모든 간선을 가중치의 오름차순으로 정렬한다
        radixSort(edges);

        // testPrint(edges);
        
        // n-1개의 간선이 선택될 때 까지 반복한다
        int chosenEdgeAmount = 0;
        int index = 0;
        while (chosenEdgeAmount < nodes.length - 1) {
            // 선택되지 않은 간선 중 가중치가 가장 작은 간선을 선택한다
            Edge leastEdge = edges[index++];

            // 만약 간선이 사이클을 만든다면 다음 간선으로 넘어간다
            if (isBelongToSameTree(leastEdge)) {
                continue;
            }

            // 간선이 사이클을 만들지 않는다면 두 트리를 하나로 합친다
            unifyTrees(leastEdge, nodes);

            // 선택한 간선을 배열에 추가한다
            chosenEdges[chosenEdgeAmount++] = leastEdge;
        }
        
        // 간선의 합을 계산한다
        result = getTotalWeight(chosenEdges);
    }

    private static void printer() throws IOException {
        out.write(Long.toString(result));

        out.close();
    }

    private static void radixSort(Edge[] edges) {
        TreeMap<Integer, List<Edge>> lists = new TreeMap<>();

        for (int i = -9; i < 10; i++) {
            lists.put(i, new ArrayList<>());
        }

        for (int i = 1; i <= MAX_VALUE*10; i *= 10) {
            for (Edge edge : edges) {
                int radix = edge.weight % (i * 10) / i;
                // 가중치가 음수라면 0~8번 인덱스에 저장함
                lists.get(radix).add(edge);
            }
            int index = 0;
            for (List<Edge> list : lists.values()) {
                for (Edge edge : list) {
                    edges[index++] = edge;
                }
                list.clear();
            }
        }
    }

    private static boolean isBelongToSameTree(Edge edge) {
        Node nodeA = edge.nodeA;
        Node nodeB = edge.nodeB;

        if (nodeA == nodeB) {
            return true;
        }

        if (nodeA.affiliation < 0 || nodeB.affiliation < 0) {
            return false;
        }

        return nodeA.affiliation == nodeB.affiliation;
    }

    private static void unifyTrees(Edge edge, Node[] nodes) {
        Node nodeA = edge.nodeA;
        Node nodeB = edge.nodeB;

        // 만약 둘 다 트리에 속하지 않는다면, 둘을 합쳐 새 트리를 만든다
        if (nodeA.affiliation == NO_AFFILIATION && nodeB.affiliation == NO_AFFILIATION) {
            int id = getId();
            nodeA.affiliation = id;
            nodeB.affiliation = id;
            return;
        }

        // 한쪽만 트리가 있을 경우엔, 하나로 합친다
        if (nodeA.affiliation == NO_AFFILIATION) {
            // nodeB만 트리가 있는 경우엔, nodeB의 트리로 합친다
            nodeA.affiliation = nodeB.affiliation;
            return;
        }
        if (nodeB.affiliation == NO_AFFILIATION) {
            // nodeA만 트리가 있는 경우엔, nodeA의 트리로 합친다
            nodeB.affiliation = nodeA.affiliation;
            return;
        }

        // 양쪽 다 트리가 있을 경우엔, nodeA의 트리로 나머지 모든 트리의 노드를 합친다
        int affiliation = nodeB.affiliation;
        for (Node node : nodes) {
            if (node.affiliation == affiliation) {
                node.affiliation = nodeA.affiliation;
            }
        }
    }
    
    private static long getTotalWeight(Edge[] edges) {
        long sum = 0;
        
        for (Edge edge : edges) {
            sum += edge.weight;
        }
        
        return sum;
    }

    private static int getId() {
        return id++;
    }

    private static void testPrint(Edge[] edges) {
        int index = 1;
        for (Edge edge : edges) {
            System.out.println(index++ + "-Edge-weight: " + edge.weight);
        }
    }

    private static class Edge {
        Node nodeA;
        Node nodeB;
        int weight;

        public Edge(Node nodeA, Node nodeB, int weight) {
            this.nodeA = nodeA;
            this.nodeB = nodeB;
            this.weight = weight;
        }
    }

    private static class Node {
        int affiliation = NO_AFFILIATION;
    }
}
