package leetcode.linklisttree.binarytree;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import leetcode.common.TreeNode;

/**
 * 有递有归
 * 二叉树的直径（递归问题）
 */
public class RecursionT {
    /**
     * 865. 具有所有最深节点的最小子树
     * 
     * 遍历一遍树，遍历的过程中记录父节点和节点深度
     * 从最深的节点往上归并，记录他们的父节点，直至父节点集合的大小为1，此时该父节点即为答案
     * 
     * 答案有以下特点：左子树的最大深度和右子树的最大深度一样（但凡该节点再往上，就不满足）
     * 满足上述特点的最上层节点是答案
     * dfs确保上层节点能更新最新答案
     * 子树的深度由叶节点决定，所以只有在叶节点更新maxDepth
     */
    TreeNode ansN = null;
    int maxDepth = -1;

    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        dfs(root, 0);
        return ansN;
    }

    int dfs(TreeNode root, int depth) {
        if(root == null) {
            maxDepth = Math.max(maxDepth, depth);
            return depth;
        }

        int leftMaxDepth =dfs(root.left, depth+1);
        int rightMaxDepth = dfs(root.right, depth+1);

        if(leftMaxDepth == maxDepth && rightMaxDepth == maxDepth) ansN = root;

        return Math.max(leftMaxDepth, rightMaxDepth);
    }

    public TreeNode subtreeWithAllDeepest0(TreeNode root) {
        Map<TreeNode, TreeNode> fa = new HashMap<>();
        Set<TreeNode> maxDepth = null;

        // 广度优先遍历
        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            maxDepth = new HashSet<>();
            for (int i = 0; i < size; i++) {
                TreeNode tmp = q.poll();
                maxDepth.add(tmp);
                if (tmp.left != null) {
                    q.offer(tmp.left);
                    fa.put(tmp.left, tmp);
                }
                if (tmp.right != null) {
                    q.offer(tmp.right);
                    fa.put(tmp.right, tmp);
                }
            }
        }

        // 此时maxDepth记录了最深的节点，然后往上归并
        while (maxDepth.size() > 1) {
            Set<TreeNode> tmp = new HashSet<>();
            for (TreeNode n : maxDepth) {
                tmp.add(fa.get(n));
            }
            maxDepth = tmp;
        }

        return maxDepth.iterator().next();
    }

    /*
     * ========================== 分割线 ==========================
     */

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
