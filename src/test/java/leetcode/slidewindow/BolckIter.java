package leetcode.slidewindow;

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
}