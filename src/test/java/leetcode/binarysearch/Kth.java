package leetcode.binarysearch;

public class Kth {
    /*
     * 378. 有序矩阵中第 K 小的元素 [Medium]
     * 
     * 二分答案，ans的值域是[matrix[0][0], matrix[r][r]]
     */
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int l = matrix[0][0], r = matrix[n - 1][n - 1];
        while (l <= r) {
            int mid = l + (r - l) / 2;
            // <=mid的数量>=k，在临界情况下，即<=mid的数量=k-1
            if (check(matrix, mid, k)) {
                r = mid - 1;
            } else { // <=mid的数量<k，在临界情况下，即<=mid的数量=k
                l = mid + 1;
            }
        }
        // r恰好不满足>=数量约束，而r+1恰好满足该约束
        return l;
    }

    // 计算<=target的元素个数是否>=k
    boolean check(int[][] matrix, int target, int k) {
        int n = matrix.length;
        int r = n - 1;
        int cnt = 0;
        // for循环中添加cnt<k的约束，能够更早结束循环
        for (int i = 0; i < n && cnt < k; i++) {
            // 搜索这一行<=target的最大位置
            int l = 0;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (matrix[i][mid] > target) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            cnt += (r + 1);
        }
        return cnt >= k;
    }
}
