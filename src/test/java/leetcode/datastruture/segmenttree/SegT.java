package leetcode.datastruture.segmenttree;

import org.junit.jupiter.api.Test;

/*
 * 线段树
 */
public class SegT {
    /*
     * 3479. 水果成篮 III [Medium]
     * 
     * 关于max数组的长度，从以下几个角度理解：
     * 1. 数组中的元素作为树的叶子节点，例如n=8，那么总节点数=8+4+2+1=2*8-1=15
     * 又由于树节点的访问是从1开始的，因此需要16个元素，即2*n
     * 
     * 2. 如果n不是2的幂，那么可以将其补齐到2的幂，考虑冗余的话，我们可以直接使用n*4
     * 而为了尽可能减少冗余，我们可以使用Integer.highestOneBit(n-1)<<1来代表补齐后的2的幂
     * 然后将其乘以2得到max数组的长度，即Integer.highestOneBit(n-1)<<2
     * 但上述方法在n=1时会导致max数组长度为0，这有以下解决方案：
     * - 使用2<<(32-Integer.numberOfLeadingZeros(n-1))，也是最推荐的
     * - 使用n==1 ? 2 : Integer.highestOneBit(n-1)<<2
     * - 使用Integer.highestOneBit(n)<<2，这样的话会在n为2的幂时多出冗余，但在非2的幂时不会
     */
    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        class SegmentTree {
            int[] max;

            public SegmentTree(int[] a) {
                int n = a.length;
                max = new int[Integer.highestOneBit(n - 1) << 2];
                build(a, 1, 0, n - 1);
            }

            void maintain(int o) {
                max[o] = Math.max(max[2 * o], max[2 * o + 1]);
            }

            void build(int[] a, int o, int l, int r) {
                if (l == r) {
                    max[o] = a[l];
                    return;
                }

                int mid = (l + r) / 2;
                build(a, 2 * o, l, mid);
                build(a, 2 * o + 1, mid + 1, r);
                maintain(o);
            }

            int findFirstAndUpdate(int o, int l, int r, int target) {
                if (max[o] < target) {
                    return -1;
                }
                if (l == r) {
                    max[o] = -1;
                    return l;
                }
                int mid = (l + r) / 2;
                int i = findFirstAndUpdate(2 * o, l, mid, target);
                if (i < 0) {
                    i = findFirstAndUpdate(2 * o + 1, mid + 1, r, target);
                }
                maintain(o);
                return i;
            }
        }

        SegmentTree st = new SegmentTree(baskets);
        int n = fruits.length;
        int ans = 0;
        for (int f : fruits) {
            if (st.findFirstAndUpdate(1, 0, n - 1, f) < 0) {
                ans++;
            }
        }
        return ans;
    }

    /*
     * 2940. 找到 Alice 和 Bob 可以相遇的建筑 [Hard]
     * 
     * i到j的移动是单向的（从左到右）
     * 假设a<=b
     * 当a==b || heights[a] < heights[b]时，a可以到达b
     * 否则（heights[a]>=heights[b]）：
     * 答案是heights中[b+1, n-1]的区间内，第一个大于heights[a]的元素idx
     * 
     * 注意区间查找的线段树findFrist方法有所不同，会多一个参数
     */
    public int[] leftmostBuildingQueries(int[] heights, int[][] queries) {
        class SegmentTree {
            int[] max;

            public SegmentTree(int[] a) {
                int n = a.length;
                max = new int[2 << (32 - Integer.numberOfLeadingZeros(n - 1))];
                build(a, 1, 0, n - 1);
            }

            void maintain(int o) {
                max[o] = Math.max(max[2 * o], max[2 * o + 1]);
            }

            void build(int[] a, int o, int l, int r) {
                if (l == r) {
                    max[o] = a[l];
                    return;
                }

                int mid = (l + r) / 2;
                build(a, 2 * o, l, mid);
                build(a, 2 * o + 1, mid + 1, r);
                maintain(o);
            }

            int findFirst(int o, int l, int r, int target) {
                if (max[o] <= target) {
                    return -1;
                }
                if (l == r) {
                    return l;
                }

                int mid = (l + r) / 2;
                int i = findFirst(2 * o, l, mid, target);
                if (i < 0) {
                    i = findFirst(2 * o + 1, mid + 1, r, target);
                }
                return i;
            }
        }

        int[] ans = new int[queries.length];
        SegmentTree st = new SegmentTree(heights);
        int n = heights.length;

        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            ans[i] = st.findFirst(1, Math.max(q[0], q[1]), n - 1, Math.max(heights[q[0]], heights[q[1]]));
        }

        return ans;
    }

    @Test
    public void testSegmentTree() {
        leftmostBuildingQueries(new int[] { 6, 4, 8, 5, 2, 7 },
                new int[][] { { 0, 1 }, { 0, 3 }, { 2, 4 }, { 3, 4 }, { 2, 2 } });
    }
}
