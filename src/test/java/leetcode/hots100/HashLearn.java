package leetcode.hots100;

import org.junit.jupiter.api.Test;

import java.util.*;

public class HashLearn {
    /**
     * 两数之和
     */
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int left = target - nums[i];
            if(map.containsKey(left)){
                return new int[]{map.get(left), i};
            }
            map.put(nums[i], i);
        }

        return new int[]{};
    }

    /**
     * 最长连续序列
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            String key = calculateHash(str);
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<>(map.values());
    }
    String calculateHash(String str){
        int len = str.length();
        int[] count = new int[26];
        for (int i = 0; i < len; i++) {
             count[str.charAt(i)-'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count.length; i++) {
            sb.append((char)('a'+i) + count[i]);
        }
        return sb.toString();
    }

    /**
     * 最长连续序列
     */
    public int longestConsecutive(int[] nums) {
        if(nums.length == 0) return 0;
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int ans = 1, i;
        for (Integer num : set) {
            if(set.contains(num-1)){
                continue;
            }
            i = 1;
            while (set.contains(num+i)){
                i++;
            }
            ans = Math.max(ans, i);
        }
        return ans;
    }

    @Test
    void invoke(){
        groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"});
    }
}
