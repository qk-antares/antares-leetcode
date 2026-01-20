package leetcode.bit;

import java.util.List;

/**
 * 思维题
 */
public class ThinkingT {
    /**
     * 3315. 构造最小位运算数组 II [Medium]
     * 
     * x | (x+1)本质是把最右的0置为1
     * x | (x+1) = nums[i]
     * 满足条件的最小x，实际上是把nums[i]最右0右边的1置为0
     * x求反，取lowbit，则lowbit就是最右0所在的位置，lowbit再右移1位，就是要置为0的那个1，减去lowbit>>1即可
     * 
     * lowbit指二进制表示中最右边的1所代表的值
     * 例如101100的lowbit是000100
     */
    public int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int x = nums.get(i);
            if (x == 2) {
                ans[i] = -1;
            } else {
                int t = ~x;
                int lowbit = t & -t;
                ans[i] = x ^ (lowbit >> 1);
            }
        }
        return ans;
    }
}
