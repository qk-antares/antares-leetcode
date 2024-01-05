package leetcode.questions.T1000.T100.hard;

import org.junit.jupiter.api.Test;

public class MathT {
    /**
     * 第k个排列
     */
    boolean[] visited;
    int[] factorial;
    public String getPermutation(int n, int k) {
        visited = new boolean[n];
        factorial = new int[10];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i-1]*i;
        }

        StringBuilder sb = new StringBuilder();
        k--;
        for (int i = 0; i < n; i++) {
            int order = k/ factorial[n-i-1] + 1;
            k %= factorial[n-i-1];

            int cur = 0;
            while (order > 0) {
                if(!visited[cur]){
                    order--;
                }
                cur++;
            }
            visited[cur-1] = true;
            sb.append(cur);
        }

        return sb.toString();
    }


    @Test
    void test(){
        getPermutation(3,3);
    }
}
