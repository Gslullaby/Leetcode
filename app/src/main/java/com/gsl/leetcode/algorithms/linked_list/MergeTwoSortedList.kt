package com.gsl.leetcode.algorithms.linked_list

/**
 * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
 *
 * Example:
 * Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
 *
 * 题目考察点
 * 1.合并两个链表
 */
class MergeTwoSortedList {

    /**
     * 迭代
     *
     * 首先变量result指向一个新创建的哑结点
     * 在定义变量rCurr指向result
     * 两个变量l1Curr,l2Curr分别指向l1的head和l2的head
     * 开始遍历，循环条件为l1Curr != null && l2Curr != null
     *      比较l1Curr和l2Curr,得到较小的一方记为a
     *          rCurr.next = a
     *          rCurr = a
     *          同时a = a.next
     * 遍历完成后分别判断l1Curr和l2Curr是否为空，如果不为空则rCurr.next = l1Curr/l2Curr
     * 返回result.next
     *
     * 时间复杂度:O(m+n)
     * 空间复杂度:O(1)
     */
    fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        val result = ListNode(0)
        var rCurr = result
        var l1Curr = l1
        var l2Curr = l2
        while (l1Curr != null && l2Curr != null) {
            if (l1Curr.`val` < l2Curr.`val`) {
                rCurr.next = l1Curr
                rCurr = l1Curr
                l1Curr = l1Curr.next
            } else {
                rCurr.next = l2Curr
                rCurr = l2Curr
                l2Curr = l2Curr.next
            }
        }
        rCurr.next = l1Curr ?: l2Curr
        return result.next
    }

    /**
     * 递归
     * 递归的两个条件
     * 1.在每次调用自身时，必须(某种意义上)更接近于解
     * 2.必须有一个终止处理或计算的准则
     *
     * 终止条件为l1或者l2为null
     *
     * 时间复杂度:O(m+n)
     * 空间复杂度:O(m+n)
     *      因为需要m+n次合并，也就创建了m+n个栈帧
     */
    fun mergeTwoLists1(l1: ListNode?, l2: ListNode?): ListNode? {
        if (l1 == null)
            return l2
        if (l2 == null)
            return l1
        if (l1.`val` < l2.`val`) {
            l1.next = mergeTwoLists1(l1.next, l2)
            return l1
        } else {
            l2.next = mergeTwoLists1(l1, l2.next)
            return l2
        }
    }
}