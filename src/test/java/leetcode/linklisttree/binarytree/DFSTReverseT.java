package leetcode.linklisttree.binarytree;

import leetcode.common.TreeNode;

/**
 * DFS遍历框架（自底向上）
 * 
 * 在归的过程中计算值
 */
public class DFSTReverseT {
    /**
     * 110. 平衡二叉树
     * 
     * 找到一棵子树不满足条件直接返回-1，最后判断根节点的返回值是否为-1
     */
    public boolean isBalanced(TreeNode root) {
        return height(root) >= 0;
    }

    int height(TreeNode root) {
        if (root == null)
            return 0;
        int left = height(root.left);
        int right = height(root.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1)
            return -1;
        return Math.max(left, right) + 1;
    }
}
