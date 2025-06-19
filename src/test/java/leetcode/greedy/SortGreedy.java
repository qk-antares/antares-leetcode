package leetcode.greedy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * 对数组排序后应用贪心策略
 */
public class SortGreedy {
    /*
     * 2966. 划分数组并满足最大差限制
     */
    public int[][] divideArray(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int[][] ans = new int[n / 3][];
        for (int i = 0; i < n / 3; i++) {
            if (nums[i * 3 + 2] - nums[i * 3] > k)
                return new int[0][0];
            ans[i] = new int[] { nums[i * 3 + 0], nums[i * 3 + 1], nums[i * 3 + 2] };
        }
        return ans;
    }

    /*
     * 2294. 划分数组使最大差为 K
     * 
     * 桶排序
     * 首先对nums进行一次遍历，统计出最小值min
     * [min, min+k], [min+k+1, min+2k+1], [min+2k+2, min+3k+2]...
     * 对nums进行1次遍历，用一个Set来保存当前放置了元素的桶
     * 最后set的大小即为答案
     * 
     * 上述思路不正确，考虑[3,5,6,9,16,19,20,20],k=4
     * 按照上述思路划分出来的桶是[3,5,6], [9], [16], [19,20,20]，其中[9,16]的最大差为7，超过了k=4
     * 但是各个桶的区间不一定是连续的，也就是说，我们可以这么分：[3,5,6], [9], [16,19,20,20]
     * 
     * 一种正确的解法是使用排序+贪心
     */
    public int partitionArray0(int[] nums, int k) {
        int min = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
        }

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            // [0,k]->0 [k+1,2k+1]->1
            set.add((num - min) / (k + 1));
        }

        return set.size();
    }

    public int partitionArray(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = 1;
        int l = nums[0];
        for(int num : nums) {
            if(num-l > k) {
                l = num;
                ans++;
            }
        }

        return ans;
    }

    //正确的桶排序解法如下。实际上是空间换时间，要用一个max-min+1的数组来标记桶的状态
    public int partitionArray1(int[] nums, int k) {
        int max = nums[0], min = nums[0];
        for (int num : nums) {
            if (max < num) {
                max = num;
            } else if (min > num) {
                min = num;
            }
        }
        if (max - min <= k) {
            return 1;
        }
        if (max - min <= 2 * k) {
            return 2;
        }
        int n = max - min + 1;
        boolean[] bucket = new boolean[n];
        for (int num : nums) {
            bucket[num - min] = true;
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (bucket[i]) {
                res++;
                i += k;
            }
        }
        return res;
    }
}
