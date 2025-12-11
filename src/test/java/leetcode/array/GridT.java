package leetcode.array;

import java.util.Arrays;

/**
 * 二维数组Grid
 */
public class GridT {
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
}
