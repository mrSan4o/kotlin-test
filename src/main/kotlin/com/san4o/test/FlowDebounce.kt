package com.san4o.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main():Unit = runBlocking {
    val flow = oneShotEvent<String>()
    launch(Dispatchers.IO) {
        for (i in 1..5) {
            val timeMillis = i * 100L

            delay(timeMillis)
            println("tap $i delay=$timeMillis")
            flow.tryEmit("$i")
        }
    }

    launch {
        flow
                .debounce(400)
                .collect {
                    println("collect1 $it")
                }
    }
    val stopMsc = 3000L
    delay(stopMsc)
    println("pass $stopMsc")
    launch {
        flow
                .collect {
                    println("collect2 $it")
                }
    }
}


fun <T> oneShotEvent(): MutableSharedFlow<T> =
        MutableSharedFlow(
                replay = 1,
                extraBufferCapacity = 0,
                onBufferOverflow = BufferOverflow.SUSPEND
        )
