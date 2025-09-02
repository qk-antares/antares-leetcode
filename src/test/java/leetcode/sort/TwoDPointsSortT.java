package leetcode.sort;

import java.util.Arrays;

/*
 * 二维平面点的排序
 */
public class TwoDPointsSortT {
    /*
     * 3025. 人员站位的方案数 I [Medium]
     * 
     * 首先对points按照x排序
     * 那么枚举i作为左上角的A点，枚举的j>i点B自动满足在右边
     * 要满足B在A的下边，则跳过y2>y1的点
     * （j）遍历过程中维护遇到的maxY，y2还需要<maxY
     * 
     * 对于同一条竖线上的特殊情况，为了避免少统计，就按照y从大到小排序
     */
    public int numberOfPairs(int[][] points) {
        Arrays.sort(points, (p1, p2) -> p1[0] - p2[0] != 0 ? p1[0] - p2[0] : p2[1] - p1[1]);
        int ans = 0;
        for (int i = 0; i < points.length; i++) {
            int y1 = points[i][1];
            int maxY = Integer.MIN_VALUE;
            for (int j = i + 1; j < points.length; j++) {
                if (points[j][1] <= y1 && points[j][1] > maxY) {
                    ans++;
                    maxY = Math.max(maxY, points[j][1]);
                }
            }
        }
        return ans;
    }

    /*
     * 3027. 人员站位的方案数 II    [Hard]
     * 
     * 和上面是同一道题，只是数据范围增大
     */
}
