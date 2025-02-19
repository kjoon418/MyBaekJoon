package baekjoon.BronzeSilver;

import java.io.*;
import java.util.*;

public class B11725 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final TreeMap<Integer, Node> nodes = new TreeMap<>();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        int loop = Integer.parseInt(in.readLine());
        for (int i = 1; i <= loop; i++) {
            nodes.put(i, new Node(i));
        }

        for (int i = 1; i < loop; i++) {
            String[] input = in.readLine().split(" ");
            int n1 = Integer.parseInt(input[0]);
            int n2 = Integer.parseInt(input[1]);
            Node node1 = nodes.get(n1);
            Node node2 = nodes.get(n2);
            node1.link(node2);
        }
    }

    private static void controller() throws IOException {
        Node root = nodes.get(1);
        for (Map.Entry<Integer, Node> entry : root.linkedNodes.entrySet()) {
            entry.getValue().setParentAndChild(1);
        }
    }

    private static void printer() throws IOException {
        for (int i = 2; i <= nodes.size(); i++) {
            Node node = nodes.get(i);
            out.write(node.getParentNumber()+System.lineSeparator());
        }

        out.close();
    }

    private static class Node {
        Node parent;
        Map<Integer, Node> children = new HashMap<>();
        int number;
        Map<Integer, Node> linkedNodes = new HashMap<>();

        public Node(int number) {
            this.number = number;
        }

        public void link(Node n) {
            this.linkedNodes.put(n.number, n);
            n.linkedNodes.put(this.number, this);
        }

        public int getParentNumber() {
            return parent.number;
        }

        public void addChild(Node node) {
            children.put(node.number, node);
        }

        public void setParentAndChild(int n) {
            for (Map.Entry<Integer, Node> entry : linkedNodes.entrySet()) {
                if (entry.getKey() == n) {
                    parent = entry.getValue();
                } else {
                    addChild(entry.getValue());
                }
            }

            linkedNodes.clear();

            for (Map.Entry<Integer, Node> entry : children.entrySet()) {
                entry.getValue().setParentAndChild(this.number);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return number == node.number;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(number);
        }
    }
}
