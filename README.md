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
需要注意`Arrays.setAll`只能用于引用类型，对于基本数据类型，如果填充不是固定值，而是有规律的逻辑（比如填充为下标），可以使用包装类型，如：
```java
Integer[] arr = new Integer[n];
Arrays.setAll(arr, i -> i);
```

#### 1.1.2 `Arrays.copyOf`

```java
int[] arr = new int[n];
//将数组arr的前n个元素复制到新数组中
int[] newArr = Arrays.copyOf(arr, n);
```
非常类似的，可以使用`System.arraycopy`，实际上`Arrays.copyOf`内部也是调用了`System.arraycopy`。使用`System.arraycopy`可以更灵活地指定源数组和目标数组的起始位置。
```java
int[] arr = new int[n];
int[] newArr = new int[n];
System.arraycopy(arr, 0, newArr, 0, n);
```

#### 1.1.3 `Arrays.asList`

```java
//将若干个元素转换成List，注意这里的参数不是数组
List<Integer> list = Arrays.asList(num1, num2, ...);
//注意：asList返回的List是固定长度的，不能添加或删除元素
```

### 1.2 Collections

#### 1.2.1 `Collections.sort`

```java
List<Integer> list = new ArrayList<>();
//用来对List进行排序
Collections.sort(list);
```

### 1.3 String

#### 1.3.1 `compareTo`

```java
//比较两个字符串的字典序，不能直接使用比较符(==,<,>)，应该使用compareTo，当s1字典序小于s2时，返回负数
s1.compareTo(s2);
```

#### 1.3.2 `replace`

```java
//将字符串中的某个字符或子串替换成另一个字符或子串，返回新字符串而不修改原字符串
res = s.replace("old", "new");
res = s.replace('a', 'b');
```

#### 1.3.3 `split()`

```java
//按照多个字符分隔字符串
String[] parts = str.split("[ .,!]+");
//按照一个或多个空格分割
String[] parts = str.split("\\s+");
//单纯按照一个空格分割
String[] parts = str.split(" ");
//按照特殊字符(|*)分割
String[] parts = str.split("\\|");
```

#### 1.3.4 `join()`

```java
//将字符串数组用空格连接成一个字符串，最后一个字符串后面不会有空格
String[] arr = {"Java", "is", "awesome"};
String result = String.join(" ", arr);
```

### 1.4 `stream()`

Java中只要是实现了`Collection`接口的类，都可以使用`stream()`。另外，一些特定类（如`Array`、`Map`）虽然不是`Collection`，也可以通过辅助方法转换成`Stream`。

`Collection`接口的实现类：

| 类型          | 示例类                                      |
| ------------- | ------------------------------------------- |
| `List`        | `ArrayList`, `LinkedList`, `Vector`         |
| `Set`         | `HashSet`, `LinkedHashSet`, `TreeSet`       |
| `Queue/Deque` | `ArrayDeque`, `LinkedList`, `PriorityQueue` |

`Array`（数组）：
```java
int[] nums = new int[10];
Arrays.stream(nums);
```

`Map`的`keySet()`、`entrySet()`方法都返回一个`Set`，而`values()`返回一个`Collection`（因为`Map`的值可以重复），可以使用`stream()`方法：
```java
Map<String, Integer> map = Map.of("a", 1, "b", 2);

map.keySet().stream();         // Stream<String>
map.values().stream();         // Stream<Integer>
map.entrySet().stream();       // Stream<Map.Entry<String, Integer>>
```

#### 1.4.1 `max().getAsInt()`

```java
//快速获取数组中的最大值
int max = Arrays.stream(nums).max().getAsInt();
```

#### 1.4.2 `mapToInt(Integer::intValue).toArray()`

```java
//将List转成Array
List<Integer> list = new ArrayList<>();
int[] arr = list.stream().mapToInt(Integer::intValue).toArray();
```

### 1.5 `Map`

#### 1.5.1 `merge`

```java
//如果key不存在，则将key-value对添加到map中；如果key存在，则使用合并函数合并旧值和新值
map.merge(key, value, (oldValue, newValue) -> oldValue + newValue);
//一个常用的写法是计数器
map.merge(key, 1, Integer::sum);
//等价于
map.put(key, map.getOrDefault(key, 0) + 1);
```

#### 1.5.2 `TreeMap`

```java
TreeMap<Integer, String> map = new TreeMap<>();

// put 添加元素
map.put(3, "three");
map.put(1, "one");
map.put(2, "two");

// firstKey/lastKey 获取最小/最大key
int minKey = map.firstKey(); // 1
int maxKey = map.lastKey();  // 3

// ceilingKey/floorKey 查找大于等于/小于等于给定key的最小/最大key
int ceil = map.ceilingKey(2); // 2
int floor = map.floorKey(2);  // 2

// higherKey/lowerKey 查找大于/小于给定key的最小/最大key
int higher = map.higherKey(2); // 3
int lower = map.lowerKey(2);   // 1

// 返回 [fromKey, toKey) 范围的视图（左闭右开）
map.subMap(fromKey, toKey);
// 可指定边界是否包含
map.subMap(fromKey, boolean fromInclusive, toKey, boolean toInclusive);
// 返回所有小于 toKey 的键值对
map.headMap(toKey);
// 返回所有大于等于 fromKey 的键值对
map.tailMap(fromKey);
```

### 1.6 `Integer`

```java
//计算整数的二进制表示中1的个数
int count = Integer.bitCount(num);

//计算前导0的个数
int leadingZeros = Integer.numberOfLeadingZeros(num);

//获取整数的最高位1所构成的整数
//例如：num=12(二进制1100)，则highestBit=8(二进制1000)
int highestBit = Integer.highestOneBit(num);

//获取整数的最低位1所构成的整数
//例如：num=12(二进制1100)，则lowestBit=4(二进制0100)
int lowestBit = Integer.lowestOneBit(num);
```

### 1.6 运算符优先级

| 优先级 | 运算符                                                           | 结合性     |
|--------|-----------------------------------------------------------------|------------|
| 1      | `()` `[]` `.`                                                   | 从左到右   |
| 2      | `!` `~` `++` `--` `+` `-`                                       | 从右到左   |
| 3      | `*` `/` `%`                                                     | 从左到右   |
| 4      | `+` `-`                                                         | 从左到右   |
| 5      | `<<` `>>` `>>>`                                                 | 从左到右   |
| 6      | `<` `<=` `>` `>=` `instanceof`                                  | 从左到右   |
| 7      | `==` `!=`                                                       | 从左到右   |
| 8      | `&`                                                             | 从左到右   |
| 9      | `^`                                                             | 从左到右   |
| 10     | `\|`                                                            | 从左到右   |
| 11     | `&&`                                                            | 从左到右   |
| 12     | `\|\|`                                                          | 从左到右   |
| 13     | `?:`                                                            | 从右到左   |
| 14     | `=` `+=` `-=` `*=` `/=` `%=` `&=` `\|=` `^=` `<<=` `>>=` `>>>=` | 从右到左   |
| 15     | `,`                                                             | 从左到右   |

> `.`是表示成员访问运算符；`,`用于在一条语句中串联多个表达式（例如for循环）

根据上表可以发现，位运算符（`<<` `>>` `&` `^` `|`）以及三目运算符（`?:`）的优先级都比较低（低于`+` `-`），因此在使用的时候要特别注意带括号

### 1.7 溢出

```java
int a, b;
int MOD;
//这种避免溢出的方式是无效的，存在一个运算优先级的问题，计算a*b时已经溢出了，再转成long是负数
long tmp = (long) (a*b);
//下面这种方法是正确的，它本质是先把a转成long类型，再进行乘法时b会被提升为long类型
long tmp = (long) a * b;
int ans = (int) (tmp % MOD);
```

## 2. 数学知识

### 2.1 数论

#### 2.1.1 最大公约数与最小公倍数

```java
//最大公因数的求法，依据定理：gcd(a,b)=gcd(b%a, a)=gcd(b,a%b)
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

#### 2.1.2 中位数贪心

有一个数组`a={a0, a1, a2, ..., an}`，假设它们代表数轴上的一些点，每个点是一座房子，现要建一个快递站`x`，使得快递站到所有房子的距离之和最小，则快递站应该建在`a`的中位数的位置。

更具体地说，当`a`的元素个数为奇数时，`x`恰好在中位数的位置；而当`a`的元素个数为偶数时，`x`可以在`a[n/2]`和`a[(n+1)/2]`之间的任意位置。

该结论的证明如下：

假设`a`已经排序好了。首先我们选择`x`的位置在`a0`的左侧，那么将`x`向右侧移动`1`个单位，与所有`n+1`个房子的距离都靠近了`1`；我们再假设`x`就处于`a0`的位置，那么将`x`向右侧移动`1`个单位，与`a0`右侧`n`个房子的距离都靠近了`1`，而只与`a0`的距离远离了`1`，所以总的距离变化量是`1-n`。可以通过递推得出，当`x`位于中位数（或当元素个数为偶数时，两个中位数之间）时，`x`与所有房子的距离之和最小。

该结论与滑动窗口结合使用时，还有一些结论：

- 假设`a`的一个窗口`{a[l], ..., a[r]}`，它的快递站最小距离为`dis`，那么当窗口右边界扩展时(`r++`)，增加的距离为`a[r]-a[(l+r)/2]`；当窗口的左边界收缩时(`l++`)，减少的距离为`a[(l+r)/2]-a[l-1]`。注意上述`r++`和`l++`的时机，下面是`[2968. 执行操作使频率分数最大]`的示例代码：

```java
while (r < nums.length) {
    edit += nums[r] - nums[(l + r) / 2];

    while (edit > k) {
        edit -= nums[(l + r + 1) / 2] - nums[l];
        l++;
    }

    r++;
    ans = Math.max(ans, r - l);
}
```

### 2.2 几何

#### 2.2.1 极坐标与三角函数

`atan2`函数可以用来计算极坐标的角度，它的几何含义是`与原点的连线`和`正x轴`之间的夹角。值域是`[-π, π]`，当`y>0`时，`atan2(y, x)`是正值，反之是负值。关联题目：`[1610. 可见点的最大数目]`

### 2.3 组合数学

#### 2.3.1 排列组合
排列组合的公式如下：
- 排列：`A(n, m) = n! / (n - m)!`
- 组合：`C(n, m) = n! / m! / (n - m)!`

#### 2.3.2 模运算
许多组合数学题目需要使用模运算来避免数值溢出。常用的模数是`10^9+7`，但需要特别注意的是：

$$
\frac{m}{n} \% p \neq \frac{m \% p}{n \% p}
$$

关于这种除法运算求模，通常可以使用如下定理求解：当 $p$ 是质数， $a$ 是 $b$ 的倍数，且 $b$ 不是 $p$ 的倍数时：

$$
\frac{a}{b} \% p = a \cdot b^{p-2} \% p
$$

这里的 $b^{p-2}$ 实质上就是 $b$ 关于模 $p$ 的逆元。模的逆元是指对于一个数 $a$，存在一个数 $b$ 使得：

$$
a \cdot b \equiv 1 \mod p
$$

当 $p$ 是一个质数时，可以依据费马小定理来计算逆元，费马小定理指出，对于一个质数 $p$ 和一个整数 $a$，如果 $a$ 不被 $p$ 整除，则存在一个整数 $b$ 使得：

$$
b^{p} \equiv b \mod p
$$

$$
b^{p-1} \equiv 1 \mod p
$$

因此， $b^{p-2}$ 就是 $b$ 关于模 $p$ 的逆元。

#### 2.3.3 组合数的计算
组合数的计算可以通过预处理阶乘和逆元来实现：

```java
int MOD = 1_000_000_007;
long[] fact = new int[n+1];
long[] invFact = new int[n+1];

void init(int n) {
    fact[0] = 1;
    for (int i = 1; i <= n; i++) {
        fact[i] = fact[i - 1] * i % MOD;
    }
    
    invFact[n] = pow(fact[n], MOD-2);
    for (int i = n - 1; i >= 0; i--) {
        invFact[i] = invFact[i + 1] * (i + 1) % MOD;
    }
}

long pow(long base, int exp) {
    long ans = 1;
    while (exp > 0) {
        if ((exp & 1) == 1) ans = ans * base % MOD;
        base = base * base % MOD;
        exp >>= 1;
    }
    return result;
}
```