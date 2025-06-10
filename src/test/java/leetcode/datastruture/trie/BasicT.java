package leetcode.datastruture.trie;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BasicT {
    /*
     * 648. 单词替换 [Medium]
     */
    public String replaceWords(List<String> dictionary, String sentence) {
        Trie trie = new Trie();
        for (String s : dictionary) {
            trie.insert(s);
        }

        String[] ss = sentence.split(" ");
        for (int i = 0; i < ss.length; i++) {
            ss[i] = trie.findPrefix(ss[i]);
        }
        return String.join(" ", ss);
    }

    /*
     * 720. 词典中最长的单词 [Medium]
     * 
     * 构造字典树，然后对字典树进行广度优先搜索
     * 答案的遍历路径中，每个点都应该是True
     */
    List<Integer> ans = new ArrayList<>();

    public String longestWord(String[] words) {
        Trie trie = new Trie();
        for (String s : words) {
            trie.insert(s);
        }

        dfs(trie.root, new ArrayList<Integer>());

        StringBuilder sb = new StringBuilder();
        for (int ch : ans) {
            sb.append((char) (ch + 'a'));
        }
        return sb.toString();
    }

    void dfs(Node cur, List<Integer> path) {
        if (path.size() > ans.size())
            ans = new ArrayList<>(path);

        for (int i = 0; i < 26; i++) {
            if (cur.children[i] != null && cur.children[i].end) {
                path.add(i);
                dfs(cur.children[i], path);
                path.removeLast();
            }
        }
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 440. 字典序的第K小数字 [Hard] <Star>
     * 
     * 遍历10叉数
     * 从node=1开始，有两种选择：
     * 当node所在的子树（包含node）<k时，访问node的右兄弟，k-=node所在子树的大小
     * 否则：访问node的第一个儿子，k--
     */
    public int findKthNumber(int n, int k) {
        int node = 1;
        // 当k=1时return
        while (k > 1) {
            // 获取node所在子树的大小
            int size = countSubstree(n, node);
            // 不在这颗子树
            if (size < k) {
                node++;
                // k-size一定满足>0
                k -= size;
            } else {
                node *= 10;
                k--;
            }
        }
        return node;
    }

    // 以node=1为例
    // lower=1, upper=2
    // 1层：size+=2-1
    // 2层：size+=20-10
    // 3层：size+=200-100
    // 4层：size+=1235-1000
    int countSubstree(int n, int node) {
        long lower = node;
        long upper = node + 1;
        int size = 0;
        while (lower <= n) {
            size += Math.min(upper, n + 1) - lower;
            lower *= 10;
            upper *= 10;
        }
        return size;
    }

    @Test
    public void test() {
        longestWord(new String[] { "w", "wo", "wor", "worl", "world" });
    }
}
