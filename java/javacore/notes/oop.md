**对象与对象变量**  
一个对象变量并没有包含一个对象，而仅仅是引用一个对象.
如```Date deadline;```只是声明了一个对象变量，该变量没有引用对象。

**基于类的访问权限**  
实例方法可以访问所调用对象的私有数据，实际上，一个方法可以访问所属类的所有对象的私有数据。
Java用于控制可见性的4个访问修饰符: 
+   仅对本类可见, private
+   对所有类可见, public
+   对本包和子类可见, protected
+   对本包可见,默认,不需要修饰符 


**final实例域**  
将实例域定义为final，构建对象时必须初始化这样的域，后面的操作不允许修改（该域引用的对象）。

**静态域与静态方法**  
类的实例之间共享静态域；静态方法不能操作对象，不能在静态方法中访问实例域，但是可以访问静态域。
静态方法常被用来实现工厂方法。main方法是静态方法，程序启动时还没有构造对象，main方法将执行并创建需要的对象。

**方法参数**  
Java总是按值传递,即方法得到的是所有参数值的一个拷贝,方法不能修改传递给它的任何参数变量的内容.  
方法参数有两种类型: 基本类型和对象引用. 会有如下结果:  
+   当参数为基本类型时,方法无法修改这个参数;
+   当参数为对象引用时,由于参数传递的是该引用的拷贝,方法可以修改该引用指向的对象,但是无法修改引用指向其它对象. 

**对象构造**  
可以提供多个构造函数(重载),当用户没有提供构造函数时,会生成一个默认构造函数,将实例域初始化为默认值.

**子类构造器**  
如果子类构造器没有显示调用父类的构造器,则将自动调用父类默认(没有参数)的构造器,如果父类中没有不带参数的构造器,并且在子类中没有显示调用父类的其他
构造器,则Java编译器将报告错误.

**final类或方法** 
将方法或类声明为final的主要目的是确保它们在子类中不会被改变. 

**抽象类** 
抽象类不能被实例化

**Object 的 equals 与 hash_code**  
Object类中的equals方法用于检测一个对象是否等于另一个对象,在Object类中,这个方法将判断两个对象是否具有相同的引用.然而,经常需要检测两个对象
状态的相等性,如果两个对象状态相等,那么这两个对象就是相等的,这就需要重写equals方法.  
如果x.equals(y)返回true, x.hashCode() 必须等于 y.hashCode().

**对象包装器与自动拆装箱**  
所有的基本类型都有与之对应的类,这些类被称作包装器.包装器类是不可变的,而且不可被继承(final) 

**可变参数**  
main方法也可被写成 ```public static void main(String... args)```

**反射** 

Class 类. Java 运行时系统为所有对象维护一个运行时的类型标识,这个信息跟踪每个对象的所属类.可以通过Class类访问这些信息,Object类的getClass方法返回Class对象.
三种方法得到对应的Class对象:  
+   instance.getClass
+   classname.class
+   Class.forName

**interface**  

an **interface** is not a class but a set of requirements for the classes that want to conform to the interface.   
All methods of an interface are automatically public.

You can use ```instanceof``` to check whether an object implements an interface: ```if (anObject instanceof Comparale)```

**clone** 
If subobject is mutable, you must redefine the clone method to make a deep copy that clones the subobjects as well. 
The ```clone``` method is declared protected in the ```Object``` class, so you have to implements Cloneable interface. 
