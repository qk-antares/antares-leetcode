package leetcode.questions.T1000.T300;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class DFST {
    /*
     * 233. 数字 1 的个数
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
