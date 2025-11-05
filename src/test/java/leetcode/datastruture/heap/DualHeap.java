package leetcode.datastruture.heap;

import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

/*
 * 对顶堆
 * 通常一个大根堆一个小根堆配合使用
 */
public class DualHeap {
    /*
     * 2102. 序列顺序查询 [Hard]
     * 
     * 用两个堆，left是大根堆，right是小根堆
     * right的大小=get的调用次数，堆顶是score第i大的元素
     * add时，先将元素添加至right，再将right的堆顶弹出并添加到left
     * get时，将left的堆顶弹出并添加到right，right的堆顶是第i大的元素
     */
    class Entity {
        public int score;
        public String name;

        public Entity(int score, String name) {
            this.score = score;
            this.name = name;
        }
    }

    class SORTracker {
        PriorityQueue<Entity> left = new PriorityQueue<>(
                (o1, o2) -> o1.score != o2.score ? o2.score - o1.score : o1.name.compareTo(o2.name));
        PriorityQueue<Entity> right = new PriorityQueue<>(
                (o1, o2) -> o1.score != o2.score ? o1.score - o2.score : o2.name.compareTo(o1.name));

        public SORTracker() {

        }

        public void add(String name, int score) {
            right.offer(new Entity(score, name));
            left.offer(right.poll());
        }

        public String get() {
            right.offer(left.poll());
            return right.peek().name;
        }
    }

    /*
     * 480. 滑动窗口中位数 [Hard]
     */
    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        double[] ans = new double[n - k + 1];

        // 确保left.size = right.size / +1
        // 大根堆
        LazyHeap<Integer> left = new LazyHeap<>((a, b) -> Integer.compare(b, a));
        // 小根堆
        LazyHeap<Integer> right = new LazyHeap<>(Integer::compare);

        int l = 0, r = 0;
        while (r < n) {
            // 入
            if (left.size == right.size) {
                right.push(nums[r++]);
                left.push(right.pop());
            } else {
                left.push(nums[r++]);
                right.push(left.pop());
            }

            if (r - l + 1 > k) {
                if (k % 2 > 0) {
                    ans[l] = left.peek();
                } else {
                    ans[l] = ((long) left.peek() + right.peek()) / 2.0;
                }
            }

            if (nums[l] <= left.peek()) {
                left.remove(nums[l++]);
                if (left.size < right.size) {
                    left.push(right.pop());
                }
            } else {
                right.remove(nums[l++]);
                if (left.size > right.size + 1) {
                    right.push(left.pop());
                }
            }
        }

        return ans;

    }

    /*
     * 3013. 将数组分成最小总代价的子数组 II [Hard]
     * 
     * 第一段的代价固定为nums[0]，剩下k-1个数组，相当于从nums[1..n-1]中选取k-1个数字，这k-1个数字的和最小
     * 同时考虑第二个数组与最后一个数组的间距<=dist，窗口大小为dist+1
     * 相当于求nums[1..n-1]上dist+1的窗口，其中前k-1小的数字之和
     * 用对顶堆
     * 左堆是一个大根堆（大小k-1），右堆是一个小根堆（大小dist+2-k）
     * 用懒删除堆
     * 维护左堆的元素之和
     * 返回最小的左堆之和+nums[0]
     */
    public long minimumCost(int[] nums, int k, int dist) {
        int n = nums.length;
        LazyHeapWithSum left = new LazyHeapWithSum((a, b) -> Integer.compare(b, a));
        LazyHeapWithSum right = new LazyHeapWithSum(Integer::compare);

        long ans = Long.MAX_VALUE;
        int l = 1, r = 1;
        while (r < n) {
            if (left.size < k - 1) {
                left.push(right.pushPop(nums[r++]));
            } else {
                right.push(left.pushPop(nums[r++]));
            }

            if (r - l < dist + 1)
                continue;

            ans = Math.min(ans, left.sum);
            if (nums[l] <= left.peek()) {
                left.remove(nums[l++]);
            } else {
                right.remove(nums[l++]);
            }
        }

        return ans + nums[0];
    }

    @Test
    void test() {
        medianSlidingWindow(new int[] { 1, 3, -1, -3, 5, 3, 6, 7 }, 3);
    }
}
