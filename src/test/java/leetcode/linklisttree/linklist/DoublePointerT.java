package leetcode.linklisttree.linklist;

import java.util.HashSet;
import java.util.Set;

import leetcode.common.ListNode;

/**
 * 双指针
 */
public class DoublePointerT {
    /**
     * 160. 相交链表 [Easy]
     * 
     * 解法1：使用Set
     * 解法2：计算两个链表的长度
     * 解法3：双指针，分别从两个链表的头部出发，走到末尾后转到另一个链表的头部继续走，直到相遇
     */
    public ListNode getIntersectionNode0(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }

        while (headB != null && !set.contains(headB)) {
            headB = headB.next;
        }
        return headB;
    }

    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        int len1 = getLen(headA);
        int len2 = getLen(headB);
        if (len1 > len2) {
            for (int i = 0; i < len1 - len2; i++) {
                headA = headA.next;
            }
        } else {
            for (int i = 0; i < len2 - len1; i++) {
                headB = headB.next;
            }
        }

        while (headA != null && headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }

        return headA;
    }

    int getLen(ListNode head) {
        int ans = 0;
        while (head != null) {
            head = head.next;
            ans++;
        }
        return ans;
    }

    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        ListNode curA = headA, curB = headB;
        while (curA != curB) {
            curA = curA.next;
            curB = curB.next;

            if (curA == null && curB == null)
                return null;
            else if (curA == null)
                curA = headB;
            else if (curB == null)
                curB = headA;
        }

        return curA;
    }
}
