package leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 二维数组Grid
 */
public class GridT {
    /**
     * 54. 螺旋矩阵 [Medium]
     * 
     * 访问过的格子标记为Integer.MAX_VALUE
     * 用dir数组维护方向
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        int idx = 0;
        List<Integer> ans = new ArrayList<>();
        int i = 0, j = 0;
        int m = matrix.length, n = matrix[0].length;
        for (int k = 0; k < m * n; k++) {
            ans.add(matrix[i][j]);
            matrix[i][j] = Integer.MAX_VALUE;
            int nextI = i + dir[idx][0];
            int nextJ = j + dir[idx][1];
            if (nextI >= 0 && nextI < m && nextJ >= 0 && nextJ < n && matrix[nextI][nextJ] != Integer.MAX_VALUE) {
                i = nextI;
                j = nextJ;
            } else {
                idx = (idx + 1) % 4;
                i = i + dir[idx][0];
                j = j + dir[idx][1];
            }
        }
        return ans;
    }

    /**
     * 48. 旋转图像 [Medium]
     * 
     * 转置后对称
     */
    public void rotate(int[][] matrix) {
        int n = matrix[0].length;
        // 转置
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }

        // 对称
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = tmp;
            }
        }
    }

    /**
     * 240. 搜索二维矩阵 II [Medium]
     * 
     * 从右上角开始搜索，逐渐缩小查找范围
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int i = 0, j = n - 1;
        while (i >= 0 && i < m && j >= 0 && j < n) {
            if (matrix[i][j] == target)
                return true;
            else if (matrix[i][j] > target) {
                j--;
            } else if (matrix[i][j] < target) {
                i++;
            }
        }
        return false;
    }

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

    /**
     * 3567. 子矩阵的最小绝对差 [Medium]
     * 
     * 暴力枚举
     */
    public int[][] minAbsDiff(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[][] ans = new int[m - k + 1][n - k + 1];

        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= n - k; j++) {
                int idx = 0;
                int[] tmp = new int[k * k];
                for (int x = 0; x < k; x++) {
                    for (int y = 0; y < k; y++) {
                        tmp[idx++] = grid[i + x][j + y];
                    }
                }
                Arrays.sort(tmp);

                int res = Integer.MAX_VALUE;
                for (int x = 1; x < k * k; x++) {
                    if (tmp[x] == tmp[x - 1])
                        continue;
                    res = Math.min(res, tmp[x] - tmp[x - 1]);
                }
                ans[i][j] = (res != Integer.MAX_VALUE ? res : 0);
            }
        }

        return ans;
    }
}
