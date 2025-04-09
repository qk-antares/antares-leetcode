package leetcode.questions.T1000.T800;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GreedyT {
    /*
     * 763. 划分字母区间
     * 
     * 方法一：
     * 首先使用一个int[] cnt统计s中各种字符的个数
     * 创建一个boolean[] curStr来记录当前遍历出现过的字符
     * 从左往右遍历s，每遇到一个字符，在cnt中减去，并将curStr对应的位置置为true
     * 检查cnt中，curStr中为true的位置是否都为0了
     * 如果都为0，证明右侧没有相同的字符了，执行一次分割并重置curStr
     * 否则接着遍历
     * 
     * 方法二：
     * 直接对s进行一次遍历，统计每个字符的最右侧位置
     * 然后对s进行第二次遍历，遍历过程中统计当前这一段字符对应的最右侧位置end
     * 直至end==当前遍历到的i，则添加一个答案
     */
    public List<Integer> partitionLabels0(String s) {
        List<Integer> ans = new ArrayList<>();

        char[] arr = s.toCharArray();
        int[] cnt = new int[26];
        for (char ch : arr) {
            cnt[ch - 'a']++;
        }

        boolean[] curStr = new boolean[26];
        int pre = -1;
        for (int i = 0; i < arr.length; i++) {
            cnt[arr[i] - 'a']--;
            curStr[arr[i] - 'a'] = true;
            if (check(cnt, curStr)) {
                ans.add(i - pre);
                Arrays.fill(curStr, false);
                pre = i;
            }
        }

        return ans;
    }

    boolean check(int[] cnt, boolean[] curStr) {
        for (int j = 0; j < 26; j++) {
            if (curStr[j] && cnt[j] > 0)
                return false;
        }
        return true;
    }

    public List<Integer> partitionLabels(String s) {
        List<Integer> ans = new ArrayList<>();

        char[] arr = s.toCharArray();
        int[] last = new int[26];
        for (int i = 0; i < arr.length; i++) {
            last[arr[i] - 'a'] = i;
        }

        int pre = -1, end = 0;
        for (int i = 0; i < arr.length; i++) {
            end = Math.max(end, last[arr[i] - 'a']);
            if (i == end) {
                ans.add(end - pre);
                pre = end;
            }
        }

        return ans;
    }
}
