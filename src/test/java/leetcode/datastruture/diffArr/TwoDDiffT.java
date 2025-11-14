package leetcode.datastruture.diffArr;

/**
 * 二维差分
 */
public class TwoDDiffT {
    /*
     * 2132. 用邮票贴满网格图 [Hard]
     * 
     * 遍历grid的每个格子，能放邮票就放，最后检查有没有没被贴到的格子
     * Q1:怎么判断某个格子能不能放邮票？
     * 求grid的前缀和，根据前缀和可以判断某个区域的总和，该总和=0则可以贴邮票
     * Q2:怎么记录格子被贴邮票的情况？
     * 使用二维差分，最后根据差分数组还原出每个格子被贴邮票的次数
     * 
     * 二维前缀和
     * s[i+1][j+1]表示grid[0][0]到grid[i][j]的总和
     * s[i+1][j+1] = s[i+1][j] + s[i][j+1] - s[i][j] + grid[i][j];
     * 则左上(x1,y1)到右下(x2,y2)这段的总和是
     * s[x2+1][y2+1]-s[x1][y2+1]-s[x2+1][y2]+s[x1][y1]
     * 
     * 二维差分
     * 左上(x1,y1)到右下(x2,y2)这块+1，等价于
     * d[x1][y1]++;d[x1][y2+1]--;d[x2+1][y1]--;d[x2+1][y2+1]++
     * 
     * 注意点：
     * 由于要根据差分矩阵直接还原原矩阵，为了避免边界问题，差分矩阵d的大小要比grid大2
     * 这其实在1维差分中也是一样的
     * nums[i] = (d[i+1] += d[i])
     */
    public boolean possibleToStamp(int[][] grid, int stampHeight, int stampWidth) {
        int m = grid.length, n = grid[0].length;

        int[][] s = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                s[i + 1][j + 1] = s[i + 1][j] + s[i][j + 1] - s[i][j] + grid[i][j];
            }
        }

        int[][] d = new int[m + 2][n + 2];
        for (int i2 = stampHeight; i2 <= m; i2++) {
            for (int j2 = stampWidth; j2 <= n; j2++) {
                int i1 = i2 - stampHeight + 1, j1 = j2 - stampWidth + 1;
                if (s[i2][j2] - s[i1 - 1][j2] - s[i2][j1 - 1] + s[i1 - 1][j1 - 1] == 0) {
                    d[i1][j1]++;
                    d[i1][j2 + 1]--;
                    d[i2 + 1][j1]--;
                    d[i2 + 1][j2 + 1]++;
                }
            }
        }

        // 还原
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                d[i + 1][j + 1] += d[i + 1][j] + d[i][j + 1] - d[i][j];
                if (grid[i][j] == 0 && d[i + 1][j + 1] == 0)
                    return false;
            }
        }

        return true;
    }

    /*
     * 2536. 子矩阵元素加 1 [Medium]
     * 
     * 跟上面一样，由于要从差分矩阵直接构造原矩阵
     * 所以差分矩阵的大小要比原矩阵大2，且构造的时候idx+1
     */
    public int[][] rangeAddQueries(int n, int[][] queries) {
        // 二维差分
        int[][] diff = new int[n + 2][n + 2];
        for (int[] q : queries) {
            int r1 = q[0], c1 = q[1], r2 = q[2], c2 = q[3];
            diff[r1 + 1][c1 + 1]++;
            diff[r1 + 1][c2 + 2]--;
            diff[r2 + 2][c1 + 1]--;
            diff[r2 + 2][c2 + 2]++;
        }

        // 原地计算 diff 的二维前缀和，然后填入答案
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                diff[i + 1][j + 1] += diff[i + 1][j] + diff[i][j + 1] - diff[i][j];
                ans[i][j] = diff[i + 1][j + 1];
            }
        }
        return ans;
    }
}
