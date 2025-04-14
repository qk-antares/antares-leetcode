package leetcode.questions.T2000.T1600;

public class ArrayT {
    /*
     * 1534. 统计好三元组
     * 
     * 最简单的方式是三重循环遍历，时间复杂度是O(n^3)
     * 
     * 为了进一步优化，考虑|A[i]-A[j]|<=a,|A[i]-A[k]|<=c这两个条件，可以得到A[i]的范围是
     * max(max(A[k]-c,A[j]-a),0)到min(min(A[k]+c,A[j]+a),mx)
     * 
     * 因此可以只枚举j和k，得到A[i]的范围，然后用一个s数组保存对应A[i]的个数（前缀和）
     * 注意s的构造必须是边遍历边构造，这样才能保证找到的答案i<j
     */
    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        int mx = 0;
        for (int num : arr) {
            if (num > mx)
                mx = num;
        }

        // arr的值域是[0,mx]，这意味其cnt数组的length是1+mx，其sum数组的length是2+mx
        // sum[0]=0, sum[1]=cnt[0], sum[2]=cnt[0]+cnt[1]
        // 这里的s必须是一遍遍历，一遍构造的，它确保了找到的答案i<j
        int[] s = new int[mx + 2];

        int ans = 0;
        for (int j = 0; j < arr.length - 1; j++) {
            for (int k = j + 1; k < arr.length; k++) {
                if (Math.abs(arr[j] - arr[k]) > b)
                    continue;

                // 计算出arr[i]的范围
                int l = Math.max(Math.max(arr[k] - c, arr[j] - a), 0);
                int r = Math.min(Math.min(arr[k] + c, arr[j] + a), mx);

                ans += Math.max(s[r + 1] - s[l], 0);
            }

            for (int k = arr[j] + 1; k < s.length; k++) {
                s[k]++;
            }
        }
        return ans;
    }
}
