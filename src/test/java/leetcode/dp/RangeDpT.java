package leetcode.dp;

import java.util.Arrays;

/* 
 * 区间Dp
*/
public class RangeDpT {
    /*
     * 1039. 多边形三角剖分的最低得分 [Medium]
     * 
     * values记录了n边形顺时针顺序的节点值
     * dp[i][j]表示[i,j]这一段的答案
     * dp[i][j] = min(dp[i][k] + dp[k][j] + values[i] * values[k] * values[j])
     */
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(0, n - 1, memo, values);
    }

    int dfs(int i, int j, int[][] memo, int[] values) {
        if (i + 1 >= j)
            return 0; // 无法组成三角形

        if (memo[i][j] != -1)
            return memo[i][j];

        int ans = Integer.MAX_VALUE;
        for (int k = i + 1; k < j; k++) {
            ans = Math.min(ans, dfs(i, k, memo, values) + dfs(k, j, memo, values) + values[i] * values[j] * values[k]);
        }

        memo[i][j] = ans;
        return ans;
    }
}
