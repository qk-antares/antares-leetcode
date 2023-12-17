package leetcode.hots100;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ArrayLearn {
    /**
     * 最大子数组和
     * dp[i]表示nums[i]结尾的序列的最大值，则dp[i] = Math.max(dp[i-1]+nums[i], nums[i])，只跟上一个状态有关，可以进行压缩
     */
    public int maxSubArray(int[] nums) {
        int lastState = nums[0], curState;
        int ans = lastState;
        for (int i = 1; i < nums.length; i++) {
            curState = lastState > 0 ? lastState+nums[i] : nums[i];
            ans = Math.max(ans, curState);
            lastState = curState;
        }
        return ans;
    }

    /**
     * 合并区间
     */
    public int[][] merge(int[][] intervals) {
        //首先将区间按照开始位置排序
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        ArrayList<int[]> ans = new ArrayList<>();
        ans.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            if(intervals[i][0] > ans.get(ans.size()-1)[1]){
                ans.add(intervals[i]);
            } else {
                int[] tmp = ans.get(ans.size() - 1);
                tmp[1] = Math.max(tmp[1], intervals[i][1]);
                ans.set(ans.size()-1, tmp);
            }
        }
        return ans.toArray(new int[ans.size()][]);
    }

    /**
     * 轮转数组（翻转数组）
     */
    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        if(k == 0){
            return;
        }
        reverse(nums, 0, nums.length-1);
        reverse(nums, 0, k-1);
        reverse(nums, k, nums.length-1);
    }

    void reverse(int[] nums, int l, int r){
        while (l < r){
            int tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
            l++;
            r--;
        }
    }

    /**
     * 除自身以外数组的乘积(维护两个数组L[],R[],L[i]代表nums[i]左侧元素的乘积，R[i]代表右侧元素的乘积)
     */
    public int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        int[] L = new int[len];
        L[0] = 1;
        int[] R = new int[len];
        R[len-1] = 1;
        for (int i = 1; i < len; i++) {
            L[i] = L[i-1] * nums[i-1];
            R[len-i-1] = R[len-i] * nums[len-i];
        }

        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = L[i] * R[i];
        }
        return ans;
    }

    /**
     * 缺失的第一个正数，假设数组长度N，则只能在[1, N+1]内
     */
    public int firstMissingPositive(int[] nums) {
        int N = nums.length;
        for (int i = 0; i < N; i++) {
            if(nums[i] <= 0){
                nums[i] = N+1;
            }
        }

        for (int i = 0; i < N; i++) {
            int index = Math.abs(nums[i]);
            if(index < N+1){
                nums[index-1] = -1 * Math.abs(nums[index-1]);
            }
        }

        for (int i = 0; i < N; i++) {
            if(nums[i] > 0){
                return i+1;
            }
        }

        return N+1;
    }

    @Test
    void invoke(){
    }
}
