package leetcode.linklisttree.linklist;

import leetcode.common.ListNode;

/**
 * 快慢指针
 */
public class SlowFastT {
    /**
     * 876. 链表的中间结点 [Easy]
     */
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 2095. 删除链表的中间节点 [Medium]
     */
    public ListNode deleteMiddle(ListNode head) {
        ListNode slow = head, fast = head;
        ListNode dummy = new ListNode();
        dummy.next = slow;
        ListNode slowPre = dummy;
        while (fast != null && fast.next != null) {
            slowPre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        slowPre.next = slow.next;
        return dummy.next;
    }

    /**
     * 234. 回文链表 [Easy]
     * 
     * 快慢指针找到中点，只反转中点之后的
     */
    public boolean isPalindrome(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        if (fast != null)
            slow = slow.next;
        ListNode r = reverse(slow);
        while (r != null) {
            if (r.val != head.val)
                return false;
            r = r.next;
            head = head.next;
        }
        return true;
    }

    ListNode reverse(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode ans = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return ans;
    }

    /**
     * 141. 环形链表 [Easy]
     * 
     * 快慢指针，最终快指针会完成对慢指针的套圈
     */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast)
                return true;
        }
        return false;
    }

    /**
     * 142. 环形链表 II [Medium]
     * 
     * 找到环中相遇点，从头节点和相遇点同步前进，再次相遇点即为环的入口
     */
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }

        return null;
    }

    /**
     * 287. 寻找重复数 [Medium]
     * 
     * 可以转化为链表中寻找环的入口问题，数组值作为指针
     */
    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
