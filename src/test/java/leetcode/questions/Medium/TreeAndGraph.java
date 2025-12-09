package leetcode.questions.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.junit.jupiter.api.Test;

import leetcode.common.TreeNode;

/**
 * @author Antares
 * @date 2022/9/22
 */
public class TreeAndGraph {
    /**
     * 对于树的一些总结：
     * 1. 中序遍历：栈
     * 2. 深度优先遍历：栈
     * 3. 广度优先遍历：队列
     */


    /**
     * 二叉树的中序遍历（我的解法：递归，内存消耗较大）
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();

        if(root == null)
            return ans;

        ans.addAll(inorderTraversal(root.left));
        ans.add(root.val);
        ans.addAll(inorderTraversal(root.right));

        return ans;
    }

    /**
     * 答案解法（使用栈）
     */
    public List<Integer> inorderTraversal0(TreeNode root) {
        if(root == null)
            return new ArrayList<>();

        ArrayList<Integer> ans = new ArrayList<>();
        Stack<TreeNode> treeNodes = new Stack<>();

        //先一直网左子树的方向
        while (root != null || !treeNodes.isEmpty()){
               while (root != null){
                   treeNodes.push(root);
                   root = root.left;
               }

               //转到右子树
               if(!treeNodes.isEmpty()){
                   TreeNode node = treeNodes.pop();
                   ans.add(node.val);
                   root = node.right;
               }
        }

        return ans;
    }

    /**
     * 二叉树的锯齿形层次遍历（我的解法，使用队列和一个临时变量来判断锯齿方向，思路有问题）
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null)
            return new ArrayList<>();

        List<List<Integer>> ans = new ArrayList<>();

        Deque<TreeNode> treeNodes = new LinkedList<>();
        treeNodes.addFirst(root);
        ans.add(new ArrayList<Integer>(Arrays.asList(root.val)));

        //size是层中的结点数
        int size;
        //flag代表锯齿的方向，true代表左，false代表右
        boolean flag = false;
        while (!treeNodes.isEmpty()){
            size = treeNodes.size();
            System.out.println(size);
            ArrayList<Integer> level = new ArrayList<>();
            if(flag){
                for(int i = 0;i < size;i++) {
                    TreeNode node = treeNodes.removeLast();
                    if (node.left != null){
                        treeNodes.addLast(node.left);
                        level.add(node.left.val);
                    }
                    if (node.right != null){
                        treeNodes.addLast(node.right);
                        level.add(node.right.val);
                    }
                }
            }else {
                for(int i = 0;i < size;i++) {
                    TreeNode node = treeNodes.removeFirst();
                    if(node.right != null){
                        treeNodes.addLast(node.right);
                        level.add(node.right.val);
                    }
                    if(node.left != null){
                        treeNodes.addLast(node.left);
                        level.add(node.left.val);
                    }
                }
            }
            flag = !flag;
            if(!level.isEmpty())
                ans.add(level);
        }
        return ans;
    }

    /**
     * 答案解法：就是普通的深度优先遍历，只不过有些层的结果调用以下reverse
     */
    public List<List<Integer>> zigzagLevelOrder0(TreeNode root) {
        if (root == null)
            return new ArrayList<>();

        List<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> treeNodes = new LinkedList<>();
        treeNodes.offer(root);

        //size是层中的结点数
        int size;
        //flag代表锯齿的方向，true代表左，false代表右
        boolean flag = true;
        while (!treeNodes.isEmpty()){
            size = treeNodes.size();
            ArrayList<Integer> level = new ArrayList<>();

            for(int i = 0;i < size;i++){
                TreeNode poll = treeNodes.poll();
                level.add(poll.val);

                if(poll.left != null)
                    treeNodes.offer(poll.left);
                if(poll.right != null)
                    treeNodes.offer(poll.right);
            }

            if(!flag){
                Collections.reverse(level);
            }
            ans.add(level);
            flag = !flag;
        }
        return ans;
    }

    /**
     * 二叉搜索树中第K小的元素（我的解法：中序遍历，使用栈）
     * 二叉搜索树的中序遍历结果就是递增排序的结果
     */
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> treeNodes = new Stack<>();
        treeNodes.push(root);

        while (root != null || !treeNodes.isEmpty()){
            //先把左子树的结点进栈
            while (root != null){
                treeNodes.push(root.left);
                root = root.left;
            }

            TreeNode pop = treeNodes.pop();

            if(pop != null){
                k--;
                if(k == 0)
                    return pop.val;

                if(pop.right != null)
                    treeNodes.push(pop.right);

                root = pop.right;
            }
        }

        return -1;
    }

    /**
     * 岛屿数量（答案解法一：深度优先搜索，遍历grid，每遇到一个1，就将其相邻的所有1单元格置为0，效率已经很不错了）
     */
    private int height;
    private int width;
    public int numIslands(char[][] grid) {
        height = grid.length;
        width = grid[0].length;

        int ans = 0;
        for(int i = 0;i < height;i++){
            for(int j = 0;j < width;j++){
                if(grid[i][j] == '1'){
                    grid[i][j] = '0';
                    ans++;
                    dfs(grid, i, j);
                }
            }
        }

        return ans;
    }

    public void dfs(char[][] grid, int i,int j){
        if(i-1 > -1 && grid[i-1][j] == '1'){
            grid[i-1][j] = '0';
            dfs(grid, i-1, j);
        }

        if(i+1 < height && grid[i+1][j] == '1'){
            grid[i+1][j] = '0';
            dfs(grid, i+1, j);
        }

        if(j-1 > -1 && grid[i][j-1] == '1'){
            grid[i][j-1] = '0';
            dfs(grid, i, j-1);
        }

        if(j+1 < width && grid[i][j+1] == '1'){
            grid[i][j+1] = '0';
            dfs(grid, i, j+1);
        }
    }

    /**
     * 答案解法二：广度优先遍历（使用了队列，无递归。小技巧：将二维数组中的坐标（i，j）转化成一个数m）
     * m = i * grid[0].length + j   (很显然，j < grid[0].length)
     *
     * i = m / grid[0].length
     * j = m % grid[0].length
     */
    public int numIslands0(char[][] grid) {
        height = grid.length;
        width = grid[0].length;

        int ans = 0;
        for(int i = 0;i < height;i++){
            for(int j = 0;j < width;j++){
                if(grid[i][j] == '1'){
                    ans++;
                    bfs(grid, i, j);
                }
            }
        }

        return ans;
    }

    public void bfs(char[][] grid, int i,int j){
        //首先将当前单元格置为0
        grid[i][j] = '0';
        //对坐标进行转换
        int res = i * width + j;

        Queue<Integer> nodes = new LinkedList<>();
        nodes.offer(res);

        while (!nodes.isEmpty()){
            //将坐标还原出来
            Integer poll = nodes.poll();
            int x = poll / width;
            int y = poll % width;

            //对邻居单元格进行判断
            for(int m = -1;m <= 1;m+=2){
                if(x+m > -1 && x+m < height && grid[x+m][y] == '1'){
                    grid[x+m][y] = '0';
                    nodes.add((x+m) * width + y);
                }
                if(y+m > -1 && y+m < width && grid[x][y+m] == '1'){
                    grid[x][y+m] = '0';
                    nodes.add(x * width + y+m);
                }
            }
        }
    }



    @Test
    public void invoke(){
//        buildTree(new int[]{3,9,20,15,7}, new int[]{9,3,15,20,7});
        numIslands0(new char[][]{{'1','1','1','1','0'},{'1','1','0','1','0'},{'1','1','0','0','0'},{'0','0','0','0','0'}});
    }
}
