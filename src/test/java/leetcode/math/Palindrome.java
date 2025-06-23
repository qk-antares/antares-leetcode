package leetcode.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Palindrome {
    /*
     * 9. 回文数 [Easy]
     * 
     * 朴素的解法是将x反转得到rev，然后判断rev和x是否相等
     * 但实际上只需要判断x的前一半和后一半是否相同
     * 这又有两种情况：奇数长度和偶数长度
     * 用rev来记录后一半反转后的结果
     * 循环的条件是rev < x / 10
     * 如果x%10==0，则直接return false
     */
    public boolean isPalindrome0(int x) {
        if (x < 0)
            return false;

        int tmpX = x;
        int rev = 0;
        while (x > 0) {
            rev = rev * 10 + x % 10;
            x /= 10;
        }
        return rev == tmpX;
    }

    public boolean isPalindrome(int x) {
        if (x < 0 || x > 0 && x % 10 == 0)
            return false;

        int rev = 0;
        while (rev < x / 10) {
            rev = rev * 10 + x % 10;
            x /= 10;
        }
        return rev == x || rev == x / 10;
    }

    /*
     * 2081. k 镜像数字的和
     */
    static int N = 30;
    @SuppressWarnings("unchecked")
    static List<Long>[] ans = new List[10];
    static boolean initialized = false;

    void init() {
        if (initialized)
            return;
        initialized = true;
        Arrays.setAll(ans, i -> new ArrayList<>());
        for (int base = 1;; base *= 10) {
            // 首先是奇回文数
            for (int i = base; i < base * 10; i++) {
                long x = i;
                // 构造x的奇回文数
                int tmp = i / 10;
                while (tmp > 0) {
                    x = x * 10 + tmp % 10;
                    tmp /= 10;
                }
                // 将x添加到ans中，并判断是否达到30个了
                if (doPalindrome(x))
                    return;
            }

            // 接下来是偶回文数
            for (int i = base; i < base * 10; i++) {
                long x = i;
                int tmp = i;
                while (tmp > 0) {
                    x = x * 10 + tmp % 10;
                    tmp /= 10;
                }
                if (doPalindrome(x))
                    return;
            }
        }
    }

    boolean doPalindrome(long x) {
        boolean done = true;
        for (int k = 2; k <= 9; k++) {
            if (ans[k].size() < N && isKPalindrome(x, k))
                ans[k].add(x);
            // 只要有一个k的List没有满足条件，就置为false
            if (ans[k].size() < N)
                done = false;
        }

        if (!done)
            return false;

        // 所有的k都求完前50个
        for (int k = 2; k <= 9; k++) {
            List<Long> s = ans[k];
            for (int i = 1; i < N; i++) {
                s.set(i, s.get(i) + s.get(i - 1));
            }
        }

        return true;
    }

    // 由于2<=k<=9，所有每一位枚举的上界是k
    // 1位数：(1),...,k-1
    // 2位数：(1)1,...,(k-1)k-1
    // 3位数：(11)1,(12)1,...,(k-1k-1)k-1
    // 我们只用枚举k进制下的前n/2小的数字，然后按照两者策略进行翻转：
    // 策略1：末尾作为中心（奇数长度回文数）
    // 策略2：对称翻转（偶数长度回文数）
    public long kMirror(int k, int n) {
        if (!initialized)
            init();
        return ans[k].get(n - 1);
    }

    // 检查一个正整数在k进制下是否是回文数
    boolean isKPalindrome(long x, int k) {
        if (x % k == 0)
            return false;
        long rev = 0;
        while (rev < x / k) {
            rev = rev * k + x % k;
            x /= k;
        }
        return rev == x || rev == x / k;
    }

    @Test
    public void test() {
        // 测试回文数
        System.out.println(isPalindrome(121)); // true

    }
}
