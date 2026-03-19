package leetcode.questions.Easy;

import java.util.LinkedList;
import java.util.Queue;

import leetcode.common.TreeNode;

/**
 * @author Antares
 * @date 2022/9/5
 */
public class EasyTree {
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
}
