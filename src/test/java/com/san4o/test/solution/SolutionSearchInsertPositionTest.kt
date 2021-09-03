package com.san4o.test.solution

import org.junit.Assert.*
import org.junit.Test

class SolutionSearchInsertPositionTest {

    @Test
    fun test1() {
        assertSolution(
            nums = intArrayOf(1, 3, 5, 6),
            target = 5,
            output = 2
        )
    }
    @Test
    fun test2() {
        assertSolution(
            nums = intArrayOf(1, 3, 5, 6),
            target = 2,
            output = 1
        )
    }
    @Test
    fun test3() {
        assertSolution(
            nums = intArrayOf(1, 3, 5, 6),
            target = 7,
            output = 4
        )
    }
    @Test
    fun test4() {
        assertSolution(
            nums = intArrayOf(1, 3, 5, 6),
            target = 0,
            output = 0
        )
    }
    @Test
    fun test5() {
        assertSolution(
            nums = intArrayOf(1),
            target = 0,
            output = 0
        )
    }
    @Test
    fun test6() {
        assertSolution(
            nums = intArrayOf(3,6,7,8,10),
            target = 5,
            output = 1
        )
    }

    private fun assertSolution(nums: IntArray, target: Int, output: Int) {
        assertEquals(
            output,
            SolutionSearchInsertPosition.searchInsert(
                nums = nums,
                target = target
            )
        )
    }
}