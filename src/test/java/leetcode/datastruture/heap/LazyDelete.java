package leetcode.datastruture.heap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

/*
 * 懒删除堆（发现货不对板直接丢掉）
 */
public class LazyDelete {
    /*
     * 3408. 设计任务管理器
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
     * 3318. 计算子数组的 x-sum I [Easy]
     * 
     * 由于nums[i]的范围在[1,50]，可以用一个大小为51的数组来统计各个数字的出现次数
     * 再使用优先级队列（大根堆）
     * 取出堆顶的x
     * 
     * TODO: 懒删除堆的方法待做
     */
    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        int[] cnt = new int[51];
        for (int i = 0; i < k; i++) {
            cnt[nums[i]]++;
        }

        for (int i = 0; i < n - k + 1; i++) {
            // 大根堆要反过来
            PriorityQueue<int[]> q = new PriorityQueue<>(
                    (o1, o2) -> o2[1] - o1[1] != 0 ? o2[1] - o1[1] : o2[0] - o1[0]);
            for (int j = 1; j < 51; j++) {
                if (cnt[j] != 0) {
                    q.add(new int[] { j, cnt[j] });
                }
            }

            int tmp = 0;
            for (int j = 0; j < x; j++) {
                int[] e = q.poll();
                tmp += e[0] * e[1];
            }
            ans[i] = tmp;

            if (i + k < n - k + 1) {
                cnt[nums[i]]--;
                cnt[nums[i + k]]++;
            }
        }

        return ans;
    }

    @Test
    void test() {
        int[] nums = { 1, 1, 2, 2, 3, 4, 2, 3 };
        int k = 6, x = 2;
        int[] res = findXSum(nums, k, x);
        System.out.println(Arrays.toString(res));
    }
}
