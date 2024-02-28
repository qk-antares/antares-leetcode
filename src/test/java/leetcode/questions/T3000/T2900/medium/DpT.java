package leetcode.questions.T3000.T2900.medium;

import java.util.Arrays;

public class DpT {
    /**
     * 2008. 出租车的最大盈利
     * dp[i+1]表示从[0...i]的最大盈利
     * dp[i+1] = max(dp[i], )
     */
    public long maxTaxiEarnings(int n, int[][] rides) {
        int m = rides.length;
        Arrays.sort(rides, (o1, o2) -> o1[1]-o2[1]);

        return 0;
    }
}
