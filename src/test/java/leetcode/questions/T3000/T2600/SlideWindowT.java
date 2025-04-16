package leetcode.questions.T3000.T2600;

import java.util.HashMap;
import java.util.Map;

public class SlideWindowT {
    /*
     * 2537. 统计好子数组的数目 [Medium]
     * 
     * 滑动窗口
     * 使用一个哈希表保存窗口中出现的数字及其次数
     * 写一个函数，根据哈希表判断当前窗口中的数字是否满足条件
     * 窗口的大小不是固定的
     * 窗口的左边是l，右边是r
     * 开始的时候，一直移动r，直至满足答案
     * 然后开始移动l，直至不满足答案
     * 则ans+=delta_l
     * 上述思路整体是正确的，但判断当前窗口中的数字是否满足条件这里不够简单
     * 假设当前通过移动右边界r，向窗口中添加了nums[r]
     * 那么新增的数对tot += cnt.get(nums[r])
     * 类似地，通过移动左边界l，移除了窗口中的nums[l]
     * 那么减少的数对tot -= cnt.get(nums[l])-1
     */
    public long countGood(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> cnt = new HashMap<>();
        // 相等数对总数
        int tot = 0, l = 0;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            // 获取当前位置数字的出现次数c，那么窗口中加入nums[i]后，增加的数对数c
            int c = cnt.getOrDefault(nums[i], 0);
            tot += c;
            cnt.put(nums[i], c + 1);
            // 移动左端点至不满足条件
            while (tot >= k) {
                // 获取左端点数字的出现次数，则移除左端点，减少数对c-1
                c = cnt.get(nums[l]);
                tot -= c - 1;
                cnt.put(nums[l], c - 1);
                l++;
            }
            ans += l;
        }
        return ans;
    }

    /**
     * 2555. 两个线段获得的最多奖品 [Medium]
     * 维护两个滑动窗口+第一个窗口的最大值
     */
    public int maximizeWin(int[] prizePositions, int k) {
        int ans = 0;
        for (int l1 = 0, r1 = 0, l2 = 0, r2 = 0, firstMax = 0; r2 < prizePositions.length; r2++) {
            // 如果第二个窗口的范围过大了，就把左端点收一收
            while (prizePositions[r2] - prizePositions[l2] > k) {
                l2++;
            }

            // 移动第一个窗口，并维护其最大值
            for (; r1 < l2; r1++) {
                // 如果第一个窗口过大
                while (prizePositions[r1] - prizePositions[l1] > k) {
                    l1++;
                }
                firstMax = Math.max(firstMax, r1 - l1 + 1);
            }

            ans = Math.max(ans, firstMax + r2 - l2 + 1);
        }
        return ans;
    }
}
