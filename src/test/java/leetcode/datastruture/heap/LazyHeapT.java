package leetcode.datastruture.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

/*
 * 懒删除堆
 */
public class LazyHeapT {
    /*
     * 3408. 设计任务管理器 [Medium]
     */
    class TaskManager {
        // 使用1个优先级队列，保存[priority, taskId, userId]三元组，
        // 按照priority，taskId的顺序排序
        // 用一个HashMap记录最新的taskId-[userId, priority]
        // edit方法更新HashMap中的priority，并将新任务加入优先级队列
        // rmv方法将HashMap中的priority置为-1
        // execTop方法弹出优先级队列的堆顶元素，检查其priority是否和HashMap中一致
        // 如果不一致，说明是旧任务，丢弃并继续弹出堆顶元素
        // 如果一致，说明是最新任务，返回userId，并将HashMap中的priority置为-1
        // 如果堆为空，返回-1
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> {
            return o2[2] - o1[2] != 0 ? o2[2] - o1[2] : o2[1] - o1[1];
        });
        Map<Integer, int[]> map = new HashMap<>();

        public TaskManager(List<List<Integer>> tasks) {
            for (List<Integer> task : tasks) {
                add(task.get(0), task.get(1), task.get(2));
            }
        }

        public void add(int userId, int taskId, int priority) {
            q.add(new int[] { userId, taskId, priority });
            map.put(taskId, new int[] { userId, priority });
        }

        public void edit(int taskId, int newPriority) {
            int userId = map.get(taskId)[0];
            add(userId, taskId, newPriority);
        }

        public void rmv(int taskId) {
            // int userId = map.get(taskId)[0];
            // map.put(taskId, new int[]{userId, -1});
            // 不用重新put，直接更新即可
            map.get(taskId)[1] = -1;
        }

        public int execTop() {
            int[] task = q.poll();
            if (task == null)
                return -1;
            if (task[2] != map.get(task[1])[1]) { // 货不对板
                return execTop();
            }
            // int userId = map.get(task.get(1))[0];
            // map.put(task.get(1), new int[]{userId, -1});
            // 不用put，直接更新即可
            int[] tmp = map.get(task[1]);
            tmp[1] = -1;
            return tmp[0];
        }
    }

    /*
     * 3607. 电网维护 [Medium]
     * 
     * 建图，通过深度优先遍历找到每个连通块
     * 每个连通块用一个懒删除堆来维护
     * 查询时，返回对应连通块堆顶元素
     * 删除时，懒删除对应连通块的堆中元素
     */
    @SuppressWarnings("unchecked")
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        List<Integer>[] g = new List[c + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] con : connections) {
            g[con[0]].add(con[1]);
            g[con[1]].add(con[0]);
        }

        List<IntegerLazyHeap> heaps = new ArrayList<>();
        // 记录每个节点属于哪个堆
        int[] belongs = new int[c + 1];
        Arrays.fill(belongs, -1);
        for (int i = 1; i <= c; i++) {
            if (belongs[i] != -1)
                continue;

            IntegerLazyHeap heap = new IntegerLazyHeap(Integer::compare);
            dfs(g, i, belongs, heap, heaps.size());
            heaps.add(heap);
        }

        int ansSize = 0;
        for (int[] q : queries) {
            if (q[0] == 1)
                ansSize++;
        }

        int[] ans = new int[ansSize];
        int cur = 0;
        for (int[] q : queries) {
            IntegerLazyHeap heap = heaps.get(belongs[q[1]]);
            if (q[0] == 1) {
                if (heap.removeCnt.containsKey(q[1])) {
                    ans[cur++] = heap.peek();
                } else {
                    ans[cur++] = q[1];
                }
            } else {
                heap.remove(q[1]);
            }
        }

        return ans;
    }

    void dfs(List<Integer>[] g, int i, int[] belongs, IntegerLazyHeap heap, int idx) {
        heap.push(i);
        belongs[i] = idx;

        for (int nbr : g[i]) {
            if (belongs[nbr] != -1)
                continue;
            dfs(g, nbr, belongs, heap, idx);
        }
    }

    @Test
    void test() {
        // int c = 5;
        // int[][] connections = { { 1, 2 }, {2, 3}, {3, 4}, { 4, 5 } };
        // int[][] queries = { { 1,3 }, { 2, 1 }, { 1, 1 }, { 2, 2 }, { 1, 2 } };
        int c = 1;
        int[][] connections = {};
        int[][] queries = { { 2, 1 }, { 1, 1 }, { 1, 1 } };
        System.out.println(Arrays.toString(processQueries(c, connections, queries)));
    }
}
