package leetcode.gridgraph;

/**
 * 网格图的深度优先遍历
 */
public class DFST {
    /**
     * 200. 岛屿数量 [Medium]
     * 
     * dfs+visit数组
     * visit过的单元格不会再访问，标记为N
     * 
     * 或者dfs上来先对i和j的合法性进行判断，这样就不用在for循环里判断边界
     */
    public int numIslands(char[][] grid) {
        int ans = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    dfs(grid, i, j);
                }
            }
        }

        return ans;
    }

    void dfs(char[][] grid, int i, int j) {
        grid[i][j] = 'N';
        for (int d = -1; d <= 1; d += 2) {
            int di = d + i;
            if (di >= 0 && di < grid.length && grid[di][j] == '1')
                dfs(grid, di, j);
            int dj = d + j;
            if (dj >= 0 && dj < grid[0].length && grid[i][dj] == '1')
                dfs(grid, i, dj);
        }
    }

    /**
     * 130. 被围绕的区域 [Medium]
     * 
     * 换个思路，从边界的O方块开始dfs，将不符合题意的O标记出来，则剩下的就是合题的
     */
    public void solve(char[][] board) {
        int m = board.length, n = board[0].length;
        boolean[][] mask = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            if (!mask[i][0] && board[i][0] == 'O')
                dfs(board, mask, i, 0);
            if (!mask[i][n - 1] && board[i][n - 1] == 'O')
                dfs(board, mask, i, n - 1);
        }

        for (int j = 0; j < n; j++) {
            if (!mask[0][j] && board[0][j] == 'O')
                dfs(board, mask, 0, j);
            if (!mask[m - 1][j] && board[m - 1][j] == 'O')
                dfs(board, mask, m - 1, j);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O' && !mask[i][j])
                    board[i][j] = 'X';
            }
        }

    }

    void dfs(char[][] board, boolean[][] mask, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || mask[i][j] || board[i][j] != 'O')
            return;

        mask[i][j] = true;
        dfs(board, mask, i - 1, j);
        dfs(board, mask, i + 1, j);
        dfs(board, mask, i, j - 1);
        dfs(board, mask, i, j + 1);
    }
}