package com.san4o.test

import org.junit.Assert.*
import org.junit.Test

class SolutionTest {

    @Test
    fun test1() {
        val nums = arrayOf(2, 7, 11, 15).toIntArray()
        val target = 9

        val result = Solution.twoSum(nums, target)

        assertEquals(listOf(0, 1), result.toList())
    }

    @Test
    fun test2() {
        val nums = arrayOf(3, 2, 4).toIntArray()
        val target = 6

        val result = Solution.twoSum(nums, target)

        assertEquals(listOf(1, 2), result.toList())
    }
    @Test
    fun test3() {
        val nums = arrayOf(3, 3).toIntArray()
        val target = 6

        val result = Solution.twoSum(nums, target)

        assertEquals(listOf(0, 1), result.toList())
    }
}