package baekjoon.gold;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class B5639 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final List<Node> inputNodes = new ArrayList<>();

    private static Node root;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        while (true) {
            String input = in.readLine();
            if (input == null || input.isEmpty()) {
                break;
            }
            Node node = new Node(Integer.parseInt(input));
            inputNodes.add(node);
        }

        root = inputNodes.get(0);
    }

    private static void controller() throws IOException {
        if (root == null) {
            return;
        }

        for (Node node : inputNodes) {
            if (node == root) {
                continue;
            }
            linkNode(root, node);
        }

        postOrder(root);
    }

    private static void printer() throws IOException {
        out.close();
    }

    private static void linkNode(Node root, Node sub) {
        if (root.value > sub.value) {
            if (root.left == null) {
                root.left = sub;
            } else {
                linkNode(root.left, sub);
            }
        } else {
            if (root.right == null) {
                root.right = sub;
            } else {
                linkNode(root.right, sub);
            }
        }
    }

    private static void postOrder(Node root) throws IOException {
        if (root.left != null) {
            postOrder(root.left);
        }
        if (root.right != null) {
            postOrder(root.right);
        }
        out.write(root.value+System.lineSeparator());
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node (int value) {
            this.value = value;
        }
    }

}
