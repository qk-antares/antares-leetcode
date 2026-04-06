package leetcode.linklisttree.binarytree;

import java.util.HashMap;
import java.util.Map;

import leetcode.common.TreeNode;

/**
 * 二叉树
 * 回溯
 */
public class BacktrackT {
    /**
     * 437. 路径总和 III
     * 
     * 回溯，dfs，前缀和
     * 可以理解为数组求和为k的子数组的变形
     */
    int ans = 0;

    public int pathSum(TreeNode root, int targetSum) {
        if (root == null)
            return 0;

        Map<Long, Integer> cnt = new HashMap<>();
        cnt.put(0L, 1);
        dfs(root, 0L, targetSum, cnt);
        return ans;
    }

    void dfs(TreeNode root, long preSum, int targetSum, Map<Long, Integer> cnt) {
        preSum += root.val;
        ans += cnt.getOrDefault(preSum - targetSum, 0);
        cnt.merge(preSum, 1, Integer::sum);

        if (root.left != null)
            dfs(root.left, preSum, targetSum, cnt);
        if (root.right != null)
            dfs(root.right, preSum, targetSum, cnt);

        cnt.merge(preSum, -1, Integer::sum);
    }
}
