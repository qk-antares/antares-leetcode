package leetcode.questions.T1000.T600;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class DFST {
    /*
     * 600. 不含连续1的非负整数
     * 
     * 数位dp
     * dfs(i, pre, limit, n, len, memo)
     * i: 当前填写的位
     * pre: 上一位
     * limit: 当前位是否限制
     * n: n
     * len: n的位数
     * memo: 记忆数组
     * 
     * 区别就是需要记录上一位。此外当前位置在填1之前，需要首先判断能不能填1
     * 如果当前没有限制，或者限制了，但是n的对应位上就是1，则可以填写1
     */
    public int findIntegers(int n) {
        int len = 0;
        for (int i = 1; i <= 32; i++) {
            if (n >> i == 0) {
                len = i;
                break;
            }
        }
        int[][] memo = new int[len][2];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return dfs(0, 0, true, n, len, memo);
    }

    int dfs(int i, int pre, boolean limit, int n, int len, int[][] memo) {
        if (i == len)
            return 1;
        if (!limit && memo[i][pre] != -1)
            return memo[i][pre];

        int ans = 0;
        // 当前位置填0
        ans += dfs(i + 1, 0, limit && (n >> (len - i - 1) & 1) == 0, n, len, memo);
        if (pre == 1)
            return ans;
        // 当前位置填1（前提是可以填）
        if (!limit || (n >> (len - i - 1) & 1) == 1) {
            ans += dfs(i + 1, 1, limit && (n >> (len - i - 1) & 1) == 1, n, len, memo);
        }

        if (!limit)
            memo[i][pre] = ans;
        return ans;
    }

    @Test
    public void test() {
        System.out.println(findIntegers(4));
    }
}
