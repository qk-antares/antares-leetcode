package leetcode.questions.T1000.T200.medium;

import org.junit.jupiter.api.Test;

import leetcode.common.ListNode;

public class LinkedListT {
    /*
     * 143. 重排链表
     * 我的递归解法时间和空间复杂度太高
     * 
     * 正确的解法是，找到链表中点，将【中点之后】的反转，之后再将前半段（注意将中点.next=null）和后半段合并
     */
    public void reorderList0(ListNode head) {
        reverseListHelper(head);
    }

    public ListNode reverseListHelper(ListNode head) {
        ListNode headNext = head.next;

        if (head.next == null || headNext.next == null) {
            return head;
        }

        ListNode tail = headNext;
        ListNode tailPre = head;
        while (tail.next != null) {
            tailPre = tail;
            tail = tail.next;
        }

        head.next = tail;
        tailPre.next = null;
        tail.next = reverseListHelper(headNext);
        return head;
    }

    public void reorderList(ListNode head) {
        ListNode mid = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            mid = mid.next;
            fast = fast.next.next;
        }

        //mid是中点，将中点【之后】反转
        ListNode reverseHead = reverseList(mid.next);
        mid.next = null;
        
        mergeList(head, reverseHead);
    }

    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode reverseHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;

        return reverseHead;
    }

    public void mergeList(ListNode l1, ListNode l2) {
        ListNode p1 = l1, p2 = l2;
        while (p1 != null && p2 != null) {
            ListNode next1 = p1.next;
            ListNode next2 = p2.next;
            p1.next = p2;
            p2.next = next1;
            p1 = next1;
            p2 = next2;
        }
    }

    @Test    
    public void test() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        reorderList(head);
    }
}
