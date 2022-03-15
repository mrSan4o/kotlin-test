package com.san4o.test

object RemoveDuplicates {

    @JvmStatic
    fun main(args: Array<String>) {
        val s = "abbabcddbabcdeedebc"
        val output = "abcde"

        println(s.toSet().joinToString(""))
    }
}