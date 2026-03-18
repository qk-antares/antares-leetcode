package leetcode.linklisttree.linklist;

import org.junit.jupiter.api.Test;

import leetcode.common.ListNode;

/**
 * 反转链表
 */
public class ReverseT {
    /**
     * 206. 反转链表 [Easy] <Star>
     * 
     * 非递归的方法再看看:
     * 把原链表的结点从前往后摘下来，然后作为新的头节点
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode ans = reverseList(head.next);
        head.next.next = head;
        head.next = null;

        return ans;
    }

    public ListNode reverseList0(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 24. 两两交换链表中的节点 [Medium]
     * 
     * 交换两个，然后递归
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode next = head.next;
        head.next = swapPairs(head.next.next);
        next.next = head;

        return next;
    }

    /**
     * 25. K 个一组翻转链表 [Hard]
     * 
     * 从头节点往后遍历，依次找到长度为k的段，将其翻转，然后继续往后找下一段，直到链表末尾
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        int i = k - 1;
        ListNode tmp = head;
        while (i > 0 && tmp != null) {
            tmp = tmp.next;
            i--;
        }
        if (i > 0 || tmp == null) {
            return head;
        }

        ListNode next = tmp.next;
        tmp.next = null;
        reverseList(head);
        head.next = reverseKGroup(next, k);
        return tmp;
    }

    @Test
    public void test() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        reverseKGroup(head, 2);
    }
}
