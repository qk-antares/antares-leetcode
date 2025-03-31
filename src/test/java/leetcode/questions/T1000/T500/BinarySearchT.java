package leetcode.questions.T1000.T500;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BinarySearchT {
        public int[] findRightInterval(int[][] intervals) {
        int n = intervals.length;
        int[][] startIntervals = new int[n][2];
        for(int i = 0; i < n; i++) {
            startIntervals[i][0] = intervals[i][0];
            startIntervals[i][1] = i;
        }

        Arrays.sort(startIntervals, (o1, o2) -> o1[0]-o2[0]);

        int[] ans = new int[n];
        for(int i = 0; i < n;i++) {
            int end = intervals[i][1];
            //在startIntervals中二分查找
            int l = 0, r = n-1;
            while(l <= r) {
                int mid = (l+r)/2;
                if(startIntervals[mid][0] < end) {
                    l = mid+1;
                }else{
                    r = mid-1;
                }
            }
            if(l == n) {
                ans[i] = -1;
            } else {
                ans[i] = startIntervals[l][1];
            }
        }

        return ans;
    }

    @Test
    public void test() {
        int[][] intervals = {{3,4},{2,3},{1,2}};
        findRightInterval(intervals);
    }
}
