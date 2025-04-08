package leetcode.questions.T4000.T3400;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ArrayT {
    /*
     * 3396. 使数组元素互不相同所需的最少操作次数
     * 
     * 第一种方法是使用一个cnt的数组或者HashMap来记录各个数字出现的次数。
     * 写一个函数根据cnt判断当前是否已经删除到了各个数字只剩一次。
     * 
     * 更简单的方法是对nums进行倒序遍历，直至遇到第一个重复元素，则进行删除时至少到底这个元素
     * ans = i/3+1
     */
    public int minimumOperations0(int[] nums) {
        int N = 100;
        int[] cnt = new int[N + 1];
        for (int num : nums) {
            cnt[num]++;
        }

        int ans = 0;
        int cur = 0;
        int n = nums.length;
        while (!arrDiff(cnt)) {
            ans++;
            for (int i = cur; i < Math.min(cur + 3, n); i++) {
                cnt[nums[i]]--;
            }
            cur += 3;
        }
        return ans;
    }

    public boolean arrDiff(int[] nums) {
        for (int num : nums) {
            if (num > 1)
                return false;
        }
        return true;
    }

    public int minimumOperations(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int len = nums.length;
        for(int i = len-1; i >= 0; i--) {
            if(set.contains(nums[i])) {
                return i/3+1;
            }
            set.add(nums[i]);
        }
        return 0;
    }

    @Test
    public void test() {
        minimumOperations(new int[]{1,2,3,4,2,3,3,5,7});
    }
}