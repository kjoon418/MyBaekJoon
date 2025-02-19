package baekjoon.BronzeSilver;

import java.io.*;
import java.util.*;

public class B1991 {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    private static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static Node root;

    private static Map<String, Node> nodes = new HashMap<>();

    public static void main(String[] args) throws IOException {
        init();

        root.preorder();
        out.write(System.lineSeparator());
        root.inorder();
        out.write(System.lineSeparator());
        root.postorder();

        out.close();
    }

    private static void init() throws IOException {
        int roof = Integer.parseInt(in.readLine());

        for (int i = 0; i < roof; i++) {
            String[] input = in.readLine().split(" ");
            String parentValue = input[0];
            String leftChildValue = input[1];
            String rightChildValue = input[2];

            Node parent;
            Node leftChild;
            Node rightChild;

            if (nodes.containsKey(parentValue)) {
                parent = nodes.get(parentValue);
            } else {
                parent = new Node(parentValue);
                nodes.put(parentValue, parent);
            }

            if (nodes.containsKey(leftChildValue)) {
                leftChild = nodes.get(leftChildValue);
            } else {
                leftChild = new Node(leftChildValue);
                if (!leftChildValue.equals(".")) {
                    nodes.put(leftChildValue, leftChild);
                }
            }

            if (nodes.containsKey(rightChildValue)) {
                rightChild = nodes.get(rightChildValue);
            } else {
                rightChild = new Node(rightChildValue);
                if (!rightChildValue.equals(".")) {
                    nodes.put(rightChildValue, rightChild);
                }
            }

            parent.setLeft(leftChild);
            parent.setRight(rightChild);
        }

        root = nodes.get("A");
    }

    private static class Node {
        private final String value;

        private Node left;
        private Node right;

        public Node(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setLeft(Node left) {
            if (left.getValue().equals(".")) {
                return;
            }

            this.left = left;
        }

        public void setRight(Node right) {
            if (right.getValue().equals(".")) {
                return;
            }

            this.right = right;
        }

        /**
         * 전위 순회 출력
         * NLR
         */
        public void preorder() throws IOException {
            out.write(this.value);
            if (left != null) {
                left.preorder();
            }
            if (right != null) {
                right.preorder();
            }
        }

        /**
         * 중위 순회 출력
         * LNR
         */
        public void inorder() throws IOException {
            if (left != null) {
                left.inorder();
            }
            out.write(this.value);
            if (right != null) {
                right.inorder();
            }
        }

        /**
         * 후위 순회 출력
         * LRN
         */
        public void postorder() throws IOException {
            if (left != null) {
                left.postorder();
            }
            if (right != null) {
                right.postorder();
            }
            out.write(this.value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }
    }
}

