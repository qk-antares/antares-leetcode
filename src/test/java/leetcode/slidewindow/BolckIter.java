package leetcode.slidewindow;

import java.util.List;

import org.junit.jupiter.api.Test;

/*
 * 分组循环
 */
public class BolckIter {
    /*
     * 2348. 全 0 子数组的数目 [Medium]
     * 
     * 滑动窗口获取每个全0段的长度len，则ans+=(len+1)*len/2
     * 
     * 或使用增量法：
     * 每遇到一个0 idx，假设它前面的第一个非0的位置是NZ，则ans+=idx-NZ
     * 遍历的过程中不断更新NZ
     */
    public long zeroFilledSubarray0(int[] nums) {
        int n = nums.length;
        int l = 0, r = 0;
        long ans = 0;
        while (r < n) {
            if (nums[r] != 0) {
                ans += (long) (r - l + 1) * (r - l) / 2;
                r++;
                l = r;
            } else {
                r++;
            }
        }
        ans += (long) (r - l + 1) * (r - l) / 2;
        return ans;
    }

    public long zeroFilledSubarray(int[] nums) {
        long ans = 0;
        int NZ = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                NZ = i;
            } else {
                ans += i - NZ;
            }
        }
        return ans;
    }

    /*
     * 3350. 检测相邻递增子数组 II [Medium]
     * 
     * 1次遍历
     * 记录两个值
     * 当前的递增序列的长度cur
     * 前一个递增序列的长度pre
     * ans = Math.max(ans, Math.max(cur/2, Math.min(cur, pre)))
     */
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int cur = 1;
        int pre = 0;
        int ans = 0;
        int n = nums.size();
        for(int i = 1; i < n; i++) {
            if(nums.get(i) > nums.get(i-1)) {
                cur++;
            } else {
                pre = cur;
                cur = 1;
            }
            ans = Math.max(ans, Math.max(cur/2, Math.min(cur, pre)));
        }
        return ans;
    }

    @Test
    void test() {
        System.out.println(maxIncreasingSubarrays(List.of(2,5,7,8,9,2,3,4,3,1)));
    }
}