package Gold;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class B1918 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static String inputString;
    private static String result;

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        inputString = in.readLine();
    }

    private static boolean isOperation(char word) {
        if (word == '+' ||
                word == '-' ||
                word == '*' ||
                word == '/' ||
                word == '(' ||
                word == ')'
        ) return true;

        return false;
    }

    private static int getPriority(char word) {
        if (word == '(') return 0;
        if (word == '+' || word == '-') return 1;
        if (word == '*' || word == '/') return 2;
        if (word == ')') return 3;

        return -1;
    }

    private static void controller() throws IOException {
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < inputString.length(); i++) {
            char word = inputString.charAt(i);
            if (isOperation(word)) {
                if (word == '(') {
                    stack.push(word);
                    continue;
                }
                if (word == ')') {
                    while (true) {
                        char pop = stack.pop();
                        if (pop == '(') {
                            break;
                        }
                        sb.append(pop);
                    }
                    continue;
                }

                // 스택이 비어 있으면 연산자를 추가함
                if (stack.isEmpty()) {
                    stack.push(word);
                    continue;
                }

                // 피연산자도 괄호도 아니므로 연산자에 해당함
                while(!stack.isEmpty()) {
                    // 자신의 우선순위 이상의 연산자면 pop해서 출력함
                    if (getPriority(stack.peek()) >= getPriority(word)) {
                        sb.append(stack.pop());
                        continue;
                    }
                    break;
                }
                // 자신이 우선순위가 더 높다면 스택에 저장함
                stack.push(word);
            } else {
                sb.append(word);
            }
        }

        // 스택의 남은 것을 모두 출력
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        result = sb.toString();
    }

    private static void printer() throws IOException {
        out.write(result);

        out.close();
    }


}
