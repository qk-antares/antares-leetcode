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

    /**
     * 最大子序和（我的解法：参照上面的解法四，效率最高）
     */
    public int maxSubArray(int[] nums) {
        if(nums == null || nums.length == 0)
            return 0;

        int cur = nums[0];
        int max_sum = cur;
        for(int i = 1;i < nums.length;i++){
            cur = Math.max(cur, 0) + nums[i];
            max_sum = Math.max(cur, max_sum);
        }

        return max_sum;
    }

    /**
     * 解法二（动态规划解法，效率有较大的下降）
     * dp[i]表示以i为右端点的子序的最大和
     * dp[i] = max(nums[i], dp[i-1]+nums[i])
     */
    public int maxSubArray0(int[] nums) {
        if(nums == null || nums.length == 0)
            return 0;

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max_sum = dp[0];
        for(int i = 1;i < nums.length;i++){
            dp[i] = Math.max(nums[i], dp[i-1]+nums[i]);
            if(dp[i] > max_sum)
                max_sum = dp[i];
        }

        return max_sum;
    }

    /**
     * 打家劫舍（我的解法，动态规划解法，效率还是不错滴）
     * dp[i][0]= max(dp[i-1][1], dp[i-1][0])
     * dp[i][1] = dp[i-1][0] + nums[i]
     * dp[0][0] = 0
     * dp[1][0] = nums[0]
     *
     * [1,2,3,1]
     */
    public int rob(int[] nums) {
        int[][] dp = new int[nums.length][2];
        dp[0][0] = 0;
        dp[0][1] = nums[0];

        for(int i = 1;i < nums.length;i++){
            dp[i][1] = dp[i-1][0] + nums[i];
            dp[i][0] = Math.max(dp[i-1][1], dp[i-1][0]);
        }

        return Math.max(dp[nums.length-1][0], dp[nums.length-1][1]);
    }

    /**
     * 我的解法二（使用临时变量而不是数组）
     * [1,1]
     */
    public int rob0(int[] nums) {
        int res0 = 0,res1 = nums[0];
        int temp = nums[0];
        for(int i = 1;i < nums.length;i++){
            res1 = res0 + nums[i];
            res0 = Math.max(temp, res0);
            temp = res1;
        }

        return Math.max(res0, res1);
    }

    @Test
    public void invoke(){
//        maxProfit1(new int[]{7,1,6,5,9,4});
        rob(new int[]{1,2,3,1});
    }

}
