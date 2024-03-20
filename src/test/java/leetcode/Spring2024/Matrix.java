package leetcode.Spring2024;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Matrix {
    /**
     * 54. 螺旋矩阵
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int row = matrix.length, col = matrix[0].length;
        return helper(matrix, 0, 0, row-1, col-1);
    }

    public List<Integer> helper(int[][] matrix, int x1, int y1, int x2, int y2) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        if(x1 > x2 || y1 > y2) {
            return ans;
        }

        if(x1 == x2 && y1 == y2){
            ans.add(matrix[x1][x2]);
            return ans;
        }

        if(x1 == x2){
            for (int i = y1; i <= y2; i++) {
                ans.add(matrix[x1][i]);
            }
            return ans;
        }

        if(y1 == y2){
            for (int i = x1; i <= x2; i++) {
                ans.add(matrix[i][y1]);
            }
            return ans;
        }

        for (int i = y1; i <= y2 - 1; i++) {
            ans.add(matrix[x1][i]);
        }
        for (int i = x1; i <= x2 - 1; i++) {
            ans.add(matrix[i][y2]);
        }
        for (int i = y2; i >= y1 + 1; i--) {
            ans.add(matrix[x2][i]);
        }
        for (int i = x2; i >= x1 + 1; i--) {
            ans.add(matrix[i][y1]);
        }
        ans.addAll(helper(matrix, x1+1, y1+1, x2-1, y2-1));
        return ans;
    }

    @Test
    void test(){
        spiralOrder(new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12}});
    }
}
