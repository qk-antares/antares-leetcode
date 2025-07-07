package leetcode.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 区间贪心
 */
public class RangeGreedy {
    /*
     * 1353. 最多可以参加的会议数目
     * 
     * 本质是一个贪心问题：
     * 每天参加可以参加的，且最快将要结束的会议
     * 这可以用最小堆来实现：
     * 首先将所有events按照开始时间进行分组groups
     * 接下来遍历groups：
     * 首先将已经结束的会议移除；
     * 将groups[i]的所有结束时间添加到q中
     * 移除q的堆顶并将ans++
     * 
     * 还可使用并查集
     * 假设fa[x]保存了x及之后最近的空闲时间
     * 初始时fa[x] = x
     * 接下来对events按照结束时间进行排序（结束时间越早越紧急）
     * 对每个e，为它分配的空闲时间为：t = find(e[0])
     * 如果t <= e[1]，则将ans++，并将fa[t]=t+1（表明t已经被占用）
     */
    @SuppressWarnings("unchecked")
    public int maxEvents0(int[][] events) {
        int mx = 0;
        for (int[] e : events)
            mx = Math.max(mx, e[1]);

        List<Integer>[] groups = new List[mx + 1];
        Arrays.setAll(groups, i -> new ArrayList<>());
        for (int[] e : events) {
            groups[e[0]].add(e[1]);
        }

        int ans = 0;
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int i = 1; i <= mx; i++) {
            while (!q.isEmpty() && q.peek() < i) {
                q.poll();
            }

            for (int end : groups[i]) {
                q.offer(end);
            }

            if (!q.isEmpty()) {
                q.poll();
                ans++;
            }
        }

        return ans;
    }

    public int maxEvents(int[][] events) {
        Arrays.sort(events, (a, b) -> a[1] - b[1]);

        int mx = events[events.length - 1][1];
        int[] fa = new int[mx + 2];
        for (int i = 0; i < fa.length; i++) {
            fa[i] = i;
        }

        int ans = 0;
        for (int[] e : events) {
            int x = find(e[0], fa); // 查找从 startDay 开始的第一个可用天
            if (x <= e[1]) {
                ans++;
                fa[x] = x + 1; // 标记 x 已占用
            }
        }
        return ans;
    }

    private int find(int x, int[] fa) {
        if (fa[x] != x) {
            fa[x] = find(fa[x], fa);
        }
        return fa[x];
    }
}
