package leetcode.hots100;

import java.util.HashSet;

public class MatrixLearn {
    /**
     * 矩阵置零
     */
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        HashSet<Integer> rows = new HashSet<>(m);
        HashSet<Integer> columns = new HashSet<>(n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(matrix[i][j] == 0){
                    rows.add(i);
                    columns.add(j);
                }
            }
        }

        for (Integer row : rows) {
            for (int j = 0; j < n; j++) {
                matrix[row][j] = 0;
            }
        }

        for (Integer column : columns) {
            for (int i = 0; i < m; i++) {
                matrix[i][column] = 0;
            }
        }
    }
}
