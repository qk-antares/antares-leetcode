package leetcode.binarysearch;

import java.util.Arrays;

/*
 * 二分答案
 */
public class SearchAns {
    /*
     * 2616. 最小化数对的最大差值   [Medium]    <Star>
     * 
     * 关键在于计算满足某个距离范围的数对个数，这里其实是贪心策略
     */
    public int minimizeMax(int[] nums, int p) {
        Arrays.sort(nums);
        int n = nums.length;
        int l = 0;
        int r = nums[n-1]-nums[0];

        while(l <= r) {
            int mid = (l+r)/2;
            if(count(nums, mid) < p) {
                l = mid+1;
            } else {
                r = mid-1;
            }
        }

        return l;
    }

    int count(int[] nums, int delta) {
        int cnt = 0;
        int n = nums.length;
        for(int i = 0; i < n-1; i++) {
            if(nums[i+1]-nums[i] <= delta) {
                cnt++;
                i++;
            }
        }
        return cnt;
    }
}
