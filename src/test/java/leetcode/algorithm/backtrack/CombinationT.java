package leetcode.algorithm.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 回溯
 * 组合
 */
public class CombinationT {
    /**
     * 22. 括号生成 [Medium]
     * 
     * 用i和j记录剩余的左括号和右括号
     * 在任何时候，剩余的左括号不能多余右括号
     */
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        char[] path = new char[2 * n];
        backtrack(0, n, n, path, ans);
        return ans;
    }

    void backtrack(int idx, int i, int j, char[] path, List<String> ans) {
        if (i == 0 && j == 0) {
            ans.add(new String(path));
            return;
        }

        if (i > 0) {
            path[idx] = '(';
            backtrack(idx + 1, i - 1, j, path, ans);
        }

        if (i < j) {
            path[idx] = ')';
            backtrack(idx + 1, i, j - 1, path, ans);
        }
    }
}
