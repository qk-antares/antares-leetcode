package leetcode.dp.other;

/**
 * 子矩阵DP
 */
public class SubMatrixDpT {
    /**
     * 221. 最大正方形 [Medium]
     * 
     * dp[i+1][j+1]标识matrix[i][j]结尾的最大正方形边长
     * matrix[i][j]=0: dp[i+1][j+1] = 0
     * matrix[i][j]=1: dp[i+1][j+1] = 1+Math.min(dp[i+1][j], dp[i][j+1], dp[i][j])
     */
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m + 1][n + 1];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    dp[i + 1][j + 1] = 1 + Math.min(dp[i + 1][j], Math.min(dp[i][j + 1], dp[i][j]));
                    ans = Math.max(ans, dp[i + 1][j + 1]);
                }
            }
        }
        return ans * ans;
    }
}
