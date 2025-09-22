package baekjoon.gold;

import java.io.*;
import java.util.*;

/**
 * 가장 가까운 공통 조상
 */
public class B3584 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static Map<Integer, Node> nodeMap;

    public static void main(String[] args) throws IOException {
        int runCount = Integer.parseInt(in.readLine());

        for (int i = 0; i < runCount; i++) {
            run();
        }

        out.close();
    }

    private static void run() throws IOException {
        int nodeAmount = Integer.parseInt(in.readLine());
        nodeMap = new HashMap<>((int) (nodeAmount * 0.76));

        for (int i = 1; i <= nodeAmount; i++) {
            nodeMap.put(i, new Node(i));
        }

        for (int i = 0; i < nodeAmount - 1; i++) {
            StringTokenizer nodeInput = new StringTokenizer(in.readLine(), " ");

            Node parent = getNodeFromInput(nodeInput);
            Node child = getNodeFromInput(nodeInput);

            child.parent = parent;
        }

        StringTokenizer questionInputs = new StringTokenizer(in.readLine(), " ");
        Node nodeA = getNodeFromInput(questionInputs);
        Node nodeB = getNodeFromInput(questionInputs);

        printNearestCommonAncestor(nodeA, nodeB);
    }

    private static Node getNodeFromInput(StringTokenizer input) {
        int key = Integer.parseInt(input.nextToken());

        return nodeMap.get(key);
    }

    private static void printNearestCommonAncestor(Node nodeA, Node nodeB) throws IOException {
        Node commonAncestor;

        Set<Node> parentsOfNodeA = new HashSet<>((int) (nodeMap.size() * 0.76));
        Node parentPointerA = nodeA;
        while (parentPointerA != null) {
            parentsOfNodeA.add(parentPointerA);
            parentPointerA = parentPointerA.parent;
        }

        Node parentPointerB = nodeB;
        while (true) {
            if (parentsOfNodeA.contains(parentPointerB)) {
                commonAncestor = parentPointerB;
                break;
            }

            parentPointerB = parentPointerB.parent;
        }

        out.write(commonAncestor.key + System.lineSeparator());
    }

    private static class Node {

        Node parent = null;
        final int key;

        public Node(int key) {
            this.key = key;
        }

    }

}
