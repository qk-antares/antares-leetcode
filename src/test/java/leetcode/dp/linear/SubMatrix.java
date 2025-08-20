package leetcode.dp.linear;

public class SubMatrix {
    /*
     * 1277. 统计全为 1 的正方形子矩阵 [Medium]
     * 
     * 设dp[i][j]表示以matrix[i][j]结尾的正方形个数
     */
    public int countSquares(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m + 1][n + 1];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    continue;
                }
                dp[i + 1][j + 1] = Math.min(dp[i][j], Math.min(dp[i][j + 1], dp[i + 1][j])) + 1;
                ans += dp[i + 1][j + 1];
            }
        }
        return ans;
    }
}
