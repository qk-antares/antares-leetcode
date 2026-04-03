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

/**
 * 另一种写法，代码更简洁
 */
class Trie0 {
    Trie0[] children;
    boolean isLeaf;

    public Trie0() {
        children = new Trie0[26];
        isLeaf = false;
    }
    
    public void insert(String word) {
        char[] s = word.toCharArray();
        Trie0 cur = this;
        for(char ch : s) {
            if(cur.children[ch-'a'] == null) cur.children[ch-'a'] = new Trie0();
            cur = cur.children[ch-'a'];
        }
        cur.isLeaf = true;
    }
    
    public boolean search(String word) {
        return find(word) == 1;
    }
    
    public boolean startsWith(String prefix) {
        return find(prefix) != 0;
    }

    int find(String str) {
        char[] s = str.toCharArray();
        Trie0 cur = this;
        for(char ch : s) {
            cur = cur.children[ch-'a'];
            if(cur == null) return 0;
        }
        return cur.isLeaf ? 1 : 2;
    }
}
