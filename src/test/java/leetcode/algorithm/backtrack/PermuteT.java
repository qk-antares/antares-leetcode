package leetcode.algorithm.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 回溯
 * 排列
 */
public class PermuteT {
    /**
     * 46. 全排列   [Medium]
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] vis = new boolean[nums.length];
        backtrack(nums, vis, path, ans);

        return ans;
    }

    void backtrack(int[] nums, boolean[] vis, List<Integer> path, List<List<Integer>> ans) {
        if (path.size() == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (vis[i])
                continue;

            path.add(nums[i]);
            vis[i] = true;
            backtrack(nums, vis, path, ans);
            path.removeLast();
            vis[i] = false;
        }
    }

    /**
     * 51. N 皇后 [Hard]
     * 
     * y, x+y, x-y3个值来标记哪些位置被皇后占据了
     * 可以用boolean而不是Set
     */
    public List<List<String>> solveNQueens(int n) {
        boolean[] col = new boolean[n];
        boolean[] plus = new boolean[2 * n];
        boolean[] minus = new boolean[2 * n];

        List<List<String>> ans = new ArrayList<>();
        List<String> path = new ArrayList<>();
        backtrack(0, n, path, ans, col, plus, minus);

        return ans;
    }

    void backtrack(int i, int n, List<String> path, List<List<String>> ans, boolean[] col, boolean[] plus,
            boolean[] minus) {
        if (i == n) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int j = 0; j < n; j++) {
            if (!col[j] && !plus[i + j] && !minus[i - j + n]) {
                char[] ss = new char[n];
                Arrays.fill(ss, '.');
                ss[j] = 'Q';
                path.add(new String(ss));
                col[j] = true;
                plus[i + j] = true;
                minus[i - j + n] = true;
                backtrack(i + 1, n, path, ans, col, plus, minus);
                path.removeLast();
                col[j] = false;
                plus[i + j] = false;
                minus[i - j + n] = false;
            }
        }
    }
}
