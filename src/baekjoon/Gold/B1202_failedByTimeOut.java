package baekjoon.Gold;

import java.io.*;
import java.util.*;

public class B1202_failedByTimeOut {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static TreeSet<Jewelry> jewelries = new TreeSet<>();
    private static int[] bags;

    private static int id = 0;
    private static long result = 0;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        StringTokenizer quantity = new StringTokenizer(in.readLine(), " ");
        int jewelryQuantity = Integer.parseInt(quantity.nextToken());
        int bagQuantity = Integer.parseInt(quantity.nextToken());
        bags = new int[bagQuantity];

        for (int i = 0; i < jewelryQuantity; i++) {
            StringTokenizer jewelryInfo = new StringTokenizer(in.readLine(), " ");
            int weight = Integer.parseInt(jewelryInfo.nextToken());
            int value = Integer.parseInt(jewelryInfo.nextToken());
            jewelries.add(new Jewelry(weight, value));
        }

        int maximum = 0;
        for (int i = 0; i < bagQuantity; i++) {
            int bag = Integer.parseInt(in.readLine());
            bags[i] = bag;
            maximum = Math.max(maximum, bag);
        }

        RadixSorter.decimalAscendSort(bags, maximum);
    }

    private static void controller() throws IOException {
        for (int i = 0; i < bags.length; i++) {
            Jewelry removeJewelry = null;
            for (Jewelry jewelry : jewelries) {
                if (jewelry.weight <= bags[i]) {
                    result += jewelry.value;
                    removeJewelry = jewelry;
                    break;
                }
            }

            if (removeJewelry != null) {
                jewelries.remove(removeJewelry);
            }
        }
    }

    private static void printer() throws IOException {
        out.write(Long.toString(result));

        out.close();
    }

    private static int getId() {
        return id++;
    }

    private static class RadixSorter {

        /**
         * 십진수 숫자형으로 구성된 배열을 오름차순으로 정렬하는 메서드
         */
        public static void decimalAscendSort(int[] array, int maxValue) {
            int size = array.length;
            List<Integer>[] lists = new List[10];
            for (int i = 0; i < lists.length; i++) {
                lists[i] = new ArrayList<>(size / 10);
            }

            // 낮은 자리수부터 최고 자리수까지 반복해서 정렬함
            for (int i = 1; i <= maxValue; i *= 10) {
                // 리스트 초기화
                for (int j = 0; j < lists.length; j++) {
                    lists[j].clear();
                }

                // 해당하는 자리수에 맞는 리스트에 값을 저장함
                for (int value : array) {
                    lists[(value % (i*10)) / i].add(value);
                }

                // 앞 리스트부터 새로운 배열에 저장함
                int index = 0;
                for (List<Integer> list : lists) {
                    for (Integer value : list) {
                        array[index] = value;
                        index++;
                    }
                }
            }
        }
    }

    private static class Jewelry implements Comparable<Jewelry> {

        private final int weight, value, id;

        public Jewelry(int weight, int value) {
            this.weight = weight;
            this.value = value;
            this.id = getId();
        }

        @Override
        public int compareTo(Jewelry o) {
            if (this.value != o.value) {
                return o.value - this.value;
            }
            if (this.weight != o.weight) {
                return o.weight - this.weight;
            }

            return this.id - o.id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Jewelry jewelry = (Jewelry) o;
            return id == jewelry.id;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }
}
