package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 백준 1107번 문제 - 리모컨(골드)
 */
public class B1107 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final int DEFAULT_CHANNEL = 100;
    private static final int MAXIMUM_CHANNEL = 500_000;

    public static void main(String[] args) throws IOException {
        Input input = readInput();
        Set<Integer> availableButtons = getAvailableButtons(input.buttonAvailableStates);

        int minimumResult = Math.abs(input.targetChannel - DEFAULT_CHANNEL);
        for (int channel = 0; channel <= MAXIMUM_CHANNEL * 10; channel++) {
            if (canMakeChannel(channel, availableButtons)) {
                int result = Math.abs(input.targetChannel - channel) + String.valueOf(channel).length();
                minimumResult = Math.min(minimumResult, result);
            }
        }

        out.write(minimumResult + "");
        out.close();
    }

    private static Input readInput() throws IOException {
        int targetChannel = Integer.parseInt(in.readLine());
        int breakButtonCount = Integer.parseInt(in.readLine());

        HashSet<Integer> breakButtons = new HashSet<>();
        if (breakButtonCount > 0) {
            StringTokenizer breakButtonInput = new StringTokenizer(in.readLine(), " ");
            while (breakButtonInput.hasMoreElements()) {
                breakButtons.add(Integer.parseInt(breakButtonInput.nextToken()));
            }
        }

        return new Input(targetChannel, breakButtons);
    }

    private static class Input {
        final int targetChannel;
        final boolean[] buttonAvailableStates;

        public Input(int targetChannel, Set<Integer> breakButtons) {
            this.targetChannel = targetChannel;

            buttonAvailableStates = new boolean[10];
            for (int i = 0; i < buttonAvailableStates.length; i++) {
                boolean isBreak = breakButtons.contains(i);
                buttonAvailableStates[i] = !isBreak;
            }
        }
    }

    private static Set<Integer> getAvailableButtons(boolean[] buttonAvailableStates) {
        HashSet<Integer> availableButtons = new HashSet<>();

        for (int i = 0; i < buttonAvailableStates.length; i++) {
            if (buttonAvailableStates[i]) {
                availableButtons.add(i);
            }
        }

        return availableButtons;
    }

    private static boolean canMakeChannel(int channel, Set<Integer> availableButtons) {
        return String.valueOf(channel)
                .chars()
                .map(Character::getNumericValue)
                .allMatch(availableButtons::contains);
    }
}
