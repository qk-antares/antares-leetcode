package leetcode.questions.T1000.T300.esay;

import leetcode.common.ListNode;

public class LinkedListT {
    /*
     * 206. 反转链表
     * 递归解法和非递归
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode reverseNext = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return reverseNext;
    }

    public ListNode reverseList1(ListNode head) {
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

    /*
     * 234. 回文链表
     * 首先找到链表的中点，将中点及之后部分反转，然后依次对比前半部分和反转的后半部分
     */
    public boolean isPalindrome(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        if (fast != null) {
            slow = slow.next;
        }

        ListNode reverse = reverseList(slow);
        while (reverse != null) {
            if (head.val != reverse.val) {
                return false;
            }
            head = head.next;
            reverse = reverse.next;
        }
        return true;
    }

}
