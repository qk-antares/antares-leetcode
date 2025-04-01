package leetcode.questions.T1000.T100;

import leetcode.common.ListNode;

public class LinkedListT {
    /*
     * 21. 合并两个有序链表 [Easy]
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        while (list1 != null && list2 != null) {
            if(list1.val < list2.val) {
                cur.next = list1;
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }

        if(list1 != null) {
            cur.next = list1;
        }
        if(list2 != null) {
            cur.next = list2;
        }
        return dummy.next;
    }
}
