package leetcode.questions.Easy;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Antares
 * @date 2022/9/6
 */
public class DesignEasy {
    /**
     * 打乱数组（我的解法）
     */
    class Solution {
        private int[] nums;

        public Solution(int[] nums) {
            this.nums = Arrays.copyOf(nums, nums.length);
        }

        public int[] reset() {
            return nums;
        }

        public int[] shuffle() {
            int[] res = Arrays.copyOf(this.nums, this.nums.length);
            int j, temp;
            for (int i = 0; i < nums.length; i++) {
                j = new Random().nextInt(this.nums.length);
                temp = res[i];
                res[i] = res[j];
                res[j] = temp;
            }
            return res;
        }
    }
}
