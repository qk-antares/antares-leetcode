package leetcode.questions.T1000.T800;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

public class BinarySearchT {
    /*
     * 704. 二分查找 [Easy]
     */
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    /*
     * 744. 寻找比目标字母大的最小字母 [Easy]
     */
    public char nextGreatestLetter(char[] letters, char target) {
        int l = 0, r = letters.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (letters[mid] <= target) {
                l++;
            } else {
                r--;
            }
        }
        return (l == letters.length) ? letters[0] : letters[l];
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 719. 找出第 K 小的数对距离 [Hard]
     * 
     * 测试用例中有的K很大，导致直接使用PriorityQueue内存会超
     */
    public int smallestDistancePair0(int[] nums, int k) {
        int len = nums.length;
        PriorityQueue<Integer> q = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        int cnt = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 1; j < len; j++) {
                int pending = Math.abs(nums[i] - nums[j]);
                if (cnt < k) {
                    q.offer(pending);
                    cnt++;
                } else {
                    if (pending < q.peek()) {
                        q.poll();
                        q.offer(pending);
                    }
                }
            }
        }
        return q.peek();
    }

    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int len = nums.length;
        int l = 0, r = nums[len - 1] - nums[0];
        while (l <= r) {
            int mid = (l + r) / 2;
            // 距离小于等于mid的数量
            int cnt = 0;
            for (int i = 1; i < len; i++) {
                cnt += i - binarySearch(nums, i - 1, nums[i] - mid);
            }
            if (cnt <= k) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    // 寻找nums中[0,end]这个区间里，>=top的最小index
    public int binarySearch(int[] nums, int end, int top) {
        int l = 0, r = end;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] < top) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return r;
    }

    @Test
    public void test() {
        int[] nums = { 1, 6, 1 };
        int k = 0;
        System.out.println(smallestDistancePair(nums, k));
    }
}
