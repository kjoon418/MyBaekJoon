package Gold;

import java.io.*;
import java.util.*;

public class B7662 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int testCase;

    private static final StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        testCase = Integer.parseInt(in.readLine());
    }

    private static void controller() throws IOException {
        for (int i = 0; i < testCase; i++) {
            PriorityQueue queue = new PriorityQueue();

            int loop = Integer.parseInt(in.readLine());

            for (int j = 0; j < loop; j++) {
                StringTokenizer st = new StringTokenizer(in.readLine(), " ");
                String order = st.nextToken();
                long value = Long.parseLong(st.nextToken());

                if (order.equals("I")) {
                    queue.insert(value);
                } else if (value == 1) {
                    queue.removeMost();
                } else {
                    queue.removeLeast();
                }
            }

            result.append(queue).append(System.lineSeparator());
        }
    }

    private static void printer() throws IOException {
        out.write(result.toString());

        out.close();
    }

    private static class PriorityQueue {
        // 해당 숫자와, 해당 숫자를 몇 개 저장하고 있는지를 저장함
        TreeMap<Long, Long> items = new TreeMap<>();
        long maxPriority = Long.MIN_VALUE;
        long minPriority = Long.MAX_VALUE;

        /**
         * 큐에 데이터를 삽입하는 메서드
         */
        public void insert(long item) {
            if (items.containsKey(item)) {
                items.put(item, items.get(item) + 1);
            } else {
                if (item > maxPriority) {
                    maxPriority = item;
                }
                if (item < minPriority) {
                    minPriority = item;
                }
                items.put(item, 1L);
            }
        }

        /**
         * 우선순위가 가장 높은 데이터를 삭제하는 메서드
         * 해당 데이터를 2개 이상 가지고 있었다면 개수를 1개 줄임
         * 해당 데이터를 1개 이하 가지고 있다면, 삭제하고 우선순위 최대값을 재조정함
         */
        public void removeMost() {
            if (items.isEmpty()) {
                return;
            }

            long amount = items.get(maxPriority);
            if (amount > 1) {
                items.put(maxPriority, items.get(maxPriority) - 1);
            } else {
                items.remove(maxPriority);
                if (items.isEmpty()) {
                    maxPriority = Long.MIN_VALUE;
                    minPriority = Long.MAX_VALUE;
                    return;
                }
                maxPriority = items.lastKey();
            }
        }

        /**
         * 우선순위가 가장 낮은 데이터를 삭제하는 메서드
         * 해당 데이터를 2개 이상 가지고 있었다면 개수를 1개 줄임
         * 해당 데이터를 1개 이하 가지고 있다면, 삭제하고 우선순위 최소값을 재조정함
         */
        public void removeLeast() {
            if (items.isEmpty()) {
                return;
            }

            Long amount = items.get(minPriority);
            if (amount > 1) {
                items.put(minPriority, items.get(minPriority) - 1);
            } else {
                items.remove(minPriority);
                if (items.isEmpty()) {
                    minPriority = Long.MAX_VALUE;
                    maxPriority = Long.MIN_VALUE;
                    return;
                }
                minPriority = items.firstKey();
            }
        }

        @Override
        public String toString() {
            if (items.isEmpty()) {
                return "EMPTY";
            }

            return maxPriority + " " + minPriority;
        }
    }


}
