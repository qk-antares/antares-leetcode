package leetcode.str;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 字符串排序
 */
public class Sort {
    /*
     * 1233. 删除子文件夹 [Medium]
     * 
     * 假设List<String> ans保存当前的结果
     * 每遍历到一个新的folder
     * 如果folder是ans中某个元素的子串，则替换他
     * 如果ans中某个元素是folder的子串，则不添加folder
     * 否则将folder添加进ans
     * 
     * 上述解法会有两个问题：
     * 问题1是每遍历一个folder，都需要与ans做正向和反向两次比较，时间复杂度很高
     * 问题2是如果ans中有多个元素是folder的子文件夹，只会替换掉一个，导致答案错误
     * 
     * 首先对folder排序，则能够作为根目录的一定排在前面，所以只需要跟前面比较
     * 更进一步地，只需要跟前面的一个元素pre比较
     * 如果当前folder不是pre的子目录，则添加进ans
     * 不是子目录有两种情况：
     * !folder.startWith(pre)
     * folder.startWith(pre) && folder.charAt(pre.length()) != '/'
     */
    public List<String> removeSubfolders0(String[] folder) {
        int n = folder.length;
        char[][] ss = new char[n][];
        int[][] pmts = new int[n][];
        for (int i = 0; i < n; i++) {
            ss[i] = folder[i].toCharArray();
            pmts[i] = buildPmt(ss[i]);
        }

        // 保存答案的索引
        List<Integer> idx = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            boolean flag = true;

            for (int j = 0; j < idx.size(); j++) {
                int k = idx.get(j);
                if (substring(ss[k], ss[i], pmts[i])) {
                    idx.set(j, i);
                    flag = false;
                    break;
                } else if (substring(ss[i], ss[k], pmts[k])) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                idx.add(i);
            }
        }

        List<String> ans = new ArrayList<>();
        for (int i : idx) {
            ans.add(folder[i]);
        }

        return ans;
    }

    // 判断p是否是s的子串
    boolean substring(char[] s, char[] p, int[] pmt) {
        int m = s.length, n = p.length;
        if (m < n)
            return false;

        int j = 0;

        for (int i = 0; i < m; i++) {
            while (j > 0 && s[i] != p[j])
                j = pmt[j - 1];
            if (s[i] == p[j])
                j++;
            if (j == n)
                return s[i + 1] == '/';
        }

        return false;
    }

    int[] buildPmt(char[] p) {
        int n = p.length;
        int[] pmt = new int[n];
        int j = 0; // 当前匹配长度

        for (int i = 1; i < n; i++) {
            while (j > 0 && p[i] != p[j])
                j = pmt[j - 1];
            if (p[i] == p[j])
                j++;
            pmt[i] = j;
        }

        return pmt;
    }

    public List<String> removeSubfolders(String[] folder) {
        Arrays.sort(folder);
        List<String> ans = new ArrayList<>();
        ans.add(folder[0]);

        for (int i = 1; i < folder.length; i++) {
            String pre = ans.getLast();
            if (!folder[i].startsWith(pre) || folder[i].charAt(pre.length()) != '/') {
                ans.add(folder[i]);
            }
        }

        return ans;
    }
}
