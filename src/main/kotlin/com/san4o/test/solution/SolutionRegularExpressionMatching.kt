package com.san4o.test.solution

object SolutionRegularExpressionMatching {
    fun isMatch(s: String, p: String): Boolean {
        return Solution.isMatch(s, p)
    }

    object Solution {
        fun isMatch(string: String, pattern: String): Boolean {
            if (pattern.indexOf('.') == -1 && pattern.indexOf('*') == -1) {
                return string == pattern
            }

            var s = 0
            var p = 0
            while (pattern.length > p && string.length > s) {
                val pc = pattern[p]

                if (!pattern.isNextZeroOrAny(p)) {

                    if (pc != '.' && string[s] != pc) {
                        return false
                    } else {
                        s++
                        p++
                        if (string.length == s) {
                            while (pattern.length > p && pattern.isNextZeroOrAny(p)) {
                                p += 2
                            }
                        }
                    }
                } else {
                    if (pc != '.') {
                        var find = false
                        while (string.length > s && string[s] == pc) {
                            find = find || string[s] == pc
                            s++
                        }
                        p += 2
                        val endString = string.length == s
                        while (pattern.length > p
                            && (pattern[p] == pc
                                    || (find && endString && pattern.isNextZeroOrAny(p))
                                    )
                        ) {
                            if (find && endString && pattern.isNextZeroOrAny(p)) {
                                p += 2
                            } else {
                                p++
                            }
                        }
                    } else {

                    }

                    val beforeS = s
                    val beforeP = p
                    var find = pc == '.'
                    while (string.length > s && (pc == '.' || string[s] == pc)) {
                        find = find || string[s] == pc
                        s++
                    }
                    p += 2
                    val endString = string.length == s

                    var ss = beforeS
                    while (pattern.length > p
                        && ((pattern[p] == pc && pattern[p] != '.')
                                || (find && endString && pattern.isNextZeroOrAny(p))
                                || (pc == '.' && (string.sameFromTheEnd(ss, pattern, p)))
                                )
                    ) {
                        if (find && endString && pattern.isNextZeroOrAny(p)) {
                            p += 2
                        } else {
                            if (pc == '.' && (string.sameFromTheEnd(ss, pattern, p))) {
                                ss++
                            }
                            p++
                        }
                    }
                }
            }


            return pattern.length == p && string.length == s
        }

        private fun String.sameFromTheEnd(
            index: Int,
            pattern: String,
            p: Int
        ): Boolean {
            return this.length > index
                    && (this[index] == pattern[p] || pattern[p] == '.'
                    || pattern[p] == '*' || pattern.isNextZeroOrAny(p))
        }

        private fun String.isNextZeroOrAny(index: Int): Boolean =
            this.length > index + 1 && this[index + 1] == '*'
    }
}

