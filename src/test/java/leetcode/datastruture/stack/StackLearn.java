package leetcode.datastruture.stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.junit.jupiter.api.Test;

import leetcode.common.TreeNode;

public class StackLearn {

    class MinStack {

        LinkedList<int[]> list;

        public MinStack() {
            list = new LinkedList<>();
            list.addLast(new int[]{0, Integer.MAX_VALUE});
        }

        public void push(int val) {
            int min = Math.min(list.getLast()[1], val);
            list.addLast(new int[]{val, min});
        }

        public void pop() {
            list.removeLast();
        }

        public int top() {
            return list.getLast()[0];
        }

        public int getMin() {
            return list.getLast()[1];
        }
    }

    /**
     * 深度优先搜索使用栈，递归地实现 DFS 时，似乎不需要使用任何栈。但实际上，我们使用的是由系统提供的隐式栈，也称为调用栈
     */
    boolean DFS(Node cur, Node target, Set<Node> visited) {
        if(cur.val == target.val){
            return true;
        }
        // for (next : each neighbor of cur) {
        //     if (next is not in visited) {
        //         add next to visited;
        //         return true if DFS(next, target, visited) == true;
        //     }
        // }
        return false;
    }

    /**
     * 岛屿数量
     */
    class NumIslands {
        boolean[][] visited;
        int m;
        int n;

        public int numIslands(char[][] grid) {
            m = grid.length;
            n = grid[0].length;
            visited = new boolean[m][n];

            int ans = 0;
            for(int i = 0;i < m;i++){
                for(int j = 0;j < n;j++){
                    //没有访问过
                    if(!visited[i][j]){
                        visited[i][j] = true;
                        if(grid[i][j] == '1'){
                            ans++;
                            dfs(grid, i, j);
                        }
                    }
                }
            }

            return ans;
        }

        public void dfs(char[][] grid, int i, int j){
            for(int step = -1;step <= 1;step += 2){
                if(i+step >= 0 && i+step<m && !visited[i+step][j]){
                    visited[i+step][j] = true;
                    if(grid[i+step][j] == '1'){
                        dfs(grid, i+step, j);
                    }
                }
                if(j+step >= 0 && j+step<n && !visited[i][j+step]){
                    visited[i][j+step] = true;
                    if(grid[i][j+step] == '1'){
                        dfs(grid, i, j+step);
                    }
                }
            }
        }
    }

    /**
     * 克隆图
     */
    class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    class CloneGraph {
        public Node cloneGraph(Node node) {
            if(node == null)
                return null;

            Node root = new Node(node.val);

            Stack<Node> stack0 = new Stack<>();
            stack0.push(node);

            Stack<Node> stack1 = new Stack<>();
            stack1.push(root);

            HashMap<Node, Node> hashMap = new HashMap<>();
            hashMap.put(node, root);

            while (!stack0.isEmpty()){
                Node pop = stack0.pop();
                Node cur = stack1.pop();
                for (Node neighbor : pop.neighbors) {
                    if(!hashMap.containsKey(neighbor)){
                        Node newNode = new Node(neighbor.val);
                        hashMap.put(neighbor, newNode);

                        stack0.push(neighbor);
                        stack1.push(newNode);
                    }

                    cur.neighbors.add(hashMap.get(neighbor));
                }
            }

            return root;
        }
    }

    /**
     * 目标和，相当于一个n+1层的二叉树（往左是+，往右是-），对其进行深度优先搜索，超时，要用函数递归写法
     */
    class FindTargetSumWays {
        public int findTargetSumWays(int[] nums, int target) {
            int ans = 0;

            int n = nums.length;

            //{index, val}
            Stack<int[]> stack = new Stack<>();
            stack.add(new int[]{-1, 0});
            while (!stack.isEmpty()){
                int[] pop = stack.pop();
                if(pop[0] == n - 1) {
                    if(pop[1] == target) ans++;
                    continue;
                }
                stack.push(new int[]{pop[0] + 1, pop[1] + nums[pop[0] + 1]});
                stack.push(new int[]{pop[0] + 1, pop[1] - nums[pop[0] + 1]});
            }

            return ans;
        }

        int ans = 0;
        public int findTargetSumWays0(int[] nums, int target) {
            dfs(nums, 0, target, 0);
            return ans;
        }

        void dfs(int nums[], int index, int target, int cur){
            if(index == nums.length){
                if(target == cur) ans++;
                return;
            }
            dfs(nums, index+1, target, cur+nums[index]);
            dfs(nums, index+1, target, cur-nums[index]);
        }
    }

    /**
     * 二叉树的中序遍历，有递归写法和栈写法
     */
    class InorderTraversal {
        public List<Integer> inorderTraversal(TreeNode root) {
            ArrayList<Integer> list = new ArrayList<>();
            inorder(root, list);
            return list;
        }

        public void inorder(TreeNode root, List<Integer> list){
            if(root == null)
                return;
            inorder(root.left, list);
            list.add(root.val);
            inorder(root.right, list);
        }

        /**
         * 栈写法
         * 根节点入栈
         * 根节点出栈，右节点入栈，根节点入栈，左节点入栈
         */
        public List<Integer> inorderTraversal0(TreeNode root) {
            ArrayList<Integer> ans = new ArrayList<>();
            if(root == null)
                return ans;

            Stack<TreeNode> stack = new Stack<>();
            while (root != null || !stack.isEmpty()) {
                while (root != null){
                    stack.add(root);
                    root = root.left;
                }
                root = stack.pop();
                ans.add(root.val);
                root = root.right;
            }

            return ans;
        }
    }

    /**
     * 用栈实现队列
     */
    class MyQueue {
        Stack<Integer> queue;
        Stack<Integer> helper;


        public MyQueue() {
            queue = new Stack<>();
            helper = new Stack<>();
        }

        public void push(int x) {
            queue.push(x);
        }

        public int pop() {
            while (!queue.isEmpty()){
                helper.push(queue.pop());
            }
            int ans = helper.pop();
            while (!helper.isEmpty()){
                queue.push(helper.pop());
            }
            return ans;
        }

        public int peek() {
            while (!queue.isEmpty()){
                helper.push(queue.pop());
            }
            int ans = helper.peek();
            while (!helper.empty()){
                queue.push(helper.pop());
            }
            return ans;
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }

    /**
     * 用队列实现栈
     */
    class MyStack {
        Queue<Integer> stack;
        Queue<Integer> helper;

        public MyStack() {
            stack = new LinkedList<>();
            helper = new LinkedList<>();
        }

        public void push(int x) {
            stack.offer(x);
        }

        public int pop() {
            while (stack.size() > 0){
                helper.offer(stack.poll());
            }
            int ans = stack.poll();
            while (!helper.isEmpty()){
                stack.offer(helper.poll());
            }
            return ans;
        }

        public int top() {
            while (stack.size() > 0){
                helper.offer(stack.poll());
            }
            int ans = stack.poll();
            helper.offer(ans);
            while (!helper.isEmpty()){
                stack.offer(helper.poll());
            }
            return ans;
        }

        public boolean empty() {
            return stack.isEmpty();
        }
    }

    /**
     * 图像渲染（深度优先搜索）
     */
    class FloodFill {
        boolean[][] visited;
        int m;
        int n;

        public int[][] floodFill(int[][] image, int sr, int sc, int color) {
            m = image.length;
            n = image[0].length;
            visited = new boolean[m][n];

            int target = image[sr][sc];

            visited[sr][sc] = true;
            image[sr][sc] = color;
            dfs(image, sr, sc, target, color);

            return image;
        }

        void dfs(int[][] image, int x, int y, int target, int color){
            for(int i = -1;i <= 1;i += 2){
                if(x+i > -1 && x+i < m && !visited[x+i][y]){
                    visited[x+i][y] = true;
                    if(image[x+i][y] == target){
                        image[x+i][y] = color;
                        dfs(image, x+i, y, target, color);

                    }
                }

                if(y+i > -1 && y+i < n && !visited[x][y+i]){
                    visited[x][y+i] = true;
                    if(image[x][y+i] == target){
                        image[x][y+i] = color;
                        dfs(image, x, y+i, target, color);

                    }
                }
            }
        }
    }

    /**
     * 01 矩阵（广度优先搜索，我的解法：StackOverflow）
     * [[0,1,0,1,1],
     * [1,1,0,0,1],
     * [0,0,0,1,0],
     * [1,0,1,1,1],
     * [1,0,0,0,1]]
     */
    class UpdateMatrix {
        boolean[][] visited;
        int m;
        int n;

        public int[][] updateMatrix(int[][] mat) {
            m = mat.length;
            n = mat[0].length;

            visited = new boolean[m][n];

            int[][] ans = new int[m][n];

            for(int i = 0;i < m;i++){
                for(int j = 0;j < n;j++){
                    if(!visited[i][j]){
                        if(mat[i][j] != 0){
                            ans[i][j] = bfs(mat, ans, i, j) + 1;
                        }
                        visited[i][j] = true;
                    }
                }
            }

            return ans;
        }

        int bfs(int[][] mat, int[][] ans, int x, int y){
            //将周围的点加入queue
            Queue<int[]> queue = new LinkedList<>();
            for(int i = -1;i <= 1;i += 2){
                if(x+i > -1 && x+i < m){
                    queue.offer(new int[]{x+i, y});
                }
                if(y+i > -1 && y+i < n){
                    queue.offer(new int[]{x, y+i});
                }
            }

            //如果周围的点有0，则找到
            for(int i = 0;i < queue.size();i++){
                int[] poll = queue.poll();
                if(mat[poll[0]][poll[1]] == 0){
                    visited[poll[0]][poll[1]] = true;
                    return 0;
                }
                queue.offer(poll);
            }

            //周围没有0，继续搜索
            for(int i = 0;i < queue.size();i++){
                int[] poll = queue.poll();
                if(!visited[poll[0]][poll[1]]){
                    if(mat[poll[0]][poll[1]] != 0){
                        ans[poll[0]][poll[1]] = bfs(mat, ans, poll[0], poll[1]) + 1;
                    }
                    visited[poll[0]][poll[1]] = true;
                }
                queue.offer(poll);
            }

            int min = Integer.MAX_VALUE;
            while (!queue.isEmpty()){
                int[] poll = queue.poll();
                if(ans[poll[0]][poll[1]] < min){
                    min = ans[poll[0]][poll[1]];
                }
            }

            return min;
        }
    }

    /**
     * 01 矩阵（广度优先搜索，但是逆向思维，先找到所有为0的，它需要的步数为0，然后向外广度扩张）
     */
    class UpdateMatrix0 {
        public int[][] updateMatrix(int[][] mat) {
            int m = mat.length;
            int n = mat[0].length;

            int[][] dirs = new int[][]{{-1,0}, {1,0}, {0,-1}, {0, 1}};
            boolean[][] visited = new boolean[m][n];
            int[][] ans = new int[m][n];

            Queue<int[]> queue = new LinkedList<>();

            for(int i = 0;i < m;i++){
                for(int j = 0;j < n;j++){
                    if(mat[i][j] == 0){
                        visited[i][j] = true;
                        queue.offer(new int[]{i, j});
                    }
                }
            }

            int x,y;
            int size;
            while (!queue.isEmpty()){
                size = queue.size();
                //注意这里i < size，而不能直接写i < queue.size()
                for(int i = 0;i < size;i++){
                    int[] poll = queue.poll();
                    for(int j = 0;j < dirs.length;j++){
                        x = poll[0] + dirs[j][0];
                        y = poll[1] + dirs[j][1];
                        if(x > -1 && x < m && y > -1 && y < n && !visited[x][y]){
                            visited[x][y] = true;
                            ans[x][y] = ans[poll[0]][poll[1]] + 1;
                            queue.offer(new int[]{x, y});
                        }
                    }
                }
            }

            return ans;
        }
    }

    @Test
    void invoke(){
        // new DailyTemperatures().dailyTemperatures(new int[]{73,74,75,71,69,72,76,73});
        // new FloodFill().floodFill(new int[][]{{1,1,1},{1,1,0},{1,0,1}}, 1, 1 ,2);
        // new UpdateMatrix().updateMatrix(new int[][]{{0,0,0},{0,1,0},{1,1,1}});
        // new UpdateMatrix0().updateMatrix(new int[][]{{0,1,0,1,1},{1,1,0,0,1},{0,0,0,1,0},{1,0,1,1,1},{1,0,0,0,1}});

    }
}
