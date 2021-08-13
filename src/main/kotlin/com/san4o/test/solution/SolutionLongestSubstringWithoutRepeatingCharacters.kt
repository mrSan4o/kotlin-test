package com.san4o.test.solution

import java.util.ArrayList

object SolutionLongestSubstringWithoutRepeatingCharacters {

    fun lengthOfLongestSubstring(string: String): Int {
        return Solution1.lengthOfLongestSubstring(string)
    }

    object Solution1 {
        fun lengthOfLongestSubstring(string: String): Int {
            if (string.isEmpty()) return 0
            val length = string.length
            if (length == 1) return 1
            // if (length == 2 && string[0]!=string[1]) return 2

            val list = ArrayList<Int>(length)
            var maxSize = 0
            val lastIndex = length - 1
            for (i in 0..lastIndex) {

                list.clear()
                list.add(string[i].toInt())
                for (j in (i + 1)..lastIndex) {
                    val temp = string[j].toInt()
                    if (list.contains(temp)) break
                    list.add(temp)
                }

                val size = list.size
                if (size > maxSize) {
                    maxSize = size
                }
                // if (maxSize > lastIndex - i) break
            }

            return maxSize
        }
    }

    object Solution2 {
        private val empty: Char = '\u0000'

        fun lengthOfLongestSubstring(string: String): Int {

            val length = string.length
            var maxSize = 0
            for (i in string.indices) {

                val array = CharArray(length - i + 1)
                var arrayIndex = 0
                for (j in i until length) {
                    val temp = string[j]
                    if (contains(array, temp)) break
                    array[arrayIndex++] = temp
                }

                val size = length(array)
                if (size > maxSize) {
                    maxSize = size
                }
            }

            return maxSize
        }

        fun contains(array: CharArray, c: Char): Boolean {
            return array.any { it == c }
        }

        fun length(array: CharArray): Int {
            return array.count { it != empty }
        }
    }

    object Solution4 {
        fun lengthOfLongestSubstring(string: String): Int {
            if (string.isEmpty()) return 0
            if (string.length == 1) return 1

            var max = 0
            for (i in 0..string.lastIndex) {

                for (j in (i + 1)..string.lastIndex) {

                    if (doublesExist(string, i, j)) {
                        val length = j - i
                        if (length > max) {
                            max = length
                        }
                        break
                    } else {

                        val length = j - i + 1
                        if (length > max) {
                            max = length
                        }
                    }
                }
            }
            return max
        }

        private fun doublesExist(string: String, start: Int, end: Int): Boolean {
            for (x1 in start..end) {
                val c1 = string[x1]
                for (x2 in (x1 + 1)..end) {
                    val c2 = string[x2]
                    if (c1 == c2) {
                        return true
                    }
                }
            }
            return false
        }
    }

    object Solution3 {
        fun lengthOfLongestSubstring(string: String): Int {
            return maxDoubleRangeLength(string, 0, 0, string.lastIndex)
        }

        private fun maxDoubleRangeLength(string: String, previousMax: Int, start: Int, end: Int): Int {
            var max = previousMax
            val length = end - start + 1
            if (length < max) {
                return max
            }
            var doubleFound = false
            for (i in start until end) {
                val c = string[i]
                for (j in (i + 1)..end) {
                    if (c == string[j]) {
                        doubleFound = true
                        // var l = j - start

                        val left = maxDoubleRangeLength(string, max, start, j - 1)
                        if (left > max) {
                            max = left
                        }

                        // l = end - i + 1 + 1

                        val right = maxDoubleRangeLength(string, max, i + 1, end)
                        if (right > max) {
                            max = right
                        }

                        break
                    }
                }
            }
            if (!doubleFound && length > max) {
                max = length
            }
            return max
        }

        fun lengthOfLongestSubstring3(string: String): Int {
            if (string.isEmpty()) return 0
            if (string.length == 1) return 1

            var maxSize = 0
            val lastIndex = string.lastIndex
            for (i in 0..lastIndex) {
                val c = string[i]

                val start = i + 1
                var nextRepeatIndex = lastIndex
                var foundDouble = false
                for (x in start..lastIndex) {
                    if (string[x] == c) {
                        nextRepeatIndex = x
                        foundDouble = true
                        break
                    }
                }

                val length = (nextRepeatIndex - i) + (if (foundDouble) 0 else 1)

                if (maxSize < length && (length == 1 || !doublesInRange(string, i, nextRepeatIndex))) {
                    maxSize = length
                }
            }

            return maxSize
        }

        private fun doublesInRange(string: String, start: Int, end: Int): Boolean {
            for (i in start until end) {
                val c = string[i]
                for (j in (i + 1)..end) {
                    if (c == string[j]) return true
                }
            }
            return false
        }

        data class Doubles(val index1: Int, val index2: Int)

        private fun findNextRepeatIndex(string: String, c: Char, start: Int): Int {
            val lastIndex = string.length - 1
            for (i in start..lastIndex) {
                if (string[i] == c) return i
            }

            return lastIndex
        }
    }

}
