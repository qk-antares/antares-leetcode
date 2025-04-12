package leetcode.questions.T1000.T300;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class DFST {
    /*
     * 233. 数字 1 的个数   [Hard]  [Link: 2999]
     * 
     * 数位dp
     * dfs(i, cnt1, isLimit, s, memo)
     * i: 当前填写的位
     * cnt1: [0..i]中1的个数
     * isLimit: 当前位是否限制
     * s: 数字char[]
     * memo: 记忆数组
     * 
     * 需要记忆的状态有i和cnt1
     * 这道题的特点就是需要记录前面已经填写的1 cnt1，然后在填写完成时返回cnt1
     */
    public int countDigitOne(int n) {
        char[] s = String.valueOf(n).toCharArray();
        int[][] memo = new int[s.length][s.length];
        for(int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return dfs(0, 0, true, s, memo);
    }

    int dfs(int i, int cnt1, boolean isLimit, char[] s, int[][] memo) {
        //填到了最后一位
        if(i == s.length) return cnt1;
        if(!isLimit && memo[i][cnt1] != -1) return memo[i][cnt1];

        int low = 0;
        int high = isLimit ? s[i] - '0' : 9;
        int ans = 0;
        for(int j = low; j <= high; j++) {
            ans += dfs(i+1, cnt1 + (j == 1 ? 1 : 0), isLimit && j == high, s, memo);
        }

        if(!isLimit) memo[i][cnt1] = ans;
        return ans;
    }

    @Test
    public void test() {
        System.out.println(countDigitOne(13));
    }
}
