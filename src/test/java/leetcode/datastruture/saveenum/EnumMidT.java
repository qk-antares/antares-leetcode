package leetcode.datastruture.saveenum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnumMidT {
    /*
     * 2909. 元素和最小的山形三元组 II [Medium]
     * 
     * 保存每个元素左边和右边的最小元素（不包含当前元素）
     * 
     * 一个小优化，不用提前创建lMin，在后面枚举寻找ans时更新lMin
     */
    public int minimumSu0(int[] nums) {
        int n = nums.length;
        int[] lMin = new int[n];
        int[] rMin = new int[n];

        lMin[0] = Integer.MAX_VALUE;
        rMin[n - 1] = Integer.MAX_VALUE;

        for (int i = 1; i < n; i++) {
            lMin[i] = Math.min(lMin[i - 1], nums[i - 1]);
            rMin[n - 1 - i] = Math.min(rMin[n - i], nums[n - i]);
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 1; i < n - 1; i++) {
            if (lMin[i] < nums[i] && rMin[i] < nums[i])
                ans = Math.min(ans, lMin[i] + nums[i] + rMin[i]);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public int minimumSum(int[] nums) {
        int n = nums.length;
        int[] rMin = new int[n];

        rMin[n - 1] = Integer.MAX_VALUE;

        for (int i = 1; i < n; i++) {
            rMin[n - 1 - i] = Math.min(rMin[n - i], nums[n - i]);
        }

        int ans = Integer.MAX_VALUE;
        int lMin = nums[0];
        for (int i = 1; i < n - 1; i++) {
            if (lMin < nums[i] && rMin[i] < nums[i])
                ans = Math.min(ans, lMin + nums[i] + rMin[i]);
            lMin = Math.min(lMin, nums[i]);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /*
     * 3583. 统计特殊三元组 [Medium]
     * 
     * 首先统计一遍nums中所有数字的出现次数cntR
     * 接下来对nums进行遍历，遍历的过程中用另一个Map cntL来统计数字的出现次数
     * 同时cntR里面对应数字的出现次数--
     * 根据cntL和cntR构造答案
     */
    public int specialTriplets(int[] nums) {
        Map<Integer, Integer> cntR = new HashMap<>();
        for (int num : nums) {
            cntR.merge(num, 1, Integer::sum);
        }

        Map<Integer, Integer> cntL = new HashMap<>();
        cntL.put(nums[0], 1);
        cntR.merge(nums[0], -1, Integer::sum);
        long ans = 0;
        int n = nums.length;
        for (int i = 1; i < n - 1; i++) {
            cntR.merge(nums[i], -1, Integer::sum);
            ans = (ans + (long) cntL.getOrDefault(nums[i] * 2, 0) * cntR.getOrDefault(nums[i] * 2, 0)) % 1_000_000_007;
            cntL.merge(nums[i], 1, Integer::sum);
        }

        return (int) ans;
    }

    /*
     * 1930. 长度为 3 的不同回文子序列 [Medium]
     * 
     * 和上一道题的思路一样
     * 区别在于字母的数量是非常有限的，可以用int[]表示Map
     * 
     * 所有的结果可以用一个26*26的布尔数组标记是否存在
     * 进一步，数组的第二维可以用位运算压缩
     */
    public int countPalindromicSubsequence0(String s) {
        int[] cntR = new int[26];
        char[] arr = s.toCharArray();
        for (char ch : arr)
            cntR[ch - 'a']++;

        int[] cntL = new int[26];
        cntL[arr[0] - 'a']++;
        cntR[arr[0] - 'a']--;

        Set<Integer> ans = new HashSet<>();

        for (int i = 1; i < arr.length - 1; i++) {
            int cur = arr[i] - 'a';
            cntR[cur]--;
            // 遍历左右两侧
            for (int j = 0; j < 26; j++) {
                if (cntL[j] > 0 && cntR[j] > 0) {
                    // 当前回文串的hash
                    int hash = j + cur * 26 + j * 26 * 26;
                    if (!ans.contains(hash))
                        ans.add(hash);
                }
            }
            cntL[cur]++;
        }

        return ans.size();
    }

    public int countPalindromicSubsequence(String s) {
        int[] cntR = new int[26];
        char[] arr = s.toCharArray();
        for (char ch : arr)
            cntR[ch - 'a']++;

        int[] cntL = new int[26];
        cntL[arr[0] - 'a']++;
        cntR[arr[0] - 'a']--;

        int[] flag = new int[26];

        for (int i = 1; i < arr.length - 1; i++) {
            int cur = arr[i] - 'a';
            cntR[cur]--;
            // 遍历左右两侧
            for (int j = 0; j < 26; j++) {
                if (cntL[j] > 0 && cntR[j] > 0) {
                    flag[cur] |= (1 << j);
                }
            }
            cntL[cur]++;
        }

        int ans = 0;
        for (int row : flag) {
            ans += Integer.bitCount(row);
        }

        return ans;

    }
}
