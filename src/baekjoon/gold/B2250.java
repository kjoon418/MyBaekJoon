package baekjoon.gold;

import java.io.*;
import java.util.*;

/**
 * 트리의 높이와 너비
 */
public class B2250 {

    private static final int UNDEFINED = -1;

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int nodeAmount = Integer.parseInt(in.readLine());

        Tree tree = new Tree();
        for (int i = 0; i < nodeAmount; i++) {
            StringTokenizer nodeInput = new StringTokenizer(in.readLine(), " ");
            int parentKey = Integer.parseInt(nodeInput.nextToken());
            int leftChildKey = Integer.parseInt(nodeInput.nextToken());
            int rightChildKey = Integer.parseInt(nodeInput.nextToken());

            tree.addNode(parentKey, leftChildKey, rightChildKey);
        }

        tree.findAndSetRoot();
        tree.calculatePosition();

        int maxWidth = tree.getMaxWidth();
        int level = tree.getLevelOfWidth(maxWidth);

        out.write(level + " " + maxWidth);
        out.close();
    }

    private static class Tree {

        Node root = null;
        Map<Integer, Node> nodeMap = new HashMap<>();
        List<Integer> minPositionsByLevel = new ArrayList<>();
        List<Integer> maxPositionsByLevel = new ArrayList<>();

        public void addNode(int parentKey, int leftChildKey, int rightChildKey) {
            Node parent = getNodeByKey(parentKey);
            Node leftChild = getNodeByKey(leftChildKey);
            Node rightChild = getNodeByKey(rightChildKey);

            parent.leftChild = leftChild;
            parent.rightChild = rightChild;

            if (leftChild != null) {
                leftChild.parent = parent;
            }
            if (rightChild != null) {
                rightChild.parent = parent;
            }
        }

        private Node getNodeByKey(int key) {
            if (key < 0) {
                return null;
            }

            if (nodeMap.containsKey(key)) {
                return nodeMap.get(key);
            }

            Node node = new Node(key);
            nodeMap.put(key, node);

            return node;
        }

        public void findAndSetRoot() {
            for (Node node : nodeMap.values()) {
                if (node.parent == null) {
                    root = node;
                    return;
                }
            }
        }

        public void calculatePosition() {
            Node leftMostNode = findLeftMostNode();
            leftMostNode.position = 1;

            recurseToChild(leftMostNode.rightChild, ChildStatus.RIGHT_CHILD);
            recurseToParent(leftMostNode.parent);
        }

        private void recurseToChild(Node node, ChildStatus childStatus) {
            if (node == null) {
                return;
            }

            Node parent = node.parent;

            switch (childStatus) {
                case LEFT_CHILD -> {
                    node.position = parent.position - (node.getRightSize() + 1);
                }
                case RIGHT_CHILD -> {
                    node.position = parent.position + node.getLeftSize() + 1;
                }
            }

            recurseToChild(node.leftChild, ChildStatus.LEFT_CHILD);
            recurseToChild(node.rightChild, ChildStatus.RIGHT_CHILD);
        }

        private void recurseToParent(Node node) {
            if (node == null) {
                return;
            }

            Node leftChild = node.leftChild;
            node.position = leftChild.position + leftChild.getRightSize() + 1;

            recurseToChild(node.rightChild, ChildStatus.RIGHT_CHILD);
            recurseToParent(node.parent);
        }

        private Node findLeftMostNode() {
            Node node = root;
            while (node.leftChild != null) {
                node = node.leftChild;
            }

            return node;
        }

        public int getMaxWidth() {
            collectPositions();

            int maxWidth = Integer.MIN_VALUE;
            for (int indexOfLevel = 0; indexOfLevel < minPositionsByLevel.size(); indexOfLevel++) {
                maxWidth = Math.max(maxWidth, calculateWidth(indexOfLevel));
            }

            return maxWidth;
        }

        private void collectPositions() {
            root.level = 1;
            ArrayDeque<Node> nodes = new ArrayDeque<>();
            nodes.push(root);

            while (!nodes.isEmpty()) {
                Node node = nodes.pollFirst();
                if (node.parent != null) {
                    node.level = node.parent.level + 1;
                }

                updateMaxPosition(node.level, node.position);
                updateMinPosition(node.level, node.position);

                if (node.leftChild != null) {
                    nodes.push(node.leftChild);
                }
                if (node.rightChild != null) {
                    nodes.push(node.rightChild);
                }
            }
        }

        private int calculateWidth(int indexOfLevel) {
            Integer min = minPositionsByLevel.get(indexOfLevel);
            Integer max = maxPositionsByLevel.get(indexOfLevel);

            return max - min + 1;
        }

        public int getLevelOfWidth(int width) {
            for (int indexOfLevel = 0; indexOfLevel < minPositionsByLevel.size(); indexOfLevel++) {
                if(calculateWidth(indexOfLevel) == width) {
                    return indexOfLevel + 1;
                }
            }

            return -1;
        }

        private void updateMaxPosition(int level, int position) {
            int indexOfLevel = level - 1;

            if (maxPositionsByLevel.size() == indexOfLevel) {
                maxPositionsByLevel.add(position);
            } else {
                int max = Math.max(maxPositionsByLevel.get(indexOfLevel), position);
                maxPositionsByLevel.set(indexOfLevel, max);
            }
        }

        private void updateMinPosition(int level, int position) {
            int indexOfLevel = level - 1;

            if (minPositionsByLevel.size() == indexOfLevel) {
                minPositionsByLevel.add(position);
            } else {
                int min = Math.min(minPositionsByLevel.get(indexOfLevel), position);
                minPositionsByLevel.set(indexOfLevel, min);
            }
        }

        private static class Node {

            final int key;
            Integer position = null;
            Node parent = null;
            Node leftChild = null;
            Node rightChild = null;
            Integer sizeOfLeftChild = null;
            Integer sizeOfRightChild = null;
            Integer level = null;

            public Node(int key) {
                this.key = key;
            }

            public int getTotalSize() {
                return getRightSize() + getLeftSize() + 1;
            }

            public int getLeftSize() {
                if (sizeOfLeftChild == null) {
                    if (leftChild == null) {
                        return 0;
                    }

                    sizeOfLeftChild = leftChild.getTotalSize();
                }

                return sizeOfLeftChild;
            }

            public int getRightSize() {
                if (sizeOfRightChild == null) {
                    if (rightChild == null) {
                        return 0;
                    }

                    sizeOfRightChild = rightChild.getTotalSize();
                }

                return sizeOfRightChild;
            }

        }

        private enum ChildStatus {
            LEFT_CHILD, RIGHT_CHILD
        }

    }

}

