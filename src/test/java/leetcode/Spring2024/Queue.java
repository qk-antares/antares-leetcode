package leetcode.Spring2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Queue {
    /**
     * 387. 字符串中的第一个唯一字符
     * 使用一个HashMap保存字符及其首次出现index（如果多次出现，不可能是答案，设为-1）
     * 使用queue保存当前第一次出现的字符
     */
    public int firstUniqChar(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        LinkedList<Pair> q = new LinkedList<Pair>();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if(!map.containsKey(ch)) {
                map.put(ch, i);
                q.add(new Pair(ch, i));
            } else {
                map.put(ch, -1);
                //从队首开始检查
                while (!q.isEmpty() && map.get(q.peek().ch) == -1) {
                    q.poll();
                }
            }
        }
        return q.isEmpty() ? -1 : q.peek().index;
    }
    class Pair {
        char ch;
        int index;
        public Pair(char ch, int index){
            this.ch = ch;
            this.index = index;
        }
    }

    /**
     * 2071. 你可以安排的最多任务数目
     * 二分，这里的边界。mid=(l+r+1)>>2 l = mid r = mid-1
     */
    int[] tasks;
    int[] workers;
    int pills;
    int strength;
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        Arrays.sort(tasks); Arrays.sort(workers);
        this.tasks = tasks; this.workers = workers; this.pills = pills; this.strength = strength;
        
        int l = 0, r = Math.min(tasks.length, workers.length);
        while (l < r) {
            int mid = (r+l+1) >> 1;
            if(check(mid)) {
                l = mid;
            } else {
                r = mid-1;
            }
        }
        return l;
    }
    //检查能够完成k个任务
    boolean check(int k) {
        int p = this.pills;
        LinkedList<Integer> q = new LinkedList<Integer>();
        int idx = workers.length - 1;
        for (int i = k-1; i >= 0; i--) {
            int t = tasks[i];
            //还有工人可用，并且这个工人给了药丸之后能解决问题（用q来收集那些至少还能解决问题的worker）
            while (idx >= 0 && workers[idx] + strength >= t) {
                q.offer(workers[idx]);
                idx--;
            }

            //没有可用的工人
            if (q.isEmpty()) {
                return false;
            }
            //不用药丸就行
            if (q.peek() >= t) {
                q.poll();
            } else {
                //需要使用药丸，则将最小的使用药丸的worker弹出（位于队尾）
                if(p > 0) {
                    q.pollLast();
                    p--;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 933. 最近的请求次数
     */
    class RecentCounter {
        LinkedList<Integer> q;

        public RecentCounter() {
            q = new LinkedList<>();
        }
        
        public int ping(int t) {
            q.offer(t);
            int border = t - 3000;
            while (q.peek() < border) {
                q.poll();
            }
            return q.size();
        }
    }
}
