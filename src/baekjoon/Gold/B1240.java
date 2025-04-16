package baekjoon.Gold;

import java.io.*;
import java.util.*;

/**
 * 노드사이의 거리
 */
public class B1240 {

    private static final String INPUT_SEPARATOR = " ";

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final Map<Integer, Node> nodes = new HashMap<>();
    private static final List<Question> questions = new ArrayList<>();
    private static final List<Integer> results = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();
        calculateResults();
        printResults();
    }

    private static void init() throws IOException {
        StringTokenizer input = new StringTokenizer(in.readLine(), INPUT_SEPARATOR);
        int nodeAmount = Integer.parseInt(input.nextToken());
        int questionAmount = Integer.parseInt(input.nextToken());

        initializeNodes(nodes, nodeAmount);

        for (int i = 0; i < nodeAmount - 1; i++) {
            StringTokenizer lengthInput = new StringTokenizer(in.readLine(), INPUT_SEPARATOR);
            int key1 = Integer.parseInt(lengthInput.nextToken());
            int key2 = Integer.parseInt(lengthInput.nextToken());
            int length = Integer.parseInt(lengthInput.nextToken());

            Node node1 = nodes.get(key1);
            Node node2 = nodes.get(key2);

            node1.connect(node2, length);
            node2.connect(node1, length);
        }

        for (int i = 0; i < questionAmount; i++) {
            StringTokenizer questionInput = new StringTokenizer(in.readLine(), INPUT_SEPARATOR);
            int key1 = Integer.parseInt(questionInput.nextToken());
            int key2 = Integer.parseInt(questionInput.nextToken());

            questions.add(new Question(key1, key2));
        }
    }

    private static void initializeNodes(Map<Integer, Node> nodes, int nodeAmount) {
        for (int i = 1; i <= nodeAmount; i++) {
            nodes.put(i, new Node(i));
        }
    }

    private static void calculateResults() {
        for (Question question : questions) {
            Node node1 = nodes.get(question.key1);
            Node node2 = nodes.get(question.key2);

            Integer result = calculateLength(null, node1, node2, 0);
            results.add(result);
        }
    }

    private static Integer calculateLength(Node prevNode, Node currentNode, Node findNode, int accumulatorOfLength) {
        if (currentNode.lengthOfNeighborNodes.containsKey(findNode)) {
            Integer length = currentNode.lengthOfNeighborNodes.get(findNode);

            return accumulatorOfLength + length;
        }

        for (Node neighborNode : currentNode.lengthOfNeighborNodes.keySet()) {
            if (neighborNode == prevNode) { // 사이클 방지
                continue;
            }

            Integer length = currentNode.lengthOfNeighborNodes.get(neighborNode);
            Integer result = calculateLength(currentNode, neighborNode, findNode, accumulatorOfLength + length);

            if (result != null) {
                return result;
            }
        }

        return null;
    }

    private static void printResults() throws IOException {
        for (Integer result : results) {
            out.write(result + System.lineSeparator());
        }

        out.close();
    }

    private static class Node {

        int key;
        final Map<Node, Integer> lengthOfNeighborNodes = new HashMap<>();

        public Node(int key) {
            this.key = key;
        }

        public void connect(Node node, int length) {
            lengthOfNeighborNodes.put(node, length);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return key == node.key;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key);
        }

    }

    private static class Question {

        final int key1, key2;

        public Question(int key1, int key2) {
            this.key1 = key1;
            this.key2 = key2;
        }

    }

}
