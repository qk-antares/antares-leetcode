package leetcode.hots100;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SlideWindowLearn {
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
