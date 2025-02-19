package baekjoon.BronzeSilver;

import java.io.*;
import java.util.*;

public class B1966 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int loop;

    private static final Deque<Paper> papers = new ArrayDeque<>();

    private static final int[] priorityCount = new int[10];

    public static void main(String[] args) throws IOException {
        init();

        for (int i = 0; i < loop; i++) {
            papers.clear();
            for (int j = 0; j < priorityCount.length; j++) {
                priorityCount[j] = 0;
            }

            String[] amountAndCurious = in.readLine().split(" ");
            int amount = Integer.parseInt(amountAndCurious[0]);
            int curiousPaper = Integer.parseInt(amountAndCurious[1]);

            // 큐에 차례대로 입력함
            String[] paperInput = in.readLine().split(" ");
            int maxPriority = 0;
            for (int j = 0; j < amount; j++) {
                int priority = Integer.parseInt(paperInput[j]);
                papers.offer(new Paper(j, priority));
                maxPriority = Math.max(priority, maxPriority);
                priorityCount[priority]++;
            }

            // 큐에 차례대로 꺼내면서, 내가 궁금한 문서가 몇번째로 나오는지 확인함
            int count = 0;
            while(!papers.isEmpty()) {
                Paper pollPaper = papers.poll();
                if (pollPaper.priority == maxPriority) {
                    count++;
                    if (pollPaper.name == curiousPaper) {
                        out.write(count+System.lineSeparator());
                        break;
                    }

                    // 큐 내에 모든 최우선순위 문서가 빠졌는지 확인함
                    if (--priorityCount[pollPaper.priority] <= 0) {
                        for (int j = 9; j > 0; j--) {
                            if (priorityCount[j] > 0) {
                                maxPriority = j;
                                break;
                            }
                        }
                    }
                } else {
                    papers.offer(pollPaper);
                }
            }
        }

        out.close();
    }

    private static void init() throws IOException {
        loop = Integer.parseInt(in.readLine());
    }

    static class Paper {
        int name;
        int priority;

        public Paper(int name, int primary) {
            this.name = name; this.priority = primary;
        }
    }
}
