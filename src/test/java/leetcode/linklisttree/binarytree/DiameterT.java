package leetcode.linklisttree.binarytree;

import leetcode.common.TreeNode;

/**
 * 二叉树的直径
 */
public class DiameterT {
    /**
     * 543. 二叉树的直径 [Easy]
     */
    int ans = Integer.MIN_VALUE;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return ans - 1;
    }

    int dfs(TreeNode root) {
        if (root == null)
            return 0;

        int l = dfs(root.left);
        int r = dfs(root.right);
        ans = Math.max(ans, l + r + 1);
        return 1 + Math.max(l, r);
    }
}
