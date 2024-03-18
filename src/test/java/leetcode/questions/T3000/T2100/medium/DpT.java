package leetcode.questions.T3000.T2100.medium;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class DpT {
    /**
     * 2008. 出租车的最大盈利
     * 首先按照结束的位置排序，假设按照结束位置排序后有n个乘客
     * dp[0]=0
     * dp[1]=乘客1
     * dp[i]=max(dp[i-1], 乘客i+可以承受的上一个dp)
     * dp[i+1]表示从[0...i]的最大盈利
     * dp[i+1] = max(dp[i], )
     */
    public int binarySearch(int[][] rides, int i, int start_j) {
        int l = 0, r = i;
        while (l < r) {
            int mid = (l+r)/2;
            if(rides[mid][1] > start_j) {
                r = mid-1;
            } else {
                l = mid+1;
            }
        }
        return l;
    }

    public long maxTaxiEarnings(int n, int[][] rides) {
        Arrays.sort(rides, (o1, o2) -> o1[1] - o2[1]);        
        //乘客数
        int cnt = rides.length;
        long[] dp = new long[cnt + 1];
        
        // 下一个到达的乘客处
        for (int i = 1; i <= cnt; i++) {
            int j = binarySearch(rides, i-1, rides[i-1][0]);
            int[] passenger = rides[i-1];
            dp[i] = Math.max(dp[j] + passenger[1] - passenger[0] + passenger[2], dp[i-1]);
        }   

        return dp[cnt];
    }

    @Test
    void test() {
        // maxTaxiEarnings(5, new int[][] { { 2, 5, 4 }, { 1, 5, 1 } });
        maxTaxiEarnings(10, new int[][]{{8,10,9},{2,9,2},{3,7,7},{7,9,5},{3,7,4},{4,8,8},{3,6,2},{7,10,1},{4,5,2},{5,6,1}});
    }
}
