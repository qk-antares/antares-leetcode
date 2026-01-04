package leetcode.math.numbertheory;

/**
 * 因子
 */
public class FactorT {
    /**
     * 1390. 四因数 [Medium]
     * 
     * nums[i]的范围在[1,100_000]
     * 预处理得到所有在这个范围内的数字是否符合要求
     * 枚举i [1, 100_000]，以及倍数j
     * 如果i*j<=10000，那么i是i*j的一个因子
     * 用一个数组来记录各个数组的因子个数
     */
    class Solution {
        public static int MAX_N = 100_001;
        public static int[] cnt = new int[MAX_N];
        public static int[] sum = new int[MAX_N];
        public static boolean initialized = false;

        public Solution() {
            if (initialized) {
                return;
            }
            initialized = true;

            for (int i = 1; i < MAX_N; i++) {
                for (int j = 1;; j++) {
                    int tmp = i * j;
                    if (tmp < MAX_N) {
                        cnt[tmp]++;
                        sum[tmp] += i;
                    } else {
                        break;
                    }
                }
            }
        }

        public int sumFourDivisors(int[] nums) {
            int ans = 0;
            for (int num : nums) {
                if (cnt[num] == 4) {
                    ans += sum[num];
                }
            }
            return ans;
        }
    }
}
