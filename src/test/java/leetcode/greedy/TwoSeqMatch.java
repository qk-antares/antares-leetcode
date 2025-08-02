package leetcode.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 双序列配对
 */
public class TwoSeqMatch {
    /*
     * 2037. 使每位学生都有座位的最少移动次数 [Easy]
     * 
     * 假设stu[j]坐到seats[i]上：cost=Math.abs(seats[i]-stu[j])
     * 这里的逻辑是，排序后的座位和排序后的学生对应（简单想一下确实是这样）
     * 即排序后，seats[i]对应stu[i]
     * 假设不满足这种对应，存在seats[i]对应stu[j]，seats[j]对应stu[i] i<j
     * deltaCost=Math.abs(seats[i]-stu[j])-Math.abs(seats[i]-stu[i])
     * +Math.abs(seats[j]-stu[i])-Math.abs(seat[j]-stu[j])>0
     */
    public int minMovesToSeat(int[] seats, int[] students) {
        Arrays.sort(seats);
        Arrays.sort(students);
        int ans = 0;
        for (int i = 0; i < seats.length; i++) {
            ans += Math.abs(seats[i] - students[i]);
        }
        return ans;
    }

    /*
     * 455. 分发饼干 [Easy]
     * 
     * g和s都排序，然后开始匹配
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int l = 0, r = 0;
        while (l < g.length && r < s.length) {
            if (s[r] >= g[l]) {
                l++;
            }
            r++;
        }
        return l;
    }

    /*
     * 2561. 重排水果 [Hard]
     * 
     * 用HashMap统计出basket1和basket2的代价数（以及总数）
     * 统计出二者需要被交换的代价列表（当该代价>2*min时，可以选择min作为媒介进行两次交换）
     * 列表的前一半和就是答案
     * 
     * 这里的小优化是，我们无需创建3个HashMap分别统计basket1、basket2和total
     * 直接使用一个HashMap来统计每个水果的数量，正数表示在basket1中，负数表示在basket2中
     * 这样最终的绝对值就是需要交换的代价数量
     */
    public long minCost0(int[] basket1, int[] basket2) {
        Map<Integer, Integer> cnt1 = new HashMap<>();
        Map<Integer, Integer> cnt2 = new HashMap<>();
        Map<Integer, Integer> total = new HashMap<>();
        int min = Integer.MAX_VALUE;
        for (int b1 : basket1) {
            cnt1.merge(b1, 1, Integer::sum);
            total.merge(b1, 1, Integer::sum);
            min = Math.min(min, b1);
        }
        for (int b2 : basket2) {
            cnt2.merge(b2, 1, Integer::sum);
            total.merge(b2, 1, Integer::sum);
            min = Math.min(min, b2);
        }

        List<Integer> trans = new ArrayList<>();

        for (Map.Entry<Integer, Integer> e : total.entrySet()) {
            Integer key = e.getKey();
            Integer val = e.getValue();
            if (val % 2 == 1)
                return -1;
            int delta = Math.abs(val / 2 - cnt1.getOrDefault(key, 0));
            for (int i = 0; i < delta; i++) {
                trans.add(Math.min(key, 2 * min));
            }
        }

        Collections.sort(trans);
        int len = trans.size();
        long ans = 0;
        for (int i = 0; i < len / 2; i++) {
            ans += trans.get(i);
        }

        return ans;
    }

    public long minCost(int[] basket1, int[] basket2) {
        Map<Integer, Integer> cache = new HashMap<>();
        int min = Integer.MAX_VALUE;
        for (int b1 : basket1) {
            cache.merge(b1, 1, Integer::sum);
            min = Math.min(min, b1);
        }
        for (int b2 : basket2) {
            cache.merge(b2, -1, Integer::sum);
            min = Math.min(min, b2);
        }

        List<Integer> trans = new ArrayList<>();

        for (Map.Entry<Integer, Integer> e : cache.entrySet()) {
            Integer key = e.getKey();
            Integer val = Math.abs(e.getValue());
            if (val % 2 == 1)
                return -1;
            for (int i = 0; i < val / 2; i++) {
                trans.add(Math.min(key, 2 * min));
            }
        }

        Collections.sort(trans);
        int len = trans.size();
        long ans = 0;
        for (int i = 0; i < len / 2; i++) {
            ans += trans.get(i);
        }

        return ans;
    }

}
