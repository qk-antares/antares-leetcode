package leetcode.questions.T2000.T1500;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class DoublePointerT {
    /*
     * TODO 1498. 满足条件的子序列数目
     * 
     * 然后对nums从前往后进行遍历，右指针在初始时指向nums的末尾（n-1）
     * 由于随着nums的遍历，遇到的元素越来越大，所以右指针是一直向左移动的，而不用每次重新置到n-1
     * 这道题要注意的点是，Math.pow(2,r-i)这里可能是一个非常大的值，甚至超出了long的范围，
     * 所以有必要提前将2的若干次方%MOD的结果保存到一个数组中
     */
    public int numSubseq(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length, r = n - 1;
        int MOD = 1000000007;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            while (r >= i && nums[r] + nums[i] > target)
                r--;
            if (r >= i) {
                ans += Math.pow(2, r - i);
                ans %= MOD;
            }
        }
        return (int) ans;
    }

    @Test
    public void test() {
        int[] nums = { 9, 25, 9, 28, 24, 12, 17, 8, 28, 7, 21, 25, 10, 2, 16, 19, 12, 13, 15, 28, 14, 12, 24, 9, 6, 7,
                2, 15, 19, 13, 30, 30, 23, 19, 11, 3, 17, 2, 14, 20, 22, 30, 12, 1, 11, 2, 2, 20, 20, 27, 15, 9, 10, 4,
                12, 30, 13, 5, 2, 11, 29, 5, 3, 13, 22, 5, 16, 19, 7, 19, 11, 16, 11, 25, 29, 21, 29, 3, 2, 9, 20, 15,
                9 };
        int target = 32;
        System.out.println(numSubseq(nums, target));
    }
}
