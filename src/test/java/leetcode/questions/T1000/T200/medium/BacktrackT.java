package leetcode.questions.T1000.T200.medium;

import java.util.ArrayList;
import java.util.List;

public class BacktrackT {
    /*
     * 131. 分割回文串
     * 这是一道dp+回溯的题，个人认为难度为难
     * 
     * 首先需要对s进行预处理，通过动态规划判断s的所有子串是否为回文串
     * 用dp[i][j]表示s[i..j]是否为回文串，显然i<=j
     * dp[i][j] = true 当 i>=j
     * dp[i][j] = dp[i+1][j-1] && s[i] == s[j] 依赖左下方的状态
     * 
     * 之后进行回溯，假设s[0...i-1]我们已经分好了，当前到了第i个字符，则我们从j=i开始，依次判断s[i..j]是否是回文串
     * 如果是，则加入分割结果中，进行下一层回溯
     * 之后删除这个分割结果
     */

    boolean[][] dp;
    List<List<String>> ans = new ArrayList<>();

    public List<List<String>> partition(String s) {
        initDp(s);        
        backtrackPartition(s, 0, new ArrayList<>());
        return ans;
    }

    public void initDp(String s) {
        int n = s.length();
        dp = new boolean[n][n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                if(i >= j) {    
                    dp[i][j] = true;
                } else {
                    dp[i][j] = dp[i + 1][j - 1] && (s.charAt(i) == s.charAt(j));
                }
            }
        }
    }

    public void backtrackPartition(String s, int start, List<String> path) {
        if (start == s.length()) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < s.length(); i++) {
            if (dp[start][i]) {
                path.add(s.substring(start, i + 1));
                backtrackPartition(s, i + 1, path);
                path.remove(path.size() - 1);
            }
        }
    }

}
