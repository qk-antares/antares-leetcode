package leetcode.prefixsum;

public class BasicT {

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
