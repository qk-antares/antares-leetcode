package leetcode.datastruture.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 堆的进阶题
 */
public class HardT {
    /**
     * 1882. 使用服务器处理任务 [Medium]
     * 
     * 用一个堆存储空闲服务器，权重小的在前面，权重相同则下标小的排在前
     * 用另一个堆存储各个服务器的释放时间，释放时间早的在前面，时间相同看权重，权重相同看下标
     */
    public int[] assignTasks(int[] servers, int[] tasks) {
        PriorityQueue<int[]> free = new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
        PriorityQueue<int[]> release = new PriorityQueue<>(
                (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : (o1[1] != o2[1] ? o1[1] - o2[1] : o1[2] - o2[2]));

        for (int i = 0; i < servers.length; i++) {
            free.offer(new int[] { servers[i], i });
        }

        int n = tasks.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            // 获取空闲服务器
            while (!release.isEmpty() && release.peek()[0] <= i) {
                int[] tmp = release.poll();
                free.offer(new int[] { tmp[1], tmp[2] });
            }

            // 有空闲服务器
            if (!free.isEmpty()) {
                int[] tmp = free.poll();
                ans[i] = tmp[1];
                release.offer(new int[] { i + tasks[i], tmp[0], tmp[1] });
            } else {
                // 没有空闲服务器，则等到release.peek()[0]
                int[] tmp = release.poll();
                ans[i] = tmp[2];
                release.offer(new int[] { tmp[0] + tasks[i], tmp[1], tmp[2] });
            }
        }

        return ans;
    }

    /**
     * 2402. 会议室 III [Hard]
     * 
     * 用小顶堆来存储空闲房间
     * 用一个数组来存储各个房间举办会议的次数
     * 用另一个堆来存储各个占用房间的释放时机，先释放的排在前面(同时间释放则编号小的房间排在前面)，这是一个小顶堆
     */
    public int mostBooked(int n, int[][] meetings) {
        PriorityQueue<Integer> rooms = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            rooms.offer(i);
        }
        int[] cnt = new int[n];
        PriorityQueue<int[]> release = new PriorityQueue<>(
                (o1, o2) -> o1[0] - o2[0] != 0 ? o1[0] - o2[0] : o1[1] - o2[1]);

        Arrays.sort(meetings, (o1, o2) -> o1[0] - o2[0]);

        for (int[] m : meetings) {
            // 获取当前可用房间
            while (!release.isEmpty() && release.peek()[0] <= m[0]) {
                rooms.offer(release.poll()[1]);
            }

            // 当前有可用房间
            if (rooms.size() > 0) {
                int room = rooms.poll();
                cnt[room]++;
                release.add(new int[] { m[1], room });
            } else {
                // 无可用房间，则一直等到release.peek()[0]
                int[] tmp = release.poll();
                cnt[tmp[1]]++;
                release.add(new int[] { tmp[0] + m[1] - m[0], tmp[1] });
            }
        }

        int ans = 0;
        for (int i = 1; i < n; i++) {
            if (cnt[i] > cnt[ans]) {
                ans = i;
            }
        }

        return ans;
    }
}
