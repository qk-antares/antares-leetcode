package leetcode.datastruture.queue;

import java.util.ArrayDeque;

import leetcode.datastruture.heap.LazyHeap;

/*
 * 单调队列
 * 思想有点类似单调栈
 * 经典题如滑动窗口最大值
 */
public class MonoQueueT {
    /*
     * 239. 滑动窗口最大值 [Hard]
     * 
     * 用双端队列来维护会变化的单调栈（单调队列）
     * 当遇到更大的元素才入队，并且把队尾更小的元素依次移除
     * 当滑动窗口向右移动时，判断队首元素是否需要出队（只有当队首元素等于滑动窗口左边界元素时才出队）
     * 
     * 还可以用下标法，即双端队列中保存的是元素下标，这样方便处理元素出窗
     * 对于保存下标的这种方法，在入队时需要入相等的元素下标（并出掉前面相等的元素）
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        int l = 0, r = 0;
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        for (int i = 0; i < k; i++) {
            // 入
            while (!q.isEmpty() && nums[r] >= nums[q.getLast()]) {
                q.removeLast();
            }
            q.addLast(r++);
        }
        ans[0] = nums[q.getFirst()];

        while (r < n) {
            // 入
            while (!q.isEmpty() && nums[r] >= nums[q.getLast()]) {
                q.removeLast();
            }
            q.addLast(r++);

            // 出
            if (l == q.getFirst()) {
                q.removeFirst();
            }
            l++;
            ans[l] = nums[q.getFirst()];
        }

        return ans;
    }

    public int[] maxSlidingWindow0(int[] nums, int k) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        int l = 0, r = 0;
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        for (int i = 0; i < k; i++) {
            // 入
            while (!q.isEmpty() && q.getLast() < nums[r]) {
                q.removeLast();
            }
            q.addLast(nums[r++]);
        }
        ans[0] = q.getFirst();

        while (r < n) {
            // 入
            while (!q.isEmpty() && q.getLast() < nums[r]) {
                q.removeLast();
            }
            q.addLast(nums[r++]);

            // 出
            if (q.getFirst() == nums[l]) {
                q.removeFirst();
            }
            l++;
            ans[l] = q.getFirst();
        }

        return ans;
    }

    /**
     * 3835. 开销小于等于 K 的子数组数目
     * 
     * 懒删除堆，一个小根堆，一个大根堆
     * 滑动窗口，越小越合法
     * 
     * 维护滑动窗口中的最大值/最小值用一个双端队列即可 [Link: 239. 滑动窗口最大值]
     */
    public long countSubarrays(int[] nums, long k) {
        ArrayDeque<Integer> min = new ArrayDeque<>();
        ArrayDeque<Integer> max = new ArrayDeque<>();
        int l = 0, r = 0;
        long ans = 0;
        while(r < nums.length) {
            // min中现存>=nums[r]的元素都丢掉，因为它们不可能是最小值了
            while(!min.isEmpty() && nums[min.getLast()] >= nums[r]) {
                min.removeLast();
            }
            min.addLast(r);

            // max中现存<=nums[r]的元素都丢掉，因为它们不可能是最大值了
            while(!max.isEmpty() && nums[max.getLast()] <= nums[r]) {
                max.removeLast();
            }
            max.addLast(r);
            r++;

            // 确定左边界
            while((nums[max.getFirst()] - nums[min.getFirst()]) * (long)(r-l) > k) {
                if(min.getFirst() == l) min.removeFirst();
                if(max.getFirst() == l) max.removeFirst();
                l++;
            }

            ans += r-l;
        }

        return ans;
    }

    public long countSubarrays0(int[] nums, long k) {
        // 小根堆
        LazyHeap<Integer> h1 = new LazyHeap<>(Integer::compare);
        // 大根堆
        LazyHeap<Integer> h2 = new LazyHeap<>((o1, o2) -> Integer.compare(o2, o1));

        long ans = 0;
        int l = 0, r = 0;
        while (r < nums.length) {
            h1.push(nums[r]);
            h2.push(nums[r]);
            r++;

            while ((h2.peek() - h1.peek()) * (long) (r - l) > k) {
                h1.remove(nums[l]);
                h2.remove(nums[l]);
                l++;
            }

            ans += (r - l);
        }

        return ans;
    }
}
