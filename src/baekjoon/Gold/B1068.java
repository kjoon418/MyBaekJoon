package baekjoon.Gold;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class B1068 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final HashMap<Integer, Node> nodes = new HashMap<>();

    private static Node root;
    private static Node removeNode;

    private static int result;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        int amount = Integer.parseInt(in.readLine());

        for (int i = 0; i < amount; i++) {
            nodes.put(i, new Node(-1));
        }

        String[] input = in.readLine().split(" ");
        for (int i = 0; i < input.length; i++) {
            int parentNumber = Integer.parseInt(input[i]);
            Node node = nodes.get(i);
            node.setValue(i);

            if (parentNumber >= 0) {
                Node parentNode = nodes.get(parentNumber);
                parentNode.addChild(node);
                node.setParent(parentNode);
            }
        }

        int removeNumber = Integer.parseInt(in.readLine());
        removeNode = nodes.get(removeNumber);
        root = getRoot();
    }

    private static Node getRoot() {
        for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            if (entry.getValue().parent == null) {
                return entry.getValue();
            }
        }

        return null;
    }

    private static void controller() throws IOException {
        if (root == null) {
            result = 0;
            return;
        }

        // 주어진 노드를 제거함
        Node parentNode = removeNode.parent;
        if (parentNode == null) {
            result = 0;
            return;
        }
        parentNode.removeChild(removeNode);

        // 리프 노드의 개수를 셈
        result = getLeafNodeAmount(root);
    }

    private static void printer() throws IOException {
        out.write(result+"");

        out.close();
    }

    private static int getLeafNodeAmount(Node node) {
        if (node.children.isEmpty()) {
            return 1;
        }

        int sum = 0;
        for (Map.Entry<Integer, Node> entry : node.children.entrySet()) {
            sum += getLeafNodeAmount(entry.getValue());
        }

        return sum;
    }

    private static class Node {
        int value;
        HashMap<Integer, Node> children = new HashMap<>();
        Node parent;

        public Node(int value) { this.value = value; }

        public void addChild(Node node) {
            children.put(node.value, node);
        }

        public void setParent(Node node) {
            this.parent = node;
        }

        public void removeChild(Node node) {
            children.remove(node.value);
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

}
