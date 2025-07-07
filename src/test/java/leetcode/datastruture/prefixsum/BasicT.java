package leetcode.datastruture.prefixsum;

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
     * 2559. 统计范围内的元音字符串数 [Medium]
     */
    public int[] vowelStrings(String[] words, int[][] queries) {
        int n = words.length;
        int[] preSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            char c1 = words[i - 1].charAt(0);
            char c2 = words[i - 1].charAt(words[i - 1].length() - 1);

            if ((c1 == 'a' || c1 == 'e' || c1 == 'i' || c1 == 'o' || c1 == 'u')
                    && (c2 == 'a' || c2 == 'e' || c2 == 'i' || c2 == 'o' || c2 == 'u'))
                preSum[i] = 1 + preSum[i - 1];
            else
                preSum[i] = preSum[i - 1];
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            ans[i] = preSum[q[1] + 1] - preSum[q[0]];
        }
        return ans;
    }

    /*
     * 3152. 特殊数组 II [Medium]
     */
    public boolean[] isArraySpecial(int[] nums, int[][] queries) {
        int[] s = new int[nums.length];
        for (int i = 1; i < nums.length; i++) {
            s[i] = s[i - 1] + (nums[i - 1] % 2 == nums[i] % 2 ? 1 : 0);
        }
        boolean[] ans = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            ans[i] = s[q[0]] == s[q[1]];
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
