package leetcode.greedy;

/**
 * 从最左/最右开始贪心
 */
public class LRGreedyT {
    /*
     * 3228. 将 1 移动到末尾的最大操作次数 [Medium]
     * 
     * 记录前面遇到的1 cnt1
     * 当遇到1次0后打开开关 flag
     * 当遇到开关，再次遇到1时，ans+=cnt1;cnt1++; 重新关闭开关
     */
    public int maxOperations(String s) {
        int cnt1 = 0;
        boolean flag = false;
        int ans = 0;
        char[] ss = s.toCharArray();
        for (int i = 0; i < ss.length; i++) {
            if (ss[i] == '1') {
                if (flag) {
                    ans += cnt1;
                    flag = false;
                }
                cnt1++;
            } else if (ss[i] == '0' && cnt1 > 0) {
                flag = true;
            }
        }

        return flag ? ans + cnt1 : ans;
    }
}
