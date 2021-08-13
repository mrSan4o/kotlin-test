package com.san4o.test.solution

import org.junit.Assert.*
import org.junit.Test
import com.san4o.test.solution.SolutionLongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring

class SolutionLongestSubstringWithoutRepeatingCharactersTest {

    @Test
    fun test1() {
        assertEquals(
            3,
            lengthOfLongestSubstring("abcabcbb")
        )
    }

    @Test
    fun test2() {
        assertEquals(
            1,
            lengthOfLongestSubstring("bbbbb")
        )
    }

    @Test
    fun test3() {
        assertEquals(
            3,
            lengthOfLongestSubstring("pwwkew")
        )
    }

    @Test
    fun test4() {
        assertEquals(
            0,
            lengthOfLongestSubstring("")
        )
    }

    @Test
    fun test5() {
        assertEquals(
            3,
            lengthOfLongestSubstring("abcabcbb")
        )
    }

    @Test
    fun test6() {
        assertEquals(
            2,
            lengthOfLongestSubstring("aab")
        )
    }

    @Test
    fun test7() {
        assertEquals(
            3,
            lengthOfLongestSubstring("dvdf")
        )
    }
    @Test
    fun test8() {
        assertEquals(
            1,
            lengthOfLongestSubstring(" ")
        )
    }
    @Test
    fun test9() {
        assertEquals(
            2,
            lengthOfLongestSubstring("au")
        )
    }
    @Test
    fun test10() {
        assertEquals(
            2,
            lengthOfLongestSubstring("cdd")
        )
    }
    @Test
    fun test11() {
        assertEquals(
            6,
            lengthOfLongestSubstring("ckclybxyyqsqieccych")
        )
    }

    @Test
    fun test12() {
        assertEquals(
            11,
            lengthOfLongestSubstring("jjsangmxbomryahpekexmyzrzjsu")
        )
    }
    @Test
    fun test13() {
        assertEquals(
            9,
            lengthOfLongestSubstring("fzsabvnkwwmnxbnskwaarhcuhcqxoxktzcv")
        )
    }
}