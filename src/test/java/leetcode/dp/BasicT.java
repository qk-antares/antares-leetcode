package leetcode.dp;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/*
 * DP基础题：
 * 爬楼梯
 * 打家劫舍
 * 最大子数组和
 */
public class BasicT {
    /*
     * 70. 爬楼梯 [Easy]
     */
    public int climbStairs(int n) {
        if (n < 3)
            return n;
        int n1 = 1, n2 = 2;
        for (int i = 3; i <= n; i++) {
            int tmp = n2;
            n2 += n1;
            n1 = tmp;
        }
        return n2;
    }

    /*
     * 746. 使用最小花费爬楼梯
     * 
     * dp[i]=Math.min(cost[i-1]+dp[i-1], cost[i-2]+dp[i-2])
     */
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int i1 = 0, i2 = 0;
        for (int i = 2; i <= n; i++) {
            int tmp = Math.min(cost[i - 1] + i2, cost[i - 2] + i1);
            i1 = i2;
            i2 = tmp;
        }
        return i2;
    }

    /*
     * 377. 组合总和 Ⅳ [Medium]
     * 
     * dp[target] = sum(dp[target-nums[i]])
     * 依赖dp数组左侧的元素，从左往右构造dp
     * 
     * 思考为什么递归写法效率更高？
     * 这可能是由于使用递归的时候，memo中很多地方我们都没有计算过
     * 而正向的dp，一定是从dp[1]到dp[target]都计算过了
     */
    public int combinationSum40(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int num : nums) {
                if (num <= i)
                    dp[i] += dp[i - num];
            }
        }
        return dp[target];
    }

    // dp[target] = sum(dp[target-nums[i]])
    public int combinationSum4(int[] nums, int target) {
        int[] memo = new int[target + 1];
        Arrays.fill(memo, -1);
        return dfs(nums, target, memo);
    }

    int dfs(int[] nums, int target, int[] memo) {
        if (target == 0)
            return 1;
        if (memo[target] != -1)
            return memo[target];

        int ans = 0;
        for (int num : nums) {
            if (num <= target)
                ans += dfs(nums, target - num, memo);
        }

        memo[target] = ans;
        return ans;
    }

    /*
     * 2466. 统计构造好字符串的方案数 [Medium]
     * 
     * 实际上是爬楼梯，计算一段的方案总和
     */
    private static final int MOD = 1_000_000_007;

    public int countGoodStrings(int low, int high, int zero, int one) {
        int[] dp = new int[high + 1];
        dp[0] = 1;
        int res = 0;
        for (int i = 1; i <= high; i++) {
            if (i >= zero) {
                dp[i] = dp[i - zero];
            }
            if (i >= one) {
                dp[i] = (dp[i] + dp[i - one]) % MOD;
            }
            if (i >= low) {
                res = (res + dp[i]) % MOD;
            }
        }
        return res;
    }

    /*
     * 2266. 统计打字方案数 [Medium]
     * 
     * 7和9一种处理，其余的一种处理
     */
    public int countTexts(String pressedKeys) {
        char[] s = pressedKeys.toCharArray();
        long ans = 1L;
        for (int i = 0; i < s.length;) {
            char ch = s[i];
            int l = i;
            while (i < s.length && s[i] == ch)
                i++;
            ans = ans * cnt(ch, i - l) % MOD;
        }
        return (int) ans;
    }

    // ch是字符，n是连续的长度
    public long cnt(char ch, int n) {
        if (ch == '7' || ch == '9') {
            long[] dp = new long[] { 0L, 1L, 2L, 4L, 8L };
            if (n <= 4)
                return dp[n];
            for (int i = 5; i <= n; i++) {
                long tmp = dp[4];
                dp[4] = (dp[4] + dp[1] + dp[2] + dp[3]) % MOD;
                dp[1] = dp[2];
                dp[2] = dp[3];
                dp[3] = tmp;
            }
            return dp[4];
        } else {
            long[] dp = new long[] { 0L, 1L, 2L, 4L };
            if (n <= 3)
                return dp[n];
            for (int i = 4; i <= n; i++) {
                long tmp = dp[3];
                dp[3] = (dp[3] + dp[1] + dp[2]) % MOD;
                dp[1] = dp[2];
                dp[2] = tmp;
            }
            return dp[3];
        }
    }

    @Test
    void test() {
        countGoodStrings(20, 20, 1, 6);
    }
}
