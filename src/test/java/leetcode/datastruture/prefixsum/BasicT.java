package leetcode.datastruture.prefixsum;

import java.util.Arrays;

public class BasicT {
    /*
     * 303. 区域和检索 - 数组不可变 [Easy[]
     */
    class NumArray {
        int[] s;

        public NumArray(int[] nums) {
            int n = nums.length;
            s = new int[n + 1];
            for (int i = 0; i < n; i++) {
                s[i + 1] = s[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return s[right + 1] - s[left];
        }
    }

    /*
     * 3427. 变长子数组求和 [Easy]
     */
    public int subarraySum(int[] nums) {
        int n = nums.length;
        int[] s = new int[n + 1];
        int ans = 0;

        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
            ans += s[i + 1] - s[Math.max(0, i - nums[i])];
        }

        return ans;
    }

    /*
     * 1371. 每个元音包含偶数次的最长子字符串 [Medium] <Star>
     * 
     * 相当于枚举右，维护左
     * 枚举到的s[i]，需要知道它们每个元音字母的奇偶性
     * 5个字母的奇偶性有2^5个状态，者可以用1<<5的数组维护
     * 维护左边某个奇偶性状态最早出现的位置
     */
    public int findTheLongestSubstring(String s) {
        char[] arr = s.toCharArray();
        // 维护每个状态的最近位置
        int[] map = new int[1 << 5];
        Arrays.fill(map, -2);
        map[0] = -1;
        // 初始状态，每个元音字母都是0
        int status = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 'a') {
                status ^= 1;
            } else if (arr[i] == 'e') {
                status ^= (1 << 1);
            } else if (arr[i] == 'i') {
                status ^= (1 << 2);
            } else if (arr[i] == 'o') {
                status ^= (1 << 3);
            } else if (arr[i] == 'u') {
                status ^= (1 << 4);
            }

            if (map[status] != -2) {
                ans = Math.max(ans, i - map[status]);
            } else {
                map[status] = i;
            }
        }

        return ans;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 1422. 分割字符串的最大得分
     * 
     * 记录s中总的1 cnt1，然后，对s进行遍历，遍历的过程中记录遇到的0 cnt0，遇到1就把cnt--
     * 则ans = Math.max(ans, cnt1 + cnt0)
     */
    public int maxScore(String s) {
        int cnt0 = 0, cnt1 = 0;
        char[] arr = s.toCharArray();

        for (char ch : arr) {
            if (ch == '1')
                cnt1++;
        }

        int ans = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == '1')
                cnt1--;
            else
                cnt0++;
            ans = Math.max(ans, cnt0 + cnt1);
        }

        return ans;
    }
}
