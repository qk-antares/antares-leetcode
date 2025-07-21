package leetcode.datastruture.prefixsum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashT {
    /*
     * 930. 和相同的二元子数组 [Medium]
     * 
     * 相当于在前缀和数组上应用枚举左维护右
     */
    public int numSubarraysWithSum(int[] nums, int goal) {
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = nums[i] + s[i];
        }
        int[] cnt = new int[30001];
        int ans = 0;
        for (int si : s) {
            ans += si - goal < 0 ? 0 : cnt[si - goal];
            cnt[si]++;
        }
        return ans;
    }

    /*
     * 560. 和为 K 的子数组 [Medium]
     */
    public int subarraySum0(int[] nums, int k) {
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = nums[i] + s[i];
        }

        Map<Integer, Integer> cnt = new HashMap<>();
        int ans = 0;
        for (int ss : s) {
            ans += cnt.getOrDefault(ss - k, 0);
            cnt.merge(ss, 1, Integer::sum);
        }

        return ans;
    }

    public int subarraySum(int[] nums, int k) {
        int s = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        cnt.merge(s, 1, Integer::sum);

        int ans = 0;
        for (int num : nums) {
            s += num;
            ans += cnt.getOrDefault(s - k, 0);
            cnt.merge(s, 1, Integer::sum);
        }

        return ans;
    }

    /*
     * 1524. 和为奇数的子数组数目 [Medium]
     * 
     * 前缀和
     * 某个子数组相当于取两个前缀和元素，相减
     * 和为奇数，则这两个前缀和奇偶性相异
     */
    public int numOfSubarrays(int[] arr) {
        int s = 0;
        int cnt1 = 1;
        int cnt2 = 0;
        for (int num : arr) {
            s += num;
            if (s % 2 == 0)
                cnt1++;
            else
                cnt2++;
        }

        return (int) ((long) cnt1 * cnt2 % 1_000_000_007);
    }

    /*
     * 2845. 统计趣味子数组的数目 [Medium] <Star>
     * 
     * 可以用一个长度与nums相同的数组，标记每个位置是否满足nums[i] % modulo == k
     * 变长的滑动窗口
     * 用l和r标记窗口的左右边界
     * 移动r，直至窗口中的true的数量是k+n*modulo
     * 可以用for训练来枚举不同的true数量
     * 没能实现上述算法，还有太复杂了（可能还有问题），仅记录下思考的过程
     * 
     * 实际上这道题可以使用前缀和+枚举右维护左的方式求解
     * 首先和上面类似，用一个int[]数组标记每个位置是否满足nums[i] % modulo == k
     * 接下来，计算flag的前缀和prefixSum
     * 然后，对prefixSum进行遍历，假设我们遍历的元素prefixSum[i]是a好了
     * 这意味着[0..i]这段有a个满足条件的索引，我们要找到[0..i]这段里的b
     * a-b=cnt, b=a-cnt
     * cnt % modulo == k，也即cnt = k + n*modulo
     * 枚举n，看b的个数
     * 
     * 上述方法的效率还是不高，因为枚举n这个过程造成了时间复杂度实际上成为O(n^2)
     * (s[r]-s[l]) mod modulo = k mod modulo
     * 可以进一步写成(s[r]-k) mod modulo = s[l] mod modulo (模运算的加减法封闭)
     * 遍历的过程统计s[l] mod modulo
     * s[l] mod modulo的值域在Math.min(n, modulo-1)
     * 所以可以用一个大小为Math.min(n+1,modulo)的数组来记录
     */
    public long countInterestingSubarrays(List<Integer> nums, int modulo, int k) {
        int n = nums.size();
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + (nums.get(i) % modulo == k ? 1 : 0);
        }

        int[] cnt = new int[Math.min(n + 1, modulo)];
        long ans = 0;
        for (int s : sum) {
            if (s >= k) {
                ans += cnt[(s - k) % modulo];
            }
            cnt[s % modulo]++;
        }

        return ans;
    }
}
