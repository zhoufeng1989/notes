## primitive type

###   整型

在Java中,整型的范围与平台无关.

+   int  4 Bytes
+   short 2 Bytes
+   long  8 Bytes
+   byte  1 Byte

长整型有后缀L,十六进制有签注0x,八进制有前缀0,java没有无符号类型.

### 浮点类型

+   float 4 Bytes
+   double 8 Bytes

float类型有后缀F,没有后缀的默认为double类型

### char类型

char类型用于表示单个字符,在Java中,char类型用UTF-16编码描述一个代码单元.
http://stackoverflow.com/questions/8061982/how-to-put-a-supplementary-unicode-character-in-a-string-literal

### 布尔类型

布尔类型有两个值: true 和 false. 整型值和布尔值之间不能互相转换.

## 变量

### 变量初始化

### 常量

用final关键字指示常量, 常量只能被赋值一次,一旦赋值后,不能修改, 习惯上,常量名全部大写.


### 字符串

String类没有提供修改字符串的方法,String类对象也被称为不可变字符串.这样,编译器可以让字符串共享(实际上,只有字符串常量是共享的,而+或substring
等操作产生的字符串不共享).

#### 字符串相等检测

用equals方法, 不能用'=='方法,因为'=='只能确定两个字符串的内存地址是否相同,然而完全相同的字符串可能位于不同的位置.

#### 空串与null串
空串有串长度(0)和内容(""); null串表示目前没有任何对象与当前变量关联, 在null串上调用字符串的方法会抛出异常: java.lang.NullPointerException.

```
if (str != null && str.length() != 0) {...}
```

### 控制流程

for, continue with label