package leetcode.algorithm.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 回溯
 * 基础题
 */
public class BasicT {
    /**
     * 17. 电话号码的字母组合 [Medium]
     */
    char[][] dict = { {}, {}, { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' }, { 'j', 'k', 'l' },
            { 'm', 'n', 'o' }, { 'p', 'q', 'r', 's' }, { 't', 'u', 'v' }, { 'w', 'x', 'y', 'z' } };

    public List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();
        char[] s = digits.toCharArray();
        char[] path = new char[s.length];

        backtrack(0, path, s, ans);
        return ans;
    }

    void backtrack(int idx, char[] path, char[] s, List<String> ans) {
        if (idx == path.length) {
            ans.add(new String(path));
            return;
        }

        for (char ch : dict[s[idx] - '0']) {
            path[idx] = ch;
            backtrack(idx + 1, path, s, ans);
        }
    }
}
