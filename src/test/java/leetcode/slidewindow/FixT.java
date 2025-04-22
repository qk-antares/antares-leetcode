package leetcode.slidewindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class FixT {
    /*
     * 1456. 定长子串中元音的最大数目 [Medium]
     */
    public int maxVowels(String s, int k) {
        char[] arr = s.toCharArray();
        int cnt = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            // 进
            char in = arr[i];
            if (in == 'a' || in == 'e' || in == 'i' || in == 'o' || in == 'u') {
                cnt++;
            }
            if (i < k - 1) {
                continue;
            }

            // 更新
            ans = Math.max(ans, cnt);

            // 出
            char out = arr[i - k + 1];
            if (out == 'a' || out == 'e' || out == 'i' || out == 'o' || out == 'u') {
                cnt--;
            }
        }
        return ans;
    }

    /*
     * 643. 子数组最大平均数 I [Easy]
     * 
     * 这题没必要对maxAvg进行更新，因为avg是一个double类型，而且还要除以k来进行计算
     * 从另一角度，更新maxSum即可
     */
    public double findMaxAverage(int[] nums, int k) {
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            // 入
            sum += nums[i];
            if (i < k - 1)
                continue;

            // 更新
            maxSum = Math.max(maxSum, sum);

            // 出
            sum -= nums[i - k + 1];
        }

        return maxSum / (double) k;
    }

    /*
     * 1343. 大小为 K 且平均值大于等于阈值的子数组数目 [Medium]
     */
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int ans = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            // 入
            sum += arr[i];
            if (i < k - 1)
                continue;

            // 更新
            if (sum / k >= threshold)
                ans++;

            // 出
            sum -= arr[i - k + 1];
        }

        return ans;
    }

    /*
     * 2090. 半径为 k 的子数组平均值 [Medium]
     * 
     * 这题的坑点在于，半径中的子数组之和有可能超出int范围
     * 另外一个技巧点在于，可以将ans初始化为-1
     */
    public int[] getAverages(int[] nums, int k) {
        int n = nums.length;
        int range = 2 * k + 1;
        long sum = 0;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);

        for (int i = 0; i < n; i++) {
            // 入
            sum += nums[i];
            if (i < range - 1)
                continue;

            // 更新
            ans[i - k] = (int) (sum / range);

            // 出
            sum -= nums[i - range + 1];
        }

        return ans;
    }

    /*
     * 2379. 得到 K 个黑块的最少涂色次数
     * 
     * 相当于统计窗口中的最少白色块数
     */
    public int minimumRecolors(String blocks, int k) {
        int minW = k;
        int cnt = 0;
        char[] arr = blocks.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            // 入
            if (arr[i] == 'W') {
                cnt++;
            }
            // 更新
            if (i < k - 1)
                continue;

            minW = Math.min(minW, cnt);

            // 出
            if (arr[i + 1 - k] == 'W') {
                cnt--;
            }
        }
        return minW;
    }

    /*
     * 2841. 几乎唯一子数组的最大和
     */
    public long maxSum(List<Integer> nums, int m, int k) {
        long ans = 0;
        long sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.size(); i++) {
            // 入
            int num = nums.get(i);
            map.put(num, map.getOrDefault(num, 0) + 1);
            sum += num;

            if (i < k - 1)
                continue;

            // 更新
            if (map.size() >= m)
                ans = Math.max(ans, sum);

            // 出
            num = nums.get(i + 1 - k);
            sum -= num;
            int oldCnt = map.get(num);
            if (oldCnt == 1)
                map.remove(num);
            else
                map.put(num, oldCnt - 1);
        }
        return ans;
    }

    /*
     * 1423. 可获得的最大点数 [Medium]
     * 
     * 有正向思维和逆向思维两种解法：
     * 对于正向思维：首先计算前k个点数的和，接着开始移动窗口（+末尾的点数，-前面的点数），边移动窗口，边记录窗口的最大和
     * 对于逆向思维：假设n张牌，拿完k张剩n-k，剩下的这些牌相当于一个窗口，且窗口内的牌点数之和最小
     * 
     * 需要注意的小点是，逆向思维需要对"把所有n张牌都拿走"这种特殊情况进行单独判断
     * 由于测试用例的设置问题（k通常较小），所以正向思维的解法时间效率会更高
     */
    public int maxScore0(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int sum = 0;
        int tot = 0;
        int minSum = Integer.MAX_VALUE;
        int window = n - k;
        for (int i = 0; i < n; i++) {
            // 入
            tot += cardPoints[i];

            if (window != 0) {
                sum += cardPoints[i];
                if (i < window - 1)
                    continue;

                // 更新
                minSum = Math.min(minSum, sum);

                // 出
                sum -= cardPoints[i + 1 - n + k];
            }
        }

        return window == 0 ? tot : tot - minSum;
    }

    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += cardPoints[i];
        }

        int ans = sum;
        for (int i = 0; i < k; i++) {
            sum += cardPoints[n - 1 - i] - cardPoints[k - 1 - i];
            ans = Math.max(ans, sum);
        }

        return ans;
    }

    /*
     * 1052. 爱生气的书店老板
     * 
     * 相当于一个抑制情绪的固定窗口，从前往后移动这个窗口，并记录最大值
     * 首先计算，假设老板不抑制情绪，可以使多少顾客满意
     * 接着通过滑动窗口，计算抑制情绪的窗口，最多可以让多少不满变满意，最后加上这个delta即可
     * 当然上述两个步骤还可以在一个for循环中同步进行
     */
    public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
        int delta = 0;
        int maxDelta = 0;
        int ans = 0;
        for (int i = 0; i < customers.length; i++) {
            // 入窗口
            if (grumpy[i] == 0) {
                ans += customers[i];
            } else {
                delta += customers[i];
            }
            if (i < minutes - 1)
                continue;

            // 更新
            maxDelta = Math.max(maxDelta, delta);

            // 出（这里需要做额外判断，只有对应的时刻生气才出）
            delta -= grumpy[i + 1 - minutes] * customers[i + 1 - minutes];
        }

        return ans + maxDelta;
    }

    /*
     * 1652. 拆炸弹
     * 
     * 用滑动窗口来做替换
     */
    public int[] decrypt(int[] code, int k) {
        // 首先计算前k个元素组成的窗口
        int sum = 0;
        int absK = Math.abs(k);
        for (int i = 0; i < absK; i++) {
            sum += code[i];
        }

        int n = code.length;
        int[] ans = new int[n];

        if (k > 0) {
            for (int i = 0; i < n; i++) {
                // 相当于n-1-i是一个整体
                ans[n - 1 - i] = sum;
                sum += code[n - 1 - i] - code[(absK - 1 - i + n) % n];
            }
        } else {
            for (int i = 0; i < n; i++) {
                // 相当于i是一个整体
                ans[(i + absK) % n] = sum;
                sum += code[(i + absK) % n] - code[i];
            }
        }
        return ans;
    }

    /*
     * 3439. 重新安排会议得到最多空余时间 I
     * 
     * 将会议与会议中间的空余时间看作一个nums数组
     * 每平移1个会议，相当于合并nums中的2个元素，则平移k个会议，相当于nums中k+1大小的窗口
     * 变成了求nums中k+1的窗口的最大元素和
     */
    public int maxFreeTime(int eventTime, int k, int[] startTime, int[] endTime) {
        List<Integer> nums = new ArrayList<>();
        int n = startTime.length;
        int end = 0;
        for (int i = 0; i < n; i++) {
            nums.add(startTime[i] - end);
            end = endTime[i];
        }
        nums.add(eventTime - endTime[n - 1]);

        int sum = 0;
        int ans = 0;
        n = nums.size();
        for (int i = 0; i < n; i++) {
            // 入
            sum += nums.get(i);
            if (i < k)
                continue;

            // 更新
            ans = Math.max(ans, sum);

            // 出
            sum -= nums.get(i - k);
        }
        return Math.max(ans, sum);
    }

    /*
     * 2134. 最少交换次数来组合所有的 1 II
     * 
     * 首先对nums进行1次遍历，统计出包含了多少个1
     * 假设它包含cnt个1吧，然后就用cnt大小的窗口对nums进行一次"环形遍历"
     * 遍历的过程中记录窗口中1的最多值（maxSum），结果即为cnt-maxSum
     */
    public int minSwaps(int[] nums) {
        int cnt = 0;
        for (int num : nums)
            cnt += num;

        int sum = 0;
        for (int i = 0; i < cnt; i++)
            sum += nums[i];

        int maxSum = sum;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum += nums[(cnt + i) % n] - nums[i];
            maxSum = Math.max(sum, maxSum);
        }
        return cnt - maxSum;
    }

    /*
     * 1297. 子串的最大出现次数 [Medium]
     * 
     * 实际上只需要统计minSize这样的一个窗口即可，maxSize这个参数是完全没有意义的
     * 想象一个maxSize的子串出现了n次，那么该子串的子串一定也出现了n次
     * 所以可以用minSize的窗口，统计窗口中的字母数量，如果字母数量满足maxLetters的条件，将子串的Map计数+1
     * 最后return Map中的最大值即可
     * 上述过程可能会想到用TreeMap，但TreeMap是根据键来进行排序的，所以不可行
     */
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        int[] cnt = new int[26];
        char[] arr = s.toCharArray();
        int ans = 0;
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            cnt[arr[i] - 'a']++;
            if (i < minSize - 1)
                continue;

            // 判断子串是否有效
            if (valid(cnt, maxLetters)) {
                String substr = s.substring(i - minSize + 1, i + 1);
                int update = map.getOrDefault(substr, 0) + 1;
                ans = Math.max(ans, update);
                map.put(substr, update);
            }

            cnt[arr[i - minSize + 1] - 'a']--;
        }
        return ans;
    }

    boolean valid(int[] cnt, int maxLetters) {
        int diff = 0;
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i] != 0)
                diff++;
        }
        return diff <= maxLetters;
    }

    /*
     * 2653. 滑动子数组的美丽值 [Medium] <Star>
     * 
     * 用大小为k的堆去维护这样的一个窗口，这应该是一个大根堆，如果遇到了比根元素更小的元素才往堆中添加元素
     * 这么做会超时
     * 
     * 根据题目的条件，nums的值域较小，可以用一个cnt数组一直统计窗口中各个数字的出现次数，然后据此来获取第x小的数
     */
    public int[] getSubarrayBeauty(int[] nums, int k, int x) {
        // 共计可能出现101种数字
        int[] cnt = new int[101];
        for (int i = 0; i < k - 1; i++) {
            cnt[nums[i] + 50]++;
        }

        int n = nums.length;
        int[] ans = new int[n - k + 1];
        for (int i = 0; i < n - k + 1; i++) {
            cnt[nums[i + k - 1] + 50]++;

            ans[i] = getXMin(cnt, x);

            cnt[nums[i] + 50]--;
        }

        return ans;
    }

    int getXMi0(int[] cnt, int x) {
        int i = 0;
        while (x > 0) {
            x -= cnt[i++];
        }
        return Math.min(0, i - 51);
    }

    int getXMin(int[] cnt, int x) {
        // 这里的小优化是，只枚举负值
        for (int i = 0; i < 50; i++) {
            x -= cnt[i];
            if (x <= 0)
                return i - 50;
        }

        return 0;
    }

    /*
     * 30. 串联所有单词的子串 [Hard] <Star>
     * 
     * 假设m=s.length(), n = words[i].length, w = words.length
     * 则s中包含m/n个单词
     * 
     * 还要考虑words中有重复的单词...
     * 还要考虑这个子串的起点，不一定是n的整倍数...
     * 
     * 因此这是一个带有外层循环（遍历窗口的起点）的固定窗口
     */
    public List<Integer> findSubstring(String s, String[] words) {
        int m = s.length(), n = words[0].length(), w = words.length;
        Map<String, Integer> wordsMap = new HashMap<>();
        for (int i = 0; i < w; i++) {
            wordsMap.put(words[i], wordsMap.getOrDefault(words[i], 0) + 1);
        }

        List<Integer> ans = new ArrayList<>();
        // 枚举各种起点
        for (int i = 0; i < n; i++) {
            // 这种起点超出了s的长度
            if (i + w * n > m)
                break;

            Map<String, Integer> diff = new HashMap<>(wordsMap);
            for (int j = i; j <= m - n; j += n) {
                String substr = s.substring(j, j + n);
                int update = diff.getOrDefault(substr, 0) - 1;
                if (update == 0)
                    diff.remove(substr);
                else
                    diff.put(substr, update);

                if (j - i + n < w * n)
                    continue;

                if (diff.size() == 0)
                    ans.add(j - (w - 1) * n);

                substr = s.substring(j - (w - 1) * n, j - (w - 2) * n);
                update = diff.getOrDefault(substr, 0) + 1;
                if (update == 0)
                    diff.remove(substr);
                else
                    diff.put(substr, update);
            }
        }
        return ans;
    }

    /*
     * 438. 找到字符串中所有字母异位词
     * 
     * 类似的题都有两种写法吧，一种是使用HashMap来统计次数，而另一种是使用一个固定大小的int[] cnt数组
     * 当Map为空或者cnt数组全0时，代表找到了一个满足条件的答案
     * 实测下来，还是使用数组的这种解法效率更高
     */
    public List<Integer> findAnagrams0(String s, String p) {
        // 统计p中各个字符的出现次数（HashMap）
        Map<Character, Integer> diff = new HashMap<>();
        char[] pArr = p.toCharArray();

        for (char ch : pArr) {
            diff.put(ch, diff.getOrDefault(ch, 0) + 1);
        }

        // 在s中使用相同的窗口大小，如果diff空了，则证明窗口中的子串是一个异位词
        char[] sArr = s.toCharArray();
        int w = pArr.length;
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < sArr.length; i++) {
            int update = diff.getOrDefault(sArr[i], 0) - 1;
            if (update == 0)
                diff.remove(sArr[i]);
            else
                diff.put(sArr[i], update);

            if (i < w - 1)
                continue;

            if (diff.isEmpty())
                ans.add(i - w + 1);

            update = diff.getOrDefault(sArr[i - w + 1], 0) + 1;
            if (update == 0)
                diff.remove(sArr[i - w + 1]);
            else
                diff.put(sArr[i - w + 1], update);
        }

        return ans;
    }

    public List<Integer> findAnagrams(String s, String p) {
        // 统计p中各个字符的出现次数（大小为26的cnt数组）
        int[] cnt = new int[26];
        char[] pArr = p.toCharArray();

        for (char ch : pArr) {
            cnt[ch - 'a']++;
        }

        char[] sArr = s.toCharArray();
        int w = pArr.length;
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < sArr.length; i++) {
            cnt[sArr[i] - 'a']--;
            if (i < w - 1)
                continue;

            if (verify(cnt))
                ans.add(i - w + 1);

            cnt[sArr[i - w + 1] - 'a']++;
        }

        return ans;
    }

    boolean verify(int[] cnt) {
        for (int c : cnt) {
            if (c != 0)
                return false;
        }
        return true;
    }

    /*
     * 1888. 使二进制字符串字符交替的最少反转次数
     * 
     * 大小为2的滑动窗口，统计11，00，01，10的出现次数
     * 对于11，00这样的数对，它们必须修改1次
     * 对于01或10，取它们中较少的那一类进行修改
     * 如果最后多一个1或0，可以随便放
     * 
     * 另外要枚举起点了
     * 
     * 上述的实现会超时
     * 
     * 实际上枚举起点这个操作，等价于把(s || s)拼接起来，然后在拼接后的这个字符串上移动大小为n的窗口
     * 0的ASCII码是48，1的ASCII码是49，因此0%2='0'%2
     */
    public int minFlips(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        int ans = n;
        int cnt = 0;
        // 外层循环枚举起点（期望窗口中是0101..的序列）
        for (int i = 0; i < 2 * n; i++) {
            if (arr[i % n] % 2 != i % 2)
                cnt++;
            if (i < n - 1)
                continue;

            ans = Math.min(ans, Math.min(cnt, n - cnt));

            if (arr[(i - n + 1) % n] % 2 != (i - n + 1) % 2) {
                cnt--;
            }
        }

        return ans;
    }

    @Test
    public void test() {
        // System.out.println("ling mind rabo ofoo owin gdin gbar rwin gmon keyp ound
        // cake".length());
        // findSubstring("barfoothefoobarman", new String[]{"foo", "bar"});
        findSubstring("wordgoodgoodgoodbestword", new String[] { "word", "good", "best", "good" });
        // System.out.println(maxSum(Arrays.asList(1, 2, 2), 2, 2));
    }
}
