package ru.sportmaster.interview

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

object CoroutineException1 {

    @JvmStatic
    fun main(args: Array<String>) {
        println("start")
        try {
            CoroutineScope(EmptyCoroutineContext).launch {
                throw IllegalStateException()
            }
        } catch (e: Exception) {
            println("catch $e")
        }

        Thread.sleep(1000)
    }
}