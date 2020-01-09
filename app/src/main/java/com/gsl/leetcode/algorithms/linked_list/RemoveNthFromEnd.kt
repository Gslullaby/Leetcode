package com.gsl.leetcode.algorithms.linked_list

/**
 * Given a linked list, remove the n-th node from the end of list and return its head.
 * Example:
 * Given linked list: 1->2->3->4->5, and n = 2.
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 *
 * 改题目的重点为
 * 1.如何倒序定位节点
 * 2.如何删除元素：删除一个节点，则必须要拿到该节点的前驱节点
 * 3.边界条件，如果要删除的是头节点，改如何处理，借助哑节点/判断null
 */
class RemoveNthFromEnd {

    /**
     * 第一眼的想法
     * 1.首先想到使用间距为n双指针遍历链表，直至链表末尾
     * 2.再想到了单链表删除一个节点的条件：需要定位到待删除节点的前驱节点
     *
     * 思路
     * 1.先要创建两个节点变量first和second，分别指向null和head
     * 2.定义一个index变量初始值为0，即当前遍历到的节点的index
     * 3.开始遍历链表，循环条件是second!=null
     *      首先判断first是否为null
     *          如果为null则先判断 index == n
     *          如果满足则first = head
     *          不满足则index ++
     *      如果不为null则first = first.next
     *      second = second.next
     * 4.遍历完成后，判断
     *      如果first为null，则表示要删除第一个元素直接返回head.next
     *      如果不为null，则删除first的next节点
     *
     * 时间复杂度:O(n)
     * 空间复杂度:O(1)
     */
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        var index = 0
        var first: ListNode? = null
        var second: ListNode? = head
        while (second != null) {
            if (first == null) {
                if (index == n) {
                    first = head
                } else {
                    index++
                }
            } else {
                first = first.next
            }
            second = second.next
        }
        if (first == null)
            return head?.next
        first.next = first.next?.next
        return head
    }

    /**
     * 这种写法的原理没变，只不过是先循环让second先跳过前n个元素，再让first起跳
     * 边界条件借助了哑节点
     */
    fun removeNthFromEnd1(head: ListNode?, n: Int): ListNode? {
        var index = 0
        var second: ListNode? = head
        while (index < n) {
            second = second?.next
            index++
        }
        val muteNode = ListNode(0)
        muteNode.next = head
        var first: ListNode? = muteNode
        while (second != null) {
            second = second.next
            first = first?.next
        }
        if (first == muteNode)
            return head?.next
        else {
            first?.next = first?.next?.next
        }
        return head
    }

    /**
     * 这种解法的思路：借助额外的空间（即list）来克服链表无法随机访问的缺点
     * 1.创建一个空的ArrayList
     * 2.遍历链表，将所有节点添加至ArrayList中
     * 3.找到待删除节点的前驱节点的下标，即
     *      preIndex = list.size - n - 1
     *      需要注意到如果删除的是head，则preIndex可能小于0
     * 4.所以判断
     *      如果preIndex < 0 则返回head.next
     *      否则list[preIndex].next = list[preIndex + 1].next
     *
     * 时间复杂度:O(n)
     * 空间复杂度:O(n)
     */
    fun removeNthFromEnd2(head: ListNode?, n: Int): ListNode? {
        val list = mutableListOf<ListNode>()
        var node = head
        while (node != null) {
            list.add(node)
            node = node.next
        }
        val preIndex = list.size - n - 1
        return if (preIndex < 0) {
            head?.next
        } else {
            list[preIndex].next = list[preIndex + 1].next
            head
        }
    }
}