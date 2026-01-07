package leetcode.linklisttree.binarytree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import leetcode.common.TreeNode;

/**
 * DFS遍历框架
 */
public class DFST {
    /**
     * 199. 二叉树的右视图
     * 
     * 层序遍历
     * 
     * 或者深度优先遍历并维护一个maxDepth全局变量
     * 优先访问右子树，当首次访问到某一深度时，记录该节点
     */
    int maxDepth = 0;

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;

        dfs(root, 1, ans);

        return ans;
    }

    void dfs(TreeNode cur, int depth, List<Integer> ans) {
        if (depth > maxDepth) {
            ans.add(cur.val);
            maxDepth = depth;
        }

        if (cur.right != null)
            dfs(cur.right, depth + 1, ans);
        if (cur.left != null)
            dfs(cur.left, depth + 1, ans);
    }

    public List<Integer> rightSideView0(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;

        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            ans.add(q.peek().val);
            for (int i = 0; i < size; i++) {
                TreeNode tmp = q.poll();
                if (tmp.right != null) {
                    q.offer(tmp.right);
                }
                if (tmp.left != null) {
                    q.offer(tmp.left);
                }
            }
        }
        return ans;
    }

    /**
     * 1339. 分裂二叉树的最大乘积 [Medium]
     * 
     * 本质是计算每个子树的和
     * 先计算每个子树的和，保存在一个list中
     * (total-sum) * sum = total*sum-sum*sum
     * 
     * 或者两次dfs
     */
    long ans = 0;

    public int maxProduct(TreeNode root) {
        int total = dfs1(root);
        dfs2(root, total);
        return (int) (ans % 1_000_000_007);
    }

    public int dfs1(TreeNode root) {
        int sum = root.val;
        if (root.left != null)
            sum += dfs1(root.left);
        if (root.right != null)
            sum += dfs1(root.right);
        return sum;
    }

    public int dfs2(TreeNode root, int total) {
        int sum = root.val;
        if (root.left != null)
            sum += dfs2(root.left, total);
        if (root.right != null)
            sum += dfs2(root.right, total);
        ans = Math.max(ans, (long) (total - sum) * sum);

        return sum;
    }

    public int maxProduct0(TreeNode root) {
        List<Integer> treeSum = new ArrayList<>();
        int total = computeTreeSum(root, treeSum);
        long ans = 0;
        for (int sum : treeSum) {
            ans = Math.max(ans, (long) (total - sum) * sum);
        }
        return (int) (ans % 1_000_000_007);
    }

    public int computeTreeSum(TreeNode root, List<Integer> treeSum) {
        int sum = root.val;
        if (root.left != null)
            sum += computeTreeSum(root.left, treeSum);
        if (root.right != null)
            sum += computeTreeSum(root.right, treeSum);
        treeSum.add(sum);
        return sum;
    }
}
