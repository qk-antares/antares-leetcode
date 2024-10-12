package leetcode.questions.T4000.T3200.easy;

import java.util.HashSet;

public class BitT {
    /*
     * 3158. 求出出现两次数字的 XOR 值
     */
    public int duplicateNumbersXOR(int[] nums) {
        HashSet<Integer> set = new HashSet<Integer>();
        int ans = 0;

        for (int num : nums) {
            if(set.contains(num)) {
                ans ^= num;
            }
            set.add(num);
        }

        return ans;
    }
}
