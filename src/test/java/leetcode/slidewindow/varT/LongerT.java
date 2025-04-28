package leetcode.slidewindow.varT;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * [变长滑动窗口]/[求子数组个数]/[越长越合法]
 */
public class LongerT {
    /*
     * 2799. 统计完全子数组的数目 [Medium]
     * 
     * 需要注意的点是，在扩展右边界r的时候，不应套while循环
     * 这是由于，即时当前的右边界下，窗口中的元素不满足条件，但是它可以使用之前的左边界
     * 如果套个while循环，将忽略当前右边界下的结果
     */
    public int countCompleteSubarrays(int[] nums) {
        // 首先统计nums中出现了哪些数字
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        // 用HashMap统计窗口中出现的元素
        Map<Integer, Integer> cnt = new HashMap<>();
        int n = nums.length;
        int size = set.size();
        int l = 0, r = 0, ans = 0;
        while (r < n) {
            // 拓展右边界时不应套while循环，while(r < n && cnt.size() < size)
            cnt.put(nums[r], cnt.getOrDefault(nums[r], 0) + 1);
            r++;

            while (cnt.size() == size) {
                int update = cnt.get(nums[l]) - 1;
                if (update == 0)
                    cnt.remove(nums[l]);
                else
                    cnt.put(nums[l], update);
                l++;
            }

            ans += l;
        }

        return ans;
    }
}
