package com.san4o.test

import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
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

    @Test
    fun testMaybe() {
        Maybe.fromCallable<Int> {
            null
        }
                .subscribe(
                        { println("Success $it") },
                        { println("Error $it") },
                        { println("Complete") },
                )
    }

    @Test
    fun testSubject() {
        val list = arrayListOf<Int>(1, 2, 4)
        list.addAll(listOf(5, 6))
        val inSubject = PublishSubject.create<String>()
        val outSubject = PublishSubject.create<String>()
        var taskDisposable: Disposable? = null
        Completable.fromCallable {
            task(777, 100)
        }.toSingle { "1" }
                .subscribeOn(Schedulers.io())
                .flatMapPublisher {
                    println("flatMap")
                    taskDisposable(
                            { outSubject.onNext("1") },
                            { outSubject.onNext("2") }
                    )
                            .also { taskDisposable = it }

                    outSubject.toFlowable(BackpressureStrategy.BUFFER)


                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnSubscribe {
                    println("doOnSubscribe $it")
                }
//                .doOnDispose {
//                    println("doOnCancel isDisposed=${taskDisposable?.isDisposed}")
//                    taskDisposable?.dispose()
//                }
                .doFinally { println("doFinally") }
                .doOnTerminate { println("Terminate") }
                .subscribe(
                        { println("main - completed") },
                        { println("main - failed : $it") }
                )
                .let {
//                    Thread.sleep(110)
//                    it.dispose()
                }


        Thread.sleep(5000)
    }

    private fun taskDisposable(
            onSuccess: () -> Unit,
            onFailed: (Throwable?) -> Unit = {}
    ): Disposable {
        return Completable.fromCallable {
            task(999, 200)
        }
                .andThen(Completable.fromCallable {
                    task(888, 300)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnSubscribe {
                    println("task subscribe")
                }
                .doOnDispose {
                    println("task disposed")
                }
                .subscribe(
                        {
                            println("task Success")
                            onSuccess()
                        },
                        {
                            println("task Failed : $it")
                            onFailed(it)
                        }
                )
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