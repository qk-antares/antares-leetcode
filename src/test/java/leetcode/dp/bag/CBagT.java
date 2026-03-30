package leetcode.dp.bag;

import java.util.Arrays;

/**
 * 完全背包问题
 * 物品可以重复选，无个数限制
 */
public class CBagT {
    /**
     * 322. 零钱兑换 [Medium]
     * 
     * dp[i]表示凑满i所需的最少硬币个数
     * dp[i] = 1 + min{dp[i-coin]}
     * 
     * 将coins的循环放在外层，amount的循环放在内层，可以提高效率
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 0; i <= amount; i++) {
            int min = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != -1 && dp[i - coin] < min) {
                    min = dp[i - coin];
                }
            }
            dp[i] = (min == Integer.MAX_VALUE ? -1 : 1 + min);
        }

        return dp[amount];
    }

    public int coinChange0(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
            }
        }

        return dp[amount] == Integer.MAX_VALUE / 2 ? -1 : dp[amount];
    }

    /**
     * 279. 完全平方数 [Medium]
     * 
     * dp[i]表示i的ans
     * dp[i] = 1+min{dp[i-n^2]}
     */
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j]);
            }
            dp[i]++;
        }
        return dp[n];
    }
}
