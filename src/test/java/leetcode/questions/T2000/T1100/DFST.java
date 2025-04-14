package leetcode.questions.T2000.T1100;

import java.util.Arrays;

public class DFST {
    /*
     * 1012. 至少有 1 位重复的数字 [Hard]   [Link 2376. 统计特殊整数]
     * 
     * 逆向思维，具有至少1位重复数字的正整数个数=n-没有重复的正整数个数
     * dfs(i,mask,isLimit,isNum)用来求没有重复的正整数个数
     * 使用mask来表示前面已经填过的数字，mask最大为1 111 111 111 （分别表示0到9）
     * 此题需要对前导0的情况进行处理，这是因为前导0中的0不参与计算
     * 
     * 这道题的注意点是转换成【没有重复的正整数个数】，经转换等价于T2376
     */
    public int numDupDigitsAtMostN(int n) {
        char[] s = String.valueOf(n).toCharArray();
        int[][] memo = new int[s.length][1 << 10];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return n - dfs(0, 0, false, true, s, memo);
    }

    int dfs(int i, int mask, boolean isNum, boolean isLimit, char[] s, int[][] memo) {
        // 填完了
        if (i == s.length) {
            return isNum ? 1 : 0;
        }

        // 填过了
        if (isNum && !isLimit && memo[i][mask] != -1)
            return memo[i][mask];

        int ans = 0;
        // 不填（注意这里isLimit会变成false）
        if (!isNum)
            ans += dfs(i + 1, 0, false, false, s, memo);

        // 填（根据之前isNum，起始是不一样的）
        int low = isNum ? 0 : 1;
        int high = isLimit ? s[i] - '0' : 9;
        for (int j = low; j <= high; j++) {
            // 这个数字之前没填过
            if ((mask >> j & 1) == 0)
                ans += dfs(i + 1, mask ^ 1 << j, true, isLimit && (j == high), s, memo);
        }

        if (isNum && !isLimit)
            memo[i][mask] = ans;
        return ans;
    }

}
