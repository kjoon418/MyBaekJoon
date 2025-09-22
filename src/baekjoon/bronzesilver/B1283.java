package baekjoon.bronzesilver;

import java.io.*;
import java.util.HashSet;

public class B1283 {

    private final static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private final static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    private static String[] options;
    private static HashSet<Character> keys = new HashSet<Character>();

    public static void main(String[] args) throws IOException {
        int count = Integer.parseInt(in.readLine());
        options = new String[count];

        initOptions(count);

        for (String option : options) {
            out.write(findKey(option)+System.lineSeparator());
        }
        out.close();
    }

    // options를 초기화
    private static void initOptions(int count) throws IOException {
        for (int i = 0; i < count; i++) {
            options[i] = in.readLine();
        }
    }

    // 변환한 String을 반환하고, 단축키를 등록함
    private static String findKey(String option) {

        // 단어의 첫글자를 단축키로 삼을 경우 사용함
        StringBuilder wordOption = new StringBuilder();
        // 앞에서부터 가능한 첫번째 글자를 단축키로 삼을 경우 사용함
        StringBuilder frontOption = new StringBuilder();

        // option을 단어별로 나눔
        String[] splitOption = option.split(" ");
        boolean isFindByWord = false;
        for (String word : splitOption) {
            char c = Character.toLowerCase(word.charAt(0));
            // 단어의 첫 글자중 단축키로 지정 가능한 것이 있는지 찾음
            if (!keys.contains(c) && !isFindByWord) {
                isFindByWord = true;
                registerKey(c);
                char[] charArray = word.toCharArray();
                wordOption.append("["+charArray[0]+"]");
                for (int j = 1; j < charArray.length; j++) {
                    wordOption.append(charArray[j]);
                }
                wordOption.append(' ');
                registerKey(c);
                continue;
            }
            wordOption.append(word+" ");
        }

        if (isFindByWord) {
            wordOption.delete(wordOption.length()-1, wordOption.length());
            return wordOption.toString();
        }

        // 단어의 앞에서부터 key로 지정할 수 있는 단축키를 찾음
        char[] charArray = option.toCharArray();
        boolean isFindByChar = false;
        for (char c : charArray) {
            if (c == ' ') {
                frontOption.append(' ');
                continue;
            }

            char lowC = Character.toLowerCase(c);
            if (!keys.contains(lowC) && !isFindByChar) {
                isFindByChar = true;
                registerKey(lowC);
                frontOption.append("["+c+"]");
                continue;
            }
            frontOption.append(c);
        }
        return frontOption.toString();
    }

    // 단축키를 등록하는 메서드
    private static void registerKey(Character key) {
        if (key == null) return;
        keys.add(Character.toLowerCase(key));
    }
}
