package leetcode.slidewindow.fix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class Basic {
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

    @Test
    public void test() {
        System.out.println(maxSum(Arrays.asList(1, 2, 2), 2, 2));
    }
}
