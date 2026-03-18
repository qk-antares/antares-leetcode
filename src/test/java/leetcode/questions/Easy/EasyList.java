package leetcode.questions.Easy;

import java.util.HashSet;

import leetcode.common.ListNode;

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
