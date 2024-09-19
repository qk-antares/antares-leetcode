package leetcode.questions.T1000.T100.hard;

import java.util.ArrayDeque;

import org.junit.jupiter.api.Test;

public class MatrixT {
    /**
     * 85. 最大矩形
     * 假设矩形的大小是m×n，统计每个位置左侧（包含自己）有多少个1，
     * 之后便可以转换成n个柱状图统计最大矩形面积，使用单调栈解决
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

        int ans=0;
        for (int j = 0; j < n; j++) {
            int[] left = new int[m];
            int[] right = new int[m];

            ArrayDeque<Integer> mono_stack = new ArrayDeque<Integer>();

            for (int i = 0; i < m; i++) {
                while (!mono_stack.isEmpty() && leftOnes[mono_stack.peek()][j] >= leftOnes[i][j]) {
                    mono_stack.pop();
                }
                left[i] = (mono_stack.isEmpty() ? -1 : mono_stack.peek());
                mono_stack.push(i);
            }

            mono_stack.clear();

            for (int i = m - 1; i >= 0; i--) {
                while (!mono_stack.isEmpty() && leftOnes[mono_stack.peek()][j] >= leftOnes[i][j]) {
                    mono_stack.pop();
                }
                right[i] = (mono_stack.isEmpty() ? m : mono_stack.peek());
                mono_stack.push(i);
            }

            for (int i = 0; i < m; i++) {
                ans = Math.max(ans, (right[i] - left[i] - 1) * leftOnes[i][j]);
            }
        }
        return ans;
    }

    @Test
    void test() {
        char[][] matrix = new char[][]{{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}};
        int ans = maximalRectangle(matrix);
        System.out.println(ans);
    }
}
