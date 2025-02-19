package baekjoon.Gold;

import java.io.*;
import java.util.*;

public class B1967 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static Node root;

    private static HashMap<Integer, Node> nodes = new HashMap<>();

    private static final TreeSet<Integer> result = new TreeSet<>();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        int nodeAmount = Integer.parseInt(in.readLine());
        for (int i = 0; i < nodeAmount - 1; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine(), " ");
            int parentNumber = Integer.parseInt(st.nextToken());
            int childNumber = Integer.parseInt(st.nextToken());
            int lengthToParent = Integer.parseInt(st.nextToken());

            Node child;
            if (nodes.containsKey(childNumber)) {
                child = nodes.get(childNumber);
            } else {
                child = new Node(childNumber);
                nodes.put(childNumber, child);
            }
            child.setLengthToParent(lengthToParent);

            Node parent;
            if (nodes.containsKey(parentNumber)) {
               parent = nodes.get(parentNumber);
            } else {
                parent = new Node(parentNumber);
                nodes.put(parentNumber, parent);
            }
            parent.addChild(child);
        }
    }

    private static void controller() throws IOException {
        // 확인해본 결과, 입력 노드가 1 하나라서 아무 것도 입력되지 않는 케이스가 있었음
        if (nodes.containsKey(1)) {
            root = nodes.get(1);
            root.findMaxDiameter(result);
        }
    }

    private static void printer() throws IOException {
        if (result.isEmpty()) {
            out.write("0");
        } else {
            out.write(result.last()+"");
        }

        out.close();
    }

    private static class Node {
        int value;
        Set<Node> children = new HashSet<>();
        List<Integer> lengths = new ArrayList<>();
        int diameter = 0;
        private int maxLength = 0;
        private int lengthToParent = 0;

        public Node(int value) {
            this.value = value;
        }

        public void addChild(Node child) {
            children.add(child);
        }

        /**
         * 부모까지의 최대 길이를 반환함
         */
        public int getMaxLength() {
            // 이미 계산한 적이 있다면 바로 반환함
            if (this.maxLength > 0) {
                return this.maxLength;
            }
            if (children.isEmpty()) {
                this.maxLength = lengthToParent;
                return this.maxLength;
            }

            int max = Integer.MIN_VALUE;
            for (Node child : children) {
                int childMaxLength = child.getMaxLength();
                if (childMaxLength > max) {
                    max = childMaxLength;
                }
            }
            maxLength = max+lengthToParent;

            return maxLength;
        }

        public int getDiameter() {
            if (this.diameter > 0) {
                return this.diameter;
            }
            if (children.isEmpty()) {
                this.diameter = 0;
                return this.diameter;
            }
            if (children.size() == 1) {
                this.diameter = children.stream()
                        .findFirst()
                        .get()
                        .getMaxLength();
                return this.diameter;
            }

            int max1 = Integer.MIN_VALUE;
            int max2 = Integer.MIN_VALUE;
            for (Node child : children) {
                int length = child.getMaxLength();
                if (max1 > max2) {
                    max2 = Math.max(max2, length);
                } else {
                    max1 = Math.max(max1, length);
                }
            }
            this.diameter = max1+max2;
            return this.diameter;
        }

        public void setLengthToParent(int lengthToParent) {
            this.lengthToParent = lengthToParent;
        }

        public void findMaxDiameter(Set<Integer> set) {
            set.add(getDiameter());
            for (Node child : children) {
                child.findMaxDiameter(set);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value == node.value;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }
    }

}
