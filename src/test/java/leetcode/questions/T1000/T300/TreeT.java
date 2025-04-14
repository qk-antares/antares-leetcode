package leetcode.questions.T1000.T300;

public class TreeT {
    /*
     * 208. 实现 Trie (前缀树)  [Medium]
     */
    class Trie {
        // 判断是否是叶子节点
        boolean end;
        // 子节点
        Trie[] children;

        public Trie() {
            children = new Trie[26];
            end = false;
        }

        public void insert(String word) {
            Trie cur = this;
            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';
                if (cur.children[index] == null) {
                    cur.children[index] = new Trie();
                }
                cur = cur.children[index];
            }
            cur.end = true;
        }

        public boolean search(String word) {
            Trie cur = this;
            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';
                if (cur.children[index] == null) {
                    return false;
                }
                cur = cur.children[index];
            }
            return cur.end;
        }

        public boolean startsWith(String prefix) {
            Trie cur = this;
            for (int i = 0; i < prefix.length(); i++) {
                int index = prefix.charAt(i) - 'a';
                if (cur.children[index] == null) {
                    return false;
                }
                cur = cur.children[index];
            }
            return true;
        }
    }

}
