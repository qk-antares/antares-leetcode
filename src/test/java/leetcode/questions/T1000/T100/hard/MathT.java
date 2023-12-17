package leetcode.questions.T1000.T100.hard;

import org.junit.jupiter.api.Test;

public class MathT {
    /**
     * 第k个排列
     */
    public String getPermutation(int n, int k) {
        int[] factorial = new int[10];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i-1]*i;
        }

        int[] indexes = new int[n];
        StringBuilder sb = new StringBuilder();
        for (int i = n; i >= 1 ; i--) {
            int index = k / factorial[i-1];
            k = k % factorial[i-1];
            if(k == 0){
                indexes[n-i] = index;
            } else {
                indexes[n-i] = index+1;
            }
        }

        boolean[] visited = new boolean[n];





        return sb.toString();
    }

    @Test
    void test(){
        getPermutation(3,3);
    }
}
