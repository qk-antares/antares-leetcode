package leetcode.dp;

import java.util.Arrays;

/*
 * 网格DP
 */
public class GridDp {
    /*
     * 64. 最小路径和 [Medium]
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        for (int i = 1; i < n; i++) {
            grid[0][i] += grid[0][i - 1];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }
        return grid[m - 1][n - 1];
    }

    /*
     * 3393. 统计异或值为给定值的路径数目 [Medium]
     * 
     * 注意grid中元素的范围是[0,16)，这意味着异或运算的值域是有限的，即[0,15]
     * 这里必须使用记忆化搜索来避免重复计算，否则超时
     */
    public int countPathsWithXorValue(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[][][] memo = new int[m][n][16];
        for (int[][] matrix : memo) {
            for (int[] row : matrix) {
                Arrays.fill(row, -1);
            }
        }

        return dfs(grid, m - 1, n - 1, k, memo);
    }

    int dfs(int[][] grid, int i, int j, int k, int[][][] memo) {
        if (i == 0 && j == 0) {
            return k == grid[0][0] ? 1 : 0;
        }

        if (memo[i][j][k] != -1)
            return memo[i][j][k];

        int ans = 0;
        if (i > 0) {
            ans = (ans + dfs(grid, i - 1, j, k ^ grid[i][j], memo)) % 1_000_000_007;
        }
        if (j > 0) {
            ans = (ans + dfs(grid, i, j - 1, k ^ grid[i][j], memo)) % 1_000_000_007;
        }
        memo[i][j][k] = ans;

        return ans;
    }

    /*
     * 931. 下降路径最小和 [Medium]
     * 
     * dp[i][j]表示走到matrix[i][j]的最小和
     * 可以直接在matrix上修改
     */
    public int minFallingPathSum0(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] += Math.min(matrix[i - 1][j],
                        Math.min(matrix[i - 1][Math.max(0, j - 1)], matrix[i - 1][Math.min(n - 1, j + 1)]));
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            ans = Math.min(ans, matrix[m - 1][j]);
        }

        return ans;
    }

    public int minFallingPathSum(int[][] matrix) {
        int len = matrix.length;

        if (len == 1)
            return matrix[0][0];

        // 从第二行开始
        for (int i = 1; i < len; i++) {
            // 第一位
            matrix[i][0] += Math.min(matrix[i - 1][0], matrix[i - 1][1]);
            // 中间位
            for (int j = 1; j < len - 1; j++) {
                matrix[i][j] += Math.min(Math.min(matrix[i - 1][j - 1], matrix[i - 1][j]), matrix[i - 1][j + 1]);
            }
            // 最后一位
            matrix[i][len - 1] += Math.min(matrix[i - 1][len - 2], matrix[i - 1][len - 1]);
        }

        // 求最小值
        int ans = matrix[len - 1][0];
        for (int i = 1; i < len; i++) {
            if (matrix[len - 1][i] < ans)
                ans = matrix[len - 1][i];
        }

        return ans;
    }

    /*
     * 2684. 矩阵中移动的最大次数 [Medium] <Star>
     * 
     * 从每行的第一个单元格出发进行dfs，一旦到达一个单元格，就将其标记为0，防止后面再次访问
     * 在dfs的过程中，记录到达的最远距离
     */
    int ans = 0;

    public int maxMoves(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            dfs(grid, i, 0, m, n);
        }
        return ans;
    }

    void dfs(int[][] grid, int i, int j, int m, int n) {
        ans = Math.max(ans, j);
        if (j == n - 1)
            return;

        int tmp = grid[i][j];
        grid[i][j] = 0;

        if (i > 0 && grid[i - 1][j + 1] > tmp) {
            dfs(grid, i - 1, j + 1, m, n);
        }
        if (grid[i][j + 1] > tmp) {
            dfs(grid, i, j + 1, m, n);
        }
        if (i < m - 1 && grid[i + 1][j + 1] > tmp) {
            dfs(grid, i + 1, j + 1, m, n);
        }
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 3363. 最多可收集的水果数目 [Hard]
     * 
     * 从(0,0)出发的只能走对角线，其余的两个角落的是相同的子问题：
     * 不能访问对角线（否则无法到达终点）
     */
    public int maxCollectedFruits(int[][] fruits) {
        int n = fruits.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += fruits[i][i];
        }

        ans += maxCorner(fruits);

        // 对矩阵进行转置
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                fruits[j][i] = fruits[i][j];
            }
        }

        ans += maxCorner(fruits);

        return ans;
    }

    int maxCorner(int[][] fruits) {
        int n = fruits.length;
        int[][] dp = new int[2][n + 1];
        dp[0][n - 1] = fruits[0][n - 1];
        for (int i = 1; i < n - 1; i++) {
            int cur = i % 2;
            int pre = (i - 1) % 2;
            for (int j = Math.max(i + 1, n - i - 1); j < n; j++) {
                dp[cur][j] = fruits[i][j] + Math.max(dp[pre][j], Math.max(dp[pre][j - 1], dp[pre][j + 1]));
            }
        }
        return dp[(n - 2) % 2][n - 1];
    }
}
