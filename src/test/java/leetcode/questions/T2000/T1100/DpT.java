package leetcode.questions.T2000.T1100;

import org.junit.jupiter.api.Test;

public class DpT {
    /**
     * 1014. 最佳观光组合   [Medium]
     * values[i] + value[j] + i - j = (values[i] + i) + (values[j] - j)
     * 分别最大化这两部分
     * 注意初始状态，(values[i] + i)这一部分即为(values[0] + 0)，而ans为0
     * 便利是，j从1开始
     */
    public int maxScoreSightseeingPair(int[] values) {
        int mxi = values[0] + 0;
        int ans = 0;
        for (int j = 1; j < values.length; j++) {
            ans = Math.max(ans, mxi + values[j] - j);
            mxi = Math.max(mxi, values[j] + j);
        }
        return ans;
    }

    @Test
    void test(){
        maxScoreSightseeingPair(new int[]{2,7,7,2,1,7,10,4,3,3});

        String str = "hello";
        StringBuilder sb = new StringBuilder(str);
        String substring = sb.reverse().substring(1);
    }
}
