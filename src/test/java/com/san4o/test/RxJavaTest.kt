package com.san4o.test

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test

class RxJavaTest {

    @Test
    fun test() {
        val completable1 = Single.fromCallable { task(delay = 2000, error = true) }
        val completable2 = Single.fromCallable { task(delay = 1000) }

        Observable.concatDelayError(
            listOf(completable1.toObservable(), completable2.toObservable())
        )
            .subscribe()

        Thread.sleep(4000)
    }

    private fun task(i: Int = numebr++, delay: Long, error: Boolean = false) {
        println("#$i Start delay=$delay")
        try {
            Thread.sleep(delay)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        if (error) {
            throw IllegalStateException("Error")
        }

        println("#$i Complete")
    }

    companion object {
        var numebr = 1
    }
}