package leetcode.questions.T3000.T2400;

import java.util.Arrays;

public class DFST {
    /*
     * 2376. 统计特殊整数 [Hard]
     * 
     * 记忆化搜索
     * dfs(int i, int mask, boolean isLimit, char[] nChars, boolean isNum) 表示
     * 填写第i位及之后的特殊整数的数量
     * i: 当前填写的位数（从0开始）
     * mask: [0..i-1]这一段已经出现的整数
     * isLimit: 是否受到nChars[i]的限制（即当前填写的数字要<=nChars[i]）
     * isNum: 是否前面都没填过
     * 需要结合记忆化搜索来避免重复计算
     * 由于isLimit为true，或isNum为false属于边界情况，不需要保存这些状态的答案数
     * 只需要根据i和mask来
     */
    public int countSpecialNumbers(int n) {
        char[] nChars = String.valueOf(n).toCharArray();
        int[][] memo = new int[nChars.length][1 << 10];
        // 首先将memo填充0代表还没有搜索到对应的状态
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(0, 0, true, nChars, false, memo);
    }

    int dfs(int i, int mask, boolean isLimit, char[] nChars, boolean isNum, int[][] memo) {
        // 找到了0/1个特殊整数
        if (i == nChars.length) {
            return isNum ? 1 : 0;
        }
        if (!isLimit && isNum && memo[i][mask] != -1)
            return memo[i][mask];

        int ans = 0;
        // 当前位可以不填，注意这里的isLimit会变成false
        if (!isNum) {
            ans += dfs(i + 1, mask, false, nChars, false, memo);
        }

        // 要填就从1开始填，当然也要看受不受到限制
        int max = isLimit ? nChars[i] - '0' : 9;
        // 注意这里j的起始位置要根据isNum
        for (int j = isNum ? 0 : 1; j <= max; j++) {
            // j这个数字之前没填过，当前就可以填j
            if ((mask >> j & 1) == 0) {
                ans += dfs(i + 1, mask ^ (1 << j), isLimit && j == max, nChars, true, memo);
            }
        }

        if (!isLimit && isNum) {
            memo[i][mask] = ans;
        }

        return ans;
    }
}
