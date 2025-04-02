package leetcode.questions.T3000.T2900;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ArrayT {
    // 2824. 统计和小于目标的下标对数目 [Easy]
    public int countPairs(List<Integer> nums, int target) {
        Collections.sort(nums);
        int ans = 0;
        int len = nums.size();
        for (int i = 0; i < len - 1; i++) {
            if (nums.get(i + 1) + nums.get(i) >= target) {
                break;
            }
            int lastIndex = binarySearch(nums, target - nums.get(i), i + 1, len - 1);
            ans += lastIndex - i;
        }
        return ans;
    }

    // 找到最后一个小于target的数的index
    public int binarySearch(List<Integer> nums, int target, int l, int r) {
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums.get(mid) < target) {
                if (mid + 1 > r || nums.get(mid + 1) >= target) {
                    return mid;
                } else {
                    l = mid + 1;
                }
            } else {
                r = mid - 1;
            }
        }

        return l - 1;
    }

    /*
     * 2873. 有序三元组中的最大值 I
     * 
     * 从nums的右端遍历（nums[k]）
     * 我们需要nums[i]-num[j]尽可能地大
     * 所以可以从前往后对nums进行一次遍历，记录每个元素左侧出现过的最大值max[j]
     * 则每个位置上的最大nums[i]-nums[j]为max[j]-nums[j]
     * 
     * 一种更简单的解法是，分别从前往后和从后往前对nums进行遍历，记录每个位置左侧的最大值leftMax和右侧的最大值rightMax
     * 然后再对nums进行一次遍历，ans = Math.max(ans, (leftMax[i]-nums[i]) * rightMax[i])
     */

    public long maximumTripletValue0(int[] nums) {
        int n = nums.length;
        // 记录每个位置左侧最大的值
        int[] leftMax = new int[n];
        // 当前位置(j)能产生的最大nums[i]-nums[j]
        int[] maxDelta = new int[n];
        // 截至当前位置能产生的最大nums[i]-nums[j]
        long[] max = new long[n];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], nums[i - 1]);
            maxDelta[i] = leftMax[i] - nums[i];
            max[i] = Math.max(max[i - 1], maxDelta[i]);
        }

        long ans = 0;
        // 对nums进行遍历
        for (int i = n - 1; i > 1; i--) {
            ans = Math.max(ans, nums[i] * max[i - 1]);
        }
        return ans;
    }

    public long maximumTripletValue(int[] nums) {
        int n = nums.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], nums[i - 1]);
            rightMax[n - 1 - i] = Math.max(rightMax[n - i], nums[n - i]);
        }

        long ans = 0;
        // 对nums进行遍历
        for (int i = 1; i < n - 1; i++) {
            ans = Math.max(ans, (long) (leftMax[i] - nums[i]) * rightMax[i]);
        }
        return ans;
    }

    @Test
    void test() {
        countPairs(Arrays.asList(-5, -4, -10, 7), 14);
    }
}
