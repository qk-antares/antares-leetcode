package leetcode.dp;

import java.util.Arrays;

/*
 * 概率与数学期望型DP
 */
public class ExDp {
    /*
     * 808. 分汤 [Medium] <Star>
     * 
     * n=Math.ceiling(n/25.0)，4种方式等价于：
     * (4,0),(3,1),(2,2),(1,3)
     * dp[i][j]表示A B汤还有i和j份时，A汤先取完的概率，则
     * dp[i][j]=1/4(dp[i-4][j]+dp[i-3][j-1]+dp[i-2][j-2]+dp[i-1][j-3])
     * 边界条件dp[0][..]=1, dp[0][0]=0.5, dp[..][0]=0
     * 上述算法的时间复杂度时O(n^2)，由于n的范围非常大，可能会超时
     * 在n大到一定程度，直接返回1
     */
    public double soupServings(int n) {
        n = (int) Math.ceil(n / 25.0);

        if (n >= 200)
            return 1.0;

        double[][] dp = new double[n + 1][n + 1];
        Arrays.fill(dp[0], 1.0);
        dp[0][0] = 0.5;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 0.0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = 0.25 * (dp[Math.max(0, i - 4)][j] + dp[Math.max(0, i - 3)][j - 1] +
                        dp[Math.max(0, i - 2)][Math.max(0, j - 2)] + dp[i - 1][Math.max(0, j - 3)]);
            }
        }

        return dp[n][n];
    }
}
