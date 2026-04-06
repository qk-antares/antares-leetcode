package leetcode.datastruture.queue;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

import org.junit.jupiter.api.Test;

public class QueueLearn {
    /**
     * 设计循环队列，需要注意的有两点，一是数组的大小为size+1（这是为了区分队列已满和队列为空这两种情况），
     * 二是初始时head和tail均为0，当head==tail时队列为空，当(head+1)%(size+1)==tail时队列已满
     */
    class MyCircularQueue {

        private int[] queue;
        // 左闭右开
        private int head = 0;
        private int tail = 0;
        public MyCircularQueue(int k) {
            queue = new int[k+1];
        }

        public boolean enQueue(int value) {
            //还有空余的位置
            if(!isFull()){
                queue[tail] = value;
                tail = (tail + 1) % queue.length;
                return true;
            }else
                return false;
        }

        public boolean deQueue() {
            //空队
            if(isEmpty())
                return false;

            head = (head+1)% queue.length;
            return true;
        }

        public int Front() {
            if(!isEmpty())
                return queue[head];
            return -1;
        }

        public int Rear() {
            if(!isEmpty())
                return queue[(queue.length + tail - 1) % queue.length];
            return -1;
        }

        public boolean isEmpty() {
            return head == tail;
        }

        public boolean isFull() {
            return (tail + 1) % queue.length == head;
        }
    }

    /**
     * 队列常常用于广度优先搜索bfs
     */
    /*
    int BFS(Node root, Node target) {
        // 存储等待被处理的节点
        Queue<Node> queue;
        // 从根节点到当前节点的步数
        int step = 0;
        // 初始化
        将根节点添加进队列;
        // BFS
        while (!queue.isEmpty()) {
            step = step + 1;
            // 取出本层的结点，一个一个处理
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                Node cur = the first node in queue;
                return step if cur is target;
                for (Node next : the neighbors of cur) {
                    add next to queue;
                }
                remove the first node from queue;
            }
        }
        // there is no path from root to target
        return -1;
    }
     */


    /**
     * 打开转盘锁（BFS解决，每一个结点有8个子结点，但是结点不能重复，不能有deadends结点，这意味着大多数是没有8个子结点的）
     */
    class OpenLock {
        public int openLock(String[] deadends, String target) {
            HashSet<String> dead = new HashSet<>();
            for (String deadEnd : deadends) {
                dead.add(deadEnd);
            }

            Queue<String> queue = new ArrayDeque<>();
            if(!dead.contains("0000")){
                queue.offer("0000");
                dead.add("0000");
            }else {
                return -1;
            }

            int size;
            int ans = -1;
            while (!queue.isEmpty()){
                size = queue.size();
                ans++;
                for(int i = 0;i < size;i++){
                    String poll = queue.poll();
                    if(poll.equals(target)){
                        return ans;
                    }

                    for(int j = 0;j < 4;j++){
                        String add = add(poll, j);
                        if(!dead.contains(add)){
                            queue.offer(add);
                            dead.add(add);
                        }

                        String sub = sub(poll, j);
                        if(!dead.contains(sub)){
                            queue.offer(sub);
                            dead.add(sub);
                        }
                    }
                }
            }

            return -1;
        }

        public String add(String str, int index){
            int num = str.charAt(index) - '0';
            num = (num + 1) % 10;
            return str.substring(0, index) + num + str.substring(index + 1, 4);
        }

        public String sub(String str, int index){
            int num = str.charAt(index) - '0';
            num = (num + 9) % 10;
            return str.substring(0, index) + num + str.substring(index + 1, 4);
        }
    }

    /**
     * 上面使用String，效率太差了，使用StringBuilder试试（稍有提升）
     */
    class OpenLock0 {
        public int openLock(String[] deadends, String target) {
            HashSet<String> dead = new HashSet<>();
            for (String deadEnd : deadends) {
                dead.add(deadEnd);
            }

            Queue<String> queue = new ArrayDeque<>();
            if(!dead.contains("0000")){
                queue.offer("0000");
                dead.add("0000");
            }else {
                return -1;
            }

            int size;
            int ans = -1;
            while (!queue.isEmpty()){
                size = queue.size();
                ans++;
                for(int i = 0;i < size;i++){
                    String poll = queue.poll();
                    if(poll.equals(target)){
                        return ans;
                    }

                    for(int j = 0;j < 4;j++){
                        String add = add(new StringBuilder(poll), j);
                        if(!dead.contains(add)){
                            queue.offer(add);
                            dead.add(add);
                        }

                        String sub = sub(new StringBuilder(poll), j);
                        if(!dead.contains(sub)){
                            queue.offer(sub);
                            dead.add(sub);
                        }
                    }
                }
            }

            return -1;
        }

        public String add(StringBuilder str, int index){
            int num = str.charAt(index) - '0';
            num = (num + 1) % 10;
            return str.replace(index, index+1, String.valueOf(num)).toString();
        }

        public String sub(StringBuilder str, int index){
            int num = str.charAt(index) - '0';
            num = (num + 9) % 10;
            return str.replace(index, index+1, String.valueOf(num)).toString();
        }
    }

    @Test
    public void invoke(){
        // new OpenLock().openLock(new String[]{"0201","0101","0102","1212","2002"}, "0202");
    }
}
