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
     * 二叉搜索树迭代器，我的解法：用一个List保存中序遍历的结果（也是答案的解法），效率还可以
     */
    class BSTIterator {

        Queue<TreeNode> queue;
        public BSTIterator(TreeNode root) {
            queue = new LinkedList<>();
            inOrder(root, queue);
        }

        public void inOrder(TreeNode root, Queue<TreeNode> queue){
            if(root == null) return;

            inOrder(root.left, queue);
            queue.offer(root);
            inOrder(root.right, queue);
        }

        /**
         * 中序遍历的非递归写法（用栈，有点类似于深度优先搜索？）
         */
        public List<Integer> inorderTraversal(TreeNode root) {
            ArrayList<Integer> ans = new ArrayList<>();
            if(root == null) return ans;
            Stack<TreeNode> stack = new Stack<>();

            while (root != null || stack.isEmpty()){
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


        public int next() {
            return queue.poll().val;
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }
    }

    /**
     * 二叉树的右视图（广度优先搜索？通过了！深度优先搜索递归写法效率更高）
     */
    class RightSideView {
        public List<Integer> rightSideView(TreeNode root) {
            ArrayList<Integer> ans = new ArrayList<>();
            if(root == null) return ans;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            ans.add(root.val);

            int size;
            int i;
            //标记该层有没有选择出一个最右边的
            boolean flag;
            while (!queue.isEmpty()){
                //以下是操作同一层的
                flag = false;
                size = queue.size();
                for(i = 0;i < size;i++){
                    TreeNode poll = queue.poll();
                    if(poll.right != null){
                        queue.offer(poll.right);
                        if(!flag){
                            ans.add(poll.right.val);
                            flag = true;
                        }
                    }
                    if(poll.left != null){
                        queue.offer(poll.left);
                        if(!flag){
                            ans.add(poll.left.val);
                            flag = true;
                        }
                    }
                }
            }

            return ans;
        }

        /**
         * DFS写法，BFS需要维护一个flag，这里需要维护一个maxDepth(其实BFS也可以改成维护一个maxDepth)
         */
        private List<Integer> ans = new ArrayList<>();

        public List<Integer> rightSideView0(TreeNode root) {
            visit(root, 0, -1);
            return ans;
        }

        /**
         * 访问某个结点
         * @param root 要访问的结点
         * @param depth 该节点所处的深度
         * @param maxDepth 当前探明的最大深度
         * @return 最大深度
         */
        private int visit(TreeNode root, int depth, int maxDepth){
            if(root == null) return depth - 1;

            //第一次到达这个深度，由于我们优先访问右子树，可以保证第一次到达深度时访问到的就是右视图中的结点
            if(maxDepth < depth)
                ans.add(root.val);

            maxDepth = Math.max(maxDepth, visit(root.right, depth + 1, maxDepth));
            maxDepth = Math.max(maxDepth, visit(root.left, depth + 1, maxDepth));
            return maxDepth;
        }

    }

    /**
     * 完全二叉树的节点个数（我的解法：广度优先搜索，效率太低，答案解法：二分查找+位运算）
     */
    class CountNodes {
        public int countNodes(TreeNode root) {
            if(root == null) return 0;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            //当前在depth层，则总结点数2^depth - 1
            int ans = 0;
            int size;
            int i;
            int levelSize;
            while (!queue.isEmpty()){
                ans = (ans + 1) * 2 - 1;
                size = queue.size();
                levelSize = 0;
                for(i = 0;i < size;i++){
                    TreeNode poll = queue.poll();
                    if(poll.left != null && poll.right != null){
                        queue.offer(poll.left);
                        queue.offer(poll.right);
                        levelSize += 2;
                    } else if (poll.left == null ^ poll.right == null){
                        return ans + levelSize + 1;
                    } else {
                        return ans + levelSize;
                    }
                }
            }
            return -1;
        }

        /**
         * 二分查找+位运算
         * ①一直往左搜索，获取完全二叉树的高度h，则树中节点数为[2^(h-1), 2^h-1]
         * ②在这个节点数区间内进行二分搜索，去看对应的结点存不存在
         * 2^(h-1)是一个h位的数字，第一位是1，后面的位都是0
         * 2^h-1也是一个h位数字，所有的位都是1
         * 可以根据2~h位来决定指针移动方向，用0代表往左，1代表往右，通过位运算可以找到对应路径
         */
        public int countNodes0(TreeNode root) {
            if(root == null) return 0;

            int depth = 0;
            TreeNode cur = root;
            while (cur != null){
                depth++;
                cur = cur.left;
            }

            //2^depth可以用 1 << (depth + 1)表示
            int left = 1 << depth, right = (1 << (depth + 1)) - 1;
            int mid;
            while (left < right){
                mid = left + (right - left) / 2;
                if(exists(root, depth, mid)){
                   left = mid;
                } else {
                    right = mid - 1;
                }
            }

            return left;
        }

        public boolean exists(TreeNode root, int depth, int k){
            TreeNode cur = root;
            int bits = 1 << (depth - 2);
            while (bits > 0) {
                if((bits & k) == 0) cur = cur.left;
                else cur = cur.right;

                bits >>= 1;
            }
            return cur == null;
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
