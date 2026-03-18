package leetcode.binarysearch;

/**
 * 二分答案
 */
public class BSAnsT {
    /**
     * 3296. 移山所需的最少秒数 [Medium]
     * 
     * w[i]*(1+x)*x/2 <= mid
     * x(x+1) <= 2*mid/w[i]
     * 求根公式[-b±√(b^2-4ac)]/2a
     * 负根无意义
     * x <= [-1+√(1+8*mid/w[i])]/2
     * x是上述值向下取整
     */
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        // l和r分别为下界和上界
        long l = 0, r = 0;
        for (long i = 1; i <= mountainHeight; i++) {
            r += workerTimes[0] * i;
        }

        while (l <= r) {
            // mid是时间
            long mid = (l + r) / 2;
            // tot是在mid时间内的高度
            int tot = 0;

            // 计算每个worker在mid时间内的高度
            for (int w : workerTimes) {
                long t = (long) ((-1.0 + Math.sqrt(1 + mid / w * 8)) / 2);
                tot += t;
            }

            if (tot < mountainHeight) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return l;
    }
}
