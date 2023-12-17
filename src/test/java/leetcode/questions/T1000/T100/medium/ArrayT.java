package leetcode.questions.T1000.T100.medium;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Array {
    /**
     * 16. 最接近的三数之和
     */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int len = nums.length;
        int sum;
        int ans = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < len-2; i++) {
            int l = i+1 ,r = len-1;
            while (l < r){
                sum = nums[i] + nums[l] + nums[r];
                if(Math.abs(sum - target) < Math.abs(ans - target)){
                    ans = sum;
                }
                if(sum < target){
                    l++;
                } else {
                    r--;
                }
            }
        }
        return ans;
    }

    /**
     * 18. 四数之和
     * 我的解法的主要问题是没有进行有效的剪枝
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        int len = nums.length;
        int sum;
        for (int i = 0; i < len-3; i++) {
            if(nums[i] > 0 && nums[i] > target) break;
            for (int j = i+1; j < len-2; j++) {
                sum = nums[i] + nums[j];
                if(sum > 0 && sum > target) break;
                int l = j+1, r = len-1;
                while (l < r){
                    sum = nums[i] + nums[j] + nums[l] + nums[r];
                    if(sum < target){
                        l++;
                    } else if(sum > target){
                        r--;
                    } else {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l+1] == nums[l]) l++;
                        l++;
                        while (l < r && nums[r-1] == nums[r]) r--;
                        r--;
                    }
                }

                while (j+1 < len && nums[j+1] == nums[j]) j++;
            }
            while (i+1 < len && nums[i+1] == nums[i]) i++;
        }
        return ans;
    }

    /**
     * 18. 四数之和 剪枝版
     */
    public List<List<Integer>> fourSum0(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        int len = nums.length;
        int sum;
        for (int i = 0; i < len-3; i++) {
            if((long) nums[i] + nums[i+1] + nums[i+2] + nums[i+3] > target) break;
            if((long) nums[i] + nums[len-1] + nums[len-2] + nums[len-3] < target) continue;
            for (int j = i+1; j < len-2; j++) {
                if((long) nums[i] + nums[j] + nums[j+1] + nums[j+2] > target) break;
                if((long) nums[i] + nums[j] + nums[len-1] + nums[len-2] < target) continue;
                int l = j+1, r = len-1;
                while (l < r){
                    sum = nums[i] + nums[j] + nums[l] + nums[r];
                    if(sum < target){
                        l++;
                    } else if(sum > target){
                        r--;
                    } else {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l+1] == nums[l]) l++;
                        l++;
                        while (l < r && nums[r-1] == nums[r]) r--;
                        r--;
                    }
                }

                while (j+1 < len && nums[j+1] == nums[j]) j++;
            }
            while (i+1 < len && nums[i+1] == nums[i]) i++;
        }
        return ans;
    }

    @Test
    void test(){
        fourSum(new int[]{1,-2,-5,-4,-3,3,3,5}, -11);
    }
}
