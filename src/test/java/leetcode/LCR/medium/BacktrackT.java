package leetcode.LCR.medium;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BacktrackT {
    /*
     * LCR 086. 分割回文串
     * 
     * 用dp[i][j]表示s[i..j]是否为回文串，显然i<=j
     * 当i>=j时，dp[i][j] = true
     * 当i<j时，dp[i][j] = dp[i+1][j-1] && s[i] == s[j] 
     * 可以看到的是，dp依赖的是左下方的状态。所以在构造时应该《按列构造》
     * 先构造dp[0...len][0]，然后是dp[0...len][1]
     * 
     */
    boolean[][] dp;
    List<List<String>> ans = new ArrayList<>();

    public String[][] partition(String s) {
        int len = s.length();
        dp = new boolean[len][len];
        for (int j = 0; j < len; j++) {
            for (int i = 0; i < len; i++) {
                if(i >= j) {
                    dp[i][j] = true;
                } else {
                    dp[i][j] = dp[i + 1][j - 1] && (s.charAt(i) == s.charAt(j));
                }
            }
        }
        backtrack(s, new ArrayList<String>(), 0);

        // 创建一个二维数组，行数为外层List的大小
        String[][] array = new String[ans.size()][];

        // 遍历外层List，为每个子List分配对应的数组
        for (int i = 0; i < ans.size(); i++) {
            // 获取每个内层的List，转换为数组
            array[i] = ans.get(i).toArray(new String[0]);
        }

        return array;
    }

    public void backtrack(String s, List<String> path, int start) {
        if(start == s.length()) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < s.length(); i++) {
            //如果是回文串
            if(dp[start][i]) {
                path.add(s.substring(start, i + 1));
                backtrack(s, path, i+1);
                path.removeLast();
            }
        }
    }

    @Test
    public void test() {
        String s = "aab";
        String[][] res = partition(s);
        for (String[] strings : res) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }
}
