# 列表 
### LinkedList 
双向链表,随机访问复杂度为O(n)

### ArrayList 
动态数组,插入、删除节点复杂度为O(n)

## 散列表  
如果散列表过满,就需要**再散列**, 将元素插入新表中,并丢弃原来的表.装填因子决定何时对散列表进行再散列,一般为0.75. 
### HashSet 
遍历时无顺序 

## 树集 
TreeSet相比HashSet,遍历时有顺序(红黑树).添加、删除、 查找元素的复杂度为O(logn) 
可以在构造函数中提供实现了Comparable接口的类,对元素进行排序

## 队列 
### 普通队列
### 双端队列 
### 优先级队列 
用堆实现  

## 映射表 
HashMap、TreeMap 
映射表有三个视图,分别是键集、值集合、键值对集. 

    ```
    Set<K> keySet()
    Collection<K> values()
    Set<Map.Entry<K, V>> entrySet()
    ```

## 专用集与映射表类  
### 弱散列映射表  
WeakHashMap,当对键的唯一引用来自于散列表条目时,键值对将被删除并垃圾回收. 

### 链接散列集和链接映射表 
LinkedHashSet遍历时按插入顺序; LinkedHashMap遍历时按访问顺序(每次put、get操作时将元素从当前链表位置删除并插入队尾),可用实现LRU缓存. 

### 枚举集与枚举映射表 
EnumSet是一个枚举类型元素集的实现,由于枚举元素个数有限,所以EnumSet内部用位序列实现.如果对应的值在集中,相应位置为1. 
EnumMap是一个键类型为枚举类型的映射表,直接用一个值数组实现. 

### 标识散列映射表 
IdentityHashMap中散列值不是由hashCode计算,而是由System.identityHashCode计算,这是Object.hashCode方法根据对象的内存地址来计算散列值
时使用的方式. 

