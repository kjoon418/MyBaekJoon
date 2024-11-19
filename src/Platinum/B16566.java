package Platinum;

import java.io.*;
import java.util.*;

public class B16566 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int totalCardAmount;
    private static int drawAmount;
    private static int gameAmount;

    private static int[] redCards;
    private static int[] blueCards;
    private static HashSet<Integer> blackList = new HashSet<>();

    private static int maxValue = 4_000_000;
    private static final StringBuilder resultBuilder = new StringBuilder();
    private static final List<Integer>[] radixLists = new List[10];

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        StringTokenizer input = new StringTokenizer(in.readLine(), " ");
        totalCardAmount = Integer.parseInt(input.nextToken());
        drawAmount = Integer.parseInt(input.nextToken());
        gameAmount = Integer.parseInt(input.nextToken());

        blueCards = new int[drawAmount];
        StringTokenizer blueInput = new StringTokenizer(in.readLine(), " ");
        for (int i = 0; i < drawAmount; i++) {
            blueCards[i] = Integer.parseInt(blueInput.nextToken());
        }

        redCards = new int[gameAmount];
        StringTokenizer redInput = new StringTokenizer(in.readLine(), " ");
        for (int i = 0; i < gameAmount; i++) {
            redCards[i] = Integer.parseInt(redInput.nextToken());
        }
    }

    private static void controller() throws IOException {
        radixSort(blueCards);

        for (int i = 0; i < gameAmount; i++) {
            int resultIndex = indexBinarySearch(redCards[i]);

            resultBuilder.append(blueCards[resultIndex]).append(System.lineSeparator());
            blackList.add(blueCards[resultIndex]);
        }
    }

    /**
     * 기수 정렬을 통해 배열을 오름차순으로 정렬하는 메서드
     */
    private static void radixSort(int[] array) {

        for (int i = 0; i < radixLists.length; i++) {
            radixLists[i] = new ArrayList<>();
        }

        for (int i = 1; i <= maxValue; i *= 10) {
            for (int value : array) {
                radixLists[(value % i * 10) / i].add(value);
            }

            int index = 0;
            for (List<Integer> radixList : radixLists) {
                for (Integer listValue : radixList) {
                    array[index] = listValue;
                    index++;
                }
                radixList.clear();
            }
        }
    }

    /**
     * 이분 탐색 메서드
     * 목표값보다 큰 값 중 가장 작은 값을 지닌 인덱스 값을 반환함
     */
    private static int indexBinarySearch(int goal) {
        int startIndex = 0;
        int endIndex = blueCards.length;

        if (startIndex == endIndex) {
            return 0;
        }

        int index = endIndex/2;
        while(true) {
            // 목표값보다 큰지 확인
            if (blueCards[index] > goal) {
                // 맨 앞 인덱스라면 바로 반환
                if (index == 0) {
                    while (blackList.contains(blueCards[index])) {
                        index++;
                    }
                    return index;
                }
                // 맨 앞 인덱스가 아니라면, 자기 앞의 값도 목표값보다 큰지 확인함
                if (blueCards[index-1] > goal) {
                    // 상한을 현재 인덱스로 두고 탐색을 재개함
                    endIndex = index;
                    index = (endIndex + startIndex) / 2;
                    continue;
                }

                // 자기 앞의 값은 목표값보다 작다 = 내가 최적값이다
                while (blackList.contains(blueCards[index])) {
                    index++;
                }
                return index;
            }

            // 목표값보다 작으면, 자기 바로 뒤의 값은 목표값보다 큰지 확인
            if (blueCards[index+1] > goal) {
                index++;
                while (blackList.contains(blueCards[index])) {
                    index++;
                }
                return index;
            }

            // 하한을 현재 인덱스로 두고 탐색을 재개함
            startIndex = index;
            index = (endIndex + startIndex) / 2;
        }
    }

    private static void printer() throws IOException {
        out.write(resultBuilder.toString());

        out.close();
    }

}
