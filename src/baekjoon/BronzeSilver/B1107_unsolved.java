package baekjoon.BronzeSilver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;

import static java.lang.Integer.parseInt;

public class B1107_unsolved {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final long defaultChannel = 100L;
    private static long channel;

    private static HashSet<Integer> buttons;

    public static void main(String[] args) throws Exception {

        init();

        controller();

        out.close();
    }

    private static void controller() throws Exception {
        // +-버튼만 누른 경우의 수
        long onlyMoveCase = ifOnlyMove(defaultChannel, channel);
        // 아무 버튼도 누를 필요 없거나, 남은 숫자 버튼이 없으면 바로 메서드 종료
        if (onlyMoveCase == 0 || buttons.isEmpty()) {
            out.write(onlyMoveCase+"");
            return;
        }

        // 모든 숫자 버튼을 바로 누를 수 있는지 확인
        if (isAllButtonAlive(channel)) {
            String stringChannel = Long.toString(channel);
            long count = stringChannel.length();
            // +- 버튼만 누른 경우의 수와 비교해서 더 작은 것을 출력함
            long minimum = Math.min(onlyMoveCase, count);
            out.write(minimum+"");
            return;
        }

        // 가장 가까운 숫자 중 작은 값, 큰 값을 구함
        long closestBigNumber = getClosestBigNumber();
        long closestSmallNumber = getClosestSmallNumber();
        // 한자리만 누를 경우도 구함
        long oneClick = getOneClick();
        // 세 값중 가장 작은 것을 출력함
        long minimum = Math.min(Math.min(closestBigNumber, closestSmallNumber), oneClick);
        out.write(minimum+"");
    }

    // 딱 한자리수만 누르고 움직버튼을 누르는 경우
    private static long getOneClick() {
        long minimum = Long.MAX_VALUE;
        for (Integer button : buttons) {
            if (Math.abs(channel - button) < minimum) {
                minimum = Math.abs(channel - button);
            }
        }
        return minimum + 1;
    }

    // 숫자 버튼을 눌러야 하는 횟수 + +-버튼을 눌러야 하는 횟수를 계산함
    private static long getTotalCount(String str) {
        long l = Long.parseLong(str);
        return str.length() + Math.abs(channel - l);
    }

    // 가장 가까운 숫자 중 큰 값을 구하는 메서드
    private static long getClosestBigNumber() {
        // 1. 숫자 버튼이 딱 맞는다면 해당 숫자 버튼을 누름
        // 2. 숫자 버튼이 맞지 않는다면, 해당 숫자보다 큰 숫자 중, 가장 작은 숫자 버튼을 누름
        // 3. 이후로는 가장 작은 숫자 버튼만 누름

        StringBuilder sb = new StringBuilder();
        String stringChannel = Long.toString(channel);
        boolean isFindButton = true;

        for (int i = 0; i < stringChannel.length(); i++) {
            if (isFindButton) {
                int number = Character.getNumericValue(stringChannel.charAt(i));
                if (buttons.contains(number)) { // 1. 숫자 버튼이 딱 맞는다면 해당 숫자 버튼을 누름
                    sb.append(number);
                }
                else { // 2. 숫자 버튼이 맞지 않는다면, 해당 숫자보다 큰 숫자 중 가장 작은 숫자 버튼을 누름
                    if (getMinimumButton(number) == Long.MAX_VALUE) { // 해당 숫자보다 큰 숫자 버튼이 없으면 구하는 것이 의미 없으니 최대값을 반환함
                        return Long.MAX_VALUE;
                    }
                    sb.append(getMinimumButton(number));
                    isFindButton = false;
                }
            } else { // 3. 이후로는 가장 작은 숫자 버튼만 누름
                sb.append(getMinimumButton());
            }
        }

        return getTotalCount(sb.toString());

    }

    // 가장 가까운 숫자 중 작은 값을 구하는 메서드
    private static long getClosestSmallNumber() {
        // 1. 숫자 버튼이 딱 맞는다면 해당 숫자 버튼을 누름
        // 2. 숫자 버튼이 맞지 않는다면, 해당 숫자보다 작은 숫자 중, 가장 큰 숫자 버튼을 누름
        // 3. 이후로는 가장 큰 숫자 버튼만 누름

        StringBuilder sb = new StringBuilder();
        String stringChannel = Long.toString(channel); // 10
        boolean isFindButton = true;

        for (int i = 0; i < stringChannel.length(); i++) {
            if (isFindButton) {
                int number = Character.getNumericValue(stringChannel.charAt(i));
                if (buttons.contains(number)) { // 1. 숫자 버튼이 딱 맞는다면 해당 숫자 버튼을 누름
                    sb.append(number);
                }
                else { // 2. 숫자 버튼이 맞지 않는다면, 해당 숫자보다 작은 숫자 중 가장 큰 숫자 버튼을 누름
                    if (getMaximumButton(number) == Long.MIN_VALUE) { // 해당 숫자보다 큰 숫자 버튼이 없으면 구하는 것이 의미 없으니 최대값을 반환함
                        return Long.MAX_VALUE;
                    }
                    sb.append(getMaximumButton(number));
                    isFindButton = false;
                }
            } else { // 3. 이후로는 가장 큰 숫자 버튼만 누름
                sb.append(getMaximumButton());
            }
        }


        return getTotalCount(sb.toString());

    }

    // 숫자 버튼 중 가장 작은 값을 반환함
    private static long getMinimumButton() {
        long minimum = Long.MAX_VALUE;
        for (Integer button : buttons) {
            if (button < minimum) {
                minimum = button;
            }
        }
        return minimum;
    }

    // 제한보다 작지 않으면서, 숫자 버튼 중 가장 작은 값을 반환함
    private static long getMinimumButton(int deadline) {
        long minimum = Long.MAX_VALUE;
        for (Integer button : buttons) {
            if (button < minimum && button > deadline) {
                minimum = button;
            }
        }
        return minimum;
    }

    // 숫자 버튼 중 가장 큰 값을 반환함
    private static long getMaximumButton() {
        long maximum = Long.MIN_VALUE;
        for (Integer button : buttons) {
            if (button > maximum) {
                maximum = button;
            }
        }
        return maximum;
    }

    // 제한보다 크지 않으면서, 숫자 버튼 중 가장 큰 값을 반환함
    private static long getMaximumButton(int deadline) {
        long maximum = Long.MIN_VALUE;
        for (Integer button : buttons) {
            if (button > maximum && button < deadline) {
                maximum = button;
            }
        }
        return maximum;
    }

    // 필요한 모든 숫자 버튼이 모두 살아 있는지 확인하는 메서드
    private static boolean isAllButtonAlive(long channel) {
        String stringChannel = Long.toString(channel);
        char[] chars = stringChannel.toCharArray();
        for (char c : chars) {
            int numericValue = Character.getNumericValue(c);
            if (!buttons.contains(numericValue)) {
                return false;
            }
        }
        return true;
    }

    // 입력과 초기화를 담당하는 메서드
    private static void init() throws Exception {
        // 채널을 입력받음
        channel = Long.parseLong(in.readLine());


        // 버튼 Set을 초기화함
        buttons = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            buttons.add(i);
        }

        // 고장난 버튼의 개수를 입력받음(사용안함)
        int i = Integer.parseInt(in.readLine());

        // 고장난 버튼을 Set에서 제외함
        if (i > 0) {
            String[] brokenButtons = in.readLine().split(" ");
            for (String brokenButton : brokenButtons) {
                buttons.remove(Integer.parseInt(brokenButton));
            }
        }
    }

    // +- 버튼만 누른 경우의 수를 계산하는 메서드
    private static long ifOnlyMove(long from, long to) {
        return Math.abs(from - to);
    }
}
