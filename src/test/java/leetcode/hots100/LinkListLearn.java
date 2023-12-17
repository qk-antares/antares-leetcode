package leetcode.hots100;

import leetcode.common.ListNode;

import java.util.*;

public class LinkListLearn {
    /**
     * 相交链表
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int m = getLen(headA), n = getLen(headB);

        if(m > n){
            for (int i = 0; i < m - n; i++) {
                headA = headA.next;
            }
        } else {
            for (int i = 0; i < n - m; i++) {
                headB = headB.next;
            }
        }

        while (headA != null){
            if(headA == headB){
                return headA;
            }
            headA = headA.next;
            headB = headB.next;
        }

        return null;
    }

    int getLen(ListNode head){
        int len = 0;
        while (head != null){
            len++;
            head = head.next;
        }
        return len;
    }

    /**
     * 反转链表
     */
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode node = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return node;
    }

    /**
     * 回文链表
     */
    public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null) return true;

        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        if(fast != null){
            slow = slow.next;
        }

        slow = reverseList(slow);

        while (slow != null){
            if(slow.val != head.val){
                return false;
            }
            slow = slow.next;
            head = head.next;
        }
        return true;
    }

    /**
     * 环形链表
     */
    public boolean hasCycle(ListNode head) {
        if(head == null || head.next == null){
            return false;
        }

        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast){
                return true;
            }
        }
        return false;
    }

    /**
     * 环形链表 II
     */
    public ListNode detectCycle(ListNode head) {
        if(head == null || head.next == null){
            return null;
        }

        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast){
                while (slow != head){
                    slow = slow.next;
                    head = head.next;
                }
                return slow;
            }
        }
        return null;
    }

    /**
     * 合并两个有序链表
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode ans = new ListNode(), headA = list1, headB = list2;
        ListNode cur = ans;

        if(headA == null && headB == null){
            return null;
        }
        if(headA == null){
            return headB;
        }
        if(headB == null){
            return headA;
        }

        while (headA != null && headB != null){
            if(headA.val < headB.val){
                cur.next = headA;
                headA = headA.next;
            } else {
                cur.next = headB;
                headB = headB.next;
            }
            cur = cur.next;
        }

        if(headA == null){
            cur.next = headB;
        } else {
            cur.next = headA;
        }
        return ans.next;
    }

    /**
     * 两数相加
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int add = 0;//表示进位
        ListNode ans = new ListNode();
        ListNode cur = ans;
        while (l1 != null && l2 != null){
            int sum = add + l1.val + l2.val;
            add = sum / 10;
            cur.next = new ListNode(sum % 10);
            l1 = l1.next;
            l2 = l2.next;
            cur = cur.next;
        }

        while (l1 != null){
            int sum = add + l1.val;
            add = sum / 10;
            cur.next = new ListNode(sum % 10);
            l1 = l1.next;
            cur = cur.next;
        }

        while (l2 != null){
            int sum = add + l2.val;
            add = sum / 10;
            cur.next = new ListNode(sum % 10);
            l2 = l2.next;
            cur = cur.next;
        }

        if(add != 0){
            cur.next = new ListNode(add);
        }

        return ans.next;
    }

    /**
     * 删除链表的倒数第 N 个结点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int len = getLen(head);
        System.out.println(len);
        int order = len - n;
        if(order < 1){
            return head.next;
        }

        ListNode cur = head;
        while (order > 1){
            cur = cur.next;
            order--;
        }
        cur.next = cur.next.next;
        return head;
    }

    /**
     * 两两交换链表中的节点
     */
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode l = head, r = head.next;
        ListNode ans = r;
        l.next = swapPairs(r.next);
        r.next = l;

        return ans;
    }

    /**
     * K 个一组翻转链表
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        int i = k-1;
        ListNode tmp = head;
        while (i > 0 && tmp != null){
            tmp = tmp.next;
            i--;
        }
        if(i > 0 || tmp == null){
            return head;
        }

        ListNode next = tmp.next;
        tmp.next = null;
        reverseList(head);
        head.next = reverseKGroup(next, k);
        return tmp;
    }

    /**
     * 复制带随机指针的链表（建立原node和新Node的hash表）
     */
    HashMap<Node, Node> map = new HashMap<>();

    public Node copyRandomList(Node head) {
        if(head == null){
            return null;
        }

        if(!map.containsKey(head)){
            Node node = new Node(head.val);
            map.put(head, node);
            node.next = copyRandomList(head.next);
            node.random = copyRandomList(head.random);
            return node;
        } else {
            return map.get(head);
        }
    }

    /**
     * 排序链表（超时）
     */
    public ListNode sortList0(ListNode head) {
        if(head == null) {
            return null;
        }

        ListNode ans = new ListNode();
        ListNode next = head.next;
        ListNode pre;
        while (head != null){
            pre = ans;
            while (pre.next != null){
                if(head.val < pre.next.val){
                    head.next = pre.next;
                    pre.next = head;
                    break;
                } else {
                    pre = pre.next;
                }
            }

            if(pre.next == null){
                head.next = null;
                pre.next = head;
            }

            head = next;
            if(next != null){
                next = next.next;
            }
        }

        return ans.next;
    }
    /**
     * 排序链表（用List存储排序，再取出来，面试肯定不能这么写，太作弊了，而且效率也不高）
     */
    public ListNode sortList1(ListNode head) {
        ArrayList<ListNode> list = new ArrayList<>();
        while (head != null){
            list.add(head);
            head = head.next;
        }

        list.sort((o1, o2) -> o1.val - o2.val);

        ListNode ans = new ListNode();
        ListNode tmp = ans;
        for (ListNode node : list) {
            tmp.next = node;
            tmp = tmp.next;
        }
        tmp.next = null;
        return ans.next;
    }

    /**
     * 排序链表（用归并排序，快慢指针找到中点，排序后合并，空间复杂度logn）
     */
    public ListNode sortList2(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }

        //找中点
        ListNode slow = head, fast = head;
        ListNode slowPre = head;
        while (fast != null){
            slowPre = slow;
            slow = slow.next;
            fast = fast.next;
            if(fast != null){
                fast = fast.next;
            }
        }
        slowPre.next = null;
        ListNode list0 = sortList2(head);
        ListNode list1 = sortList2(slow);
        return merge(list0, list1);
    }

    public ListNode sortList(ListNode head){
        if (head == null) {
            return null;
        }
        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }

        ListNode ans = new ListNode(0);
        ans.next = head;

        //链表拆分
        for (int subLen = 1;subLen < length;subLen <<= 1){
            ListNode pre = ans;
            ListNode cur = ans.next;

            while (cur != null){
                //拆分链表
                ListNode head1 = cur;
                for (int i = 1;i < subLen && cur != null && cur.next != null;i++){
                    cur = cur.next;
                }

                ListNode head2 = cur.next;
                cur.next = null;    //断开两个链表
                cur = head2;
                for (int i = 1;i < subLen && cur != null && cur.next != null;i++){
                    cur = cur.next;
                }

                //断开第二个链表和之后的节点
                ListNode next = null;   //断开之前要记录下，方面后面继续进行合并
                if(cur != null){
                    next = cur.next;
                    cur.next = null;
                }

                //合并两个链表
                ListNode merged = mergeTwoLists(head1, head2);
                pre.next = merged;
                while (pre.next != null){
                    pre = pre.next;
                }
                cur = next;
            }
        }

        return ans.next;
    }

    public ListNode merge(ListNode node0, ListNode node1){
        if(node0 == null){
            return node1;
        }
        if(node1 == null){
            return node0;
        }

        if(node0.val < node1.val){
            node0.next = merge(node0.next, node1);
            return node0;
        } else {
            node1.next = merge(node0, node1.next);
            return node1;
        }
    }

    /**
     * 合并 K 个升序链表
     */
    public ListNode mergeKLists0(ListNode[] lists) {
        int len = lists.length;
        if(len == 0){
            return null;
        }
        if(len == 1){
            return lists[0];
        }

        return mergeKLists0(lists, 0, len-1);
    }
    public ListNode mergeKLists0(ListNode[] lists, int start, int end) {
        if(end - start == 1){
            return mergeTwoLists(lists[start], lists[end]);
        } else {
            return mergeTwoLists(lists[start], mergeKLists(lists, start+1, end));
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        int len = lists.length;
        if(len == 0){
            return null;
        }

        return mergeKLists(lists, 0, len-1);
    }
    public ListNode mergeKLists(ListNode[] lists, int start, int end) {
        if(start == end){
            return lists[start];
        }
        if(end - start == 1){
            return mergeTwoLists(lists[start], lists[end]);
        }
        int mid = (start + end) / 2;
        return mergeTwoLists(mergeKLists(lists, start, mid), mergeKLists(lists, mid+1, end));
    }

    /**
     * LRU缓存
     */
    class LRUCache {
        class DLinkedNode {
            Integer key;
            Integer value;
            DLinkedNode prev;
            DLinkedNode next;
        }

        DLinkedNode head, tail;
        HashMap<Integer, DLinkedNode> map;
        int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>(capacity);
            head = new DLinkedNode();
            tail = new DLinkedNode();
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            if(map.containsKey(key)){
                DLinkedNode node = map.get(key);
                moveToHead(node);
                return node.value;
            } else {
                return -1;
            }
        }

        public void put(int key, int value) {
            if (map.containsKey(key)){
                DLinkedNode node = map.get(key);
                node.value = value;
                moveToHead(node);
            } else {
                DLinkedNode node = new DLinkedNode();
                node.key = key;
                node.value = value;
                insertNodeToHead(node);
                map.put(key, node);

                if(map.size() > capacity){
                    map.remove(tail.prev.key);
                    removeTail();
                }
            }
        }

        void moveToHead(DLinkedNode node){
            DLinkedNode prev = node.prev;
            DLinkedNode next = node.next;
            prev.next = next;
            next.prev = prev;

            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        void insertNodeToHead(DLinkedNode node){
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        void removeTail(){
            DLinkedNode remove = tail.prev;

            tail.prev = tail.prev.prev;
            tail.prev.next = tail;
            remove.next = null;
            remove.prev = null;
        }
    }


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
}
