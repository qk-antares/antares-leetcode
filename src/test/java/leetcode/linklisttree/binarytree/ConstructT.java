package leetcode.linklisttree.binarytree;

import java.util.HashMap;
import java.util.Map;

import leetcode.common.TreeNode;

/**
 * 二叉树的构造
 */
public class ConstructT {
    /**
     * 106. 从中序与后序遍历序列构造二叉树 [Medium]
     * 
     * 后序遍历[左,右, 根]，中序遍历[左, 根, 右]
     * 根据后序遍历，找到根在中序遍历的位置
     * 为避免找根在中序遍历的位置时每次重新遍历，可用Map预先保存
     */
    public TreeNode buildTree0(int[] inorder, int[] postorder) {
        Map<Integer, Integer> idxMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++)
            idxMap.put(inorder[i], i);

        return helper0(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1, idxMap);
    }

    public TreeNode helper0(int[] inorder, int l1, int r1, int[] postorder, int l2, int r2,
            Map<Integer, Integer> idxMap) {
        if (l1 > r1 || l2 > r2)
            return null;

        TreeNode root = new TreeNode(postorder[r2]);

        // 找到r2在inorder中的位置
        int idx = idxMap.get(postorder[r2]);

        root.left = helper0(inorder, l1, idx - 1, postorder, l2, l2 + idx - 1 - l1, idxMap);
        root.right = helper0(inorder, idx + 1, r1, postorder, l2 + idx - l1, r2 - 1, idxMap);

        return root;
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     */
    public TreeNode buildTree1(int[] preorder, int[] inorder) {
        Map<Integer, Integer> idxMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            idxMap.put(inorder[i], i);
        }

        return helper1(inorder, 0, inorder.length - 1, preorder, 0, preorder.length - 1, idxMap);
    }

    public TreeNode helper1(int[] inorder, int l1, int r1, int[] preorder, int l2, int r2,
            Map<Integer, Integer> idxMap) {
        if (l1 > r1 || l2 > r2)
            return null;

        TreeNode root = new TreeNode(preorder[l2]);
        int idx = idxMap.get(preorder[l2]);

        root.left = helper1(inorder, l1, idx - 1, preorder, l2 + 1, l2 + idx - l1, idxMap);
        root.right = helper1(inorder, idx + 1, r1, preorder, l2 + idx - l1 + 1, r2, idxMap);

        return root;
    }
}
