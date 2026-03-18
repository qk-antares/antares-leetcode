package leetcode.linklisttree.linklist;

import leetcode.common.ListNode;

/**
 * 前后指针
 */
public class PreSufT {
    /**
     * 19. 删除链表的倒数第 N 个结点 [Medium]
     * 
     * 快指针先走n步
     * 然后快慢指针同步走
     * 当快指针到达null时，满指针指向要被删除的节点
     * 当快指针到达最后一个节点时，满指针指向要被删除的前一个节点
     * 
     * 为防止删除头节点，使用虚拟头节点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode slow = dummy, fast = dummy;
        while (n > 0) {
            fast = fast.next;
            n--;
        }

        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;

        return dummy.next;
    }
}
