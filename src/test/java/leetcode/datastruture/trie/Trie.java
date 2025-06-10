package leetcode.datastruture.trie;

class Node {
    Node[] children = new Node[26]; // 26个小写字母
    boolean end = false;    // 该节点是否对应一个完整的单词结尾
}

class Trie {
    Node root = new Node();

    public void insert(String word) {
        Node cur = root;
        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            if (cur.children[idx] == null)
                cur.children[idx] = new Node();
            cur = cur.children[idx];
        }
        cur.end = true;
    }

    public boolean search(String word) {
        return find(word) == 1;
    }

    public boolean startsWith(String prefix) {
        return find(prefix) != 0;
    }

    // 0代表不匹配，1代表完全匹配，2代表前缀匹配
    int find(String s) {
        Node cur = root;
        for (char ch : s.toCharArray()) {
            int idx = ch - 'a';
            if (cur.children[idx] == null)
                return 0;
            cur = cur.children[idx];
        }
        return cur.end ? 1 : 2;
    }

    // 查找s在字典中的最短前缀，如果找不到返回s本身
    String findPrefix(String s) {
        int len = 0;
        Node cur = root;
        for (char ch : s.toCharArray()) {
            len++;
            int idx = ch - 'a';
            if (cur.children[idx] == null)
                return s;
            if (cur.children[idx].end)
                break;
            cur = cur.children[idx];
        }
        return s.substring(0, len);
    }
}