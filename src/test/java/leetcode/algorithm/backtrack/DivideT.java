package leetcode.algorithm.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 回溯
 * 分割
 */
public class DivideT {
    /**
     * 131. 分割回文串 [Medium]
     * 
     * 首先构造dp判断s的子串是否是回文串
     * i>=j：dp[i][j] = true
     * dp[i][j] = s[i]==s[j] && dp[i+1][j-1]
     * dp依赖下一行，所以构造dp的时候从下往上构造
     * 
     * 之后进行回溯，假设s[0...i-1]我们已经分好了，当前到了第i个字符，则我们从j=i开始，依次判断s[i..j]是否是回文串
     * 如果是，则加入分割结果中，进行下一层回溯
     * 之后删除这个分割结果
     */

    public List<List<String>> partition(String s) {
        char[] ss = s.toCharArray();
        int n = ss.length;
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (i >= j)
                    dp[i][j] = true;
                else
                    dp[i][j] = ss[i] == ss[j] && dp[i + 1][j - 1];
            }
        }

        List<List<String>> ans = new ArrayList<>();
        List<String> path = new ArrayList<>();
        backtrack(0, s, dp, path, ans);
        return ans;
    }

    void backtrack(int i, String s, boolean[][] dp, List<String> path, List<List<String>> ans) {
        if (i == s.length()) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int j = i; j < s.length(); j++) {
            if (dp[i][j]) {
                path.add(s.substring(i, j + 1));
                backtrack(j + 1, s, dp, path, ans);
                path.removeLast();
            }
        }
    }
}
