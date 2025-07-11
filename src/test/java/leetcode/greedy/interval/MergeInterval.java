package leetcode.greedy.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/*
 * 贪心合并区间
 */
public class MergeInterval {
    /*
     * 3169. 无需开会的工作日 [Medium]
     * 
     * 本质上是一个合并区间的题
     * 将所有的区间按照起始排序
     */
    public int countDays0(int days, int[][] meetings) {
        Arrays.sort(meetings, (o1, o2) -> o1[0] - o2[0]);
        List<int[]> intervals = new ArrayList<>();

        int n = meetings.length;
        int l = meetings[0][0], r = meetings[0][1];
        for (int i = 1; i < n; i++) {
            if (meetings[i][0] <= r + 1) {
                r = Math.max(r, meetings[i][1]);
            } else {
                intervals.add(new int[] { l, r });
                l = meetings[i][0];
                r = meetings[i][1];
            }
        }
        intervals.add(new int[] { l, r });

        int sum = 0;
        for (int[] interval : intervals) {
            sum += interval[1] - interval[0] + 1;
        }

        return days - sum;
    }

    public int countDays(int days, int[][] meetings) {
        Arrays.sort(meetings, (o1, o2) -> o1[0] - o2[0]);

        int n = meetings.length;
        int l = meetings[0][0], r = meetings[0][1];
        int sum = 0;
        for (int i = 1; i < n; i++) {
            if (meetings[i][0] <= r + 1) {
                r = Math.max(r, meetings[i][1]);
            } else {
                sum += r - l + 1;
                l = meetings[i][0];
                r = meetings[i][1];
            }
        }
        sum += r - l + 1;

        return days - sum;
    }

    /*
     * 2580. 统计将重叠区间合并成组的方案数 [Medium]
     * 
     * 首先合并区间
     * 显然合并成一个区间的若干个子区间，它们必然被分在同一个组内
     * 假设合并后有cnt个区间数，则答案=(1<<cnt)%MOD
     * 
     * 对上述思路进行优化，我们发现维护区间的l是不必要的
     * 另外可以将r初始化为-1，从0开始遍历区间，这样就可以避免再for循环外面再cnt++
     */
    @SuppressWarnings("unused")
    public int countWays0(int[][] ranges) {
        Arrays.sort(ranges, (o1, o2) -> o1[0] - o2[0]);
        // 合并后的区间数
        int cnt = 0;
        int l = ranges[0][0], r = ranges[0][1];
        for (int i = 1; i < ranges.length; i++) {
            if (ranges[i][0] <= r) {
                r = Math.max(r, ranges[i][1]);
            } else {
                cnt++;
                l = ranges[i][0];
                r = ranges[i][1];
            }
        }
        cnt++;

        int ans = 1;
        while (cnt > 0) {
            ans <<= 1;
            ans %= 1_000_000_007;
            cnt--;
        }
        return ans;
    }

    public int countWays(int[][] ranges) {
        Arrays.sort(ranges, (o1, o2) -> o1[0] - o2[0]);
        // 合并后的区间数
        int r = -1;
        int ans = 1;
        for (int i = 0; i < ranges.length; i++) {
            if (ranges[i][0] > r) {
                ans = (ans << 1) % 1_000_000_007;
            }
            r = Math.max(r, ranges[i][1]);
        }
        return ans;
    }

    /*
     * 3394. 判断网格图能否被切割成块 [Medium]
     * 
     * 显然是水平垂直分别判断（逻辑是一样的）
     * 所以问题变成，给定若干个区间（区间可能重叠），能够找到这样的2条分割线
     * 问题进一步变成对这些区间进行合并（只有有交集才合并，相邻不合并），然后区间数是否>=3
     */
    public boolean checkValidCuts(int n, int[][] rectangles) {
        int len = rectangles.length;
        int[][] h = new int[len][];
        int[][] v = new int[len][];
        for (int i = 0; i < len; i++) {
            h[i] = new int[] { rectangles[i][0], rectangles[i][2] };
            v[i] = new int[] { rectangles[i][1], rectangles[i][3] };
        }

        return check(h) || check(v);
    }

    public boolean check(int[][] ranges) {
        Arrays.sort(ranges, (o1, o2) -> o1[0] - o2[0]);

        int cnt = 0;
        int maxR = -1;
        for (int[] r : ranges) {
            if (r[0] >= maxR)
                cnt++;
            maxR = Math.max(maxR, r[1]);
        }
        return cnt > 2;
    }

    @Test
    public void test() {
        int[][] ranges = { { 0, 2, 2, 4 }, { 1, 0, 3, 2 }, { 2, 2, 3, 4 }, { 3, 0, 4, 2 }, { 3, 2, 4, 4 } };
        System.out.println(checkValidCuts(4, ranges)); // 输出：8

    }
}
