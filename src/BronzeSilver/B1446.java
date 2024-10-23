package BronzeSilver;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class B1446 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final Set<Shortcut> shortcuts = new HashSet<>();

    private static int roadLength;

    private static int minimumLength = Integer.MAX_VALUE;


    public static void main(String[] args) throws IOException {
        init();

        findMinimum();

        out.write(minimumLength+"");
        out.close();
    }

    private static void findMinimum() {
        // 지름길이 아예 없다면 바로 종료
        if (shortcuts.isEmpty()) {
            minimumLength = roadLength;
            return;
        }

        // 각 지름길을 탈 때를 계산함
        for (Shortcut shortcut : shortcuts) {
            int startLength = shortcut.start;
            int result = startLength + calculateLength(shortcut);
            if (result < minimumLength) {
                minimumLength = result;
            }
        }
    }

    // 해당 지름길을 사용하면, 숏컷을 탈 때 부터 목적지까지 최소 얼마가 걸리는지 계산
    private static int calculateLength(Shortcut shortcut) {
        int length = shortcut.length;
        int start = shortcut.end;

        // 추가 숏컷 없이 바로 목적지까지 가는 것을 일단 최소치로 잡음
        int minimum = length + (roadLength - start);

        // 해당 지름길을 나온 후 탈 수 있는 지름길을 찾음
        List<Shortcut> availableShortcuts = shortcuts.stream()
                .filter(sc -> sc.start >= start)
                .collect(Collectors.toList());


        // 탈 수 있는 지름길이 있다면, 각각 계산해서 최소치만 반환함
        // 탈 수 있는 지름길이 더 없다면 계산한 최소치를 바로 반환하게 됨
        for (Shortcut availableShortcut : availableShortcuts) {
            int gap = availableShortcut.start - start;
            int result = length + gap + calculateLength(availableShortcut);
            if (result < minimum) {
                minimum = result;
            }
        }

        return minimum;
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        int roof = Integer.parseInt(input[0]);
        roadLength = Integer.parseInt(input[1]);

        for (int i = 0; i < roof; i++) {
            String[] str = in.readLine().split(" ");
            int start = Integer.parseInt(str[0]);
            int end = Integer.parseInt(str[1]);
            int length = Integer.parseInt(str[2]);

            // 지름길을 타지 않는게 빠를 경우 저장하지 않음
            if (end - start < length) {
                continue;
            }

            // 목적지보다 멀리 보낼 경우 저장하지 않음
            if (end > roadLength) {
                continue;
            }

            // 숏컷을 저장함(기존에 똑같은 출발/도착 숏컷이 있었다면 더 짧은 것 하나만 남김)
            Shortcut newShortcut = new Shortcut(start, end, length);
            if (shortcuts.contains(newShortcut)) {
                Shortcut existShortcut = shortcuts.stream()
                        .filter((shortcut -> shortcut.equals(newShortcut)))
                        .findFirst()
                        .get();

                if (existShortcut.length > newShortcut.length) {
                    shortcuts.remove(existShortcut);
                }
            }
            shortcuts.add(newShortcut);
        }
    }

}

class Shortcut implements Comparable<Shortcut> {

    int start, end;
    int length;

    public Shortcut(int start, int end, int length) {
        this.start = start;
        this.end = end;
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shortcut shortcut = (Shortcut) o;
        return start == shortcut.start && end == shortcut.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public int compareTo(Shortcut o) {
        return this.start - o.start;
    }

    @Override
    public String toString() {
        return "Shortcut = [" + this.start + ", " + this.end + ", " + this.length + "]";
    }
}