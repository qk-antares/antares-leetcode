package leetcode.questions.T3000.T2800;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class DFST {
    /*
     * 2719. 统计整数数目   [Hard]  [Link: T2999]
     * 
     * 数位dp
     * dfs(i, num1, num2, num1Limit, num2Limit, sum, min_sum, max_sum, memo)
     * i: 当前填写的数位
     * num1: 下界数字对应的char[]
     * num2: 上界数字对应的char[]
     * num1Limit: num1是否限制
     * num2Limit: num2是否限制
     * sum: 当前和
     * min_sum: 最小和
     * max_sum: 最大和
     * memo: 记忆数组
     * 
     * num1Limit和num2Limit这两个状态属于边界状态，所以不需要记忆
     * 需要记忆的状态有i和sum
     * 
     * 还有一个小的注意点是，1e9+1和1_000_000_007是不一样的，前者是double，后者是int
     * 在计算时可能存在较大的性能差异
     */
    public int count(String num1, String num2, int min_sum, int max_sum) {
        num1 = "0".repeat(num2.length() - num1.length()) + num1;
        int[][] memo = new int[num2.length()][Math.min(9 * num2.length(), max_sum) + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return dfs(0, num1.toCharArray(), num2.toCharArray(), true, true, 0, min_sum, max_sum, memo);
    }

    int dfs(int i, char[] num1, char[] num2, boolean num1Limit, boolean num2Limit, int sum, int min_sum, int max_sum,
            int[][] memo) {
        // 退出条件
        if (sum > max_sum)
            return 0;
        if (i == num2.length) {
            return sum >= min_sum ? 1 : 0;
        }

        // 搜索过
        if (!num1Limit && !num2Limit && memo[i][sum] != -1)
            return memo[i][sum];

        int low = num1Limit ? num1[i] - '0' : 0;
        int high = num2Limit ? num2[i] - '0' : 9;

        int ans = 0;
        for (int j = low; j <= high; j++) {
            ans = (ans + dfs(i + 1, num1, num2, num1Limit && j == low, num2Limit && j == high, sum + j, min_sum,
                    max_sum, memo)) % 1_000_000_007;
        }

        if (!num1Limit && !num2Limit)
            memo[i][sum] = ans;

        return ans;
    }

    @Test
    public void test() {
        System.out.println(count("123", "999", 1, 20));
    }
}
