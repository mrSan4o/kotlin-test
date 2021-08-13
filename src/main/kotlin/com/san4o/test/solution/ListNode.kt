package com.san4o.test.solution

data class ListNode(
    val `val`: Int,
    val next:ListNode? = null
) {

    companion object {

        fun fromList(vararg items: Int): ListNode {
            return fromList(items.toList())
        }
        fun fromList(items: List<Int>): ListNode {
            val lastIndex = items.lastIndex
            var listNode: ListNode = ListNode(items[lastIndex])
            for (index in lastIndex - 1 downTo 0) {
                listNode = ListNode(items[index], listNode)
            }

            return listNode
        }
    }

    override fun toString(): String {
        return "$`val`" + next?.toString()?.let { " >> $it" }.orEmpty()
    }
}

