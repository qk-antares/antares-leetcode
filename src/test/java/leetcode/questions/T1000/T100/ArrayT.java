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
        for (int i = 0; i < len - 2; i++) {
            int l = i + 1, r = len - 1;
            while (l < r) {
                sum = nums[i] + nums[l] + nums[r];
                if (Math.abs(sum - target) < Math.abs(ans - target)) {
                    ans = sum;
                }
                if (sum < target) {
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
        for (int i = 0; i < len - 3; i++) {
            if (nums[i] > 0 && nums[i] > target)
                break;
            for (int j = i + 1; j < len - 2; j++) {
                sum = nums[i] + nums[j];
                if (sum > 0 && sum > target)
                    break;
                int l = j + 1, r = len - 1;
                while (l < r) {
                    sum = nums[i] + nums[j] + nums[l] + nums[r];
                    if (sum < target) {
                        l++;
                    } else if (sum > target) {
                        r--;
                    } else {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l + 1] == nums[l])
                            l++;
                        l++;
                        while (l < r && nums[r - 1] == nums[r])
                            r--;
                        r--;
                    }
                }

                while (j + 1 < len && nums[j + 1] == nums[j])
                    j++;
            }
            while (i + 1 < len && nums[i + 1] == nums[i])
                i++;
        }
        return ans;
    }

    /*
     * 18. 四数之和 剪枝版 [Medium]
     */
    public List<List<Integer>> fourSum0(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        int len = nums.length;
        int sum;
        for (int i = 0; i < len - 3; i++) {
            if ((long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target)
                break;
            if ((long) nums[i] + nums[len - 1] + nums[len - 2] + nums[len - 3] < target)
                continue;
            for (int j = i + 1; j < len - 2; j++) {
                if ((long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target)
                    break;
                if ((long) nums[i] + nums[j] + nums[len - 1] + nums[len - 2] < target)
                    continue;
                int l = j + 1, r = len - 1;
                while (l < r) {
                    sum = nums[i] + nums[j] + nums[l] + nums[r];
                    if (sum < target) {
                        l++;
                    } else if (sum > target) {
                        r--;
                    } else {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l + 1] == nums[l])
                            l++;
                        l++;
                        while (l < r && nums[r - 1] == nums[r])
                            r--;
                        r--;
                    }
                }

                while (j + 1 < len && nums[j + 1] == nums[j])
                    j++;
            }
            while (i + 1 < len && nums[i + 1] == nums[i])
                i++;
        }
        return ans;
    }

    /*
     * TODO 31. 下一个排列 [Medium]
     * 
     * 算法的整个流程可以描述为：
     * 从右往左遍历，找到第一个降序数对(i-1,i)，满足nums[i-1]<nums[i]，
     * 接下来交换nums[i-1]与[i..]中的某个元素j，具体来说：
     * 从右往左找[i..]这一段，>nums[i-1]的最小的数nums[j]
     * 最后反转[i..]
     * 
     * 在上述算法流程中，[i..]这一段一直是单调递减的，在反转后变为单调递增
     */
    public void nextPermutation(int[] nums) {
        int i = nums.length - 1;
        while (i - 1 >= 0 && nums[i - 1] >= nums[i])
            i--;
        if (i > 0) {
            // 从右往左找[i..]这一段，>nums[i-1]的最小的数nums[j]
            int j = nums.length - 1;
            while (nums[j] <= nums[i - 1])
                j--;
            swap(nums, i - 1, j);
        }

        reverse(nums, i, nums.length - 1);
    }

    void reverse(int[] nums, int l, int r) {
        while (l < r) {
            swap(nums, l, r);
            l++;
            r--;
        }
    }

    void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /*
     * 40. 组合总和 II（使用回溯法） [Medium]
     */
    List<List<Integer>> ans = new ArrayList<>();
    ArrayList<Integer> curPath = new ArrayList<>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        backtrack(candidates, 0, target);
        return ans;
    }

    public void backtrack(int[] choices, int curIndex, int target) {
        if (target == 0) {
            ans.add(new ArrayList<>(curPath));
            return;
        }
        if (curIndex == choices.length || target < 0) {
            return;
        }

        for (int i = curIndex; i < choices.length; i++) {
            if (i > curIndex && choices[i] == choices[i - 1]) {
                continue;
            }
            curPath.add(choices[i]);
            backtrack(choices, i + 1, target - choices[i]);
            curPath.remove(curPath.size() - 1);
        }
    }

    /*
     * TODO 75. 颜色分类    [Medium]
     * 
     * 最简单的方法是记录三种颜色的数量，然而这种方法需要对nums遍历两遍
     * 
     * 一种更优的方法是使用两个指针p0和p1，p0记录0插入的位置，p1记录1插入的位置
     * 接下来对nums进行遍历，如果遇到了0，就交换p0和i（再判断一下交换过来的nums[i]是不是1）
     * 如果遇到了1，就交换p1和i
     */
    public void sortColors(int[] nums) {
        int p0 = 0, p1 = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) {
                swap(nums, p0, i);
                if(nums[i] == 1) swap(nums, p1, i);
                p0++;
                p1++;
            } else if(nums[i] == 1) {
                swap(nums, p1++, i);
            }
        }
    }

    @Test
    void test() {
        // combinationSum2(new int[] { 10, 1, 2, 7, 6, 1, 5 }, 8);
        nextPermutation(new int[] { 3, 2, 1 });
    }
}
