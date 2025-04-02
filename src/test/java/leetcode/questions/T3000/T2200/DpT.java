package leetcode.questions.T3000.T2200;

import org.junit.jupiter.api.Test;

public class DpT {
    /*
     * 2140. 解决智力问题
     * 
     * 状态定义：使用dp[i]表示倒着做，做到questions[i]所能获得的最高分数
     * 由于做了questions[i]影响的是后面的题目，我们需要从后往前遍历，这样每做1道题相当于影响的是前面的
     * 状态转移（对当前的问题解决或者跳过）：dp[i] = Math.max(questions[i][0]+dp[i+question[1]],
     * dp[i+1])
     * 显然dp[n-1] = questions[n-1][0]
     */
    public long mostPoints(int[][] questions) {
        int n = questions.length;
        int[] dp = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            // 如果做了，之前能获得的最大分数
            int doPrev = i + questions[i][1] + 1 >= n ? 0 : dp[i + questions[i][1] + 1];
            // 如果不做，之前能获得的最大分数
            int skipPrev = i + 1 >= n ? 0 : dp[i + 1];

            dp[i] = Math.max(questions[i][0] + doPrev, skipPrev);
        }

        return dp[0];
    }

    @Test
    void test() {
        mostPoints(new int[][] { { 3, 2 }, { 4, 3 }, { 4, 4 }, { 2, 5 } }); // 5
        mostPoints(new int[][] { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 } }); // 7
    }
}
