package leetcode.interview150;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class Hash {
    /**
     * 383. 赎金信
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        if(ransomNote.length() > magazine.length()) return false;
        int[] cnt = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            cnt[magazine.charAt(i)-'a']++;
        }

        for (int i = 0; i < ransomNote.length(); i++) {
            cnt[ransomNote.charAt(i)-'a']--;
            if(cnt[ransomNote.charAt(i)-'a'] < 0) return false;
        }

        return true;
    }

    /**
     * 205. 同构字符串
     */
    public boolean isIsomorphic(String s, String t) {
        HashMap<Character, Character> map = new HashMap<>();
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if(!map.containsKey(s.charAt(i))){
                if(set.contains(t.charAt(i))){
                    return false;
                }
                map.put(s.charAt(i), t.charAt(i));
                set.add(t.charAt(i));
            } else {
                if(t.charAt(i) != map.get(s.charAt(i))){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 290. 单词规律
     */
    public boolean wordPattern(String pattern, String s) {
        String[] strings = s.split(" ");
        if(pattern.length() != strings.length) return false;

        HashMap<String, Character> map = new HashMap<>();
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < strings.length; i++) {
            if(!map.containsKey(strings[i])){
                if(set.contains(pattern.charAt(i))){
                    return false;
                }
                map.put(strings[i], pattern.charAt(i));
                set.add(pattern.charAt(i));
            } else {
                if(pattern.charAt(i) != map.get(strings[i])){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 219. 存在重复元素 II
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(map.containsKey(nums[i]) && i-map.get(nums[i]) <= k){
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }

    @Test
    void test(){
        isIsomorphic("badc", "baba");
    }
}
