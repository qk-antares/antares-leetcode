package leetcode.linklisttree.binarytree;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import leetcode.common.TreeNode;

/**
 * 最近公共祖先
 */
public class NearestAncestorT {
    /**
     * 236. 二叉树的最近公共祖先 [Medium]
     * 
     * 用HashMap记录每个节点的父节点
     * 获取从p到root的路径(用Set存储)
     * 获取q到root的路径，直到和p到root的路径重合
     * 
     * 从root往下寻找
     * 找到p/q返回，或者到null节点了，返回null
     * 第一次在左右各找到p/q时，返回该节点
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q)
            return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left == null)
            return right;
        if (right == null)
            return left;
        return root;
    }

    public TreeNode lowestCommonAncestor0(TreeNode root, TreeNode p, TreeNode q) {
        HashMap<TreeNode, TreeNode> map = new HashMap<>();
        ArrayDeque<TreeNode> que = new ArrayDeque<>();
        que.offer(root);
        while(!que.isEmpty()) {
            int size = que.size();
            for(int i = 0; i < size; i++) {
                TreeNode tmp = que.poll();
                if(tmp.left != null) {
                    map.put(tmp.left, tmp);
                    que.offer(tmp.left);
                }
                if(tmp.right != null) {
                    map.put(tmp.right, tmp);
                    que.offer(tmp.right);
                }
            }
        }

        Set<TreeNode> path = new HashSet<>();
        while(p != null) {
            path.add(p);
            p = map.get(p);
        }

        while(!path.contains(q)) {
            q = map.get(q);
        }
        
        return q;
    }
}
