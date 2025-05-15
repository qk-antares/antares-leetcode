package leetcode.dp;

import java.util.Arrays;

public class DigitDpT {
    /*
     * 233. 数字 1 的个数 [Hard] [Link: 2999. 统计强大整数的数目]
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
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return dfs(0, 0, true, s, memo);
    }

    int dfs(int i, int cnt1, boolean isLimit, char[] s, int[][] memo) {
        // 填到了最后一位
        if (i == s.length)
            return cnt1;
        if (!isLimit && memo[i][cnt1] != -1)
            return memo[i][cnt1];

        int low = 0;
        int high = isLimit ? s[i] - '0' : 9;
        int ans = 0;
        for (int j = low; j <= high; j++) {
            ans += dfs(i + 1, cnt1 + (j == 1 ? 1 : 0), isLimit && j == high, s, memo);
        }

        if (!isLimit)
            memo[i][cnt1] = ans;
        return ans;
    }

    /*
     * 面试题 17.06. 2出现的次数
     * 
     * dfs(i,cnt2,isLimit)
     * i代表当前填写的数位
     * cnt2代表前面已经填写的2的数量
     * isLimit表示是否受到n的限制
     * s
     * memo
     */
    public int numberOf2sInRange(int n) {
        char[] s = String.valueOf(n).toCharArray();
        int[][] memo = new int[s.length][s.length];
        for (int[] row : memo)
            Arrays.fill(row, -1);
        return dfs0(0, 0, true, s, memo);
    }

    public int dfs0(int i, int cnt2, boolean isLimit, char[] s, int[][] memo) {
        // 填写完了
        if (i == s.length)
            return cnt2;
        // 前面搜索过
        if (!isLimit && memo[i][cnt2] != -1)
            return memo[i][cnt2];

        int ans = 0;
        int max = isLimit ? s[i] - '0' : 9;
        for (int j = 0; j <= max; j++) {
            ans += dfs(i + 1, cnt2 + (j == 2 ? 1 : 0), isLimit && j == max, s, memo);
        }

        if (!isLimit)
            memo[i][cnt2] = ans;

        return ans;
    }

    /*
     * 552. 学生出勤记录 II [Hard]  <Star>
     * 
     * 类似数位dp
     * dfs(i,pre,a)
     * i表示当前填写的数位
     * preL表示前面的连续L个数（至多2个，3种状态）
     * a表示前面A的个数（至多1个，2种状态）
     * 
     * 由于本题的范围比较小，所以可以将memo设置为static，这样在不同的测试用例之间避免了重复计算，提高通过效率
     * 需要注意的是，此时dfs的参数需要从n开始递减
     */
    public int checkRecord0(int n) {
        int[][][] memo = new int[n][3][2];
        for (int[][] m : memo) {
            for (int[] row : m)
                Arrays.fill(row, -1);
        }

        return dfs0(0, 0, 0, memo, n);
    }

    public int dfs0(int i, int preL, int a, int[][][] memo, int n) {
        // 填到了最后一个数位
        if (i == n)
            return 1;
        // 当前状态已经计算过
        if (memo[i][preL][a] > -1)
            return memo[i][preL][a];

        int ans = 0;
        // 当前位填写A
        ans = (ans + dfs0(i + 1, 0, a, memo, n)) % MOD;
        // 当前位填写L（前提是前面连续的L<2）
        if (preL < 2)
            ans = (ans + dfs0(i + 1, preL + 1, a, memo, n)) % MOD;
        // 当前位填写a（前提是前面的a<1）
        if (a < 1)
            ans = (ans + dfs0(i + 1, 0, a + 1, memo, n)) % MOD;

        memo[i][preL][a] = ans;
        return ans;
    }

    static final int MOD = 1_000_000_007;
    static final int N = 100_001;
    static final int[][][] memo = new int[N][3][2];
    public int checkRecord(int n) {
        return dfs(n,0,0);
    }

    public int dfs(int i, int preL, int a) {
        //填到了最后一个数位
        if(i == 0) return 1;
        //当前状态已经计算过
        if(memo[i][preL][a] > 0) return memo[i][preL][a];

        int ans = 0;
        //当前位填写A
        ans = (ans + dfs(i-1, 0, a)) % MOD;
        //当前位填写L（前提是前面连续的L<2）
        if(preL < 2) ans = (ans + dfs(i-1, preL+1, a)) % MOD;
        //当前位填写a（前提是前面的a<1）
        if(a < 1) ans = (ans + dfs(i-1, 0, a+1)) % MOD;

        memo[i][preL][a] = ans;
        return ans;
    }
}
