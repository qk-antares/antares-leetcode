package leetcode.datastruture.prefixsum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import leetcode.common.TreeNode;

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
     * 974. 和可被 K 整除的子数组 [Medium]
     * 
     * 求前缀和
     * 子数组的和是从前缀和取两个元素作差
     * 差的结果是k的倍数(s2-s1)%k==0 s2-s1=nk s1=s2-nk max=s2-nk (s2-max)/k<=n<=(s2-min)/k
     * 维护前缀和的最大最小值
     * 
     * 超时，map的key应该是s%k的余数
     */
    public int subarraysDivByK0(int[] nums, int k) {
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
        }

        int[] cnt = new int[k];

        int ans = 0;
        for (int s2 : s) {
            int mod = (s2 % k + k) % k;
            ans += cnt[mod];
            cnt[mod]++;
        }

        return ans;
    }

    public int subarraysDivByK(int[] nums, int k) {
        int s = 0;
        int[] cnt = new int[k];
        cnt[0]++;
        int ans = 0;
        for (int num : nums) {
            s += num;
            int mod = (s % k + k) % k;
            ans += cnt[mod];
            cnt[mod]++;
        }

        return ans;
    }

    /*
     * 523. 连续的子数组和
     * 
     * 本题k的范围较大，所以只能使用HashMap
     */
    public boolean checkSubarraySum0(int[] nums, int k) {
        int s = 0;
        // 某个s%k出现的最早下标
        Map<Integer, Integer> idx = new HashMap<>();
        idx.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            s += nums[i];
            int mod = (s % k + k) % k;
            Integer val = idx.get(mod);
            if (val != null) {
                if (i - val >= 2)
                    return true;
            } else {
                idx.put(mod, i);
            }
        }
        return false;
    }

    public boolean checkSubarraySum(int[] nums, int k) {
        int n = nums.length;
        // 记录前缀和的mod（本题没有负数）
        int[] mod = new int[n + 1];
        for (int i = 0; i < n; i++) {
            mod[i + 1] = (mod[i] + nums[i]) % k;
        }

        // 某个s%k是否出现过
        Set<Integer> set = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            if (set.contains(mod[i]))
                return true;
            set.add(mod[i - 1]);
        }
        return false;
    }

    /*
     * 437. 路径总和 III [Medium]
     */
    int ans = 0;

    // 深度优先遍历+前缀和
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null)
            return 0;

        Map<Long, Integer> map = new HashMap<>();
        map.put(0L, 1);
        dfs(root, targetSum, 0L, map);
        return ans;
    }

    // 统计以cur结尾的等于target的路径数
    void dfs(TreeNode cur, int targetSum, long preSum, Map<Long, Integer> map) {
        preSum += cur.val;
        ans += map.getOrDefault(preSum - targetSum, 0);
        map.merge(preSum, 1, Integer::sum);

        if (cur.left != null)
            dfs(cur.left, targetSum, preSum, map);
        if (cur.right != null)
            dfs(cur.right, targetSum, preSum, map);

        map.merge(preSum, -1, Integer::sum);
    }

    /*
     * 2588. 统计美丽子数组数目 [Medium]
     * 
     * 用bits来表示nums的前n个元素，32个bit位出现1总数的奇偶性
     * 记录某个奇偶性的出现次数
     * 美丽子数组对应的bits[l]和bits[r]一定相同
     */
    public long beautifulSubarrays(int[] nums) {
        int bits = 0;
        Map<Integer, Integer> map = new HashMap<>(nums.length + 1);
        map.put(0, 1);
        long ans = 0;
        for (int num : nums) {
            bits ^= num;
            // getOrDefault与merge合并能提高算法效率
            // int old = map.getOrDefault(bits, 0);
            // ans += old;
            // map.put(bits, old + 1);
            ans += map.getOrDefault(bits, 0);
            map.merge(bits, 1, Integer::sum);
        }

        return ans;
    }

    /*
     * 525. 连续数组 [Medium]
     * 
     * 计算前缀和s
     * s[i]-s[j]=(i-j)/2
     * 2s[i]-2s[j]=i-j
     * 2s[i]-i=2s[j]-j
     * 记录每个2s[i]-i出现的最早下标
     */
    public int findMaxLength(int[] nums) {
        int n = nums.length;
        int s = 0;
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        for (int i = 1; i <= n; i++) {
            s += nums[i - 1];
            int key = 2 * s - i;
            Integer idx = map.get(key);
            if (idx != null) {
                ans = Math.max(ans, i - idx);
            } else {
                map.put(key, i);
            }
        }

        return ans;
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

    @Test
    public void test() {
        System.out.println(findMaxLength(new int[] { 0, 1, 1, 1, 1, 1, 0, 0, 0 }));
    }
}
