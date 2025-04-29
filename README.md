## 1. Java API

### 1.1 Arrays

```java
int[] arr = new int[n];
Arrays.fill(arr, 0);

Object[] objs = new Object[n];
//这个地方不能使用Arrays.fill，因为会让数组中每个元素引用同一个对象
Arrays.setAll(objs, i -> new Object());
```