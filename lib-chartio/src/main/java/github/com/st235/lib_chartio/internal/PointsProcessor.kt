package github.com.st235.lib_chartio.internal

import kotlin.math.abs

internal class PointsProcessor {

    /**
     * Points list is needed for a binary search afterward
     */
    private val xCoords: MutableList<Float> = mutableListOf()
    private val yCoords: MutableList<Float> = mutableListOf()

    private val extras: MutableList<Any> = mutableListOf()

    fun addPoint(x: Float, y: Float, extra: Any) {
        xCoords.add(x)
        yCoords.add(y)
        extras.add(extra)
    }

    fun get(index: Int): Triple<Float, Float, Any> = Triple(xCoords[index], yCoords[index], extras[index])

    /**
     * Modified binary search mechanism
     * Looking for a nearest
     *
     * Complexity: O(log n)
     */
    fun findNearestTo(x: Float, y: Float): Triple<Float, Float, Any>? {
        if (xCoords.size == 0) {
            return null
        }

        var left = 0
        var right = xCoords.size - 1

        while (left <= right) {
            if (left == right) {
                val currentDistance = x.distanceToNearestIfPossible(left)
                val leftDistance = x.distanceToNearestIfPossible(left - 1)
                val rightDistance = x.distanceToNearestIfPossible(left + 1)

                if (currentDistance < leftDistance &&
                    currentDistance < rightDistance) {
                    return Triple(xCoords[left], yCoords[left], extras[left])
                }

                if (leftDistance < rightDistance) {
                    return Triple(xCoords[left - 1], yCoords[left - 1], extras[left - 1])
                }

                return Triple(xCoords[left + 1], yCoords[left + 1], extras[left + 1])
            }

            val middle = left + (right - left) / 2

            if (xCoords[middle] == x) {
                return Triple(xCoords[middle], yCoords[middle], extras[left])
            }

            if (x < xCoords[middle]) {
                right = middle
            } else {
                left = middle + 1
            }
        }

        return null
    }

    /**
     * Calculates Cartesian distance between two coordinates
     */
    private fun Float.distanceToNearestIfPossible(index: Int): Float {
        if (index >= 0 && index < xCoords.size) {
            return abs(this - xCoords[index])
        }
        return Float.MAX_VALUE
    }

    fun clear() {
        xCoords.clear()
        yCoords.clear()
        extras.clear()
    }
}
