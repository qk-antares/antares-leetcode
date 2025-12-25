package leetcode.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

/*
 * 最长递增子序列
 * 
 * 这类题都有两种解法：
 * 第一种是动态规划，时间复杂度O(n^2)
 * 第二种是"新员工替代老员工"的方法(一次遍历+二分)，时间复杂度O(nlogn)
 * 
 * 对于n范围较大的题，只能使用第二种解法
 */
public class LIST {
    int lower_bound(int[] subSeq, int cur, int target) {
        int l = 0, r = cur - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (subSeq[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }

    int upper_bound(int[] subSeq, int cur, int target) {
        return lower_bound(subSeq, cur, target + 1);
    }

    /*
     * 300. 最长递增子序列 [Medium] <Star> [Link: 354. 俄罗斯套娃信封问题]
     * 
     * 使用dp[i]表示以nums[i]结尾的最长递增子序列，则
     * dp[i] = 1 + Math.max(dp[j])，其中j<i且nums[j]<nums[i]
     * 最后遍历一遍dp，最大值就是答案
     * dp[0]=1
     * 
     * 上述方法的时间复杂度为O(n^2)
     * 
     * 另一种方法是"新员工替代老员工"的二分法：
     * 1. 初始时，最长递增子序列为：d=[nums[0]]
     * 2. 接着开始从1遍历nums，如果nums[i]>d[d.length-1]，则将nums[i]加入d，d的长度+1
     * 3. 如果nums[i]<=d[d.length-1]，则通过二分法将nums[i]插入d中
     * 4. 遍历完nums后，d的长度即为答案
     */
    public int lengthOfLIS0(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i])
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
            }
        }

        int ans = 1;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] d = new int[n];
        d[0] = nums[0];
        int len = 1;

        for (int i = 1; i < n; i++) {
            if (nums[i] > d[len - 1]) {
                d[len++] = nums[i];
            } else {
                // 找到第一个>=nums[i]的数进行替换
                int idx = lower_bound(d, len, nums[i]);
                d[idx] = nums[i];
            }
        }
        return len;
    }

    /*
     * 2826. 将三个组排序 [Medium]
     * 
     * 本质就是从前往后选元素，这些元素不能出现下降，然后序列的最大长度
     * 使用dp[i]表示nums[i]结尾的序列最大长度
     * dp[i] = 1 + max(dp[j]) 0<=j<i&&nums[i]>=nums[j]
     * 
     * 用新员工替代老员工的方法：
     * 一次扫描，用cur来标记插入指针，cur=0
     * 如果nums[i]>=nums[cur]，nums[cur++]=nums[i]
     * 如果nums[i]<nums[cur]，通过二分将nums[i]插入到[0..cur-1]
     * 最后返回cur
     */
    public int minimumOperations0(List<Integer> nums) {
        int n = nums.size();
        int[] dp = new int[n];
        dp[0] = 1;
        int max = 1;
        for (int i = 1; i < n; i++) {
            boolean[] flags = new boolean[nums.get(i)];
            for (int j = i - 1; j >= 0; j--) {
                if (dp[j] > dp[i] && nums.get(j) <= nums.get(i)) {
                    dp[i] = dp[j];
                    flags[nums.get(j) - 1] = true;
                }

                if (allTrue(flags))
                    break;
            }
            dp[i]++;
            max = Math.max(max, dp[i]);
        }

        return n - max;
    }

    public boolean allTrue(boolean[] flags) {
        for (int i = 0; i < flags.length; i++) {
            if (!flags[i])
                return false;
        }
        return true;
    }

    public int minimumOperations(List<Integer> nums) {
        int n = nums.size();
        int cur = 1;
        for (int i = 1; i < n; i++) {
            if (nums.get(i) >= nums.get(cur - 1))
                nums.set(cur++, nums.get(i));
            else {
                // 使用二分将nums[i]插入到指定位置，找到第一个>nums[i]值
                int l = 0, r = cur - 1;
                while (l <= r) {
                    int mid = (l + r) / 2;
                    if (nums.get(mid) <= nums.get(i)) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                nums.set(l, nums.get(i));
            }
        }
        return n - cur;
    }

    /*
     * 1671. 得到山形数组的最少删除次数 [Hard]
     * 
     * 本质是之前的题进行两次计算
     * dp0[i]代表以nums[i]结尾的最长(严格)递增子序列
     * dp0[i] = 1 + dp0[j] (0<=j<i&&nums[j]<nums[i])
     * dp1[i]代表以nums[i]开始的最长(严格)递减子序列
     * dp1[i] = 1 + dp1[j] (i<j<n&&nums[j]>nums[i])
     * dp0的构造是从前往后的，dp1的构造从后往前
     * 计算max(dp0[i]+dp1[i]-1)
     * 这道题nums.length的范围比较小，所以可以使用上述方法
     * 
     * 也可改造成"新员工替代老员工"的方法
     */
    public int minimumMountainRemovals0(int[] nums) {
        int n = nums.length;
        int[] dp0 = new int[n];
        dp0[0] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (dp0[j] > dp0[i] && nums[j] < nums[i]) {
                    dp0[i] = dp0[j];
                }
            }
            dp0[i]++;
        }

        int[] dp1 = new int[n];
        dp1[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (dp1[j] > dp1[i] && nums[j] < nums[i]) {
                    dp1[i] = dp1[j];
                }
            }
            dp1[i]++;
        }

        int max = 0;
        for (int i = 0; i < n; i++) {
            if (dp0[i] != 1 && dp1[i] != 1)
                max = Math.max(max, dp0[i] + dp1[i]);
        }
        return n - max + 1;
    }

    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] d = new int[n];
        d[0] = nums[0];
        int[] dp0 = new int[n];
        dp0[0] = 1;
        int cur = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > d[cur - 1]) {
                d[cur++] = nums[i];
                dp0[i] = cur;
            } else {
                // nums[i] <= d[cur-1]
                // 使用二分将nums[i]插入到d中，找到最后一个>=nums[i]的元素，替换它后面的元素
                int idx = lower_bound(d, cur, nums[i]);
                d[idx] = nums[i];
                dp0[i] = idx + 1;
            }
        }

        d[0] = nums[n - 1];
        int[] dp1 = new int[n];
        dp1[n - 1] = 1;
        cur = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > d[cur - 1]) {
                d[cur++] = nums[i];
                dp1[i] = cur;
            } else {
                int idx = lower_bound(d, cur, nums[i]);
                d[idx] = nums[i];
                dp1[i] = idx + 1;
            }

        }

        int max = 0;
        for (int i = 0; i < n; i++) {
            if (dp0[i] != 1 && dp1[i] != 1)
                max = Math.max(max, dp0[i] + dp1[i]);
        }
        return n - max + 1;
    }

    /*
     * 1964. 找出到每个位置为止最长的有效障碍赛跑路线
     * 
     * 最终返回的就是之前的dp[i]
     * dp[i]表示obstacles[i]结尾的最长障碍
     * dp[i] = 1 + max(dp[j]) (0<=j<i)
     * 
     * 因为此题n的范围较大，所以只能使用一遍扫描+二分的"新员工替代老员工"
     */
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        int n = obstacles.length;
        int[] dp = new int[n];
        dp[0] = 1;
        int cur = 1;
        for (int i = 1; i < n; i++) {
            if (obstacles[i] >= obstacles[cur - 1]) {
                obstacles[cur++] = obstacles[i];
                dp[i] = cur;
            } else {
                // 将obstacles[i]插入到obstacles[0..cur-1]，找到>obstacles[i]的第一个元素，替换它
                int idx = lower_bound(obstacles, cur, obstacles[i] + 1);
                obstacles[idx] = obstacles[i];
                dp[i] = idx + 1;
            }
        }

        return dp;
    }

    /*
     * 2111. 使数组 K 递增的最少操作次数
     * 
     * 必须使用新员工替代老员工
     * k实际上把arr切分成了k个子序列
     * 遍历这k个子序列，求子序列的"最长递增子序列"
     * 把它们加起来得到sum
     * 再用arr.length减去sum
     * 注意本题是非严格递增
     */
    public int kIncreasing(int[] arr, int k) {
        int n = arr.length;
        int sum = 0;
        int[] subSeq = new int[n];
        int cur = 0;
        int max = 0;
        for (int i = 0; i < k; i++) {
            subSeq[0] = arr[i];
            cur = 1;
            max = 1;
            for (int j = i + k; j < n; j += k) {
                if (arr[j] >= subSeq[cur - 1]) {
                    subSeq[cur++] = arr[j];
                    max = Math.max(max, cur);
                } else {
                    int idx = lower_bound(subSeq, cur, arr[j] + 1);
                    subSeq[idx] = arr[j];
                }
            }
            sum += max;
        }

        return n - sum;
    }

    /**
     * 960. 删列造序 III [Hard]
     * 
     * 设dp[i]是以i结尾的最长递增子序列的长度
     */
    public int minDeletionSize(String[] strs) {
        int n = strs[0].length();
        int[] dp = new int[n];
        int maxLen = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(dp[i] < dp[j] && checkIJ(strs, i, j)) {
                    dp[i] = dp[j];
                }
            }
            dp[i]++;
            maxLen = Math.max(maxLen, dp[i]);
        }
        return n-maxLen;
    }

    public boolean checkIJ(String[] strs, int i, int j) {
        for(String str : strs) {
            if(str.charAt(j) > str.charAt(i)) return false;
        }
        return true;
    }

    /*
     * 673. 最长递增子序列的个数 [Medium]
     * 
     * len[i]代表以nums[i]结尾的最长递增子序列的长度
     * cnt[i]代表以nums[i]结尾的最长递增子序列的个数
     * len[i]=1+max(len[j]) (0<=j<i)
     * 在遍历len[j]的同时，统计==max(len[j])的个数，这个个数即为cnt[i]
     * 此题nums.length的范围较小，因此上述方法不会超时
     */
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] len = new int[n];
        int[] cnt = new int[n];

        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            int c = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] <= nums[j])
                    continue;

                if (len[j] == len[i])
                    c += cnt[j];
                else if (len[j] > len[i]) {
                    c = cnt[j];
                    len[i] = len[j];
                }
            }
            len[i]++;
            maxLen = Math.max(maxLen, len[i]);
            cnt[i] = c > 0 ? c : 1;
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (len[i] == maxLen) {
                ans += cnt[i];
            }
        }

        return ans;
    }

    /*
     * 354. 俄罗斯套娃信封问题 [Hard] <Star> [Link: 300. 最长递增子序列]
     * 
     * 首先对envelopes进行排序，排序的规则为：（首要）wi从小到大，（次要）hi从大到小。
     * 经过如此排序后，按照hi进行比较，如果hi<hj（i<j），可以保证(wi,hi)一定能够放入(wj,hj)
     * 经过如此排序的步骤之后，求最大套娃相当于求hi的最长上升子序列，这就回到了T300
     */
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0];
            }
        });

        int n = envelopes.length;
        int[] d = new int[n];
        d[0] = envelopes[0][1];
        int len = 1;
        for (int i = 1; i < n; i++) {
            if (d[len - 1] < envelopes[i][1]) {
                d[len++] = envelopes[i][1];
            } else {
                // 通过二分找到它的插入位置（第一个>=envelopes[i][1]的位置）
                int l = 0, r = len - 1;
                while (l <= r) {
                    int mid = (l + r) / 2;
                    if (d[mid] < envelopes[i][1]) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                d[l] = envelopes[i][1];
            }
        }
        return len;
    }

    /*
     * 2901. 最长相邻不相等子序列 II [Medium] <Star>
     * 
     * dp[i]表示words[i]开头，words[i+1..n-1]为后缀所能组成的最大长度
     * 则dp[i]=1+max(dp[j]) i<j<n&&groups[i]!=groups[j]&&ham==1
     * maxLen = max(dp[i])
     * 同时记录最优转移状态，from[]
     */
    public List<String> getWordsInLongestSubsequence(String[] words, int[] groups) {
        int n = words.length;
        int[] dp = new int[n];
        int[] from = new int[n];

        // 标记dp中最大元素的idx，在构造dp的时候更新maxIdx
        int maxIdx = n - 1;

        for (int i = n - 1; i >= 0; i--) {
            // 找到max(dp[j])
            for (int j = i + 1; j < n; j++) {
                // 提前比较dp[j]和当前已经遍历到的max(dp[j])，如果小于等于的话不用执行后续的判断
                if (dp[j] > dp[i] && groups[j] != groups[i] && verify(words[j], words[i])) {
                    dp[i] = dp[j];
                    from[i] = j;
                }
            }
            dp[i]++;

            if (dp[i] > dp[maxIdx]) {
                maxIdx = i;
            }
        }

        List<String> ans = new ArrayList<>();
        int len = dp[maxIdx];
        for (int i = 0; i < len; i++) {
            ans.add(words[maxIdx]);
            maxIdx = from[maxIdx];
        }
        return ans;
    }

    // 判断两个字符串的汉明距离是否是1
    boolean verify(String s1, String s2) {
        int len1 = s1.length(), len2 = s2.length();
        if (len1 != len2)
            return false;

        int diff = 0;
        for (int i = 0; i < len1; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (diff > 0)
                    return false;
                diff++;
            }
        }
        return true;
    }

    @Test
    public void test() {
        // minimumMountainRemovals(new int[] { 2, 1, 1, 5, 6, 2, 3, 1 });
        // kIncreasing(new int[] { 5, 4, 3, 2, 1 }, 1);
        // findNumberOfLIS(new int[] { 1, 1, 1, 2, 2, 2, 3, 3, 3 });
        minDeletionSize(new String[] {"babca","bbazb"});
    }
}
