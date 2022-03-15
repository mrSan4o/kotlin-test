package ru.sportmaster.interview

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

object CoroutineException2 {

    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("handler $throwable")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("start")
        CoroutineScope(EmptyCoroutineContext).launch() {

            launch(handler) {
                throw IllegalStateException()
            }
        }

        Thread.sleep(1000)
    }
}