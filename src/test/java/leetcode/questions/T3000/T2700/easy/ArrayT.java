package leetcode.questions.T3000.T2700.easy;

public class ArrayT {
    /*
     * 2643. 一最多的行
     */
    public int[] rowAndMaximumOnes(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int index = 0, cnt = 0;
        for (int i = 0; i < m; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += mat[i][j];
            }
            if (sum > cnt) {
                cnt = sum;
                index = i;
            }
        }
        return new int[]{index, cnt};
    }
}
