package leetcode.questions.T1000.T100;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BacktrackT {
    /*
     * 77. 组合 [Medium]
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        boolean[] visited = new boolean[n];

        Deque<Integer> path = new ArrayDeque<>();
        backtrackCombine(ans, visited, path, n, k, 0);
        return ans;
    }

    public void backtrackCombine(List<List<Integer>> ans, boolean[] visited, Deque<Integer> path, int n, int k,
            int start) {
        if (path.size() == k) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < n; i++) {
            path.addLast(i + 1);
            visited[i] = true;
            backtrackCombine(ans, visited, path, n, k, i + 1);
            visited[i] = false;
            path.removeLast();
        }
    }

    /*
     * 93. 复原 IP 地址 [Medium]
     */
    public List<String> restoreIpAddresses(String s) {
        ArrayList<String> ans = new ArrayList<String>();
        int[] segments = new int[4];

        if (s.length() > 12) {
            return ans;
        }

        backtrackIp(s, ans, segments, 0, 0);
        return ans;
    }

    public void backtrackIp(String s, List<String> ans, int[] segments, int segId, int startIndex) {
        // 遍历完
        if (segId == 4) {
            if (startIndex == s.length()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < segments.length; i++) {
                    sb.append(segments[i]);
                    if (i != 3) {
                        sb.append('.');
                    }
                }
                ans.add(sb.toString());
            }
            return;
        }

        // 遍历完了，但没凑够4段
        if (startIndex == s.length()) {
            return;
        }

        // 前导0
        if (s.charAt(startIndex) == '0') {
            segments[segId] = 0;
            backtrackIp(s, ans, segments, segId + 1, startIndex + 1);
            return;
        }

        // 遍历所有可能
        int cur = 0;
        for (int i = 0; i < 3 && startIndex + i < s.length(); i++) {
            cur = cur * 10 + (s.charAt(startIndex + i) - '0');
            if (cur <= 255) {
                segments[segId] = cur;
                backtrackIp(s, ans, segments, segId + 1, startIndex + i + 1);
            } else {
                continue;
            }
        }
    }

    /*
     * 90. 子集 II [Medium]
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> curPath = new ArrayList<Integer>();
        Arrays.sort(nums);

        dfs(ans, curPath, false, nums, 0);
        return ans;
    }

    public void dfs(List<List<Integer>> ans, ArrayList<Integer> curPath, boolean choosePre, int[] nums, int curIndex) {
        if (curIndex == nums.length) {
            ans.add(new ArrayList<>(curPath));
            return;
        }

        // 不选择的这层dfs必须位于前面
        dfs(ans, curPath, false, nums, curIndex + 1);

        if (curIndex > 0 && !choosePre && nums[curIndex] == nums[curIndex - 1]) {
            return;
        }

        curPath.add(nums[curIndex]);
        dfs(ans, curPath, true, nums, curIndex + 1);
        curPath.remove(curPath.size() - 1);
    }

    /*
     * ========================== 分割线 ==========================
     */

    private final boolean[][] lines = new boolean[9][9];
    private final boolean[][] columns = new boolean[9][9];
    private final boolean[][][] blocks = new boolean[3][3][9];
    private final List<int[]> spaces = new ArrayList<>();
    private boolean valid = false;

    /*
     * 37. 解数独 [Hard]
     */
    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    spaces.add(new int[] { i, j });
                } else {
                    int num = board[i][j] - '1';
                    lines[i][num] = true;
                    columns[j][num] = true;
                    blocks[i / 3][j / 3][num] = true;
                }
            }
        }

        backtrack(board, 0);
    }

    public void backtrack(char[][] board, int index) {
        if (index == spaces.size()) {
            valid = true;
            return;
        }

        int[] p = spaces.get(index);

        for (int i = 0; i < 9 && !valid; i++) {
            if (!lines[p[0]][i] && !columns[p[1]][i] && !blocks[p[0] / 3][p[1] / 3][i]) {
                board[p[0]][p[1]] = (char) (i + '1');
                lines[p[0]][i] = columns[p[1]][i] = blocks[p[0] / 3][p[1] / 3][i] = true;
                backtrack(board, index + 1);
                lines[p[0]][i] = columns[p[1]][i] = blocks[p[0] / 3][p[1] / 3][i] = false;
            }
        }
    }

    /*
     * 52. N 皇后 II [Hard]
     */
    int ans = 0;

    public int totalNQueens(int n) {
        boolean[] add = new boolean[2 * n - 1];
        boolean[] minus = new boolean[2 * n - 1];
        boolean[] r = new boolean[n];
        boolean[] c = new boolean[n];

        int curR = 0;
        backtrack(add, minus, r, c, n, curR);
        return ans;
    }

    public void backtrack(boolean[] add, boolean[] minus, boolean[] r, boolean[] c, int n, int curR) {
        if (curR == n) {
            ans++;
            return;
        }

        // 寻找放置的列
        int x, y;
        for (int i = 0; i < n; i++) {
            x = curR + i;
            y = curR - i + n;
            if (!add[x] && !minus[y] && !r[curR] && !c[i]) {
                add[x] = true;
                minus[y] = true;
                r[curR] = true;
                c[i] = true;

                backtrack(add, minus, r, c, n, curR + 1);

                add[x] = false;
                minus[y] = false;
                r[curR] = false;
                c[i] = false;
            }
        }
    }

    @Test
    void test() {
        // permuteUnique(new int[]{1,1,2});
        // restoreIpAddresses("0000");
        subsetsWithDup(new int[] { 1, 2, 2 });

        // char[][] test = new char[][]{
        // new char[]{'5','3','.','.','7','.','.','.','.'},
        // new char[]{'6','.','.','1','9','5','.','.','.'},
        // new char[]{'.','9','8','.','.','.','.','6','.'},
        // new char[]{'8','.','.','.','6','.','.','.','3'},
        // new char[]{'4','.','.','8','.','3','.','.','1'},
        // new char[]{'7','.','.','.','2','.','.','.','6'},
        // new char[]{'.','6','.','.','.','.','2','8','.'},
        // new char[]{'.','.','.','4','1','9','.','.','5'},
        // new char[]{'.','.','.','.','8','.','.','7','9'}
        // };
        // solveSudoku(test);

        totalNQueens(4);
    }

}
