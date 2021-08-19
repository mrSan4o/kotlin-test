package com.san4o.test.solution

object SolutionRegularExpressionMatching {
    fun isMatch(s: String, p: String): Boolean {
        return Solution2.isMatch(s, p)
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
                    // вариант с *

                    // символ буква
                    if (pc != '.') {

                        // длина после * повторений pc
                        val afterPcDoublesCount = afterPcDoublesCount(pattern, p)
                        var findCount = 0
                        var eqIndex = s
                        while (string.length > eqIndex && string[eqIndex++] == pc) {
                            findCount++
                        }
                        if (string.length == eqIndex) {
                            var singlePcCount = 0
                            var equalPC: Boolean
                            var isZeroOrAny: Boolean
                            do {
                                equalPC = pattern[p] == pc
                                isZeroOrAny = pattern.isNextZeroOrAny(p)
                                if (isZeroOrAny) {
                                    p += 2
                                } else if (equalPC) {
                                    p++
                                    singlePcCount++
                                }
                            } while (equalPC || isZeroOrAny)
                            if (singlePcCount > findCount) {
                                return false
                            }
                        } else {

                        }

                        // если после * есть pc, то в строке должно быть не меньше
                        if (findCount < afterPcDoublesCount) return false
                        // передвигаем указатель паттерна на количество pc* и кол-во дубдей pc после *
                        p += 2 + afterPcDoublesCount

                        val endString = string.length == s
                        // если конец строки, то в паттерне нельзя ли
                        //      схлопнуть оставшуюся часть как "буква*"
                        //
                        if (string.length == s) {

                            var equalPC: Boolean
                            var isZeroOrAny: Boolean
                            do {
                                equalPC = pattern[p] == pc
                                isZeroOrAny = pattern.isNextZeroOrAny(p)
                                if (isZeroOrAny) {
                                    p += 2
                                } else if (equalPC) {
                                    p++
                                }
                            } while (equalPC || isZeroOrAny)
                        }
                    } else {
                        // в паттерне последняя инстр - вся строка замачена
                        if (pattern.length <= p + 2) {
                            return true
                        }
                        val subPattern = pattern.substring(p + 2)
                        val subString = string.substring(s)

                        //
                        return isMatch(subString, subPattern)
                    }
                }
            }


            return pattern.length == p && string.length == s
        }

        private fun afterPcDoublesCount(pattern: String, p: Int): Int {
            var count = 0
            var i = p + 2
            var search = false
            while (pattern.length > i && search) {

                search = if (pattern[i] == pattern[p]) {
                    i++
                    count++
                    true
                } else if (pattern.isNextZeroOrAny(i + 1)) {
                    i += 2
                    true
                } else {
                    false
                }
            }

            return count
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
            isMatch(index + 1, '*')

        private fun String.isMatch(i: Int, c: Char): Boolean =
            this.length > i && this[i] == c
    }

    object Solution2 {
        fun isMatch(string: String, pattern: String): Boolean {
            println("\nmatching:\ns: $string\np: $pattern")
            if (pattern.isEmpty()) return false
            if (pattern.indexOf('.') == -1 && pattern.indexOf('*') == -1) {
                return string == pattern
            }

            var s = 0
            var p = 0
            while (pattern.length > p && string.length > s) {
                val pc = pattern[p]

                if (pattern.isNextZeroOrAny(p)) {

                    val equalsCount = if (pc == '.') {
                        string.length - s
                    } else {
                        equalCharCount(string, s, pc)
                    }
                    p += 2

                    var f = 0
                    var found = false
                    while (equalsCount >= f && !found) {
                        val info = "'$pc*' p=${p - 2} f=$f"
                        val subpattern = pattern.substring(p)
                        val substring = string.substring(s + f)

                        println("Compare [$info] s: $substring, p: $subpattern")
                        if (subpattern.isEmpty() && s + equalsCount == string.length) {
                            s += f + equalsCount
                            found = true
                            println("True [$info] match:\ns: $string\np: $pattern")
                        } else if (isMatch(
                                string = substring,
                                pattern = subpattern
                            )
                        ) {
                            s += f
                            found = true

                            println("True [$info] match:\ns: $substring\np: $subpattern")
                        } else {
                            println("False [$info]\ns: $substring\np: $subpattern")
                            f++
                        }
                    }
                } else {
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
                }
            }


            return pattern.length == p && string.length == s
        }

        private fun String.isNextZeroOrAny(index: Int): Boolean =
            isMatch(index + 1, '*')

        private fun String.isMatch(i: Int, c: Char): Boolean =
            this.length > i && this[i] == c

        private fun equalCharCount(string: String, start: Int, pc: Char): Int {
            var findCount = 0
            var i = start
            while (string.length > i && string[i++] == pc) {
                findCount++
            }
            return findCount
        }
    }
}


