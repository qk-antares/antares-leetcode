package leetcode.questions.Easy;

import leetcode.common.ListNode;

import java.util.HashSet;
import java.util.Stack;

/**
 * @author Antares
 * @date 2022/9/4
 */
public class EasyList {
    /**
     * 删除链表中的节点
     * 我的解法，由于只知道要被删除的结点，而不知道该节点之前的结点，因此我们可以把该结点之后的结点搬到前面去
     */
    public void deleteNode(ListNode node) {
        while (node.next != null){
            node.val = node.next.val;
            if(node.next.next == null){
                node.next = null;
                break;
            }
            else
                node = node.next;
        }
    }

    /**
     * 答案解法（两行代码...根本用不着循环）
     */
    public void deleteNode0(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     * 删除链表的倒数第N个节点
     * 我的解法：（没有考虑到删除的是头节点的情况）效率还行
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int len = getListLen(head);
        if(len == 1)
            return null;
        if(len == n)
            return head.next;
        //倒数第n就是正数第len-n+1
        n = len - n + 1;
        //首先要找到这个节点之前的那个结点
        ListNode pre = head;
        while (n > 1){
            pre = pre.next;
            n--;
        }
        pre.next = pre.next.next;
        return head;
    }

    //获取链表的长度
    public int getListLen(ListNode head){
        int len = 1;
        while (head.next != null){
            len++;
            head = head.next;
        }
        return len;
    }

    /**
     * 我的解法二（双指针，不用求链表长度）性能更好
     */
    public ListNode removeNthFromEnd0(ListNode head, int n) {
        if(head.next == null)
            return null;
        ListNode target_pre = head,end = head;
        //让end指针先往前走n布
        while (n > 0){
            end = end.next;
            n--;
        }
        //这证明要删除头节点
        if(end == null)
            return head.next;
        //然后target_pre和end指针同时往前，当end为尾指针时，target_pre就是要被删除的结点的前一个结点
        while (end.next != null){
            target_pre = target_pre.next;
            end = end.next;
        }
        target_pre.next = target_pre.next.next;
        return head;
    }

    /**
     * 反转链表(没想出来)
     * 答案解法之栈(非常高效！)
     */
    public ListNode reverseList(ListNode head) {
        if (head == null)
            return null;
        Stack<ListNode> listNodes = new Stack<>();
        while (head != null){
            listNodes.add(head);
            head = head.next;
        }
        head = listNodes.pop();
        ListNode res = head;
        while (!listNodes.isEmpty()){
            head.next = listNodes.pop();
            head = head.next;
        }
        head.next = null;
        return res;
    }

    /**
     * 双链表求解：把原链表的结点从前往后摘下来，然后作为新的头节点(这里用了new ListNode来创建新的结点，因此比较消耗内存)
     */
    public ListNode reverseList0(ListNode head) {
        ListNode newHead = null;
        while (head != null){
            ListNode p = new ListNode(head.val);
            p.next = newHead;
            newHead = p;
            head = head.next;
        }
        return newHead;
    }

    /**
     * 双链表求解：把原链表的结点从前往后摘下来，然后作为新的头节点(临时变量发，更节省内存（十分有限）)
     */
    public ListNode reverseList1(ListNode head) {
        ListNode newHead = null;
        ListNode p = head;
        while (head != null){
            p = p.next;
            head.next = newHead;
            newHead = head;
            head = p;
        }
        return newHead;
    }

    /**
     * 合并两个有序链表
     * 我的解法（消耗内存较大）
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = null, tail = null;
        while (list1 != null && list2 != null){
            if(list1.val <= list2.val){
                if(head == null){
                    head = list1;
                    tail = list1;
                }
                else {
                    tail.next = list1;
                    tail = tail.next;
                }
                list1 = list1.next;
            }else{
                if(head == null){
                    head = list2;
                    tail = list2;
                }
                else {
                    tail.next = list2;
                    tail = tail.next;
                }
                list2 = list2.next;
            }
        }
        if(head == null)
            return list1 != null ? list1 : list2;
        if (list1 != null){
            tail.next = list1;
        }
        if (list2 != null){
            tail.next = list2;
        }
        return head;
    }

    /**
     * 答案解法，其实是类似的，只不过巧了一点，代码可能少了一点
     */
    public ListNode mergeTwoLists0(ListNode list1, ListNode list2) {
        //如果其中一个为null
        if(list1 == null || list2 == null)
            return list1 != null ? list1 : list2;

        ListNode headPre = new ListNode(0);
        ListNode tail = headPre;

        while (list1 != null && list2 != null){
            if(list1.val <= list2.val){
                tail.next = list1;
                list1 = list1.next;
            }
            else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }

        tail.next = list1 != null ? list1 : list2;
        return headPre.next;
    }

    /**
     * 回文链表
     * 我的解法（用栈，效率太低了！！！）
     */
    public boolean isPalindrome(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        ListNode p = head;
        while (p != null){
            stack.push(p.val);
            p = p.next;
        }
        while (head != null){
            if(stack.pop() != head.val)
                return false;
            head = head.next;
        }
        return true;
    }

    /**
     * 答案解法（非常巧妙！然而也就占用的内存少了些）
     * 首先利用快慢指针找到链表的中间结点，然后将后面那段链表反转，最后再逐个进行比较
     */
    public boolean isPalindrome0(ListNode head) {
        ListNode fast = head,slow = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        if(fast != null)
            slow = slow.next;

        slow = reverseList(slow);
        fast = head;

        while (slow != null){
            if(fast.val != slow.val)
                return false;
            fast = fast.next;
            slow = slow.next;
        }
        return true;
    }

    /**
     * 环形链表
     * 我的解法（快慢指针，还行）
     */
    public boolean hasCycle(ListNode head) {
        ListNode fast = head,slow = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }
        return false;
    }

    /**
     * 答案解法之逐个放进Set(内存消耗小但时间复杂度高)
     */
    public boolean hasCycle0(ListNode head) {
        HashSet<ListNode> listNodes = new HashSet<>();
        while (head != null){
            if(listNodes.contains(head)){
                return false;
            }else {
                listNodes.add(head);
                head = head.next;
            }
        }
        return true;
    }
}
