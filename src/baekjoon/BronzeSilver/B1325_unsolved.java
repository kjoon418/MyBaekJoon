package baekjoon.BronzeSilver;

import java.io.*;
import java.util.*;

public class B1325_unsolved {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    // <상위 컴퓨터, 하위 컴퓨터>
    private static HashMap<Integer, Set<Integer>> computers = new HashMap<>();
    // 하위 컴퓨터가 어떤 상위 컴퓨터로 들어갔는지를 저장하는 맵
    private static HashMap<Integer, Set<Integer>> movingLog = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // 초기화
        init();

        // 출력
        print();
    }

    private static void init() throws IOException {

        // 입력받음
        String[] numbers = in.readLine().split(" ");
        int amount = Integer.parseInt(numbers[1]);

        for (int i = 0; i < amount; i++) {
            String[] input = in.readLine().split(" ");
            int subComputer = Integer.parseInt(input[0]);
            int superComputer = Integer.parseInt(input[1]);

            // case1: 기존 상위 컴퓨터가(지금은 하위 컴퓨터), 새로운 상위 컴퓨터를 만남
            if (computers.containsKey(subComputer)) {
                // 기존 상위 컴퓨터의 정보를 삭제하고, 새로운 상위 컴퓨터에 편입시켜야 함
                Set<Integer> remove = computers.remove(subComputer);
                remove.add(subComputer);

                // 해당 상위 컴퓨터도 기존에 존재했는지 확인해야 함
                if (computers.containsKey(superComputer)) {
                    // 존재하면 기존 셋에 추가
                    computers.get(superComputer).addAll(remove);
                } else {
                    // 존재하지 않으면 새로운 셋 추가
                    computers.put(superComputer, remove);
                }

                // 이동 로그를 생성함
                addLog(subComputer, superComputer);
                continue;
            }
            // case2: 기존 상위 컴퓨터에 새로운 하위 컴퓨터가 추가됨
            if (computers.containsKey(superComputer)) {
                computers.get(superComputer).add(subComputer);
                // 이동 로그를 생성함
                addLog(subComputer, superComputer);
                continue;
            }
            // case3: 예전에 다른 곳으로 편입한 컴퓨터가 상위 컴퓨터로 입력됨
            if (movingLog.containsKey(superComputer)) {
                // 로그를 역순으로 거슬러 올라가 가장 최근에 편입한 곳을 찾아 추가함
                traceLogAndAdd(subComputer, superComputer);
                addLog(subComputer, superComputer);
                continue;
            }
            // case4: 아예 새로운 상위 컴퓨터 + 하위 컴퓨터가 입력됨
            HashSet<Integer> set = new HashSet<>();
            set.add(subComputer);
            computers.put(superComputer, set);
            addLog(subComputer, superComputer);
        }
    }

    // 로그를 추가하는 메서드
    private static void addLog(int subComputer, int superComputer) {
        if (movingLog.containsKey(subComputer)) {
            movingLog.get(subComputer).add(superComputer);
        } else {
            HashSet<Integer> set = new HashSet<>();
            set.add(superComputer);
            movingLog.put(subComputer, set);
        }
    }

    // 이전에 이미 편입한 컴퓨터가 상위 컴퓨터로 입력된 상황
    private static void traceLogAndAdd(int subComputer, int superComputer) {
        // 로그에 있다면 새로 찾아 재귀호출함
        if (movingLog.containsKey(superComputer)) {
            for (Integer i : movingLog.get(superComputer)) {
                traceLogAndAdd(subComputer, i);
            }
        } else { // 로그에 없다면 이곳이 최종 도착지임
            computers.get(superComputer).add(subComputer);
        }
    }

    private static void print() throws IOException {
        // 최대값을 구함
        long max = Long.MIN_VALUE;
        for (Set<Integer> set : computers.values()) {
            max = Math.max(max, set.size());
        }
        long getMax = max;

        // 최대값을 지닌 숫자들로 리스트를 구성함
        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Set<Integer>> entry : computers.entrySet()) {
            if (entry.getValue().size() == max) {
                result.add(entry.getKey());
            }
        }
        result.sort(null);

        StringBuilder sb = new StringBuilder();
        for (Integer i : result) {
            sb.append(i+" ");
        }
        sb.deleteCharAt(sb.length()-1);
        out.write(sb.toString());
        out.close();
    }


}
