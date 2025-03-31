package leetcode.questions.T1000.T800.hard;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

public class HeapT {
    /*
     * 719. 找出第 K 小的数对距离（错解）
     * 
     * 测试用例中有的K很大，导致直接使用PriorityQueue内存会超
     */
        public int smallestDistancePair(int[] nums, int k) {
        int len = nums.length;
        PriorityQueue<Integer> q = new PriorityQueue<>(new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2){
                return o2-o1;
            }
        });

        int cnt = 0;
        for(int i = 0; i < len; i++){
            for(int j = 1; j < len; j++) {
                int pending = Math.abs(nums[i]-nums[j]);
                if(cnt < k) {
                    q.offer(pending);
                    cnt++;
                } else {
                    if(pending < q.peek()){
                        q.poll();
                        q.offer(pending);
                    }
                }
            }
        }
        return q.peek();
    }

    @Test
    public void test() {
        int[] nums = {1,6,1};
        int k = 3;
        System.out.println(smallestDistancePair(nums, k));
    }
}


