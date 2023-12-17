package leetcode.datastruture.tree;

import java.util.ArrayList;
import java.util.List;

import leetcode.common.TreeNode;

public class BinaryTreeEasy {
    /**
     * 翻转二叉树，我的解法，递归
     */
    class Solution {
        public TreeNode invertTree(TreeNode root) {
            invertHelper(root);
            return root;
        }

        public void invertHelper(TreeNode root) {
            if(root == null) return;

            TreeNode left = root.left;
            root.left = root.right;
            root.right = left;
            invertHelper(root.left);
            invertHelper(root.right);
        }
    }

    /**
     * 二叉树的所有路径（深度优先搜索，递归写法）
     */
    class BinaryTreePaths {
        public List<String> binaryTreePaths(TreeNode root) {
            ArrayList<String> ans = new ArrayList<>();
            //叶子结点
            if(root.left == null && root.right == null){
                ans.add(String.valueOf(root.val));
                return ans;
            }
            if(root.left != null) ans.addAll(binaryTreePaths(root.left));
            if(root.right != null) ans.addAll(binaryTreePaths(root.right));
            int size = ans.size();
            for(int i = 0;i < size;i++){
                ans.set(i, root.val + "->" + ans.get(i));
            }
            return ans;
        }

        /**
         * 不能上来就创建一个StringBuilder对象，那样的话整个过程中使用的都是同一个对象，因此应该传String，然后再根据String创建对象
         * 也不能只使用String，那样效率比传String创建StringBuilder低得多？为什么！！
         */
        public List<String> binaryTreePaths0(TreeNode root) {
            List<String> ans = new ArrayList<>();
            dfs(root, "", ans);
            return ans;
        }

        public void dfs(TreeNode root, String curPath, List<String> ans){
            if(root != null){
                StringBuffer pathSB = new StringBuffer(curPath);
                pathSB.append(root.val);
                //必须是叶子节点
                if(root.left == null && root.right == null){
                    ans.add(pathSB.toString());
                } else {
                    pathSB.append("->");
                    dfs(root.left, pathSB.toString(), ans);
                    dfs(root.right, pathSB.toString(), ans);
                }
            }
        }
    }



}
