package leetcode.datastruture.segmenttree;

import org.junit.jupiter.api.Test;

/*
 * 线段树
 */
public class SegT {
    /*
     * 3479. 水果成篮 III [Medium]
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

    // i到j的移动是单向的（从左到右），所以答案是heights中[Math.max(a,b),
    // n-1]的区间内，第一个大于Math.max(heights[a], heights[b])的元素idx
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
        leftmostBuildingQueries(new int[]{6,4,8,5,2,7}, new int[][]{{0, 1}, {0, 3}, {2, 4}, {3, 4}, {2, 2}});
    }
}
