package leetcode.slidewindow.varT;

import java.util.HashMap;
import java.util.Map;

/*
 * [变长滑动窗口] / [求最长/最短]
 */
public class LongShortEstT {
    /*
     * 3. 无重复字符的最长子串 [Medium]
     * 
     * 用l和r分别标记窗口的左右端点。
     * r向右扩展，直至不满足条件
     * l向右扩展，直至满足条件
     * 这样一个迭代的过程
     * 虽然在代码上我们会看到两个while循环，但算法整体的时间复杂度是O(n)
     */
    public int lengthOfLongestSubstring(String s) {
        int ans = 0;
        int l = 0, r = 0;
        int[] cnt = new int[128];
        char[] arr = s.toCharArray();
        int n = arr.length;
        while (r < n) {
            int add = arr[r];
            cnt[add]++;
            r++;

            while (cnt[add] > 1) {
                cnt[arr[l]]--;
                l++;
            }
            ans = Math.max(ans, r - l);
        }
        return ans;
    }

    /*
     * 3090. 每个字符最多出现两次的最长子字符串 [Easy]
     * 
     * 完全同上
     */
    public int maximumLengthSubstring(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        int l = 0, r = 0;
        int ans = 0;
        int[] cnt = new int[26];

        while (r < n) {
            int add = arr[r] - 'a';
            cnt[add]++;
            r++;

            while (cnt[add] > 2) {
                cnt[arr[l] - 'a']--;
                l++;
            }

            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 1493. 删掉一个元素以后全为 1 的最长子数组 [Medium]
     * 
     * 依然是变长滑动窗口
     * 只是序列中允许出现一个0
     */
    public int longestSubarray(int[] nums) {
        int l = 0, r = 0, n = nums.length;
        int ans = 0;
        int[] cnt = new int[2];
        while (r < n) {
            cnt[nums[r]]++;
            r++;

            while (cnt[0] > 1) {
                cnt[nums[l]]--;
                l++;
            }
            ans = Math.max(ans, r - l);
        }

        return ans - 1;
    }

    /*
     * 1208. 尽可能使字符串相等 [Medium]
     * 
     * 使用一个数组来标记各个位置的costs
     * 在costs上应用变长滑动窗口
     * 或者再优化一点，根本不需要costs（内存占用小，时间开销变大）
     */
    public int equalSubstring(String s, String t, int maxCost) {
        char[] ss = s.toCharArray();
        char[] tt = t.toCharArray();
        int n = ss.length;

        int l = 0, r = 0;
        int ans = 0;
        while (r < n) {
            maxCost -= Math.abs(ss[r] - tt[r]);
            r++;

            while (maxCost < 0) {
                maxCost += Math.abs(ss[l] - tt[l]);
                l++;
            }
            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 904. 水果成篮 [Medium]
     * 
     * 使用一个map来统计当前摘到的果子
     * 也可以把map换成一个数组，大小为n+1，效率稍高
     */
    public int totalFruit(int[] fruits) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int n = fruits.length;
        int l = 0, r = 0;
        int ans = 0;
        while (r < n) {
            cnt.put(fruits[r], cnt.getOrDefault(fruits[r], 0) + 1);
            r++;

            while (cnt.size() > 2) {
                int update = cnt.get(fruits[l]) - 1;
                if (update == 0)
                    cnt.remove(fruits[l]);
                else
                    cnt.put(fruits[l], update);
                l++;
            }

            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 1695. 删除子数组的最大得分 [Medium]
     */
    public int maximumUniqueSubarray(int[] nums) {
        int[] cnt = new int[10001];
        int n = nums.length;
        int l = 0, r = 0;
        int sum = 0;
        int ans = 0;
        while (r < n) {
            int add = nums[r];
            sum += add;
            cnt[add]++;
            r++;

            while (cnt[add] > 1) {
                int remove = nums[l];
                cnt[remove]--;
                sum -= remove;
                l++;
            }

            ans = Math.max(ans, sum);
        }

        return ans;
    }

    /*
     * 2958. 最多 K 个重复元素的最长子数组 [Medium]
     * 
     * 这题nums[i]的范围比较大，只能使用HashMap来统计各个元素的出现次数
     */
    public int maxSubarrayLength(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int n = nums.length;
        int l = 0, r = 0;
        int ans = 0;
        while (r < n) {
            int add = nums[r];
            int addCnt = cnt.getOrDefault(add, 0) + 1;
            cnt.put(add, addCnt);
            r++;

            while (addCnt > k) {
                int remove = nums[l];
                int removeCnt = cnt.get(remove) - 1;
                if (removeCnt == 0)
                    cnt.remove(remove);
                else
                    cnt.put(remove, removeCnt);
                if (remove == add)
                    addCnt--;
                l++;
            }

            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 2024. 考试的最大困扰度
     * 
     * 使用TCnt和FCnt分别统计窗口中出现的T和F（最好还是用一个数组，这样后面不用做T和F的字符的判断，好吧，实际效率差的不多）
     * 窗口中只允许有一个字符的出现次数超过2
     */
    public int maxConsecutiveAnswers(String answerKey, int k) {
        char[] s = answerKey.toCharArray();
        int n = s.length;
        int l = 0, r = 0;
        int ans = 0;
        int TCnt = 0, FCnt = 0;
        while (r < n) {
            if (s[r] == 'T')
                TCnt++;
            else
                FCnt++;
            r++;

            while (TCnt > k && FCnt > k) {
                if (s[l] == 'T')
                    TCnt--;
                else
                    FCnt--;
                l++;
            }

            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 1004. 最大连续1的个数 III
     * 
     * 变长滑动窗口，窗口中1的个数不限，0的个数最多k个，统计窗口长度最大值
     */
    public int longestOnes(int[] nums, int k) {
        int[] cnt = new int[2];
        int n = nums.length;
        int l = 0, r = 0;
        int ans = 0;
        while (r < n) {
            cnt[nums[r++]]++;
            while (cnt[0] > k) {
                cnt[nums[l++]]--;
            }
            ans = Math.max(ans, r - l);
        }
        return ans;
    }

    /*
     * 1658. 将 x 减到 0 的最小操作数
     */
    public int minOperations(int[] nums, int x) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum < x)
            return -1;

        // sum减去窗口中的值，直至等于x
        int n = nums.length;
        int ans = -1;
        int l = 0, r = 0;
        while (r < n) {
            sum -= nums[r++];

            while (sum < x) {
                sum += nums[l++];
            }

            if (sum == x) {
                ans = Math.max(ans, r - l);
            }
        }

        return ans == -1 ? -1 : n - ans;
    }
}
