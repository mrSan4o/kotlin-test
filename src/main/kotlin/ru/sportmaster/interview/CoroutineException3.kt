package ru.sportmaster.interview

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

object CoroutineException3 {
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("handler $throwable")
    }
    @JvmStatic
    fun main(args: Array<String>) {
        println("start")
        CoroutineScope(SupervisorJob()).launch {
            launch {
                println("launch1")
                delay(200)
                println("launch1 complete")
            }

            launch {
                delay(100)
                throw IllegalStateException()

            }

            launch {
                println("launch3")
                delay(200)
                println("launch3 complete")
            }
        }

        Thread.sleep(1000)
    }
}