package leetcode.datastruture.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import leetcode.common.TreeNode;

/**
 * @author Antares
 * @date 2022/9/13
 */
public class BinaryTree {
    /**
     * 二叉树的前序遍历(我的解法：递归)
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        preorderTraversalHelper(root, res);
        return res;
    }

    public void preorderTraversalHelper(TreeNode root, List<Integer> res){
        if(root == null)
            return;
        res.add(root.val);
        preorderTraversalHelper(root.left, res);
        preorderTraversalHelper(root.right, res);
    }

    /**
     * 解法二（非递归，使用栈，效率更高）
     */
    public List<Integer> preorderTraversal0(TreeNode root) {
        LinkedList<Integer> ans = new LinkedList<>();

        if(root == null)
            return ans;

        Stack<TreeNode> treeNodes = new Stack<>();
        treeNodes.push(root);
        while (!treeNodes.isEmpty()){
            TreeNode node = treeNodes.pop();
            ans.add(node.val);
            if(node.right != null)
                treeNodes.push(node.right);
            if(node.left != null)
                treeNodes.push(node.left);
        }

        return ans;
    }

    /**
     * 解法三（Morris前序遍历，有时间了了解下）
     * https://mp.weixin.qq.com/s?__biz=MzU0ODMyNDk0Mw==&mid=2247489528&idx=1&sn=c339bb1b7e1fef4a186aa9d8563e3856&chksm=fb4184d8cc360dce5e2303e6796a6964952240f5ae8eb6217b8a37d3475a5736b1b011cd58ae&token=1745824839&lang=zh_CN#rd
     */

    /**
     * 二叉树的中序遍历（我的解法：递归）
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        LinkedList<Integer> ans = new LinkedList<>();
        inorder(root, ans);
        return ans;
    }

    public void inorder(TreeNode root, List<Integer> ans){
        if(root == null)
            return;
        inorder(root.left, ans);
        ans.add(root.val);
        inorder(root.right, ans);
    }

    /**
     * 解法二：使用栈，注意while循环还是有较大的差异
     */
    public List<Integer> inorderTraversal0(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        //加个边界条件判断
        if (root == null)
            return res;

        Stack<TreeNode> treeNodes = new Stack<>();
        while (root != null || !treeNodes.isEmpty()){
            while (root != null){
                treeNodes.push(root);
                root = root.left;
            }

            if(!treeNodes.isEmpty()){
                root = treeNodes.pop();
                res.add(root.val);
                root = root.right;
            }
        }

        return res;
    }

    /**
     * 二叉树的后序遍历（我的解法：递归）
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> ans = new LinkedList<>();
        postorder(root, ans);
        return ans;
    }

    public void postorder(TreeNode root, List<Integer> ans){
        if(root == null)
            return;

        postorder(root.left, ans);
        postorder(root.right, ans);
        ans.add(root.val);
    }

    /**
     * 解法二（使用栈，后序遍历是前序遍历的逆序）
     */
    public List<Integer> postorderTraversal0(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if(root == null)
            return ans;

        Stack<TreeNode> treeNodes = new Stack<>();
        treeNodes.push(root);

        while (!treeNodes.isEmpty()){
            TreeNode pop = treeNodes.pop();
            ans.add(pop.val);

            if(pop.left != null)
                treeNodes.push(pop.left);
            if(pop.right != null)
                treeNodes.push(pop.right);
        }

        Collections.reverse(ans);

        return ans;
    }

    /**
     * 二叉树的层序遍历(我的解法，使用双队列，解决了，但是效率有点低)
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();

        if(root == null)
            return ans;

        Queue<TreeNode> lastLevel = new LinkedList<>();
        Queue<TreeNode> newLevel = new LinkedList<>();
        lastLevel.offer(root);

        while (true){
            ArrayList<Integer> levelAns = new ArrayList<>();
            while (!lastLevel.isEmpty()){
                TreeNode pop = lastLevel.poll();
                levelAns.add(pop.val);
                if(pop.left != null)
                    newLevel.offer(pop.left);
                if(pop.right != null)
                    newLevel.offer(pop.right);
            }

            ans.add(levelAns);

            if(newLevel.isEmpty())
                break;

            lastLevel = newLevel;
            newLevel = new LinkedList<>();
        }

        return ans;
    }

    /**
     * 解法二（广度优先遍历，使用的是队列不是栈！精髓之处在于while循环中判定当前行的结点个数）
     */
    public List<List<Integer>> levelOrder0(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();

        if(root == null)
            return ans;

        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.offer(root);
        int size,i;
        while (!nodes.isEmpty()){
            size = nodes.size();
            ArrayList<Integer> levelAns = new ArrayList<>();
            for(i = 0;i < size;i++){
                TreeNode poll = nodes.poll();
                levelAns.add(poll.val);
                if(poll.left != null)
                    nodes.offer(poll.left);
                if(poll.right != null)
                    nodes.offer(poll.right);
            }
            ans.add(levelAns);
        }

        return ans;
    }

    /**
     * 解法三（深度优先遍历，效率是最高的）
     */
    public List<List<Integer>> levelOrder1(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        levelOrderHelper(ans, root, 0);
        return ans;
    }

    public void levelOrderHelper(List<List<Integer>> ans, TreeNode root, int level){
        if(root == null)
            return;

        //说明到下一层了
        if(level >= ans.size()){
            ans.add(new ArrayList<Integer>());
        }

        ans.get(level).add(root.val);

        levelOrderHelper(ans, root.left, level+1);
        levelOrderHelper(ans, root.right, level+1);
    }


    /**
     * 二叉树的最大深度（我的解法：递归，效率挺高）
     */
    public int maxDepth(TreeNode root) {
        if(root == null)
            return 0;
        if(root.left == null && root.right == null)
            return 1;

        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * 对称二叉树（我的解法：递归，内存消耗有点大）
     */
    public boolean isSymmetric(TreeNode root) {
        if(root == null)
            return true;
        return isSymmetricHelper(root.left, root.right);
    }

    public boolean isSymmetricHelper(TreeNode left, TreeNode right){
        if(left == null && right == null)
            return true;
        if(left == null || right == null || left.val != right.val)
            return false;

        return isSymmetricHelper(left.left, right.right) && isSymmetricHelper(left.right, right.left);
    }


    /**
     * 二叉树的最近公共祖先(解不出来，答案解法：遍历树的同时记录每个结点的父节点)
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        HashMap<TreeNode, TreeNode> parents = new HashMap<>();
        //深度优先遍历
        Stack<TreeNode> nodes = new Stack<>();
        nodes.push(root);
        while (!nodes.isEmpty()){
            TreeNode pop = nodes.pop();

            if(pop.right != null){
                nodes.push(pop.right);
                parents.put(pop.right, pop);
            }
            if(pop.left != null){
                nodes.push(pop.left);
                parents.put(pop.left, pop);
            }
        }

        //寻找p到root的路径
        HashSet<TreeNode> path = new HashSet<>();
        while (p != null){
            path.add(p);
            p = parents.get(p);
        }

        //寻找q的到root的路径，知道和上面的路径重合
        while (q != null){
            if(path.contains(q))
                return q;
            q = parents.get(q);
        }

        return null;
    }

    /**
     * 解法二：递归解法（和我最开始的想法一样）
     */
    public TreeNode lowestCommonAncestor0(TreeNode cur, TreeNode p, TreeNode q) {
        if (cur == null || cur == p || cur == q)
            return cur;
        TreeNode left = lowestCommonAncestor(cur.left, p, q);
        TreeNode right = lowestCommonAncestor(cur.right, p, q);
        //如果left为空，说明这两个节点在cur结点的右子树上，我们只需要返回右子树查找的结果即可
        if (left == null)
            return right;
        //同上
        if (right == null)
            return left;
        //如果left和right都不为空，说明这两个节点一个在cur的左子树上一个在cur的右子树上，
        //我们只需要返回cur结点即可。
        return cur;
    }


    /**
     * 二叉树的序列化与反序列化（答案解法：BFS，用#代表空，用，分隔，存储所有的null结点）
     */
    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null)
                return "#";

            StringBuilder ans = new StringBuilder();
            Queue<TreeNode> nodes = new LinkedList<>();
            nodes.offer(root);
            while (!nodes.isEmpty()){
                TreeNode poll = nodes.poll();

                if(poll == null)
                    ans.append("#,");
                else {
                    ans.append(poll.val).append(",");
                    nodes.offer(poll.left);
                    nodes.offer(poll.right);
                }
            }

            return ans.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if(data.equals("#"))
                return null;

            String[] values = data.split(",");
            TreeNode[] nodes = new TreeNode[values.length];
            for(int i = 0;i < nodes.length;i++){
                if(values[i].equals("#"))
                    nodes[i] = null;
                else
                    nodes[i] = new TreeNode(Integer.valueOf(values[i]));
            }

            int i = 0,j = 1;
            while (i < nodes.length){
                if(nodes[i] != null){
                    nodes[i].left = nodes[j++];
                    nodes[i].right = nodes[j++];
                }
                i++;
            }

            return nodes[0];
        }
    }

    /**
     * 解法二(此法甚妙，大量用到了递归，但是效率并不是很高)：DFS从根节点开始，一直往左子节点走，当到达叶子节点的时候会返回到父节点，然后从从父节点的右子节点继续遍历
     */
    public class Codec0 {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null)
                return "#";
            return root.val + "," + serialize(root.left) + "," + serialize(root.right);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            //首先将值存到一个队列中（DFS还原反而用到了队列）
            Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
            return helper(queue);
        }

        public TreeNode helper(Queue<String> queue){
            String poll = queue.poll();
            if(poll.equals("#"))
                return null;

            TreeNode treeNode = new TreeNode(Integer.valueOf(poll));

            treeNode.left = helper(queue);
            treeNode.right = helper(queue);

            return treeNode;
        }
    }


}
