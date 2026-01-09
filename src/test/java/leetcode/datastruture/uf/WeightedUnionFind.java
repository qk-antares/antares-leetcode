package leetcode.datastruture.uf;

import java.util.Arrays;

public class WeightedUnionFind {
    int[] fa; // 代表元
    double[] mul; // x*mul[x]=x的代表元的值

    public WeightedUnionFind(int n) {
        fa = new int[n];
        for (int i = 0; i < n; i++)
            fa[i] = i;
        mul = new double[n];
        Arrays.fill(mul, 1.0);
    }

    // 往上寻找代表元的过程，将各个节点的fa直接设置为代表元，并更新mul
    int find(int x) {
        if (fa[x] != x) {
            int root = find(fa[x]);
            mul[x] *= mul[fa[x]]; // 更新x到其代表元的mul值
            fa[x] = root;
        }
        return fa[x];
    }

    // x/y=value即y*value=x
    void merge(int x, int y, double value) {
        int fx = find(x);
        int fy = find(y);
        // 已经在同一个连通分量里
        if (fx == fy)
            return;

        // fx ------ fy
        // / /
        // x ------- y
        // 已知 x*mul[x]=fx 和 y*mul[y]=fy
        // 合并 fx 和 fy，新的代表元是fx
        // 又已知y*value=x，即fy/mul[y]*value=fx/mul[x]
        // 所以 fy*mul[x]/mul[y]*value=fx
        // mul[fy]= mul[x]/mul[y]*value
        fa[fy] = fx;
        mul[fy] = mul[x] / mul[y] * value;
    }

    boolean check(int x, int y) {
        return find(x) == find(y);
    }
}
