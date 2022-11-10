package leetcode.datastruture.queue;

import org.junit.jupiter.api.Test;

import java.util.*;

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
     * 岛屿数量，我的解法（广度优先搜索，代码上可以再优化下，但思想是对的）
     */
    class NumIslands {
        boolean[][] visited;
        public int numIslands(char[][] grid) {
            int height = grid.length;
            int width = grid[0].length;
            visited = new boolean[height][width];

            int ans = 0;

            for(int i = 0;i < height;i++){
                for(int j = 0;j < width;j++){
                    //如果该节点是没有访问过的1结点
                    if(grid[i][j] == '1' && !visited[i][j]){
                        ans++;
                        visited[i][j] = true;
                        bfs(grid, i, j);
                    }
                }
            }

            return ans;
        }

        //将岛屿及其周围的0结点标记为访问过
        public void bfs(char[][] grid, int i , int j){
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{i, j});

            int size;
            while (!queue.isEmpty()){
                size = queue.size();
                for(int k = 0;k < size;k++){
                    int[] poll = queue.poll();
                    //遍历周边的四个结点
                    if(poll[0] - 1 > -1 && !visited[poll[0]-1][poll[1]]){
                        visited[poll[0]-1][poll[1]] = true;
                        if(grid[poll[0]-1][poll[1]] == '1')
                            queue.offer(new int[]{poll[0]-1, poll[1]});
                    }
                    if(poll[0] + 1 < grid.length && !visited[poll[0]+1][poll[1]]){
                        visited[poll[0]+1][poll[1]] = true;
                        if(grid[poll[0]+1][poll[1]] == '1')
                            queue.offer(new int[]{poll[0]+1, poll[1]});
                    }
                    if(poll[1] - 1 > -1 && !visited[poll[0]][poll[1]-1]){
                        visited[poll[0]][poll[1]-1] = true;
                        if(grid[poll[0]][poll[1]-1] == '1')
                            queue.offer(new int[]{poll[0], poll[1]-1});
                    }
                    if(poll[1] + 1 < grid[0].length && !visited[poll[0]][poll[1]+1]){
                        visited[poll[0]][poll[1]+1] = true;
                        if(grid[poll[0]][poll[1]+1] == '1')
                            queue.offer(new int[]{poll[0], poll[1]+1});
                    }
                }
            }
        }
    }

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

    /**
     * 完全平方数，BFS
     * 每个结点上是当前的和，空间复杂度太高，爆内存，到后期可能会有几千个结点（缓解这个问题的途径是添加一个Set，只有从未添加过的结点才加进去）
     * BFS的效率太低，使用动态规划
     */
    class NumSquares {
        public int numSquares(int n) {
            Set<Integer> set = new HashSet<>();

            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(0);

            int size;
            int ans = 0;
            int delta;
            double sqrt;
            int temp;
            while (!queue.isEmpty()){
                size = queue.size();
                ans++;
                for(int i = 0;i < size;i++){
                    Integer cur = queue.poll();
                    set.add(cur);
                    if(cur == n){
                        return ans-1;
                    }
                    delta = n - cur;
                    sqrt = Math.sqrt(delta);
                    for(int j = (int)sqrt;j >= 1;j--){
                        temp = cur + j * j;
                        if(!set.contains(temp)){
                            queue.offer(temp);
                        }
                    }
                }
            }

            return -1;
        }

        /**
         * dp[0] = 0;
         * dp[i] = min{dp[i-j*j]} + 1 (1<=j<=(int)sqrt(i), 1<=i<=n)
         * 效率有非常明显的提高
         */
        public int numSquares0(int n) {
            int[] dp = new int[n + 1];
            dp[0] = 0;
            int min;
            for(int i = 1;i <= n;i++){
                min = Integer.MAX_VALUE;
                for(int j = 1;j <= (int)Math.sqrt(i);j++){
                    if(dp[i - j * j] < min){
                        min = dp[i - j * j];
                    }
                    dp[i] = min + 1;
                }
            }

            return dp[n];
        }
    }



    @Test
    public void invoke(){
        // new NumIslands().numIslands(new char[][]{{'1','1','1','1','0'},{'1','1','0','1','0'},{'1','1','0','0','0'},{'0','0','0','0','0'}});
        // new OpenLock().openLock(new String[]{"0201","0101","0102","1212","2002"}, "0202");
        // new NumSquares().numSquares(9999);

    }
}
