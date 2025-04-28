package leetcode.slidewindow.varT;

/*
 * [变长滑动窗口]/[求子数组个数]/[越短越合法]
 */
public class ShorterT {
    /*
     * 2302. 统计得分小于 K 的子数组数目
     */
    public long countSubarrays(int[] nums, long k) {
        int n = nums.length;
        int l = 0, r = 0;
        long ans = 0;
        long sum = 0;
        while(r < n) {
            sum += nums[r];
            r++;
            while(sum * (r-l) >= k) {
                sum -= nums[l];
                l++;
            }
            ans += r-l;
        }
        return ans;
    }
}
