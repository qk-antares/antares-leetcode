package leetcode.questions.T1000.T100.medium;

import org.junit.jupiter.api.Test;

public class MatrixT {
    /**
     * 59. 螺旋矩阵 II
     */
    public int[][] generateMatrix(int n) {
        int[][] ans = new int[n][n];

        int start = 1;
        for (int i = 0; i < (n + 1) / 2; i++) {
            setMatrix(i, n-1-i, start, ans);
            start += 4*(n-1-2*i);
        }

        return ans;
    }

    public void setMatrix(int x, int y, int start, int[][] matrix){
        if(x == y){
            matrix[x][x] = start;
            return;
        }

        int delta = y-x;

        for (int i = 0; i < delta ; i++) {
            matrix[x][x+i] = start+i;
            matrix[x+i][y] = start+delta+i;
            matrix[y][y-i] = start+delta*2+i;
            matrix[y-i][x] = start+delta*3+i;
        }
    }

    @Test
    void test(){
        generateMatrix(3);
    }
}
