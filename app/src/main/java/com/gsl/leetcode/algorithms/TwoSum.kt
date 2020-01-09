package com.gsl.leetcode.algorithms

import java.lang.IllegalStateException

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
class TwoSum {

    /**
     * 双循环遍历寻找result，
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val result = IntArray(2)
        for ((i, e) in nums.withIndex()) {
            for (j in (i + 1) until nums.size) {
                if (e + nums[j] == target) {
                    result[0] = i
                    result[1] = j
                    return result
                }
            }
        }
        return result
    }

    /**
     * 借助HashMap，只需要一次遍历就可以找到result
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * HashMap的特点：支持以'近似'恒定的时间进行快速查找。我用“近似”来描述，是因为一旦出现冲突，查找用时可能会退化到 O(n)
     * 但只要你仔细地挑选哈希函数，在哈希表中进行查找的用时应当被摊销为 O(1)。
     * 而遍历的过程中如果未命中，则可以将当前元素及下标存入HashMap中
     */
    fun twoSum1(nums: IntArray, target: Int): IntArray {
        val map = hashMapOf<Int, Int>()
        for ((i, e) in nums.withIndex()) {
            val diff = target - e
            val index = map[diff]
            if(index!=null)
                return intArrayOf(index,i)
            map[e] = i
        }
        throw IllegalStateException("no solution found")
    }
}