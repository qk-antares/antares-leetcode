package leetcode.questions.T3000.T2600.medium;

import org.junit.jupiter.api.Test;

public class ArrayT {
    /*
     * 2502. 设计内存分配器
     * 使用一个int型的数组mem来保存内存的占用情况
     * mem[i] = mID表示内存的第i位被mID占用
     * 
     * 关键在于allocate的实现，要在mem数组中找到第一个连续size为0的位置
     * 使用双指针对mem进行一次遍历
     */
    class Allocator {
        int n;
        int[] memory;

        public Allocator(int n) {
            this.n = n;
            this.memory = new int[n];
        }
        
        /*
         * 我的实现
         */
        public int allocate0(int size, int mID) {
            int freeLen;
            for (int i = 0; i < n; i++) {
                freeLen = 0;
                while (i < n && memory[i] == 0 && freeLen < size) {
                    freeLen++;
                    i++;
                }
                if(freeLen == size) {
                    for (int j = i-freeLen; j < i; j++) {
                        memory[j] = mID;
                    }
                    return i-freeLen;
                }
            }
            return -1;
        }

        /*
         * 答案的实现
         */
        public int allocate(int size, int mID) {
            int start = 0, end = 0;
            for (int i = 0; i < n; i++) {
                end++;
                if(memory[i] == 0){
                    if(end - start == size) {
                        break;
                    }
                } else {
                    start = end;
                }
            }

            if(end-start == size) {
                for (int i = start; i < end; i++) {
                    memory[i] = mID;
                }
                return start;
            }
            return -1;
        }
        
        public int freeMemory(int mID) {
            int cnt = 0;
            for (int i = 0; i < n; i++) {
                if(memory[i] == mID) {
                    memory[i] = 0;
                    cnt++;
                }
            }
            return cnt;
        }
    }

    @Test
    void test(){
    }
}
