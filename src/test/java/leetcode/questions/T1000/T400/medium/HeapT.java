package leetcode.questions.T1000.T400.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class HeapT {
    /*
     * TODO 373. 查找和最小的 K 对数字
     * 
     * 直接使用暴力解法会超时
     * 
     * 实际上不需要把所有的数对添加到堆中。
     * 首先，很显然(nums1[0], nums2[0])是最小的数对，相对应的索引为(0,0)
     * 假设目前已经选了n个小数对，它们的索引为(a1,b1), (a2,b2), ..., (an,bn)
     * 那么下一个最小的数对的索引只可能从以下索引中产生：(a1+1,b1), (a1,b1+1), ..., (an+1,bn), (an,bn+1)
     * 
     * 利用这种性质，初始时堆中(保存索引对)只有(0,0)
     * 每取出一个，就根据当前已经取出的索引对，向堆中添加新的索引对，直至取出k个
     * 
     * 从另一方面，索引可能被重复添加，为了防止这种情况，初始时将(0...m-1,0)全部添加到堆中
     * 然后每取出一个(idx1,idx2)，我们只用向堆中添加(idx1, idx2+1)
     * 详解见：https://leetcode.cn/problems/find-k-pairs-with-smallest-sums/solutions/1210221/bu-chong-guan-fang-ti-jie-you-xian-dui-l-htf8/
     */
    public List<List<Integer>> kSmallestPairs0(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        PriorityQueue<List<Integer>> q = new PriorityQueue<>(new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> l1, List<Integer> l2) {
                return l1.get(0) + l1.get(1) - l2.get(0) - l2.get(1);
            }
        });

        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                q.add(new ArrayList<>(List.of(nums1[i], nums2[j])));
            }
        }

        while (k-- > 0 && !q.isEmpty()) {
            ans.add(q.poll());
        }

        return ans;
    }

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        //保存索引
        PriorityQueue<int[]> q = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return nums1[o1[0]] + nums2[o1[1]] - nums1[o2[0]] - nums2[o2[1]];
            }
        });
        for(int i = 0;i < Math.min(k, nums1.length); i++){
            q.offer(new int[]{i, 0});
        }

        while(k-->0 && !q.isEmpty()) {
            int[] idx = q.poll();
            ans.add(Arrays.asList(nums1[idx[0]], nums2[idx[1]]));
            if(idx[1]+1 < nums2.length) {
                q.offer(new int[]{idx[0], idx[1]+1});
            }
        }
        return ans;
    }
}
