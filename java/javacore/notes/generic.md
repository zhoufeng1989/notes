## 泛型与虚拟机 
虚拟机没有泛型类型对象,所有对象都属于普通类.针对每一个泛型类型,都自动提供一个相应的原始类型(raw type).原始类型就是删去类型参数后的泛型类型
名.擦除(erase)类型变量,并替换为第一个限定类型(无限定的变量用Object). 

如:   
    ```
    class Pair<T> {
        private T first;
        private T second;
    }
    ```   
替换为:   
    ```
    class Pair {
        private Object first;
        private Object second;
    }
    ```   

    ```
    class Interval<T extends Comparable & Serializable> implements Serializable {
        private T lower;
        private T upper;
    }
    ```   
替换为:   
    ```
    class Interval implements Serializable {
        private Comparable lower;
        private Comparable upper;
    }
    ``` 
    
### 翻译泛型表达式  
当程序调用泛型方法时,由于擦除了返回类型,编译器要插入强制类型转换. 



