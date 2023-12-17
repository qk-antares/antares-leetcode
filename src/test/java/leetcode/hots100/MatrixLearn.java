package leetcode.hots100;

import java.util.*;

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

    /**
     * 螺旋矩阵（递归，外面一圈，然后内部还是矩阵）
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int count = (Math.min(m,n)+1)/2;
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ans.addAll(helper(matrix, i, m, n));
            m-=2;
            n-=2;
        }
        return ans;
    }
    List<Integer> helper(int[][] matrix, int pos, int m, int n){
        ArrayList<Integer> ans = new ArrayList<>();
        if(m == 1 && n == 1){
            ans.add(matrix[pos][pos]);
            return ans;
        }

        if(m == 1){
            for (int j = pos; j < pos+n; j++) {
                ans.add(matrix[pos][j]);
            }
            return ans;
        }

        if(n == 1){
            for (int i = pos; i < pos+m; i++) {
                ans.add(matrix[i][pos]);
            }
            return ans;
        }

        for (int j = pos; j < pos+n-1; j++) {
            ans.add(matrix[pos][j]);
        }
        for (int i = pos; i < pos+m-1; i++) {
            ans.add(matrix[i][pos+n-1]);
        }
        for (int j = pos+n-1;j > pos;j--){
            ans.add(matrix[pos+m-1][j]);
        }
        for (int i = pos+m-1; i > pos; i--) {
            ans.add(matrix[i][pos]);
        }
        return ans;
    }

    /**
     * 旋转图像（转置后对称）
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        //转置
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        //对称
        for (int j = 0; j < n / 2; j++) {
            for (int i = 0; i < n; i++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][n-j-1];
                matrix[i][n-j-1] = tmp;
            }
        }
    }

    /**
     * 搜索二维矩阵（从右上角开始查找！！！）
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length, x = 0, y = n-1;

        while (x < m && y > -1){
            if(matrix[x][y] == target){
                return true;
            } else if(matrix[x][y] > target){
                y--;
            } else {
                x++;
            }
        }
        return false;
    }

}
