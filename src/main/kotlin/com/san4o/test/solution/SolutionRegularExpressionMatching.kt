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
            // println("\nmatching:\ns: $string\np: $pattern")
            if (pattern.isEmpty()) return false
            if (pattern.indexOf('.') == -1 && pattern.indexOf('*') == -1) {
                return string == pattern
            }
            if (string.isEmpty() && pattern.length == 2 && pattern[1] == '*') {
                return true
            }

            var s = 0
            var p = 0
            while (pattern.length > p && string.length > s) {
                val pc = pattern[p]

                if (pattern.isNextZeroOrAny(p)) {

                    if (pc == '.') {
                        if (p + 1 == pattern.length - 1) {
                            // если конец паттерна значит вся оставшаяся строка мачится
                            p += 2
                            s = string.length
                            return true
                        } else {
                            // если не конец, то надо смотреть что идет после pc
                            val pn = p + 2
                            val pcn = pattern[pn]
                            if (pn + 1 < pattern.length && pattern[pn + 1] == '*') {
                                // если за pcn следует * значит надо рассматривать как zeroOrAny
                                p += 2
                                s += foundMatchesZeroOrAnyCount(
                                    baseInfo = "'$pc*' s=$s p=${p - 2}",
                                    paramInfo = "$string <> $pattern",
                                    string = string,
                                    start = s,
                                    subpattern = pattern.substring(p),
                                    equalsCount = string.length - s
                                )
                            } else {
                                // иначе это либо конец патерна, либо обычный символ
                                val subpattern = pattern.substring(p + 2)

                                p += 2
                                val baseInfo = "'$pc*' s=$s p=${p - 2}"
                                val paramInfo = "$string <> $pattern"
                                var f = 0
                                val substring = string.substring(s)
                                val length = substring.length
                                while (length > f) {
                                    val info = "$baseInfo f=$f/$length"
                                    val substringf = substring.substring(f)
                                    println("Compare [$info] - $substringf <> $subpattern in $paramInfo")

                                    if ((substring[f] == pcn || pcn == '.') && isMatch(substringf, subpattern)) {
                                        return true
                                    } else {
                                        f++
                                    }
                                }
                            }
                        }
                    } else {
                        // если * перед буквой то ищем ее последующее повторение
                        val equalsCount = equalCharCount(string, s, pc)
                        p += 2

                        // далее пытаемся определить сколько pc захватывает символов в строке
                        // сравнивая оставшуюся часть строки с оставшейся частью паттерна
                        s += foundMatchesZeroOrAnyCount(
                            baseInfo = "'$pc*' s=$s p=${p - 2}",
                            paramInfo = "$string <> $pattern",
                            string = string,
                            start = s,
                            subpattern = pattern.substring(p),
                            equalsCount = equalsCount
                        )
                    }
                } else {
                    // если это простой символ или люблй то смотрим равен ли он в строке
                    if (pc != '.' && string[s] != pc) {
                        println("NOT match: p[$p]=$pc s[$s]=${string[s]} $string <> $pattern")
                        return false
                    } else {
                        println("match: p[$p]=$pc s[$s]=${string[s]} $string <> $pattern")
                        s++
                        p++
                        // если строка закончилась, то смотрим не остались ли в конце
                        // *-символы
                        if (string.length == s && pattern.isNotEmpty()) {
                            println("End string. found zeroOrAny in '${pattern.substring(p)}'")
                            while (pattern.length > p && pattern.isNextZeroOrAny(p)) {
                                p += 2
                            }
                        }
                    }
                }
            }

            val match = pattern.length == p && string.length == s
            println("matching $match '$string' <> '$pattern'")
            return match
        }

        private fun foundMatchesZeroOrAnyCount(
            baseInfo: String,
            paramInfo: String,
            string: String,
            start: Int,
            subpattern: String,
            equalsCount: Int
        ): Int {
            if (subpattern.isEmpty()) {
                // Если оставшейся части паттерна нет, значит то что нашлось в equalsCount
                // последнее то что нашлось по патерну
                return equalsCount
            }
            if (equalsCount == 0) {
                return 0
            }
            var f = 0
            while (equalsCount >= f) {
                val info = "$baseInfo f=$f/$equalsCount"
                val substring = string.substring(start + f)

                println("Compare [$info] - $substring <> $subpattern in $paramInfo")

                if (isMatch(substring, subpattern)) {
                    // проверяем как отдельную задачу
                    // в случае успеха возвращаем смещение
                    println("Compare True [$info] - $substring <> $subpattern in $paramInfo")
                    return f
                } else {
                    // в случае провала уменьшаем строку на один элемент
                    // и рассматриваем его
                    println("Compare False [$info] - $substring <> $subpattern in $paramInfo")
                    f++
                }
            }
            return 0
        }

        private fun println(message: String) {
            kotlin.io.println(message)
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


