package leetcode.questions.T1000.T200.medium;

public class MatrixT {
    /**
     * 130. 被围绕的区域
     */
    boolean[][] mask;
    int m;
    int n;

    public void solve(char[][] board) {
        m = board.length;
        n = board[0].length;
        mask = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == 0 || j == 0 || i == m - 1 || j == n - 1) && board[i][j] == 'O') {
                    dfs(board, i, j);
                }    
            }
        }

        for (int i = 0; i < m; i++) {   
            for (int j = 0; j < n; j++) {
                if (!mask[i][j] && board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    public void dfs(char[][] board, int i, int j) {
        if(i < 0 || j < 0 || i >= m || j >= n || mask[i][j] || board[i][j] == 'X') {
            return;
        }
        mask[i][j] = true;
        dfs(board, i + 1, j);
        dfs(board, i - 1, j);
        dfs(board, i, j + 1);
        dfs(board, i, j - 1);

    }
}
