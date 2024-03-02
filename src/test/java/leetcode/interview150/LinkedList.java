package leetcode.interview150;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import leetcode.common.ListNode;

public class LinkedList {
    /**
     * 138. 随机链表的复制
     */
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

    /**
     * 82. 删除排序链表中的重复元素 II
     */
    public ListNode deleteDuplicates(ListNode head) {
        //虚拟头节点
        ListNode h = new ListNode();
        h.next = head;

        ListNode pre = h, cur = head;
        while(cur != null) {
            if(cur.next != null && cur.val == cur.next.val) {
                int val = cur.val;
                //一直跳出这个相等区间为止
                while (cur != null && cur.val == val) {
                    cur = cur.next;
                }
                pre.next = cur;
            } else {
                pre = pre.next;
                cur = cur.next;
            }
        }

        return h.next;
    }

    /**
     * 86. 分隔链表
     */
    public ListNode partition(ListNode head, int x) {
        ListNode smaller = new ListNode(), bigger = new ListNode();
        ListNode h1 = smaller, h2 = bigger;
        while (head != null) {
            if(head.val < x) {
                smaller.next = head;
                smaller = smaller.next;
            } else {
                bigger.next = head;
                bigger = bigger.next;
            }
            head = head.next;
        }
        bigger.next = null;
        smaller.next = h2.next;
        return h1.next;
    }

    /**
     * 25. K 个一组翻转链表
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        int i = k-1;
        ListNode nextHeadPre = head;
        while (i > 0 && nextHeadPre != null) {
            nextHeadPre = nextHeadPre.next;
            i--;
        }

        if(i > 0 || nextHeadPre == null) {
            return head;
        }


        ListNode nextHead = nextHeadPre.next;
        nextHeadPre.next = null;

        reverseHelper(head);
        head.next = reverseKGroup(nextHead, k);

        return nextHeadPre;
    }

    /**
     * 19. 删除链表的倒数第 N 个结点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dumyNode = new ListNode();
        dumyNode.next = head;
        
        ListNode fast = dumyNode, slow = dumyNode;
        while (n >= 0) {
            fast = fast.next;
            n--;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        //此时slow指向要删除的节点的前一个节点
        slow.next = slow.next.next;
        return dumyNode.next;
    }

    /**
     * 61. 旋转链表
     */
    public ListNode rotateRight(ListNode head, int k) {
        //获取链表的长度
        int len = 0;
        ListNode tmp = head;
        while (tmp != null) {
            len++;
            tmp = tmp.next;
        }

        if(len <= 1) {
            return head;
        }
        k %= len;
        if(k == 0) {
            return head;
        }

        ListNode reverse = reverseHelper(head);

        //找到第k个节点
        ListNode kNode = reverse;
        while (k > 1) {
            k--;
            kNode = kNode.next;
        }

        ListNode next = kNode.next;
        kNode.next = null;

        reverseHelper(reverse);
        reverse.next = reverseHelper(next);
        return kNode;
    }

    class LRUCache {
        class DNode {
            DNode pre;
            DNode next;
            int key;
            int val;
        }
        
        Map<Integer, DNode> map;
        DNode head;
        DNode tail;
        int capacity;

        public LRUCache(int capacity) {
            map = new HashMap<>();
            head = new DNode();
            tail = new DNode();
            head.next = tail;
            tail.pre = head;
            this.capacity = capacity;
        }
        
        public int get(int key) {
            if(map.containsKey(key)) {
                DNode node = map.get(key);
                moveToHead(node);
                return node.val;
            }
            return -1;
        }
        
        public void put(int key, int value) {
            if(map.containsKey(key)) {
                DNode node = map.get(key);
                node.val = value;
                moveToHead(node);
            } else {
                DNode newNode = new DNode();
                newNode.key = key;
                newNode.val = value;
                insertNodeToHead(newNode);
                map.put(key, newNode);

                if(map.size() > capacity){
                    map.remove(tail.pre.key);
                    removeTail();
                }
            }
        }

        void moveToHead(DNode node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;

            head.next.pre = node;
            node.next = head.next;
            head.next = node;
            node.pre = head;
        }

        void insertNodeToHead(DNode node){
            node.pre = head;
            node.next = head.next;
            head.next.pre = node;
            head.next = node;
        }

        void removeTail(){
            DNode remove = tail.pre;

            tail.pre = tail.pre.pre;
            tail.pre.next = tail;
            remove.next = null;
            remove.pre = null;
        }
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
        // reverseBetween(n1, 2, 4);
        rotateRight(n1, 2);
    }
}
