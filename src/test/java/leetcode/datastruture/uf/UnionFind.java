package leetcode.datastruture.uf;

class UnionFind {
    int[] fa;   //代表元
    int cc; //连通块个数

    //n是一共有多少元素
    public UnionFind(int n) {
        fa = new int[n];
        cc = n;
        for(int i = 0; i < n; i++) {
            fa[i] = i;
        }
    }

    //找到x的代表元
    int find(int x) {
        if(fa[x] != x) return find(fa[x]);
        return fa[x];
    }

    //将x和y合并到同一个连通分量
    void merge(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        //已经在同一个连通分量里
        if(fx == fy) return; 

        fa[fx] = fy;
        cc--;
    }

    boolean check(int x, int y) {
        return find(x) == find(y);
    }
}

class UnionFindDir {
    int[] fa;

    public UnionFindDir(int n) {
        fa = new int[n];
        for(int i = 0; i < n; i++) fa[i] = i;
    }

    int find(int x) {
        if(fa[x] != x) return find(fa[x]);
        return x;
    }

    void merge(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if(fx > fy) fa[fx] = fy;
        else fa[fy] = fx;
    }
}
