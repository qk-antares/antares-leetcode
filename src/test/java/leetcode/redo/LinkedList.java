package leetcode.redo;

import leetcode.common.ListNode;

public class LinkedList {
    /**
     * 奇偶链表，我的解法太麻烦，应该用双指针解
     */
    class OddEvenList {
        public ListNode oddEvenList(ListNode head) {
            if(head == null || head.next == null){
                return head;
            }

            ListNode l1 = head;
            ListNode l2 = head.next;
            ListNode cur1 = l1, cur2 = l2;

            int count = 2;
            head = head.next.next;
            while (head != null){
                if(count % 2 == 0){
                    cur1.next = head;
                    cur1 = cur1.next;
                } else {
                    cur2.next = head;
                    cur2 = cur2.next;
                }
                head = head.next;
                count++;
            }

            cur2.next = null;
            cur1.next = l2;
            return l1;
        }

        public ListNode oddEvenList0(ListNode head) {
            if(head == null || head.next == null){
                return head;
            }

            ListNode l1 = head;
            ListNode l2 = head.next;
            ListNode head2 = l2;

            while (l2 != null && l2.next != null){
                l1.next = l1.next.next;
                l2.next = l2.next.next;
                l1 = l1.next;
                l2 = l2.next;
            }

            l1.next = head2;
            return head;
        }
    }

    /**
     * 回文链表（先翻转后半部分链表，然后再逐一进行比较）
     */
    class IsPalindrome {
        public boolean isPalindrome(ListNode head) {
            ListNode slow = head, fast = head;
            while (fast != null && fast.next != null){
                slow = slow.next;
                fast = fast.next.next;
            }

            //奇数个结点
            if(fast != null){
                slow = slow.next;
            }

            ListNode reverseList = reverseList0(slow);
            while (reverseList != null){
                if(head.val != reverseList.val){
                    return false;
                }
                head = head.next;
                reverseList = reverseList.next;
            }

            return true;
        }

        public ListNode reverseList(ListNode head){
            //这里的head == null仅仅为了防止传进来的head直接为null
            if(head == null || head.next == null)
                return head;

            ListNode newHead = reverseList(head.next);
            head.next.next = head;
            head.next = null;
            return newHead;
        }

        /**
         * 反转链表的非递归写法，效率明显更高
         */
        public ListNode reverseList0(ListNode head){
            if(head == null)
                return null;

            //这里的head == null仅仅为了防止传进来的head直接为null
            ListNode newHead = new ListNode();
            newHead.next = head;

            ListNode pre = head, cur = head.next;

            while (cur != null){
                newHead.next = cur;
                pre.next = cur.next;
                cur.next = newHead.next;

                cur = pre.next;
            }

            return newHead.next;
        }
    }

    /**
     * 合并两个有序链表
     */
    class MergeTwoLists {
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            ListNode newHead = new ListNode();
            ListNode cur = newHead;

            while (list1 != null && list2 != null){
                if(list1.val > list2.val){
                    cur.next = list2;
                    list2 = list2.next;
                } else {
                    cur.next = list1;
                    list1 = list1.next;
                }
                cur = cur.next;
            }

            while (list1 != null){
                cur.next = list1;
                list1 = list1.next;
                cur = cur.next;
            }

            while (list2 != null){
                cur.next = list2;
                list2 = list2.next;
                cur = cur.next;
            }

            return newHead.next;
        }
    }

    /**
     * 两数相加
     */
    class AddTwoNumbers {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode newHead = new ListNode();
            ListNode cur = newHead;

            int temp;
            int add = 0;
            int ans;
            while (l1 != null && l2 != null){
                temp = l1.val + l2.val + add;
                ans = temp % 10;
                cur.next = new ListNode(ans);
                add = temp / 10;
                cur = cur.next;

                l1 = l1.next;
                l2 = l2.next;
            }

            while (l1 != null){
                temp = l1.val + add;
                ans = temp % 10;
                cur.next = new ListNode(ans);
                add = temp / 10;
                cur = cur.next;

                l1 = l1.next;
            }

            while (l2 != null){
                temp = l2.val + add;
                ans = temp % 10;
                cur.next = new ListNode(ans);
                add = temp / 10;
                cur = cur.next;

                l2 = l2.next;
            }

            if(add > 0){
                cur.next = new ListNode(add);
            }

            return newHead.next;
        }
    }
}
