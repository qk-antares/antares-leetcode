package leetcode.slidewindow.fix;

import java.util.Arrays;

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
     * 2090. 半径为 k 的子数组平均值
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
}
