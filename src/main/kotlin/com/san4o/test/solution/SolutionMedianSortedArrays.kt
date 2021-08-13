package com.san4o.test.solution

import kotlin.math.min

object SolutionMedianSortedArrays {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        return Solution2.findMedianSortedArrays(nums1, nums2)
    }

    private object Solution0 {
        fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
            val result = ArrayList<Int>()
            result.addAll(nums1.asIterable())
            result.addAll(nums2.asIterable())
            result.sort()

            val size = result.size

            if (size % 2 == 0) {
                val index1 = (size / 2) - 1
                val value1 = result[index1].toDouble()
                val value2 = result[index1 + 1].toDouble()
                return (value1 + value2) / 2
            } else {
                val centerIndex = (size - 1) / 2
                return result[centerIndex].toDouble()
            }
        }
    }

    private object Solution1 {
        fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
            val size1 = nums1.size
            val size2 = nums2.size

            val size = size1 + size2

            if (size % 2 == 0) {
                val targetIndex = (size / 2) - 1

                val indexOfArray1 = ArrayList<Int>()
                val indexOfArray2 = ArrayList<Int>()
                var index = 0
                var medianIndex = -1
                var arrNum: ArrNum = ArrNum.Arr1
                while (index <= targetIndex) {

                    val (findArrNum, findMedianIndex) = findMinArrIndex(nums1, nums2, indexOfArray1, indexOfArray2)
                    medianIndex = findMedianIndex
                    arrNum = findArrNum
                    index++
                }

                val (findArrNum, findMedianIndex) = findMinArrIndex(nums1, nums2, indexOfArray1, indexOfArray2)

                val value1 = getValue(nums1, nums2, arrNum, medianIndex)
                val value2 = getValue(nums1, nums2, findArrNum, findMedianIndex)
                return (value1 + value2)
            } else {

                val targetIndex = ((size - 1) / 2) - 1

                val indexOfArray1 = ArrayList<Int>()
                val indexOfArray2 = ArrayList<Int>()
                var index = 0
                var medianIndex = -1
                var arrNum: ArrNum = ArrNum.Arr1
                while (index <= targetIndex) {

                    val (findArrNum, findMedianIndex) = findMinArrIndex(nums1, nums2, indexOfArray1, indexOfArray2)
                    medianIndex = findMedianIndex
                    arrNum = findArrNum
                    index++
                }

                return getValue(nums1, nums2, arrNum, medianIndex)
            }
        }

        private fun getValue(
            nums1: IntArray,
            nums2: IntArray,
            arrNum: ArrNum,
            medianIndex: Int
        ) = when (arrNum) {
            ArrNum.Arr1 -> nums1[medianIndex].toDouble()
            ArrNum.Arr2 -> nums2[medianIndex].toDouble()
        }

        private fun findMinArrIndex(
            nums1: IntArray,
            nums2: IntArray,
            indexOfArray1: ArrayList<Int>,
            indexOfArray2: ArrayList<Int>
        ): Pair<ArrNum, Int> {
            val (index1, value1) = findMin(nums1.size, nums1, indexOfArray1)
            val (index2, value2) = findMin(nums2.size, nums2, indexOfArray2)
            if (value1 < value2) {
                indexOfArray1.add(index1)
                return Pair<ArrNum, Int>(ArrNum.Arr1, index1)
            } else {
                indexOfArray2.add(index2)
                return Pair<ArrNum, Int>(ArrNum.Arr2, index2)
            }
        }

        private fun findMin(
            size: Int,
            nums: IntArray,
            indexOfArray: List<Int>
        ): IndexedValue<Int> {
            var minIndex = -1
            var minValue = Int.MAX_VALUE
            for (index in 0 until size) {
                val value = nums[index]
                if (value < minValue && !indexOfArray.contains(index)) {
                    minValue = value
                    minIndex = index
                }
            }
            return IndexedValue(minIndex, minValue)
        }

        enum class ArrNum {
            Arr1,
            Arr2,
        }
    }

    private object Solution2 {
        fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
            val size1 = nums1.size
            val size2 = nums2.size
            if (size1 == 0 && size2 == 0) {
                return 0.0
            }
            val lastIndex1 = size1 - 1
            val lastIndex2 = size2 - 1
            val size = size1 + size2

            val target = targetIndex(size)
            val centerIndexTarget: Target.SingleValueCenter? = target as? Target.SingleValueCenter
            val twoValueIndexTarget: Target.TwoValueCenter? = target as? Target.TwoValueCenter

            if (size1 == 0) {
                return median(target, nums2)
            }
            if (size2 == 0) {
                return median(target, nums1)
            }

            var i = 0
            var i1 = 0
            var i2 = 0
            var nums1Close = false
            var nums2Close = false

            var twoValueFirst = 0
            while (i1 < size1 || i2 < size2) {

                val num1 = nums1[i1]
                val num2 = nums2[i2]

                if (num1 == num2){
                    i1 = if (i1 < lastIndex1) i1 + 1 else i1
                    i2 = if (i2 < lastIndex2) i2 + 1 else i2
                    continue
                }

                val min = if (!nums1Close && !nums2Close) {
                    Math.min(num1, num2)
                } else if (nums1Close) {
                    num2
                } else if (nums2Close) {
                    num1
                } else {
                    0
                }

                if (centerIndexTarget != null) {
                    if (i == centerIndexTarget.index) {
                        return min.toDouble()
                    }
                }
                if (twoValueIndexTarget != null) {
                    if (i == twoValueIndexTarget.index1) {
                        twoValueFirst = min
                    } else if (i == twoValueIndexTarget.index2) {
                        return (twoValueFirst.toDouble() + min) / 2
                    }
                }
                i++
                val minNum1 = min == num1
                val minNum2 = min == num2
                nums1Close = i1 == lastIndex1 && minNum1
                nums2Close = i2 == lastIndex2 && minNum2
                i1 = if (i1 < lastIndex1 && minNum1) i1 + 1 else i1
                i2 = if (i2 < lastIndex2 && minNum2) i2 + 1 else i2
            }
            return 0.0
        }

        private fun median(
            target: Target,
            nums2: IntArray
        ) = when (target) {
            is Target.SingleValueCenter -> nums2[target.index].toDouble()
            is Target.TwoValueCenter -> (nums2[target.index1].toDouble() + nums2[target.index2]) / 2
        }

        private fun targetIndex(size: Int): Target {
            return if (size % 2 == 0) {
                val index1 = (size / 2) - 1
                Target.TwoValueCenter(index1, index1 + 1)
            } else {
                Target.SingleValueCenter((size - 1) / 2)
            }
        }

        sealed class Target {
            data class TwoValueCenter(val index1: Int, val index2: Int) : Target()
            data class SingleValueCenter(val index: Int) : Target()
        }
    }
}