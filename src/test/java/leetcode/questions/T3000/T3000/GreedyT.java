package leetcode.questions.T3000.T3000;

import org.junit.jupiter.api.Test;

public class GreedyT {
    /*
     * 2918. 数组的最小相等和   [Medium]
     * 
     * 简单的贪心
     * 首先计算nums1和nums2的和sum1，sum2（long），同时统计其中0的个数cnt1，cnt2
     * if(cnt1 == 0 && cnt2 == 0)，则判断sum1==sum2
     * if(cnt1 == 0)，则只能添加sum2来达到和sum1相等，return sum1-sum2 < cnt2 ? -1 : sum1;
     * if(cnt2 == 0) return sum2-sum1 < cnt1 ? -1 : sum2;
     * return Math.max(sum1+cnt1, sum2+cnt2)
     */
    public long minSum(int[] nums1, int[] nums2) {
        long sum1 = 0, sum2 = 0;
        int cnt1 = 0, cnt2 = 0;
        for (int num : nums1) {
            if (num == 0)
                cnt1++;
            sum1 += num;
        }
        for (int num : nums2) {
            if (num == 0)
                cnt2++;
            sum2 += num;
        }

        if (cnt1 == 0 && cnt2 == 0)
            return sum1 == sum2 ? sum1 : -1;
        if (cnt1 == 0)
            return sum1 - sum2 < cnt2 ? -1 : sum1;
        if (cnt2 == 0)
            return sum2 - sum1 < cnt1 ? -1 : sum2;

        return Math.max(sum1 + cnt1, sum2 + cnt2);
    }

    @Test
    public void test() {
        int[] nums1 = { 17, 1, 13, 12, 3, 13 };
        int[] nums2 = { 2, 25 };
        System.out.println(minSum(nums1, nums2)); // 输出：15
    }
}
