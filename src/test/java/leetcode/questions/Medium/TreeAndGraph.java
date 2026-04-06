package leetcode.questions.Medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import leetcode.common.TreeNode;

/**
 * @author Antares
 * @date 2022/9/22
 */
public class TreeAndGraph {
    /**
     * 对于树的一些总结：
     * 1. 中序遍历：栈
     * 2. 深度优先遍历：栈
     * 3. 广度优先遍历：队列
     */


    /**
     * 二叉树的中序遍历（我的解法：递归，内存消耗较大）
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();

        if(root == null)
            return ans;

        ans.addAll(inorderTraversal(root.left));
        ans.add(root.val);
        ans.addAll(inorderTraversal(root.right));

        return ans;
    }

    /**
     * 答案解法（使用栈）
     */
    public List<Integer> inorderTraversal0(TreeNode root) {
        if(root == null)
            return new ArrayList<>();

        ArrayList<Integer> ans = new ArrayList<>();
        Stack<TreeNode> treeNodes = new Stack<>();

        //先一直网左子树的方向
        while (root != null || !treeNodes.isEmpty()){
               while (root != null){
                   treeNodes.push(root);
                   root = root.left;
               }

               //转到右子树
               if(!treeNodes.isEmpty()){
                   TreeNode node = treeNodes.pop();
                   ans.add(node.val);
                   root = node.right;
               }
        }

        return ans;
    }
}
