package leetcode.redo;

import java.util.HashSet;

public class Bitwise {
    /**
     * 只出现一次的数字，我的解法，hashset，效率显然是不行的，答案解法：位运算
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

        /**
         * 位运算解决，依据的基本公式：
         * a ^ a = 0
         * a ^ 0 = a
         * 异或运算满足交换律
         * 故将nums中的所有元素做抑或运算即可
         */
        public int singleNumber0(int[] nums) {
            int ans = 0;
            for (int num : nums) {
                ans ^= num;
            }
            return ans;
        }

    }


}
