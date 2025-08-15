package leetcode.hots100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

public class ArrayLearn {
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
