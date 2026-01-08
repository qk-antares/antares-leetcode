package leetcode.linklisttree.binarytree;

import java.util.ArrayList;
import java.util.List;

import leetcode.common.TreeNode;

/**
 * 二叉搜索树，一般中序遍历
 */
public class SearchTreeT {
    /**
     * TODO 530. 二叉搜索树的最小绝对差 [Easy]
     * 
     * 对二叉搜索树进行前序遍历，得到有序数组，这个效率很低
     * 
     * 或者维护前一个节点prev
     */
    int ans0 = Integer.MAX_VALUE;
    int prev = Integer.MIN_VALUE / 2;

    // 二叉搜索树的中序遍历是有序的，直接中序遍历一遍，记录上个节点就好
    public int getMinimumDifference(TreeNode root) {
        inorder(root);
        return ans0;
    }

    void inorder(TreeNode cur) {
        if (cur == null)
            return;
        inorder(cur.left);
        ans0 = Math.min(ans0, cur.val - prev);
        prev = cur.val;
        inorder(cur.right);
    }

    public int getMinimumDifference0(TreeNode root) {
        List<Integer> inOrder = new ArrayList<>();
        inOrder0(root, inOrder);
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i < inOrder.size(); i++) {
            ans = Math.min(ans, inOrder.get(i) - inOrder.get(i - 1));
        }
        return ans;
    }

    public void inOrder0(TreeNode root, List<Integer> inOrder) {
        if (root.left != null) {
            inOrder0(root.left, inOrder);
        }
        inOrder.add(root.val);
        if (root.right != null) {
            inOrder0(root.right, inOrder);
        }
    }

    /**
     * 230. 二叉搜索树中第 K 小的元素 [Medium]
     * 
     * 维护一个全局的当前访问到的元素，思路和[530]类似
     */
    int cur = 0;
    int ans = -1;

    public int kthSmallest(TreeNode root, int k) {
        inorder(root, k);
        return ans;
    }

    void inorder(TreeNode root, int k) {
        if (root == null)
            return;
        inorder(root.left, k);
        cur++;
        if (cur == k) {
            ans = root.val;
            return;
        }
        inorder(root.right, k);
    }

    /**
     * 98. 验证二叉搜索树
     * 
     * 中序遍历，记录上一个节点
     */
    long prev1 = Long.MIN_VALUE;
    boolean ans1 = true;

    public boolean isValidBST(TreeNode root) {
        inorder1(root);
        return ans1;
    }

    void inorder1(TreeNode root) {
        if (root == null)
            return;
        inorder1(root.left);
        if (root.val <= prev1) {
            ans1 = false;
            return;
        }
        prev1 = root.val;
        inorder1(root.right);
    }
}