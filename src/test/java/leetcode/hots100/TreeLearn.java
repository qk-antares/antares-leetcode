package leetcode.hots100;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import leetcode.common.TreeNode;

public class TreeLearn {
    /**
     * 二叉树的中序遍历
     */
    public List<Integer> inorderTraversal0(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();

        if(root == null){
            return ans;
        }

        ans.addAll(inorderTraversal0(root.left));
        ans.add(root.val);
        ans.addAll(inorderTraversal0(root.right));
        return ans;
    }
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();

        while (root != null || !stack.isEmpty()){
            while (root != null){
                stack.push(root);
                root = root.left;
            }

            TreeNode pop = stack.pop();
            ans.add(pop.val);
            root = pop.right;
        }

        return ans;
    }

    /**
     * 二叉树的最大深度
     */
    public int maxDepth0(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth0(root.left), maxDepth0(root.right));
    }
    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        int ans = 0;
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            ans++;
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode poll = queue.poll();
                if(poll.left != null){
                    queue.offer(poll.left);
                }
                if(poll.right != null){
                    queue.offer(poll.right);
                }
            }
        }
        return ans;
    }

    /**
     * 翻转二叉树
     */
    public TreeNode invertTree(TreeNode root) {
        if(root == null){
            return null;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = invertTree(right);
        root.right = invertTree(left);

        return root;
    }

    /**
     * 对称二叉树
     */
    public boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        return isSymmetric(root.left, root.right);
    }
    public boolean isSymmetric(TreeNode left, TreeNode right) {
        if(left == null && right == null){
            return true;
        }
        if(left == null || right == null || left.val != right.val){
            return false;
        }
        return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
    }

    /**
     * 二叉树的直径
     */
    public int ans = Integer.MIN_VALUE;
    public int diameterOfBinaryTree(TreeNode root) {
        travelNodeCount(root);
        return ans-1;
    }
    public int travelNodeCount(TreeNode root){
        if(root == null){
            return 0;
        }
        int L = travelNodeCount(root.left);
        int R = travelNodeCount(root.right);
        ans = Math.max(ans, L+R+1);
        return 1 + Math.max(L, R);
    }

    /**
     * 将有序数组转换为二叉搜索树
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums, 0, nums.length-1);
    }
    public TreeNode sortedArrayToBST(int[] nums, int start, int end) {
        if(start > end){
            return null;
        }

        int mid = (start + end) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, start, mid-1);
        root.right = sortedArrayToBST(nums, mid+1, end);
        return root;
    }

    /**
     * 验证二叉搜索树
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode root, long min, long max) {
        if(root == null){
            return true;
        }
        if(root.val <= min || root.val >= max){
            return false;
        }
        return isValidBST(root.left, min ,root.val) &&
                isValidBST(root.right, root.val, max);
    }

    /**
     * 二叉搜索树中第K小的元素
     */
    public int kthSmallest(TreeNode root, int k) {
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()){
            while (root != null){
                stack.push(root);
                root = root.left;
            }
            TreeNode pop = stack.pop();
            k--;
            if(k == 0){
                return pop.val;
            }
            root = pop.right;
        }
        return -1;
    }

    /**
     * 二叉树的右视图
     */
    public List<Integer> rightSideView0(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }

        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            TreeNode poll = queue.poll();
            ans.add(poll.val);
            if(poll.right != null){
                queue.offer(poll.right);
            }
            if(poll.left != null){
                queue.offer(poll.left);
            }
            for (int i = 1; i < levelSize; i++) {
                poll = queue.poll();
                if(poll.right != null){
                    queue.offer(poll.right);
                }
                if(poll.left != null){
                    queue.offer(poll.left);
                }
            }
        }
        return ans;
    }

}
