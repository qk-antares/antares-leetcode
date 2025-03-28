package leetcode.questions.T3000.T2700.medium;

import org.junit.jupiter.api.Test;

public class BitT {
    /*
     * 2680. 最大或值
     * 
     * 首先要意识到k次操作必定是在一个数字上（它的二进制表示1尽可能靠前）
     * 
     * 构造一个后缀数组suf，suf[i]表示nums[i+1..]或运算的结果
     * 然后开始遍历nums，遍历的过程中维护一个前缀pre，表示nums[...i]的或运算结果
     * 如此ans=Math.max(pre | num[i]<<k | suf[i], ans);
     * 
     * 最后返回ans
     */
    public long maximumOr(int[] nums, int k) {
        int len = nums.length;
        long[] suf = new long[nums.length];
        for(int i = nums.length-2; i>=0; i--){
            suf[i] = nums[i+1] | suf[i+1]; 
        }

        long ans = 0;
        long pre = 0;
        for(int i = 0; i < len; i++){
            ans = Math.max(pre | (long)nums[i] << k | suf[i], ans);
            pre |= nums[i];
        }
        return ans;
    }

    @Test
    public void test() {
        System.out.println(maximumOr(new int[]{12, 9}, 1));
    }
}
