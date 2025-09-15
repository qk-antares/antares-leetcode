package leetcode.bit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class BasicT {
    /*
     * 3370. 仅含置位位的最小整数 [Easy]
     * 
     * Integer.numberOfLeadingZeros(n) 返回 n 的二进制表示中前导零的个数
     */
    public int smallestNumber0(int n) {
        boolean flag = false;
        for (int i = 31; i >= 0; i--) {
            int andVal = 1 << i;
            if (flag && (andVal & n) == 0) {
                n |= andVal;
            } else if (!flag && (andVal & n) > 0) {
                flag = true;
            }
        }
        return n;
    }

    public int smallestNumber(int n) {
        n = 32 - Integer.numberOfLeadingZeros(n);
        return (1 << n) - 1;
    }

    /*
     * 3226. 使两个整数相等的位更改次数 [Easy]
     * 
     * 要求n是k的母集，在该条件的基础下，统计相异的位
     */
    public int minChanges(int n, int k) {
        return (n & k) != k ? -1 : Integer.bitCount(n ^ k);
    }

    /*
     * 1356. 根据数字二进制下 1 的数目排序 [Easy]
     * 
     * 由于自定义排序规则只能用于 引用类型，如Integer[]，因此需要先将 int[] 转为 Integer[]
     * 
     * 或者把bitCount*10_001（范围）加到前面，返回答案的时候再%10_001
     */
    public int[] sortByBits0(int[] arr) {
        // 1. 转为 Integer[]
        Integer[] boxed = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        // 2. 自定义排序
        Arrays.sort(boxed, (a, b) -> {
            int cmp = Integer.bitCount(a) - Integer.bitCount(b);
            return cmp != 0 ? cmp : a - b;
        });
        // 3. 转回 int[]
        for (int i = 0; i < arr.length; i++)
            arr[i] = boxed[i];
        return arr;
    }

    public int[] sortByBits(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            ans[i] = Integer.bitCount(arr[i]) * 10_001 + arr[i];
        }
        Arrays.sort(ans);
        for (int i = 0; i < ans.length; ++i) {
            ans[i] = ans[i] % 10_001;
        }
        return ans;
    }

    /*
     * 461. 汉明距离 [Easy]
     */
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    /*
     * 2220. 转换数字的最少位翻转次数 [Easy]
     */
    public int minBitFlips(int start, int goal) {
        return Integer.bitCount(start ^ goal);
    }

    /*
     * 1342. 将数字变成 0 的操作次数 [Easy]
     */
    public int numberOfSteps(int num) {
        int ans = 0;
        while (num != 0) {
            if ((num & 1) == 1) {
                num -= 1;
            } else {
                num >>= 1;
            }
            ans++;
        }
        return ans;
    }

    /*
     * 476. 数字的补数 [Easy]
     * 
     * 相当于num和全1做异或运算
     */
    public int findComplement(int num) {
        return num ^ ((Integer.highestOneBit(num) << 1) - 1);
    }

    /*
     * 1009. 十进制整数的反码 [Easy]
     */
    public int bitwiseComplement(int n) {
        return n > 0 ? n ^ ((Integer.highestOneBit(n) << 1) - 1) : 1;
    }

    /*
     * 868. 二进制间距 [Easy]
     */
    public int binaryGap(int n) {
        int ans = 0;
        int pre = -1;
        for (int i = 0; i < 32; i++) {
            if (((n >> i) & 1) == 1) {
                if (pre != -1) {
                    ans = Math.max(ans, i - pre);
                }
                pre = i;
            }
        }
        return ans;
    }

    /*
     * 2917. 找出数组中的 K-or 值 [Easy]
     */
    public int findKOr(int[] nums, int k) {
        int[] cnt = new int[32];
        for (int num : nums) {
            for (int i = 0; i < 32; i++) {
                cnt[i] += ((num >> i) & 1);
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (cnt[i] >= k) {
                ans |= (1 << i);
            }
        }
        return ans;
    }

    /*
     * 693. 交替位二进制数 [Easy]
     * 
     * 解法一：逐位比较
     * 
     * 解法二：如果n是交替位二进制数，则n>>1仍然是交替位二进制数，且 n ^ (n >> 1) 形如000..111
     * 设x=n ^ (n >> 1)，则((x+1)&x) == 0
     */
    public boolean hasAlternatingBits0(int n) {
        int bitCount = 32 - Integer.numberOfLeadingZeros(n);
        for (int i = 1; i < bitCount; i++) {
            if (((n >> (i - 1) & 1) ^ (n >> i & 1)) != 1)
                return false;
        }
        return true;
    }

    public boolean hasAlternatingBits(int n) {
        int x = n ^ n >> 1;
        return (x & (x + 1)) == 0;
    }

    /*
     * 2657. 找到两个数组的前缀公共数组 [Medium]
     * 
     * 由于A和B的长度有限，可以用两个long来标记出现的元素
     * 注意点是进行或运算时应该使用1L
     */
    public int[] findThePrefixCommonArray(int[] A, int[] B) {
        long a = 0, b = 0;
        int n = A.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            a |= (1L << A[i]);
            b |= (1L << B[i]);
            ans[i] = Long.bitCount(a & b);
        }
        return ans;
    }

    /*
     * 231. 2 的幂 [Easy]
     */
    public boolean isPowerOfTwo(int n) {
        return n > 0 && Integer.bitCount(n) == 1;
    }

    /*
     * 342. 4的幂 [Easy]
     */
    public boolean isPowerOfFour(int n) {
        return n > 0 && Integer.bitCount(n) == 1 && (Integer.numberOfLeadingZeros(n) - 1) % 2 == 0;
    }

    /*
     * 191. 位1的个数 [Easy]
     */
    public int hammingWeight(int n) {
        return Integer.bitCount(n);
    }

    /*
     * 338. 比特位计数 [Easy]
     * 
     * 规律：（copy，+1）重复，例如：
     * [0], [1], [1,2], [1,2,2,3], [1,2,2,3,2,3,3,4]
     * 
     * 可以预处理，或者根据下标的规律直接构造ans
     */
    static int[] bitCnt = new int[200_002];

    static {
        bitCnt[1] = 1;
        int cur = 2, delta = 1;
        while (cur < 100_001) {
            // copy
            System.arraycopy(bitCnt, cur - delta, bitCnt, cur, delta);
            // +1
            for (int l = cur, r = cur + delta; l < cur + delta; l++, r++) {
                bitCnt[r] = bitCnt[l] + 1;
            }
            delta *= 2;
            cur += delta;
        }
    }

    public int[] countBits0(int n) {
        int[] ans = new int[n + 1];
        System.arraycopy(bitCnt, 0, ans, 0, n + 1);
        return ans;
    }

    public int[] countBits(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            ans[i] = ans[i >> 1] + (i & 1);
        }
        return ans;
    }

    /*
     * 2595. 奇偶位数 [Easy]
     * 
     * 第一种方法是逐位取，时间复杂度O(logn)
     * 第二种方法是利用位掩码 0x55555555（二进制 0101⋯01）
     */
    public int[] evenOddBit0(int n) {
        int[] ans = new int[2];
        int i = 0;
        int high = 32 - Integer.numberOfLeadingZeros(n);
        while (i < high) {
            ans[i % 2] += (1 & n >> i);
            i++;
        }
        return ans;
    }

    public int[] evenOddBit(int n) {
        return new int[] { Integer.bitCount(n & 0x55555555), Integer.bitCount(n & (~0x55555555)) };
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 1935. 可以输入的最大单词数 [Easy]
     * 
     * 使用int的mask标记brokenLetters所代表的26种字母
     * 然后对text进行1次遍历
     * 如果遇到的字母&mask != 0: 一直向后移动直至遇到空格+1
     * 正常遇到了空格，ans+1
     */
    public int canBeTypedWords0(String text, String brokenLetters) {
        String[] words = text.split(" ");
        Set<Character> chars = new HashSet<>();
        for (char ch : brokenLetters.toCharArray()) {
            chars.add(ch);
        }
        int ans = 0;
        for (String word : words) {
            boolean flag = true;
            for (char ch : word.toCharArray()) {
                if (chars.contains(ch)) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                ans++;
        }
        return ans;
    }

    public int canBeTypedWords(String text, String brokenLetters) {
        int ans = 0;
        int mask = 0;
        for (char ch : brokenLetters.toCharArray()) {
            mask |= (1 << (ch - 'a'));
        }

        char[] s = text.toCharArray();
        boolean flag = true;
        for (int i = 0; i < s.length; i++) {
            if (s[i] == ' ')
                ans++;
            else if (((1 << (s[i] - 'a')) & mask) != 0) {
                flag = false;
                while (i < s.length && s[i] != ' ')
                    i++;
            } else {
                flag = true;
            }
        }

        // 对最后一个单词做特殊处理
        if (flag)
            ans++;

        return ans;
    }

    @Test
    void test() {
        findThePrefixCommonArray(
                new int[] { 1, 11, 30, 28, 22, 26, 27, 4, 8, 29, 23, 24, 2, 16, 25, 5, 14, 13, 6, 17, 9, 3, 21, 31, 15,
                        12, 10, 19, 7, 20, 18 },
                new int[] { 28, 30, 17, 23, 13, 4, 7, 16, 19, 26, 12, 1, 15, 6, 9, 20, 27, 24, 21, 10, 8, 11, 29, 3, 31,
                        22, 14, 5, 25, 18, 2 });
    }
}