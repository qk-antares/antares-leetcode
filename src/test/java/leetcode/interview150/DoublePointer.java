package leetcode.interview150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DoublePointer {
    /**
     * 125. 验证回文串
     */
    public boolean isPalindrome(String s) {
        for(int i = 0,j = s.length()-1;i < j;){
            if(!Character.isLetter(s.charAt(i)) && !Character.isDigit(s.charAt(i))){
                i++;
                continue;
            }
            if(!Character.isLetter(s.charAt(j)) && !Character.isDigit(s.charAt(j))){
                j--;
                continue;
            }
            if(Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j)))
                return false;
            i++;
            j--;
        }
        return true;
    }

    /**
     * 392. 判断子序列
     */
    public boolean isSubsequence(String s, String t) {
        if(s.length() == 0) return true;

        int l = 0;
        int n = t.length();
        for (int i = 0; i < n; i++) {
            if(s.charAt(l) == t.charAt(i)){
                l++;
                if(l == s.length()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 167. 两数之和 II - 输入有序数组
     * 使用一个HashMap存储每个元素【剩余， index】
     */
    public int[] twoSum(int[] numbers, int target) {
        int l = 0, r = numbers.length-1;
        while (l < r){
            int sum = numbers[l] + numbers[r];
            if(sum > target){
                r--;
            } else if(sum < target){
                l++;
            } else {
                return new int[]{l+1, r+1};
            }
        }
        return null;
    }

    /**
     * 11. 盛最多水的容器
     */
    public int maxArea(int[] height) {
        int l = 0, r = height.length-1;
        int ans = Integer.MIN_VALUE;
        while (l < r){
            ans = Math.max(ans, Math.min(height[l], height[r])*(r-l));
            if(height[l] > height[r]) r--;
            else if(height[l] < height[r]) l++;
            else {
                l++;r--;
            }
        }
        return ans;
    }

    /**
     * 15. 三数之和
     * 首先对数组进行排序，然后遍历每个元素，剩下的元素中按照双指针来解
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            if(nums[i] > 0) return ans;
            if(i > 0 && nums[i] == nums[i-1]) continue;
            int l = i+1, r= nums.length-1;
            while (l < r){
                int sum = nums[i] + nums[l] + nums[r];
                if(sum == 0){
                    ans.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    //防止元素重复
                    while (l < r && nums[l] == nums[l+1]) l++;
                    while (l < r && nums[r] == nums[r-1]) r--;
                    r--;l++;
                }
                else if(sum > 0)
                    r--;
                else
                    l++;
            }
        }

        return ans;
    }

    @Test
    void test(){
        threeSum(new int[]{-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0});
    }
}
