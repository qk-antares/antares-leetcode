package leetcode.interview150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import leetcode.common.TreeNode;

public class Tree {
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
                if(poll.left != null) {
                    queue.addLast(poll.left);
                }
                if(poll.right != null) {
                    queue.addLast(poll.right);
                }
            }
            ans.add(sum / size);
        }
        return ans;
    }
}
