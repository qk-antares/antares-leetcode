package leetcode.datastruture.design;

import java.util.Random;

public class SkipListT {
    class SkipListNode {
        int val;
        SkipListNode[] next; // 每一层的前进指针

        public SkipListNode(int val, int level) {
            this.val = val;
            this.next = new SkipListNode[level];
        }
    }

    static final int MAX_LEVEL = 32;    // 最大层数
    static final double P_FACTOR = 0.25; // 长高概率
    private Random random;
    private SkipListNode head; // 头节点

    public SkipListT() {
        this.head = new SkipListNode(-1, MAX_LEVEL);
        this.random = new Random();
    }

    /**
     * 找到每一层严格小于target的最后一个节点，即target的前驱节点
     * @param target
     * @return
     */
    SkipListNode[] find(int target) {
        SkipListNode[] prev = new SkipListNode[MAX_LEVEL];
        SkipListNode curr = head;
        for (int level = MAX_LEVEL - 1; level >= 0; level--) {
            while (curr.next[level] != null && curr.next[level].val < target) {
                curr = curr.next[level];
            }
            prev[level] = curr;
        }
        return prev;
    }

    public boolean search(int target) {
        SkipListNode[] prev = find(target);
        // 最底层前驱的后继即为目标节点
        SkipListNode curr = prev[0].next[0];
        return curr != null && curr.val == target;
    }

    public void add(int num) {
        SkipListNode[] prev = find(num);
        // 为新节点随机生成层数
        int level = randomLevel();
        SkipListNode newNode = new SkipListNode(num, level);
        for (int i = 0; i < level; i++) {
            // 后继设置为前驱的后继
            newNode.next[i] = prev[i].next[i];
            // 前驱的后继设置为新节点
            prev[i].next[i] = newNode;
        }
    }

    private int randomLevel() {
        int level = 1;
        while (random.nextDouble() < P_FACTOR && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public boolean erase(int num) {
        SkipListNode[] prev = find(num);
        // 最底层前驱的后继即为目标节点
        SkipListNode curr = prev[0].next[0];
        if (curr == null || curr.val != num) {
            return false;
        }
        for (int i = 0; i < curr.next.length; i++) {
            // 前驱的后继设置为当前节点的后继
            prev[i].next[i] = curr.next[i];
        }
        return true;
    }

}
