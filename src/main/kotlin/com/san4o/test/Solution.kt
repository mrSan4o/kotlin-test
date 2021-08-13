package com.san4o.test

object Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        for ((index, value) in nums.withIndex()) {

            for ((indexAdd1, valueAdd1) in nums.withIndex()) {
                if (index == indexAdd1) continue

                if (value + valueAdd1 == target){
                    return intArrayOf(index, indexAdd1)
                }
            }
        }

        return intArrayOf()
    }
}