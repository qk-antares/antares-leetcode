package leetcode.linklisttree.binarytree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import leetcode.common.TreeNode;

/**
 * 二叉树的遍历
 */
public class TraversalT {
    /**
     * 94. 二叉树的中序遍历 [Easy]
     * 
     * 非递归写法用栈
     */
    public List<Integer> inorderTraversal0(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        ArrayDeque<TreeNode> stk = new ArrayDeque<>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }

            TreeNode pop = stk.pop();
            ans.add(pop.val);
            root = pop.right;
        }

        return ans;
    }

    List<Integer> ans = new ArrayList<>();

    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null)
            return ans;
        inorderTraversal(root.left);
        ans.add(root.val);
        inorderTraversal(root.right);
        return ans;
    }
}