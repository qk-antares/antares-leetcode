package leetcode.datastruture.trie;

public class HardT {
    /**
     * 211. 添加与搜索单词 - 数据结构设计 [Medium]
     */
    class WordDictionary {
        // 字典树，匹配是对.做特殊处理即可
        Node root;

        public WordDictionary() {
            root = new Node();
        }

        public void addWord(String word) {
            char[] s = word.toCharArray();
            Node cur = root;
            for (char c : s) {
                int idx = c - 'a';
                if (cur.children[idx] == null) {
                    cur.children[idx] = new Node();
                }
                cur = cur.children[idx];
            }
            cur.end = true;
        }

        public boolean search(String word) {
            char[] s = word.toCharArray();
            return search(root, s, 0);
        }

        public boolean search(Node cur, char[] s, int start) {
            for (int i = start; i < s.length; i++) {
                char c = s[i];
                if (c != '.') {
                    int idx = c - 'a';
                    if (cur.children[idx] == null)
                        return false;
                    cur = cur.children[idx];
                } else {
                    for (Node child : cur.children) {
                        if (child != null && search(child, s, i + 1))
                            return true;
                    }
                    return false;
                }
            }
            return cur.end;
        }
    }
}
