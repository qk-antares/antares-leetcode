package leetcode.questions.Medium;

import org.junit.jupiter.api.Test;

import leetcode.common.ListNode;

/**
 * @author Antares
 * @date 2022/9/22
 */
public class MediumList {
    /**
     * 奇偶链表（我的解法，再创建两个头，一遍过，效率极高！）
     */
    public ListNode oddEvenList(ListNode head) {
        ListNode l1 = new ListNode();
        ListNode p = l1;
        ListNode l2 = new ListNode();
        ListNode q = l2;

        int count = 0;
        while (head != null){
            //奇数次
            if(++count % 2 == 1){
                p.next = new ListNode(head.val);
                p = p.next;
            }else { //偶数次
                q.next = new ListNode(head.val);
                q = q.next;
            }
            head = head.next;
        }

        p.next = l2.next;
        return l1.next;
    }

    @Test
    public void invoke(){
    }
}
