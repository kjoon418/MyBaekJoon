package baekjoon.gold;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 호텔 - DP
 */
public class B1106 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int goalCustomerAmount;
    private static final List<PromotionInfo> promotionInfos = new ArrayList<>();

    // 홍보로 증가하는 고객 수의 최대값
    private static int mostIncrease;

    // N명의 고객을 만들기 위한 최소 비용들을 담은 배열
    private static Integer[] totalCosts;

    public static void main(String[] args) throws IOException {
        init();

        calculateTotalCosts();

//        System.out.println(Arrays.toString(totalCosts));
        out.write(getBestCase() + "");
        out.close();
    }

    private static void init() throws IOException {
        StringTokenizer goalAndAmount = new StringTokenizer(in.readLine(), " ");
        goalCustomerAmount = Integer.parseInt(goalAndAmount.nextToken());
        totalCosts = new Integer[goalCustomerAmount + 1];
        Arrays.fill(totalCosts, null);
        totalCosts[0] = 0;
        int infoAmount = Integer.parseInt(goalAndAmount.nextToken());

        mostIncrease = Integer.MIN_VALUE;
        for (int i = 0; i < infoAmount; i++) {
            StringTokenizer promotionInfoInput = new StringTokenizer(in.readLine(), " ");
            int cost = Integer.parseInt(promotionInfoInput.nextToken());
            int customerIncrease = Integer.parseInt(promotionInfoInput.nextToken());

            mostIncrease = Math.max(mostIncrease, customerIncrease);

            promotionInfos.add(new PromotionInfo(cost, customerIncrease));
        }
    }

    private static void calculateTotalCosts() {
        for (int currentCustomer = 1; currentCustomer < totalCosts.length; currentCustomer++) {
            Integer bestCost = null;
            for (PromotionInfo promotionInfo : promotionInfos) {
                int prevCustomer = currentCustomer - promotionInfo.customerIncrease;
                if (prevCustomer < 0 || totalCosts[prevCustomer] == null) {
                    continue;
                }

                int totalCost = totalCosts[prevCustomer] + promotionInfo.cost;
                if (bestCost == null || totalCost < bestCost) {
                    bestCost = totalCost;
                }
            }

            if (bestCost != null) {
                totalCosts[currentCustomer] = bestCost;
            }
        }
    }

    private static int getBestCase() {
        Integer bestCost = totalCosts[goalCustomerAmount];

        int startCustomer = goalCustomerAmount - mostIncrease;
        startCustomer = Math.max(startCustomer, 0);
        for (int customerAmount = startCustomer; customerAmount < goalCustomerAmount; customerAmount++) {
            for (PromotionInfo promotionInfo : promotionInfos) {
                int totalCustomer = customerAmount + promotionInfo.customerIncrease;
                if (totalCustomer < goalCustomerAmount || totalCosts[customerAmount] == null) {
                    continue;
                }

                int totalCost = totalCosts[customerAmount] + promotionInfo.cost;

                if (bestCost == null || totalCost < bestCost) {
                    bestCost = totalCost;
                }
            }
        }

        if (bestCost == null) {
            throw new IllegalStateException("BEST CASE는 NULL일 수 없음");
        }

        return bestCost;
    }

    private static class PromotionInfo {
        final int cost, customerIncrease;

        public PromotionInfo(int cost, int customerIncrease) {
            this.cost = cost;
            this.customerIncrease = customerIncrease;
        }
    }

}
