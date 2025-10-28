package leetcode.dp;

/*
 * 前后缀分解
 */
public class PreSuffixT {
    /*
     * 3354. 使数组元素等于零
     * 
     * curr左右的和相同(或相差1)，当相同时+2，相差1时+1
     */
    public int countValidSelections0(int[] nums) {
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
        }

        int ans = 0;
        if (s[n] % 2 != 0) {
            for (int i = 0; i < n; i++) {
                if (nums[i] == 0 && (s[i + 1] == s[n] / 2 || s[i + 1] == s[n] / 2 + 1)) {
                    ans++;
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                if (nums[i] == 0 && s[i + 1] == s[n] / 2) {
                    ans += 2;
                }
            }
        }
        return ans;
    }

    /*
     * 一种更简便的写法
     */
    public int countValidSelections(int[] nums) {
        int n = nums.length;
        int s = 0;
        for(int i = 0; i < n; i++) {
            s += nums[i];
        }

        int ans = 0;
        int cur = 0;
        for(int i = 0; i < n; i++) {
            if(nums[i] == 0) {
                if(cur * 2 == s) ans += 2;
                else if(cur*2 == s-1 || cur*2 == s+1) ans += 1;
            }

            cur += nums[i];
        }
        return ans;
    }
}
