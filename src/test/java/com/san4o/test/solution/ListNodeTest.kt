package com.san4o.test.solution

import org.junit.Test
import kotlin.test.assertEquals

class ListNodeTest {

    @Test
    fun test() {
        val node = ListNode.fromList(1, 2, 3)
        println(node)
        assertEquals(3, node.size())
        assertEquals(123, node.toNumber())
        assertEquals(708, ListNode.fromList(7, 0, 8).toNumber())
    }
}