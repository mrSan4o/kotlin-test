package com.san4o.test.solution

object SolutionRegularExpressionMatching {
    fun isMatch(s: String, p: String): Boolean {
        return Solution3.isMatch(s, p)
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
            if (string.isEmpty()) {
                var p = 0
                while (pattern.length > p && pattern.isNextZeroOrAny(p)) {
                    p += 2
                }
                return pattern.length == p
            }

            var s = 0
            var p = 0
            while (pattern.length > p && string.length > s) {
                val pc = pattern[p]

                if (pattern.isNextZeroOrAny(p)) {

                    if (pc == '.' && p + 1 == pattern.length - 1) {
                        return true
                    }
                    // если * перед буквой то ищем ее последующее повторение
                    val equalsCount = equalCharCount(string, s, pc)
                    p += 2

                    // далее пытаемся определить сколько pc захватывает символов в строке
                    // сравнивая оставшуюся часть строки с оставшейся частью паттерна
                    val baseInfo = "'$pc*' s=$s p=${p - 2}"
                    val paramInfo = "$string <> $pattern"
                    val subpattern = pattern.substring(p)
                    if (subpattern.isEmpty()) {
                        // Если оставшейся части паттерна нет, значит то что нашлось в equalsCount
                        // последнее то что нашлось по патерну
                        s += equalsCount
                    } else if (equalsCount > 0) {
                        var f = 0
                        while (equalsCount >= f) {
                            val info = "$baseInfo f=$f/$equalsCount"
                            val substring = string.substring(s + f)

                            println("Compare [$info] - $substring <> $subpattern in $paramInfo")

                            if (isMatch(substring, subpattern)) {
                                // проверяем как отдельную задачу
                                // в случае успеха возвращаем смещение
                                println("Compare True [$info] - $substring <> $subpattern in $paramInfo")
                                return true
                            } else {
                                // в случае провала уменьшаем строку на один элемент
                                // и рассматриваем его
                                println("Compare False [$info] - $substring <> $subpattern in $paramInfo")
                                f++
                            }
                        }
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
            if (pc == '.') return string.length - start
            var findCount = 0
            var i = start
            while (string.length > i && string[i++] == pc) {
                findCount++
            }
            return findCount
        }
    }

    /**
     * Runtime: 164 ms
     * Memory Usage: 35.9 MB
     *
     * Runtime: 148 ms, faster than 98.68% of Kotlin online submissions for Regular Expression Matching.
     * Memory Usage: 35.6 MB, less than 69.74% of Kotlin online submissions for Regular Expression Matching.
     * */
    object Solution21 {
        fun isMatch(string: String, pattern: String): Boolean {
            return isMatch(string, 0, pattern, 0)
        }

        private fun isMatch(string: String, startStr: Int, pattern: String, startPtr: Int): Boolean {
            // println("\nmatching:\ns: $string\np: $pattern")
            if (pattern.indexOf('.') == -1 && pattern.indexOf('*') == -1) {
                return string == pattern
            }

            var s = startStr
            var p = startPtr
            while (pattern.length > p && string.length > s) {
                val pc = pattern[p]
                val str = string[s]

                val pn = p + 1
                if (pattern.length > pn) {
                    val pcn = pattern[pn]
                    if (pcn.isZeroOrMore()) {

                        if (pc.isAny()) {
                            p += 2

                            if (pn == pattern.lastIndex) {
                                // если это конец паттерна или до конца паттерна только ZeroOrMore символы
                                // то все в строке мачится
                                return true
                            }

                            while (pattern.length > p) {
                                val fpc = pattern[p]
                                val fpn = p + 1

                                // если следующий за fpc символ * или текущий *
                                // то идем дальше
                                if ((pattern.length > fpn && pattern[fpn].isZeroOrMore()) || fpc.isZeroOrMore()) {
                                    p++
                                } else {
                                    // идем по строке и находим символ равный fpc
                                    // и смотрим строку после него можно рассматривать
                                    // как то что замачится по существующему алгоритму
                                    while (string.length > s) {
                                        if ((fpc.isAny() || string[s] == fpc) && isMatch(string, s, pattern, p)) {
                                            return true
                                        } else {
                                            s++
                                        }
                                    }
                                    // если нашли не zeroOrMore и его нет в строке
                                    // значит точно не мачится
                                    return false
                                }
                            }
                            s = string.length
                        } else {
                            // количество возможных символов в строке
                            // которые замачатся на zerOrMore
                            val equalsCount = equalCharCount(string, s, pc)
                            p += 2
                            // если в паттерне еще такие же zeroOrMore следом
                            // то схлопываем их так как они имеют вместе такой же эффект как и один
                            while (pattern.isNextZeroOrMore(p) && pc == pattern[p]) {
                                p += 2
                            }

                            // далее пытаемся определить сколько pc захватывает символов в строке
                            // сравнивая оставшуюся часть строки с оставшейся частью паттерна
                            // val baseInfo = "'$pc*' s=$s p=${p - 2}"
                            // val paramInfo = "$string <> $pattern"
                            if (pattern.length == p) {
                                // Если оставшейся части паттерна нет,
                                // значит то что нашлось в equalsCount последнее что нашлось по патерну
                                s += equalsCount
                            } else if (equalsCount > 0) {
                                // далее пытаемся определить сколько pc захватывает символов в строке
                                // сравнивая оставшуюся часть строки с оставшейся частью паттерна
                                var fp = p
                                var pcEquals = 0
                                while (pattern.length > fp && pattern[fp++] == pc) {
                                    pcEquals++
                                }

                                var f = pcEquals
                                while (equalsCount >= f) {
//                                    val info = "$baseInfo f=$f/$equalsCount"
//                                    val compareInfo = "${string.substring(s + f)} <> ${pattern.substring(p)}"
//                                    println("Compare [$info] - $compareInfo in $paramInfo")

                                    val startFStr = s + f

                                    if (string.length == startFStr) {
                                        // если строка полностью рассмотрена
                                        // то проверяем в паттерн не остался ли набор только из zeroOrMore
                                        return isOnlyZeroOrMoreCharsToEnd(pattern, p)
                                    }
                                    if (isMatch(string, startFStr, pattern, p)) {
                                        // проверяем как отдельную задачу
//                                        println("Compare True [$info] - $compareInfo in $paramInfo")
                                        return true
                                    }
                                    // в случае провала уменьшаем строку на один элемент
                                    // и рассматриваем его
//                                    println("Compare False [$info] - $compareInfo in $paramInfo")
                                    f++
                                }
                            }
                        }
                        continue
                    }
                }

                // если это простой символ или люблй то смотрим равен ли он тому что в строке
                if (!pc.isAny() && str != pc) {
//                    println("NOT match: p[$p]=$pc s[$s]=${str} $string <> $pattern")
                    return false
                } else {
//                    println("match: p[$p]=$pc s[$s]=${str} $string <> $pattern")
                    s++
                    p++
                    // если строка закончилась, то смотрим не остались ли в конце
                    // *-символы
                    if (string.length == s && pattern.isNotEmpty()) {
//                        println("End string. found zeroOrAny in '${pattern.substring(p)}'")
                        while (pattern.length > p && pattern.isNextZeroOrMore(p)) {
                            p += 2
                        }
                    }
                }
            }

            val match = pattern.length == p && string.length == s
//            println("matching $match '$string' <> '$pattern'")
            return match
        }

        private fun isOnlyZeroOrMoreCharsToEnd(pattern: String, start: Int): Boolean {
            var p = start
            while (pattern.isZeroOrMore(p + 1)) {
                p += 2
            }
            return pattern.length == p
        }

        private fun println(message: String) {
            kotlin.io.println(message)
        }

        private fun String.isNextZeroOrMore(index: Int): Boolean =
            isZeroOrMore(index + 1)

        private fun String.isZeroOrMore(index: Int): Boolean =
            isMatch(index, '*')

        private fun Char.isAny(): Boolean = this == '.'
        private fun Char.isZeroOrMore(): Boolean = this == '*'

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

    object Solution3 {
        fun isMatch(string: String, pattern: String, s: Int = 0, p: Int = 0): Boolean {
            if (p >= pattern.length) return s >= string.length

            val first = s < string.length
                    && (string[s] == pattern[p] || pattern[p] == '.')

            return if (p < pattern.length - 1 && pattern[p + 1] == '*') {
                isMatch(string, pattern, s, p + 2)
                        || (first && isMatch(string, pattern, s + 1, p))
            } else {
                first && isMatch(string, pattern, s + 1, p + 1)
            }
        }
    }

    object Solution4 {
        fun isMatch(string: String, pattern: String): Boolean {
            if (pattern.isEmpty()) return false
            if (pattern.indexOf('.') == -1 && pattern.indexOf('*') == -1) {
                return string == pattern
            }
            return false
        }
    }
}


