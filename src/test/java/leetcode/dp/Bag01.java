package leetcode.dp;

import java.util.Arrays;

public class Bag01 {
    /*
     * 416. 分割等和子集 [Medium]
     * 
     * 使用dp[i][j]表示从nums[...i]中选取一些数，能否使其和恰好为j
     * 本问题等价于从nums中选取一些数，使其和恰好为sum/2(target)
     * 首先处理一些边界情况，当数组长度为1，或者总和为1时，无法划分，直接返回false
     * 对于边界情况：dp[i][0]，全部为true，dp[0][j]为true，当且仅当nums[0] == j
     * 当nums[i] > j时：dp[i][j] = dp[i-1][j]
     * 当nums[i] <= j时：dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]]
     * dp中的状态依赖于上一行，所以按行构造dp
     */
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        if (n < 2)
            return false;

        int sum = 0, max = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            max = Math.max(nums[i], max);
        }
        if (sum % 2 != 0)
            return false;

        int target = sum / 2;
        if (max > target)
            return false;

        boolean[][] dp = new boolean[n][target + 1];
        // 首先初始化边界情况
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }
        dp[0][nums[0]] = true;

        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= target; j++) {
                if (nums[i] > j)
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
            }
        }

        return dp[n - 1][target];
    }

    /*
     * 494. 目标和 [Medium] <Star>
     * 
     * dfs(i,cur,nums,target)
     * i是当前填的位
     * cur是当前的和
     * 返回==target的方案数
     * 
     * nums的所有和为S
     * 添加正号元素之和为p，负号元素之和为q
     * p+q=S，p-q=target
     * p=(S+target)/2，q=(S-target)/2
     * 
     * 问题转换成从nums选取一些正数，和为(S-|target|)/2
     * dfs(i,tot) 表示从nums[0..i]中选取一些数，和为tot的方案数
     */
    public int findTargetSumWays(int[] nums, int target) {
        int S = 0;
        for (int num : nums)
            S += num;
        int tot = S - Math.abs(target);
        if (tot < 0 || tot % 2 != 0)
            return 0;

        tot /= 2;
        int n = nums.length;
        int[][] memo = new int[n][tot + 1];
        for (int[] row : memo)
            Arrays.fill(row, -1);
        return dfs(n - 1, tot, nums, memo);
    }

    int dfs(int i, int tot, int[] nums, int[][] memo) {
        if (i < 0) {
            return tot == 0 ? 1 : 0;
        }

        if (memo[i][tot] != -1)
            return memo[i][tot];

        int ans = 0;
        // 该条件下才能选择nums[i]
        if (tot >= nums[i])
            ans += dfs(i - 1, tot - nums[i], nums, memo);
        ans += dfs(i - 1, tot, nums, memo);

        memo[i][tot] = ans;
        return ans;
    }
}
