package leetcode.datastruture.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import leetcode.common.TreeNode;

public class BinaryTreeMedium {

    /**
     * 二叉树展开为链表，用前序遍历+队列接收结果
     */
    class Flatten {
        /**
         * 递归实现（效率和寻找前驱节点相近）
         */
        public void flatten(TreeNode root) {
            if(root == null) return;

            Queue<TreeNode> queue = new LinkedList<>();
            preOrder(root, queue);

            TreeNode head = queue.poll();
            TreeNode cur = head;
            while (!queue.isEmpty()){
                cur.left = null;
                cur.right = queue.poll();
                cur = cur.right;
            }
        }

        public TreeNode preOrder(TreeNode root, Queue<TreeNode> queue){
            queue.add(root);
            if(root.left != null) preOrder(root.left, queue);
            if(root.right != null) preOrder(root.right, queue);
            return root;
        }

        /**
         * 非递归实现（效率最低）
         */
        public void flatten0(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            Queue<TreeNode> queue = new LinkedList<>();

            TreeNode cur = root;
            while (cur != null || !stack.isEmpty()){
                while (cur != null){
                    stack.push(cur);
                    queue.add(cur);
                    cur = cur.left;
                }
                cur = stack.pop().right;
            }

            root = queue.poll();
            cur = root;
            while (!queue.isEmpty()){
                cur.left = null;
                cur.right = queue.poll();
                cur = cur.right;
            }
        }

        /**
         * 最佳解法：寻找前驱节点
         */
        public void flatten1(TreeNode root) {
            TreeNode cur = root;
            TreeNode next, pre;
            while (cur != null){
                if(cur.left != null){
                    next = cur.left;
                    pre = cur.left;
                    while (pre.right != null){
                        pre = pre.right;
                    }
                    pre.right = cur.right;
                    cur.left = null;
                    cur.right = next;
                }
                cur = cur.right;
            }
        }
    }

    /**
     * 求根节点到叶节点数字之和，我的解法，广度优先遍历，另外用一个arrayList存储叶节点的结果（效率比较低），深度优先遍历不用再单独存储叶节点结果
     */
    class SumNumbers {
        public int sumNumbers(TreeNode root) {
            Queue<TreeNode> queue = new LinkedList<>();
            ArrayList<Integer> nums = new ArrayList<>();

            queue.offer(root);
            int size, i;
            while (!queue.isEmpty()){
                size = queue.size();
                for(i = 0;i < size;i++){
                    TreeNode poll = queue.poll();
                    //到了叶子节点
                    if(poll.left == null && poll.right == null){
                        nums.add(poll.val);
                        continue;
                    }
                    if(poll.left != null){
                        poll.left.val += poll.val * 10;
                        queue.offer(poll.left);
                    }
                    if(poll.right != null){
                        poll.right.val += poll.val * 10;
                        queue.offer(poll.right);
                    }
                }
            }

            int ans = 0;
            for (Integer num : nums) {
                ans += num;
            }

            return ans;
        }

        public int sumNumbers0(TreeNode root) {
            int ans = dfs(root, 0);
            return ans;
        }

        public int dfs(TreeNode root, int preSum){
            if(root == null) return 0;

            int sum = preSum * 10 + root.val;

            //到了叶节点
            if(root.left == null && root.right == null){
                return sum;
            }
            return dfs(root.left, sum) + dfs(root.right, sum);
        }
    }

    /**
     * 二叉搜索树的最近公共祖先
     * 就按照二叉搜索树的特性往下搜索，第一个分叉的结点就是最近公共祖先
     */
    class LowestCommonAncestor {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            TreeNode ans = root;
            int max = Math.max(p.val, q.val);
            int min = Math.min(p.val, q.val);

            while (ans != null){
                if(ans.val > max){
                    ans = ans.left;
                } else if (ans.val < min) {
                    ans = ans.right;
                } else {
                    return ans;
                }
            }
            return null;
        }
    }

    /**
     * 验证二叉树的前序序列化（用栈，两个null相碰和前一个元素变成null，效率实在是太低了）
     * 整体的思路是对的，但是分割preorder字符串是个很傻的做法，效率太低了，不如直接操作preorder字符串
     */
    class IsValidSerialization {
        public boolean isValidSerialization(String preorder) {
            List<Character> collect = Arrays.stream(preorder.split(","))
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList());

            int len = collect.size();

            Stack<Character> stack = new Stack<>();
            char temp;
            for(int i = 0;i < len;i++){
                temp = collect.get(i);
                if(temp == '#'){
                    while (!stack.isEmpty() && stack.peek() == '#'){
                        try {
                            stack.pop();
                            stack.pop();
                        } catch (Exception e) {
                            return false;
                        }
                    }
                }
                stack.push(temp);
            }

            return stack.size() == 1 && stack.peek() == '#';
        }

        public boolean isValidSerialization0(String preorder) {
            String[] split = preorder.split(",");
            int len = split.length;

            Stack<String> stack = new Stack<>();
            String temp;
            for(int i = 0;i < len;i++){
                temp = split[i];
                if(temp.equals("#")){
                    while (!stack.isEmpty() && stack.peek().equals("#")){
                        try {
                            stack.pop();
                            stack.pop();
                        } catch (Exception e) {
                            return false;
                        }
                    }
                }
                stack.push(temp);
            }

            return stack.size() == 1 && stack.peek().equals("#");
        }

        /**
         * 效率有很大提升
         */
        public boolean isValidSerialization1(String preorder) {
            Stack<Character> stack = new Stack<>();
            char temp;
            int len = preorder.length();
            for(int i = 0;i < len;i++){
                temp = preorder.charAt(i);
                if(temp == ',') continue;
                if(temp == '#'){
                    while (!stack.isEmpty() && stack.peek() == '#'){
                        try {
                            stack.pop();
                            stack.pop();
                        } catch (Exception e) {
                            return false;
                        }
                    }
                }
                stack.push(temp);
                //至下一个逗号之前都是数字
                while (i+1 < len && preorder.charAt(i+1) != ',') i++;

            }

            return stack.size() == 1 && stack.peek() == '#';
        }

        /**
         * 最简单解法！！！
         * 把整个过程看作是槽位的增减，遇到一个数字，消耗一个槽位（它是一个子结点），同时添加两个操作（有两个子结点），遇到一个#，消耗一个槽位
         */
        public boolean isValidSerialization2(String preorder) {
            int slot = 1;
            int len = preorder.length();
            char temp;
            int i = 0;
            while (i < len){
                if(slot == 0) return false;
                temp = preorder.charAt(i);
                if(temp == ',') {
                    i++;
                    continue;
                }
                if(temp == '#'){
                    slot--;
                    i++;
                } else {
                    slot++;
                    while (i < len && preorder.charAt(i) != ',') i++;
                }
            }
            return slot == 0;
        }

    }

    /**
     * 打家劫舍 III
     * 如果使用递归 + 动态规划
     * f(node) = node.val + g(node.left) + g(node.right)
     * g(node) = max(f(node.left),g(node.left)) + max(f(node.right),g(node.right))
     * ans = max{g(node), f(node)}
     */
    class Rob {
        Map<TreeNode, Integer> f = new HashMap<TreeNode, Integer>();
        Map<TreeNode, Integer> g = new HashMap<TreeNode, Integer>();

        public int rob(TreeNode root) {
            dfs(root);
            return Math.max(f.get(root), g.get(root));
        }

        public void dfs(TreeNode root){
            if(root == null) return;
            dfs(root.left);
            dfs(root.right);
            f.put(root, root.val + g.getOrDefault(root.left, 0) + g.getOrDefault(root.right, 0));
            g.put(root, Math.max(f.getOrDefault(root.left, 0), g.getOrDefault(root.left, 0)) + Math.max(f.getOrDefault(root.right, 0), g.getOrDefault(root.right, 0)));
        }

        /**
         * 优化版
         */
        public int rob0(TreeNode root) {
            int[] ans = dfs0(root);
            return Math.max(ans[0], ans[1]);
        }

        public int[] dfs0(TreeNode root){
            if(root == null) {
                return new int[]{0,0};
            }
            int[] left = dfs0(root.left);
            int[] right = dfs0(root.right);
            int selected = root.val + left[1] + right[1];
            int notSelected = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
            return new int[]{selected, notSelected};
        }
    }

    /**
     * 扁平化嵌套列表迭代器，我的解法：递归，类似于解套娃（实质是递归的深度优先遍历），时间复杂度稍高
     */
    public interface NestedInteger {
        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    public class NestedIterator implements Iterator<Integer> {
        Deque<Integer> ans;

        public NestedIterator(List<NestedInteger> nestedList) {
            ans = new ArrayDeque<>();

            for (NestedInteger nestedInteger : nestedList) {
                ans.addAll(getIntegerList(nestedInteger));
            }
        }

        public Deque<Integer> getIntegerList(NestedInteger nestedInteger){
            Deque<Integer> ans = new ArrayDeque<>();

            if(nestedInteger.isInteger()) {
                ans.offer(nestedInteger.getInteger());
                return ans;
            }

            List<NestedInteger> list = nestedInteger.getList();
            for (NestedInteger integer : list) {
                ans.addAll(getIntegerList(integer));
            }
            return ans;
        }

        @Override
        public Integer next() {
            return ans.poll();
        }

        @Override
        public boolean hasNext() {
            return !ans.isEmpty();
        }
    }

    /**
     * 改进方法：不再使用队列，而是使用数组+iterator
     */
    public class NestedIterator0 implements Iterator<Integer> {
        List<Integer> ans;
        Iterator<Integer> iterator;

        public NestedIterator0(List<NestedInteger> nestedList) {
            ans = new ArrayList<>();
            ans.addAll(getIntegerList(nestedList));
            iterator = ans.iterator();
        }

        public List<Integer> getIntegerList(List<NestedInteger> nestedList){
            for (NestedInteger nestedInteger : nestedList) {
                if(nestedInteger.isInteger()) {
                    ans.add(nestedInteger.getInteger());
                } else {
                    getIntegerList(nestedInteger.getList());
                }
            }
            return ans;
        }

        @Override
        public Integer next() {
            return iterator.next();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }
    }

    /**
     * 上述的方法虽然简单，但是不太符合迭代器的定义，不应该直接存储迭代的结果，而是提供访问途径
     */

    /**
     * 左叶子之和，我的解法：使用递归
     */
    class SumOfLeftLeaves {
        public int sumOfLeftLeaves(TreeNode root) {
            return helper(root.left, true) + helper(root.right, false);
        }

        public int helper(TreeNode root, boolean flag){
            if(root == null) return 0;
            if(flag && root.left == null && root.right == null) return root.val;
            return helper(root.left, true) + helper(root.right, false);
        }
    }

    @Test
    void invoke(){
        new IsValidSerialization().isValidSerialization2(new String("9,#,92,#,#"));
    }
}
