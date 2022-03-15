package ru.sportmaster.interview

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

object CoroutineException4 {
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("handler $throwable")
    }
    @JvmStatic
    fun main(args: Array<String>):Unit = runBlocking {
        println("start")

        val deferred = async(handler) {
            println("async")
            delay(100)
            throw IllegalStateException()
        }

        val deferred2 = async(handler) {
            println("async2")
            delay(200)
            println("async2 - complete")
        }


        awaitAll(deferred, deferred2)
    }
}