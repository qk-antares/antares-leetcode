package leetcode.datastruture.heap;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*
 * 懒删除堆
 * 
 * 这种堆支持删除某个元素的操作remove(T e)
 * 用一个HashMap来保存每个元素的删除次数
 * 当执行peek()或poll()时，检查堆顶元素是否在HashMap中
 * 如果在，说明该元素被删除过，弹出堆顶元素
 * 执行上述操作，直至堆顶元素在HashMap中的cnt=0
 */
public class LazyHeap<T> {
    public PriorityQueue<T> q;
    public Map<T, Integer> removeCnt;
    public int size;

    public LazyHeap(Comparator<? super T> comparator) {
        q = new PriorityQueue<>(comparator);
        removeCnt = new HashMap<>();
        size = 0;
    }

    // 从堆中删除一个元素
    public void remove(T e) {
        removeCnt.merge(e, 1, Integer::sum);
        size--;
    }

    // 正式执行删除操作
    private void applyRemove() {
        while (removeCnt.getOrDefault(q.peek(), 0) > 0) {
            removeCnt.merge(q.poll(), -1, Integer::sum);
        }
    }

    public T peek() {
        applyRemove();
        return q.peek();
    }

    public T pop() {
        applyRemove();
        size--;
        return q.poll();
    }

    public void push(T e) {
        q.offer(e);
        size++;
    }

    /*
     * 懒删除堆通常和对顶堆结合使用，而对顶堆为了维持平衡
     * 需要频繁地执行左堆倒右堆的操作
     * 
     * 为了减少applyRemove的调用次数，可以再实现一个pushPop方法
     * 该方法先将元素e加入堆中，然后弹出堆顶元素并返回
     */
    public T pushPop(T e) {
        if (q.size() > 0 && q.comparator().compare(e, q.peek()) > 0) {
            q.offer(e);
            return q.poll();
        }
        return e;
    }
}

class LazyHeapWithSum {
    PriorityQueue<Integer> q;
    Map<Integer, Integer> removeCnt;
    int size = 0;
    long sum = 0;

    public LazyHeapWithSum(Comparator<Integer> comparator) {
        q = new PriorityQueue<>(comparator);
        removeCnt = new HashMap<>();
    }

    public void remove(Integer e) {
        removeCnt.merge(e, 1, Integer::sum);
        size--;
        sum -= e;
    }

    //真正执行删除
    public void applyRemove() {
        while(removeCnt.getOrDefault(q.peek(), 0) > 0) {
            removeCnt.merge(q.poll(), -1, Integer::sum);
        }
    }

    public Integer peek() {
        applyRemove();
        return q.peek();
    }

    public Integer pop() {
        applyRemove();
        size--;
        int ret = q.poll();
        sum -= ret;
        return ret;
    }

    public void push(Integer e) {
        size++;
        sum += e;
        q.offer(e);
    }

    public Integer pushPop(Integer e) {
        if(size > 0 && q.comparator().compare(e, peek()) > 0) {
            q.offer(e);
            int ret = q.poll();
            sum += e-ret;
            return ret;
        }
        return e;
    }
}

class IntegerLazyHeap {
    PriorityQueue<Integer> q;
    Map<Integer, Integer> removeCnt;
    int size;

    public IntegerLazyHeap(Comparator<Integer> comparator) {
        q = new PriorityQueue<>(comparator);
        removeCnt = new HashMap<>();
        size = 0;
    }

    public void remove(Integer e) {
        removeCnt.merge(e, 1, Integer::sum);
        size--;
    }

    public void applyRemove() {
        while(removeCnt.getOrDefault(q.peek(), 0) > 0) {
            removeCnt.merge(q.poll(), -1, Integer::sum);
        }
    }

    public Integer peek() {
        applyRemove();
        return q.isEmpty() ? -1 : q.peek();
    }

    public void push(Integer e) {
        applyRemove();
        q.offer(e);
        size++;
    }

    public Integer pop() {
        applyRemove();
        size--;
        return q.poll();
    }
}
