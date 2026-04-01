package leetcode.greedy.interval;

import java.util.Arrays;

/**
 * 区间覆盖
 */
public class IntervalCoverT {
    /**
     * 45. 跳跃游戏 II
     * 
     * dp[i]表示到达i的最少跳数
     * dp[i+d] = Math.min(dp[i+d], 1+dp[i])
     * 
     * maxCur记录当前step能跳的最远距离
     * maxNext是下一step能跳的最远距离
     * 当i==maxCur，step++
     * 需特别小心，遍历到n-1就可以了
     */

    public int jump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int d = 1; d <= nums[i] && i + d < n; d++) {
                dp[i + d] = Math.min(dp[i + d], 1 + dp[i]);
            }
        }
        return dp[n - 1];
    }

    public int jump0(int[] nums) {
        int step = 0;
        int maxCur = 0;
        int maxNext = 0;
        for(int i = 0; i < nums.length-1; i++) {
            maxNext = Math.max(maxNext, i+nums[i]);
            if(i == maxCur) {
                step++;
                maxCur = maxNext;
            }
        }
        return step;
    }
}
