package leetcode.questions.T3000.T2200;

public class ArrayT {
    /*
     * TODO 2179. 统计数组中好三元组数目
     * 
     * 置换：相当于换了一个坐标系
     * 对nums1和nums2应用同样的置换，则公共的上升子序列从(x,y,z)转成(p(x),p(y),p(z))
     */
    class BinaryIndexedTree {
        int[] tree;

        public BinaryIndexedTree(int n) {
            tree = new int[n + 1];
        }

        public void update(int i, int val) {
            for (; i < tree.length; i += i & -i) {
                tree[i] += val;
            }
        }

        public int pre(int i) {
            int sum = 0;
            for (; i > 0; i -= i & -i) {
                sum += tree[i];
            }
            return sum;
        }
    }

    public long goodTriplets(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] permute = new int[n];
        // 得到置换函数
        for (int i = 0; i < n; i++) {
            permute[nums1[i]] = i;
        }

        // 小优化，这里没有必要直接对nums2应用置换，而是在下面的for循环中获取对应的置换(后)元素即可
        // 对nums2应用同样的置换函数
        // for(int i = 0; i < n; i++) {
        // nums2[i] = permute[nums2[i]];
        // }

        BinaryIndexedTree tree = new BinaryIndexedTree(n);
        tree.update(permute[nums2[0]] + 1, 1);

        long ans = 0;
        // 统计nums2中长度为3的严格递增子序列
        for (int i = 1; i < n - 1; i++) {
            int y = permute[nums2[i]];

            // 左侧小于nums2[i]的元素个数
            int preMin = tree.pre(y);
            // 由于全部元素是n个，小于nums2[i]的元素共计有nums2[i]个，所以说右侧会有num2[i]-preMin个
            // int lastMin = nums2[i] - preMin;
            // 而右侧一共有n-i-1个元素，所以说右侧大于nums2[i]的有
            int lastMax = n - i - 1 - y + preMin;

            ans += (long) preMin * lastMax;

            tree.update(y + 1, 1);
        }

        return ans;
    }
}
