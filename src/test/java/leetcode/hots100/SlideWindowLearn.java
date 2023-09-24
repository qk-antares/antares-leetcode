package leetcode.hots100;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SlideWindowLearn {
    /**
     * 无重复字符的最长子串
     */
    public int lengthOfLongestSubstring(String s) {
        int ans = 0;
        int l = 0,r = 0,len = s.length();
        HashSet<Character> window = new HashSet<>();
        while (r < len){
            while (window.contains(s.charAt(r))){
                //窗口左边界开始向右收缩
                window.remove(s.charAt(l++));
            }
            window.add(s.charAt(r));
            ans = Math.max(ans, window.size());
            r++;
        }
        return ans;
    }

    /**
     * 找到字符串中所有字母异位词
     */
    public List<Integer> findAnagrams(String s, String p) {
        ArrayList<Integer> ans = new ArrayList<>();
        int sLen = s.length(), pLen = p.length();
        if(sLen < pLen) return ans;

        int[] targetCount = calculateCount(p);
        int[] currentCount = new int[26];
        for (int i = 0; i < pLen; i++) {
            currentCount[s.charAt(i)-'a']++;
        }
        for (int i = 0; i <= sLen - pLen; i++) {
            if(verify(targetCount, currentCount)){
                ans.add(i);
            }
            if(i != sLen - pLen){
                currentCount[s.charAt(i)-'a']--;
                currentCount[s.charAt(pLen+i)-'a']++;
            }
        }
        return ans;
    }

    int[] calculateCount(String p) {
        int len = p.length();
        int[] count = new int[26];
        for (int i = 0; i < len; i++) {
            count[p.charAt(i)-'a']++;
        }
        return count;
    }

    boolean verify(int[] target, int[] cur){
        for (int i = 0; i < target.length; i++) {
            if(target[i] != cur[i]) return false;
        }
        return true;
    }

    @Test
    void invoke(){
        findAnagrams("abab", "ab");
    }
}
