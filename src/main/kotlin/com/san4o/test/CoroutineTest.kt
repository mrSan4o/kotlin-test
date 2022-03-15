package com.san4o.test

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

fun mai5n() = runBlocking {
    val s = newFixedThreadPoolContext(1, "testtt")
    CoroutineScope(s + CoroutineExceptionHandler { _, throwable ->
        println("main $throwable on ${Thread.currentThread().name}")
    }).launch(
            CoroutineExceptionHandler { _, throwable ->
                println("launch $throwable on ${Thread.currentThread().name}")
            }
    ) {
        println("Start on ${Thread.currentThread().name}")
        error("CRASH")
    }

    Thread.sleep(1000)
}
object CoroutineTest {
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("handle $throwable")
    }
    @JvmStatic
    fun main(args: Array<String>) {
        println("launch")
        val scope = CoroutineScope(SupervisorJob()+ handler)
        scope.launch() {
            launch(handler) {
                throw Exception("Failed coroutine")
            }
        }

        Thread.sleep(1000)
    }
}
