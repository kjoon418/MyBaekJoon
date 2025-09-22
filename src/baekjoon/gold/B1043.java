package baekjoon.gold;

import java.io.*;
import java.util.*;

public class B1043 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final HashMap<Integer, Set<Integer>> parties = new HashMap<>();

    private static final Set<Integer> truePeople = new HashSet<>();

    private static int result;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        String[] amountInput = in.readLine().split(" ");
        int partyAmount = Integer.parseInt(amountInput[1]);

        // 진실을 아는 사람들 저장
        String[] truePeopleInput = in.readLine().split(" ");
        for (int i = 1; i < truePeopleInput.length; i++) {
            int person = Integer.parseInt(truePeopleInput[i]);
            truePeople.add(person);
        }

        // 각 파티에 대한 정보 저장
        for (int i = 0; i < partyAmount; i++) {
            String[] partyInput = in.readLine().split(" ");

            HashSet<Integer> people = new HashSet<>();
            for (int j = 1; j < partyInput.length; j++) {
                int person = Integer.parseInt(partyInput[j]);
                people.add(person);
            }

            parties.put(i, people);
        }
    }

    private static void controller() throws IOException {
        while(true) {
            int size = parties.size();
            List<Integer> removeList = new ArrayList<>();
            for (Map.Entry<Integer, Set<Integer>> entry : parties.entrySet()) {
                int partyId = entry.getKey();
                Set<Integer> people = entry.getValue();

                if (isKnowTrue(people)) {
                    addTruePeople(people);
                    removeList.add(partyId);
                }
            }

            for (Integer removeId : removeList) {
                parties.remove(removeId);
            }

            // 파티 리스트의 수가 변경되지 않았다면 반복 종료
            if (size == parties.size()) {
                break;
            }
        }

        result = parties.size();
    }

    private static void printer() throws IOException {
        out.write(result+System.lineSeparator());

        out.close();
    }

    /**
     * 해당 셋에 진실을 아는 사람이 있는지 검사함
     */
    private static boolean isKnowTrue(Set<Integer> people) {
        for (int person : people) {
            if (truePeople.contains(person)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 셋에 있는 사람들을 모두 진실 리스트에 추가함
     */
    private static void addTruePeople(Set<Integer> people) {
        truePeople.addAll(people);
    }
}
