package org.example.leetcode

/**
 * Problem:
 *
 * Given log files of users logged into a system with their respective 'token' and access timestamp in seconds;
 * we want to calculate for each user the earliest and latest access timestamp.
 *
 * 1. Example incoming data structure:
 * [
 *  ["user_1", "100", "resource_1"],
 *  ["user_1", "200", "resource_2"],
 *  ["user_1", "300", "resource_3"]
 * ]
 *
 * 1. expected result:
 * {
 *  "user_1": [100, 300]
 * }
 * ---
 *
 * 2. Example incoming data structure:
 * [
 *  ["user_1", "100", "resource_1"],
 *  ["user_1", "200", "resource_2"],
 *  ["user_2", "300", "resource_1"]
 * ]
 *
 * 2. expected result:
 * {
 *  "user_1": [100, 200],
 *  "user_2": [300]
 * }
 * ---
 *
 * 3. Example incoming data structure:
 * [
 *  ["user_1", "100", "resource_1"],
 *  ["user_1", "200", "resource_2"],
 *  ["user_2", "300", "resource_1"],
 *  ["user_1", "600", "resource_3"],
 *  ["user_2", "500", "resource_2"],
 *  ["user_3", "100", "resource_1"],
 *  ["user_2", "550", "resource_3"],
 *  ["user_3", "500", "resource_2"]
 * ]
 *
 * 3. expected result:
 * {
 *  "user_1": [100, 600],
 *  "user_2": [300, 550],
 *  "user_3: [100, 500]
 * }
 * ---
 */

/**
 * Solution 1 -- the long way.
 */
fun findEarliestAndLatestTimestamps(userLogs: Array<Array<String>>): Map<String, List<Int>> {
    val materializedLog = mutableMapOf<String, List<Int>>()
    var earliestTimestamp = 0
    var latestTimestamp = 0

    userLogs.forEach { userLog ->
        val user = userLog[0]
        val timestamp = userLog[1].toInt()

        // check what are the current recorded earliest and latest timestamps.
        materializedLog[user]?.let { recordedTimestamps ->
            if (recordedTimestamps.isNotEmpty()) {
                earliestTimestamp = recordedTimestamps.minOrNull()!!
                latestTimestamp = recordedTimestamps.maxOrNull()!!
            } else
                throw IllegalStateException("Earliest and latest timestamps of a user cannot be empty")
        } ?: run {
            // reset to `0` if no recorded timestamps for user.
            earliestTimestamp = 0
            latestTimestamp = 0
        }

        // set the earliest if current timestamp is less than previous recorded.
        earliestTimestamp =
            if (earliestTimestamp.hasNoValue()) timestamp
            else timestamp.takeIfLessThan(earliestTimestamp) ?: earliestTimestamp

        // set the latest if current timestamp is greater than previous recorded.
        latestTimestamp =
            if (latestTimestamp.hasNoValue()) timestamp
            else timestamp.takeIfGreaterThan(latestTimestamp) ?: latestTimestamp

        // update the data structure.
        materializedLog[user] =
            if (loggedInOnce(earliestTimestamp, latestTimestamp)) listOf(earliestTimestamp)
            else listOf(earliestTimestamp, latestTimestamp)
    }
    return materializedLog
}

/**
 * Solution 2 -- the clean and easy way.
 */
fun findEarliestAndLatestTimestampsV2(userLogs: List<List<String>>): Map<String, List<String>> {
    return userLogs.groupBy({it[0]}, {it[1]})
        .mapValues {
            if (it.value.size == 1) listOf(it.value.first())
            else listOf(it.value.minOrNull()!!, it.value.maxOrNull()!!)
        }
}

/**
 * Represents the beginning of traversing the array.
 */
private fun Int.hasNoValue(): Boolean = this == 0

/**
 * If signed in only once, either earliest or latest works the same.
 */
private fun loggedInOnce(earliestTimestamp: Int, latestTimestamp: Int): Boolean = earliestTimestamp == latestTimestamp

private fun Int.takeIfLessThan(earliestTimestamp: Int): Int? = takeIf { it < earliestTimestamp }
private fun Int.takeIfGreaterThan(latestTimestamp: Int): Int? = takeIf { it > latestTimestamp }
