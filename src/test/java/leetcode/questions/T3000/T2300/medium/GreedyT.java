package leetcode.questions.T3000.T2300.medium;

import org.junit.jupiter.api.Test;

public class GreedyT {
    /*
     * 2207. 字符串中最多数目的子序列
     * 只有两种情况，如果加pattern[0]，那必定是加在最前面（增加的子序列数是text中pattern[1]的个数）；
     * 如果加pattern[1]，那一定加在最后面（同理）
     * 
     * 遍历text，遇到pattern[0]，就可以和后面所有的pattern[1]形成子串
     * 
     * 所以整体的思路是；
     * 1.首先从右往左遍历text：
     * 遇到pattern[1]，cnt1++;
     * 遇到pattern[0]，cnt0++; ans+=cnt1
     * 2. ans += Math.max(cnt0, cnt1)
     */
    public long maximumSubsequenceCount(String text, String pattern) {
        long ans = 0, cnt0 = 0, cnt1 = 0;
        for (int i = text.length() - 1; i >= 0; i--) {
            if(text.charAt(i)==pattern.charAt(0)){
                cnt0++;
                ans += cnt1;
            } 
            if(text.charAt(i)==pattern.charAt(1)){
                cnt1++;
            }
        }
        ans += Math.max(cnt0, cnt1);
        return ans;
    }

    @Test
    public void test(){
        String text = "zzzz";
        String pattern = "zz";
        System.out.println(maximumSubsequenceCount(text, pattern));
    }
}
