package leetcode.questions.T1000.T300;

import java.util.PriorityQueue;

public class HeapT {
    /*
     * TODO 295. 数据流的中位数 [Hard]
     * 
     * 维护两个PriorityQueue l和r，l维护比中位数小的，r维护比中位数大的
     * 要维护l.size() = r.size() (+1)
     * l是一个大根堆，即根元素最大，r是一个小根堆
     * 当插入元素时：
     * l.size() = r.size():
     * num > r.peek(): 取出r的根并加入l，将num加入r
     * num <= r.peek(): 将num加入l
     * 
     * l.size() = r.size()+1
     * num < l.peek(): 取出l的根加入r，将num加入l
     * num >= l.peek(): 将num加入r
     */
    class MedianFinder {
        PriorityQueue<Integer> l;
        PriorityQueue<Integer> r;

        public MedianFinder() {
            l = new PriorityQueue<>((o1, o2) -> o2 - o1);
            r = new PriorityQueue<>();
        }

        public void addNum(int num) {
            if (l.size() == r.size()) {
                if (l.isEmpty() || num <= r.peek()) {
                    l.offer(num);
                } else {
                    l.offer(r.poll());
                    r.offer(num);
                }
            } else {
                if (num < l.peek()) {
                    r.offer(l.poll());
                    l.offer(num);
                } else {
                    r.offer(num);
                }
            }
        }

        public double findMedian() {
            if (l.size() == r.size()) {
                return (double) (l.peek() + r.peek()) / 2.0;
            } else {
                return (double) l.peek();
            }
        }
    }

}
