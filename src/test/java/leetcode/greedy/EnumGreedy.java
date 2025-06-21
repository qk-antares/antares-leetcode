package leetcode.greedy;

import java.util.Arrays;

/*
 * 先枚举，再贪心
 */
public class EnumGreedy {
    /*
     * 3085. 成为 K 特殊字符串需要删除的最少字符数
     * 
     * 首先统计26个字母的频数，然后排序
     * 不为0的频数分散在数轴上，用大小为k+1的窗口来框
     * 刚开始的时候，前k+1个字母(a,b,...)在窗口中，计算此时需要删除的字符数量cur
     * 随着窗口向右移动，调整cur，并记录最小的cur作为ans
     */
    public int minimumDeletions0(String word, int k) {
        int[] cnt = new int[26];
        for (char c : word.toCharArray()) {
            cnt[c - 'a']++;
        }
        Arrays.sort(cnt);

        int maxSave = 0;
        //枚举i作为保留区间的左边界
        for (int i = 0; i < 26; i++) {
            int sum = 0;
            for (int j = i; j < 26; j++) {
                sum += Math.min(cnt[j], cnt[i] + k); // 至多保留 cnt[i]+k 个
            }
            maxSave = Math.max(maxSave, sum);
        }

        return word.length() - maxSave;
    }

    public int minimumDeletions(String word, int k) {
        int[] cnt = new int[26];
        for (char c : word.toCharArray()) {
            cnt[c - 'a']++;
        }
        Arrays.sort(cnt);

        int maxSave = 0;
        // 枚举i作为保留区间的左边界
        for (int i = 0; i < 26; i++) {
            int sum = 0;
            for (int j = i; j < 26; j++) {
                sum += Math.min(cnt[j], cnt[i] + k); // 至多保留 cnt[i]+k 个
            }
            maxSave = Math.max(maxSave, sum);
        }

        return word.length() - maxSave;
    }
}
