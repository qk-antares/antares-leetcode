package leetcode.datastruture.prefixsum;

public class OtherT {
    /*
     * 2438. 二的幂数组中查询范围内的乘积 [Medium]
     * 
     * 暴力解法直接对每个query，遍历[q[0], q[1]]，计算乘积并取MOD
     * 该道题不能直接对2^n取MOD后应用前缀积，这是因为(m/n)%p != (m%p)/(n%p)
     * 即使可以通过逆元计算，依然不推荐（复杂度很高）
     * 
     * 另一种思路是不直接记录2^n，而是仅记录指数部分的n
     * 这样对于每个query，ans相当于2^[s[q[1]+1]-s[q[0]]] % MOD
     * 需要注意s[q[1]+1]-s[q[0]]的范围并不是32，而是0+1+2+...+29=436
     */
    public int[] productQueries0(int n, int[][] queries) {
        int[] n2 = new int[32];
        int j = 0;
        for (int i = 0; i < 32; i++) {
            if (((n >> i) & 1) == 1) {
                n2[j++] = (1 << i);
            }
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            ans[i] = n2[q[0]];
            for (int k = q[0] + 1; k <= q[1]; k++) {
                ans[i] = (int) ((long) ans[i] * n2[k] % 1_000_000_007);
            }
        }

        return ans;
    }

    public int[] productQueries(int n, int[][] queries) {
        int[] n2 = new int[32];
        int j = 0;
        for (int i = 0; i < 32; i++) {
            if (((n >> i) & 1) == 1) {
                n2[j++] = i;
            }
        }

        int[] preS = new int[j + 1];
        for (int i = 0; i < j; i++) {
            preS[i + 1] = preS[i] + n2[i];
        }

        int[] pow2 = new int[436];
        pow2[0] = 1;
        for (int i = 1; i < 436; i++) {
            pow2[i] = 2 * pow2[i - 1] % 1_000_000_007;
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            ans[i] = pow2[preS[q[1] + 1] - preS[q[0]]];
        }

        return ans;
    }
}
