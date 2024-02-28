package leetcode.interview150;


import java.util.HashMap;

import org.junit.jupiter.api.Test;

import leetcode.common.ListNode;

public class LinkedList {
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
        HashMap<Node, Node> map = new HashMap<>();
        Node tmp = head;
        while (tmp != null){
            map.put(tmp, new Node(tmp.val));
            tmp = tmp.next;
        }
        tmp = head;
        while (tmp != null){
            Node copy = map.get(tmp);
            copy.random = map.get(tmp.random);
            copy.next = map.get(tmp.next);
            tmp = tmp.next;
        }
        return map.get(head);
    }

    /**
     * 92. 反转链表 II
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if(left == right) {
            return head;
        }

        ListNode h = new ListNode();
        h.next = head;

        //首先找到翻转起点的前1一个节点
        ListNode startPre = h;
        for (int i = 1; i < left; i++) {
            startPre = startPre.next;
        }
        ListNode end = startPre;
        for (int i = 0; i <= right-left; i++) {
            end = end.next;
        }

        ListNode reverse = reverseHelper(startPre.next, end, end.next);
        startPre.next = reverse;
        return h.next;
    }

    public ListNode reverseHelper(ListNode start, ListNode end, ListNode endNext){
        if(start == end) {
            return start;
        }
        ListNode reverse = reverseHelper(start.next, end, endNext);
        start.next.next = start;
        start.next =endNext;
        return reverse;
    }

    /**
     * 反转链表II答案解法：
     * 关键点是记录反转的起始点、终点以及【起始点的前一个节点】和【终点的后一个节点】
     * 找到之后，将终点的next设置为null即可，
     */
    public ListNode reverseBetween0(ListNode head, int left, int right) {
        //由于起始点可能是头节点，所以需要一个虚拟头节点
        ListNode h = new ListNode();
        h.next = head;

        //首先找到翻转起点的前1一个节点
        ListNode startPre = h;
        for (int i = 1; i < left; i++) {
            startPre = startPre.next;
        }
        ListNode start = startPre.next;

        ListNode end = start;
        for (int i = 0; i < right-left; i++) {
            end = end.next;
        }
        ListNode endNext = end.next;

        end.next = null;
        ListNode reverse = reverseHelper(start);
        startPre.next = reverse;
        start.next = endNext;
        return h.next;
    }

    public ListNode reverseHelper(ListNode start){
        if(start == null || start.next == null) {
            return start;
        }
        ListNode reverse = reverseHelper(start.next);
        start.next.next = start;
        start.next = null;
        return reverse;
    }

    @Test
    void test(){
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = null;
        reverseBetween(n1, 2, 4);
    }
}
