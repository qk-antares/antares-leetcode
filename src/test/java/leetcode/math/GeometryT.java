package leetcode.math;

/*
 * 有关（平面/立体）几何的题
 */
public class GeometryT {
    /*
     * 812. 最大三角形面积
     * 
     * 叉积的概念：
     * 设向量a=(x1,y1),b=(x2,y2)，则a×b=x1*y2-x2*y1
     * a×b的绝对值等于以a,b为两边的平行四边形的面积
     * 因此以a,b为两边的三角形面积=|a×b|/2
     * 
     * 三重循环
     */
    public double largestTriangleArea(int[][] points) {
        int n = points.length;
        double ans = 0.0;
        // 第一个点
        for (int i = 0; i < n - 2; i++) {
            int x0 = points[i][0];
            int y0 = points[i][1];
            // 第二个点
            for (int j = i + 1; j < n - 1; j++) {
                double x1 = points[j][0] - x0;
                double y1 = points[j][1] - y0;
                // 第三个点
                for (int k = j + 1; k < n; k++) {
                    double x2 = points[k][0] - x0;
                    double y2 = points[k][1] - y0;
                    ans = Math.max(ans, Math.abs(x1 * y2 - x2 * y1));
                }
            }
        }
        return ans / 2.0;
    }

}
