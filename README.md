## 1. Java API

### 1.1 Arrays

#### 1.1.1 `Arrays.fill`与`Arrays.setAll`

```java
int[] arr = new int[n];
Arrays.fill(arr, 0);

Object[] objs = new Object[n];
//这个地方不能使用Arrays.fill，因为会让数组中每个元素引用同一个对象
Arrays.setAll(objs, i -> new Object());
```

#### 1.1.2 `Arrays.stream()`

```java
//快速获取数组中的最大值
int max = Arrays.stream(nums).max().getAsInt();
```

### 1.2 Collections

#### 1.2.1 `Collections.sort`

```java
List<Integer> list = new ArrayList<>();
//用来对List进行排序
Collections.sort(list);
```

## 2. 数学知识

### 2.1 最大公约数与最小公倍数
```java
//最大公因数的求法，依据定理：gcd(a,b)=gcd(b%a, a)=gcd(b,a mod b)
int gcd(int a, int b) {
    while(a != 0) {
        int tmp = a;
        a = b % a;
        b = tmp;
    }
    return b;
}

int lcm(int a, int b) {
    return a / gcd(a, b) * b;
}
```

### 2.2 极坐标与三角函数

`atan2`函数可以用来计算极坐标的角度，它的几何含义是`与原点的连线`和`正x轴`之间的夹角。值域是`[-π, π]`，当`y>0`时，`atan2(y, x)`是正值，反之是负值。关联题目：`[1610. 可见点的最大数目]`