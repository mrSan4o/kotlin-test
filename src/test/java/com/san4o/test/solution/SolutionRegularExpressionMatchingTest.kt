package com.san4o.test.solution

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class SolutionRegularExpressionMatchingTest {

    @Test
    fun test01() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "aaab",
                p = "a*b"
            )
        )
    }
    @Test
    fun test02() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "aaab",
                p = "a*ab"
            )
        )
    }
    @Test
    fun test03() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "aabad",
                p = "a*b*ad"
            )
        )
    }

    @Test
    fun test1() {
        assertFalse(
            SolutionRegularExpressionMatching.isMatch(
                s = "aa",
                p = "a"
            )
        )
    }
    @Test
    fun test2() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "aa",
                p = "a*"
            )
        )
    }
    @Test
    fun test3() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "ab",
                p = ".*"
            )
        )
    }
    @Test
    fun test4() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "aab",
                p = "c*a*b"
            )
        )
    }
    @Test
    fun test5() {
        assertFalse(
            SolutionRegularExpressionMatching.isMatch(
                s = "mississippi",
                p = "mis*is*p*."
            )
        )
    }
    @Test
    fun test6() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "mississippi",
                p = "mis*is*ip*."
            )
        )
    }
    @Test
    fun test7() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "aaa",
                p = "a*a"
            )
        )
    }
    @Test
    fun test8() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "aaa",
                p = "ab*a*c*a"
            )
        )
    }
    @Test
    fun test9() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "aaca",
                p = "ab*a*c*a"
            )
        )
    }
    @Test
    fun test10() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "a",
                p = "ab*"
            )
        )
    }
    @Test
    fun test11() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "bbbba",
                p = ".*a*a"
            )
        )
    }
    @Test
    fun test12() {
        assertFalse(
            SolutionRegularExpressionMatching.isMatch(
                s = "ab",
                p = ".*c"
            )
        )
    }
    @Test
    fun test13() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "ab",
                p = ".*.."
            )
        )
    }
    @Test
    fun test14() {
        assertFalse(
            SolutionRegularExpressionMatching.isMatch(
                s = "a",
                p = ".*..a"
            )
        )
    }
    @Test
    fun test15() {
        assertFalse(
            SolutionRegularExpressionMatching.isMatch(
                s = "a",
                p = ".*..a*"
            )
        )
    }
    @Test
    fun test16() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "ab",
                p = ".*..c*"
            )
        )
    }
    @Test
    fun test17() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "abbabaaaaaaacaa",
                p = "a*.*b.a.*c*b*a*c*"
            )
        )
    }
    @Test
    fun test170() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "baaaaaaacaa",
                p = ".*c*b*a*c*"
            )
        )
    }
    @Test
    fun test171() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "",
                p = "c*"
            )
        )
    }
    @Test
    fun test172() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "caa",
                p = "c*b*a*c*"
            )
        )
    }
    @Test
    fun test18() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "abcaaaaaaabaabcabac",
                p = ".*ab.a.*a*a*.*b*b*"
            )
        )
    }
    @Test
    fun test180() {
        assertTrue(
            SolutionRegularExpressionMatching.isMatch(
                s = "abcaaaabac",
                p = ".*ab.a.*b*b*"
            )
        )
    }


}