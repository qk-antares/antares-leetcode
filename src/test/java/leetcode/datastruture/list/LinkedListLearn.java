package leetcode.datastruture.list;

import leetcode.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.*;

public class LinkedListLearn {
    /**
     * 设计链表
     */
    class MyLinkedList {
        class MyListNode {
            public int val;
            public MyListNode next;
            public MyListNode() {}
            public MyListNode(int val) { this.val = val; }
            public MyListNode(int val, MyListNode next) { this.val = val; this.next = next; }
        }

        private MyListNode head = null;
        private MyListNode tail = null;
        int len = 0;

        public MyLinkedList() {
            head = new MyListNode();
            head.next = new MyListNode();
            tail = head.next;
        }

        public int get(int index) {
            if(index < len){
                MyListNode head = this.head;
                for(int i = 0;i <= index;i++){
                    head = head.next;
                }
                return head.val;
            }
            return -1;
        }

        public void addAtHead(int val) {
            MyListNode node = new MyListNode(val);
            node.next = head.next;
            head.next = node;
            len++;
        }

        public void addAtTail(int val) {
            tail.val = val;
            MyListNode node = new MyListNode();
            tail.next = node;
            tail = node;
            len++;
        }

        public void addAtIndex(int index, int val) {
            if(index < len){
                MyListNode node = new MyListNode(val);

                MyListNode head = this.head;
                for(int i = 0;i < index;i++){
                    head = head.next;
                }

                node.next = head.next;
                head.next = node;

                len++;
            } else if (index == len) {
                addAtTail(val);
            }
        }

        public void deleteAtIndex(int index) {
            if(index < len){
                MyListNode head = this.head;
                for(int i = 0;i < index;i++){
                    head = head.next;
                }

                head.next = head.next.next;
                len--;
            }
        }
    }

    /**
     * 环形链表 II
     * 快慢指针
     * 第一次相遇时，slow走了m个结点，则fast走了2m个结点，m是环的长度
     * 此时从相遇点和头节点分别出发，再次相遇就是第一个环中结点
     *
     */
    public class DetectCycle {
        public ListNode detectCycle(ListNode head) {
            ListNode slow, fast;
            try {
                slow = head.next;
                fast = head.next.next;
                while (slow != fast){
                    slow = slow.next;
                    fast = fast.next.next;
                }
            } catch (Exception e) {
                return null;
            }
            slow = head;
            while (slow != fast){
                slow = slow.next;
                fast = fast.next;
            }

            return slow;
        }
    }

    /**
     * 相交链表，LA+LB和LB+LA，从头节点开始遍历，然后就可以找到
     */
    public class GetIntersectionNode {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            ListNode l1 = headA, l2 = headB;
            while (l1 != l2){
                l1 = l1.next;
                l2 = l2.next;
                if (l1 == null && l2 == null)
                    return null;
                if(l1 == null) l1 = headB;
                if (l2 == null) l2 = headA;
            }

            return l1;
        }
    }

    /**
     * 移除链表元素
     */
    class RemoveElements {
        public ListNode removeElements(ListNode head, int val) {
            ListNode helperHead = new ListNode();
            helperHead.next = head;

            ListNode pre = helperHead;
            while (head != null){
                if(head.val == val){
                    pre.next = head.next;
                } else {
                    pre = head;
                }
                head = head.next;
            }

            return helperHead.next;
        }
    }

    /**
     * 扁平化多级双向链表，（用栈，可以过，效率并不是很高）
     */
    class Flatten {
        class Node {
            public int val;
            public Node prev;
            public Node next;
            public Node child;
        };

        public Node flatten(Node head) {
            if(head == null) return null;

            Node newHead = new Node();
            Node cur = newHead;

            Stack<Node> nodes = new Stack<>();
            nodes.push(head);

            while (!nodes.isEmpty()){
                Node pop = nodes.pop();
                if (pop == null){
                    try {
                        cur.next = nodes.peek();
                    } catch (Exception e) {

                    }
                } else{
                    cur.next = pop;
                    pop.prev = cur;
                    cur = cur.next;
                    nodes.push(pop.next);
                    if (pop.child != null){
                        nodes.push(pop.child);
                        pop.child = null;
                    }
                }
            }

            newHead.next.prev = null;
            return newHead.next;
        }

        /**
         * 答案解法：竖起来看成一棵树，之后用dfs，效率大增
         */
        public Node flatten0(Node head) {
            if(head == null) return null;

            Node temp = head;
            ArrayList<Node> ans = new ArrayList<>();

            dfs(head, ans);

            for (Node node : ans) {
                node.child = null;
                node.prev = temp;
                temp.next = node;
                temp = temp.next;
            }

            head.prev = null;
            return head;
        }

        public void dfs(Node head, List<Node> ans){
            if(head != null){
                ans.add(head);
                dfs(head.child, ans);
                dfs(head.next, ans);
            }
        }
    }

    /**
     * 复制带随机指针的链表，建立一个原结点和拷贝结点的hashmap，整个过程两次普通的next遍历
     */
    class CopyRandomList {
        class Node {
            int val;
            Node next;
            Node random;

            public Node(int val) {
                this.val = val;
                this.next = null;
                this.random = null;
            }
        }

        public Node copyRandomList(Node head) {
            HashMap<Node, Node> hashMap = new HashMap<>();
            Node cur = head;

            while (cur != null){
                hashMap.put(cur, new Node(cur.val));
                cur = cur.next;
            }

            cur = head;
            Node node;
            while (cur != null){
                node = hashMap.get(cur);
                node.next = hashMap.get(cur.next);
                node.random = hashMap.get(cur.random);
                cur = cur.next;
            }

            return hashMap.get(head);
        }
    }

    /**
     * 旋转链表，首先用快慢指针找到旋转点，之后对旋转点前后的结点进行微操
     */
    class RotateRight {
        public ListNode rotateRight(ListNode head, int k) {
            if(head == null || head.next == null || k == 0) return head;

            ListNode fast = head, slow = head;
            int len = 0;

            //处理k特别大的情况
            while (k > 0 && fast != null){
                len++;
                k--;
                fast = fast.next;
            }

            if(k != 0){
                k %= len;
                if(k == 0) return head;
                fast = head;
                while (k > 0){
                    k--;
                    fast = fast.next;
                }
            }

            if(fast == null) return head;

            while (fast.next != null){
                slow = slow.next;
                fast = fast.next;
            }

            //此时fast指向尾结点，slow指向旋转点
            ListNode ans = slow.next;
            slow.next = null;
            fast.next = head;

            return ans;
        }

        /**
         * 上面的方法效率可以，但是太复杂了，用先连环，再断环两次遍历更简单，效率基本没变
         */
        public ListNode rotateRight0(ListNode head, int k) {
            if(head == null || head.next == null || k == 0) return head;

            int len = 1;
            ListNode cur = head;
            while (cur.next != null){
                len++;
                cur = cur.next;
            }
            cur.next = head;
            //至此连成环

            //这是等价右移的长度
            k %= len;
            //需要找的断点
            k = len - k + 1;

            cur = head;
            ListNode ans;
            while (k > 0){
                cur = cur.next;
            }
            ans = cur.next;
            cur.next = null;

            return ans;
        }

    }

    @Test
    void invoke(){

    }
}
