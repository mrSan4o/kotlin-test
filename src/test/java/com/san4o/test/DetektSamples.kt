@file:Suppress("TestFunctionName")

package com.san4o.test

import org.junit.Test
import kotlin.test.assertTrue

class DetektSamples {

    @Test
    fun CastToNullableType() {
        val any: Any? = 1

        val string: String? = any as? String

        println(string)
    }

    @Test
    fun DontDowncastCollectionTypes() {
        val list: List<Int> = listOf(1, 2, 3)
        list.toMutableList().add(42)

        println(list)
    }

    @Test
    fun NullableToStringCall() {
        val any: Any? = null

        val message = any.toString()
        assertTrue(message == "null")

        val toString = any?.toString()
        assertTrue(toString == null)
    }
}