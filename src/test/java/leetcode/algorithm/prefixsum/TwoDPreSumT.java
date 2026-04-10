package leetcode.algorithm.prefixsum;

/*
 * 二维前缀和
 */
public class TwoDPreSumT {
    /*
     * 304. 二维区域和检索 - 矩阵不可变 [Medium]
     */
    class NumMatrix {

        int[][] s;

        public NumMatrix(int[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            s = new int[m + 1][n + 1];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    s[i + 1][j + 1] = s[i + 1][j] + s[i][j + 1] - s[i][j] + matrix[i][j];
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return s[row2 + 1][col2 + 1] - s[row1][col2 + 1] - s[row2 + 1][col1] + s[row1][col1];
        }
    }

    /**
     * 3070. 元素和小于等于 k 的子矩阵的数目
     * 
     * 用tmp维护每行和
     * 用s维护当前遍历到的位置的矩阵和
     */
    public int countSubmatrices(int[][] grid, int k) {
        int ans = 0;
        int m = grid.length, n = grid[0].length;
        int[] s = new int[n];
        for (int i = 0; i < m; i++) {
            int tmp = 0;
            for (int j = 0; j < n; j++) {
                tmp += grid[i][j];
                s[j] += tmp;
                if (s[j] <= k)
                    ans++;
            }
        }
        return ans;
    }

    /**
     * 3212. 统计 X 和 Y 频数相等的子矩阵数量 [Medium]
     */
    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length, n = grid[0].length;

        // 记录每一行的状态
        int[] x = new int[n];
        int[] y = new int[n];

        int ans = 0;
        for (int i = 0; i < m; i++) {
            int cntX = 0, cntY = 0;
            for (int j = 0; j < n; j++) {
                switch (grid[i][j]) {
                    case 'X':
                        cntX++;
                        break;
                    case 'Y':
                        cntY++;
                        break;
                }
                x[j] += cntX;
                y[j] += cntY;

                if (x[j] > 0 && x[j] == y[j])
                    ans++;
            }
        }

        return ans;
    }
}
