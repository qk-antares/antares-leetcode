package leetcode.linklisttree;

import java.util.HashSet;
import java.util.Set;

import leetcode.common.ListNode;

/*
 * 删除链表中的节点
 */
public class DeleteNodeT {
    /*
     * 3217. 从链表中移除在数组中存在的节点
     */
        public ListNode modifiedList(int[] nums, ListNode head) {
        ListNode dummy = new ListNode();
        Set<Integer> set = new HashSet<>();
        for(int num : nums) set.add(num);

        ListNode cur = dummy;
        while(head != null) {
            if(!set.contains(head.val)) {
                cur.next = head;
                cur = head;
            }
            head = head.next;
        }
        cur.next = null;

        return dummy.next;
    }
}
