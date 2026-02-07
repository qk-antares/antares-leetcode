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
        for (int i = 0; i < n; i++) {
            s += nums[i];
        }

        int ans = 0;
        int cur = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                if (cur * 2 == s)
                    ans += 2;
                else if (cur * 2 == s - 1 || cur * 2 == s + 1)
                    ans += 1;
            }

            cur += nums[i];
        }
        return ans;
    }

    /**
     * 1653. 使字符串平衡的最少删除次数 [Medium]
     * 
     * 记录每个字符左侧b的数量和右侧a的数量
     * 枚举每个字符作为边界
     * 
     * 统计s中a的个数，初始时假设删除所有a（边界在idx=0之前），ans=a
     * 接下来从左往右遍历（遍历到的元素作为边界左侧的元素）：
     * 遇到a则删除次数-1
     * 遇到b则删除次数+1
     * 
     * 将if-else分支合并成运算可以提高运算效率
     */
    public int minimumDeletions(String s) {
        char[] ss = s.toCharArray();
        int n = ss.length;
        int[] lb = new int[n];
        int[] ra = new int[n];

        for (int i = 0; i < n - 1; i++) {
            if (ss[i] == 'b') {
                lb[i + 1] = lb[i] + 1;
            } else {
                lb[i + 1] = lb[i];
            }
            if (ss[n - 1 - i] == 'a') {
                ra[n - 2 - i] = ra[n - 1 - i] + 1;
            } else {
                ra[n - 2 - i] = ra[n - 1 - i];
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, lb[i] + ra[i]);
        }

        return ans;
    }

    public int minimumDeletions0(String s) {
        char[] ss = s.toCharArray();
        int cnt = 0;
        for (char ch : ss) {
            cnt += 'b' - ch;
        }

        int ans = cnt;
        for (int i = 0; i < ss.length; i++) {
            cnt += (ss[i] - 'a') * 2 - 1;
            ans = Math.min(ans, cnt);
        }

        return ans;
    }
}
