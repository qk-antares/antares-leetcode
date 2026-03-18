package leetcode.linklisttree.linklist;

import java.util.PriorityQueue;

import leetcode.common.ListNode;

/**
 * 合并链表
 */
public class MergeListT {
    /**
     * 2. 两数相加 [Medium]
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int add = 0;// 表示进位
        ListNode ans = new ListNode();
        ListNode cur = ans;
        while (l1 != null && l2 != null) {
            int sum = add + l1.val + l2.val;
            add = sum / 10;
            cur.next = new ListNode(sum % 10);
            l1 = l1.next;
            l2 = l2.next;
            cur = cur.next;
        }

        while (l1 != null) {
            int sum = add + l1.val;
            add = sum / 10;
            cur.next = new ListNode(sum % 10);
            l1 = l1.next;
            cur = cur.next;
        }

        while (l2 != null) {
            int sum = add + l2.val;
            add = sum / 10;
            cur.next = new ListNode(sum % 10);
            l2 = l2.next;
            cur = cur.next;
        }

        if (add != 0) {
            cur.next = new ListNode(add);
        }

        return ans.next;
    }

    /**
     * 21. 合并两个有序链表 [Easy]
     * 
     * 解法1：双指针
     * 解法2：递归，比较两个链表的头结点，较小的头结点作为结果链表的头结点，然后递归合并剩余的链表
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                cur.next = list1;
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }

        while (list1 != null) {
            cur.next = list1;
            list1 = list1.next;
            cur = cur.next;
        }

        while (list2 != null) {
            cur.next = list2;
            list2 = list2.next;
            cur = cur.next;
        }

        return dummy.next;
    }

    public ListNode mergeTwoLists0(ListNode list1, ListNode list2) {
        if (list1 == null)
            return list2;
        if (list2 == null)
            return list1;

        if (list1.val < list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }

    /**
     * 148. 排序链表 [Hard]
     * 
     * 递归，找到中点，对前后两段进行排序，合并
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode mid = middleNode(head);
        return mergeTwoLists(sortList(head), sortList(mid));
    }

    ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        ListNode slowPre = head;
        while (fast != null && fast.next != null) {
            slowPre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        slowPre.next = null;
        return slow;
    }

    /**
     * 23. 合并 K 个升序链表 [Hard]
     * 
     * 解法1：使用堆维护每个链表的头结点，每次取出最小的头结点，加入结果链表，并把这个链表的下一个结点加入堆中
     * 解法2：归并，两两合并链表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((o1, o2) -> o1.val - o2.val);
        for (ListNode head : lists) {
            if (head != null)
                pq.add(head);
        }

        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        while (!pq.isEmpty()) {
            ListNode tmp = pq.poll();
            cur.next = tmp;
            cur = cur.next;

            if (tmp.next != null) {
                pq.offer(tmp.next);
            }
        }

        return dummy.next;
    }

    public ListNode mergeKLists0(ListNode[] lists) {
        int len = lists.length;
        if (len == 0) {
            return null;
        }

        return mergeKLists(lists, 0, len - 1);
    }

    public ListNode mergeKLists(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        }
        if (end - start == 1) {
            return mergeTwoLists0(lists[start], lists[end]);
        }
        int mid = (start + end) / 2;
        return mergeTwoLists0(mergeKLists(lists, start, mid), mergeKLists(lists, mid + 1, end));
    }
}
