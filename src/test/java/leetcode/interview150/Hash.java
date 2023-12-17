package leetcode.interview150;

import org.junit.jupiter.api.Test;

import java.util.*;

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
     * 49. 字母异位词分组
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> ans = new HashMap<>();
        char[] chars;
        for (String str : strs) {
            chars = str.toCharArray();
            Arrays.sort(chars);
            if (ans.containsKey(String.valueOf(chars))) {
                ans.get(String.valueOf(chars)).add(str);
            } else {
                ans.put(String.valueOf(chars), new ArrayList<String>(Collections.singletonList(str)));
            }
        }
        return new ArrayList<>(ans.values());
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

    /**
     * 128. 最长连续序列
     */
    public int longestConsecutive(int[] nums) {
        if(nums.length == 0) return 0;

        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int ans = Integer.MIN_VALUE;
        int max;
        for (int num : nums) {
            if(set.contains(num-1)){
                continue;
            }
            max = 1;
            while (set.contains(num+max)){
                max++;
            }
            ans = Math.max(max, ans);
        }
        return ans;
    }

    @Test
    void test(){
        isIsomorphic("badc", "baba");
    }
}
