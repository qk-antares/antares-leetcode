package leetcode.questions.T1000.T200.hard;

import org.junit.jupiter.api.Test;

public class DpT {
    /**
     * 115. 不同的子序列
     * dp[i][j]表示【s的前i位】和【t的前j位】在题设下的解
     * 显然i<j时，dp[i][j]=0
     * i=j时，dp[i][j]=1 (s[0..i]=t[0..j]) 否则 dp[i][j]=0
     * i>j时，dp[i][j]=dp[i-1][j] + dp[i-1][j-1] (如果s[i]=t[j])
     *        dp[i][j]=dp[i-1][j] (如果s[i]!=t[j]) 依赖左上角和上方的值
     * 假设 s='aab' t='ab'
     *    t   t   t
     * s [1] [0] [0]
     * s [1] [1] [0]
     * s [1] [ ] [0]
     * s [1] [ ] [ ]
     */
    public int numDistinct(String s, String t) {
        int m = s.length(), n = t.length();
        if(m < n){
            return 0;
        }

        int[][] dp = new int[m + 1][n + 1];
        // 初始化
        for (int i = 0; i < m+1; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < n+1; i++) {
            dp[i][i] = s.charAt(i-1) == t.charAt(i-1) ? dp[i-1][i-1] : 0;
        }

        // 构造dp
        for (int i = 2; i < m+1; i++) {
            for (int j = 1; j < i && j < n+1; j++) {
                if(s.charAt(i-1) == t.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[m][n];
    }

    /**
     * 上述方法还可以简写为
     */
    public int numDistinct0(String s, String t) {
        int m = s.length(), n = t.length();
        if(m < n){
            return 0;
        }

        int[][] dp = new int[m + 1][n + 1];
        // 初始化
        for (int i = 0; i < m+1; i++) {
            dp[i][0] = 1;
        }

        // 构造dp
        for (int i = 1; i < m+1; i++) {
            for (int j = 1; j <= i && j < n+1; j++) {
                if(s.charAt(i-1) == t.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[m][n];
    }

    /**
     * 123. 买卖股票的最佳时机 III
     */
    public int maxProfitWrongAns(int[] prices) {
        int len = prices.length;
        if(len == 1){
            return 0;
        }

        int max1 = 0, max2 = 0;
        int[] delta = new int[len-1];
        for (int i = 0; i < len-1; i++) {
            delta[i] = prices[i+1] - prices[i];
        }
        for (int i = 0; i < delta.length; i++) {
            int up = 0;
            while (i < delta.length && delta[i] > 0) {
                up += delta[i];
                i++;
            }
            if(up > max1){
                max2 = max1;
                max1 = up;
            } else if(up > max2){
                max2 = up;
            }
        }
        return max1 + max2;
    }


    public int maxProfit(int[] prices) {
        int buy1 = -prices[0], sell1 = 0, buy2 = -prices[0], sell2 = 0;
        for (int i = 1; i < prices.length; i++) {
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }
        return sell2;
    }

    @Test
    void test(){
        maxProfit(new int[]{1,2,4,2,5,7,2,4,9,0});
    }
}
