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
}
