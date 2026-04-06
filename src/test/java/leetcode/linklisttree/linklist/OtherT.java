package leetcode.linklisttree.linklist;

import java.util.HashMap;
import java.util.Map;

/**
 * 链表的其他题
 */
public class OtherT {
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

    /**
     * 138. 随机链表的复制 [Medium]
     * 
     * 解法1：使用HashMap建立原node和新Node的hash表
     * 解法2：在原链表的每个结点后面插入一个新结点，原链表的结点和新结点的val相同
     * 最后执行分离：把新结点的random指针指向原结点的random指针的下一个结点
     */
    public Node copyRandomList(Node head) {
        Map<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            Node copy = map.get(cur);
            copy.next = map.get(cur.next);
            copy.random = map.get(cur.random);

            cur = cur.next;
        }

        return map.get(head);
    }

    /**
     * 146. LRU 缓存    [Hard]
     * 
     * 双链表+HashMap
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

            tail.prev = remove.prev;
            remove.prev.next = tail;
            remove.next = null;
            remove.prev = null;
        }
    }
}
