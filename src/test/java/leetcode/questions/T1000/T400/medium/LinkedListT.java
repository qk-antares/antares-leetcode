package leetcode.questions.T1000.T400.medium;

import leetcode.common.ListNode;

public class LinkedListT {
    /*
     * 328. 奇偶链表
     */
    public ListNode oddEvenList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }

        ListNode oddHead = new ListNode();
        ListNode evenHead = new ListNode();
        ListNode odd = oddHead, even = evenHead;
        int i = 1;
        while (head != null) {
            if(i % 2 == 1){
                odd.next = head;
                odd = odd.next;
            } else {
                even.next = head;
                even = even.next;
            }
            head = head.next;
            i++;
        }

        odd.next = evenHead.next;
        even.next = null;
        return oddHead.next;
    }
}
