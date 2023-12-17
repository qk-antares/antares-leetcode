package leetcode.interview150;


import java.util.HashMap;

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
}
