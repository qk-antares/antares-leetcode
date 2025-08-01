package leetcode.bit;

import java.util.Arrays;

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
}