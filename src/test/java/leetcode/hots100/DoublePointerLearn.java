package leetcode.hots100;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoublePointerLearn {
    /**
     * 移动零
     */
    public void moveZeroes(int[] nums) {
        int cur = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != 0){
                nums[cur] = nums[i];
                cur++;
            }
        }
        for (int i = cur; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    /**
     * 盛最多水的容器
     */
    public int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int ans = Integer.MIN_VALUE;
        while (l < r){
            ans = Math.max(ans, (r-l)*Math.min(height[l], height[r]));
            if(height[l] < height[r]){
                l++;
            } else if (height[l] > height[r]){
                r--;
            } else {
                l++;
                r--;
            }
        }
        return ans;
    }

    /**
     * 三数之和
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        ArrayList<List<Integer>> ans = new ArrayList<>();
        int l,r;
        for (int i = 0; i < nums.length - 2;i++) {
            if(nums[i] > 0) return ans;
            if(i > 0 && nums[i] == nums[i-1]) continue;
            l = i+1;
            r = nums.length-1;
            while (l < r){
                if(nums[i] + nums[l] + nums[r] == 0){
                    ans.add(new ArrayList<>(Arrays.asList(nums[i], nums[l], nums[r])));
                    while(l<r && nums[l]==nums[l+1]) l++;
                    while(l<r && nums[r]==nums[r-1]) r--;
                    l++;
                    r--;
                } else if(nums[i] + nums[l] + nums[r] < 0){
                    l++;
                } else {
                    r--;
                }
            }
        }
        return ans;
    }

    /**
     * ***接雨水
     */
    public int trap(int[] height) {
        int sum = 0;
        int l = 1, r=height.length-2;
        int lMax = height[0],rMax=height[height.length-1];
        for (int i = 1; i < height.length-1; i++) {
            if(height[l-1] < rMax){
                lMax = Math.max(lMax, height[l]);
                sum += lMax - height[l];
                l++;
            } else {
                rMax = Math.max(rMax, height[r]);
                sum += rMax - height[r];
                r--;
            }
        }
        return sum;
    }

    @Test
    void invoke(){
        threeSum(new int[]{-1,0,1,2,-1,-4});
    }
}
