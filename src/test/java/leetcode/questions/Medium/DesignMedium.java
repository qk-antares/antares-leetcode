package leetcode.questions.Medium;

import leetcode.common.TreeNode;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;

/**
 * @author Antares
 * @date 2022/9/29
 */
public class DesignMedium {
    /**
     * 二叉树的序列化与反序列化（解法一：层序遍历）
     */
    public class Codec {


        // 序列化时使用深度优先搜索
        public String serialize(TreeNode root) {
            if(root == null)
                return "#";

            StringBuilder ans = new StringBuilder();
            Queue<TreeNode> nodes = new LinkedList<>();
            nodes.offer(root);

            int size;
            while (!nodes.isEmpty()){
                size = nodes.size();
                for(int i = 0;i < size;i++){
                    TreeNode poll = nodes.poll();
                    if(poll == null)
                        ans.append("#,");
                    else {
                        ans.append(poll.val + ",");
                        nodes.offer(poll.left);
                        nodes.offer(poll.right);
                    }
                }
            }
            return ans.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String[] split = data.split(",");
            TreeNode[] treeNodes = new TreeNode[split.length];

            for(int i = 0;i < split.length;i++){
                if(split[i].equals("#")){
                    treeNodes[i] = null;
                }else {
                    treeNodes[i] = new TreeNode(Integer.valueOf(split[i]));
                }
            }

            int i = 0,j = 1;
            while (j < split.length){
                if(treeNodes[i] != null){
                    treeNodes[i].left = treeNodes[j++];
                    treeNodes[i].right = treeNodes[j++];
                }
                i++;
            }

            return treeNodes[0];
        }
    }

    /**
     * 解法二：深度优先遍历
     */
    public class Codec0 {
        public String serialize(TreeNode root) {
            if(root == null)
                return "#";

            return root.val + "," + serialize(root.left) + serialize(root.right);
        }

        public TreeNode deserialize(String data) {
            Queue<String> nodes = new LinkedList<>(Arrays.asList(data.split(",")));
            return helper(nodes);
        }

        public TreeNode helper(Queue<String> nodes){
            String poll = nodes.poll();
            if(poll == "#")
                return null;

            TreeNode node = new TreeNode(Integer.valueOf(poll));

            node.left = helper(nodes);
            node.right = helper(nodes);

            return node;
        }
    }

    /**
     * 常数时间插入、删除和获取随机元素（我的解法：使用数据结构HashSet）
     */
    class RandomizedSet {
        public HashSet<Integer> nums;

        public RandomizedSet() {
            nums = new HashSet<>();
        }

        public boolean insert(int val) {
            return nums.add(val);
        }

        public boolean remove(int val) {
            return nums.remove(val);
        }

        public int getRandom() {
            ArrayList<Integer> integers = new ArrayList<>(nums.size());
            integers.addAll(nums);
            int index = new Random().nextInt(nums.size());
            return integers.get(index);
        }
    }
}
