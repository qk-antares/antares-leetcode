package leetcode.questions.T2000.T1400;

public class BinarySearch {
    /*
     * 1351. 统计有序矩阵中的负数
     * 想到的简单方法就是对隔行或者各列进行遍历，每一遍遍历使用二分找到第一个负数的位置
     */
    public int countNegatives(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int ans = 0;
        for (int i = 0; i < m; i++) {
            ans += n - findNegIndex(grid[i]);
        }
        return ans;
    }

    public int findNegIndex(int[] row) {
        int l = 0, r = row.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (row[mid] >= 0) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }
}
