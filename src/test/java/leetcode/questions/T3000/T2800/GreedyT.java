package leetcode.questions.T3000.T2800;

public class GreedyT {
    /*
     * 2712. 使所有字符相等的最小成本   [Medium]
     */
    public long minimumCost(String s) {
        long ans = 0;
        int len = s.length();
        for (int i = 0; i < len - 1; i++) {
            if (s.charAt(i) != s.charAt(i + 1)) {
                ans += Math.min(i + 1, len - i - 1);
            }
        }
        return ans;
    }
}
