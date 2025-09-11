package leetcode.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 和字符串相关的排序题
 */
public class StringSortT {
    /*
     * 2785. 将字符串中的元音字母排序 [Medium]
     * 
     * 将s中的元音收集一遍，然后排序
     * 
     * 直接统计s中各个元音的个数
     */
    public String sortVowels0(String s) {
        List<Character> list = new ArrayList<>();
        char[] ss = s.toCharArray();
        for (char ch : ss) {
            char low = Character.toLowerCase(ch);
            if (low == 'a' || low == 'e' || low == 'i' || low == 'o' || low == 'u') {
                list.add(ch);
            }
        }
        Collections.sort(list);

        int n = list.size();
        for (int i = 0, j = 0; i < ss.length && j < n; i++) {
            char low = Character.toLowerCase(ss[i]);
            if (low == 'a' || low == 'e' || low == 'i' || low == 'o' || low == 'u') {
                ss[i] = list.get(j++);
            }
        }

        return new String(ss);
    }

    public String sortVowels(String s) {
        char[] ss = s.toCharArray();
        int[] cnt = new int[128];
        for (char ch : ss) {
            cnt[ch]++;
        }

        char[] flag = new char[] { 'A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u' };
        int j = 0;
        for (int i = 0; i < ss.length; i++) {
            char low = Character.toLowerCase(ss[i]);
            if (low == 'a' || low == 'e' || low == 'i' || low == 'o' || low == 'u') {
                while (cnt[flag[j]] <= 0) {
                    j++;
                }
                ss[i] = flag[j];
                cnt[flag[j]]--;
            }
        }

        return new String(ss);
    }
}
