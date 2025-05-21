package leetcode.algorithm.diffArr;

import java.util.List;

public class OneDDiffArrT {
    /*
     * 2848. 与车相交的点 [Easy]
     * 
     * 假设a表示每个点被覆盖的区间数
     * nums[i]=[starti,endi]，相当于a[starti..endi]这段++
     * 假设d是a的差分数组，则上述操作又等价于
     * d[starti]++,d[endi+1]--
     */
    public int numberOfPoints(List<List<Integer>> nums) {
        int[] d = new int[102];
        for (List<Integer> range : nums) {
            d[range.get(0)]++;
            d[range.get(1) + 1]--;
        }

        int ans = 0;
        int s = 0;
        for (int i = 1; i <= 100; i++) {
            s += d[i];
            if (s > 0)
                ans++;
        }
        return ans;
    }

    /*
     * 1893. 检查是否区域内所有整数都被覆盖 [Easy]
     */
    public boolean isCovered(int[][] ranges, int left, int right) {
        int[] d = new int[52];
        for (int[] r : ranges) {
            d[r[0]]++;
            d[r[1] + 1]--;
        }

        int s = 0;
        for (int i = 1; i <= right; i++) {
            s += d[i];
            if (s == 0 && i >= left)
                return false;
        }
        return true;
    }

    /*
     * 1854. 人口最多的年份
     * 
     * 用cnt来表示各个年份的人口数量
     * d是cnt的差分数组
     * 每个log相当于cnt[log[0]..log[1]-1]++
     * 相当于d[log[0]]++,d[log[1]]--
     * 最后根据差分数组还原cnt，并统计最大值
     */
    public int maximumPopulation(int[][] logs) {
        int[] cnt = new int[102];
        for (int[] log : logs) {
            cnt[log[0] - 1950]++;
            cnt[log[1] - 1950]--;
        }

        int s = 0;
        int max = 0;
        int ans = 1950;
        for (int i = 0; i <= 100; i++) {
            s += cnt[i];
            if (s > max) {
                max = s;
                ans = i + 1950;
            }
        }
        return ans;
    }

    /*
     * 1094. 拼车 [Medium] <Star>
     * 
     * 数组a代表在每个位置上的乘客数
     * 数组d是a的差分数组
     * 则对于每个trips[i]=[num,from,to]
     * 相当于a[from..to-1]+=num
     * 这种对原数组一个区间上的操作，可以转换为差分数组上两个元素的操作，即：
     * 相当于d[from]+=num，d[to]-=num
     */
    public boolean carPooling(int[][] trips, int capacity) {
        int[] d = new int[1001];
        for (int[] t : trips) {
            d[t[1]] += t[0];
            d[t[2]] -= t[0];
        }

        int s = 0;
        for (int i = 0; i < d.length; i++) {
            s += d[i];
            if (s > capacity)
                return false;
        }
        return true;
    }

    /*
     * 3355. 零数组变换 I [Medium]
     * 
     * 由于nums.length的范围较小([1,100_000])，所以可以使用一个数组cnt统计各个位置最大可以减多少
     * 
     * 方法1对于每个query[i]，使用for循环来增加cnt数组的值
     * 当query[i]的范围较大时，上述方法会超时
     * 
     * 方法2是使用差分数组
     * 假设d是cnt的差分数组
     * 则cnt[l..r]这段+1 等价于d[l]++,d[r+1]--
     * 
     * 对于差分这种方法，可以发现如下规律：
     * 1. 差分数组的长度比原数组多1，这是因为d[r+1]--，r+1后可能超出原数组下标
     * 2. 在从差分数组构造回原数组的时候，不要遍历差分数组的最后一个元素
     */
    public boolean isZeroArray0(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] cnt = new int[n];
        for (int[] q : queries) {
            for (int i = q[0]; i <= q[1]; i++) {
                cnt[i]++;
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] > cnt[i])
                return false;
        }

        return true;
    }

    public boolean isZeroArray(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] d = new int[n + 1];
        for (int[] q : queries) {
            d[q[0]]++;
            d[q[1] + 1]--;
        }

        int s = 0;
        for (int i = 0; i < nums.length; i++) {
            s += d[i];
            if (s < nums[i])
                return false;
        }
        return true;
    }

    /*
     * 3356. 零数组变换 II [Medium] <Star>
     * 
     * 假设cnt是每个位置被减的最大值
     * 对于queries[i]=[li,ri,vali]，它相当于cnt[li..ri]+=vali
     * 用d表示cnt的差分，相当于d[li]+=vali，d[ri+1]-=vali
     * 每执行一次queries[i]，就将d与nums进行比较
     * 
     * 上述方法超时
     * 
     * 可使用二分+差分来优化，即k是通过二分来枚举的
     * 
     * 也可只进行一次遍历，效率更高
     */
    public int minZeroArray0(int[] nums, int[][] queries) {
        int l = 0, r = queries.length;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (!verifyK(nums, queries, mid)) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l <= queries.length ? l : -1;
    }

    boolean verifyK(int[] nums, int[][] queries, int k) {
        int n = nums.length;
        int[] d = new int[n + 1];

        for (int i = 0; i < k; i++) {
            int[] q = queries[i];
            d[q[0]] += q[2];
            d[q[1] + 1] -= q[2];
        }

        int s = 0;
        for (int i = 0; i < n; i++) {
            s += d[i];
            if (s < nums[i])
                return false;
        }
        return true;
    }

    public int minZeroArray(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] d = new int[n + 1];
        int s = 0;
        int k = 0;
        for (int i = 0; i < n; i++) {
            s += d[i];
            // 如果不能满足，则一直执行新的queries[]
            while (k < queries.length && s < nums[i]) {
                int[] q = queries[k];
                d[q[0]] += q[2];
                d[q[1] + 1] -= q[2];
                if (q[0] <= i && i <= q[1]) {
                    s += q[2];
                }
                k++;
            }
            if (s < nums[i])
                return -1;
        }

        return k;
    }
}
