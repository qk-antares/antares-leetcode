package leetcode.dp;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

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

    /*
     * 2915. 和为目标值的最长子序列的长度 [Medium]
     * 
     * dp[i][j]表示从nums的前i个元素，选择一些，和恰好为j，的子序列长度最大值
     * j<nums[i] dp[i+1][j] = dp[i][j]
     * dp[i+1][j] = Math.max(dp[i][j], 1+dp[i][j-nums[i]])
     */
    public int lengthOfLongestSubsequence0(List<Integer> nums, int target) {
        Integer[] a = nums.toArray(Integer[]::new); // 转成数组处理，更快
        int n = a.length;
        int[][] memo = new int[n][target + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示没有计算过
        }

        int ans = dfs(n - 1, target, a, memo);
        return ans > 0 ? ans : -1;
    }

    public int lengthOfLongestSubsequence1(List<Integer> nums, int target) {
        int n = nums.size();
        int[][] dp = new int[n + 1][target + 1];
        Arrays.fill(dp[0], Integer.MIN_VALUE);
        dp[0][0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= target; j++) {
                if (j < nums.get(i))
                    dp[i + 1][j] = dp[i][j];
                else
                    dp[i + 1][j] = Math.max(dp[i][j], 1 + dp[i][j - nums.get(i)]);
            }
        }
        return dp[n][target] > 0 ? dp[n][target] : -1;
    }

    private int dfs(int i, int j, Integer[] nums, int[][] memo) {
        if (i < 0) {
            return j == 0 ? 0 : Integer.MIN_VALUE;
        }
        if (memo[i][j] != -1) { // 之前计算过
            return memo[i][j];
        }

        int res = dfs(i - 1, j, nums, memo); // 不选 nums[i]
        if (j >= nums[i]) {
            res = Math.max(res, dfs(i - 1, j - nums[i], nums, memo) + 1); // 选 nums[i]
        }
        return memo[i][j] = res; // 记忆化
    }

    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = 0;
        for (int num : nums) {
            for (int j = target; j >= num; j--) {
                dp[j] = Math.max(dp[j], 1 + dp[j - num]);
            }
        }
        return dp[target] > 0 ? dp[target] : -1;
    }

    /*
     * 2787. 将一个数字表示成幂的和的方案数 [Medium]    <Star>
     * 
     * 设 upper = (int) Math.round(Math.pow(n, 1.0 / x))
     * 本题相当于从[1..upper]中选取一些数，这些数的x次方和等于n
     * dfs(i,target)表示从[1..i]中选取一些数，这些数的x次方和等于target的方案数
     * 
     * dp[i][j]表示从前i个正整数中选取一些数，这些数的x次方和等于target的方案数
     */

    public int numberOfWays0(int n, int x) {
        int upper = (int) Math.round(Math.pow(n, 1.0 / x));
        int[][] memo = new int[upper + 1][n + 1];
        for (int[] row : memo)
            Arrays.fill(row, -1);
        return dfs(upper, n, memo, x);
    }

    int dfs(int i, int target, int[][] memo, int x) {
        if (i < 1) {
            return target == 0 ? 1 : 0;
        }

        if (memo[i][target] != -1)
            return memo[i][target];

        int ans = 0;
        // 该条件下才能选i
        int tmp = (int) Math.pow(i, x);
        if (tmp <= target) {
            ans = (ans + dfs(i - 1, target - tmp, memo, x)) % 1_000_000_007;
        }
        // 不选
        ans = (ans + dfs(i - 1, target, memo, x)) % 1_000_000_007;

        memo[i][target] = ans;
        return ans;
    }

    public int numberOfWays(int n, int x) {
        int upper = (int) Math.round(Math.pow(n, 1.0 / x));
        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 1; i <= upper; i++) {
            int tmp = (int) Math.pow(i, x);
            for (int j = n; j >= tmp; j--) {
                dp[j] = (dp[j] + dp[j - tmp]) % 1_000_000_007;
            }
        }

        return dp[n];
    }

    /*
     * 3180. 执行操作可获得的最大总奖励 I [Medium]
     * 
     * 假设rewardValues中的最大值为m，则最大总奖励的上界是2m-1
     * dp[i][j]表示能否从rewardValues的前i个选取一些值，恰好得到j
     * 需要题目中并没有说从前往后选择，为了将题目重构成一个从前往后选择的场景，可以对rewardValues进行排序
     */
    public int maxTotalReward0(int[] rewardValues) {
        Arrays.sort(rewardValues);

        int n = rewardValues.length;
        int m = Integer.MIN_VALUE;
        for (int v : rewardValues)
            m = Math.max(m, v);

        boolean[][] dp = new boolean[n + 1][2 * m];
        dp[0][0] = true;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2 * m; j++) {
                if (j < rewardValues[i] || j >= 2 * rewardValues[i])
                    dp[i + 1][j] = dp[i][j];
                else
                    dp[i + 1][j] = dp[i][j] || dp[i][j - rewardValues[i]];
            }
        }

        for (int j = 2 * m - 1; j >= 0; j--) {
            if (dp[n][j])
                return j;
        }

        return 0;
    }

    public int maxTotalReward(int[] rewardValues) {
        Arrays.sort(rewardValues);

        int n = rewardValues.length;
        int m = Integer.MIN_VALUE;
        for (int v : rewardValues)
            m = Math.max(m, v);

        boolean[] dp = new boolean[2 * m];
        dp[0] = true;

        for (int i = 0; i < n; i++) {
            for (int j = 2 * rewardValues[i] - 1; j >= rewardValues[i]; j--) {
                dp[j] = dp[j] || dp[j - rewardValues[i]];
            }
        }

        for (int j = 2 * m - 1; j >= 0; j--) {
            if (dp[j])
                return j;
        }

        return 0;
    }

    /*
     * 474. 一和零  [Medium]
     * 
     * 相当于背包里面的dp多了一个维度
     * 用dp[i][j][k]表示从strs的前i个选取一些元素，这些元素最多有j个0和k个1，最大子集的长度
     * 当strs[i].0 > j || strs[i].1 > k: dp[i+1][j][k] = dp[i][j][k]
     * 否则：dp[i+1][j][k] = Math.max(dp[i][j][k], 1+dp[i][j-strs[i].0][k-strs[i].1])
     */
    public int findMaxForm0(String[] strs, int m, int n) {
        int len = strs.length;
        int[][][] dp = new int[len + 1][m + 1][n + 1];

        int[][] cnt01 = new int[len][2];
        for (int i = 0; i < len; i++) {
            for (char ch : strs[i].toCharArray()) {
                cnt01[i][ch - '0']++;
            }
        }

        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    if (cnt01[i][0] > j || cnt01[i][1] > k)
                        dp[i + 1][j][k] = dp[i][j][k];
                    else
                        dp[i + 1][j][k] = Math.max(dp[i][j][k], 1 + dp[i][j - cnt01[i][0]][k - cnt01[i][1]]);
                }
            }
        }

        return Math.max(dp[len][m][n], 0);
    }

    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        int[][] dp = new int[m+1][n+1];

        int[][] cnt01 = new int[len][2];
        for(int i = 0; i < len; i++) {
            for(char ch : strs[i].toCharArray()) {
                cnt01[i][ch-'0']++;
            }
        }

        for(int[] c : cnt01) {
            for(int j = m; j >= c[0]; j--) {
                for(int k = n; k >= c[1]; k--) {
                    dp[j][k] = Math.max(dp[j][k], 1+dp[j-c[0]][k-c[1]]);
                }
            }
        }

        return dp[m][n];
    }

    @Test
    public void test() {
        // int[] nums = { 1, 6, 4, 3, 2 };
        // System.out.println(maxTotalReward(nums));
        String[] strs = {"10001110","11000","111110"};
        findMaxForm(strs, 6, 6);
    }
}
