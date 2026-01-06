package leetcode.linklisttree.binarytree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import leetcode.common.TreeNode;

public class BFST {
    /**
     * 637. 二叉树的层平均值 [Easy]
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> ans = new ArrayList<>();
        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            long sum = 0;
            for (int i = 0; i < size; i++) {
                TreeNode tmp = q.poll();
                sum += tmp.val;
                if (tmp.left != null)
                    q.offer(tmp.left);
                if (tmp.right != null)
                    q.offer(tmp.right);
            }
            ans.add(sum / (double) size);
        }
        return ans;
    }

    /**
     * 102. 二叉树的层序遍历 [Medium]
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null)
            return ans;
        q.add(root);

        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> row = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode tmp = q.poll();
                row.add(tmp.val);
                if (tmp.left != null)
                    q.offer(tmp.left);
                if (tmp.right != null)
                    q.offer(tmp.right);
            }
            ans.add(row);
        }

        return ans;
    }

    /**
     * TODO 103. 二叉树的锯齿形层序遍历 [Medium]
     * 
     * 多一个flag变量
     * 错误思路：每层根据flag决定是从队头取还是从队尾取
     * 正确思路：每层正常从队头取，最后根据flag决定是否反转该层结果
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null)
            return ans;
        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        boolean flag = false;
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> row = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode tmp = q.poll();
                row.add(tmp.val);
                if (tmp.left != null)
                    q.offer(tmp.left);
                if (tmp.right != null)
                    q.offer(tmp.right);
            }

            if (flag) {
                Collections.reverse(row);
            }

            ans.add(row);
            flag = !flag;
        }
        return ans;
    }

    /**
     * 1161. 最大层内元素和 [Medium]
     * 
     * 二叉树的层序遍历
     */
    public int maxLevelSum(TreeNode root) {
        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        int idx = 0;
        int ans = 0;
        int maxSum = Integer.MIN_VALUE;
        while (!q.isEmpty()) {
            idx++;
            int size = q.size();
            int sum = 0;
            for (int i = 0; i < size; i++) {
                TreeNode tmp = q.poll();
                sum += tmp.val;
                if (tmp.left != null)
                    q.offer(tmp.left);
                if (tmp.right != null)
                    q.offer(tmp.right);
            }
            if (sum > maxSum) {
                maxSum = sum;
                ans = idx;
            }
        }
        return ans;
    }
}
