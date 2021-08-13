package com.san4o.test.solution

import org.junit.Assert.*
import org.junit.Test

class SolutionMedianSortedArraysTest {


    @Test
    fun test1() {
        assertMedian(
            expected = 2.0,
            array1 = intArrayOf(1, 3),
            array2 = intArrayOf(2)
        )
    }
    @Test
    fun test2() {
        assertMedian(
            expected = 2.5,
            array1 = intArrayOf(1, 2),
            array2 = intArrayOf(3, 4)
        )
    }
    @Test
    fun test3() {
        assertMedian(
            expected = 0.0,
            array1 = intArrayOf(0, 0),
            array2 = intArrayOf(0, 0)
        )
    }
    @Test
    fun test4() {
        assertMedian(
            expected = 1.0,
            array1 = intArrayOf(),
            array2 = intArrayOf(1)
        )
    }
    @Test
    fun test5() {
        assertMedian(
            expected = 1.0,
            array1 = intArrayOf(),
            array2 = intArrayOf(1)
        )
    }
    @Test
    fun test6() {
        assertMedian(
            expected = 2.0,
            array1 = intArrayOf(2),
            array2 = intArrayOf()
        )
    }
    @Test
    fun test7() {
        assertMedian(
            expected = 0.0,
            array1 = intArrayOf(0,0,0,0,0),
            array2 = intArrayOf(-1,0,0,0,0,0,1)
        )
    }

    private fun assertMedian(expected: Double, array1: IntArray, array2: IntArray) {
        assertEquals(
            expected,
            SolutionMedianSortedArrays.findMedianSortedArrays(
                array1,
                array2
            ),
            0.000001
        )
    }
}