package leetcode.linklisttree.binarytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import leetcode.common.Node;
import leetcode.common.TreeNode;

/**
 * 链表+二叉树
 */
public class LinkTreeT {

    /**
     * 117. 填充每个节点的下一个右侧节点指针 II [Medium]
     * 
     * 二叉树的层序遍历，通过一个队列实现的
     */
    public Node connect(Node root) {
        if (root == null)
            return null;

        Queue<Node> nodes = new LinkedList<>();
        nodes.offer(root);

        int size;
        int i;
        while (!nodes.isEmpty()) {
            // 当前行的结点数
            size = nodes.size();
            Node poll = nodes.peek();
            for (i = 0; i < size; i++) {
                poll = nodes.poll();
                poll.next = nodes.peek();

                if (poll.left != null)
                    nodes.offer(poll.left);
                if (poll.right != null)
                    nodes.offer(poll.right);
            }
            poll.next = null;
        }

        return root;
    }

    /**
     * 114. 二叉树展开为链表 [Medium]
     * 
     * 用一个List来保存先序遍历的结果，反正保存的是引用
     * 或者用递归+头插法（效率更高）
     */
    public void flatten0(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        preorder(root, list);
        TreeNode dummy = new TreeNode();
        for (TreeNode node : list) {
            dummy.left = null;
            dummy.right = node;
            dummy = dummy.right;
        }
    }

    public void preorder(TreeNode root, List<TreeNode> list) {
        if (root == null)
            return;
        list.add(root);
        preorder(root.left, list);
        preorder(root.right, list);
    }

    // 头插法，插入当前节点的前面
    private TreeNode head;

    public void flatten(TreeNode root) {
        if (root == null)
            return;
        flatten(root.right);
        flatten(root.left);
        root.left = null;
        // 将root插入当前头节点的前面
        root.right = head;
        head = root;
    }
}
