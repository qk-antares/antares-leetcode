package leetcode.questions.T3000.T2800.medium;

import java.util.HashSet;

public class MatrixT {
    /*
     * 2711. 对角线上不同值的数量差
     */
    public int[][] differenceOfDistinctValues(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] ans = new int[m][n];
        
        for (int i = 0; i < m; i++) {
            HashSet<Integer> set = new HashSet<>();
            int x = i, y = 0;
            while (x < m && y < n) {
                ans[x][y] += set.size();
                set.add(grid[x][y]);
                x++; y++;
            }
        }

        for(int j = 1; j < n; j++) {
            HashSet<Integer> set = new HashSet<>();
            int x = 0, y = j;
            while (x < m && y < n) {
                ans[x][y] += set.size();
                set.add(grid[x][y]);
                x++; y++;
            }
        }

        for (int i = 0; i < m; i++) {
            HashSet<Integer> set = new HashSet<>();
            int x = i, y = n-1;
            while (x > -1 && y > -1) {
                ans[x][y] = Math.abs(set.size() - ans[x][y]);
                set.add(grid[x][y]);
                x--; y--;
            }
        }

        for(int j = n-2; j > -1; j--) {
            HashSet<Integer> set = new HashSet<>();
            int x = m-1, y = j;
            while (x > -1 && y > -1) {
                ans[x][y] = Math.abs(set.size() - ans[x][y]);
                set.add(grid[x][y]);
                x--; y--;
            }
        }

        return ans;
    }
}
