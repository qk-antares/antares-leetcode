package leetcode.datastruture.list;

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

        public MyLinkedList() {
            head = new MyListNode();
            tail = head;
        }

        public int get(int index) {
            MyListNode head = this.head;
            for(int i = -1;i < index && head != null;i++){
                head = head.next;
            }

            return head.val;
        }

        public void addAtHead(int val) {
            MyListNode node = new MyListNode(val);
            node.next = head.next;
            head.next = node;

            if(tail == head){
                tail = node;
            }
        }

        public void addAtTail(int val) {
            MyListNode node = new MyListNode(val);
            tail.next = node;
            tail = node;
        }

        public void addAtIndex(int index, int val) {
            MyListNode node = new MyListNode(val);

            MyListNode head = this.head;
            for(int i = -1;i < index - 1 && head != null;i++){
                head = head.next;
            }

            node.next = head.next;
            head.next = node;

        }

        public void deleteAtIndex(int index) {
            MyListNode head = this.head;
            for(int i = -1;i < index - 1 && head != null;i++){
                head = head.next;
            }

            head.next = head.next.next;
        }
    }


}
