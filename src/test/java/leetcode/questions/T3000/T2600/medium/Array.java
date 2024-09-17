package leetcode.questions.T3000.T2600.medium;

import org.junit.jupiter.api.Test;

public class Array {
    /**
     * 2555. 两个线段获得的最多奖品
     * 维护两个滑动窗口+第一个窗口的最大值
     */
    public int maximizeWin(int[] prizePositions, int k) {
        int ans = 0;
        for(int l1 = 0, r1 = 0, l2 = 0, r2 = 0, firstMax = 0; r2 < prizePositions.length; r2++) {
            //如果第二个窗口的范围过大了，就把左端点收一收
            while (prizePositions[r2] - prizePositions[l2] > k) {
                l2++;
            }

            //移动第一个窗口，并维护其最大值
            for(;r1 < l2;r1++) {
                //如果第一个窗口过大
                while (prizePositions[r1] - prizePositions[l1] > k) {
                    l1++;
                }
                firstMax = Math.max(firstMax, r1 - l1 + 1);
            }

            ans = Math.max(ans, firstMax + r2 - l2 + 1);
        }
        return ans;
    }

    @Test
    void test(){
        maximizeWin(new int[]{1,1,2,2,3,3,5}, 2);
    }
}
