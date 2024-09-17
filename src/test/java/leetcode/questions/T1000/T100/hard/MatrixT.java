package leetcode.questions.T1000.T100.hard;

public class MatrixT {
    /**
     * 85. 最大矩形
     */
    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        if(m == 0) {
            return 0;
        }
        int n = matrix[0].length;

        int[][] leftOnes = new int[m][n];
        for (int i = 0; i < leftOnes.length; i++) {
            for (int j = 0; j < leftOnes[0].length; j++) {
                if(matrix[i][j] == '1') {
                    leftOnes[i][j] = (j == 0 ? 0 : leftOnes[i][j-1]) + 1;
                } 
            }
        }
    }
}
