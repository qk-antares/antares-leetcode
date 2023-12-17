package leetcode.datastruture.tree;

import leetcode.common.TreeNode;

public class BinaryTreeHard {
    /**
     * 二叉树中的最大路径和
     * 每个结点维护一个贡献值，贡献值 = 结点值 + max{左子树值 + 右子树值}
     * 之后对于每个结点，则经过该结点的最长路径 = 该节点的值 + max{0, 左子树值，右子树值}
     */
    class MaxPathSum {
        public int ans = Integer.MIN_VALUE;

        public int maxPathSum(TreeNode root) {
            maxGain(root);
            return ans;
        }

        public int maxGain(TreeNode root){
            if(root == null)
                return 0;

            int leftGain = Math.max(maxGain(root.left), 0);
            int rightGain = Math.max(maxGain(root.right), 0);

            ans = Math.max(root.val + leftGain + rightGain, ans);

            return root.val + Math.max(leftGain, rightGain);
        }
    }
}
