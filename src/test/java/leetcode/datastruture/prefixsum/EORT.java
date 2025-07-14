package leetcode.datastruture.prefixsum;

import java.util.Arrays;

/*
 * 前缀异或和
 */
public class EORT {
    /*
     * 1371. 每个元音包含偶数次的最长子字符串 [Medium] <Star>
     * 
     * 相当于枚举右，维护左
     * 枚举到的s[i]，需要知道它们每个元音字母的奇偶性
     * 5个字母的奇偶性有2^5个状态，者可以用1<<5的数组维护
     * 维护左边某个奇偶性状态最早出现的位置
     */
    public int findTheLongestSubstring(String s) {
        char[] arr = s.toCharArray();
        // 维护每个状态的最近位置
        int[] map = new int[1 << 5];
        Arrays.fill(map, -2);
        map[0] = -1;
        // 初始状态，每个元音字母都是0
        int status = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 'a') {
                status ^= 1;
            } else if (arr[i] == 'e') {
                status ^= (1 << 1);
            } else if (arr[i] == 'i') {
                status ^= (1 << 2);
            } else if (arr[i] == 'o') {
                status ^= (1 << 3);
            } else if (arr[i] == 'u') {
                status ^= (1 << 4);
            }

            if (map[status] != -2) {
                ans = Math.max(ans, i - map[status]);
            } else {
                map[status] = i;
            }
        }

        return ans;
    }
}
