package leetcode.questions.T3000.T2100;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DFST {
    /*
     * 2094. 找出 3 位偶数  [Easy]
     * 
     * 统计0-9各个元素的出现次数
     * 由于不含前导0，所以第一位不能是0
     * 第二位随意，第三位0、2、4...
     * 回溯
     * 
     * 优化的点在于：
     * 在回溯时，可以从高位开始枚举，这样得到的结果直接就是升序排列的，避免进行一次排序；
     * 回溯时，对于三种情况的判断可以合并
     * 
     * 注意的点：
     * List转Array时，使用list.stream().mapToInt(Integer::intValue).toArray()
     */
    public int[] findEvenNumbers0(int[] digits) {
        int[] cnt = new int[10];
        for (int digit : digits)
            cnt[digit]++;

        List<Integer> ans = new ArrayList<>();

        dfs(cnt, ans, 0, 0);

        Collections.sort(ans);
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    public void dfs0(int[] cnt, List<Integer> ans, int cur, int idx) {
        if (idx == 3) {
            ans.add(cur);
            return;
        }
        for (int i = 0; i < 10; i++) {
            if (cnt[i] > 0) {
                cur += i;
                cnt[i]--;
                dfs(cnt, ans, cur, 1);
                cnt[i]++;
                cur -= i;
            }
        }
    }

    public int[] findEvenNumbers(int[] digits) {
        int[] cnt = new int[10];
        for (int digit : digits)
            cnt[digit]++;

        List<Integer> ans = new ArrayList<>();

        dfs(cnt, ans, 0, 0);

        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    public void dfs(int[] cnt, List<Integer> ans, int cur, int idx) {
        if (idx == 3) {
            ans.add(cur / 10);
            return;
        }
        for (int i = 0; i < 10; i++) {
            if (cnt[i] > 0 && (idx == 0 && i != 0 || idx == 1 || idx == 2 && i % 2 == 0)) {
                cur = cur + i;
                cnt[i]--;
                dfs(cnt, ans, cur * 10, idx + 1);
                cnt[i]++;
                cur -= i;
            }
        }
    }
}
