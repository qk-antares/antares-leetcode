package leetcode.questions.T2000.T1600;

public class BinarySearchT {
    /*
     * 1539. 第 k 个缺失的正整数 [Easy]
     * 
     * arr[0..i]的缺失数量为arr[i]-i-1
     * 通过二分查找，找到缺失数量<k的最大下标arr[target]，则第k个缺失值为arr[target]+
     * 或者，找到确实数量>=k的最小下标arr[target]，则第k个缺失值为arr[target]-1
     * 
     */
    public int findKthPositive(int[] arr, int k) {
        int n = arr.length, l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (arr[mid] - mid - 1 >= k) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        // 缺失的在前面
        if (r == -1) {
            return k;
        } else {
            return arr[r] + (k + r + 1 - arr[r]);
        }
        // 上面相当于return k+r+1或者return k+l
    }
}
