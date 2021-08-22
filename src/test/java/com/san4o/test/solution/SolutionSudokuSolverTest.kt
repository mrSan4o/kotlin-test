package com.san4o.test.solution

import org.junit.Assert.*
import org.junit.Test

class SolutionSudokuSolverTest {
    @Test
    fun testConvert() {
        assertArrays(
            expected = arrayOf(charArrayOf('1', '2', '3'), charArrayOf('4','5','6'), charArrayOf('7','8','9')),
            actual = stringToCharArray("[1,2,3],[4,5,6],[7,8,9]", 3)
        )
    }

    @Test
    fun test1() {
        assertSudokuSolver(
            input = "[5,3,.,.,7,.,.,.,.],[6,.,.,1,9,5,.,.,.],[.,9,8,.,.,.,.,6,.]," +
                    "[8,.,.,.,6,.,.,.,3],[4,.,.,8,.,3,.,.,1],[7,.,.,.,2,.,.,.,6]," +
                    "[.,6,.,.,.,.,2,8,.],[.,.,.,4,1,9,.,.,5],[.,.,.,.,8,.,.,7,9]",
            output = "[5,3,4,6,7,8,9,1,2],[6,7,2,1,9,5,3,4,8],[1,9,8,3,4,2,5,6,7]," +
                    "[8,5,9,7,6,1,4,2,3],[4,2,6,8,5,3,7,9,1],[7,1,3,9,2,4,8,5,6]," +
                    "[9,6,1,5,3,7,2,8,4],[2,8,7,4,1,9,6,3,5],[3,4,5,2,8,6,1,7,9]"
        )


    }

    private fun assertArrays(expected: Array<CharArray>, actual: Array<CharArray>) {
        assertEquals(expected.size, actual.size)

        for (i in expected.indices) {
            val expectedChars = expected[i]
            val actualChars = actual[i]
            assertEquals("row: $i. size comparing", expectedChars.size, actualChars.size)
            for (j in expectedChars.indices) {

                assertEquals("row: $i, column: $j", expectedChars[j], actualChars[j])
            }
        }
    }

    private fun assertSudokuSolver(input: String, output: String) {
        assertSudokuSolver(
            input = stringToCharArray(input),
            output = stringToCharArray(output)
        )
    }

    private fun stringToCharArray(string: String, size: Int = 9): Array<CharArray> {
        val result = Array<CharArray>(size) { CharArray(size) }
        var r = 0
        var a = 0
        for (i in string.indices) {
            val c = string[i]

            if (c == '[') {
                a = 0
            } else if (c == ']') {
                r++
            } else if (c != ',' && c != ']') {
                result[r][a++] = c
            }
        }
        return result
    }

    private fun assertSudokuSolver(input: Array<CharArray>, output: Array<CharArray>) {
        assertArrays(
            expected = output,
            actual = SolutionSudokuSolver.solveSudoku(input)
        )


    }
}