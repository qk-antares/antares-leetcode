package leetcode.redo;

import java.util.HashSet;

public class questions {
    /**
     * 只出现一次的数字，我的解法，hashset
     */
    class SingleNumber {
        public int singleNumber(int[] nums) {
            HashSet<Integer> hashSet = new HashSet<>();
            int sum = 0;
            for (int num : nums) {
                sum += num;
                hashSet.add(num);
            }

            int sum0 = 0;
            for (Integer integer : hashSet) {
                sum0 += integer;
            }

            return 2 * sum0 - sum;
        }
    }
}
