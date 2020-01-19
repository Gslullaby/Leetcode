package com.gsl.leetcode.algorithms.linked_list

/**
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 *
 * Example:
 * Input:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * Output: 1->1->2->3->4->4->5->6
 *
 * 有很多中解法
 * 1.归并merge
 *      因为merge两个链表很容易
 *      将list两两分组进行merge
 *      如此循环，直至剩下一个list
 * 2.采用与merge两个链表的同样的方式
 *      使用一个list保存所有链表当前的遍历的位置
 * 3.分治大法
 *      递归实现
 *      非递归实现
 */
class MergeKSortedList {

    /**
     * 采用与merge两个链表相同的方式
     * 1.创建result指向哑结点
     * 2.创建rCurr指向result表示result的tail
     * 4.定义nullCount = 0
     * 5.定义minIndex = -1
     * 6.开始循环
     *      循环条件为!oneLeft
     *      遍历array找到min value index，同时计数nullCount
     *
     *      如果nullCount < array.size - 1
     *          rCurr.next = array[minIndex]
     *          rCurr = array[minIndex]
     *          array[index] = array[minIndex].next
     *      否则直接break
     * 7.返回result.next
     *
     * 记list的长度为size，链表平均长度为length
     * 时间复杂度:O(length * size * size)
     * 空间复杂度:O(size)
     *
     * 这种方法的缺点
     * 1.由于每次都需要从length个节点中选取一个min，效率奇低
     *
     * 方法的改进
     * 1.可以通过node的val构建一个最小堆，堆顶Node即为最小元素
     */
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        val result = ListNode(0)
        var rCurr = result
        var nullCount = 0
        var minIndex = -1
        val nullMax = lists.size - 1
        var min: ListNode? = null
        while (nullCount < nullMax) {
            nullCount = 0
            minIndex = -1
            min = null
            for ((i, e) in lists.withIndex()) {
                if (e == null) {
                    nullCount++
                    continue
                } else if (min == null) {
                    min = e
                    minIndex = i
                } else {
                    if (e.`val` < min.`val`) {
                        min = e
                        minIndex = i
                    }
                }
            }
            if (min != null) {
                rCurr.next = min
                rCurr = min
                lists[minIndex] = min.next
            }
        }
        return result.next
    }

    /**
     * 遍历数组从头开始逐渐merge
     * 1.定义变量result为null
     * 2.从头开始遍历lists
     *      如果lists[i]==null则continue
     *      否则调用mergeTwoSortedList，传入result和lists[i]，得到结果ri
     *      result = ri
     * 3.遍历结束返回result
     *
     * 记list的长度为size，链表平均长度为length
     * 时间复杂度:O(length * size)
     * 空间复杂度:O(size)
     */
    fun mergeKLists1(lists: Array<ListNode?>): ListNode? {
        var result: ListNode? = null
        lists.forEach {
            if (it != null) {
                result = mergeTwoSortedList(result, it)
            }
        }
        return result
    }

    fun mergeTwoSortedList(l1: ListNode?, l2: ListNode?): ListNode? {
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
     * 归并分合大法（递归实现）
     * 万万没想到，这方法效率这么高，哭了，这可是递归啊，220ms就跑完了
     *
     * 时间复杂度:O(length * log(size))
     * 空间复杂度:O(size)
     */
    fun mergeKLists2(lists: Array<ListNode?>): ListNode? {
        return when (lists.size) {
            0 -> null
            1 -> lists[0]
            2 -> mergeTwoSortedList(lists[0], lists[1])
            else -> {
                val splitSize = lists.size / 2
                val arr1 = arrayOfNulls<ListNode?>(splitSize)
                val arr2 = arrayOfNulls<ListNode?>(lists.size - splitSize)
                System.arraycopy(lists, 0, arr1, 0, splitSize)
                System.arraycopy(lists, splitSize, arr2, 0, arr2.size)
                mergeTwoSortedList(mergeKLists2(arr1), mergeKLists2(arr2))
            }
        }
    }

    /**
     * 归并分合大法（非递归实现）
     * 思路：数组中首尾链表依次合并，并将合并后的结果存储在数组的前半部分中，直至数组中只剩一个链表
     * 1.声明变量right = size，right表示数组中真实的链表的个数，每遍历一遍right都会减半
     * 2.开始while循环，循环的条件是right < 1
     * 3.开始遍历数组，i从0至right/2
     *      合并首尾两个链表，lists[i] = mergeTwoSortedList(lists[i], list[right-i])
     * 4.遍历完成后，right = (right + 1) / 2
     * 5.return lists[0]
     *
     * 该方法的难点在于每次合并完成后要正确的计算出right的值，
     *      如果size为奇数，则进行一次遍历合并后right应该为 size / 2 + 1
     *      如果size为偶数，则进行一次遍历合并后right应该为 size / 2
     *      巧妙之处在于如果为偶数则 (size + 1) / 2 = size / 2 , 如果为奇数则(size + 1) / 2 = size / 2 + 1
     *
     * 要善于找到本质问题，然后在考虑如何解决!!!
     * 时间复杂度:O(length * log(size))
     * 空间复杂度:O(1)
     */
    fun mergeKLists3(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty())
            return null
        var right = lists.size
        while (right > 1) {
            for (i in 0 until right / 2) {
                lists[i] = mergeTwoSortedList(lists[i], lists[right - i - 1])
            }
            right = (right + 1) / 2
        }
        return lists[0]
    }

    fun mergeKLists4(lists: Array<ListNode?>): ListNode? {
        return null
    }
}