package leetcode.slidewindow.varT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
     * 1658. 将 x 减到 0 的最小操作数 [Medium]
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

    /*
     * 2730. 找到最长的半重复子字符串 [Medium]
     * 
     * 用一个boolean数组flags，标记s[i]==s[i-1]
     * 接下来在flags上使用滑动窗口，窗口中最多框住一个true，统计窗口的最大长度，再加上1就是答案
     */
    public int longestSemiRepetitiveSubstring(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        boolean[] flags = new boolean[n];
        for (int i = 1; i < n; i++) {
            flags[i] = arr[i] == arr[i - 1];
        }

        int ans = 0;
        int cnt = 0;
        int l = 1, r = 1;
        while (r < n) {
            if (flags[r++])
                cnt++;

            while (cnt == 2) {
                if (flags[l++])
                    cnt--;
            }

            ans = Math.max(ans, r - l);
        }
        return ans + 1;
    }

    /*
     * 2779. 数组的最大美丽值
     * 
     * 这里的子序列不考虑连续，所以可以先对nums进行排序在进行后续的处理
     * 滑动窗口中元素的极差<= 2k
     */
    public int maximumBeauty(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int l = 0, r = 0;

        int ans = 0;
        while (r < n) {
            while (nums[r] - nums[l] > k * 2)
                l++;
            r++;
            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 1838. 最高频元素的频数
     * 
     * 首先依然排序，然后变长滑动窗口
     * 由于操作只能是+1，所以将窗口最右侧的那个值视为target
     * 
     * 具体到这一题有几个注意点
     * 1. 统计当前的+1次数时（必须这么做，不能直接利用k），要用long类型，防止可能发生的溢出（1个测试用例）
     * 2. 这道题的l和r都是闭区间
     */
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);

        int n = nums.length;
        // 这里是闭区间
        int l = 0, r = 1;
        // 目前+1次数，注意用long防止溢出
        long cnt = 0;
        int ans = 1;
        while (r < n) {
            cnt += (long) (nums[r] - nums[r - 1]) * (r - l);

            while (cnt > k) {
                cnt -= nums[r] - nums[l];
                l++;
            }

            r++;
            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 2516. 每种字符至少取 K 个
     * 
     * 逆向思维
     * 首先统计s中各个字符的数量cnt
     * 然后用滑动窗口，窗口中圈住相应字符，统计最大窗口大小
     */
    public int takeCharacters(String s, int k) {
        char[] arr = s.toCharArray();
        int[] cnt = new int[3];

        for (char ch : arr)
            cnt[ch - 'a']++;

        if (cnt[0] < k || cnt[1] < k || cnt[2] < k)
            return -1;

        int n = arr.length;
        int l = 0, r = 0;
        int maxW = 0;
        while (r < n) {
            cnt[arr[r] - 'a']--;
            r++;

            while (cnt[0] < k || cnt[1] < k || cnt[2] < k) {
                cnt[arr[l] - 'a']++;
                l++;
            }

            maxW = Math.max(maxW, r - l);
        }

        return n - maxW;
    }

    /*
     * 2831. 找出最长等值子数组
     * 
     * 变长滑动窗口
     * 注意这道题nums的值域，由于1<=nums[i]<=nums.length，实际上可以利用数组对窗口中的数字进行统计，而不是哈希表，效率更高
     * 窗口中出现次数最多的元素，它的cnt+k>=窗口长度
     * 上述算法超时
     * 
     * 首先使用List<List<Integer>> posLists（大小为n+1），统计nums中各个数字出现的下标
     * 接着for循环对posLists进行遍历
     * 假设我们现在遍历的pos好了
     * 如果pos.length <= ans，这意味着我们无论怎么删，都不可能得到一个更大的答案，直接continue
     * 否则对pos使用变长滑动窗口，窗口的左右端点分别是l=0,r=1（闭区间）
     * 窗口中每添加一个元素，这意味着我们要删除pos[r]-pos[r-1]-1个元素
     * 如果删除的元素数量>k，则移动左端点，这意味着我们要删除的元素，减少了pos[l+1]-pos[l]-1
     * 滑动窗口的过程中，记录最长等值序列
     * 
     * 上述过程还有一个更简化的写法，只是比较难想到：
     * 窗口中添加一个元素，意味着我们要删除pos[r]-pos[l]-(r-l)个元素（不是在原来的基础上新增）
     * 发现上式其实有一定规律，我们可以在记录pos的时候，直接存pos[i]-i
     */
    public int longestEqualSubarray0(List<Integer> nums, int k) {
        int n = nums.size();
        int[] cnt = new int[n + 1];
        int l = 0, r = 0;
        int ans = 0;
        while (r < n) {
            cnt[nums.get(r)]++;
            r++;

            while (deleteCnt(cnt) > k) {
                cnt[nums.get(l)]--;
                l++;
            }

            ans = Math.max(ans, r - l - deleteCnt(cnt));
        }

        return ans;
    }

    int deleteCnt(int[] cnt) {
        int sumCnt = 0;
        int maxCnt = 0;
        for (int c : cnt) {
            sumCnt += c;
            maxCnt = Math.max(maxCnt, c);
        }
        return sumCnt - maxCnt;
    }

    @SuppressWarnings("unchecked")
    public int longestEqualSubarray(List<Integer> nums, int k) {
        int n = nums.size();
        List<Integer>[] posLists = new ArrayList[n + 1];
        Arrays.setAll(posLists, i -> new ArrayList<>());
        for (int i = 0; i < n; i++) {
            posLists[nums.get(i)].add(i);
        }

        int ans = 0;
        for (List<Integer> pos : posLists) {
            int m = pos.size();
            if (m <= ans)
                continue;

            int cnt = 0;
            int l = 0, r = 0;
            while (r < m) {
                cnt = pos.get(r) - pos.get(l) - (r - l);

                while (cnt > k) {
                    l++;
                    cnt = pos.get(r) - pos.get(l) - (r - l);
                }

                r++;
                ans = Math.max(ans, r - l);
            }

        }

        return ans;
    }

    /*
     * ========================== 分割线 ==========================
     */

}
