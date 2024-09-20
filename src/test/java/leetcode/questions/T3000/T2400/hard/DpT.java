package leetcode.questions.T3000.T2400.hard;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class DpT {
    /**
     * 2376. 统计特殊整数
     */
    public int countSpecialNumbers(int n) {
        String nStr = String.valueOf(n);
        int len = nStr.length();

        int ans = 0;
        int prod = 9;
        //这里从低位开始考虑
        for (int i = 0; i < len-1; i++) {
            ans += prod;
            prod *= 9-i;
        }

        ans += dp(0, false, nStr);
        return ans;
    }

    /**
     * 计算以mask为前缀的数字个数。
     * mask的第i位（从右往左，0开始）为1表示数字i已经使用过，prefixSmaller表示前缀是否小于nStr的前缀
     * 
     * 数字从高位开始构建。
     * lowerBound: 如果mask为0，代表当前位是最高位，从1开始构建；否则从0开始构建
     * upperBound: 如果prefixSmaller为true，代表前缀小于nStr，可以构建到9；否则代表前缀等于nStr，可以构建到nStr的当前位
     * 
     * 递归结束的条件是构造的数字位数等于nStr。
     * 
     * 为了防止递归过程的重复计算，用mask * 2 + (prefixSmaller ? 1 : 0)作为key，结果作为value存储到哈希表中
     */
    Map<Integer, Integer> memo = new HashMap<Integer, Integer>();

    public int dp(int mask, boolean prefixSmaller, String nStr) {
        //数字已经构造完整
        if(Integer.bitCount(mask) == nStr.length()) {
            return 1;
        }
        int key = (mask << 1) + (prefixSmaller ? 1 : 0);
        if(memo.containsKey(key)) {
            return memo.get(key);
        }
        int ans = 0;
        //如果当前位是最高位，从1开始构建，否则从0构建
        int lowerBound = mask == 0 ? 1 : 0;
        //如果前缀更小，构建到9，否则构建到nStr的当前位
        int upperBound = prefixSmaller ? 9 : nStr.charAt(Integer.bitCount(mask)) - '0';

        for(int i = lowerBound; i <= upperBound; i++) {
            //如果i没有使用过
            if((mask >> i & 1) == 0) {
                ans += dp(mask | 1 << i, prefixSmaller || i < upperBound, nStr);
            }
        }

        memo.put(key, ans);
        return ans;
    }

    @Test
    void test() {
        countSpecialNumbers(20);
        // dp(0, false, "321");
    }
}
