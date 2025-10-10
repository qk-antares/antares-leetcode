package leetcode.greedy.logic;

/*
 * 逆向思维
 */
public class ReverseT {
    /*
     * 3147. 从魔法师身上吸取的最大能量
     * 
     * 终点可能是n-1, n-2, ... n-k
     * 枚举终点，然后计算"后缀和"
     */
    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        int ans = Integer.MIN_VALUE;
        for (int i = n - 1; i >= n - k; i--) {
            // 前缀和
            int s = 0;
            for (int j = i; j >= 0; j -= k) {
                s += energy[j];
                ans = Math.max(ans, s);
            }
        }
        return ans;
    }
}
