package com.san4o.test.solution

import org.junit.Assert.*
import org.junit.Test

class SolutionAddTwoNumbersTest {
    val sut = SolutionAddTwoNumbers()

    @Test
    fun test1() {
        assertEquals(
            ListNode.fromList(7, 0, 8),
            sut.addTwoNumbers(
                ListNode.fromList(2, 4, 3),
                ListNode.fromList(5, 6, 4)
            )
        )
    }
    @Test
    fun test2() {
        assertEquals(
            ListNode.fromList(7, 0, 4, 0, 1),
            sut.addTwoNumbers(
                ListNode.fromList(2, 4, 9),
                ListNode.fromList(5, 6, 4, 9)
            )
        )
    }
}