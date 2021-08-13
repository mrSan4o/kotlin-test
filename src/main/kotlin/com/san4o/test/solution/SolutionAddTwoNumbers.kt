package com.san4o.test.solution

class SolutionAddTwoNumbers {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val num1 = l1?.toNumber() ?: 0
        val num2 = l2?.toNumber() ?: 0
        val sum = num1 + num2
        println("$num1 + $num2 = $sum")
        return fromInt(sum)
    }
}

fun fromInt(number: Int): ListNode {
    val numberAsString = number.toString()

    val numbers = numberAsString
        .indices
        .map { i -> numberAsString[i].toString().toInt() }
        .reversed()

    return ListNode.fromList(numbers)
}

fun ListNode.size(): Int {
    var size = 0
    var temp: ListNode? = this
    while (temp != null) {
        size++
        temp = temp.next
    }
    return size
}

fun ListNode.toNumber(): Int {
    var result: Int = 0
    var temp: ListNode? = this
    while (temp != null) {
        val size = temp.size()
        val add = multiply10Times(size) * temp.`val`
        result += add
        temp = temp.next
    }
    return result
}

fun multiply10Times(size: Int) = (1..size).reduce { acc, i -> acc * 10 }
