package leetcode.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 198. 打家劫舍 [Medium]
     * 
     * dp[i+1]表示nums[0..i]能投到的最高金额
     * dp[0] = 0
     * dp[i+1] = Math.max(dp[i], nums[i]+dp[i-1])
     * 
     * 可进行状态压缩
     */
    public int rob0(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n + 1];
        dp[1] = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i + 1] = Math.max(dp[i], nums[i] + dp[i - 1]);
        }
        return dp[n];
    }

    public int rob(int[] nums) {
        int n = nums.length;
        int prepre = 0, pre = nums[0];
        for (int i = 1; i < n; i++) {
            int tmp = pre;
            pre = Math.max(pre, nums[i] + prepre);
            prepre = tmp;
        }
        return pre;
    }

    /*
     * 213. 打家劫舍 II [Medium]
     * 
     * 用dp[i][0]和dp[i][1]来区分选了第一个和不选第一个两种情况
     * 也可以用4个状态来压缩，这里就略了
     */
    public int rob2(int[] nums) {
        int n = nums.length;
        if (n < 2)
            return nums[0];
        int[][] dp = new int[n + 1][2];
        dp[1][0] = nums[0];
        for (int i = 1; i < n; i++) {
            // 选了
            dp[i + 1][0] = Math.max(dp[i][0], dp[i - 1][0] + nums[i]);
            // 没选
            dp[i + 1][1] = Math.max(dp[i][1], dp[i - 1][1] + nums[i]);
        }
        return Math.max(dp[n - 1][0], dp[n][1]);
    }

    /*
     * 2320. 统计放置房子的方式数 [Medium]
     * 
     * 矩阵快速幂：
     * 每1列有4种情况：00 01 10 11
     * 01 和 10 不能和自身以及11相邻
     * 11只能和00相邻
     * f[i][00] = f[i-1][00] + f[i-1][01] + f[i-1][10] + f[i-1][11]
     * f[i][01] = f[i-1][00] + f[i-1][10]
     * f[i][10] = f[i-1][00] + f[i-1][01]
     * f[i][11] = f[i-1][00]
     * F[i] = F[i-1] * M
     * F是一个行向量
     * 状态转移矩阵M：
     * 1 1 1 1
     * 1 0 1 0
     * 1 1 0 0
     * 1 0 0 0
     * 初始F[1] = [1, 1, 1, 1]
     * F[i] = F[1] * M^(i-1)
     * 
     * 打家劫舍：
     * 单独考虑一侧，两侧相互独立
     * f[i+1]表示前i个地块的放置数
     * f[i+1] = f[i] (i+1不放) + f[i-1] (i+1放)
     * 初始条件f[0]=1,f[1]=2
     * 可以进行预处理
     * 
     * 矩阵快速幂的方法可以推广到同列不能同时建房子，同时效率更高
     */
    public int countHousePlacements(int n) {
        int[][] M = new int[][] {
                { 1, 1, 1, 1 },
                { 1, 0, 1, 0 },
                { 1, 1, 0, 0 },
                { 1, 0, 0, 0 }
        };

        int[][] F = new int[][] {
                { 1, 1, 1, 1 }
        };

        F = matrixPow(F, M, n - 1);
        int ans = 0;
        for (int i = 0; i < 4; i++) {
            ans = (ans + F[0][i]) % 1_000_000_007;
        }
        return ans;
    }

    int[][] matrixPow(int[][] F, int[][] M, int n) {
        while (n > 0) {
            if ((n & 1) > 0)
                F = matrixMul(F, M);
            M = matrixMul(M, M);
            n >>= 1;
        }
        return F;
    }

    int[][] matrixMul(int[][] A, int[][] B) {
        int[][] ans = new int[A.length][B[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                for (int k = 0; k < B.length; k++) {
                    if (A[i][k] == 0 || B[k][j] == 0)
                        continue;
                    ans[i][j] = (int) ((ans[i][j] + (long) A[i][k] * B[k][j]) % 1_000_000_007);
                }
            }
        }
        return ans;
    }

    static int[] f = new int[10_001];
    static {
        f[0] = 1;
        f[1] = 2;
        for (int i = 1; i < 10_000; i++) {
            f[i + 1] = (f[i] + f[i - 1]) % 1_000_000_007;
        }
    }

    public int countHousePlacements0(int n) {
        return (int) ((long) f[n] * f[n] % 1_000_000_007);
    }

    /*
     * 740. 删除并获得点数 [Medium]
     * 
     * dp[i+1] 为从[1..i]可以获得的最大点数
     * dp[i+1] = Math.max(dp[i-1] + cnt[i] * i, dp[i])
     * dp[0] = 0;
     * dp[1] = cnt[1]*1
     */
    public int deleteAndEarn(int[] nums) {
        int[] cnt = new int[10_001];
        for (int num : nums) {
            cnt[num]++;
        }

        int prepre = 0;
        int pre = cnt[1];
        for (int i = 2; i <= 10_000; i++) {
            int tmp = pre;
            pre = Math.max(pre, prepre + cnt[i] * i);
            prepre = tmp;
        }

        return pre;
    }

    /*
     * 3186. 施咒的最大总伤害 [Medium] <Star>
     * 
     * 本题power的值域较大，不能用暴力算法
     * 用HashMap统计所有数字的出现次数，key为数字
     * 接下来用一个数组ps统计所有key并排序
     * 对ps进行遍历，并用j来标记比当前遍历元素p小2的元素（ps[j]<p-2）
     */
    public long maximumTotalDamage(int[] power) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int p : power) {
            cnt.merge(p, 1, Integer::sum);
        }

        int n = cnt.size();
        // 记录key（power）并排序
        int[] ps = new int[n];
        int idx = 0;
        for (Integer key : cnt.keySet()) {
            ps[idx++] = key;
        }
        Arrays.sort(ps);

        long[] dp = new long[n + 1];
        int j = 0;
        for (int i = 0; i < n; i++) {
            int p = ps[i];

            while (ps[j] < p - 2) {
                j++;
            }

            // 不选或选
            dp[i + 1] = Math.max(dp[i], dp[j] + (long) p * cnt.get(p));
        }

        return dp[n];
    }

    /*
     * 2140. 解决智力问题 [Medium]
     * 
     * dp[i]表示[i..n-1]能获得的最大分数
     * 倒序
     */
    public long mostPoints(int[][] questions) {
        int n = questions.length;
        long[] dp = new long[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = Math.max(
                    dp[i + 1],
                    questions[i][0] + dp[Math.min(n, i + 1 + questions[i][1])]);
        }

        return dp[0];
    }

    /*
     * 53. 最大子数组和 [Medium]
     * 
     * 前缀和，过程中记录前面的最小值
     */
    public int maxSubArray(int[] nums) {
        int min = 0;
        int s = 0;
        int ans = Integer.MIN_VALUE;
        for (int num : nums) {
            s += num;
            ans = Math.max(ans, s - min);
            min = Math.min(min, s);
        }
        return ans;
    }

    /*
     * 2606. 找到最大开销的子字符串 [Medium]
     * 
     * 前缀和
     */
    public int maximumCostSubstring(String s, String chars, int[] vals) {
        int[] charVals = new int[26];
        for (int i = 0; i < 26; i++) {
            charVals[i] = i + 1;
        }

        for (int i = 0; i < vals.length; i++) {
            charVals[chars.charAt(i) - 'a'] = vals[i];
        }

        int sum = 0;
        int min = 0;
        int ans = 0;
        for (char ch : s.toCharArray()) {
            sum += charVals[ch - 'a'];
            ans = Math.max(ans, sum - min);
            min = Math.min(min, sum);
        }

        return ans;
    }

    /*
     * 1749. 任意子数组和的绝对值的最大值 [Medium]
     * 
     * 前缀和，记录最大以及最小
     */
    public int maxAbsoluteSum(int[] nums) {
        int s = 0;
        int min = 0;
        int max = 0;
        int ans1 = 0;
        int ans2 = 0;
        for (int num : nums) {
            s += num;
            ans1 = Math.max(ans1, s - min);
            ans2 = Math.min(ans2, s - max);
            min = Math.min(min, s);
            max = Math.max(max, s);
        }
        return Math.max(Math.abs(ans1), Math.abs(ans2));
    }

    /*
     * 1191. K 次串联后最大子数组之和 [Medium]
     * 
     * 前缀和，统计k=1以及k=2的答案
     * 按照arr的s以及k的奇偶可以分为4种情况：
     * 当s<=0时，重复arr是没有意义的，此时k=1则返回k1，否则返回k2
     * 当s>0时，中间的arr都重复，重复(k-2)次，然后加上k2，当然这要求至少重复2次，所以k=1时返回k1
     */
    public int kConcatenationMaxSum(int[] arr, int k) {
        int s = 0;
        int min = 0;
        int k1 = 0;
        for (int i = 0; i < arr.length; i++) {
            s += arr[i];
            k1 = Math.max(k1, s - min);
            min = Math.min(min, s);
        }

        int k2 = 0;
        for (int i = 0; i < arr.length; i++) {
            s += arr[i];
            k2 = Math.max(k2, s - min);
            min = Math.min(min, s);
        }

        if (s <= 0) {
            return k < 2 ? k1 : k2;
        } else {
            return k < 2 ? k1 : (int) (((long) s / 2 * (k - 2) + k2) % 1_000_000_007);
        }
    }

    /*
     * 918. 环形子数组的最大和  [Medium]
     * 
     * 计算和最大/最小的子数组
     * 则ans=Math.max(和最大的子数组，总和-和最小的子数组)
     * 特殊情况是总和=和最小的子数组，由于题目要求子数组不能为空，此时不管和最大的子数组是><0，都要返回它
     */
    public int maxSubarraySumCircular(int[] nums) {
        int min = 0, max = 0;
        int s = 0;
        int ans1 = Integer.MIN_VALUE, ans2 = Integer.MAX_VALUE;
        for(int num : nums) {
          s+=num;
          ans1 = Math.max(ans1, s-min);
          ans2 = Math.min(ans2, s-max);
          min = Math.min(min, s);
          max = Math.max(max, s);
        }

        return ans2 == s ? ans1 : Math.max(ans1, s-ans2);
    }

    @Test
    void test() {
        countGoodStrings(20, 20, 1, 6);
    }
}
