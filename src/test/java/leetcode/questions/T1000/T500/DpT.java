package leetcode.questions.T1000.T500;

public class DpT {
    /*
     * TODO 416. 分割等和子集 [Medium]
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
}
