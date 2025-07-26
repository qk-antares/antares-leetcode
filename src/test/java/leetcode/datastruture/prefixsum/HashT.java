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
     * 面试题 17.05. 字母与数字 [Medium]
     * 
     * 求前缀和，字母当作0，数字当作1
     * 有相同个数的0和1，即s[i]-s[j]=(i-j)/2
     * 即2s[i]-i=2s[j]-j
     * 用HashMap记录某个2s[i]-i出现的最早下标
     * 用ans，l，r三个值记录答案
     * 
     * 字母当作-1，数字当作1
     * s[i]-s[j]=0
     * 这种方案因为多了s的自减操作，效率稍差
     */
    public String[] findLongestSubarray0(String[] array) {
        int n = array.length;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        int s = 0;
        int ans = 0, l = 0;
        for (int i = 1; i <= n; i++) {
            String c = array[i - 1];
            if (Character.isDigit(c.charAt(0))) {
                s += 1;
            } else {
                s -= 1;
            }

            Integer j = map.get(s);
            if (j == null) {
                map.put(s, i);
            } else if (i - j > ans) {
                ans = i - j;
                l = j;
            }
        }

        String[] subArr = new String[ans];
        System.arraycopy(array, l, subArr, 0, ans);
        return subArr;
    }

    public String[] findLongestSubarray(String[] array) {
        int n = array.length;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        int s = 0;
        int ans = 0, l = 0;
        for (int i = 1; i <= n; i++) {
            String c = array[i - 1];
            if (Character.isDigit(c.charAt(0))) {
                s += 1;
            }

            int key = 2 * s - i;
            Integer j = map.get(key);
            if (j == null) {
                map.put(key, i);
            } else if (i - j > ans) {
                ans = i - j;
                l = j;
            }
        }

        String[] subArr = new String[ans];
        System.arraycopy(array, l, subArr, 0, ans);
        return subArr;
    }

    /*
     * 3026. 最大好子数组和 [Medium]
     * 
     * 维护左遍历右+前缀和
     * 用一个Map记录左侧出现过的元素，且前缀和最小的位置
     */
    public long maximumSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> idx = new HashMap<>();
        int n = nums.length;
        long[] s = new long[n + 1];
        long ans = Long.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
            Integer preIdx = idx.get(nums[i] - k);
            if (preIdx != null) {
                ans = Math.max(ans, s[i + 1] - s[preIdx]);
            }
            preIdx = idx.get(nums[i] + k);
            if (preIdx != null) {
                ans = Math.max(ans, s[i + 1] - s[preIdx]);
            }

            // 前缀和更小
            Integer val = idx.get(nums[i]);
            if (val == null || s[i] < s[val]) {
                idx.put(nums[i], i);
            }
        }
        return ans == Long.MIN_VALUE ? 0 : ans;
    }

    /*
     * 1477. 找两个和为目标值且不重叠的子数组 [Medium]
     * 
     * 可以求出所有满足target的区间（尽可能小），然后选择两个区间，区间长度总和尽可能小（动态规划）
     * len标记每个位置可以作为合法区间右端点的最小长度，如果不能作为右端点，则len[i]=Integer.MAX_VALUE
     * leftMin标记每个位置（包含当前位置）左侧的最短合法区间
     * 
     * 滑动窗口+维护左侧的最短合法区间
     */
    public int minSumOfLengths0(int[] arr, int target) {
        int n = arr.length;
        int[] s = new int[n + 1];
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        int[] len = new int[n];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + arr[i];
            // 区间的长度
            if (map.containsKey(s[i + 1] - target)) {
                len[i] = i - map.get(s[i + 1] - target) + 1;
            } else {
                len[i] = Integer.MAX_VALUE;
            }
            map.put(s[i + 1], i + 1);
        }

        // 每个位置左侧的最小区间（包含）
        int[] leftMin = new int[n];
        leftMin[0] = len[0];
        for (int i = 1; i < n; i++) {
            leftMin[i] = Math.min(leftMin[i - 1], len[i]);
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            // 当前位置可以作为第二个区间的右端点，则再往前找第一个最短区间leftMin[i-len[i]]
            if (len[i] != -1 && i - len[i] >= 0 && leftMin[i - len[i]] != Integer.MAX_VALUE) {
                ans = Math.min(ans, len[i] + leftMin[i - len[i]]);
            }
        }

        return ans != Integer.MAX_VALUE ? ans : -1;
    }

    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] leftMin = new int[n + 1];
        leftMin[0] = Integer.MAX_VALUE;

        int ans = Integer.MAX_VALUE;

        int l = 0, r = 0;
        int s = 0;
        while (r < n) {
            s += arr[r++];
            while (s > target) {
                s -= arr[l++];
            }

            if (s == target) {
                leftMin[r] = Math.min(r - l, leftMin[r - 1]);
                // 该位置可以作为第二个区间（第一个区间leftMin[l]是存在的）
                if (leftMin[l] != Integer.MAX_VALUE)
                    ans = Math.min(ans, r - l + leftMin[l]);
            } else {
                leftMin[r] = leftMin[r - 1];
            }
        }

        return ans != Integer.MAX_VALUE ? ans : -1;
    }

    /*
     * 1546. 和为目标值且不重叠的非空子数组的最大数目 [Medium]
     * 
     * 滑动窗口+贪心
     * 由于元素并不一定全是正的，所以不能简单地使用滑动窗口
     */
    public int maxNonOverlapping(int[] nums, int target) {
        int s = 0;
        Set<Integer> set = new HashSet<>();
        set.add(0);
        int ans = 0;
        for (int num : nums) {
            s += num;
            if (set.contains(s - target)) {
                ans++;
                set.clear();
            }
            set.add(s);
        }

        return ans;
    }

    /*
     * 1124. 表现良好的最长时间段 [Medium]
     * 
     * 将hours中劳累的置为1，不劳累的置为-1
     * 计算前缀和
     * 那么对于表现良好的时间段(j,i]
     * s[i]-s[j] > 0
     * s[i] > s[j]
     * 
     * TODO: 整理
     */
    public int longestWPI(int[] hours) {
        int n = hours.length;
        for (int i = 0; i < n; i++) {
            if (hours[i] > 8)
                hours[i] = 1;
            else
                hours[i] = -1;
        }

        int[] s = new int[n + 1];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + hours[i];
            for (int j = 0; j <= i; j++) {
                if (s[j] < s[i + 1]) {
                    ans = Math.max(ans, i + 1 - j);
                    break;
                }
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
        // System.out.println(findMaxLength(new int[] { 0, 1, 1, 1, 1, 1, 0, 0, 0 }));
        // maximumSubarraySum(new int[]{1,5}, 2);
        maximumSubarraySum(new int[] { -677, -599, -452, -340, -561, -402, -741, -373, -1000, -842, -355, -717, -556,
                -196, -126, -511, -174, -424, -569, -566, -161, -438, -402, -915, -709, -797, -377, -731, -380, -975,
                -601, -280, -629, -171, -558, -626, -857, -942, -223, -632, -950, -449, -136, -865, -350, -791, -781,
                -271, -953, -912, -100, -775, -938, -576, -268, -230, -269, -393, -844, -897, -828, -498, -598, -344,
                -775, -187, -437, -797, -311, -287, -978, -334, -961, -264, -323, -282, -659, -980, -622, -701, -116,
                -277, -861, -562, -647, -183, -856, -372, -111, -624, -514, -252, -275, -430, -273, -323, -774, -535,
                -797, -291 }, 53);
    }
}
