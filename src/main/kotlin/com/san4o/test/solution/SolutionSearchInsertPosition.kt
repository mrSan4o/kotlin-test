package com.san4o.test.solution

object SolutionSearchInsertPosition {
    fun searchInsert(nums: IntArray, target: Int): Int {

        var min = Int.MAX_VALUE
        for (i in nums.indices) {
            val num = nums[i]
            if (num == target) {
                return i
            } else {
                val diff = target - num
                if (diff > 0 && (diff < min || min == Int.MAX_VALUE)) {
                    min = diff
                } else {
                    return i
                }
            }
        }
        return nums.size
    }
}