package leetcode.questions.T1000.T200.hard;

public class BitT {
    /**
     * 137. 只出现一次的数字 II
     */
    public int singleNumber(int[] nums) {
        // 用 high，low 2个二进制位表示某个二进制位上1出现的次数。
        // high和low都为1时，说明有3个1.归零即可。
        // 最终high必然为0，而low若为0：表示唯一的数在该位为0，若low为1：唯一的数在该位为1
        // 所以最终low即是答案
        int low = 0, high = 0;
        for (int num : nums) {
            int carry = low & num;
            low ^= num;
            high |= carry;

            int reset = low ^ high;
            low &= reset;
            high &= reset;
        }

        return low;
    }

    public int singleNumber0(int[] nums) {
        // 依次计算每一位
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            int cnt = 0;
            for (int num : nums) {
                cnt += ((num >> i) & 1);
            }
            ans |= ((cnt % 3) << i);
        }
        return ans;
    }
}
