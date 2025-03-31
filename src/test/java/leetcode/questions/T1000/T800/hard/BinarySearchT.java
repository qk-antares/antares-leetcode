package leetcode.questions.T1000.T800.hard;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BinarySearchT {
    /*
     * 719. 找出第 K 小的数对距离
     */
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int len = nums.length;
        int l = 0, r = nums[len-1] - nums[0];
        while(l <= r) {
            int mid = (l + r) / 2;
            //距离小于等于mid的数量
            int cnt = 0;
            for(int i = 1 ; i < len; i++){
                cnt += i - binarySearch(nums, i-1, nums[i]-mid);
            }
            if(cnt <= k){
                r = mid-1;
            } else {
                l = mid+1;
            }
        }
        return l;
    }

    //寻找nums中[0,end]这个区间里，>=top的最小index
    public int binarySearch(int[] nums, int end, int top) {
        int l = 0, r = end;
        while(l < r) {
            int mid = (l+r) / 2;
            if(nums[mid] < top) {
                l = mid+1;
            } else {
                r = mid;
            }
        }
        return r;
    }

    @Test
    public void test() {
        int[] nums = {1,6,1};
        int k = 0;
        System.out.println(smallestDistancePair(nums, k));
    }
}
