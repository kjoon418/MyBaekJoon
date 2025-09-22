package baekjoon.bronzesilver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class B1932 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final TreeMap<Integer, List<Node>> triangle = new TreeMap<>();

    private static Node root;

    private static int result;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        int height = Integer.parseInt(in.readLine());
        for (int i = 0; i < height; i++) {
            triangle.put(i, new ArrayList<>());
        }

        // 루트 설정
        int rootValue = Integer.parseInt(in.readLine());
        root = new Node(rootValue);
        triangle.get(0).add(root);

        // 루트 이후 설정
        for (int i = 1; i < height; i++) {
            String[] input = in.readLine().split(" ");

            List<Node> upperNodes = triangle.get(i - 1);
            List<Node> thisNodes = triangle.get(i);

            // 맨 왼쪽 입력은, 부모가 하나뿐임
            int leftValue = Integer.parseInt(input[0]);
            Node leftNode = new Node(leftValue);
            upperNodes.get(0).setLeftChild(leftNode);
            thisNodes.add(leftNode);

            // 중간 노드들을 저장
            for (int j = 1; j < input.length - 1; j++) {
                int value = Integer.parseInt(input[j]);
                Node node = new Node(value);
                upperNodes.get(j - 1).setRightChild(node);
                upperNodes.get(j).setLeftChild(node);
                thisNodes.add(node);
            }

            // 맨 오른쪽 입력도, 부모가 하나뿐임
            int rightValue = Integer.parseInt(input[input.length - 1]);
            Node rightNode =new Node(rightValue);
            upperNodes.get(upperNodes.size() - 1).setRightChild(rightNode);
            thisNodes.add(rightNode);
        }
    }

    private static void controller() throws IOException {
        result = root.getTotalValue();
    }

    private static void printer() throws IOException {
        out.write(result+"");

        out.close();
    }

    private static class Node {
        int originalValue;
        Node left, right;
        int totalValue = -1;

        public Node(int value) {
            this.originalValue = value;
        }

        public void setLeftChild(Node n) {
            this.left = n;
        }

        public void setRightChild(Node n) {
            this.right = n;
        }

        public int getTotalValue() {
            if (left == null && right == null) {
                totalValue = originalValue;
                return totalValue;
            }

            int leftValue = 0;
            int rightValue = 0;

            if (left.totalValue > 0) {
                leftValue = left.totalValue;
            } else {
                leftValue = left.getTotalValue();
            }

            if (right.totalValue > 0) {
                rightValue = right.totalValue;
            } else {
                rightValue = right.getTotalValue();
            }

            this.totalValue = this.originalValue + Math.max(leftValue, rightValue);
            return totalValue;
        }
    }


}
