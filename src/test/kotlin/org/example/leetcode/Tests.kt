package org.example.leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("can calculate latest and earliest access timestamps")
class Tests {
    @Test
    fun `for one user logged in once`() {
        val userLog = arrayOf(arrayOf("user_1", "100", "resource_1"))

        assertEquals(
            mapOf("user_1" to listOf(100)),
            findEarliestAndLatestTimestamps(userLog)
        )
    }

    @Test
    fun `for one user logged in multiple times -- scenario 1`() {
        val userLog = arrayOf(
            arrayOf("user_1", "100", "resource_1"),
            arrayOf("user_1", "200", "resource_2"),
            arrayOf("user_1", "300", "resource_3")
        )

        assertEquals(
            mapOf("user_1" to listOf(100, 300)),
            findEarliestAndLatestTimestamps(userLog)
        )
    }

    @Test
    fun `for multiple users logged in multiple times -- scenario 2`() {
        val userLog = arrayOf(
            arrayOf("user_1", "100", "resource_1"),
            arrayOf("user_1", "200", "resource_2"),
            arrayOf("user_2", "300", "resource_1")
        )

        assertEquals(
            mapOf(
                "user_1" to listOf(100, 200),
                "user_2" to listOf(300)
            ),
            findEarliestAndLatestTimestamps(userLog)
        )
    }

    @Test
    fun `for multiple users logged in multiple times -- scenario 3`() {
        val userLog = arrayOf(
            arrayOf("user_1", "100", "resource_1"),
            arrayOf("user_1", "200", "resource_2"),
            arrayOf("user_2", "300", "resource_1"),
            arrayOf("user_1", "600", "resource_3"),
            arrayOf("user_2", "500", "resource_2"),
            arrayOf("user_3", "100", "resource_1"),
            arrayOf("user_2", "550", "resource_3"),
            arrayOf("user_3", "500", "resource_2"),
        )

        assertEquals(
            mapOf(
                "user_1" to listOf(100, 600),
                "user_2" to listOf(300, 550),
                "user_3" to listOf(100, 500),
            ),
            findEarliestAndLatestTimestamps(userLog)
        )
    }
}