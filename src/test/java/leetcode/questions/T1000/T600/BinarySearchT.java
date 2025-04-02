package leetcode.questions.T1000.T600;

import java.util.Random;

public class BinarySearchT {
    /*
     * 528. 按权重随机选择
     * 
     * 定义一个wSum数组，统计截至当前元素（包含当前元素）的w之和
     * 当pick时，通过一个随机函数生成[1, weight]的随机数random
     * 通过二分查找看这个随机数落在哪个weight区间（>=random的最小wSum值的index）
     */
    class Solution {
        int[] wSum;
        int n;

        public Solution(int[] w) {
            n = w.length;
            wSum = new int[n];
            wSum[0] = w[0];
            for (int i = 1; i < n; i++) {
                wSum[i] = w[i] + wSum[i - 1];
            }
        }

        public int pickIndex() {
            int l = 0, r = n - 1;
            Random random = new Random();
            int randomVal = random.nextInt(wSum[r]) + 1;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (wSum[mid] < randomVal) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            return l;
        }
    }

    /*
     * 540. 有序数组中的单一元素 [Medium]
     * 
     * nums[2k]=nums[2k+1]，如果不满足这一点，证明前面出现了目标值
     */
    public int singleNonDuplicate(int[] nums) {
        // 这里的r一定是个奇数
        int n = nums.length, l = 0, r = n - 1;
        int mid;
        while (l < r) {
            mid = (l + r) / 2;

            if (mid % 2 == 0) {
                if (nums[mid] == nums[mid + 1]) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            } else {
                if (nums[mid] == nums[mid - 1]) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            }
        }
        return nums[l];
    }
}
