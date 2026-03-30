package leetcode.questions.Easy;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/9/5
 */
public class DpEasy {

    /**
     * 买卖股票的最佳时机（我的解法）
     * 每一天有3种选择
     * [7,1,5,3,6,4]
     * dp[i][0] = max(dp[i-1][0], prices[i] + dp[i][1])
     * dp[i][1] = max(dp[i-1][1], -prices[i])
     * return dp[len-1][0]
     */
    public int maxProfit(int[] prices) {
        int dp[][] = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -1 * prices[0];

        for(int i = 1;i < prices.length;i++){
            dp[i][1] = Math.max(dp[i-1][1], -1 * prices[i]);
            dp[i][0] = Math.max(dp[i-1][0], prices[i] + dp[i-1][1]);
        }
        return dp[prices.length-1][0];
    }

    /**
     * 解法二，使用两个临时变量（略有提升）
     */
    public int maxProfit0(int[] prices) {
        int temp = -1 * prices[0];
        int res0 = 0;
        int res1 = temp;
        for(int i = 1;i < prices.length;i++){
            res1 = Math.max(res1, -1 * prices[i]);
            res0 = Math.max(res0, prices[i] + temp);
            temp = res1;
        }
        return res0;
    }

    /**
     * 解法三：双指针，一个指向访问过的最小值，一个往后求差值的值
     */
    public int maxProfit1(int[] prices) {
        if(prices == null || prices.length == 0)
            return 0;
        int min = prices[0];
        int max_pro = 0;
        for(int i = 1;i < prices.length;i++){
            if(prices[i] < min)
                min = prices[i];
            max_pro = Math.max(max_pro, prices[i]-min);
        }
        return max_pro;
    }

    /**
     * 解法四：最大子序和(效率最低)
     */
    public int maxProfit2(int[] prices) {
        if(prices == null || prices.length == 0)
            return 0;

        int max_pro = 0;
        int cur = 0;
        for(int i = 1;i < prices.length;i++){
            cur = Math.max(cur, 0) + prices[i] - prices[i-1];
            max_pro = Math.max(max_pro, cur);
        }

        return max_pro;
    }

    @Test
    public void invoke(){
//        maxProfit1(new int[]{7,1,6,5,9,4});
    }

}
