package leetcode.algorithm.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 回溯
 * 搜索
 */
public class SearchT {
    /**
     * 79. 单词搜索 [Medium]
     */
    int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    int m = 0;
    int n = 0;

    public boolean exist(char[][] board, String word) {
        m = board.length;
        n = board[0].length;
        boolean[][] vis = new boolean[m][n];
        char[] s = word.toCharArray();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == s[0]) {
                    vis[i][j] = true;
                    if (backtrack(i, j, vis, s, board, 1))
                        return true;
                    vis[i][j] = false;
                }
            }
        }

        return false;
    }

    boolean backtrack(int i, int j, boolean[][] vis, char[] s, char[][] board, int len) {
        if (len == s.length)
            return true;

        for (int[] xy : dir) {
            int x = i + xy[0], y = j + xy[1];
            if (x >= 0 && x < m && y >= 0 && y < n && !vis[x][y] && board[x][y] == s[len]) {
                vis[x][y] = true;
                if (backtrack(x, y, vis, s, board, len + 1))
                    return true;
                vis[x][y] = false;
            }
        }

        return false;
    }

    /**
     * 这个写法更简洁，无需使用vis数组，直接修改board的值来标记是否访问过，回溯时再恢复原值
     * 且回溯的边界条件全部写在backtrack里
     */
    public boolean exist0(char[][] board, String word) {
        m = board.length;
        n = board[0].length;
        char[] s = word.toCharArray();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (backtrack(i, j, s, board, 0))
                    return true;
            }
        }

        return false;
    }

    boolean backtrack(int i, int j, char[] s, char[][] board, int len) {
        if (len == s.length)
            return true;
        if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] != s[len])
            return false;

        char tmp = board[i][j];
        board[i][j] = '.';
        for (int[] xy : dir) {
            if (backtrack(i + xy[0], j + xy[1], s, board, len + 1))
                return true;
        }
        board[i][j] = tmp;

        return false;
    }


    /**
     * 756. 金字塔转换矩阵 [Medium]
     * 
     * 本质是一个dfs，每摆一层，上面的一层就确定了
     * 使用一个List[6][6]来记录摆放了2个之后，上面的情况
     * 
     * 使用一个Set记录无法摆到顶部的字符串，避免重复计算
     */
    @SuppressWarnings("unchecked")
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        List<Character>[][] state = new List[6][6];
        for (List<Character>[] row : state) {
            Arrays.setAll(row, i -> new ArrayList<>());
        }

        for (String a : allowed) {
            state[a.charAt(0) - 'A'][a.charAt(1) - 'A'].add(a.charAt(2));
        }

        char[] bs = bottom.toCharArray();
        int n = bs.length;
        char[][] pyramid = new char[n][];
        pyramid[n - 1] = bs;
        for (int i = n - 2; i >= 0; i--) {
            pyramid[i] = new char[i + 1];
        }

        // 记录无法填到顶部的字符串
        Set<String> vis = new HashSet<>();
        return dfs(n - 2, 0, pyramid, state, vis);
    }

    // i是当前填写行数
    boolean dfs(int i, int j, char[][] pyramid, List<Character>[][] state, Set<String> vis) {
        if (i < 0)
            return true;

        String tmp = new String(pyramid[i], 0, j);
        if (vis.contains(tmp))
            return false;

        if (j > i) {
            vis.add(new String(pyramid[i]));
            return dfs(i - 1, 0, pyramid, state, vis);
        }

        for (Character ch : state[pyramid[i + 1][j] - 'A'][pyramid[i + 1][j + 1] - 'A']) {
            pyramid[i][j] = ch;
            if (dfs(i, j + 1, pyramid, state, vis)) {
                return true;
            }
        }

        return false;
    }
}
