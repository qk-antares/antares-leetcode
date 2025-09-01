package leetcode.datastruture.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

public class BasicT {
    /*
     * 3275. 第 K 近障碍物查询 [Medium]
     * 
     * 大小为k的大根堆
     */
    public int[] resultsArray(int[][] queries, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int n = queries.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int dis = Math.abs(queries[i][0]) + Math.abs(queries[i][1]);
            if (heap.size() < k)
                heap.offer(dis);
            else if (dis < heap.peek()) {
                heap.poll();
                heap.offer(dis);
            }
            if (heap.size() < k)
                ans[i] = -1;
            else
                ans[i] = heap.peek();
        }
        return ans;
    }

    /*
     * 1834. 单线程 CPU [Medium]
     */
    public int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        // 按照任务的起始时间排序，记录排序后的index
        Integer[] idx = new Integer[n];
        Arrays.setAll(idx, i -> i);
        Arrays.sort(idx, (i0, i1) -> tasks[i0][0] - tasks[i1][0]);

        // 任务队列，按照执行时间/下标排序（小根堆）
        PriorityQueue<int[]> heap = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0] != 0 ? o1[0] - o2[0] : o1[1] - o2[1]);

        // 当前的时间戳
        int timestamp = tasks[idx[0]][0];

        // 待加入队列的索引i
        int i = 0;
        // j是待加入ans的索引
        int j = 0;
        int[] ans = new int[n];
        while (i < n) {
            // 将<=当前时间戳的任务加入队列
            while (i < n && tasks[idx[i]][0] <= timestamp) {
                heap.offer(new int[] { tasks[idx[i]][1], idx[i] });
                i++;
            }
            // 从队列中取出任务
            int[] task = heap.poll();
            if (task != null) {
                ans[j++] = task[1];
                timestamp += task[0];
            } else {
                timestamp = tasks[idx[i]][0];
            }
        }

        // 处理剩余任务
        while (!heap.isEmpty()) {
            ans[j++] = heap.poll()[1];
        }

        return ans;
    }

    /*
     * 1792. 最大平均通过率 [Medium]
     * 
     * 用堆，按照班级大小将index添加到堆中，小的在堆顶（或者直接排序）
     * 逐个取出堆顶元素，看当前班级是否满了，以及是否有多余的student
     * 统计通过率之和
     * 这里的问题在于没有考虑到添加了学生后，班级本身的学生总数也会增加
     * 
     * 将每个班级看作一个节点，记作(a,b)，则添加一个学生，该节点增加的通过率gain为：
     * (a+1)/(b+1)-a/b=[(a+1)b-a(b+1)]/b(b+1)=(b-a)/b(b+1)
     * 首先将所有的节点添加到一个堆中（按gain的大根堆），然后不断地从堆中取出节点，修改节点并重新放回到堆中，直至extraStudents减为0
     * 最后逐个取出堆中元素，返回答案
     * 
     * 此处的class Node可以用record简化，record自带全参构造函数
     */
    class Node {
        int a;
        int b;
        double gain;

        public Node(int a, int b, double gain) {
            this.a = a;
            this.b = b;
            this.gain = gain;
        }
    }

    public double maxAverageRatio0(int[][] classes, int extraStudents) {
        Arrays.sort(classes, (o1, o2) -> o1[1] - o2[1]);
        double sum = 0;
        int i = 0, n = classes.length;
        while (extraStudents > 0 && i < n) {
            int delta = Math.min(extraStudents, classes[i][1] - classes[i][0]);
            sum += (double) (classes[i][0] + delta) / (classes[i][1] + delta);
            extraStudents -= delta;
            i++;
        }

        while (i < n) {
            sum += (double) classes[i][0] / classes[i][1];
            i++;
        }

        return sum / n;

    }

    public double maxAverageRatio(int[][] classes, int extraStudents) {
        PriorityQueue<Node> q = new PriorityQueue<>((n1, n2) -> Double.compare(n2.gain, n1.gain));

        for (int[] c : classes) {
            int a = c[0];
            int b = c[1];
            q.add(new Node(a, b, 1.0 * (b - a) / (1L * b * (b + 1))));
        }

        while (extraStudents > 0) {
            Node n = q.poll();
            int a = n.a + 1;
            int b = n.b + 1;
            q.add(new Node(a, b, 1.0 * (b - a) / (1L * b * (b + 1))));
            extraStudents--;
        }

        double sum = 0;
        while (!q.isEmpty()) {
            Node n = q.poll();
            sum += 1.0 * n.a / n.b;
        }

        return sum / classes.length;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 2099. 找到和最大的长度为 K 的子序列 [Easy]
     * 
     * 小根堆，如果当前元素大于堆顶，则加入堆中，堆中的元素还要保存其下标
     */
    public int[] maxSubsequence(int[] nums, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (heap.size() < k)
                heap.offer(new int[] { nums[i], i });
            else if (nums[i] > heap.peek()[0]) {
                heap.poll();
                heap.offer(new int[] { nums[i], i });
            }
        }

        int[] idx = new int[k];
        for (int i = 0; i < k; i++) {
            idx[i] = heap.poll()[1];
        }
        Arrays.sort(idx);

        for (int i = 0; i < k; i++) {
            idx[i] = nums[idx[i]];
        }
        return idx;
    }
}