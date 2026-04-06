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

    /**
     * 124. 二叉树中的最大路径和 [Hard]
     * 
     * 枚举树中的每个节点作为拐弯点
     * 则 ans = Math.max(ans, 拐弯点.val+左子树的最大链+右子树的最大链)
     * 写一个函数来计算树的最大链
     */
    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return ans;
    }

    int maxGain(TreeNode root) {
        if (root == null)
            return 0;

        int leftGain = Math.max(maxGain(root.left), 0);
        int rightGain = Math.max(maxGain(root.right), 0);

        ans = Math.max(ans, root.val + leftGain + rightGain);
        return Math.max(root.val + leftGain, root.val + rightGain);
    }
}
