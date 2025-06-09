package leetcode.datastruture.trie;

public class BasicT {
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
}
