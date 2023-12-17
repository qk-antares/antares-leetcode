package leetcode.datastruture.tree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class KTreeMedium {
    /**
     * 建立四叉树
     */


    class Construct {
        class Node {
            public boolean val;
            public boolean isLeaf;
            public Node topLeft;
            public Node topRight;
            public Node bottomLeft;
            public Node bottomRight;


            public Node() {
                this.val = false;
                this.isLeaf = false;
                this.topLeft = null;
                this.topRight = null;
                this.bottomLeft = null;
                this.bottomRight = null;
            }

            public Node(boolean val, boolean isLeaf) {
                this.val = val;
                this.isLeaf = isLeaf;
                this.topLeft = null;
                this.topRight = null;
                this.bottomLeft = null;
                this.bottomRight = null;
            }

            public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
                this.val = val;
                this.isLeaf = isLeaf;
                this.topLeft = topLeft;
                this.topRight = topRight;
                this.bottomLeft = bottomLeft;
                this.bottomRight = bottomRight;
            }
        }

        Node ans = new Node();

        public Node construct(int[][] grid) {
            boolean flag = isSame(grid, 0, 0, grid.length);
            if(flag) {
                ans.isLeaf = true;
                ans.val = grid[0][0] == 1;
            } else{
                constructHelper(grid, 0, 0, grid.length/2, ans);
            }
            return ans;
        }

        public void constructHelper(int[][] grid, int startX, int startY, int step, Node cur){
            boolean flag = isSame(grid, startX, startY, step);
            if(flag){
                cur.topLeft = new Node(grid[startX][startY] == 1, true);
            }else{
                cur.topLeft = new Node();
                constructHelper(grid, startX, startY, step/2, cur.topLeft);
            }

            flag = isSame(grid, startX, startY + step, step);
            if(flag){
                cur.topRight = new Node(grid[startX][startY+step] == 1, true);
            }else{
                cur.topRight = new Node();
                constructHelper(grid, startX, startY+step, step/2, cur.topRight);
            }

            flag = isSame(grid, startX + step, startY, step);
            if(flag){
                cur.bottomLeft = new Node(grid[startX+step][startY] == 1, true);
            }else{
                cur.bottomLeft = new Node();
                constructHelper(grid, startX+step, startY, step/2, cur.bottomLeft);
            }

            flag = isSame(grid, startX + step, startY, step);
            if(flag){
                cur.bottomRight = new Node(grid[startX+step][startY+step] == 1, true);
            }else{
                cur.bottomRight = new Node();
                constructHelper(grid, startX+step, startY+step, step/2, cur.bottomRight);
            }
        }

        public boolean isSame(int[][] grid, int startX, int startY, int step){
            for(int i = 0;i < step;i++){
                for(int j = 0;j < step;j++){
                    if(grid[startX + i][startY + j] != grid[startX][startY])
                        return false;
                }
            }
            return true;
        }
    }

    /**
     * 简化版
     */
    class Construct0 {
        class Node {
            public boolean val;
            public boolean isLeaf;
            public Node topLeft;
            public Node topRight;
            public Node bottomLeft;
            public Node bottomRight;


            public Node() {
                this.val = false;
                this.isLeaf = false;
                this.topLeft = null;
                this.topRight = null;
                this.bottomLeft = null;
                this.bottomRight = null;
            }

            public Node(boolean val, boolean isLeaf) {
                this.val = val;
                this.isLeaf = isLeaf;
                this.topLeft = null;
                this.topRight = null;
                this.bottomLeft = null;
                this.bottomRight = null;
            }

            public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
                this.val = val;
                this.isLeaf = isLeaf;
                this.topLeft = topLeft;
                this.topRight = topRight;
                this.bottomLeft = bottomLeft;
                this.bottomRight = bottomRight;
            }
        }

        public Node construct(int[][] grid) {
            return dfs(grid, 0, 0, grid.length);
        }

        public Node dfs(int[][] grid, int startX, int startY, int step){
            if(isSame(grid, startX, startY, step)){
                return new Node(grid[startX][startY] == 1, true);
            }

            return new Node(grid[startX][startY] == 1, false,
                    dfs(grid, startX, startY, step/2),
                    dfs(grid, startX, startY+step/2, step/2),
                    dfs(grid, startX+step/2, startY, step/2),
                    dfs(grid, startX+step/2, startY+step/2, step/2));
        }

        public boolean isSame(int[][] grid, int startX, int startY, int step){
            for(int i = 0;i < step;i++){
                for(int j = 0;j < step;j++){
                    if(grid[startX + i][startY + j] != grid[startX][startY])
                        return false;
                }
            }
            return true;
        }
    }

    /**
     * N 叉树的层序遍历（这不是白给？）
     */
    class LevelOrder {
        class Node {
            public int val;
            public List<Node> children;

            public Node() {}

            public Node(int _val) {
                val = _val;
            }

            public Node(int _val, List<Node> _children) {
                val = _val;
                children = _children;
            }
        };

        public List<List<Integer>> levelOrder(Node root) {
            if(root == null) return new ArrayList<>();

            List<List<Integer>> ans = new ArrayList<>();
            Queue<Node> queue = new LinkedList<>();
            queue.offer(root);

            int size, i;
            while (!queue.isEmpty()){
                size = queue.size();
                ArrayList<Integer> level = new ArrayList<>();
                for(i = 0;i < size;i++){
                    Node poll = queue.poll();
                    for (Node child : poll.children) {
                        queue.offer(child);
                    }
                    level.add(poll.val);
                }
                ans.add(level);
            }

            return ans;
        }
    }

    @Test

    void invoke(){
        // new Construct().construct(new int[][]{{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0}});
        // new Construct().construct(new int[][]{{1,1,0,0},{0,0,1,1},{1,1,0,0},{0,0,1,1}});
        // new Construct().construct(new int[][]{{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,0,0},{1,1,1,1,1,1,0,0},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{0,0,0,0,1,1,1,1},{0,0,0,0,1,1,1,1}});
    }
}
