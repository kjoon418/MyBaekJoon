package baekjoon.gold;

import java.io.*;
import java.util.*;

public class B20303_failedByTimeout {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int limitKidsAmount;
    private static Kid[] kids;
    private static final Map<Integer, Group> groups = new HashMap<>();
    private static int groupId = 0;
    private static int maxResult = 0;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        StringTokenizer amountInput = new StringTokenizer(in.readLine(), " ");
        int kidsAmount = Integer.parseInt(amountInput.nextToken());
        int friendshipAmount = Integer.parseInt(amountInput.nextToken());
        limitKidsAmount = Integer.parseInt(amountInput.nextToken());

        kids = new Kid[kidsAmount];
        StringTokenizer candyInput = new StringTokenizer(in.readLine(), " ");
        for (int i = 0; i < kidsAmount; i++) {
            kids[i] = new Kid(i, Integer.parseInt(candyInput.nextToken()));
        }

        for (int i = 0; i < friendshipAmount; i++) {
            StringTokenizer friendshipInput = new StringTokenizer(in.readLine()," ");

            Kid kid1 = kids[Integer.parseInt(friendshipInput.nextToken()) - 1];
            Kid kid2 = kids[Integer.parseInt(friendshipInput.nextToken()) - 1];

            if (kid1.hasFriend) {
                // 둘 다 친구가 있었다면, 기존의 그룹을 합쳐야 함
                if (kid2.hasFriend) {
                    Group group1 = groups.get(kid1.groupId);
                    Group group2 = groups.get(kid2.groupId);
                    group1.joinGroups(group2);

                    if (group1 != group2) {
                        groups.remove(group2.groupId);
                    }

//                    printGroup();
                    continue;
                }

                // kid1만 그룹이 있었으므로, kid2를 그룹에 합류시킴
                Group group = groups.get(kid1.groupId);
                kid2.setGroup(group);

//                printGroup();
                continue;
            }
            // kid1은 그룹이 없고 kid2만 그룹이 있는 상황 -> kid1을 그룹에 합류시킴
            if (kid2.hasFriend) {
                Group group = groups.get(kid2.groupId);
                kid1.setGroup(group);

//                printGroup();
                continue;
            }
            // 둘 다 그룹이 없는 상황 -> 새로운 그룹을 만듦
            int groupId = getGroupId();
            Group group = new Group(groupId);
            kid1.setGroup(group);
            kid2.setGroup(group);

            groups.put(groupId, group);
//            printGroup();
        }
    }

    private static void controller() throws IOException {
        List<Kid> noFriendKids = new ArrayList<>();
        for (Kid kid : kids) {
            if (!kid.hasFriend) {
                noFriendKids.add(kid);
            }
        }

        int[] candyInfo = new int[groups.size()+noFriendKids.size()];
        int[] kidInfo = new int[groups.size()+noFriendKids.size()];

        int index = 0;
        for (Group group : groups.values()) {
            candyInfo[index] = group.candyAmount;
            kidInfo[index] = group.getKidAmount();

            index++;
        }
        for (Kid kid : noFriendKids) {
            candyInfo[index] = kid.candyAmount;
            kidInfo[index] = 1;

            index++;
        }

        takeCandy(0, 0, 0, candyInfo, kidInfo);
    }

    /**
     * 모든 경우의 수를 계산하는 메서드
     */
    private static void takeCandy(int candyAmount, int kidAmount, int index, int[] candyInfo, int[] kidInfo) {
        if (kidAmount >= limitKidsAmount) {
            return;
        }

        if (index >= candyInfo.length) {
            maxResult = Math.max(maxResult, candyAmount);
            return;
        }

        maxResult = Math.max(maxResult, candyAmount);
        int nextIndex = index + 1;

        // case1: 현재 인덱스의 아이의 사탕을 빼앗는다
        int nextCandyAmount = candyAmount + candyInfo[index];
        int nextKidAmount = kidAmount + kidInfo[index];
        takeCandy(nextCandyAmount, nextKidAmount, nextIndex, candyInfo, kidInfo);

        // case2: 현재 인덱스의 아이의 사탕을 빼앗지 않는다
        takeCandy(candyAmount, kidAmount, nextIndex, candyInfo, kidInfo);
    }

    private static void printGroup() {
        for (Group group : groups.values()) {
            System.out.println("==============");
            System.out.println("size: " + group.getKidAmount());
            System.out.println("group.candyAmount = " + group.candyAmount);
            System.out.println("group.groupId = " + group.groupId);
        }
    }

    private static void printer() throws IOException {
        out.write(Integer.toString(maxResult));

        out.close();
    }

    private static int getGroupId() {
        return groupId++;
    }

    private static class Kid {
        int id;
        int candyAmount;
        int groupId;
        boolean hasFriend = false;

        public Kid(int id, int candyAmount) {
            this.id = id;
            this.candyAmount = candyAmount;
        }

        public void setGroup(Group group) {
            group.addKid(this);
            this.groupId = group.groupId;
            this.hasFriend = true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Kid kid = (Kid) o;
            return id == kid.id;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

    private static class Group {
        int groupId;
        int candyAmount;
        HashSet<Kid> kids = new HashSet<>();

        public Group(int groupId) {
            this.groupId = groupId;
        }

        public void addKid(Kid kid) {
            if (kids.contains(kid)) {
                return;
            }

            this.candyAmount += kid.candyAmount;
            kids.add(kid);
        }

        public void joinGroups(Group group) {
            for (Kid kid : group.kids) {
                kid.setGroup(this);
            }

            this.kids.addAll(group.kids);
        }

        public int getKidAmount() {
            return kids.size();
        }
    }
}
