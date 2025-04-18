package baekjoon.Gold;

import java.io.*;
import java.util.*;

/**
 * 사과나무
 */
public class B2233 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static Map<Integer, Apple> indexAppleMap;

    public static void main(String[] args) throws IOException {
        int appleAmount = Integer.parseInt(in.readLine());
        indexAppleMap = new HashMap<>((int) (appleAmount * 0.76));

        String[] bugArray = in.readLine().split("");
        Apple root = buildAppleTree(bugArray);
        recordApplesToMaps(root);

        StringTokenizer badAppleInput = new StringTokenizer(in.readLine(), " ");

        Apple badApple1 = indexAppleMap.get(Integer.parseInt(badAppleInput.nextToken()) - 1);
        Apple badApple2 = indexAppleMap.get(Integer.parseInt(badAppleInput.nextToken()) - 1);

        Apple nearestAncestor = findNearestAncestor(badApple1, badApple2, appleAmount);

        out.write((nearestAncestor.zeroIndex + 1) + " " + (nearestAncestor.oneIndex + 1));
        out.close();
    }

    private static Apple buildAppleTree(String[] bugArray) throws IOException {
        Apple root = new Apple();
        Apple currentApple = root;
        currentApple.zeroIndex = 0;

        for (int i = 1; i < bugArray.length; i++) {
            int bugNumber = Integer.parseInt(bugArray[i]);

            // 숫자가 0이라면 새로운 노드를 방문(생성)함
            if (bugNumber == 0) {
                Apple child = new Apple();
                child.parent = currentApple;
                child.zeroIndex = i;

                currentApple.children.add(child);
                currentApple = child;
            } else {
                // 숫자가 1이라면 기존 노드(부모)로 복귀함
                currentApple.oneIndex = i;
                currentApple = currentApple.parent;
            }
        }

        return root;
    }

    private static Apple findNearestAncestor(Apple appleA, Apple appleB, int appleAmount) {
        if (appleA == null) {
            throw new IllegalStateException();
        }
        if (appleB == null) {
            throw new IllegalArgumentException();
        }

        Set<Apple> parentsOfAppleA = new HashSet<>((int) (appleAmount * 0.76));
        Apple parentPointA = appleA;

        while (parentPointA != null) {
            parentsOfAppleA.add(parentPointA);
            parentPointA = parentPointA.parent;
        }

        Apple parentPointB = appleB;
        while (true) {
            if (parentsOfAppleA.contains(parentPointB)) {
                return parentPointB;
            }

            parentPointB = parentPointB.parent;
        }
    }

    private static void recordApplesToMaps(Apple apple) {
        indexAppleMap.put(apple.zeroIndex, apple);
        indexAppleMap.put(apple.oneIndex, apple);

        for (Apple child : apple.children) {
            recordApplesToMaps(child);
        }
    }

    private static class Apple {

        Apple parent = null;
        List<Apple> children = new ArrayList<>();
        int zeroIndex;
        int oneIndex;

    }

}
