package leetcode.questions.T1000.T200.medium;

import org.junit.jupiter.api.Test;

public class DpT {
    /*
     * 198. 打家劫舍
     */
    public int rob(int[] nums) {
        int len = nums.length;
        if(len < 2) return nums[0];
        int[] dp = new int[len];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for(int i = 2;i < len;i++) {
            dp[i] = Math.max(dp[i-1], nums[i]+dp[i-2]);
        }

        return dp[len-1];
    }

    @Test
    public void test() {
        System.out.println(rob(new int[]{1,2,3,1}));
    }


}
