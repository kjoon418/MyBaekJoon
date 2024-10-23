package Gold;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class B5430 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int loop;

    public static void main(String[] args) throws IOException {
        init();

        for (int i = 0; i < loop; i++) {
            controller();
        }

        out.close();
    }

    private static void controller() throws IOException {
        try {
            Deque<Integer> numbers = new ArrayDeque<>();
            boolean isReversed = false;

            String commands = in.readLine();
            int amount = Integer.parseInt(in.readLine());
            String numberInput = in.readLine();
            String[] numberString = numberInput.substring(1, numberInput.length() - 1).split(",");

            // 입력으로 들어온 숫자를 큐에 넣음
            for (int i = 0; i < amount; i++) {
                numbers.offer(Integer.parseInt(numberString[i]));
            }

            // 명령어를 수행함
            for (int i = 0; i < commands.length(); i++) {
                char command = commands.charAt(i);

                // 명령이 버리기라면 버리기 수행
                if (command == 'D') {
                    delete(numbers, isReversed);
                    continue;
                }

                // 명령이 뒤집기라면 뒤집기 수행
                if (command == 'R') {
                    // 연속된 R의 개수를 셈
                    int rAmount = countReverse(commands, i);

                    // R이 짝수개라면 뒤집음
                    if (rAmount % 2 != 0) {
                        isReversed = !isReversed;
                    }

                    // 처리한 R만큼 인덱스를 증가시킴
                    i += rAmount - 1;
                }
            }

            // 결과 배열을 출력함
            StringBuilder sb = new StringBuilder();

            if (!numbers.isEmpty()) {
                List<Integer> printNumbers = new ArrayList<>();
                sb.append("[");

                if (isReversed) {
                    while (!numbers.isEmpty()) {
                        printNumbers.add(numbers.pollLast());
                    }
                } else {
                    while (!numbers.isEmpty()) {
                        printNumbers.add(numbers.pollFirst());
                    }
                }

                for (int i = 0; i < printNumbers.size(); i++) {
                    sb.append(printNumbers.get(i)+",");
                }

                sb.delete(sb.length() - 1, sb.length());
                sb.append("]");
            } else {
                sb.append("[]");
            }


            out.write(sb.toString()+System.lineSeparator());

        } catch(IllegalStateException e) {
            out.write("error"+System.lineSeparator());
        }
    }

    /**
     * 배열이 비어 있다면 IllegalStateException을 던짐
     */
    private static void delete(Deque<Integer> numbers, boolean isReversed) throws IllegalStateException {
        if (numbers.isEmpty()) {
            throw new IllegalStateException();
        }

        // 뒤집혔다면 배열의 뒤에서 수를 제거함
        if (isReversed) {
            numbers.pollLast();
            return;
        }
        numbers.pollFirst();
    }

    /**
     * 연속되어 있는 R 명령어를 한번에 처리할 수 있도록 개수를 세는 메서드
     * 처리한 R 명령어의 개수를 반환함
     */
    private static int countReverse(String command, int startIndex) {
        int count = 0;

        // 연속되어 있는 R 명령어를 계산함
        for (int i = startIndex; i < command.length(); i++) {
            if (command.charAt(i) != 'R') {
                break;
            }
            count++;
        }

        return count;
    }

    private static void init() throws IOException {
        loop = Integer.parseInt(in.readLine());
    }
    
}
