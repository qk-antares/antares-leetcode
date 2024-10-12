package leetcode.questions.T4000.T3200.medium;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class HashT {
    /*
     * 3164. 优质数对的总数 II
     */
    public long numberOfPairs(int[] nums1, int[] nums2, int k) {
        HashMap<Integer,Integer> cnt1 = new HashMap<>();
        HashMap<Integer,Integer> cnt2 = new HashMap<>();
        int max1 = 0;

        for (int i = 0; i < nums1.length; i++) {
            cnt1.put(nums1[i], cnt1.getOrDefault(nums1[i], 0) + 1);
            max1 = Math.max(max1, nums1[i]);
        }
        for (int i = 0; i < nums2.length; i++) {
            cnt2.put(nums2[i], cnt2.getOrDefault(nums2[i], 0) + 1);
        }

        long ans = 0;
        for(int num2: cnt2.keySet()) {
            for(int num1 = num2 * k; num1 <= max1; num1 += num2 * k) {
                if(cnt1.containsKey(num1)) {
                    ans += 1L * cnt1.get(num1) * cnt2.get(num2);
                }
            }
        }
        return ans;
    }   

    @Test
    public void test() {
        int[] nums1 = {1,3,4};
        int[] nums2 = {1,3,4};
        int k = 1;
        System.out.println(numberOfPairs(nums1, nums2, k));
    }
}
