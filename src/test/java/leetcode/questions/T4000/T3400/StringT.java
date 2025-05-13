package leetcode.questions.T4000.T3400;

import java.util.ArrayDeque;
import java.util.Deque;

public class StringT {
    /*
     * 3335. 字符串转换后的长度 I
     * 
     * 首先统计s中各个字符的出现次数cnt
     * 执行一次操作后，cnt[0..24]向右移动一格到cnt[1..25]，cnt[0]=cnt[25]（需提前保存），cnt[1]+=cnt[25]
     * 由于经常在头尾执行操作，考虑使用Deque来维护cnt
     * 则上面提到的"右移一格"：cntZ = deque.removeLast()
     * cnt[1]+=cnt[25]：cntB = deque.removeFirst(); cntB += cntZ;
     * deque.addFirst(cntB)
     * cnt[0]=cnt[25]: deque.addFirst(cntZ)
     * 
     * 需要注意防溢出
     */
    public int lengthAfterTransformations(String s, int t) {
        char[] arr = s.toCharArray();
        int[] cnt = new int[26];
        for (char ch : arr)
            cnt[ch - 'a']++;
        Deque<Integer> deque = new ArrayDeque<>();
        for (int c : cnt)
            deque.addLast(c);

        for (int i = 0; i < t; i++) {
            int cntZ = deque.removeLast();
            int cntB = deque.removeFirst();
            cntB += cntZ;
            cntB %= 1_000_000_007;

            deque.addFirst(cntB);
            deque.addFirst(cntZ);
        }

        int ans = 0;
        while (!deque.isEmpty()) {
            ans = (ans + deque.removeFirst()) % 1_000_000_007;
        }

        return ans;
    }
}
