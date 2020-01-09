package com.gsl.leetcode.algorithms.linked_list

/**
 * 时间复杂度O(max(m,n))
 * 空间复杂度O(max(m,n) + 1)
 *
 * 单链表的特性
 * 1.当访问某一节点后，只能继续访问其后继节点，而无法访问其前驱节点，
 * 2.相对于数组来说，无法根据index进行随机访问，这也就导致了在index位置插入删除时需要遍历到index节点才行
 * 3.插入删除元素在不考虑到遍历到特定位置的前提下时间复杂度为O(1)
 * 4.链表时非线性的，无需提前预估存储空间，而数组定长，在初始化时已经确定所需占用的内存
 *
 * Note
 * 1.哑节点的使用
 *   哑节点:避免处理头节点为空的边界问题的作用，减少代码执行异常的可能性。通过一个哑节点避开对头节点删除时的特殊处理
 */
class AddTwoNumbers {

    /**
     * 初次提交
     * 1.没有想清楚循环的条件
     * 2.没有想好应该什么时候跳转next
     * 3.不要基于动手，当思路清晰了再开始写，可以先尝试写伪代码
     */
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        if (l2 == null)
            return l1
        if (l1 == null)
            return l2
        var carry: Int
        var nextL1 = l1
        var nextL2 = l2
        var sum = l1.`val` + l2.`val`
        l1.`val` = sum % 10
        carry = sum / 10
        var currL1: ListNode?
        while (true) {
            currL1 = nextL1
            nextL1 = nextL1?.next
            nextL2 = nextL2?.next
            if (nextL1 != null && nextL2 != null) {
                sum = nextL1.`val` + nextL2.`val` + carry
                nextL1.`val` = sum % 10
                carry = if (sum >= 10) 1 else 0
            } else {
                if (nextL1 == null)
                    currL1?.next = nextL2
                nextL1 = currL1?.next
                while (carry != 0 && nextL1 != null) {
                    sum = nextL1.`val` + carry
                    nextL1.`val` = sum % 10
                    carry = if (sum >= 10) 1 else 0
                    currL1 = nextL1
                    nextL1 = nextL1.next
                }
                if (carry != 0) {
                    currL1?.next =
                        ListNode(carry)
                }
                return l1
            }
        }
    }

    /**
     * 这种解法优点在于
     * 1.while循环的条件，只要有其中一个链表没遍历到末尾或者有进位时都需要进行sum的计算
     * 2.创建下一个ListNode的时机
     * 3.写法简洁
     * 4.对哑节点的使用
     * 缺点
     * 1.在连条链表不等长的情况下，遍历至某个链表末尾时，再继续遍历会执行无用代码，即空node的next以及val
     * 2.当遍历至两条链表的末尾时，如果carry不为0则循环中的sum及carry的计算是无效的，会带来额外的时间成本
     */
    fun addTwoNumbers1(l1: ListNode?, l2: ListNode?): ListNode? {
        val head = ListNode(0)
        var carry = 0
        var nL1 = l1
        var nL2 = l2
        var tail: ListNode? = head
        while (nL1 != null || nL2 != null || carry != 0) {
            // 这里很巧妙，因为只有在确定上面判断的情况下才需要新增一个ListNode
            // 因此tail = tail?.next 不能像nL1 = nL1?.next 那样放在末尾
            // 否则就会出现链表尾部为0的情况
            // 这种写法在head空出来一个0，只要返回head.next即可
            tail?.next = ListNode(0)
            tail = tail?.next
            val sum = (nL1?.`val` ?: 0) + (nL2?.`val` ?: 0) + carry
            tail?.`val` = sum % 10
            carry = if (sum >= 10) 1 else 0
            nL1 = nL1?.next
            nL2 = nL2?.next
        }
        return head.next
    }
}
