package leetcode.questions.T1000.T800;

import leetcode.common.TreeNode;

public class TreeT {
    /**
     * 783. 二叉搜索树节点最小距离  [Easy]
     */
    int pre;
    int ans = Integer.MAX_VALUE;
    public int minDiffInBST(TreeNode root) {
        pre = -1;
        preOrder(root);
        return ans;
    }

    public void preOrder(TreeNode root){
        if(root == null) {
            return;
        }
        preOrder(root.left);
        
        if(pre == -1) {
            pre = root.val;
        } else {
            ans = Math.min(ans, root.val-pre);
            pre = root.val;
        }

        preOrder(root.right);
    }
}
