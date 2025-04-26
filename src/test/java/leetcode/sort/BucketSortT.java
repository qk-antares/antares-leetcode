package leetcode.sort;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class BucketSortT {
    /*
     * 220. 存在重复元素 III    [Hard]
     * 
     * 使用桶排序+滑动窗口，假设元素的绝对差是valueDiff，则桶的大小w=valueDiff+1，确保桶内的元素差不超过valueDiff
     * 
     * 滑动窗口来保证始终维护indexDiff个桶，一旦一个桶内出现多个元素，就返回true
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        int w = valueDiff + 1;
        int n = nums.length;
        Map<Integer, Integer> buckets = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Integer bucketId = getId(nums[i], w);
            if (buckets.containsKey(bucketId))
                return true;
            if (buckets.containsKey(bucketId - 1) && nums[i] - buckets.get(bucketId - 1) <= valueDiff)
                return true;
            if (buckets.containsKey(bucketId + 1) && buckets.get(bucketId + 1) - nums[i] <= valueDiff)
                return true;

            buckets.put(bucketId, nums[i]);
            if (i >= indexDiff) {
                buckets.remove(getId(nums[i - indexDiff], w));
            }
        }
        return false;
    }

    int getId(int num, int w) {
        if (num >= 0) {
            return num / w;
        }
        return (num + 1) / w - 1;
    }

    @Test
    public void test() {
        int[] nums = { -1, 3, -1, 3 };
        int k = 1;
        int t = 3;
        System.out.println(containsNearbyAlmostDuplicate(nums, k, t));
    }
}
