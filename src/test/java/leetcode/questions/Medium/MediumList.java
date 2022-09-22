package leetcode.questions.Medium;

import leetcode.common.ListNode;
import leetcode.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

/**
 * @author Antares
 * @date 2022/9/22
 */
public class MediumList {
    /**
     * 两数相加(我的解法：暴力解法，当位数过多会溢出)
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        long num1 = 0,num2 = 0;
        int count;
        count = 0;
        while (l1 != null){
            count++;
            num1 += l1.val * Math.pow(10, count-1);
            l1 = l1.next;
        }
        count = 0;
        while (l2 != null){
            count++;
            num2 += l2.val * Math.pow(10, count-1);
            l2 = l2.next;
        }
        String ansVal = String.valueOf(num1 + num2);
        System.out.println(num1 + " " + num2 + " " + ansVal);
        ListNode ans = new ListNode();
        ans.val = Integer.valueOf(ansVal.charAt(0) - '0');

        for(int i = 1;i < ansVal.length();i++){
            ListNode node = new ListNode();
            node.val = Integer.valueOf(ansVal.charAt(i) - '0');
            node.next = ans;
            ans = node;
        }

        return ans;
    }

    /**
     * 我的解法二（执行用时打败100%，内存消耗有点大）
     */
    public ListNode addTwoNumbers0(ListNode l1, ListNode l2) {
        ListNode ans = new ListNode();
        ListNode p = ans;
        //这是进位
        int add = 0;
        while (l1 != null && l2 != null){
            p.next = new ListNode();
            p = p.next;

            int sum = l1.val + l2.val + add;
            if(sum < 10){
                add = 0;
                p.val = sum;
            }else {
                String s = String.valueOf(sum);
                add = s.charAt(0) - '0';
                p.val = s.charAt(1) - '0';
            }

            l1 = l1.next;
            l2 = l2.next;
        }

        while (l1 != null){
            p.next = new ListNode();
            p = p.next;

            int sum = l1.val + add;
            if(sum < 10){
                add = 0;
                p.val = sum;
            }else {
                String s = String.valueOf(sum);
                add = s.charAt(0) - '0';
                p.val = s.charAt(1) - '0';
            }

            l1 = l1.next;
        }

        while (l2 != null){
            p.next = new ListNode();
            p = p.next;

            int sum = l2.val + add;
            if(sum < 10){
                add = 0;
                p.val = sum;
            }else {
                String s = String.valueOf(sum);
                add = s.charAt(0) - '0';
                p.val = s.charAt(1) - '0';
            }

            l2 = l2.next;
        }

        if(add != 0){
            p.next = new ListNode();
            p = p.next;
            p.val = add;
        }

        return ans.next;
    }

    /**
     * 答案解法（还是那个思路，但是把代码简化了）
     */
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        ListNode ans = new ListNode();
        ListNode p = ans;
        //l1。l2、进位、运算结果
        int a = 0, b = 0, c = 0, res = 0;
        while (l1 != null || l2 != null){
            p.next = new ListNode();
            p = p.next;

            a = 0;
            b = 0;
            if(l1 != null){
                a = l1.val;
                l1 = l1.next;
            }
            if(l2 != null){
                b = l2.val;
                l2 = l2.next;
            }

            res = (a + b + c) % 10;
            c = (a + b + c) / 10;
            p.val = res;
        }

        if(c != 0){
            p.next = new ListNode();
            p = p.next;
            p.val = c;
        }

        return ans.next;
    }

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

    /**
     * 相交链表(我的解法：使用Set，内存消耗较小但是执行用时太高)
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        HashSet<ListNode> listNodes = new HashSet<>();
        while (headA != null){
            listNodes.add(headA);
            headA = headA.next;
        }

        while (headB != null){
            if(listNodes.contains(headB))
                return headB;
            headB = headB.next;
        }

        return null;
    }

    /**
     * 答案解法（双指针，非常巧妙，两个指针分别走两个链表，最终就会在相同结点相遇，综合性能不错）
     */
    public ListNode getIntersectionNode0(ListNode headA, ListNode headB) {
        ListNode p = headA;
        ListNode q = headB;

        while (p != null || q != null){
            if(p == null)
                p = headB;
            if(q == null)
                q = headA;

            if(p == q)
                return p;
            p = p.next;
            q = q.next;
        }

        return null;
    }

    /**
     * 答案解法二：（求两个链表的长度，效果也很好）
     */
    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        ListNode p = headA;
        ListNode q = headB;

        int len1 = 0, len2 = 0;

        while (p != null){
            len1++;
            p = p.next;
        }
        while (q != null){
            len2++;
            q = q.next;
        }

        p = headA;
        q = headB;
        if(len1 > len2){
            for(int i = 0;i < len1-len2;i++){
                p = p.next;
            }
        }else {
            for(int i = 0;i < len2-len1;i++){
                q = q.next;
            }
        }

        while (p != null || q != null){
            if(p == q)
                return p;
            p = p.next;
            q = q.next;
        }

        return null;
    }

    @Test
    public void invoke(){
    }
}
