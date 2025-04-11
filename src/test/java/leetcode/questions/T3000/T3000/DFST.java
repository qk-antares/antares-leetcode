package leetcode.questions.T3000.T3000;

import java.util.Arrays;

public class DFST {
    /*
     * 2999. 统计强大整数的数目
     * 
     * 数位dp，我更习惯称之为一种记忆化搜索
     * dp(i,lowLimit,highLimit,low,high,limit,s)
     * i: 当前填写的位
     * lowLimit: 当前位是否有下限
     * highLimit: 当前位是否有上限
     * low: 下限数字char[]
     * high: 上限数字char[]
     * limit: 每一位上的最大数字
     * memo: 记忆数组
     * s: 后缀字符串
     * 
     * lowLimit和highLimit这两个状态属于边界状态，所以不需要记忆
     */
    public long numberOfPowerfulInt(long start, long finish, int limit, String s) {
        String low = String.valueOf(start);
        String high = String.valueOf(finish);
        int n = high.length();
        low = "0".repeat(n - low.length()) + low;   //补充前导0
        long[] memo = new long[high.length()];
        Arrays.fill(memo, -1);

        return dfs(0, true, true, low.toCharArray(), high.toCharArray(), limit, memo, s.toCharArray());
    }

    long dfs(int i, boolean lowLimit, boolean highLimit, char[] low, char[] high, int limit, long[] memo, char[] s) {
        //到达最后一位
        if(i == high.length) {
            return 1;
        }
        //已经计算过
        if(!lowLimit && !highLimit && memo[i] != -1) return memo[i];

        int l = lowLimit ? low[i]-'0' : 0;
        int r = highLimit ? high[i]-'0' : 9;

        long ans = 0;
        //当前位是枚举的
        if(i < high.length - s.length) {
            for(int j = l; j <= Math.min(r, limit); j++) {
                ans += dfs(i+1, lowLimit && j == l, highLimit && j == r, low, high, limit, memo, s);
            }
        } else {    //当前位是后缀
            int x = s[i - (high.length - s.length)] - '0';
            if(x >= l && x <= r) {
                ans += dfs(i+1, lowLimit && x == l, highLimit && x == r, low, high, limit, memo, s);
            }

        }

        if(!lowLimit && !highLimit) {
            memo[i] = ans;
        }

        return ans;
    }
}
