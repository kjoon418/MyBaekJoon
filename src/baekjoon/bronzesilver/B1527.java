package baekjoon.bronzesilver;

import java.io.*;

public class B1527 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    
    private static long start;
    private static long end;

    public static void main(String[] args) throws IOException {
        init();

        int count = controller();

        out.write(count+"");
        out.close();
    }

    /**
     * 초기화(입력) 담당 메서드
     */
    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        start = Long.parseLong(input[0]);
        end = Long.parseLong(input[1]);
    }

    /**
     * 범위 내에서 금민수를 찾고, 총 갯수를 반환함
     */
    private static int controller() {
        int count = 0;

        long number = findFirstNumber(start);
        while (number <= end) {
            count++;
            number = findNextNumber(number);
        }
        return count;
    }

    /**
     * 금민수에 해당하는 가장 낮은 숫자를 반환함
     */
    private static long findFirstNumber(long startNumber) {
        String startNumberString = Long.toString(startNumber);



        // case 1: 이미 금민수면 그대로 반환
        boolean isGoldMinsu = true;
        for (int i = 0; i < startNumberString.length(); i++) {
            int n = Character.getNumericValue(startNumberString.charAt(i));
            if (n == 4 || n == 7) {
                continue;
            } else {
                isGoldMinsu = false;
                break;
            }
        }
        if (isGoldMinsu) {
            return startNumber;
        }

        // 아래 검사를 위해 맨 앞자리를 추출
        int frontNumber = Character.getNumericValue(startNumberString.charAt(0));

        // case 2: 맨 앞자리가 0~3이라면 4444...4로 바꿈
        if (frontNumber < 4) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < startNumberString.length(); i++) {
                sb.append("4");
            }
            return Long.parseLong(sb.toString());
        }

        // case 3: 맨 앞자리가 5~6이라면 7444....4로 바꿈
        if (4 < frontNumber && frontNumber < 7) {
            StringBuilder sb = new StringBuilder();
            sb.append("7");
            for (int i = 1; i < startNumberString.length(); i++) {
                sb.append("4");
            }
            return Long.parseLong(sb.toString());
        }

        // case 4: 맨 앞자리가 8~9라면 한자리 추가해서 4444...4로 바꿈
        if (frontNumber > 7) {
            StringBuilder sb = new StringBuilder();
            sb.append("4");
            for (int i = 0; i < startNumberString.length(); i++) {
                sb.append("4");
            }
            return Long.parseLong(sb.toString());
        }

        // 만약 맨 앞자리가 4 혹은 7이라면 뒷 비트를 검사해야 함
        StringBuilder sb = new StringBuilder();
        sb.append(frontNumber);
        for (int i = 1; i < startNumberString.length(); i++) {
            int number = Character.getNumericValue(startNumberString.charAt(i));

            // case 5: 하나라도 0~3인 자릿수가 있다면, 해당 자릿수부터 뒤 자리수를 전부 4로 변경함
            if (number < 4) {
                for (int j = i; j < startNumberString.length(); j++) {
                    sb.append("4");
                }
                return Long.parseLong(sb.toString());
            }

            // case 6: 하나라도 5~6인 자리수가 있다면, 해당 자리수를 7로 바꾸고 뒤 자리수를 전부 4로 변경함
            if (4 < number && number < 7) {
                sb.append("7");
                for (int j = i+1; j < startNumberString.length(); j++) {
                    sb.append("4");
                }
                return Long.parseLong(sb.toString());
            }

            // case 7: 만약 4나 7이라면 뒷자리로 넘어감
            if (number == 4 || number == 7) {
                sb.append(number);
                continue;
            }

            // case 8: 만약 8~9라면 앞자리에 따라 로직이 달라짐
            int beforeNumber = Character.getNumericValue(sb.charAt(sb.length() - 1));

            // case 8-1: 앞자리가 4였다면, 앞자리를 7로 바꾸고 뒤를 전부 4로 함
            if (beforeNumber == 4) {
                sb.deleteCharAt(sb.length() - 1);
                sb.append("7");
                for (int j = i; j < startNumberString.length(); j++) {
                    sb.append("4");
                }
                return Long.parseLong(sb.toString());
            }

            // case 8-2: 앞자리가 7이었다면, 한칸 더 앞자리를 찾아가야 함
            // 7이 아닌 앞자리를 계속 찾음
            for (int j = i-1; j >= 0; j--) {
                int n = Character.getNumericValue(startNumberString.charAt(j));
                // 7이 아닌 앞자리(=4)를 찾았으니, 그 자리를 7로 바꾸고 나머지는 4로 바꿈
                if (n != 7) {
                    sb.delete(j, sb.length());
                    sb.append("7");
                    for (int k = j+1; k < startNumberString.length(); k++) {
                        sb.append("4");
                    }
                    return Long.parseLong(sb.toString());
                }
            }
            // 7이 아닌 앞자리를 찾지 못했음 -> 전부 7임 -> 한자리 추가한 444...4로 바꿔야함
            StringBuilder newSb = new StringBuilder();
            for (int j = -1; j < startNumberString.length(); j++) {
                    newSb.append("4");
            }
            return Long.parseLong(newSb.toString());
        }

        return Long.parseLong(sb.toString());
    }

    /**
     * 금민수를 입력받고 다음 금민수를 반환함
     */
    private static long findNextNumber(long prevNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(prevNumber);
        String prevNumberString = Long.toString(prevNumber);

        // 뒷자리부터 탐색
        for (int i = prevNumberString.length() - 1; i >= 0; i--) {
            // case 1: 4를 찾으면 해당 자리를 7로 바꾸고 반환
            if (prevNumberString.charAt(i) == '4') {
                sb.setCharAt(i, '7');
                return Long.parseLong(sb.toString());
            }

            // case 2: 7을 찾으면 해당 자리를 4로 바꾸고 앞 자리를 찾음
            if (prevNumberString.charAt(i) == '7') {
                sb.setCharAt(i, '4');
                // 만약 가장 앞 자리라면, 앞에 새로 4를 붙이고 반환
                if (i == 0) {
                    sb.insert(0, '4');
                    return Long.parseLong(sb.toString());
                }
            }
        }

        return Long.parseLong(sb.toString());
    }
    
}
