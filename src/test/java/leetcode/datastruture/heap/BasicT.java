package leetcode.datastruture.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

public class BasicT {
    /*
     * 3275. 第 K 近障碍物查询 [Medium]
     * 
     * 大小为k的大根堆
     */
    public int[] resultsArray(int[][] queries, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int n = queries.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int dis = Math.abs(queries[i][0]) + Math.abs(queries[i][1]);
            if (heap.size() < k)
                heap.offer(dis);
            else if (dis < heap.peek()) {
                heap.poll();
                heap.offer(dis);
            }
            if (heap.size() < k)
                ans[i] = -1;
            else
                ans[i] = heap.peek();
        }
        return ans;
    }

    /*
     * 1834. 单线程 CPU [Medium]
     */
    public int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        // 按照任务的起始时间排序，记录排序后的index
        Integer[] idx = new Integer[n];
        Arrays.setAll(idx, i -> i);
        Arrays.sort(idx, (i0, i1) -> tasks[i0][0] - tasks[i1][0]);

        // 任务队列，按照执行时间/下标排序（小根堆）
        PriorityQueue<int[]> heap = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0] != 0 ? o1[0] - o2[0] : o1[1] - o2[1]);

        // 当前的时间戳
        int timestamp = tasks[idx[0]][0];

        // 待加入队列的索引i
        int i = 0;
        // j是待加入ans的索引
        int j = 0;
        int[] ans = new int[n];
        while (i < n) {
            // 将<=当前时间戳的任务加入队列
            while (i < n && tasks[idx[i]][0] <= timestamp) {
                heap.offer(new int[] { tasks[idx[i]][1], idx[i] });
                i++;
            }
            // 从队列中取出任务
            int[] task = heap.poll();
            if (task != null) {
                ans[j++] = task[1];
                timestamp += task[0];
            } else {
                timestamp = tasks[idx[i]][0];
            }
        }

        // 处理剩余任务
        while (!heap.isEmpty()) {
            ans[j++] = heap.poll()[1];
        }

        return ans;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 2099. 找到和最大的长度为 K 的子序列 [Easy]
     * 
     * 小根堆，如果当前元素大于堆顶，则加入堆中，堆中的元素还要保存其下标
     */
    public int[] maxSubsequence(int[] nums, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (heap.size() < k)
                heap.offer(new int[] { nums[i], i });
            else if (nums[i] > heap.peek()[0]) {
                heap.poll();
                heap.offer(new int[] { nums[i], i });
            }
        }

        int[] idx = new int[k];
        for (int i = 0; i < k; i++) {
            idx[i] = heap.poll()[1];
        }
        Arrays.sort(idx);

        for (int i = 0; i < k; i++) {
            idx[i] = nums[idx[i]];
        }
        return idx;
    }
}