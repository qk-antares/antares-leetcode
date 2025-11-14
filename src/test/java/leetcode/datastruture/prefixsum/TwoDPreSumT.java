package leetcode.datastruture.prefixsum;

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

}
