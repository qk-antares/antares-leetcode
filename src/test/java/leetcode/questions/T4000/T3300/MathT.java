package leetcode.questions.T4000.T3300;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MathT {
    /*
     * TODO 3272. 统计好整数的数目
     * 
     * 首先，枚举的是左半部分：
     * 当位数n=2k时，枚举[1..k]这几位
     * 当位数n=2k+1时，枚举[1..k+1]这几位
     * k=(n-1)/2，枚举范围[10^k,10^(k+1))，左闭右开的区间
     * 例如，n=1时，k=0，枚举[1,10); n=4时，k=1，枚举[10,100)
     * 
     * 每当枚举到一个k回文整数时，计算所有的排列数
     * 对于无重复元素的排列数：例如1234，它的排列数是k!（k是位数）
     * 对于带有重复元素的排列数，例如1123，它的排列数是k!/cnt0!*cnt1!*...&cnt9!
     * 对于带有重复元素，且不能有前导0的排列数，例如1102，它的排列数是(k-cnt0)(k-1)!/cnt0!*cnt1!*...&cnt9!
     * 如何理解这个公式呢？
     * 首先，因为不能有前导0，所以在填写第一位时，选择有k-cnt0个；
     * 后面的数字可以填写0，选择有(k-1)!种
     * 其次，对于重复的元素，它们本质上是一样的，所以还要除以各自出现次数的阶乘
     * 
     * 最后，我们要防止重复枚举
     * 例如，n=7，当我们枚举1123(211)时，假设其符合条件，那么后面的1213(121)、2113(112)等都不应该重复枚举
     * 为此，可以将每个已经枚举过的结果按照位的大小排列成一个String，保存到HashSet中
     */
    public long countGoodIntegers(int n, int k) {
        int m = (n - 1) / 2;
        Set<String> set = new HashSet<String>();

        long[] f = new long[n + 1]; // 保存各个数字的阶乘(0-n)
        f[0] = 1;
        for (int i = 1; i <= n; i++) {
            f[i] = i * f[i - 1];
        }

        long ans = 0;
        int skip = n % 2;
        int low = (int) Math.pow(10, m);
        int high = low * 10;
        for (int i = low; i < high; i++) {
            String iStr = String.valueOf(i);
            String numStr = iStr + new StringBuilder(iStr).reverse().substring(skip);
            char[] chArr = numStr.toCharArray();
            Arrays.sort(chArr);

            // 跳过
            if (set.contains(new String(chArr)))
                continue;

            // 这是一个k回文整数
            if (Long.valueOf(numStr) % k == 0) {
                int[] cnt = new int[10];
                for (char ch : chArr) {
                    cnt[ch - '0']++;
                }
                int repeat = 1;
                for (int c : cnt) {
                    repeat *= f[c];
                }
                ans += (n - cnt[0]) * f[n - 1] / repeat;
                set.add(new String(chArr));
            }
        }

        return ans;
    }
}
