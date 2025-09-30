package leetcode.datastruture.diffArr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

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
     * 1854. 人口最多的年份 [Easy]
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
     * 面试题 16.10. 生存人数 [Medium]
     */
    //年份的区间是[1900,2000]，这可以用一个长度2001的数组表示，则其差分数组的长度是2002
    public int maxAliveYear(int[] birth, int[] death) {
        int[] d = new int[2002];
        for(int i = 0; i < birth.length; i++) {
            d[birth[i]]++;
            d[death[i]+1]--;
        }

        int max = 0;
        int maxIdx = -1;
        int s = 0;
        for(int i = 0; i <= 2000; i++) {
            s += d[i];
            if(s > max) {
                max = s;
                maxIdx = i;
            }
        }

        return maxIdx;
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
     * 1109. 航班预订统计 [Medium]
     * 
     * 用cnt来表示每个航班上的预定座位总数
     * d是cnt的差分数组
     */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] d = new int[n + 2];
        for (int[] b : bookings) {
            d[b[0]] += b[2];
            d[b[1] + 1] -= b[2];
        }

        int[] cnt = new int[n];
        int s = 0;
        for (int i = 1; i <= n; i++) {
            s += d[i];
            cnt[i - 1] = s;
        }
        return cnt;
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
     * 56. 合并区间 [Medium] [ps: 似乎和差分并没有什么关系]
     * 
     * 下述的差分数组做法很难写：
     * 用cnt来标记每个位置上被覆盖的区间数
     * d是cnt的差分数组
     * 本题的区间范围较小
     * 
     * 这主要是因为可能存在如下形式的测试用例：
     * [[1,4],[0,0]]
     * 差分数组的方法很难区分被覆盖的"点"和"区间"
     * 它们本质上是两个概念
     */
    public int[][] merge0(int[][] intervals) {
        int[] d = new int[10002];
        for (int[] r : intervals) {
            d[r[0]]++;
            d[r[1]]--;
        }

        // 还原cnt数组，直接在d上操作
        // 一边还原，一边开始生成答案
        List<int[]> ans = new ArrayList<>();
        int l = d[0] == 0 ? -1 : 0;
        int r = l;
        for (int i = 1; i <= 10000; i++) {
            d[i] += d[i - 1];
            if (d[i] != 0) {
                if (l == -1) {
                    l = i;
                    r = i;
                } else
                    r = i;
            } else {
                if (l != -1) {
                    ans.add(new int[] { l, r + 1 });
                    l = -1;
                    r = -1;
                }
            }
        }

        if (l != -1)
            ans.add(new int[] { l, r + 1 });

        return ans.toArray(new int[0][]);
    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
        List<int[]> ans = new ArrayList<>();
        ans.add(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            // 起点大于当前区间的终点
            if (intervals[i][0] > ans.get(ans.size() - 1)[1]) {
                ans.add(intervals[i]);
            } else {
                ans.get(ans.size() - 1)[1] = Math.max(ans.get(ans.size() - 1)[1], intervals[i][1]);
            }
        }

        return ans.toArray(new int[ans.size()][]);
    }

    /*
     * 57. 插入区间 [Medium] [ps: 似乎和差分并没有什么关系]
     * 
     * 对intervals中的每个区间进行遍历
     * 只要该区间和newInterval有交集，就将它们合并：
     * newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
     * newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> ans = new ArrayList<>();
        boolean flag = false;
        for (int i = 0; i < intervals.length; i++) {
            // 无交集
            if (intervals[i][1] < newInterval[0])
                ans.add(intervals[i]);
            else if (intervals[i][0] > newInterval[1]) {
                if (!flag) {
                    ans.add(newInterval);
                    flag = true;
                }
                ans.add(intervals[i]);
            }
            // 有交集
            else {
                newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
                newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            }
        }

        if (!flag)
            ans.add(newInterval);

        return ans.toArray(new int[0][]);
    }

    /*
     * 732. 我的日程安排表 III [Hard]
     */
    class MyCalendarThree {
        TreeMap<Integer, Integer> d;

        public MyCalendarThree() {
            d = new TreeMap<>();
        }

        public int book(int startTime, int endTime) {
            d.put(startTime, d.getOrDefault(startTime, 0) + 1);
            d.put(endTime, d.getOrDefault(endTime, 0) - 1);
            int ans = 0;
            int s = 0;
            for (Map.Entry<Integer, Integer> e : d.entrySet()) {
                s += e.getValue();
                ans = Math.max(ans, s);
            }
            return ans;
        }
    }

    /*
     * 2406. 将区间分为最少组数 [Medium]
     * 
     * 计算某个位置上相交的区间的最大值，即为最少需要划分的组数
     * 由于区间的范围还是比较大的，使用TreeMap
     * 
     * 方法二是使用堆+贪心，效率更高：
     * 首先对intervals进行排序，按照左端点升序排列
     * 使用heap（PriorityQueue）维护各个组的右端点
     * 遍历intervals，当新加进来一个区间时：
     * 如果这个区间的左端点在堆顶的左边，则必须新增加一个组
     * 如果这个区间的左端点在堆顶的右侧，则将其加入堆顶的组，并更新其右端点
     * 最后堆的大小即为答案
     */
    public int minGroups0(int[][] intervals) {
        Map<Integer, Integer> d = new TreeMap<>();
        for (int[] i : intervals) {
            d.put(i[0], d.getOrDefault(i[0], 0) + 1);
            d.put(i[1] + 1, d.getOrDefault(i[1] + 1, 0) - 1);
        }

        int s = 0;
        int ans = 0;
        for (int val : d.values()) {
            s += val;
            ans = Math.max(ans, s);
        }

        return ans;
    }

    public int minGroups(int[][] intervals) {
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(intervals[0][1]);
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > pq.peek()) {
                pq.poll();
            }
            pq.offer(intervals[i][1]);
        }

        return pq.size();
    }

    /*
     * 3453. 分割正方形 I [Medium] <Star>
     * 
     * 用cnt来标记y轴上每个单元上的正方形面积
     * 每新增进来一个正方形[xi,yi,li]
     * 这相当于cnt[yi..yi+li-1]的范围内全部增加li
     * 用d来表示cnt的差分数组，上述操作等价于d[yi]+=li,d[yi+li]-=li
     * 然后用d来还原cnt，并记录总面积
     * 再次遍历cnt，直至到中位数
     * 此题的范围又比较大，所以用TreeMap
     */
    public double separateSquares(int[][] squares) {
        Map<Integer, Integer> d = new TreeMap<>();
        long totS = 0;
        for (int[] s : squares) {
            d.put(s[1], d.getOrDefault(s[1], 0) + s[2]);
            d.put(s[1] + s[2], d.getOrDefault(s[1] + s[2], 0) - s[2]);
            totS += (long) s[2] * s[2];
        }

        // 总面积
        long s = 0;
        // 之前的行面积
        long preSL = 0;
        int preY = -1;
        for (var e : d.entrySet()) {
            int y = e.getKey();
            s += preSL * (y - preY);
            if (s * 2 >= totS) {
                return y - (s - totS / 2.0) / preSL;
            }
            preY = y;
            preSL += e.getValue();
        }
        return -1;
    }

    /*
     * 2381. 字母移位 II [Medium]
     * 
     * 假设s的长度是n
     * 用一个长度为n的cnt数组来记录每个位置的移位数量
     * 因为每个shift相当于对cnt的区间操作
     * 用长度为n+1的d数组来表示cnt的差分数组
     * dir=1相当于d[l]+=1;d[r+1]-=1，dir=0类似
     * 之后一边还原cnt数组，一边构造答案
     * 
     * 需要注意最后ans[i]的计算方式
     */
    public String shiftingLetters(String s, int[][] shifts) {
        int n = s.length();
        char[] arr = s.toCharArray();
        char[] ans = new char[n];

        int[] d = new int[n + 1];
        for (int[] shift : shifts) {
            int l = shift[0];
            int r = shift[1];
            int dir = shift[2];
            if (dir == 1) {
                d[l] += 1;
                d[r + 1] -= 1;
            } else {
                d[l] -= 1;
                d[r + 1] += 1;
            }
        }

        int cnt = 0;
        for (int i = 0; i < n; i++) {
            cnt += d[i];
            ans[i] = (char) ('a' + (26 + arr[i] - 'a' + cnt % 26) % 26);
        }

        return new String(ans);
    }

    /*
     * 995. K 连续位的最小翻转次数 [Hard] <Star>
     * 
     * nums.length不是很大
     * 本题是贪心的策略，要想变成全1数组，只能从前往后进行翻转，遇到0就翻转从该位置开始的k个数
     * 使用差分数组d来记录各个位置被翻转的次数
     * 对nums进行一次遍历，边更新d
     * 如果到n-k的位置遇到了0，那就证明无法翻转成功
     */
    public int minKBitFlips(int[] nums, int k) {
        int ans = 0;
        int n = nums.length;
        int[] d = new int[n + 1];
        // s记录当前遍历位置的翻转总次数
        int s = 0;
        for (int i = 0; i < n; i++) {
            s += d[i];
            // [i..i+k-1]要进行1次翻转
            if (nums[i] == 0 && s % 2 == 0 || nums[i] == 1 && s % 2 == 1) {
                // 超出位置限制
                if (i > n - k)
                    return -1;
                ans++;
                d[i]++;
                d[i + k]--;
                s++;
            }
        }
        return ans;
    }

    /*
     * 1589. 所有排列中的最大和 [Medium]
     * 
     * 记录每个位置被查询的次数cnt
     * nums的范围不是特别大
     * d是cnt的差分数组
     * 根据d还原cnt
     * 将nums和cnt排序
     * ans=nums*cnt
     */
    public int maxSumRangeQuery(int[] nums, int[][] requests) {
        int n = nums.length;
        int[] d = new int[n + 1];
        for (int[] r : requests) {
            d[r[0]]++;
            d[r[1] + 1]--;
        }

        int s = 0;
        int[] cnt = new int[n];
        for (int i = 0; i < n; i++) {
            s += d[i];
            cnt[i] = s;
        }

        Arrays.sort(cnt);
        Arrays.sort(nums);

        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans = (ans + (long) nums[i] * cnt[i]) % 1_000_000_007;
        }

        return (int) ans;
    }

    /*
     * 1526. 形成目标数组的子数组最少增加次数 [Hard]
     * 
     * 构造target的差分数组d
     * 然后计算d中的正数之和
     */
    public int minNumberOperations(int[] target) {
        int n = target.length;
        int ans = 0;
        // d[0]=target[0]
        ans += Math.max(0, target[0]);
        for (int i = 1; i < n; i++) {
            ans += Math.max(0, target[i] - target[i - 1]);
        }
        return ans;
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

    /*
     * 3362. 零数组变换 III [Medium] <Star>
     */
    public int maxRemoval(int[] nums, int[][] queries) {
        Arrays.sort(queries, (o1, o2) -> o1[0] - o2[0]);
        PriorityQueue<Integer> q = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int n = nums.length;
        int[] d = new int[n + 1];
        int s = 0;
        int idx = 0;
        for (int i = 0; i < n; i++) {
            s += d[i];
            while (idx < queries.length && queries[idx][0] <= i) {
                q.add(queries[idx][1]);
                idx++;
            }
            // 选择右端点最大的区间
            while (!q.isEmpty() && s < nums[i] && q.peek() >= i) {
                s++;
                d[q.poll() + 1]--;
            }
            if (s < nums[i])
                return -1;

        }

        return q.size();
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 2200. 找出数组中的所有 K 近邻下标
     * 
     * 二分
     * 首先找出所有key的下标 n
     * 然后对nums中的每个i使用二分查找是否存在符合要求的key
     * 
     * 方法2是使用差分
     * 遍历nums，遇到一个key，相当于将[i-k, i+k]的区间+1，即差分数组d[Math.max(i-k,
     * 0)]++,d[Math.min(i+k+1, n)]--
     * 最后根据d构造s，s[i]>0则i满足题意
     * 
     * 方法3是滑动窗口，首先遍历nums的[0,k-1]的元素，记录key最后一次出现的位置idx
     * 接下来开始对nums从0进行遍历
     * 先更新idx，然后将当前遍历元素的i与idx进行比较
     */
    public List<Integer> findKDistantIndices0(int[] nums, int key, int k) {
        List<Integer> keyIdx = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == key)
                keyIdx.add(i);
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            // 二分查找第一个>=i-k的下标
            int l = 0, r = keyIdx.size() - 1;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (keyIdx.get(mid) < i - k) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            if (l < keyIdx.size() && keyIdx.get(l) <= i + k)
                ans.add(i);
        }

        return ans;
    }

    public List<Integer> findKDistantIndices1(int[] nums, int key, int k) {
        int n = nums.length;
        int[] d = new int[n+1];
        for(int i = 0; i < n; i++) {
            if(nums[i] == key) {
                d[Math.max(i-k, 0)]++;
                d[Math.min(i+k+1, n)]--;
            }
        }

        List<Integer> ans = new ArrayList<>();
        if(d[0] > 0) ans.add(0);
        for(int i = 1; i < n; i++) {
            d[i] += d[i-1];
            if(d[i] > 0) ans.add(i);
        }

        return ans;
    }

    public List<Integer> findKDistantIndices(int[] nums, int key, int k) {
        // 代表key没有出现
        int idx = -1;
        int n = nums.length;
        for (int i = 0; i < k; i++) {
            if (nums[i] == key)
                idx = i;
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int next = k + i;
            if (next < n && nums[next] == key)
                idx = next;
            if (idx >= 0 && Math.abs(idx - i) <= k)
                ans.add(i);
        }

        return ans;
    }

    @Test
    public void test() {
        // int[][] squares = { { 0, 0, 2 }, { 1, 1, 1 } };
        // System.out.println(separateSquares(squares));
    }

}
