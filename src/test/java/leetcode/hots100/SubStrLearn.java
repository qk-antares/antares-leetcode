package leetcode.hots100;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class SubStrLearn {
    /**
     * 和为 K 的子数组
     */
    public int subarraySum(int[] nums, int k) {
        int ans = 0, sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            ans += map.getOrDefault(sum-k, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }

    /**
     * 滑动窗口最大值
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int count = nums.length - k + 1;
        int[] ans = new int[count];
        Deque<Integer> queue = new LinkedList<>();

        for (int i = 0; i < k; i++) {
            while (!queue.isEmpty() && nums[i] > nums[queue.peekLast()]) {
                queue.pollLast();
            }
            queue.offerLast(i);
        }

        ans[0] = nums[queue.peekFirst()];
        for (int i = k; i < nums.length; i++) {
            while (!queue.isEmpty() && nums[i] > nums[queue.peekLast()]) {
                queue.pollLast();
            }
            queue.offer(i);
            if(queue.peekFirst() <= i-k){
                queue.pollFirst();
            }
            ans[i-k+1] = nums[queue.peekFirst()];
        }

        return ans;
    }

    @Test
    void invoke(){
        //subarraySum(new int[]{1,2,1,2,1}, 3);
        maxSlidingWindow(new int[]{1,-1}, 1);
    }
}
