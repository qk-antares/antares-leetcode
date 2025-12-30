package leetcode.array;

import java.util.Arrays;

/**
 * 二维数组Grid
 */
public class GridT {
    /**
     * 3531. 统计被覆盖的建筑 [Medium]
     * 
     * 可以将buildings的坐标转为唯一的long型
     * x*100000+y
     * 没读题，不要求相邻
     * 横向和纵向两次遍历
     */
    public int countCoveredBuildings(int n, int[][] buildings) {
        int[] rowMin = new int[n + 1];
        Arrays.fill(rowMin, n + 1);
        int[] rowMax = new int[n + 1];
        int[] colMin = new int[n + 1];
        Arrays.fill(colMin, n + 1);
        int[] colMax = new int[n + 1];
        for (int[] b : buildings) {
            int row = b[0], col = b[1];
            rowMin[row] = Math.min(rowMin[row], col);
            rowMax[row] = Math.max(rowMax[row], col);
            colMin[col] = Math.min(colMin[col], row);
            colMax[col] = Math.max(colMax[col], row);
        }

        int ans = 0;
        for (int[] b : buildings) {
            int row = b[0], col = b[1];
            if (row > colMin[col] && row < colMax[col] && col > rowMin[row] && col < rowMax[row])
                ans++;
        }

        return ans;
    }

    /**
     * 840. 矩阵中的幻方 [Medium]
     * 
     * sum(1,9)=10*9/2 = 45
     * 则每行/每列/对角线的和为15
     * 用两个flag矩阵标记：
     * flag1: [idx . .] 的和为15
     * flag2:
     * idx
     * .
     * .
     * 的和为15
     * 则满足幻方的前提是：
     * flag1[i][j] && flag1[i+1][j] && flag1[i+2][j] && flag2[i][j] && flag2[i][j+1]
     * && flag2[i][j+2]
     * 枚举幻方的左上角
     * 
     * 经证明，幻方的中间只能为5
     */
    public int numMagicSquaresInside(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int ans = 0;

        for (int i = 0; i < m - 2; i++) {
            for (int j = 0; j < n - 2; j++) {
                if (grid[i + 1][j + 1] != 5) {
                    continue;
                }

                int mask = 0;
                int[] rSum = new int[3];
                int[] cSum = new int[3];
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        int x = grid[i + r][j + c];
                        mask |= 1 << x;
                        rSum[r] += x;
                        cSum[c] += x;
                    }
                }

                if (mask == (1 << 10) - 2 &&
                        rSum[0] == 15 && rSum[1] == 15 &&
                        cSum[0] == 15 && cSum[1] == 15) {
                    ans++;
                }
            }
        }

        return ans;
    }

    public int numMagicSquaresInside0(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        if (m < 3 || n < 3)
            return 0;

        boolean[][] flag1 = new boolean[m][n - 2];
        boolean[][] flag2 = new boolean[m - 2][n];

        for (int i = 0; i < m; i++) {
            int s = grid[i][0] + grid[i][1] + grid[i][2];
            flag1[i][0] = s == 15;
            for (int j = 1; j < n - 2; j++) {
                s += grid[i][j + 2] - grid[i][j - 1];
                flag1[i][j] = s == 15;
            }
        }

        for (int j = 0; j < n; j++) {
            int s = grid[0][j] + grid[1][j] + grid[2][j];
            flag2[0][j] = s == 15;
            for (int i = 1; i < m - 2; i++) {
                s += grid[i + 2][j] - grid[i - 1][j];
                flag2[i][j] = s == 15;
            }
        }

        int ans = 0;
        for (int i = 0; i < m - 2; i++) {
            for (int j = 0; j < n - 2; j++) {
                if (flag1[i][j] && flag1[i + 1][j] && flag1[i + 2][j] && flag2[i][j] && flag2[i][j + 1]
                        && flag2[i][j + 2] && check0(grid, i, j))
                    ans++;
            }
        }

        return ans;
    }

    public boolean check0(int[][] grid, int x, int y) {
        boolean[] flag = new boolean[10];
        for (int i = x; i <= x + 2; i++) {
            for (int j = y; j <= y + 2; j++) {
                if (grid[i][j] > 9 || grid[i][j] < 1 || flag[grid[i][j]])
                    return false;
                flag[grid[i][j]] = true;
            }
        }
        return grid[x][y] + grid[x + 1][y + 1] + grid[x + 2][y + 2] == 15
                && grid[x + 2][y] + grid[x + 1][y + 1] + grid[x][y + 2] == 15;
    }
}
