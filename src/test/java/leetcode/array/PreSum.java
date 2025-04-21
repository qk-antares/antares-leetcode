package leetcode.array;

public class PreSum {
    /*
     * 2145. 统计隐藏数组数目
     * 
     * 统计数组的前缀和（delta）的最大值和最小值，注意初始化为0
     * 还有一个注意点是delta在累积的过程中可能会溢出，所以要么使用long，要么在循环中判断是否已经超出范围了
     */
    public int numberOfArrays(int[] differences, int lower, int upper) {
        int minDelta = 0;
        int maxDelta = 0;
        int delta = 0;
        for (int diff : differences) {
            delta += diff;
            minDelta = Math.min(minDelta, delta);
            maxDelta = Math.max(maxDelta, delta);
            if (maxDelta - minDelta > upper - lower)
                return 0;
        }

        return Math.max(upper - lower - maxDelta + minDelta + 1, 0);
    }
}
