package leetcode.questions.T4000.T3300;

public class DpT {
    /*
     * 3259. 超级饮料的最大强化能量 [Medium]
     * 
     * dpA[i]表示第i小时以A结尾的答案
     * dpB[i]同理
     * dpA[i] = Math.max(A[i]+dpA[i-1], A[i]+dpB[i-2])
     * dpB[i] = Math.max(B[i]+dpB[i-1], B[i]+dpA[i-2])
     * dpA[0] = A[0], dpB[0] = B[0]
     * dpA[1] = A[0] + A[1], dpB[1] = B[0] + B[1]
     * 只依赖前面固定的状态，还可以进一步压缩
     * 
     */

    public long maxEnergyBoost(int[] energyDrinkA, int[] energyDrinkB) {
        int len = energyDrinkA.length;
        if (len < 2) {
            return (long) Math.max(energyDrinkA[0], energyDrinkB[0]);
        }

        long[] dpA = new long[len];
        long[] dpB = new long[len];
        dpA[0] = energyDrinkA[0];
        dpB[0] = energyDrinkB[0];
        dpA[1] = energyDrinkA[0] + energyDrinkA[1];
        dpB[1] = energyDrinkB[0] + energyDrinkB[1];

        for (int i = 2; i < len; i++) {
            dpA[i] = Math.max(energyDrinkA[i] + dpA[i - 1], energyDrinkA[i] + dpB[i - 2]);
            dpB[i] = Math.max(energyDrinkB[i] + dpB[i - 1], energyDrinkB[i] + dpA[i - 2]);
        }

        return Math.max(Math.max(dpA[len - 1], dpA[len - 2]), Math.max(dpB[len - 1], dpB[len - 2]));
    }
}
