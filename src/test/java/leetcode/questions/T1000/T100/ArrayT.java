package leetcode.questions.T1000.T100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ArrayT {
    /*
     * 16. 最接近的三数之和 [Medium]
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

    /*
     * 18. 四数之和 [Medium]
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

    /*
     * 18. 四数之和 剪枝版  [Medium]
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

    /*
     * 组合总和 II（使用回溯法）    [Medium]
     */
    List<List<Integer>> ans = new ArrayList<>();
    ArrayList<Integer> curPath = new ArrayList<>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        backtrack(candidates, 0, target);
        return ans;
    }
    
    public void backtrack(int[] choices, int curIndex, int target) {
        if(target == 0){
            ans.add(new ArrayList<>(curPath));
            return;
        }
        if(curIndex == choices.length || target < 0){
            return;
        }

        for (int i = curIndex; i < choices.length; i++) {
            if(i > curIndex && choices[i] == choices[i-1]){
                continue;
            }
            curPath.add(choices[i]);
            backtrack(choices, i+1, target-choices[i]);
            curPath.remove(curPath.size()-1);
        }
    }

    @Test
    void test(){
        combinationSum2(new int[]{10,1,2,7,6,1,5}, 8);
    }
}
