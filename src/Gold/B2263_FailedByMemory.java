package Gold;

import java.io.*;
import java.util.*;

public class B2263_FailedByMemory {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final Map<Integer, Node> nodes = new HashMap<>();

    private static Node[] inorderNodes;

    private static Node[] postorderNodes;

    private static Node root;

    private static StringBuilder resultBuilder = new StringBuilder();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        int nodeAmount = Integer.parseInt(in.readLine());
        inorderNodes = new Node[nodeAmount];
        postorderNodes = new Node[nodeAmount];

        StringTokenizer inorder = new StringTokenizer(in.readLine(), " ");
        StringTokenizer postorder = new StringTokenizer(in.readLine(), " ");

        int inorderIndex = 0;
        while (inorder.hasMoreTokens()) {
            int value = Integer.parseInt(inorder.nextToken());
            Node node = new Node(inorderIndex, value);
            inorderNodes[inorderIndex] = node;
            nodes.put(value, node);

            inorderIndex++;
        }

        int postorderIndex = 0;
        while (postorder.hasMoreTokens()) {
            int value = Integer.parseInt(postorder.nextToken());
            Node node = nodes.get(value);
            postorderNodes[postorderIndex] = node;
            node.postorderIndex = postorderIndex;

            postorderIndex++;
        }
    }

    private static void controller() throws IOException {
        root = postorderNodes[postorderNodes.length - 1];

        setChild(root);

        Deque<Node> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        while(!nodeQueue.isEmpty()) {
            Node node = nodeQueue.poll();
            if (node.leftChild != null) {
                setChild(node.leftChild);
                nodeQueue.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                setChild(node.rightChild);
                nodeQueue.offer(node.rightChild);
            }
        }

        root.preorder(resultBuilder);
    }

    private static void printer() throws IOException {
        out.write(resultBuilder.toString());

        out.close();
    }

    private static void setChild(Node node) {
        Node parent = node.parent;
        if (parent == null) {
            node.startIndex = 0;
            node.endIndex = inorderNodes.length-1;
        }

        Node[] leftTree = new Node[node.inorderIndex - node.startIndex];
        Node[] rightTree = new Node[node.endIndex - node.inorderIndex];

        // 범위 안에서 본인보다 왼쪽에 있는 노드는 왼쪽 트리에 속함
        for (int i = node.startIndex; i < node.inorderIndex; i++) {
            leftTree[i - node.startIndex] = inorderNodes[i];
        }
        // 범위 안에서 본인보다 오른쪽에 있는 노드는 오른쪽 트리에 속함
        for (int i = node.inorderIndex+1; i <= node.endIndex; i++) {
            rightTree[i - node.inorderIndex - 1] = inorderNodes[i];
        }

        // 왼쪽 트리 중에 후위순회 기준으로 가장 뒤에 있는 노드가 내 왼쪽 자식 노드가 됨
        for (Node leftNode : leftTree) {
            if (node.leftChild == null) {
                node.leftChild = leftNode;
                continue;
            }
            if (node.leftChild.postorderIndex < leftNode.postorderIndex) {
                node.leftChild = leftNode;
            }
        }

        // 오른쪽 트리 중에 후위순회 기준으로 가장 뒤에 있는 노드가 내 오른쪽 자식 노드가 됨
        for (Node rightNode : rightTree) {
            if (node.rightChild == null) {
                node.rightChild = rightNode;
                continue;
            }
            if (node.rightChild.postorderIndex < rightNode.postorderIndex) {
                node.rightChild = rightNode;
            }
        }

        if (node.leftChild != null) {
            node.leftChild.setParent(node);
            node.leftChild.startIndex = node.startIndex;
            node.leftChild.endIndex = node.inorderIndex-1;
        }
        if (node.rightChild != null) {
            node.rightChild.setParent(node);
            node.rightChild.startIndex = node.inorderIndex+1;
            node.rightChild.endIndex = node.endIndex;
        }
    }

    private static class Node {
        int inorderIndex;
        int postorderIndex;
        int value;
        Node parent;
        Node leftChild, rightChild;
        int startIndex;
        int endIndex;

        public Node(int inorderIndex, int value) {
            this.inorderIndex = inorderIndex;
            this.value = value;
        }

        public void setPostorderIndex(int index) {
            this.postorderIndex = index;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public void preorder(StringBuilder sb) {
            sb.append(this.value).append(" ");
            if (leftChild != null) {
                leftChild.preorder(sb);
            }
            if (rightChild != null) {
                rightChild.preorder(sb);
            }
        }
    }
}
