package com.san4o.test

class Solution2 {

    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {

        val result = HashSet<List<Int>>()

        for (index1 in nums.indices) {

            for (index2 in nums.indices) {
                if (index1 >= index2) continue

                for (index3 in nums.indices) {
                    if (index2 >= index3) continue

                    for (index4 in nums.indices) {
                        if (index3 >= index4) continue
                        val value1 = nums[index1]
                        val value2 = nums[index2]
                        val value3 = nums[index3]
                        val value4 = nums[index4]
                        if (value1 + value2 + value3 + value4 != target) continue

                        val fourItems = listOf(value1, value2, value3, value4).sorted()

                        result.add(fourItems)
                    }
                }
            }
        }

        return result.toList()
    }
}

fun <T> List<T>.containsOnly(list: List<T>): Boolean {
    if (this.size != list.size) return false

    var count = 0
    val indexes = ArrayList<Int>()
    for (value in list) {
        this.withIndex()
            .find { (i, v) -> v == value && !indexes.contains(i) }
            ?.let { (i, v) ->
                indexes.add(i)
                count++
            }
    }

    return count == this.size
}