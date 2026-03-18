package leetcode.interview150;

import java.util.ArrayDeque;

import leetcode.common.TreeNode;

public class Tree {
    /**
     * 104. 二叉树的最大深度
     * 广度优先遍历解法
     */
    public int maxDepth0(TreeNode root) {
        if (root == null) {
            return 0;
        }

        ArrayDeque<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.addLast(root);

        int ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            ans++;
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.removeFirst();
                if (poll.left != null) {
                    queue.addLast(poll.left);
                }
                if (poll.right != null) {
                    queue.addLast(poll.right);
                }
            }
        }
        return ans;
    }

    /**
     * 100. 相同的树
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null || p.val != q.val) {
            return false;
        }

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 226. 翻转二叉树
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null || root.left == null && root.right == null) {
            return root;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    /**
     * 101. 对称二叉树
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetricHelper(root.left, root.right);
    }

    public boolean isSymmetricHelper(TreeNode tree1, TreeNode tree2) {
        if (tree1 == null && tree2 == null) {
            return true;
        } else if (tree1 == null || tree2 == null || tree1.val != tree2.val) {
            return false;
        }
        return isSymmetricHelper(tree1.left, tree2.right) && isSymmetricHelper(tree1.right, tree2.left);
    }
}
