package leetcode.hots100;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DpLearn {
    /**
     * 139. 单词拆分
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<String>();
        for (String word : wordDict) {
            set.add(word);
        }

        int len = s.length();
        boolean[] dp = new boolean[len+1];
        dp[0] = true;
        for (int i = 1; i <= len; i++) {
            for (int j = 0; j < i; j++) {
                if(dp[j] && set.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[len];
    }

    /**
     * 152. 乘积最大子数组
     * max+
     * max-
     */
    public int maxProduct(int[] nums) {
        int ans = nums[0];
        int maxP = nums[0], maxN = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int tmp = maxP;
            maxP = Math.max(Math.max(nums[i], maxP * nums[i]), maxN * nums[i]);
            maxN = Math.min(Math.min(nums[i], tmp * nums[i]), maxN * nums[i]);

            if(maxP > ans) {
                ans = maxP;
            }
        }
        return ans;
    }

    /**
     * 416. 分割等和子集
     * dp[i][j]代表[0...i]号元素是否能达到j
     */
    public boolean canPartition(int[] nums) {
        if(nums.length < 2) {
            return false;
        }

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if(sum % 2 == 1) {
            return false;
        }

        int target = sum / 2;
        int len = nums.length;
        boolean[][] dp = new boolean[len][target+1];

        //初始化
        for (int i = 0; i < len; i++) {
            dp[i][0] = true;
        }
        if(nums[0] <= target) {
            dp[0][nums[0]] = true;
        }

        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= target; j++) {
                if(j-nums[i] >= 0) {
                    dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[len-1][target];

    }

    @Test
    public void test(){
        // maxProduct(new int[]{-4,-3,-2});
        canPartition(new int[]{9,5});
    }

}
