package leetcode.prefixsum;

import java.util.List;

public class HashT {
    /**
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
