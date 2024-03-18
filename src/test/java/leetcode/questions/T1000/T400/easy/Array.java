package leetcode.questions.T1000.T400.easy;

public class Array {
    class NumArray {
        int[] nums;
        int[] sum;

        public NumArray(int[] nums) {
            int len = nums.length;
            this.nums = nums;
            this.sum = new int[len+1];
            sum[0] = 0;
            for (int i = 1; i <= len; i++) {
                sum[i] = sum[i-1] + nums[i-1];
            }
        }
        
        public int sumRange(int left, int right) {
            return sum[right+1] - sum[left];
        }
    }
}
