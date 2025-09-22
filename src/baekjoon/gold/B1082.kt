package baekjoon.gold

import java.math.BigInteger

/**
 * 백준 1082번 문제 - 방 번호(골드 3)
 * DP, 그리디
 */
fun main1082() = with(System.`in`.bufferedReader() to System.out.bufferedWriter()) {
    val (reader, writer) = this

    val numberCount = reader.readLine().toInt()
    val numberPrices = reader.readLine()
        .split(" ")
        .map { it.toInt() }
    val maxPrice = numberPrices.max()
    val maxMoney = reader.readLine().toInt()
    val dp = Array(size = maxMoney + 1) { DEFAULT_CASE }

    // Bottom-up
    for (currentMoney in 1..maxMoney) {
        var bestCase = DEFAULT_CASE

        // 이전 케이스부터 조회
        for (previousMoney in (currentMoney - maxPrice)..currentMoney) {
            if (previousMoney < 0) continue

            val previousCase = dp[previousMoney]
            val usableMoney = currentMoney - previousMoney + previousCase.leftMoney

            val highestNumber = findHighestNumber(numberPrices, usableMoney)
            val price = if (highestNumber == null) 0 else numberPrices[highestNumber]

            val case = previousCase.getNextCase(
                purchasedNumber = highestNumber,
                price = price,
                usableMoney = usableMoney
            )

            bestCase = max(bestCase, case)
        }

        dp[currentMoney] = bestCase
    }

    writer.write(dp[maxMoney].sortedNumber.toString())
    writer.close()
}

private val DEFAULT_CASE = Case(0, listOf())

private class Case(
    var leftMoney: Int,
    var numbers: List<Int>,
) {
    val sortedNumber: BigInteger = numbers.sortedDescending()
        .joinToString(separator = "") { it.toString() }
        .toBigInteger()

    fun getNextCase(purchasedNumber: Int?, price: Int, usableMoney: Int): Case {
        val nextNumbers = if (purchasedNumber == null) numbers else (numbers + purchasedNumber)
        return Case(
            leftMoney = (usableMoney - price),
            numbers = nextNumbers
        )
    }
}

private fun max(case1: Case, case2: Case): Case {
    return if (case1.sortedNumber > case2.sortedNumber) case1 else case2
}

private fun findHighestNumber(numberPrices: List<Int>, usableMoney: Int): Int? {
    for (numberForPurchase in (numberPrices.size - 1) downTo 0) {
        val price = numberPrices[numberForPurchase]
        if (price <= usableMoney) {
            return numberForPurchase
        }
    }

    return null
}

private fun String.toBigInteger(): BigInteger {
    return if (this.isEmpty()) BigInteger("0") else BigInteger(this)
}
