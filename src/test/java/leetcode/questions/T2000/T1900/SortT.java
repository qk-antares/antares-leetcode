package leetcode.questions.T2000.T1900;

import java.util.PriorityQueue;

public class SortT {
    /*
     * 1845. 座位预约管理系统
     */
    class SeatManager {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        public SeatManager(int n) {
            for (int i = 1; i <= n; i++) {
                pq.add(i);
            }
        }
        
        public int reserve() {
            int seatNumber = pq.poll();
            return seatNumber;
        }
        
        public void unreserve(int seatNumber) {
            pq.add(seatNumber);
        }
    }
}
