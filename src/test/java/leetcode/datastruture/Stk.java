package leetcode.datastruture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class Stk {
    /*
     * 3170. 删除星号以后字典序最小的字符串 [Medium]
     * 
     * 从前往后遍历s，用小根堆来保存所有字符（及其下标）
     * 排序首先按照字符大小，其次按照下标（下标越大越靠近堆顶）
     * 当遇到一个*，这意味着我们要从已有的堆顶取出一个元素
     * 最后堆中剩下的元素即为答案
     * 
     * 另一种方法是使用26个栈，从前往后遍历s，并保存遇到的字母的index
     * 当遇到*，从前往后遍历26个栈，找到第一个非空的栈，弹出栈顶元素，并将s的对应位置设置为*
     * 最后对s进行一次遍历，删除所有的*
     * 
     */
    public String clearStars0(String s) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0] != 0 ? o1[0] - o2[0] : o2[1] - o1[1]);
        char[] arr = s.toCharArray();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] != '*')
                heap.offer(new int[] { arr[i], i });
            else
                heap.poll();
        }

        List<Integer> idx = heap.stream()
                .map(o -> o[1])
                .sorted()
                .collect(Collectors.toList());

        int m = idx.size();

        char[] ans = new char[m];
        for (int i = 0; i < m; i++) {
            ans[i] = arr[idx.get(i)];
        }

        return new String(ans);
    }

    @SuppressWarnings("unchecked")
    public String clearStars(String s) {
        char[] arr = s.toCharArray();
        List<Integer>[] stks = new List[26];
        Arrays.setAll(stks, i->new ArrayList<>());
    
        int n = arr.length;
        for(int i = 0; i < n; i++) {
            if(arr[i] != '*') stks[arr[i]-'a'].add(i);
            else {
                for(int j = 0; j < 26; j++) {
                    if(!stks[j].isEmpty()) {
                        arr[stks[j].removeLast()] = '*';
                        break;
                    }
                }
                
            }
        }

        int j = 0;
        for(int i = 0; i < n; i++) {
            if(arr[i] != '*') arr[j++] = arr[i];
        }

        return new String(arr, 0, j);
    }

    @Test
    public void testClearStars() {
        String s = "aaba*";
        String result = clearStars(s);
        System.out.println(result); // Expected output: "leetcode"
    }
}
