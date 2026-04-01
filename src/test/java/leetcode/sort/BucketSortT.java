package leetcode.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * 桶排序
 */
public class BucketSortT {
    /**
     * 347. 前 K 个高频元素 [Medium]
     * 
     * 桶排序O(n)
     * 堆O(nlongn)
     */
    @SuppressWarnings("unchecked")
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            cnt.merge(num, 1, Integer::sum);
        }
        int maxCnt = Collections.max(cnt.values());

        List<Integer>[] buckets = new List[maxCnt + 1];
        Arrays.setAll(buckets, i -> new ArrayList<>());

        for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
            buckets[e.getValue()].add(e.getKey());
        }

        int[] ans = new int[k];
        int idx = 0;
        for (int i = maxCnt; i > 0; i--) {
            for (int val : buckets[i]) {
                ans[idx++] = val;
            }
            if (idx == k)
                return ans;
        }

        return ans;
    }

    public int[] topKFrequent0(int[] nums, int k) {
        // 首先统计每个数字的出现次数
        HashMap<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> b[1] - a[1]);

        Set<Integer> keySet = count.keySet();
        for (Integer integer : keySet) {
            priorityQueue.add(new int[] { integer, count.get(integer) });
        }

        // 取堆中前k大元素
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = priorityQueue.poll()[0];
        }

        return ans;
    }

    /*
     * 220. 存在重复元素 III [Hard]
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
