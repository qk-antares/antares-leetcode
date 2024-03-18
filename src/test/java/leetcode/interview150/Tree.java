package leetcode.interview150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

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
     * 104. 二叉树的最大深度
     * 深度优先遍历解法
     */
    int maxDepth = 0;

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root, 0);
        return maxDepth;
    }

    public void dfs(TreeNode root, int curDepth) {
        curDepth++;
        maxDepth = Math.max(curDepth, maxDepth);
        if (root.left != null) {
            dfs(root.left, curDepth);
        }
        if (root.right != null) {
            dfs(root.right, curDepth);
        }
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

    /**
     * 637. 二叉树的层平均值
     * 二叉树的层次遍历，使用队列
     */
    public List<Double> averageOfLevels(TreeNode root) {
        ArrayDeque<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.addLast(root);
        ArrayList<Double> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            Double sum = 0.0;
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.removeFirst();
                sum += poll.val;
                if (poll.left != null) {
                    queue.addLast(poll.left);
                }
                if (poll.right != null) {
                    queue.addLast(poll.right);
                }
            }
            ans.add(sum / size);
        }
        return ans;
    }

    /**
     * 530. 二叉搜索树的最小绝对差
     * 对二叉搜索树进行前序遍历
     */
    public int getMinimumDifference(TreeNode root) {
        List<Integer> preOrder = new ArrayList<>();
        preOrder(root, preOrder);
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i < preOrder.size(); i++) {
            ans = Math.min(ans, preOrder.get(i) - preOrder.get(i-1));
        }
        return ans;
    }

    public void preOrder(TreeNode root, List<Integer> preOrder) {
        if(root.left != null) {
            preOrder(root.left, preOrder);
        }
        preOrder.add(root.val);
        if(root.right != null) {
            preOrder(root.right, preOrder);
        }
    }
}
