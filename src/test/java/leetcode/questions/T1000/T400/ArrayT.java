package leetcode.questions.T1000.T400;

public class ArrayT {
    /*
     * TODO 307. 区域和检索 - 数组可修改 [Medium]   [Link: 2179. 统计数组中好三元组数目]
     * 
     * 树状数组
     */
    class NumArray {
        int[] nums;
        int[] tree;

        public NumArray(int[] nums) {
            int n = nums.length;
            this.nums = new int[n];
            this.tree = new int[n + 1];
            for (int i = 0; i < n; i++) {
                this.update(i, nums[i]);
            }
        }

        public void update(int index, int val) {
            int delta = val - nums[index];
            this.nums[index] = val;
            // 更新受影响的树状数组元素
            for (int i = index + 1; i < this.tree.length; i += i & -i) {
                this.tree[i] += delta;
            }
        }

        public int sumRange(int i) {
            int s = 0;
            for (; i > 0; i -= i & -i) {
                s += tree[i];
            }
            return s;
        }

        public int sumRange(int left, int right) {
            return sumRange(right + 1) - sumRange(left);
        }
    }
}
