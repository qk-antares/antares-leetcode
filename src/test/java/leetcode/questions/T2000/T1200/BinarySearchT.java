package leetcode.questions.T2000.T1200;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class BinarySearchT {
    /*
     * 1146. 快照数组 [Medium]
     * 
     * 使用TreeMap的解法虽然能做出来，但并不是这道题想考察的点，实际应该使用二分查找
     * 
     * 此外这道题和 981. 基于时间的键值存储 的解体思路和代码框架都差不多
     * 区别在于那题必须自定义一个Pair类，因为数对的类型不同。而这题直接用int[]就可以了
     */
    class SnapshotArray0 {
        int snap_id;
        TreeMap<Integer, Integer>[] mapList;
        int len;

        @SuppressWarnings("unchecked")
        public SnapshotArray0(int length) {
            this.snap_id = 0;
            this.len = length;
            this.mapList = new TreeMap[this.len];
            for (int i = 0; i < this.len; i++) {
                this.mapList[i] = new TreeMap<Integer, Integer>();
            }
        }

        public void set(int index, int val) {
            mapList[index].put(this.snap_id, val);
        }

        public int snap() {
            this.snap_id += 1;
            return this.snap_id - 1;
        }

        public int get(int index, int snap_id) {
            Integer key = mapList[index].floorKey(snap_id);
            return key == null ? 0 : mapList[index].get(key);
        }
    }

    class SnapshotArray {
        int snap_id;
        List<int[]>[] list;
        int len;

        @SuppressWarnings("unchecked")
        public SnapshotArray(int length) {
            this.snap_id = 0;
            this.len = length;
            this.list = new List[this.len];
            for (int i = 0; i < this.len; i++) {
                this.list[i] = new ArrayList<int[]>();
            }
        }

        public void set(int index, int val) {
            this.list[index].add(new int[] { this.snap_id, val });
        }

        public int snap() {
            this.snap_id += 1;
            return this.snap_id - 1;
        }

        public int get(int index, int snap_id) {
            List<int[]> slot = this.list[index];
            int l = 0, r = slot.size() - 1;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (slot.get(mid)[0] > snap_id) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            return r == -1 ? 0 : slot.get(r)[1];
        }
    }
}
