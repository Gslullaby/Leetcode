package com.gsl.leetcode.algorithms.heap

/**
 * 二叉堆：最大堆、最小堆
 * 拥有两种属性约束
 * 1.完全二叉树
 * 2.堆
 */
interface BinaryHeap<in T> {

    fun add(t: T)

    fun remove()

}