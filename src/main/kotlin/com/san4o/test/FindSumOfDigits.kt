package com.san4o.test

object FindSumOfInteger {
    fun findSumOfDigits(num: Int): Int {
        return if (num == 0) 0 else num % 10 + findSumOfDigits(num / 10)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val n = 4321
        println(n % 10)
        println(findSumOfDigits(n))
    }
}