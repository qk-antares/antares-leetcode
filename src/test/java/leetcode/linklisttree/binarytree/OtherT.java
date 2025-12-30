package leetcode.linklisttree.binarytree;

import java.util.ArrayDeque;

import leetcode.common.TreeNode;

/**
 * 二叉树其他题目
 */
public class OtherT {
    /**
     * 173. 二叉搜索树迭代器 [Medium]
     * 
     * 用栈实现中序遍历
     * 或者直接把中序遍历的结果保存起来，每次取
     */
    class BSTIterator {
        ArrayDeque<TreeNode> stk;

        public BSTIterator(TreeNode root) {
            stk = new ArrayDeque<>();
            while (root != null) {
                stk.addLast(root);
                root = root.left;
            }
        }

        public int next() {
            TreeNode ans = stk.removeLast();
            TreeNode tmp = ans.right;
            while (tmp != null) {
                stk.addLast(tmp);
                tmp = tmp.left;
            }
            return ans.val;
        }

        public boolean hasNext() {
            return !stk.isEmpty();
        }
    }

    /**
     * 222. 完全二叉树的节点个数 [Easy]
     * 
     * 广度优先遍历
     * 
     * 二分答案，向左找到树高
     */
    public int countNodes(TreeNode root) {
        if (root == null)
            return 0;
        int depth = 0;
        TreeNode cur = root;
        while (cur != null) {
            depth++;
            cur = cur.left;
        }

        // 高depth的完全二叉树，节点数[2^(depth-1), 2^depth-1]
        int left = 1 << (depth - 1), right = (1 << depth) - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            // 从第2个bit位开始，0往左，1往右
            if (exists(root, depth, mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }

    public boolean exists(TreeNode root, int depth, int k) {
        TreeNode cur = root;
        int bits = 1 << (depth - 2);
        while (bits > 0) {
            if ((bits & k) != 0)
                cur = cur.right;
            else
                cur = cur.left;

            bits >>= 1;
        }
        return cur != null;
    }
}
