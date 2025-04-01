package leetcode.questions.T1000.T100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BitT {
    /**
     * 89. 格雷编码 [Medium]
     * 找规律题
     * n=0: 0
     * n=1: [0] 1
     * n=2: [00 01] 11 10
     * n=3: [000 001 011 010] 110 111 101 100
     * 观察发现，n的格雷编码的前一部分总是和n-1的格雷编码一致；后一部分是n-1的格雷编码前面补1再反转
     */
    public List<Integer> grayCode(int n) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        ans.add(0);
        for (int i = 1; i <= n; i++) {
            int len = ans.size();
            for (int j = len - 1; j >= 0; j--) {
                ans.add(ans.get(j) + (1 << (i - 1)));
            }
        }
        return ans;
    }

    /**
     * 90. 子集 II  [Medium]
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        Arrays.sort(nums);
        int len = nums.length;
        boolean flag = true;

        for (int mask = 0; mask <= (1 << len) - 1; mask++) {
            tmp.clear();
            flag = true;
            for (int j = 0; j < len; j++) {
                // 准备选中这个元素
                if (((mask >> j) & 1) == 1) {
                    if (j > 0 && ((mask >> (j - 1)) & 1) == 0 && nums[j] == nums[j - 1]) {
                        flag = false;
                        break;
                    } else {
                        tmp.add(nums[j]);
                    }
                }
            }
            if (flag) {
                ans.add(new ArrayList<>(tmp));
            }
        }
        return ans;
    }
}
