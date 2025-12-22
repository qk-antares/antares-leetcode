package leetcode.linklisttree.binarytree;

import leetcode.common.TreeNode;

/**
 * 二叉树的直径（递归问题）
 */
public class RecursionT {
    /**
     * 124. 二叉树中的最大路径和 [Hard]
     * 
     * 枚举树中的每个节点作为拐弯点
     * 则 ans = Math.max(ans, 拐弯点.val+左子树的最大链+右子树的最大链)
     * 写一个函数来计算树的最大链
     */

    int ans = Integer.MIN_VALUE;

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
