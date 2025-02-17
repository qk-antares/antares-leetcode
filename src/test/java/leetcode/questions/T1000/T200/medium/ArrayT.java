package leetcode.questions.T1000.T200.medium;

import java.util.Arrays;

public class ArrayT {
    /*
     * 164. 最大间距
     * 
     * 解法1是使用暴力算法，首先使用常规的排序算法（nlogn），然后遍历数组寻找最大间距
     * 
     * 标准解法可以利用桶排序的思想
     * 桶排序的思想：
     * 1、首先根据数组的max和min值划分若干个桶
     * 2、遍历数组，将每个元素放入对应的桶中
     * 3、对每个桶使用常规的排序算法（nlogn）
     * 4、合并所有桶的结果
     * 如何迁移到本题中呢
     * 假设nums的极值是max和min，元素个数是n，则最大间距>=⌈(max-min)/(n-1)⌉（向上取整）
     * 则可以将桶的大小设置为d=⌊(max-min)/(n-1)⌋
     * 之后按照桶排序的步骤将元素依次放至桶中，则最大间距一定来自桶和桶之间的间距（一定不在桶的内部）
     * 维护每个桶的最大值和最小值，最后遍历各个桶即可得到答案
     * 
     * 这里有个小注意点是，Java中可以利用Arrays.stream(nums).min().getAsInt()和Arrays.stream(nums).max().getAsInt()获取数组的最大值和最小值
     */
    public int maximumGap0(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        for (int i = 1; i < nums.length; i++) {
            ans = Math.max(ans, nums[i] - nums[i - 1]);
        }
        return ans;
    }

    public int maximumGap(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return 0;
        }

        int max = Arrays.stream(nums).max().getAsInt();
        int min = Arrays.stream(nums).min().getAsInt();
        int d = Math.max(1, (max - min) / (n - 1));
        int bucketCnt = (max - min) / d + 1;

        int[][] buckets = new int[bucketCnt][2];  // 每个桶维护最大值和最小值
        for (int i = 0; i < buckets.length; i++) {
            Arrays.fill(buckets[i], -1);    // -1 表示桶为空
        }

        for (int i = 0; i < nums.length; i++) {
            int index = (nums[i] - min) / d;
            if (buckets[index][0] == -1) {  // 桶为空，填充首个元素
                buckets[index][0] = buckets[index][1] = nums[i];
            } else {
                buckets[index][0] = Math.min(buckets[index][0], nums[i]);
                buckets[index][1] = Math.max(buckets[index][1], nums[i]);
            }
        }
        

        int ans = 0;
        int prev = buckets[0][1];
        for (int i = 1; i < buckets.length; i++) {
            if (buckets[i][0] != -1) {
                ans = Math.max(ans, buckets[i][0] - prev);
                prev = buckets[i][1];
            }
        }
        return ans;
    }
}
