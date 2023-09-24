package leetcode.hots100;

import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

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

    /**
     * 最小覆盖子串（方法超时）
     */
    public String minWindow(String s, String t) {
        int l = 0, tLen = t.length(), r = tLen - 1, sLen = s.length();
        if(tLen > sLen){
            return "";
        }

        int[] target = calculateCount(t);
        int[] cur = calculateCount(s.substring(l, r + 1));
        int ansL = 0, ansR = 0, minLen = Integer.MAX_VALUE;
        while (r < sLen){
            if(verify(target, cur)){
                if(r-l+1 < minLen){
                    ansL = l;
                    ansR = r;
                    minLen = r-l+1;
                }
                cur[s.charAt(l)-'A']--;
                l++;
            } else {
                r++;
                if(r < sLen){
                    cur[s.charAt(r)-'A']++;
                }
            }
        }
        if(minLen == Integer.MAX_VALUE){
            return "";
        }
        return s.substring(ansL, ansR+1);
    }

    int[] calculateCount(String p) {
        int len = p.length();
        int[] count = new int['z' - 'A' + 1];
        for (int i = 0; i < len; i++) {
            count[p.charAt(i)-'A']++;
        }
        return count;
    }

    boolean verify(int[] target, int[] cur){
        for (int i = 0; i < target.length; i++) {
            if(target[i] > cur[i]) return false;
        }
        return true;
    }

    @Test
    void invoke(){
        //subarraySum(new int[]{1,2,1,2,1}, 3);
        maxSlidingWindow(new int[]{1,-1}, 1);
    }
}
