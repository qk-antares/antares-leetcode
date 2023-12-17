package leetcode.questions.Easy;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.junit.jupiter.api.Test;

import leetcode.common.TreeNode;

/**
 * @author Antares
 * @date 2022/9/5
 */
public class EasyTree {
    /**
     * 二叉树的最大深度我的解法(递归解法，内存消耗比较大)
     */
    public int maxDepth(TreeNode root) {
        if(root == null)
            return 0;

        if(root.left == null && root.right == null){
            return 1;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * 解法二：BFS(广度优先遍历，使用了双端队列Dequeue，内存消耗小了，但是时间复杂度高)
     * 需要注意的是，Dequeue还是可以使用push和pop的，这两个函数操作的都是队头
     */
    public int maxDepth0(TreeNode root) {
        if(root == null)
            return 0;

        Deque<TreeNode> deque = new LinkedList<>();
        int count = 0;
        int size;
        deque.addFirst(root);
        while (!deque.isEmpty()){
            //记录每一层的结点数
            size = deque.size();
            while (size > 0){
                TreeNode pop = deque.removeFirst();
                if(pop.left != null)
                    deque.addLast(pop.left);
                if(pop.right != null)
                    deque.addLast(pop.right);
                size--;
            }
            count++;
        }
        return count;
    }

    /**
     * 解法三：DFS（深度优先遍历，内存消耗更小了，但是时间复杂度依然较高）
     * 使用两个stack，一个记录结点，另一个记录节点所在的层数
     * 深度优先遍历只有一层for循环，而广度优先遍历是两层for循环
     */
    public int maxDepth1(TreeNode root) {
        if(root == null)
            return 0;
        Stack<TreeNode> treeNodes = new Stack<>();
        Stack<Integer> levels = new Stack<>();
        treeNodes.push(root);
        levels.push(1);
        int max = 0;
        while (!treeNodes.isEmpty()){
            TreeNode node = treeNodes.pop();
            int level = levels.pop();
            max = Math.max(level, max);
            if(node.left != null){
                treeNodes.push(node.left);
                levels.add(level + 1);
            }
            if(node.right != null){
                treeNodes.push(node.right);
                levels.add(level + 1);
            }
        }
        return max;
    }

    /**
     * 验证二叉搜索树(没想出来，解法一：递归写法，效率极高)
     */
    public boolean isValidBST(TreeNode root) {
        //注意这里必须使用Long而不是Integer，因为根节点取Integer.MIN_VALUE,或者Integer.MAX_VALUE都是可以的
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode root, long min, long max){
        if(root == null)
            return true;
        if(root.val >= max || root.val <= min)
            return false;
        return isValidBST(root.left, min, root.val) && isValidBST(root.right, root.val, max);
    }

    /**
     * 解法二：中序遍历（左子树，根节点，右子树）递归写法(内存消耗稍高)
     * 二叉搜索树的中序遍历结果有序（递增）
     */
    //前一个结点
    TreeNode pre;
    public boolean isValidBST0(TreeNode root){
        if(root == null)
            return true;

        if(!isValidBST0(root.left))
            return false;

        if(pre != null && pre.val >= root.val)
            return false;
        pre = root;
        if(!isValidBST0(root.right))
            return false;
        return true;
    }

    /**
     * 解法三：中序遍历非递归(不管是时间还是空间表现都比较差)
     */
    public boolean isValidBST1(TreeNode root){
        if(root == null)
            return true;

        Stack<TreeNode> treeNodes = new Stack<>();
        TreeNode pre = null;
        while (root != null || !treeNodes.isEmpty()){
            while (root != null){
                treeNodes.push(root);
                root = root.left;
            }

            root = treeNodes.pop();
            if(pre != null && root.val <= pre.val)
                return false;
            pre = root;
            root = root.right;
        }
        return true;
    }

    /**
     * 对称二叉树（我的解法：递归，效率还算可以哦！）
     */
    public boolean isSymmetric(TreeNode root) {
        if(root == null)
            return true;
        return isSymmetricTrees(root.left, root.right);
    }

    public boolean isSymmetricTrees(TreeNode left,TreeNode right){
        if(left == null ^ right == null)
            return false;
        if(left == null && right == null)
            return true;
        //到了叶节点
        if(left.left == null && left.right == null && right.left == null && right.right == null)
            return left.val == right.val;
        //没到叶节点
        return left.val == right.val && isSymmetricTrees(left.left, right.right) && isSymmetricTrees(left.right, right.left);
    }

    /**
     * 答案解法（思路是一样的，只是代码更加简洁了！）
     */
    public boolean isSymmetricTrees0(TreeNode left,TreeNode right){
        //如果左右子节点都为空，说明当前节点是叶子节点，返回true
        if (left == null && right == null)
            return true;
        //如果当前节点只有一个子节点或者有两个子节点，但两个子节点的值不相同，直接返回false（这一步大大简略了！！！）
        if (left == null || right == null || left.val != right.val)
            return false;
        //然后左子节点的左子节点和右子节点的右子节点比较，左子节点的右子节点和右子节点的左子节点比较
        return isSymmetricTrees0(left.left, right.right) && isSymmetricTrees0(left.right, right.left);
    }

    /**
     * 答案解法（非递归，表现最差）
     */
    public boolean isSymmetric0(TreeNode root) {
        if(root == null)
            return true;

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root.left);
        queue.add(root.right);

        while (!queue.isEmpty()){
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            //注意这里是continue而不是return true，在递归调用中这里return true代表两颗子树符合对称，而这里没有递归
            if(left == null && right == null)
                continue;
            if (left == null || right == null || left.val != right.val)
                return false;
            queue.add(left.left);
            queue.add(right.right);
            queue.add(left.right);
            queue.add(right.left);
        }
        return true;
    }

    /**
     * 二叉树的层序遍历（我的解法：BFS广度优先遍历，效率并不是很高）
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();

        if(root == null)
            return res;

        Queue<TreeNode> treeNodes = new LinkedList<>();
        treeNodes.add(root);
        //size是当前层的结点个数
        int size;
        while (!treeNodes.isEmpty()){
            size = treeNodes.size();
            //这里存放当前层的结果
            List<Integer> temp = new LinkedList<>();
            while (size-- > 0){
                TreeNode poll = treeNodes.poll();
                temp.add(poll.val);
                if(poll.left != null)
                    treeNodes.add(poll.left);
                if(poll.right != null)
                    treeNodes.add(poll.right);
            }
            res.add(temp);
        }

        return res;
    }

    /**
     * 解法二（DFS深度优先遍历，只是添加了一个level变量，时间复杂度相比于BFS还是有提升的）
     */
    public List<List<Integer>> levelOrder0(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        DFS(root, res, 1);
        return res;
    }

    //level代表当前所在层
    public void DFS(TreeNode root, List<List<Integer>> res, int level){
        if(root == null)
            return;

        if(level > res.size())
            res.add(new LinkedList<>());

        res.get(level).add(root.val);

        DFS(root.left, res, level + 1);
        DFS(root.right, res, level + 1);
    }

    /**
     * 将有序数组转换为二叉搜索树（没想出来，看完解释的第一句恍悟，使用递归解决）
     * 每次都取数组的中间值来作为根节点，左侧构成左子树，右侧构成右子树
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        int len = nums.length;
        if(len == 0)
            return null;
        return sortedArrayToBSTHelper(nums, 0, len-1);
    }

    public TreeNode sortedArrayToBSTHelper(int[] nums, int startIndex, int endIndex){
        if(startIndex <= endIndex){
            TreeNode root = new TreeNode(nums[(startIndex + endIndex)/2]);
            root.left = sortedArrayToBSTHelper(nums, startIndex, (startIndex + endIndex)/2-1);
            root.right = sortedArrayToBSTHelper(nums, (startIndex + endIndex)/2+1, endIndex);
            return root;
        }
        return null;
    }

    @Test
    public void invoke(){
//        merge2(new int[]{1,2,3,0,0,0},3,new int[]{2,5,6},3);
    }


}
