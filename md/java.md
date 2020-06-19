# JAVA总结
## 基础知识
### JAVA中的几种基本数据类型是什么，各自占用多少字节

基本类型 | 大小 | 取值范围 | 数值类型
:---:|:---:|:---:|:---:
byte | 8bit(位) = 1字节 | -128 ~ 127 | 整型 
short | 16bit(位) = 2字节 | -2^15 ~ 2^15-1 | 整型 
int | 32bit(位) = 4字节 | -2^31 ~ 2^32-1 | 整型 
long | 64bit(位) = 8字节 | -2^63 ~ 2^63-1 | 整型 
float | 32bit(位) = 4字节 | - | 浮点数 
double | 64bit(位) = 8字节 | - | 浮点型
char | 16bit(位) = 2字节 | - | 字符型 
boolean | 1bit(位) |  - | 布尔型
---
`注：基本数据类型只有在对象中或者是静态变量才有默认值`

### String类能被继承吗，为什么
> 不可以，因为String类有final修饰符，而final修饰的类是不能被继承的，实现细节不允许改变。
Java关键字final有“这是无法改变的”或者“终态的”含义，它可以修饰非抽象类、非抽象类成员方法和变量。
你可能出于两种理解而需要阻止改变：设计或效率。 
>>1.final类，不能被继承，没有子类，final类中的方法默认是final的。 
2.final方法，不能被子类的方法覆盖，但可以被继承。 
3.final成员变量，表示常量，只能被赋值一次，赋值后值不再改变。 
4.final不能用于修饰构造方法。 
注意：父类的private成员方法是不能被子类方法覆盖的，因此private类型的方法默认是final类型的。
如果一个类不允许其子类覆盖某个方法，则可以把这个方法声明为final方法。 
>> 使用final方法的原因有二： 
第一、把方法锁定，防止任何继承类修改它的意义和实现。 
第二、高效。编译器在遇到调用final方法时候会转入内嵌机制，大大提高执行效率。（这点有待商榷，《Java编程思想》中对于这点存疑）  

### String s = new String(“xyz”);  创建了几个对象
> 1个或2个， 如果”xyz”已经存在于常量池中，则只在堆中创建”xyz”对象的一个拷贝，否则还要在常量池中再创建一份

### String s = "a"+"b"+"c"+"d"; //创建了几个对象
> 这个和JVM实现有关， 如果常量池为空，可能是1个也可能是7个。

### String，StringBuffer，StringBuilder的区别
> String不可变，每次修改值底层都进行了新建对象；
StringBuffer修改字符串内容不产生新对象，线程安全；
StringBuilder修改字符串内容不产生新对象，线程不安全。
```
String的成员变量private final char value[]是用于保存字符串值的，final修饰也就是说初始化之后不可改变。
数组也是对象，所以value也只是一个引用，它指向一个真正的数组对象。
String对象真的不可变吗？value是final修饰的，也就是说final不能再指向其他数组对象，
那么我能改变value指向的数组的内容吗？ 比如将数组中的某个位置上的字符变为下划线“_”。 至少在我们自己写的普通代码中不能够做到，因为我们根本不能够访问到这个value引用，更不能通过这个引用去修改数组。
那么用什么方式可以访问私有成员呢？ 没错，用反射， 可以反射出String对象中的value属性， 进而改变通过获得的value引用改变数组的结构。下面是实例代码：
```
```
public static void main(String[] args) throws Exception{
        // 通过反射修改

        // 创建字符串"Hello World"， 并赋给引用s
        String s = "Hello World";

        System.out.println("s = " + s + "[" + s.hashCode() + "]"); //Hello World

        //获取String类中的value字段
        Field valueFieldOfString = String.class.getDeclaredField("value");

        //改变value属性的访问权限
        valueFieldOfString.setAccessible(true);

        //获取s对象上的value属性的值
        char[] value = (char[]) valueFieldOfString.get(s);

        //改变value所引用的数组中的第5个字符
        value[5] = '_';
        
        System.out.println("s = " + s + "[" + s.hashCode() + "]");  //Hello_World

        // 直接修改
        String s2 = "Hi";
        System.out.println("s2 = " + s2 + "[" + s2.hashCode() + "]");
        s2 = "Halo";
        System.out.println("s2 = " + s2 + "[" + s2.hashCode() + "]");
    }
    输出：
    s = Hello World[-862545276]
    s = Hello_World[-862545276]
    s2 = Hi[2337]
    s2 = Halo[2241628] 
```
### ArrayList 和 LinkedList 有什么区别
> ArrayList和LinkedList都实现了List接口，有以下的不同点： 
 1.ArrayList是基于索引的数据接口。它的底层是数组。它可以以O(1)时间复杂度对元素进行随机访问。以此对应，LinkedList是以元素列表的形式存储的数据，每一个元素都和它的前一个后一个元素链接在一起，在这种情况下，查找某个元素的时间复杂度是O(n)。 
 2.相对于ArrayList，LinkedList的插入，添加，删除操作速度更快，因为当元素被添加到集合任意位置的时候，不需要像数组那样重新计算大小或者是更新索引。
 3.LinkedList比ArrayList更占内存，因为LinkedList为每一个节点存储了两个引用，一个指向前一个元素，一个指向下一个元素。

### 讲讲类的实例化顺序，比如父类静态数据，构造函数，字段，子类静态数据，构造函数，字段，当new的时候，他们的执行顺序。
> 父类静态代变量、 
  父类静态代码块、 
  子类静态变量、 
  子类静态代码块、
  父类非静态变量（父类实例成员变量）、 
  父类构造函数、 
  子类非静态变量（子类实例成员变量）、 
  子类构造函数。
>> 父->子,静态->非静态->构造
### 用过哪些Map类，都有什么区别，HashMap是线程安全的吗,并发下使用的Map是什么，他们内部原理分别是什么，比如存储方式，hashcode，扩容，默认容量等
> HashMap、HashTable、LinkedHashMap和TreeMap。  
> **HashMap**:
  HashMap 是一个最常用的Map，它根据键的HashCode值存储数据，根据键可以直接获取它的值，具有很快的访问速度。
  遍历时，取得数据的顺序是完全随机的。
  HashMap最多只允许一条记录的键为Null；允许多条记录的值为 Null
  HashMap不支持线程的同步，是非线程安全的，即任一时刻可以有多个线程同时写HashMap，可能会导致数据的不一致。如果需要同步，可以用 Collections和synchronizedMap方法使HashMap具有同步能力，或者使用ConcurrentHashMap。
  Hashmap在插入元素过多的时候需要进行Resize，Resize的条件是HashMap.Size   >=  Capacity * LoadFactor。
  Hashmap的Resize包含扩容和ReHash两个步骤，ReHash在并发的情况下可能会形成链表环。
---
> **Hashtable**:
  Hashtable与 HashMap类似，它继承自Dictionary类，不同的是：
  它不允许记录的键或者值为空。
  它支持线程的同步，即任一时刻只有一个线程能写Hashtable，因此也导致了 Hashtable在写入时会比较慢。
---
> **LinkedHashMap**:
  LinkedHashMap 保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，先得到的记录肯定是先插入的。在遍历的时候会比HashMap慢，不过有种情况例外，当HashMap容量很大，实际数据较少时，遍历起来可能会比LinkedHashMap慢，因为LinkedHashMap的遍历速度只和实际数据有关，和容量无关，而HashMap的遍历速度和容量有关。
---
> **TreeMap**: 
  TreeMap实现SortMap接口，能够把它保存的记录根据键排序，默认是按键值的升序排序，也可以指定排序的比较器，当用Iterator 遍历TreeMap时，得到的记录是排过序的。
---
>**区别:**
一般情况下，我们用的最多的是HashMap，HashMap里面存入的键值对在取出的时候是随机的，它根据键的HashCode值存储数据，根据键可以直接获取它的值，具有很快的访问速度。在Map中插入、删除和定位元素，HashMap 是最好的选择。
TreeMap取出来的是排序后的键值对。但如果要按自然顺序或自定义顺序遍历键，那么TreeMap会更好。
LinkedHashMap是HashMap的一个子类，如果需要输出的顺序和输入的相同，那么用LinkedHashMap可以实现，它还可以按读取顺序来排列，像连接池中可以应用。

### JAVA8的ConcurrentHashMap为什么放弃了分段锁，有什么问题吗，如果你来设计，你如何设计?
> jdk1.7分段锁的实现:
在jdk1.7中ConcurrentHashMap的底层数据结构是数组加链表,和hashmap一样。和hashmap不同的是ConcurrentHashMap中存放的数据是一段段的，即由多个Segment(段)组成的。每个Segment中都有着类似于数组加链表的结构。
 关于Segment  
 ConcurrentHashMap有3个参数：
 initialCapacity：初始总容量，默认16
 loadFactor：加载因子，默认0.75
 concurrencyLevel：并发级别，默认16
 其中并发级别控制了Segment的个数，在一个ConcurrentHashMap创建后Segment的个数是不能变的，扩容过程过改变的是每个Segment的大小。  
 关于分段锁
 段Segment继承了重入锁ReentrantLock，有了锁的功能，每个锁控制的是一段，当每个Segment越来越大时，锁的粒度就变得有些大了。  
 分段锁的优势在于保证在操作不同段 map 的时候可以并发执行，操作同段 map 的时候，进行锁的竞争和等待。这相对于直接对整个map同步synchronized是有优势的。
 缺点在于分成很多段时会比较浪费内存空间(不连续，碎片化); 操作map时竞争同一个分段锁的概率非常小时，分段锁反而会造成更新等操作的长时间等待; 当某个段很大时，分段锁的性能会下降。
 jdk1.8的map实现
 和hashmap一样,jdk 1.8中ConcurrentHashmap采用的底层数据结构为数组+链表+红黑树的形式。数组可以扩容，链表可以转化为红黑树。  
 什么时候扩容？  
 当前容量超过阈值
 当链表中元素个数超过默认设定（8个），当数组的大小还未超过64的时候，此时进行数组的扩容，如果超过则将链表转化成红黑树
 什么时候链表转化为红黑树？
 当数组大小已经超过64并且链表中的元素个数超过默认设定（8个）时，将链表转化为红黑树,把数组中的每个元素看成一个桶。代码中很多都是CAS操作，加锁的部分是对桶的头节点进行加锁，锁粒度很小。
 为什么不用ReentrantLock而用synchronized ?
 减少内存开销:如果使用ReentrantLock则需要节点继承AQS来获得同步支持，增加内存开销，而1.8中只有头节点需要进行同步。
 内部优化:synchronized则是JVM直接支持的，JVM能够在运行时作出相应的优化措施：锁粗化、锁消除、锁自旋等等。
通过  JDK 的源码和官方文档看来， 他们认为的弃用分段锁的原因由以下几点：

加入多个分段锁浪费内存空间。
生产环境中， map 在放入时竞争同一个锁的概率非常小，分段锁反而会造成更新等操作的长时间等待。
为了提高 GC 的效率

我想从下面几个角度讨论这个问题：  
锁的粒度
首先锁的粒度并没有变粗，甚至变得更细了。每当扩容一次，ConcurrentHashMap的并发度就扩大一倍。
Hash冲突
JDK1.7中，ConcurrentHashMap从过二次hash的方式（Segment -> HashEntry）能够快速的找到查找的元素。在1.8中通过链表加红黑树的形式弥补了put、get时的性能差距。
扩容
JDK1.8中，在ConcurrentHashmap进行扩容时，其他线程可以通过检测数组中的节点决定是否对这条链表（红黑树）进行扩容，减小了扩容的粒度，提高了扩容的效率。
下面是我对面试中的那个问题的一下看法：

为什么是synchronized，而不是可重入锁
1. 减少内存开销
假设使用可重入锁来获得同步支持，那么每个节点都需要通过继承AQS来获得同步支持。但并不是每个节点都需要获得同步支持的，只有链表的头节点（红黑树的根节点）需要同步，这无疑带来了巨大内存浪费。
2. 获得JVM的支持
可重入锁毕竟是API这个级别的，后续的性能优化空间很小。
synchronized则是JVM直接支持的，JVM能够在运行时作出相应的优化措施：锁粗化、锁消除、锁自旋等等。这就使得synchronized能够随着JDK版本的升级而不改动代码的前提下获得性能上的提升。

### 有没有有顺序的Map实现类，如果有，他们是怎么保证有序的。
TreeMap和LinkedHashmap都是有序的。（TreeMap默认是key升序，LinkedHashmap默认是数据插入顺序）

TreeMap是基于比较器Comparator来实现有序的。

LinkedHashmap是基于链表来实现数据插入有序的。
 
### 抽象类和接口的区别，类可以继承多个类么，接口可以继承多个接口么,类可以实现多个接口么

1.抽象类可以有自己的实现方法，接口在jdk8以后也可以有自己的实现方法（default）

2.抽象类的抽象方法是由非抽象类的子类实现，接口的抽象方法有接口的实现类实现

3.接口不能有私有的方法跟对象，抽象类可以有自己的私有的方法跟对象

类不可以继承多个类，接口可以继承多个接口，类可以实现多个接口

 
### 继承和聚合的区别在哪。
继承关系即is a 关系,子类继承父类的属性 方法;比如:我 is a 人;再比如菱形、圆形和方形都是形状的一种，那么他们都应该从形状类继承而不是聚合/组合关系。
聚合/组合关系即has a关系，两个对象之间是整体和部分的关系;比如:我 has a 头;再比如电脑是由显示器、CPU、硬盘这些类聚合成电脑类,而不是从电脑类继承。

聚合:表示两个对象之间是整体和部分的弱关系,部分的生命周期可以超越整体。如电脑和鼠标；

组合:表示两个对象之间是整体和部分的强关系,部分的生命周期不能超越整体,或者说不能脱离整体而存在。组合关系的“部分”,是不能在整体之间进行共享的。如人和眼睛的关系；不过,如果你要说,眼睛可以移植,是不是说可以脱离人而存在,它们就不是组合关系了?其实,UML中对象的关系都是在相应的软件环境或实际场景下定义的,这里区别聚合和组合的关系。关键还是在于它们之中整体和部分的关系强、弱,以及它们之间的依附关系。如果刚才说眼睛可以移植给别人，那你也可以把它认为是聚合，这都要结合实际场景来说明。

聚合和组合都属于关联，很难区分，但是只要记住一点，区分它们就容易多了：
处于聚合关系的两个类生命周期不同步，则是聚合关系；处于组合关系的两个类的生命周期同步;eg:聚合关系,当A创建的时候,B不一定创建;当A消亡时,B不一定消亡。class A{ private B;}class B{….}组合关系,当创建一个A对象时,也会创建一个B对象;当A消亡时,作为A的属性的B对象也会消亡。class A{private b=new B();….}class B{..｝

 
### IO模型有哪些，讲讲你理解的nio ，他和bio，aio的区别是啥，谈谈reactor模型
 1.什么是BIO,NIO,AIO
 JAVA BIO：同步并阻塞，服务器实现模式为一个连接一个线程，即客户端有连接请求时服务器端就需要启动一个线程并处理，如果这个连接不做任何事情会造成不必要的开销，当然可以通过线程池机制改善
 JAVA NIO：同步非阻塞，服务器实现模式为一个请求一个线程，即客户端发送的连接请求都会注册到多路复用器上，多路复用器轮询到连接有IO请求时才启动一个线程进行处理
 JAVA AIO(NIO2)：异步非阻塞，服务器实现模式为一个有效请求一个线程，客户端的I/O请求都是由OS先完成了再通知服务器应用去启动线程进行处理
 
 2.使用场景
 BIO方式适用于连接数目比较小且固定的架构，这种方式对服务器资源要求比较高，并发局限于应用中，JDK1.4以前的唯一选择，但程序直观简单易理解。
 NIO方式适用于连接数目多且连接比较短（轻操作）的架构，比如聊天服务器，并发局限于应用中，编程比较复杂，JDK1.4开始支持。
 AIO方式使用于连接数目多且连接比较长（重操作）的架构，比如相册服务器，充分调用OS参与并发操作，编程比较复杂，JDK7开始支持。
 
 3.BIO 同步并阻塞
 tomcat采用的传统的BIO（同步阻塞IO模型）+线程池模式，对于十万甚至百万连接的时候，传统BIO模型是无能为力的：
 ①线程的创建和销毁成本很高，在linux中，线程本质就是一个进程，创建销毁都是重量级的系统函数
 ②线程本身占用较大的内存，像java的线程栈一般至少分配512K-1M的空间，如果系统线程过高，内存占用是个问题
 ③线程的切换成本高，操作系统发生线程切换的时候，需要保留线程的上下文，然后执行系统调用，如果线程数过高可能执行线程切换的时间甚至大于线程执行的时间，这时候带来的表现是系统load偏高，CPUsy使用率很高
 ④容易造成锯齿状的系统负载。系统负载是用活动线程数或CPU核心数，一旦线程数量高但外部网络环境不是很稳定，就很容易造成大量请求的结果同时返回，激活大量阻塞线程从而使系统负载压力过大。
 
 4NIO同步非阻塞
 NIO基于Reactor，当socket有流可读或可写入socket，操作系统会相应的通知引用程序进行处理，应用再将流读取到缓冲区或写入操作系统。也就是，不是一个链接就要对应一个处理线程，而是一个有效请求对应一个线程，当连接没有数据时，是没有工作线程来处理的
 Reactor模型
 ![Reactor模型图](https://img-blog.csdnimg.cn/20190530164359237.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjY1Njc5NA==,size_16,color_FFFFFF,t_70)
 nio只有acceptor的服务线程是堵塞进行的，其他读写线程是通过注册事件的方式，有读写事件激活时才调用线程资源区执行，不会一直堵塞等着读写操作，Reactor的瓶颈主要在于acceptor的执行，读写事件也是在这一块分发

 5AIO异步非堵塞IO
 ![AIO异步非堵塞IO](https://img-blog.csdnimg.cn/20190530165225603.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjY1Njc5NA==,size_16,color_FFFFFF,t_70)
 AIO需要一个链接注册读写事件和回调方法，当进行读写操作时，只须直接调用API的read或write方法即可，这两种方法均为异步，对于读操作而言，当有流可读取时，操作系统会将可读的流传入read方法的缓冲区，并通知应用程序；对于写操作而言，当操作系统将write方法传递的流写入完毕时，操作系统主动通知应用程序
 即，read/write方法都是异步的，完成后会主动调用回调函数

### 反射的原理，反射创建类实例的三种方式是什么。
 反射机制:
 所谓的反射机制就是java语言在运行时拥有一项自观的能力。通过这种能力可以彻底的了解自身的情况为下一步的动作做准备。下面具体介绍一下java的反射机制。这里你将颠覆原来对java的理解。 Java的反射机制的实现要借助于4个类：class，Constructor，Field，Method；其中class代表的时类对 象，Constructor－类的构造器对象，Field－类的属性对象，Method－类的方法对象。通过这四个对象我们可以粗略的看到一个类的各个组 成部分。
 Java反射之类的实例对象的三种表示方式
```java
public class ClassDemo1 {
public static void main(String[] args) {
    
    //Foo的实例对象如何表示
    Foo foo1 = new Foo();//foo1就表示出来了
    //Foo这个类，也是一个实例对象，Class类的实例对象，如何表示呢、
    //任何一个类都是Class的实例对象，这个实例对象那个有三个表示方式
    //第一种表示方式--》实际在告诉我们任何一个类都有一个隐含的静态成员变量class
    Class class1 = Foo.class;
    
    //第二种表示方式  已经知道该类的对象通过getClass方法
    Class class2 = foo1.getClass();
    
    /*
     * 官网class1 ,class2表示了Foo类的类类型(class type)
     * 万事万物 都是对象
     * 类也是对象，是Class类的实例对象
     * 这个对象我们称为该类的类类型
     */
    //不管class1  or class2都代表了Foo类的类类型，一个类只可能是Class；类的一个实例对象
    System.out.println(class1==class2);//true'
    
    //第三种表达方式
    Class class3 = null;
    try {
        class3 = Class.forName("com.imooc.reflect.Foo");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    //
    System.out.println(class2==class3);//true
    
    //我们完全尅通过类的类类型创建该类的对象实例--》通过class1  or class2 or class3
    //创建Foo类的实例对象
    try {
        //需要有无参数的构造方法
        Foo foo = (Foo) class1.newInstance();//需要强转
        foo.print();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
//
class Foo{
    public void print(){
        System.out.println("foo");
    }
}
```
反射详解
一、什么是Java反射机制
当程序运行时，允许改变程序结构或变量类型，这种语言称为动态语言。我们认为java并不是动态语言，但是它却有一个非常突出的动态相关机制，俗称：反射。

IT行业里这么说，没有反射也就没有框架，现有的框架都是以反射为基础。在实际项目开发中，用的最多的是框架，填的最多的是类，反射这一概念就是将框架和类揉在一起的调和剂。所以，反射才是接触项目开发的敲门砖！

二、反射的应用及原理
我们可能听过，Java编写的程序，一次编译，到处运行。这也是Java程序为什么是无关平台的所在，原因在于，java的源代码会被编译成.class文件字节码，只要装有Java虚拟机JVM的地方（Java提供了各种不同平台上的虚拟机制，第一步由Java IDE进行源代码编译，得到相应类的字节码.class文件，第二步，Java字节码由JVM执行解释给目标计算机，第三步，目标计算机将结果呈现给我们计算机用户；因此，Java并不是编译机制，而是解释机制），.class文件畅通无阻。

Java的反射机制，操作的就是这个.class文件，首先加载相应类的字节码（运行eclipse的时候，.class文件的字节码会加载到内存中），随后解剖（反射 reflect）出字节码中的构造函数、方法以及变量（字段），或者说是取出，我们先来定义一个类Animal，里面定义一些构造函数，方法，以及变量：
```java
package com.reflect;

public class Animal {

    public String name = "Dog";
    private int age = 30;

    //默认无参构造函数
    public Animal() {
        System.out.println("Animal");
    }

    //带参数的构造函数
    public Animal(String name, int age) {
        System.out.println(name + "," + age);
    }

    //公开 方法  返回类型和参数均有
    public String sayName(String name) {
        return "Hello," + name;
    }

}
```
我们再定义一个测试类：ReflectTest.java
```java
package com.reflect;

public class ReflectTest {
    public static void main(String[] args) {
        
        Animal animal = new Animal();
        System.out.println(animal.name);
        
    }
}
```
我们运行一下我们的项目，会发现Animal.java被翻译成了对应的Animal.class
![Animal.java被翻译成了对应的Animal.class](https://img-blog.csdnimg.cn/20190205211959611.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3JpZW1hbm5f,size_16,color_FFFFFF,t_70)
我们借助javap命令查看一下，这个Animal.class里面的内容是什么：
```
D:\Tools\intellij idea\ideaIU_workspace\LeetCode\target\classes\com\reflect>javap -c Animal.class
Compiled from "Animal.java"
public class com.reflect.Animal {
  public java.lang.String name;

  public com.reflect.Animal();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":
()V
       4: aload_0
       5: ldc           #2                  // String Dog
       7: putfield      #3                  // Field name:Ljava/lang/String;
      10: aload_0
      11: bipush        30
      13: putfield      #4                  // Field age:I
      16: getstatic     #5                  // Field java/lang/System.out:Ljava/
io/PrintStream;
      19: ldc           #6                  // String Animal
      21: invokevirtual #7                  // Method java/io/PrintStream.printl
n:(Ljava/lang/String;)V
      24: return

  public com.reflect.Animal(java.lang.String, int);
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":
()V
       4: aload_0
       5: ldc           #2                  // String Dog
       7: putfield      #3                  // Field name:Ljava/lang/String;
      10: aload_0
      11: bipush        30
      13: putfield      #4                  // Field age:I
      16: getstatic     #5                  // Field java/lang/System.out:Ljava/
io/PrintStream;
      19: new           #8                  // class java/lang/StringBuilder
      22: dup
      23: invokespecial #9                  // Method java/lang/StringBuilder."<
init>":()V
      26: aload_1
      27: invokevirtual #10                 // Method java/lang/StringBuilder.ap
pend:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      30: ldc           #11                 // String ,
      32: invokevirtual #10                 // Method java/lang/StringBuilder.ap
pend:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      35: iload_2
      36: invokevirtual #12                 // Method java/lang/StringBuilder.ap
pend:(I)Ljava/lang/StringBuilder;
      39: invokevirtual #13                 // Method java/lang/StringBuilder.to
String:()Ljava/lang/String;
      42: invokevirtual #7                  // Method java/io/PrintStream.printl
n:(Ljava/lang/String;)V
      45: return

  public java.lang.String sayName(java.lang.String);
    Code:
       0: new           #8                  // class java/lang/StringBuilder
       3: dup
       4: invokespecial #9                  // Method java/lang/StringBuilder."<
init>":()V
       7: ldc           #14                 // String Hello,
       9: invokevirtual #10                 // Method java/lang/StringBuilder.ap
pend:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      12: aload_1
      13: invokevirtual #10                 // Method java/lang/StringBuilder.ap
pend:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      16: invokevirtual #13                 // Method java/lang/StringBuilder.to
String:()Ljava/lang/String;
      19: areturn
}
```
我们发现，字节码里面包含了类Animal的构造函数、变量以及方法，但注意，全都是public类型的，我们的定义的类的私有变量 private int age =30 哪去了？当然，既然是类的私有部分，肯定不会暴露在外面的，但是不阻碍我们通过反射获得字节码中的私有成员（本篇只举例说明私有变量（字段field），其他私有类成员同理）。

我们的类Animal在Anima.java中定义，但在Animal.class文件中，我们的Animal类阐述如下：

public class com.reflect.Animal
1
下面，我们来写一段demo，来演示一下，如何使用反射机制，将.class文件中的类加载出来，并解剖出字节码中对应类的相关内容（构造函数、属性、方法）：

ReflectTest.java:
```java
package com.reflect;

import java.lang.reflect.Constructor;

public class ReflectTest {
    public static void main(String[] args) throws Exception {

        //1、加载类,指定类的完全限定名：包名+类名
        Class c1 = Class.forName("com.reflect.Animal");
        System.out.println(c1); //打印c1，发现值和字节码中的类的名称一样

        //2、解刨(反射)类c1的公开构造函数，且参数为null
        Constructor ctor1 = c1.getConstructor();

        //3、构造函数的用途，就是创建类的对象（实例）的
        //除了私有构造函数外（单列模式，禁止通过构造函数创建类的实例，保证一个类只有一个实例）
        //ctor1.newInstance()默认生成一个Object对象,我们需要转化成我们要的Animal类对象
        Animal a1 = (Animal) ctor1.newInstance();

        //4、证明一下a1确实是Animal的实例，我们通过访问类中的变量来证明
        System.out.println(a1.name);

    }
}
/**
  输出结果：
  class com.reflect.Animal
  Animal
  Dog
*/

```
我们接着走，获得类中的变量（字段）和方法，两种方式，一个是getXXX，一个是getDeclaredXXX，二者是有区别的，下面demo注释的很详细，并且，我们使用反射出的字段和方法，去获取相应实例的字段值和唤起方法（相当于执行某实例的方法），我们看下完整版demo：

加强版的 ReflectTest.java
```java
package com.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {
    public static void main(String[] args) throws Exception {

        System.out.println("A(无参构造函数)--加载类、反射类的构造函数、利用构造函数new一个Animal实例instance--");

        //1、加载类,指定类的完全限定名：包名+类名
        Class c1 = Class.forName("com.reflect.Animal");
        System.out.println(c1); //打印c1，发现值和字节码中的类的名称一样

        //2、解刨(反射)类c1的公开构造函数，且参数为null
        Constructor ctor1 = c1.getConstructor();

        //3、构造函数的用途，就是创建类的对象（实例）的
        //除了私有构造函数外（单列模式，禁止通过构造函数创建类的实例，保证一个类只有一个实例）
        //ctor1.newInstance()默认生成一个Object对象,我们需要转化成我们要的Animal类对象
        Animal a1 = (Animal) ctor1.newInstance();

        //4、证明一下a1确实是Animal的实例，我们通过访问类中的变量来证明
        System.out.println(a1.name);

        System.out.println("A(有参构造函数)--加载类、反射类的构造函数、利用构造函数new一个Animal实例instance--");

        //2.b、 解刨(反射)类c1的公开构造函数，参数为string和int
        Constructor ctor2 = c1.getConstructor(String.class, int.class);
        Animal a2 = (Animal) ctor2.newInstance("cat", 20);

        System.out.println("B--获得本类中的所有的字段----------------------------");

        //5、获得类中的所有的字段	包括public、private和protected，不包括父类中申明的字段
        Field[] fields = c1.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        System.out.println("C--获得本类中的所有公有的字段，并获得指定对象的字段值-----");

        //6、获得类中的所有的公有字段
        fields = c1.getFields();
        for (Field field : fields) {
            System.out.println(field + ", 字段值 = " + field.get(a1));
            //注意：私有变量值，无法通过field.get(a1)进行获取值
            //通过反射类中的字段name，修改name的值（注意，原值在类中name="Dog"）
            //如果，字段名称等于"name"，且字段类型为String，我们就修改字段的值，也就是类中变量name的值
            if (field.getName() == "name" && field.getType().equals(String.class)) {
                String name_new = (String) field.get(a1); //记得转换一下类型
                name_new = "哈士奇"; //重新给name赋值
                field.set(a1, name_new); //设置当前实例a1的name值，使修改后的值生效
            }
        }

        System.out.println("利用反射出的字段，修改字段值，修改后的name = "+a1.name);
        System.out.println("D--获取本类中的所有的方法--------------------");

        //7、获取本类中所有的方法 包括public、private和protected，不包括父类中申明的方法
        Method[] methods = c1.getDeclaredMethods();
        for (Method m : methods) {
            System.out.println(m);//我们在类Animal中只定义了一个public方法，sayName
        }

        System.out.println("E--获取本类中的所有的公有方法，包括父类中和实现接口中的所有public方法-----------");

        //8、获取类中所有公有方法，包括父类中的和实现接口中的所有public 方法
        methods = c1.getMethods();
        for (Method m : methods) {
            System.out.println(m);//我们在类Animal中只定义了一个public方法，sayName
        }

        System.out.println("F--根据方法名称和参数类型获取指定方法，并唤起方法：指定所属对象a1，并给对应参数赋值-----------");

        //9、唤起Method方法(执行) getMethod:第一个参数是方法名，后面跟方法参数的类
        Method sayName = c1.getMethod("sayName", String.class);
        System.out.println(sayName.invoke(a1, "riemann"));

    }
}
/**
输出结果：

A(无参构造函数)--加载类、反射类的构造函数、利用构造函数new一个Animal实例instance--
class com.reflect.Animal
Animal
Dog
A(有参构造函数)--加载类、反射类的构造函数、利用构造函数new一个Animal实例instance--
cat,20
B--获得本类中的所有的字段----------------------------
public java.lang.String com.reflect.Animal.name
private int com.reflect.Animal.age
C--获得本类中的所有公有的字段，并获得指定对象的字段值-----
public java.lang.String com.reflect.Animal.name, 字段值 = Dog
利用反射出的字段，修改字段值，修改后的name = 哈士奇
D--获取本类中的所有的方法--------------------
public java.lang.String com.reflect.Animal.sayName(java.lang.String)
E--获取本类中的所有的公有方法，包括父类中和实现接口中的所有public方法-----------
public java.lang.String com.reflect.Animal.sayName(java.lang.String)
public final void java.lang.Object.wait() throws java.lang.InterruptedException
public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
public boolean java.lang.Object.equals(java.lang.Object)
public java.lang.String java.lang.Object.toString()
public native int java.lang.Object.hashCode()
public final native java.lang.Class java.lang.Object.getClass()
public final native void java.lang.Object.notify()
public final native void java.lang.Object.notifyAll()
F--根据方法名称和参数类型获取指定方法，并唤起方法：指定所属对象a1，并给对应参数赋值-----------
Hello,riemann
*/

```
反射的机制，无非就是先加载对应字节码中的类，然后，根据加载类的信息，一点点的去解剖其中的内容，不管你是public的还是private的，亦或是本类的还是来自原继承关系或者实现接口中的方法，我们java的反射技术 reflect，均可以将其从字节码中拉回到现实，不仅可以得到字段的名字，我们还可以获得字段的值和修改字段的值，不仅可以得到方法的申明我们还可以拿到方法的定义和唤起方法（执行方法），当然，你会有一个这样的疑惑？

为什么new一个对象那么简单，非要用反射技术中的newInstance？

为什么，我可以直接对象a1. 变量访问变量，却非要用反射那么费劲的获得name字段呢？

为什么，我几行代码就能搞定的事情，非要用反射呢？
ok，解密答案之前，我们先来思考一个问题？

假设我们定义了很多类，有Animal、Person、Car… ，如果我想要一个Animal实例，那我就new Animal（），如果另一个人想要一个Person实例，那么他需要new Person（），当然，另一个说，我只要一个Car实例，于是它要new Car（）…这样一来就导致，每个用户new的对象需求不相同，因此他们只能修改源代码，并重新编译才能生效。这种将new的对象写死在代码里的方法非常不灵活，因此，为了避免这种情况的方法，Java提供了反射机制
比如，在Spring中，我们经常看到：
```xml
<!-- 配置 Spring 的 org.springframework.jdbc.core.JdbcTemplate -->
<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
     <property name="dataSource" ref="dataSource"></property>
</bean>
```
针对上述的配置，我们Spring是怎么帮助我们实例化对象，并放到容器中去了呢？ 没错，就是通过反射！！！！

我们看下，下面的伪代码实现过程:

```
//解析<bean .../>元素的id属性得到该字符串值为"sqlSessionFactory" 
	    String idStr = "sqlSessionFactory";  
	    //解析<bean .../>元素的class属性得到该字符串值为"org.mybatis.spring.SqlSessionFactoryBean"  
	    String classStr = "org.mybatis.spring.SqlSessionFactoryBean";  
	    //利用反射知识，通过classStr获取Class类对象  
	    Class cls = Class.forName(classStr);  
	    //实例化对象  
	    Object obj = cls.newInstance();  
	    //container表示Spring容器  
	    container.put(idStr, obj);  
		
	    //当一个类里面需要用另一类的对象时，我们继续下面的操作
	    
	    //解析<property .../>元素的name属性得到该字符串值为“dataSource”  
	    String nameStr = "dataSource";  
	    //解析<property .../>元素的ref属性得到该字符串值为“dataSource”  
	    String refStr = "dataSource";  
	    //生成将要调用setter方法名  
	    String setterName = "set" + nameStr.substring(0, 1).toUpperCase()  
	            + nameStr.substring(1);  
	    //获取spring容器中名为refStr的Bean，该Bean将会作为传入参数  
	    Object paramBean = container.get(refStr);  
	    //获取setter方法的Method类，此处的cls是刚才反射代码得到的Class对象  
	    Method setter = cls.getMethod(setterName, paramBean.getClass());  
	    //调用invoke()方法，此处的obj是刚才反射代码得到的Object对象  
	    setter.invoke(obj, paramBean); 
```
是不是很熟悉，虽然是伪代码，但是和我们本篇讲的反射机制的使用是相同的，现在知道我们的反射机制用在哪了吧，没错就是我们经常提到的Java web框架中，里面就用到了反射机制，只要在代码或配置文件中看到类的完全限定名（包名+类名）,其底层原理基本上使用的就是Java的反射机制。

因此，如果你不做框架的话，基本上是用不到反射机制的，我们大多时候是使用框架的一方，而反射机制都已经在底层实现过了，因此，我们不必担心，我们会写那么复杂的代码。但是，我们必须要理解这种机制的存在！
三、创建类实例的三种方式
JavaBean
```java
public class Person implements China{
      private String name;
      private int age ;
      private char sex ;

      public Person() {
           super ();
     }

      public Person(String name, int age, char sex) {
           super ();
           this .name = name;
           this .age = age;
           this .sex = sex;
     }

      public String getName() {
           return name ;
     }

      public void setName(String name) {
           this .name = name;
     }

      public int getAge() {
           return age ;
     }

      public void setAge(int age) {
           this .age = age;
     }

      public char getSex() {
           return sex ;
     }

      public void setSex(char sex) {
           this .sex = sex;
     }
      public void eat()
     {
          System. out .println("吃了" );
     }

      @Override
      public String toString() {
           return "Person [name=" + name + ", age=" + age + ", sex=" + sex + "]" ;
     }

      @Override
      public void sayChina() {
           // TODO Auto-generated method stub
          System. out .println("作者：" + AUTHOR + "国籍："+ NATIONAL );
     }

      @Override
      public String sayHello(String name, int age, char sex) {
           // TODO Auto-generated method stub
           return "姓名:" + name + "年龄："+ age + "性别:" + sex;
     }

}
```
```java
public class ClassDemo {

     public static void main(String[] args) {
          Person p1 = new Person("小明" ,20,'男' );
          Person p2 = new Person("小红" ,23,'女' );

           //创建Class对象的方式一：(对象.getClass())，获取person类中的字节码文件
           Class class1 = p1.getClass();
          System. out.println(p1.getClass().getName());
           Class class2 = p2.getClass();
          System. out.println(class1 == class2 );

          System. out.println("==============================" );
           //创建Class对象的方式二：(类.class:需要输入一个明确的类，任意一个类型都有一个静态的class属性)
           Class class3 = Person.class;
          System. out.println(class1 == class2);

          System. out.println("==============================" );
           //创建Class对象的方式三：(forName():传入时只需要以字符串的方式传入即可)
           //通过Class类的一个forName（String className)静态方法返回一个Class对象，className必须是全路径名称；
           //Class.forName()有异常：ClassNotFoundException

           Class class4 = null;
           try {
              class4 = Class.forName("cn.itcast.Person");
          } catch (ClassNotFoundException e) {
               // TODO Auto-generated catch block
              e.printStackTrace();
          }
          System. out.println(class4 == class3);
     }
}

```
注意：在开发中一般使用第三种方法，因为第三种接收的是一个字符串路径，将来可以通过配置文件获取，通用性好；

### 反射中，Class.forName和ClassLoader区别。
一 Java类装载过程
装载 -> 链接(链接、准备、解析) -> 初始化 -> 使用 ->卸载  
![过程](https://images2015.cnblogs.com/blog/809371/201608/809371-20160831162844230-2019325734.jpg)

装载：通过累的全限定名获取二进制字节流，将二进制字节流转换成方法区中的运行时数据结构，在内存中生成Java.lang.class对象； 

链接：执行下面的校验、准备和解析步骤，其中解析步骤是可以选择的； 

　　校验：检查导入类或接口的二进制数据的正确性；（文件格式验证，元数据验证，字节码验证，符号引用验证） 

　　准备：给类的静态变量分配并初始化存储空间； 

　　解析：将常量池中的符号引用转成直接引用； 

初始化：激活类的静态变量的初始化Java代码和静态Java代码块，并初始化程序员设置的变量值。

二 分析 Class.forName()和ClassLoader.loadClass


Class.forName(className)方法，内部实际调用的方法是  Class.forName(className,true,classloader);

第2个boolean参数表示类是否需要初始化，  Class.forName(className)默认是需要初始化。

一旦初始化，就会触发目标对象的 static块代码执行，static参数也也会被再次初始化。

    

ClassLoader.loadClass(className)方法，内部实际调用的方法是  ClassLoader.loadClass(className,false);

第2个 boolean参数，表示目标对象是否进行链接，false表示不进行链接，由上面介绍可以，

不进行链接意味着不进行包括初始化等一些列步骤，那么静态块和静态对象就不会得到执行




三  数据库链接为什么使用Class.forName(className)

 

JDBC  Driver源码如下,因此使用Class.forName(classname)才能在反射回去类的时候执行static块。
```
static {
    try {
        java.sql.DriverManager.registerDriver(new Driver());
    } catch (SQLException E) {
        throw new RuntimeException("Can't register driver!");
    }
}
```
jdk动态代理类中也是用的Class.forName

### 描述动态代理的几种实现方式，分别说出相应的优缺点。
为什么需要动态代理?
如spring等这样的框架，要增强具体业务的逻辑方法，不可能在框架里面去写一个静态代理类，只能按照用户的注解或者xml配置来动态生成代理类。
业务代码内，当需要增强的业务逻辑非常通用（如:添加log，重试，统一权限判断等）时，使用动态代理将会非常简单，如果每个方法增强逻辑不同，那么静态代理更加适合。
使用静态代理时，如果代理类和被代理类同时实现了一个接口，当接口方法有变动时，代理类也必须同时修改。
动态代理在Java中有着广泛的应用，比如Spring AOP、Hibernate数据查询、测试框架的后端mock、RPC远程调用、Java注解对象获取、日志、用户鉴权、全局性异常处理、性能监控，甚至事务处理等
---
两种常见的动态代理方式： 
JDK原生动态代理-> 通过接口
CGLIB动态代理-> 通过继承
---
常见的字节码操作类库
Apache BCEL (Byte Code Engineering Library)：是Java classworking广泛使用的一种框架，它可以深入到JVM汇编语言进行类操作的细节。
ObjectWeb ASM：是一个Java字节码操作框架。它可以用于直接以二进制形式动态生成stub根类或其他代理类，或者在加载时动态修改类。
CGLIB(Code Generation Library)：是一个功能强大，高性能和高质量的代码生成库，用于扩展JAVA类并在运行时实现接口。
Javassist：是Java的加载时反射系统，它是一个用于在Java中编辑字节码的类库; 它使Java程序能够在运行时定义新类，并在JVM加载之前修改类文件。
---
JDK动态代理
JDK动态代理主要涉及两个类：java.lang.reflect.Proxy 和 java.lang.reflect.InvocationHandler
InvocationHandler 和 Proxy 的主要方法介绍如下：

java.lang.reflect.InvocationHandler

Object invoke(Object proxy, Method method, Object[] args) 定义了代理对象调用方法时希望执行的动作，用于集中处理在动态代理类对象上的方法调用

java.lang.reflect.Proxy

static InvocationHandler getInvocationHandler(Object proxy) 用于获取指定代理对象所关联的调用处理器

static Class<?> getProxyClass(ClassLoader loader, Class<?>... interfaces) 返回指定接口的代理类

static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) 构造实现指定接口的代理类的一个新实例，所有方法会调用给定处理器对象的 invoke 方法

static boolean isProxyClass(Class<?> cl) 返回 cl 是否为一个代理类

CGLIB 创建动态代理类的模式是：
查找目标类上的所有非final 的public类型的方法定义；
将这些方法的定义转换成字节码；
将组成的字节码转换成相应的代理的class对象；
实现 MethodInterceptor接口，用来处理对代理类上所有方法的请求

JDK动态代理与CGLIB动态代理对比
JDK动态代理：基于Java反射机制实现，必须要实现了接口的业务类才能用这种办法生成代理对象。

cglib动态代理：基于ASM机制实现，通过生成业务类的子类作为代理类。

JDK Proxy 的优势：

最小化依赖关系，减少依赖意味着简化开发和维护，JDK 本身的支持，可能比 cglib 更加可靠。
平滑进行 JDK 版本升级，而字节码类库通常需要进行更新以保证在新版 Java 上能够使用。
代码实现简单。
基于类似 cglib 框架的优势：

无需实现接口，达到代理类无侵入
只操作我们关心的类，而不必为其他相关类增加工作量。
高性能

---
总结 
静态代理：代理对象和实际对象都继承了同一个接口，在代理对象中指向的是实际对象的实例，这样对外暴露的是代理对象而真正调用的是 Real Object
静态代理的缺点
虽然静态代理实现简单，且不侵入原代码，但是，当场景稍微复杂一些的时候，静态代理的缺点也会暴露出来。

1、 当需要代理多个类的时候，由于代理对象要实现与目标对象一致的接口，有两种方式：

只维护一个代理类，由这个代理类实现多个接口，但是这样就导致代理类过于庞大
新建多个代理类，每个目标对象对应一个代理类，但是这样会产生过多的代理类
2、 当接口需要增加、删除、修改方法的时候，目标对象与代理类都要同时修改，不易维护。

优点：可以很好的保护实际对象的业务逻辑对外暴露，从而提高安全性。
缺点：不同的接口要有不同的代理类实现，会很冗余
JDK 动态代理：

为了解决静态代理中，生成大量的代理类造成的冗余；
JDK 动态代理只需要实现 InvocationHandler 接口，重写 invoke 方法便可以完成代理的实现，
jdk的代理是利用反射生成代理类 Proxyxx.class 代理类字节码，并生成对象
jdk动态代理之所以只能代理接口是因为代理类本身已经extends了Proxy，而java是不允许多重继承的，但是允许实现多个接口

优点：解决了静态代理中冗余的代理实现类问题。
缺点：JDK 动态代理是基于接口设计实现的，如果没有接口，会抛异常。

CGLIB 代理：

由于 JDK 动态代理限制了只能基于接口设计，而对于没有接口的情况，JDK方式解决不了；
CGLib 采用了非常底层的字节码技术，其原理是通过字节码技术为一个类创建子类，并在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑，来完成动态代理的实现。
实现方式实现 MethodInterceptor 接口，重写 intercept 方法，通过 Enhancer 类的回调方法来实现。
但是CGLib在创建代理对象时所花费的时间却比JDK多得多，所以对于单例的对象，因为无需频繁创建对象，用CGLib合适，反之，使用JDK方式要更为合适一些。
同时，由于CGLib由于是采用动态创建子类的方法，对于final方法，无法进行代理。

优点：没有接口也能实现动态代理，而且采用字节码增强技术，性能也不错。
缺点：技术实现相对难理解些。


### 为什么CGlib方式可以对接口实现代理。
对接口进行代理的cglib，最后生成的源码是实现了该接口和Factory接口
对实现类进行代理的cglib，最后生成的源码是继承了实现类并实现了Factory接口

### final的用途。
+ 作用于类:此类不可被继承,类中方法默认是final。 

+ 作用于方法:此方法不可被重载，不可被重写，可以被继承。
但是不可作用于构造方法。private 方法隐式为final方法 
+ 作用于变量： 
    + 申明后使用前，必须赋值
    + 不可更改具体分为两种 ：引用类型和基本类型，前者是指不可更改指向所以申明时候不可赋值null，可以留空，
   但是可以更改具体所指向对象的数据状态，后者仅仅可以被赋值一次，一旦赋值不可更改 。
    + 成员变量，通常是类的成员变量，必须在定义时或者构造器中进行初始化赋值，而且final变量一旦被初始化赋值之后，就不能再被赋值了。如若类定义时的申明未赋值，那么实例化对象的时候最好赋值，不然不能保证在使用前它一定被赋值过。作为接口成员的静态变量，赋值只能在其声明中通过初始化表达式完成；    
    + 参数变量，方法的参数变量，final Object i，i不可转移自己的指向到其他对象，基本类型 final int j ；j++ 错误。
    + 局部变量，在需要的时候赋值。
    + final变量会被编译器当成编译常量，相当于直接访问的这个常量，不需要在运行时确定，final String s = "h"(必须是明确赋值的，即编译器就知道的，如果是一个返回string的方法则还是运行时) ; String a = "h";s == a ;false!

---
Java中final修饰符既可以修饰类、方法，也可以修饰变量.

基本规则
用final修饰的类不能被扩展，也就是说不可能有子类；
用final修饰的方法不能被替换或隐藏：
使用final修饰的实例方法在其所属类的子类中不能被替换（overridden）；
使用final修饰的静态方法在其所属类的子类中不能被重定义（redefined）而隐藏（hidden）；
用final修饰的变量最多只能赋值一次，在赋值方式上不同类型的变量或稍有不同：
静态变量必须明确赋值一次（不能只使用类型缺省值）；作为类成员的静态变量，赋值可以在其声明中通过初始化表达式完成，也可以在静态初始化块中进行；作为接口成员的静态变量，赋值只能在其声明中通过初始化表达式完成；
实例变量同样必须明确赋值一次（不能只使用类型缺省值）；赋值可以在其声明中通过初始化表达式完成，也可以在实例初始化块或构造器中进行；
方法参数变量在方法被调用时创建，同时被初始化为对应实参值，终止于方法体（body）结束，在此期间其值不能改变；
构造器参数变量在构造器被调用（通过实例创建表达式或显示的构造器调用）时创建，同时被初始化为对应实参值，终止于构造器体结束，在此期间其值不能改变；
异常处理器参数变量在有异常被try语句的catch子句捕捉到时创建，同时被初始化为实际的异常对象，终止于catch语句块结束，在此期间其值不能改变；
局部变量在其值被访问之前必须被明确赋值；
关于final变量的进一步说明：
定义：blank final变量是其声明中不包含初始化表达式的final变量。
对于引用类型变量，final修饰符表示一旦赋值该变量就始终指向堆中同一个对象，不可改变，但是其所指对象本身（其状态）是可以改变的；不象C++中的const，在Java中没有办法仅通过一个final就可声明一个对象的不变性（immutability）。
常变量（constant variable）：
定义：常变量是用编译时常量表达式初始化的带有final修饰符的基本类型或字符串类型变量；
无论静态变量还是实例变量，如果它是常变量，则其引用在编译时会被解析成该常变量所表示的值，在class文件中并不存在任何对常变量域的引用；也正是基于此，当在源代码中修改某个常变量域的初始值并重新编译后，该改动并不为其他class文件可见，除非对他们也重新编译。
final变量的初始化
共性：
final在初始化之后，就不能再赋值了，也就是说，它们只能被赋值一次
一般情况下是定义时直接初始化如：
final int i=3;
但也可以定义时不初始化，叫blank final,如:

final int bi;
然后留待后面进行赋值。

但这因三种情况而不同：
1.普通auto变量(就是如方法中的局部变量)：可以在其后的代码中赋值，但也可以不赋值。

final int i;//blank final
anything();
i=1;//在其后赋值。
i=3; //error!不可再次赋值
而成员变量必须被赋值，只是赋值的地方不同：

静态成员变量：
静态成员变量必须在静态构造代码中初始化，
static final int s;
static { s=3;}//静态构造块
非静态成员变量：
必须在构造函数中被赋值。如：
final int ai;
{ ai = 3; }//instance initializer
public Contructor(){ai=3;}//构造函数中
public Contructor(int in){ai=in;}//构造函数中
注意构造函数可以会有互相调用，注意在这过程中不要使变量被重复的赋值。

另外，如果变量是对象或数组这样的引用类型。则可以操作其对象或数组，但不可以改变引用本身：


final int [] array={1,2,3};//一个由三个数字组成的数组。
array[1]=9;//array == {1,9,3}
// array=new int[6]; error!


### 写出三种单例模式实现

设计单例模式要点

 * 单例模式是最常用的设计模式，一个完美的单例需要做到哪些事呢？ 
 * 1、保证单例 
 * 2、延迟加载 
 * 3、线程安全 
 * 4、没有性能问题 
 * 5、防止序列化产生新对象 
 * 6、防止反射攻击 
 针对线程安全的问题，会用synchronized 关键字修饰getInstance()方法，如双重校验锁。另外还有懒汉式、静态内部类、枚举实现等写法。
 就枚举实现单例而言，出了能保证线程安全以外，这种方法无偿提供了序列化机制，绝对防止多次实例化，即使是在面对复杂序列化或者反射攻击的时候。
 一般有五种写法：懒汉，饿汉，双重校验锁，枚举和静态内部类。
 ```java
 /**
  * @ClassName HungerSingleton
  * @Description: 饿汉模式
  * 优点：写法简单，可以保证多个线程下的唯一实例，getInstance方法性能较高。
  * 缺点：无懒加载，类加载的时候单例对象就产生了，如果类成员占有的资源比较多，这种方法较为不妥。
  * 注意点：实现了Serializable接口后，反序列化时单例会被破坏。
  * 如果实现Serializable接口，那么需要重写readResolve，才能保证其反序列化依旧是单例
  **/
public class HungerSingleton {
    private static final HungerSingleton instance = new HungerSingleton();

    /**
     * 私有构造
     */
    private HungerSingleton(){
    }

    public static HungerSingleton getInstance(){
        return instance;
    }

    /*
    // 如果实现了Serializable, 必须重写这个方法
    private Object readResolve() throws ObjectStreamException {
        return instance;
    }*/
}

```
```java

/**
 * @ClassName LazySingleton
 * @Description: 懒汉模式
 * 优点：懒加载
 * 缺点：线程不安全，多线程环境下不能保证单例的唯一性
 * 注意点：为了达到线程安全，可以为getInstance方法加上synchronized关键字。
 * 但是会导致同一时间内只有一个线程能够调用getInstance方法，即使只需要获取实例也要过锁，使得性能不佳。
 **/
public class LazySingleton {

    // 定义实例但是不直接初始化
    private static LazySingleton instance = null;

    // 私有构造函数不允许外部new
    private LazySingleton(){

    }

    public static LazySingleton getInstance(){
        // 多线程环境下多个线程同时到达这一步，且instance=null时将会创建多个实例
        if(null==instance) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
```
---
```java
/**
 * @ClassName EnumSingleton
 * @Description: 单例模式的枚举写法《Effective Java》作者力推的方式，jdk1.5以上才适用
 **/
public enum  EnumSingleton {
    INSTANCE;
    public void method(){
        //do something
    }
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
```
---
```java
/**
 * @ClassName DoubleCheckedSingleton
 * @Description: 双重校验单例模式
 * @Author wuchangfu
 * @Date 2019/12/17 14:22
 **/
public class DoubleCheckedSingleton {

    /**
     * 定义实例但是不直接初始化,volatile禁止重排序操作，避免空指针异常：
     *
     * 主要在于instance = new Singleton()这句，这并非是一个原子操作，事实上在JVM中该语句会分为三个步骤：
     * 1.给 instance 分配内存
     * 2.调用 Singleton 的构造函数来初始化成员变量
     * 3.将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）
     * 由于在JVM的即时编译器中存在指令重排序的优化。也就是说上面的第二步和第三步的顺序是不能保证的，
     * 最终的执行顺序可能是 1-2-3 也可能是 1-3-2。
     * 如果是后者，那么在 3 执行完毕，2 未执行之前，被线程二抢占了，这时 instance 已经是非 null 了（但却没有初始化），
     * 所以线程二会直接返回 instance，一旦使用便引发空指针异常。
     * 特别注意在 Java 5 以前的版本使用了 volatile 的双检锁还是有问题的。其原因是 Java 5 以前的 JMM （Java 内存模型）是存在缺陷的，即时将变量声明成 volatile 也不能完全避免重排序，主要是 volatile 变量前后的代码仍然存在重排序问题。这个 volatile 屏蔽重排序的问题在 Java 5 中才得以修复，所以在这之后才可以放心使用 volatile。
     */
    private static volatile DoubleCheckedSingleton instance = null;

    /**
     * 私有构造
     */
    private DoubleCheckedSingleton(){
    }
    public static DoubleCheckedSingleton getInstance(){
        if(null==instance){
            synchronized (DoubleCheckedSingleton.class){
                if (null==instance) {
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        return instance;
    }
}

```
---
```java
/**
 * @ClassName HolderSingleton
 * @Description: 静态内部类
 * 静态内部类实现单例模式，通过类加载机制保证了单例对象的唯一性
 * 因为实例的建立是在类加载时完成，所以天生对多线程友好，getInstance() 方法也无需使用同步关键字。
 **/
public class HolderSingleton {
    private HolderSingleton(){

    }
    private static class Holder{
        private static HolderSingleton instance = new HolderSingleton();
    }
    public  static  HolderSingleton getInstance(){
        return Holder.instance;
    }
}

```

JDK中的Runtime是使用的简单的饿汉模式。

为了保证枚举类型像Java规范中所说的那样，每一个枚举类型极其定义的枚举变量在JVM中都是唯一的，在枚举类型的序列化和反序列化上，Java做了特殊的规定。原文如下：

Enum constants are serialized differently than ordinary serializable or externalizable objects. The serialized form of an enum constant consists solely of its name; field values of the constant are not present in the form. To serialize an enum constant, ObjectOutputStream writes the value returned by the enum constant’s name method. To deserialize an enum constant, ObjectInputStream reads the constant name from the stream; the deserialized constant is then obtained by calling the java.lang.Enum.valueOf method, passing the constant’s enum type along with the received constant name as arguments. Like other serializable or externalizable objects, enum constants can function as the targets of back references appearing subsequently in the serialization stream. The process by which enum constants are serialized cannot be customized: any class-specific writeObject, readObject, readObjectNoData, writeReplace, and readResolve methods defined by enum types are ignored during serialization and deserialization. Similarly, any serialPersistentFields or serialVersionUID field declarations are also ignored–all enum types have a fixedserialVersionUID of 0L. Documenting serializable fields and data for enum types is unnecessary, since there is no variation in the type of data sent.

大概意思就是说，在序列化的时候Java仅仅是将枚举对象的name属性输出到结果中，反序列化的时候则是通过java.lang.Enum的valueOf方法来根据名字查找枚举对象。同时，编译器是不允许任何对这种序列化机制的定制的，因此禁用了writeObject、readObject、readObjectNoData、writeReplace和readResolve等方法。 



### 如何在父类中为子类自动完成所有的hashcode和equals实现？这么做有何优劣？
父类的equals不一定满足子类的equals需求。比如所有的对象都继承Object，默认使用的是Object的equals方法，在比较两个对象的时候，是看他们是否指向同一个地址。

但是当我们的需求是对象的某个属性相同，就相等了，而默认的equals方法满足不了当前的需求，所以我们要重写equals方法。

如果重写了equals 方法就必须重写hashcode方法，否则就会降低map等集合的索引速度。
所以同时复写hashcode和equals方法。优势可以添加自定义逻辑，实现业务上的相等，且不关心超类的实现。

两个对象相等，hashcode一定相等

两个对象不等，hashcode不一定不等

hashcode相等，两个对象不一定相等

hashcode不等，两个对象一定不等 

### 请结合OO设计理念，谈谈访问修饰符public、private、protected、default在应用设计中的作用。
OO(Object Oriented)设计理念：封装、继承、多态

封装，也就是把客观事物封装成抽象的类，并且类可以把自己的数据和方法只让可信的类或者对象操作，对不可信的进行信息隐藏。所以我们可以通过public、private、protected、default 来进行访问控制

 
关键字 | 类内部 | 本包 | 子类 | 外部包
:---:|:---:|:---:|:---:|:---:
public | √ | √ | √ | √
protected | √ | √ | √ | ×
default | √ | √ | × | ×
private | √ | × | × | ×
---
public： Java语言中访问限制最宽的修饰符，一般称之为“公共的”。被其修饰的类、属性以及方法不 
　　　　　仅可以跨类访问，而且允许跨包（package）访问。 
private: Java语言中对访问权限限制的最窄的修饰符，一般称之为“私有的”。被其修饰的类、属性以 
　　　　　及方法只能被该类的对象访问，其子类不能访问，更不能允许跨包访问。 
protect: 介于public 和 private 之间的一种访问修饰符，一般称之为“保护形”。被其修饰的类、 
　　　　　属性以及方法只能被类本身的方法及子类访问，即使子类在不同的包中也可以访问。 
default：即不加任何访问修饰符，通常称为“默认访问模式“。该模式下，只允许在同一个包中进行访 
　　　　　问。

### 深拷贝和浅拷贝区别。
如果一个对象内部只有基本数据类型，那用 clone() 方法获取到的就是这个对象的深拷贝，而如果其内部还有引用数据类型，那用 clone() 方法就是一次浅拷贝的操作。

1.浅拷贝

对基本数据类型进行值传递，对引用数据类型进行引用传递般的拷贝，此为浅拷贝。

2.深拷贝

对基本数据类型进行值传递，对引用数据类型，创建一个新的对象，并复制其内容，此为深拷贝。

可以通过序列化和反序列化来实现深拷贝。


### 数组和链表数据结构描述，各自的时间复杂度
概述
　　数组　是将元素在内存中连续存放，由于每个元素占用内存相同，可以通过下标迅速访问数组中任何元素。但是如果要在数组中增加一个元素，需要移动大量元素，在内存中空出一个元素的空间，然后将要增加的元素放在其中。同样的道理，如果想删除一个元素，同样需要移动大量元素去填掉被移动的元素。如果应用需要快速访问数据，很少插入和删除元素，就应该用数组。

　　链表　中的元素在内存中不是顺序存储的，而是通过存在元素中的指针联系到一起，每个结点包括两个部分：一个是存储 数据元素 的　数据域，另一个是存储下一个结点地址的 指针。 
　　如果要访问链表中一个元素，需要从第一个元素开始，一直找到需要的元素位置。但是增加和删除一个元素对于链表数据结构就非常简单了，只要修改元素中的指针就可以了。如果应用需要经常插入和删除元素你就需要用链表。

内存存储区别
~~数组从栈中分配空间, 对于程序员方便快速,但自由度小。
链表从堆中分配空间, 自由度大但申请管理比较麻烦.~~

逻辑结构区别
数组必须事先定义固定的长度（元素个数），不能适应数据动态地增减的情况。当数据增加时，可能超出原先定义的元素个数；当数据减少时，造成内存浪费。　

链表动态地进行存储分配，可以适应数据动态地增减的情况，且可以方便地插入、删除数据项。（数组中插入、删除数据项时，需要移动其它数据项）　

总结
1、存取方式上，数组可以顺序存取或者随机存取，而链表只能顺序存取；　

2、存储位置上，数组逻辑上相邻的元素在物理存储位置上也相邻，而链表不一定；　

3、存储空间上，链表由于带有指针域，存储密度不如数组大；　

4、按序号查找时，数组可以随机访问，时间复杂度为O(1)，而链表不支持随机访问，平均需要O(n)；　

5、按值查找时，若数组无序，数组和链表时间复杂度均为O(1)，但是当数组有序时，可以采用折半查找将时间复杂度降为O(logn)；　

6、插入和删除时，数组平均需要移动n/2个元素，而链表只需修改指针即可；　

7、空间分配方面： 
　　数组在静态存储分配情形下，存储元素数量受限制，动态存储分配情形下，虽然存储空间可以扩充，但需要移动大量元素，导致操作效率降低，而且如果内存中没有更大块连续存储空间将导致分配失败； 
　　链表存储的节点空间只在需要的时候申请分配，只要内存中有空间就可以分配，操作比较灵活高效；

### error和exception的区别，CheckedException，RuntimeException的区别。
首先Exception和Error都是继承于Throwable 类，在 Java 中只有 Throwable 类型的实例才可以被抛出（throw）或者捕获（catch），它是异常处理机制的基本组成类型。

Exception和Error体现了JAVA这门语言对于异常处理的两种方式。

Exception是java程序运行中可预料的异常情况，咱们可以获取到这种异常，并且对这种异常进行业务外的处理。

Error是java程序运行中不可预料的异常情况，这种异常发生以后，会直接导致JVM不可处理或者不可恢复的情况。所以这种异常不可能抓取到，比如OutOfMemoryError、NoClassDefFoundError等。

其中的Exception又分为检查性异常和非检查性异常。两个根本的区别在于，检查性异常 必须在编写代码时，使用try catch捕获（比如：IOException异常）。非检查性异常 在代码编写使，可以忽略捕获操作（比如：ArrayIndexOutOfBoundsException），这种异常是在代码编写或者使用过程中通过规范可以避免发生的。 切记，Error是Throw不是Exception 。

检查异常 : 编译时被检测的异常 （throw后，方法有能力处理就try-catch处理，没能力处理就必须throws）。编译不通过，检查语法(其实就是throw和throws的配套使用)。

运行时异常 : 编译时不被检查的异常(运行时异常。RuntimeException及其子类)。编译通过。

1、机制上

主要表现在以下两个方面 : 
（1）如何定义方法 
（2）如何处理抛出的异常

运行时异常，不需要用throws 声明抛出 异常对象所属类，也可以不用throw 抛出异常对象或异常引用。对于调用该方法，也不需要放于 try-catch 代码块中。（为什么 ？ 如果你捕获它，就会冒这么一个风险：程序代码错误被掩盖在运行中无法察觉)

而检查异常 : 一旦 用throw 抛出异常，如果当前方法 可处理异常，那么直接在该方法内用try-catch 去处理。如果当前方法不具备处理该异常的能力，那么就必须在 参数列表后方法体前用 throws 声明 异常 所属类，交给调用该方法的 调用者(方法) 去处理 。

2、逻辑上

从逻辑的角度来看， checked 异常 和 RuntimeException 有着不同的使用目的，检查性异常 用来指示 一种调用方能够直接处理的异常情况(例如: 用户输入错误，程序可以直接捕获并处理，提示用户输入错误), 而RuntimeException 是用来指 调用方 本身无法 处理或回复 的程序错误（例如，你封装个库给别人用，当别人调用你库中某个方法是，需要传入某些参数，如果用户传入的参数不合法，你自己没办法处理，那么刺客你抛出的就应该是运行时异常）。
异常总结：
Throwable的两个子类：
Error:不希望程序捕获或程序无法处理的错误，NoSuchClassError,StackOverflowError,OutOfMemorryError,AWTError,ThreadDeath,IOError等。

---
Exception:
IOException,RuntimeException等
IOException包含ClassFormatException、StreamErrorException、SocketException、StreamOverflowException、CloseChannelException、ProtocolException等
RuntimeException包含IndexOutOfBoundsException、ClassCastException、ArithmeticException、NullPointerException、IllegalArgumentException、NumberFormatException、StringIndexOutOfBoundsException


### 请列出5个运行时异常。
RuntimeException包含IndexOutOfBoundsException、ClassCastException、ArithmeticException、NullPointerException、IllegalArgumentException、NumberFormatException、StringIndexOutOfBoundsException

###  NoClassDefFoundError 和 ClassNotFoundException 有什么区别

区别一： NoClassDefFoundError它是Error，ClassNotFoundException是
Exception。

区别二：还有一个区别在于NoClassDefFoundError是JVM运行时通过classpath加载类
时，找不到对应的类而抛出的错误。ClassNotFoundException是在编译过程中如果可能出现此异常，在编译过程中必须将ClassNotFoundException异常抛出！

NoClassDefFoundError发生场景如下：
    1、类依赖的class或者jar不存在 （简单说就是maven生成运行包后被篡改）
    2、类文件存在，但是存在不同的域中 （简单说就是引入的类不在对应的包下)
    3、大小写问题，javac编译的时候是无视大小的，很有可能你编译出来的class文件就与想要的不一样！这个没有做验证


    ClassNotFoundException发生场景如下：
    1、调用class的forName方法时，找不到指定的类
    2、ClassLoader 中的 findSystemClass() 方法时，找不到指定的类

举例说明如下:
    Class.forName("abc"); 比如abc这个类不存项目中，代码编写时，就会提示此异常是检查性异常，比如将此异常抛出。


### 在自己的代码中，如果创建一个java.lang.String类，这个类是否可以被类加载器加载？为什么。 
加载器层次结构：
JVM启动时，姓曾的三个类加载器组成的机构
1.Bootstrap ClassLoader 根类 ------引导类加载器，加载java核心类。非java.lang.ClassLoader子类，而是JVM自身实现
2.Extension ClassLoader 扩展类-----加载JRE的扩展目录中的JAR包的类（%JAVA_HOME%/jre/lib/ext或java.ext.dirs系统属性指定的目录）
3.System ClassLoader 系统类-----加载cmd java -cp **,环境变量指定的jar包和类路径。ClassLoader.getSystemClassLoader获得 系统类加载器。
4.用户类加载器。。。 
  
1. 双亲委派模型

　　从虚拟机角度看，只存在两种类加载器：1. 启动类加载器。2. 其他类加载器。从开发人员角度看，包括如下类加载器：1. 启动类加载器。2. 扩展类加载器。3. 应用程序类加载器。4. 自定义类加载器。

　　① 启动类加载器，用于加载Java API，加载<JAVA_HOME>\lib目录下的类库。

　　② 扩展类加载类，由sun.misc.Launcher$ExtClassLoader实现，用于加载<JAVA_HOME>\lib\ext目录下或者被java.ext.dirs系统变量指定路径下的类库。

　　③ 应用程序类加载器，也成为系统类加载器，由sun.misc.Launcher$AppClassLoader实现，用于加载用户类路径(ClassPath)上所指定的类库。

　　④ 自定义类加载器，继承系统类加载器，实现用户自定义加载逻辑。

　　各个类加载器之间是组合关系，并非继承关系。

　　当一个类加载器收到类加载的请求，它将这个加载请求委派给父类加载器进行加载，每一层加载器都是如此，最终，所有的请求都会传送到启动类加载器中。只有当父类加载器自己无法完成加载请求时，子类加载器才会尝试自己加载。

　　双亲委派模型可以确保安全性，可以保证所有的Java类库都是由启动类加载器加载。如用户编写的java.lang.Object，加载请求传递到启动类加载器，启动类加载的是系统中的Object对象，而用户编写的java.lang.Object不会被加载。如用户编写的java.lang.virus类，加载请求传递到启动类加载器，启动类加载器发现virus类并不是核心Java类，无法进行加载，将会由具体的子类加载器进行加载，而经过不同加载器进行加载的类是无法访问彼此的。由不同加载器加载的类处于不同的运行时包。所有的访问权限都是基于同一个运行时包而言的。　

　　当一个类加载器收到类加载的请求，它将这个加载请求委派给父类加载器进行加载，每一层加载器都是如此，最终，所有的请求都会传送到启动类加载器中。只有当父类加载器自己无法完成加载请求时，子类加载器才会尝试自己加载。

　　双亲委派模型可以确保安全性，可以保证所有的Java类库都是由启动类加载器加载。如用户编写的java.lang.Object，加载请求传递到启动类加载器，启动类加载的是系统中的Object对象，而用户编写的java.lang.Object不会被加载。如用户编写的java.lang.virus类，加载请求传递到启动类加载器，启动类加载器发现virus类并不是核心Java类，无法进行加载，将会由具体的子类加载器进行加载，而经过不同加载器进行加载的类是无法访问彼此的。由不同加载器加载的类处于不同的运行时包。所有的访问权限都是基于同一个运行时包而言的。　

2.为什么要使用这种双亲委托模式呢？
因为这样可以避免重复加载，当父亲已经加载了该类的时候，就没有必要子ClassLoader再加载一次。
考虑到安全因素，我们试想一下，如果不使用这种委托模式，那我们就可以随时使用自定义的String来动态替代java核心api中定义类型，这样会存在非常大的安全隐患，而双亲委托的方式，就可以避免这种情况，因为String已经在启动时被加载，所以用户自定义类是无法加载一个自定义的ClassLoader。
 

思考：假如我们自己写了一个java.lang.String的类，我们是否可以替换调JDK本身的类？
答案是否定的。我们不能实现。为什么呢？我看很多网上解释是说双亲委托机制解决这个问题，其实不是非常的准确。因为双亲委托机制是可以打破的，你完全可以自己写一个classLoader来加载自己写的java.lang.String类，但是你会发现也不会加载成功，具体就是因为针对java.*开头的类，jvm的实现中已经保证了必须由bootstrp来加载。
 
 3.定义自已的ClassLoader
 
既然JVM已经提供了默认的类加载器，为什么还要定义自已的类加载器呢？
 
因为Java中提供的默认ClassLoader，只加载指定目录下的jar和class，如果我们想加载其它位置的类或jar时，比如：我要加载网络上的一个class文件，通过动态加载到内存之后，要调用这个类中的方法实现我的业务逻辑。在这样的情况下，默认的ClassLoader就不能满足我们的需求了，所以需要定义自己的ClassLoader。
 
定义自已的类加载器分为两步：
 
1、继承java.lang.ClassLoader
 
2、重写父类的findClass方法
 
读者可能在这里有疑问，父类有那么多方法，为什么偏偏只重写findClass方法？
 
因为JDK已经在loadClass方法中帮我们实现了ClassLoader搜索类的算法，当在loadClass方法中搜索不到类时，loadClass方法就会调用findClass方法来搜索类，所以我们只需重写该方法即可。如没有特殊的要求，一般不建议重写loadClass搜索类的算法。
类加载器加载Class大致要经过9个步骤：

1.检测此Class 是否被载入过（即在缓存区中是否由此 Class），有，则进入第8步，否则执行第2步。

2.如果父类加载器不存在（要么parent 一定是根类加载器，要么本身就是根类加载器），则跳到第4步；如果父类加载器存在，则执行第3步。

3.请求使用父类加载器去载入目标类，如果成功则跳到第8步，否则执行第5步

4.请求使用 根类加载器 载入目标类，成功则跳到第8步，否则跳到第7步

5.当前类加载器 尝试寻找 Class文件（从与此ClassLoader相关的类路径中寻找），如果找到则执行第6步，否则跳到第7步。

6.从文件中载入Class，成功后跳到第8步。

7.抛出ClassNotFoundException异常。

8.返回对应的 java.lang.Class对象。

其中 第5,6步允许重写ClassLoader的findClass()方法来实现自己的载入策略，甚至重写loadClass()方法来实现自己 的载入过程
### 说一说你对java.lang.Object对象中hashCode和equals方法的理解。在什么场景下需要重新实现这两个方法。
1. equals 和 hashCode 方法是什么？
equal 方法用于检测一个对象是否等于另一个对象。在 Object 类中，这个方法将判断两个对象是否具有相同的引用。如果两个对象具有相同的引用，它们一定是相等的。然而，对于大多数类来说，这样的判断并没有什么意义，经常需要检测两个对象状态的相等性，如果两个对象的状态相等，就认为这两个对象是相等的。所以这个时候需要重写 equals 方法，来满足业务上的需求。下面是默认的 equals 方法实现：

public boolean equals(Object obj) {
    return (this == obj);
}
hashCode 方法由对象导出的一个整型值（散列码）。散列码没有规律。在 Object 类中，默认的散列码为对象的存储地址。

2. equals 方法具有哪些特性？
Java 语言规范要求 equals 方法具有下面的特性：

自反性：对于任何非空引用 x，x.equals(x) 应该返回 true。
对称性：对于任何引用 x 和 y，当且仅当 y.equals(x) 返回 true，x.equals(y) 也应该返回 true。
传递性：对于任何引用 x 、y 和 z，如果 x.equals(y) 返回 true，y.equals(z) 返回 true，x.equals(z) 也应该返回 true。
一致性：如果 x 和 y 引用的对象没有发生变化，反复调用 x.equals(y) 应该返回同样的结果。
对于任意非空引用 x，x.equals(null) 应该返回 false。
3. equals 和 hashCode 间的通用约定
看一下 Object.hashCode 的通用约定：

在一个应用程序执行期间，如果一个对象的 equals 方法做比较所用到的信息没有被修改的话，那么对该对象调用 hashCode 方法多次，它必须始终如一地返回同一个整数。在同一个应用程序的多次执行过程中，这个整数可以不同，即这个应用程序这次执行返回的整数与下一次执行返回的整数可以不一致。
如果两个对象根据 equals(Object) 方法是相等的，那么调用这两个对象中任一个对象的 hashCode 方法必须产生同样的整数结果。
如果两个对象根据 equals(Object) 方法是不相等的，那么调用这两个对象中任一个对象的 hashCode 方法，不要求必须产生不同的整数结果。然而，程序员应该意识到这样的事实，对于不相等的对象产生截然不同的整数结果，有可能提高散列表（hash table）的性能。

如果重新定义 equals 方法，就必须重新定义 hashCode 方法，以便用户可以将对象插入到散列表中。

在业务场景中，当我们的需求是两个对象的某个属性相同，就认为这两个对象相等，此时默认的equals方法满足不了当前的需求，我们就需要重写equals和hashCode方法。
### 在jdk1.5中，引入了泛型，泛型的存在是用来解决什么问题。
泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数，泛型的好处是在编译的时候检查类型安全，并且所有的强制转换都是自动和隐式的，以提高代码的重用率
### 这样的a.hashcode() 有什么用，与a.equals(b)有什么关系。
hashcode（）方法提供了对象的hashCode值，是一个native方法，返回的默认值与System.identityHashCode(obj)一致。

通常这个值是对象头部的一部分二进制位组成的数字，具有一定的标识对象的意义存在，但绝不定于地址。

作用是：用一个数字来标识对象。比如在HashMap、HashSet等类似的集合类中，如果用某个对象本身作为Key，即要基于这个对象实现Hash的写入和查找，那么对象本身如何实现这个呢？就是基于hashcode这样一个数字来完成的，只有数字才能完成计算和对比操作。

hashcode是否唯一
hashcode只能说是标识对象，在hash算法中可以将对象相对离散开，这样就可以在查找数据的时候根据这个key快速缩小数据的范围，但hashcode不一定是唯一的，所以hash算法中定位到具体的链表后，需要循环链表，然后通过equals方法来对比Key是否是一样的。

equals与hashcode的关系
equals相等两个对象，则hashcode一定要相等。但是hashcode相等的两个对象不一定equals相等。
### 有没有可能2个不相等的对象有相同的hashcode。
hashCode是所有java对象的固有方法，如果不重载的话，返回的实际上是该对象在jvm的堆上的内存地址，而不同对象的内存地址肯定不同，所以这个hashCode也就肯定不同了。如果重载了的话，由于采用的算法的问题，有可能导致两个不同对象的hashCode相同

### Java中的HashSet内部是如何工作的。
HashSet是用来存储没有重复元素的集合类，并且它是无序的。

HashSet 内部实现是基于 HashMap ，实现了 Set 接口。

源码解析
构造方法
    public HashSet() {
        map = new HashMap<>();
    }

    public HashSet(Collection<? extends E> c) {
        map = new HashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));
        addAll(c);
    }

    public HashSet(int initialCapacity, float loadFactor) {
        map = new HashMap<>(initialCapacity, loadFactor);
    }

    public HashSet(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
    }

    HashSet(int initialCapacity, float loadFactor, boolean dummy) {
        map = new LinkedHashMap<>(initialCapacity, loadFactor);
    }
我们发现，除了最后一个 HashSet 的构造方法外，其他所有内部就是去创建一个 Hashap 。没有其他的操作。而最后一个构造方法不是 public 的，所以不对外公开。

add
    public boolean add(E e) {
        // PRESENT = new Object()
        return map.put(e, PRESENT)==null;
    }
add 方法很简单，就是在 map 中放入一键值对。 key 就是要存入的元素，value 是 PRESENT ，其实就是 new Object() 。所以，HashSet 是不能重复的。

remove
    public boolean remove(Object o) {
        return map.remove(o)==PRESENT;
    }
相应的，remove 就是从 map 中移除 key 。

contains
    public boolean contains(Object o) {
        return map.containsKey(o);
    }
这些代码应该很明白，不需要讲了。

iterator
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }
内部调用的就是 HashMap 中 keySet 的 iterator 方法。

size

    public int size() {
        return map.size();
    }


### 什么是序列化，怎么序列化，为什么序列化，反序列化会遇到什么问题，如何解决。
序列化与反序列化（可以）
序列化 (Serialization)是将对象的状态信息转换为可以存储或传输的形式的过程。一般将一个对象存储至一个储存媒介，例如档案或是记亿体缓冲等。在网络传输过程中，可以是字节或是XML等格式。而字节的或XML编码格式可以还原完全相等的对象。这个相反的过程又称为反序列化。
Java对象的序列化与反序列化
在Java中，我们可以通过多种方式来创建对象，并且只要对象没有被回收我们都可以复用该对象。但是，我们创建出来的这些Java对象都是存在于JVM的堆内存中的。只有JVM处于运行状态的时候，这些对象才可能存在。一旦JVM停止运行，这些对象的状态也就随之而丢失了。

但是在真实的应用场景中，我们需要将这些对象持久化下来，并且能够在需要的时候把对象重新读取出来。Java的对象序列化可以帮助我们实现该功能。

对象序列化机制（object serialization）是Java语言内建的一种对象持久化方式，通过对象序列化，可以把对象的状态保存为字节数组，并且可以在有需要的时候将这个字节数组通过反序列化的方式再转换成对象。对象序列化可以很容易的在JVM中的活动对象和字节数组（流）之间进行转换。

在Java中，对象的序列化与反序列化被广泛应用到RMI(远程方法调用)及网络传输中。
相关接口及类
Java为了方便开发人员将Java对象进行序列化及反序列化提供了一套方便的API来支持。其中包括以下接口和类：

java.io.Serializable

java.io.Externalizable

ObjectOutput

ObjectInput

ObjectOutputStream

ObjectInputStream
Serializable 接口


### java8的新特性
Java 8是自Java  5（2004年）发布以来Java语言最大的一次版本升级，Java 8带来了很多的新特性，比如编译器、类库、开发工具和JVM（Java虚拟机）。
+ Lambda 表达式
```
Lambda 表达式
Lambda 表达式将函数当成参数传递给某个方法，或者把代码本身当作数据处理；

语法格式：

用逗号分隔的参数列表
-> 符号
和 语句块 组成
Arrays.asList( "a", "b", "d" ).forEach( e -> System.out.println( e ) );
等价于

List<String> list = Arrays.asList( "a", "b", "d" );
for(String e:list){
    System.out.println(e);
}
如果语句块比较复杂，使用 {} 包起来

Arrays.asList( "a", "b", "d" ).forEach( e -> {
    String m = "9420 "+e;
    System.out.print( m );
});
Lambda 本质上是匿名内部类的改装，所以它使用到的变量都会隐式的转成 final 的

String separator = ",";
Arrays.asList( "a", "b", "d" ).forEach( 
    e -> System.out.print( e + separator ) );
等价于

final String separator = ",";
Arrays.asList( "a", "b", "d" ).forEach( 
    e -> System.out.print( e + separator ) );
Lambda 的返回值和参数类型由编译器推理得出，不需要显示定义，如果只有一行代码可以不写 return 语句

Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> e1.compareTo( e2 ) );
等价于

List<String> list = Arrays.asList("a", "b", "c");
Collections.sort(list, new Comparator<String>() {
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
});
```
+ 函数式接口
    + 接口中只能有一个接口方法 
    + 可以有静态方法和默认方法 
    + 使用 @FunctionalInterface 标记 
    + 默认方法可以被覆写 
```
@FunctionalInterface@Function 
public interface FunctionalDefaultMethods {
    void method();
 
    default void defaultMethod() {            
    }
    
    static void staticMethod(){
    }
}
private interface Defaulable {
    // Interfaces now allow default methods, the implementer may or 
    // may not implement (override) them.
    default String notRequired() { 
        return "Default implementation"; 
    }        
}
 
private static class DefaultableImpl implements Defaulable {
}
 
private static class OverridableImpl implements Defaulable {
    @Override
    public String notRequired() {
        return "Overridden implementation";
    }
}

// 也可以由接口覆盖 
public interface OverridableInterface extends Defaulable{
    @Override
    public String notRequired() {
        return "interface Overridden implementation";
    }
}
由于JVM上的默认方法的实现在字节码层面提供了支持，因此效率非常高。默认方法允许在不打破现有继承体系的基础上改进接口。该特性在官方库中的应用是：给 java.util.Collection接口添加新方法，如 stream()、parallelStream()、forEach()和removeIf() 等等。

已经存在的 Java8 定义的函数式接口
我们基本不需要定义自己的函数式接口，Java8 已经给我们提供了大量的默认函数式接口，基本够用，在 rt.jar 包的 java.util.function 目录下可以看到所有默认的函数式接口，大致分为几类

Function<T,R> T 作为输入，返回的 R 作为输出
Predicate<T> T 作为输入 ，返回 boolean 值的输出
Consumer<T> T 作为输入 ，没有输出
Supplier<R> 没有输入 , R 作为输出
BinaryOperator<T> 两个 T 作为输入 ，T 同样是输出
UnaryOperator<T> 是 Function 的变种 ，输入输出者是 T
其它的都是上面几种的各种扩展，只为更方便的使用，下面演示示例，你可以把其当成正常的接口使用，由用户使用 Lambda 传入。

// hello world 示例
Function<String,String> function = (x) -> {return x+"Function";};
System.out.println(function.apply("hello world"));  // hello world Function

UnaryOperator<String> unaryOperator = x -> x + 2;
System.out.println(unaryOperator.apply("9420-"));   // 9420-2

// 判断输入值是否为偶数示例
Predicate<Integer> predicate = (x) ->{return x % 2 == 0 ;};
System.out.println(predicate.test(1));              // false

// 这个没有返回值
Consumer<String> consumer = (x) -> {System.out.println(x);};
consumer.accept("hello world ");                    // hello world

// 这个没有输入 
Supplier<String> supplier = () -> {return "Supplier";};
System.out.println(supplier.get());                 // Supplier

// 找出大数
BinaryOperator<Integer> bina = (x, y) ->{return x > y ? x : y;};
bina.apply(1,2);                 
```
+ 方法引用
```
方法引用使得开发者可以直接引用现存的方法、Java类的构造方法或者实例对象。方法引用和Lambda表达式配合使用，使得java类的构造方法看起来紧凑而简洁，没有很多复杂的模板代码。

public static class Car {
    public static Car create( final Supplier< Car > supplier ) {
        return supplier.get();
    }              
 
    public static void collide( final Car car ) {
        System.out.println( "Collided " + car.toString() );
    }
 
    public void follow( final Car another ) {
        System.out.println( "Following the " + another.toString() );
    }
 
    public void repair() {   
        System.out.println( "Repaired " + this.toString() );
    }
}
第一种方法引用的类型是构造器引用，语法是Class::new，或者更一般的形式：Class::new。注意：这个构造器没有参数。

final Car car = Car.create( Car::new );
等价于

Car car = Car.create(() -> new Car());
第二种方法引用的类型是静态方法引用，语法是Class::static_method。注意：这个方法接受一个Car类型的参数。

cars.forEach( Car::collide );
forEach 原型为 forEach(Consumer<? super T> action) 使用的是 Consumer 只有参数，没有返回值；这个参数 T 就是 car 类型，因为是 cars.forEach 嘛，所以上面的方法引用等价于

cars.forEach(car -> Car.collide(car));
第三种方法引用的类型是某个类的成员方法的引用，语法是Class::method，注意，这个方法没有定义入参：

cars.forEach( Car::repair );
它等价于

cars.forEach(car -> car.repair());
```
+ 重复注解  
自从Java 5中引入注解以来，这个特性开始变得非常流行，并在各个框架和项目中被广泛使用。不过，注解有一个很大的限制是：在同一个地方不能多次使用同一个注解。Java 8打破了这个限制，引入了重复注解的概念，允许在同一个地方多次使用同一个注解。

在Java 8中使用 @Repeatable 注解定义重复注解，实际上，这并不是语言层面的改进，而是编译器做的一个trick，底层的技术仍然相同。可以利用下面的代码说明：
```
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Repeatable( Filters.class )
public @interface Filter {
    String value();
};

@Filter( "filter1" )
@Filter( "filter2" )
public interface Filterable {        
}

public static void main(String[] args) {
    for( Filter filter: Filterable.class.getAnnotationsByType( Filter.class ) ) {
        System.out.println( filter.value() );
    }
}

正如我们所见，这里的Filter类使用 @Repeatable(Filters.class) 注解修饰，而Filters是存放Filter注解的容器，编译器尽量对开发者屏蔽这些细节。这样，Filterable接口可以用两个Filter注解注释（这里并没有提到任何关于Filters的信息）。

另外，反射API提供了一个新的方法：getAnnotationsByType()，可以返回某个类型的重复注解，例如Filterable.class.getAnnoation(Filters.class)将返回两个Filter实例
```
+ 更好的类型推断  
```
Java 8编译器在类型推断方面有很大的提升，在很多场景下编译器可以推导出某个参数的数据类型，从而使得代码更为简洁。例子代码如下：

public class Value< T > {
    public static< T > T defaultValue() { 
        return null; 
    }
 
    public T getOrDefault( T value, T defaultValue ) {
        return ( value != null ) ? value : defaultValue;
    }
}
public class TypeInference {
    public static void main(String[] args) {
        final Value< String > value = new Value<>();
        value.getOrDefault( "22", Value.defaultValue() );
    }
}
参数 Value.defaultValue() 的类型由编译器推导得出，不需要显式指明。在Java 7中这段代码会有编译错误，除非使用Value.<String>defaultValue()
```
+ 拓宽注解的应用场景  
```
Java 8拓宽了注解的应用场景。现在，注解几乎可以使用在任何元素上：局部变量、接口类型、超类和接口实现类，甚至可以用在函数的异常定义上。下面是一些例子：

package com.javacodegeeks.java8.annotations;
 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
 
public class Annotations {
    @Retention( RetentionPolicy.RUNTIME )
    @Target( { ElementType.TYPE_USE, ElementType.TYPE_PARAMETER } )
    public @interface NonEmpty {        
    }
 
    public static class Holder< @NonEmpty T > extends @NonEmpty Object {
        public void method() throws @NonEmpty Exception {            
        }
    }
 
    @SuppressWarnings( "unused" )
    public static void main(String[] args) {
        final Holder< String > holder = new @NonEmpty Holder< String >();        
        @NonEmpty Collection< @NonEmpty String > strings = new ArrayList<>();        
    }
}
ElementType.TYPE_USER 和 ElementType.TYPE_PARAMETER 是Java 8新增的两个注解，用于描述注解的使用场景。Java 语言也做了对应的改变，以识别这些新增的注解。
```
+ Java 编译器的新特性 

Java 8 开始正式支持参数名称，终于不需要读 class 字节码来获取参数名称了，这对于经常使用反射的人特别有用。

在 Java8 这个特性默认是关闭的，需要开启参数才能获取参数名称：

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.1</version>
    <configuration>
        <compilerArgument>-parameters</compilerArgument>
        <source>1.8</source>
        <target>1.8</target>
    </configuration>
</plugin>

+ JVM 的新特性
使用Metaspace（JEP 122）代替持久代（PermGen space）。在JVM参数方面，使用-XX:MetaSpaceSize和-XX:MaxMetaspaceSize代替原来的-XX:PermSize和-XX:MaxPermSize。

+ Streams
Stream API 简化了集合的操作，并扩展了集合的分组，求和，mapReduce，flatMap ，排序等功能
```java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    //车架号
    private String vin;
    // 车主手机号
    private String phone;
    // 车主姓名
    private String name;
    // 所属车租车公司
    private Integer companyId;
    // 个人评分
    private Double score;
    //安装的设备列表imei,使用逗号分隔
    private String deviceNos;
}
准备一些车辆数据
static List<Vehicle> vehicles = new ArrayList<>();

@Before
public void init(){
    List<String> imeis = new ArrayList<>();
    for (int i = 0; i <5 ; i++) {
        List<String> singleVehicleDevices = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            String imei = RandomStringUtils.randomAlphanumeric(15);
            singleVehicleDevices.add(imei);
        }
        imeis.add(StringUtils.join(singleVehicleDevices,','));
    }
    vehicles.add(new Vehicle("KPTSOA1K67P081452","17620411498","9420",1,4.5,imeis.get(0)));
    vehicles.add(new Vehicle("KPTCOB1K18P057071","15073030945","张玲",2,1.4,imeis.get(1)));
    vehicles.add(new Vehicle("KPTS0A1K87P080237","19645871598","sanri1993",1,3.0,imeis.get(2)));
    vehicles.add(new Vehicle("KNAJC526975740490","15879146974","李种",1,3.9,imeis.get(3)));
    vehicles.add(new Vehicle("KNAJC521395884849","13520184976","袁绍",2,4.9,imeis.get(4)));
}
4.1.1 forEach 遍历Collection 数据
vehicles.forEach(vehicle -> System.out.println(vehicle));

//这样就可以遍历打印
vehicles.forEach(System.out::println);
4.1.2 forEach 遍历 Map 数据
Map<String,Integer> map = new HashMap<>();
map.put("a",1);map.put("b",2);map.put("c",3);

map.forEach((k,v) -> System.out.println("key:"+k+",value:"+v));
4.1.3 filter 数据过滤
// 去掉评分为 3 分以下的车
List<Vehicle> collect = vehicles.stream().filter(vehicle -> vehicle.getScore() >= 3).collect(Collectors.toList());
4.1.4 map 对象映射
对一个 List<Object> 大部分情况下，我们只需要列表中的某一列，或者需要把里面的每一个对象转换成其它的对象，这时候可以使用 map 映射,示例：

// 取出所有的车架号列表
 List<String> vins = vehicles.stream().map(Vehicle::getVin).collect(Collectors.toList());
4.1.5 groupBy 按照某个属性进行分组
// 按照公司 Id 进行分组
Map<Integer, List<Vehicle>> companyVehicles = vehicles.stream().collect(Collectors.groupingBy(Vehicle::getCompanyId));

// 按照公司分组求司机打分和
Map<Integer, Double> collect = vehicles.stream().collect(Collectors.groupingBy(Vehicle::getCompanyId, Collectors.summingDouble(Vehicle::getScore)));
4.1.6 sort 按照某个属性排序 ，及多列排序
// 单列排序 
vehicles.sort((v1,v2) -> v2.getScore().compareTo(v1.getScore()));

// 或使用 Comparator 类来构建比较器，流处理不会改变原列表，需要接收返回值才能得到预期结果
 List<Vehicle> collect = vehicles.stream().sorted(Comparator.comparing(Vehicle::getScore).reversed()).collect(Collectors.toList());

// 多列排序，score 降序，companyId 升序排列
List<Vehicle> collect = vehicles.stream().sorted(Comparator.comparing(Vehicle::getScore).reversed()
                .thenComparing(Comparator.comparing(Vehicle::getCompanyId)))
                .collect(Collectors.toList());
4.1.7 flatMap 扁平化数据处理
// 查出所有车绑定的所有设备
List<String> collect = vehicles.stream().map(vehicle -> {
    String deviceNos = vehicle.getDeviceNos();
    return StringUtils.split(deviceNos,',');
}).flatMap(Arrays::stream).collect(Collectors.toList());
flatMap 很适合 List<List> 或 List<object []> 这种结构，可以当成一个列表来处理；像上面的设备列表，在数据库中存储的结构就是以逗号分隔的数据，而车辆列表又是一个列表数据。

4.1.8 mapReduce 数据处理
// 对所有司机的总分求和
Double reduce = vehicles.stream().parallel().map(Vehicle::getScore).reduce(0d, Double::sum);
4.1.9 综合处理示例
// 总的分值
Double totalScore = vehicles.stream().parallel().map(Vehicle::getScore).reduce(0d, Double::sum);

// 查看每一个司机占的分值比重
List<String> collect = vehicles.stream()
    .mapToDouble(vehicle -> vehicle.getScore() / totalScore)
    .mapToLong(weight -> (long) (weight * 100))
    .mapToObj(percentage -> percentage + "%")
    .collect(Collectors.toList());
```
+ Optional

+ Date/Time API(JSR 310)
新的日期时间工具全部都在 java.time 及其子包中。

4.3.1 新 Date/Time API 设计原则
Java 8日期/时间API是 JSR-310 规范的实现，它的目标是克服旧的日期/时间API实现中所有的缺陷，新的日期/时间API的一些设计原则如下：

不变性：新的日期/时间API中，所有的类都是不可变的，这种设计有利于并发编程。
关注点分离：新的API将人可读的日期时间和机器时间（unix timestamp）明确分离，它为日期（Date）、时间（Time）、日期时间（DateTime）、时间戳（unix timestamp）以及时区定义了不同的类。
清晰：在所有的类中，方法都被明确定义用以完成相同的行为。举个例子，要拿到当前实例我们可以使用now()方法，在所有的类中都定义了format()和parse()方法，而不是像以前那样专门有一个独立的类。为了更好的处理问题，所有的类都使用了工厂模式和策略模式，一旦你使用了其中某个类的方法，与其他类协同工作并不困难。
实用操作：所有新的日期/时间API类都实现了一系列方法用以完成通用的任务，如：加、减、格式化、解析、从日期/时间中提取单独部分等操作。
可扩展性：新的日期/时间API是工作在ISO-8601日历系统上的，但我们也可以将其应用在非IOS的日历上。
4.3.2 常用类及其使用
时间大致可以分为三个部分：日期、时间、时区

其中日期又细分为年、月、日；时间又细分为时、分、秒

一般机器时间用从 1970-01-01T00:00 到现在的秒数来表示时间; 这里纠正大部分人犯的一个错误概念，时间戳指的是秒数，而不是毫秒数。

几乎所有的时间对象都实现了 Temporal 接口，所以接口参数一般都是 Temporal

Instant： 表示时间线上的一个点，参考点是标准的Java纪元(epoch)，即1970-01-01T00：00：00Z（1970年1月1日00:00 GMT）

LocalDate： 日期值对象如 2019-09-22

LocalTime：时间值对象如 21:25:36

LocalDateTime：日期+时间值对象

ZoneId：时区

ZonedDateTime：日期+时间+时区值对象

DateTimeFormatter：用于日期时间的格式化

Period：用于计算日期间隔

Duration：用于计算时间间隔

4.3.2.1 Instant 表示时间线上的一个点(瞬时)
// 测试执行一个 new 操作使用的时间(纳秒值)
Instant begin = Instant.now();
StreamMain streamMain = new StreamMain();
Instant end = Instant.now();
System.out.println(Duration.between(begin,end).toNanos());
4.3.2.2 LocalDate、LocalTime、LocalDateTime、ZonedDateTime 可以规为一组，用于表示时间的
// 可以使用 of 方法构建它们的实例,如下面创建了一个 2019-9-22 21:42:59 东八区 的时间对象 
LocalDate localDate = LocalDate.of(2019, Month.SEPTEMBER, 22);
LocalTime localTime = LocalTime.of(21, 42, 59);
LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());

// 获取现在的时间，这是一个静态方法
LocalDate now = LocalDate.now();

// 每个实例可以获取它们的 part 信息,如获取年 
int year = localDate.getYear();

// 可以修改 part 信息，这将返回一个新对象，如增加一年
LocalDate localDatePlus = localDate.plusYears(1);

// 设置 part 信息，也会返回新的对象，如设置为 2017 年 
LocalDate localDateWithYear = localDate.withYear(2017);

// 比较两个日期 isAfter,isBefore
boolean after = localDate.isAfter(LocalDate.now());

// 格式化日期时间
// yyyy-MM-dd
System.out.println(now.format(DateTimeFormatter.ISO_DATE));
// yyyy-MM-ddTHH:mm:ss
System.out.println(now.format(DateTimeFormatter.ISO_DATE_TIME));
// yyyy-MM-dd HH:mm:ss
System.out.println(now.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

// 日期解析 
System.out.println(LocalDate.parse("2019-09-22"));
System.out.println(LocalDateTime.parse("2019-09-22T21:05:22"));
System.out.println(LocalDateTime.parse("2019-09-22 21:05:22",DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
4.3.2.3 ZoneId 用来操作时区，它提供了获取所有时区和本地时区的方法
ZoneId zoneId = ZoneId.systemDefault();
Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
4.3.2.4 Period，Duration 可以视为一组，用于计算时间间隔
// 创建一个两周的间隔
Period periodWeeks = Period.ofWeeks(2);

// 一年三个月零二天的间隔
Period custom = Period.of(1, 3, 2);

// 一天的时长
Duration duration = Duration.ofDays(1);

// 计算2015/6/16 号到现在 2019/09/22 过了多久，它这个把间隔分到每个 part 了
LocalDate now = LocalDate.now();
LocalDate customDate = LocalDate.of(2015, 6, 16);
Period between = Period.between(customDate, now);
// 结果为 4:3:6 即过去了 4年3个月6天了
System.out.println(between.getYears()+":"+between.getMonths()+":"+between.getDays());

// 比较两个瞬时的时间间隔 
Instant begin = Instant.now();
Instant end = Instant.now();
Duration.between(begin,end);

// 同样可以修改 part 信息和设置 part 信息，都是返回新的对象来表示设置过的值，原来的对象不变
Period plusDays = between.plusDays(1);
Period withDays = between.withDays(4);
4.4 Base64
对于 Base64 终于不用引用第三方包了，使用 java 库就可以完成

// 编码
final String encoded = Base64.getEncoder().encodeToString( text.getBytes( StandardCharsets.UTF_8 ) );
// 解码
final String decoded = new String( Base64.getDecoder().decode( encoded ),StandardCharsets.UTF_8 );
4.5 JUC 工具包扩充
基于新增的lambda表达式和steam特性，为Java 8中为java.util.concurrent.ConcurrentHashMap类添加了新的方法来支持聚焦操作；另外，也为java.util.concurrentForkJoinPool类添加了新的方法来支持通用线程池操作（更多内容可以参考我们的并发编程课程）。

Java 8还添加了新的java.util.concurrent.locks.StampedLock类，用于支持基于容量的锁——该锁有三个模型用于支持读写操作（可以把这个锁当做是java.util.concurrent.locks.ReadWriteLock的替代者）。

在java.util.concurrent.atomic包中也新增了不少工具类，列举如下：

DoubleAccumulator
DoubleAdder
LongAccumulator
LongAdder
5. 新的工具
Java 8提供了一些新的命令行工具，这部分会讲解一些对开发者最有用的工具。

5.1 类依赖分析器：jdeps
deps是一个相当棒的命令行工具，它可以展示包层级和类层级的Java类依赖关系，它以.class文件、目录或者Jar文件为输入，然后会把依赖关系输出到控制台。

我们可以利用jedps分析下Spring Framework库，为了让结果少一点，仅仅分析一个JAR文件：org.springframework.core-3.0.5.RELEASE.jar。

jdeps org.springframework.core-3.0.5.RELEASE.jar
这个命令会输出很多结果，我们仅看下其中的一部分：依赖关系按照包分组，如果在classpath上找不到依赖，则显示"not found".

org.springframework.core-3.0.5.RELEASE.jar -> C:\Program Files\Java\jdk1.8.0\jre\lib\rt.jar
   org.springframework.core (org.springframework.core-3.0.5.RELEASE.jar)
      -> java.io                                            
      -> java.lang                                          
      -> java.lang.annotation                               
      -> java.lang.ref                                      
      -> java.lang.reflect                                  
      -> java.util                                          
      -> java.util.concurrent                               
      -> org.apache.commons.logging                         not found
      -> org.springframework.asm                            not found
      -> org.springframework.asm.commons                    not found
   org.springframework.core.annotation (org.springframework.core-3.0.5.RELEASE.jar)
      -> java.lang                                          
      -> java.lang.annotation                               
      -> java.lang.reflect                                  
      -> java.util
## JVM知识

### 什么情况下会发生栈内存溢出。

### JVM的内存结构，Eden和Survivor比例。

### JVM内存为什么要分成新生代，老年代，持久代。新生代中为什么要分为Eden和Survivor。

### JVM中一次完整的GC流程是怎样的，对象如何晋升到老年代，说说你知道的几种主要的JVM参数。

### 你知道哪几种垃圾收集器，各自的优缺点，重点讲下cms和G1，包括原理，流程，优缺点。

### 垃圾回收算法的实现原理。

### 当出现了内存溢出，你怎么排错。

### JVM内存模型的相关知识了解多少，比如重排序，内存屏障，happen-before，主内存，工作内存等。

### 简单说说你了解的类加载器，可以打破双亲委派么，怎么打破。

### 讲讲JAVA的反射机制。

### 你们线上应用的JVM参数有哪些。

### g1和cms区别,吞吐量优先和响应优先的垃圾收集器选择。

### 怎么打出线程栈信息。

### 请解释如下jvm参数的含义：

```
-server -Xms512m -Xmx512m -Xss1024K
-XX:PermSize=256m -XX:MaxPermSize=512m 
-XX:MaxTenuringThreshold=20XX:CMSInitiatingOccupancyFraction=80 
-XX:+UseCMSInitiatingOccupancyOnly。
```
## 开源框架知识
### 简单讲讲tomcat结构，以及其类加载器流程，线程模型等。
### tomcat如何调优，涉及哪些参数 。
### 讲讲Spring加载流程。
### Spring AOP的实现原理。
### 讲讲Spring事务的传播属性。
### Spring如何管理事务的。
### Spring怎么配置事务（具体说出一些关键的xml 元素）。
### 说说你对Spring的理解，非单例注入的原理？它的生命周期？循环注入的原理，aop的实现原理，说说aop中的几个术语，它们是怎么相互工作的。
### Springmvc 中DispatcherServlet初始化过程。
### netty的线程模型，netty如何基于reactor模型上实现的。
### 为什么选择netty。
### 什么是TCP粘包，拆包。解决方式是什么。
### netty的fashwheeltimer的用法，实现原理，是否出现过调用不够准时，怎么解决。
### netty的心跳处理在弱网下怎么办。
### netty的通讯协议是什么样的。
### springmvc用到的注解，作用是什么，原理。
### springboot启动机制。

## 操作系统

### Linux系统下你关注过哪些内核参数，说说你知道的。
### Linux下IO模型有几种，各自的含义是什么。
### epoll和poll有什么区别。
### 平时用到哪些Linux命令。
### 用一行命令查看文件的最后五行。
### 用一行命令输出正在运行的java进程。
### 介绍下你理解的操作系统中线程切换过程。
### 进程和线程的区别。
### top 命令之后有哪些内容，有什么作用。
### 线上CPU爆高，请问你如何找到问题所在。

## 多线程
### 多线程的几种实现方式，什么是线程安全。
### volatile的原理，作用，能代替锁么。
### 画一个线程的生命周期状态图。
### sleep和wait的区别。
### sleep和sleep(0)的区别。
### Lock与Synchronized的区别 。
### synchronized的原理是什么，一般用在什么地方(比如加在静态方法和非静态方法的区别，静
### 态方法和非静态方法同时执行的时候会有影响吗)，解释以下名词：重排序，自旋锁，偏向锁，轻
### 量级锁，可重入锁，公平锁，非公平锁，乐观锁，悲观锁。
### 用过哪些原子类，他们的原理是什么。
### JUC下研究过哪些并发工具，讲讲原理。
### 用过线程池吗，如果用过，请说明原理，并说说newCache和newFixed有什么区别，构造函数的各个参数的含义是什么，比如coreSize，maxsize等。
### 线程池的关闭方式有几种，各自的区别是什么。
### 假如有一个第三方接口，有很多个线程去调用获取数据，现在规定每秒钟最多有10个线程同时调用它，如何做到。

### spring的controller是单例还是多例，怎么保证并发的安全。
### 用三个线程按顺序循环打印abc三个字母，比如abcabcabc。
### ThreadLocal用过么，用途是什么，原理是什么，用的时候要注意什么。
### 如果让你实现一个并发安全的链表，你会怎么做。
### 有哪些无锁数据结构，他们实现的原理是什么。
### 讲讲java同步机制的wait和notify。
### CAS机制是什么，如何解决ABA问题。
### 多线程如果线程挂住了怎么办。
### countdowlatch和cyclicbarrier的内部原理和用法，以及相互之间的差别(比如countdownlatch的await方法和是怎么实现的)。

###对AbstractQueuedSynchronizer了解多少，讲讲加锁和解锁的流程，独占锁和公平所加锁有什么不同。

### 使用synchronized修饰静态方法和非静态方法有什么区别。
### 简述ConcurrentLinkedQueue和LinkedBlockingQueue的用处和不同之处。
### 导致线程死锁的原因？怎么解除线程死锁。
### 非常多个线程（可能是不同机器），相互之间需要等待协调，才能完成某种工作，问怎么设计这种协调方案。
### 用过读写锁吗，原理是什么，一般在什么场景下用。
###开启多个线程，如果保证顺序执行，有哪几种实现方式，或者如何保证多个线程都执行完再拿到结果。

### 延迟队列的实现方式，delayQueue和时间轮算法的异同。

## TCP与HTTP

### http1.0和http1.1有什么区别。
### TCP三次握手和四次挥手的流程，为什么断开连接要4次,如果握手只有两次，会出现什么。
### TIME_WAIT和CLOSE_WAIT的区别。
### 说说你知道的几种HTTP响应码，比如200, 302, 404。
### 当你用浏览器打开一个链接（如：http://www.javastack.cn）的时候，计算机做了哪些工作步骤。
### TCP/IP如何保证可靠性，说说TCP头的结构。
### 如何避免浏览器缓存。
### 如何理解HTTP协议的无状态性。
### 简述Http请求get和post的区别以及数据包格式。
### HTTP有哪些method
### 简述HTTP请求的报文格式。
### HTTP的长连接是什么意思。
### HTTPS的加密方式是什么，讲讲整个加密解密流程。
### Http和https的三次握手有什么区别。
### 什么是分块传送。
### Session和cookie的区别。

## 架构设计与分布式

### 用java自己实现一个LRU。
### 分布式集群下如何做到唯一序列号。
### 设计一个秒杀系统，30分钟没付款就自动关闭交易。
### 如何使用redis和zookeeper实现分布式锁？有什么区别优缺点，会有什么问题，分别适用什么场景。（延伸：如果知道redlock，讲讲他的算法实现，争议在哪里）

### 如果有人恶意创建非法连接，怎么解决。
### 分布式事务的原理，优缺点，如何使用分布式事务，2pc 3pc 的区别，解决了哪些问题，还有哪些问题没解决，如何解决，你自己项目里涉及到分布式事务是怎么处理的。

### 什么是一致性hash。
### 什么是restful，讲讲你理解的restful。
### 如何设计一个良好的API。
### 如何设计建立和保持100w的长连接。
### 解释什么是MESI协议(缓存一致性)。
### 说说你知道的几种HASH算法，简单的也可以。
### 什么是paxos算法， 什么是zab协议。
### 一个在线文档系统，文档可以被编辑，如何防止多人同时对同一份文档进行编辑更新。

### 线上系统突然变得异常缓慢，你如何查找问题。
### 说说你平时用到的设计模式。
### Dubbo的原理，有看过源码么，数据怎么流转的，怎么实现集群，负载均衡，服务注册和发现，重试转发，快速失败的策略是怎样的 。

### 一次RPC请求的流程是什么。
### 自己实现过rpc么，原理可以简单讲讲。Rpc要解决什么问题。
### 异步模式的用途和意义。
### 编程中自己都怎么考虑一些设计原则的，比如开闭原则，以及在工作中的应用。
### 设计一个社交网站中的“私信”功能，要求高并发、可扩展等等。 画一下架构图。
### MVC模式，即常见的MVC框架。
### 聊下曾经参与设计的服务器架构并画图，谈谈遇到的问题，怎么解决的。
### 应用服务器怎么监控性能，各种方式的区别。
### 如何设计一套高并发支付方案，架构如何设计。
### 如何实现负载均衡，有哪些算法可以实现。
### Zookeeper的用途，选举的原理是什么。
### Zookeeper watch机制原理。
### Mybatis的底层实现原理。
### 请思考一个方案，实现分布式环境下的countDownLatch。
### 后台系统怎么防止请求重复提交。
### 描述一个服务从发布到被消费的详细过程。
### 讲讲你理解的服务治理。
### 如何做到接口的幂等性。
### 如何做限流策略，令牌桶和漏斗算法的使用场景。
### 什么叫数据一致性，你怎么理解数据一致性。
### 分布式服务调用方，不依赖服务提供方的话，怎么处理服务方挂掉后，大量无效资源请求的浪费，如果只是服务提供方吞吐不高的时候该怎么做，如果服务挂了，那么一会重启，该怎么做到最小的资源浪费，流量半开的实现机制是什么。

### dubbo的泛化调用怎么实现的，如果是你，你会怎么做。
###远程调用会有超时现象，如果做到优雅的控制，JDK自带的超时机制有哪些，怎么实现的。

## 算法

### 10亿个数字里里面找最小的10个。
### 有1亿个数字，其中有2个是重复的，快速找到它，时间和空间要最优。
### 2亿个随机生成的无序整数,找出中间大小的值。
### 给一个不知道长度的（可能很大）输入字符串，设计一种方案，将重复的字符排重。
### 遍历二叉树。
### 有3n+1个数字，其中3n个中是重复的，只有1个是不重复的，怎么找出来。
### 写一个字符串（如：www.javastack.cn）反转函数。
### 常用的排序算法，快排，归并、冒泡。 快排的最优时间复杂度，最差复杂度。冒泡排序的优化方案。
### 二分查找的时间复杂度，优势。
### 一个已经构建好的TreeSet，怎么完成倒排序。
### 什么是B+树，B-树，列出实际的使用场景。
### 一个单向链表，删除倒数第N个数据。
### 200个有序的数组，每个数组里面100个元素，找出top20的元素。
### 单向链表，查找中间的那个元素。

## 数据库知识

### 数据库隔离级别有哪些，各自的含义是什么，MYSQL默认的隔离级别是是什么。
### 什么是幻读。
### MYSQL有哪些存储引擎，各自优缺点。
### 高并发下，如何做到安全的修改同一行数据。
### 乐观锁和悲观锁是什么，INNODB的标准行级锁有哪2种，解释其含义。
### SQL优化的一般步骤是什么，怎么看执行计划，如何理解其中各个字段的含义。
### 数据库会死锁吗，举一个死锁的例子，mysql怎么解决死锁。
### MYsql的索引原理，索引的类型有哪些，如何创建合理的索引，索引如何优化。
### 聚集索引和非聚集索引的区别。
### select for update 是什么含义，会锁表还是锁行或是其他。
### 为什么要用Btree实现，它是怎么分裂的，什么时候分裂，为什么是平衡的。
### 数据库的ACID是什么。
### 某个表有近千万数据，CRUD比较慢，如何优化。
### Mysql怎么优化table scan的。
### 如何写sql能够有效的使用到复合索引。
### mysql中in 和exists 区别。
### 数据库自增主键可能的问题。
### MVCC的含义，如何实现的。
### 你做过的项目里遇到分库分表了吗，怎么做的，有用到中间件么，比如sharding jdbc等,他们的原理知道么。
### MYSQL的主从延迟怎么解决。

## 消息队列
### 消息队列的使用场景。
### 消息的重发，补充策略。
### 如何保证消息的有序性。
### 用过哪些MQ，和其他mq比较有什么优缺点，MQ的连接是线程安全的吗，你们公司的MQ服务架构怎样的。
### MQ系统的数据如何保证不丢失。
### rabbitmq如何实现集群高可用。
### kafka吞吐量高的原因。
### kafka 和其他消息队列的区别，kafka 主从同步怎么实现。
### 利用mq怎么实现最终一致性。
### 使用kafka有没有遇到什么问题，怎么解决的。
### MQ有可能发生重复消费，如何避免，如何做到幂等。
### MQ的消息延迟了怎么处理，消息可以设置过期时间么，过期了你们一般怎么处理。

## 缓存
### 常见的缓存策略有哪些，如何做到缓存(比如redis)与DB里的数据一致性，你们项目中用到了什么缓存系统，如何设计的。

### 如何防止缓存击穿和雪崩。
### 缓存数据过期后的更新如何设计。
### redis的list结构相关的操作。
### Redis的数据结构都有哪些。
### Redis的使用要注意什么，讲讲持久化方式，内存设置，集群的应用和优劣势，淘汰策略等。
### redis2和redis3的区别，redis3内部通讯机制。
### 当前redis集群有哪些玩法，各自优缺点，场景。
### Memcache的原理，哪些数据适合放在缓存中。
### redis和memcached 的内存管理的区别。
### Redis的并发竞争问题如何解决，了解Redis事务的CAS操作吗。
### Redis的选举算法和流程是怎样的。
### redis的持久化的机制，aof和rdb的区别。
### redis的集群怎么同步的数据的。
### 知道哪些redis的优化操作。
### Reids的主从复制机制原理。
### Redis的线程模型是什么。
### 请思考一个方案，设计一个可以控制缓存总体大小的自动适应的本地缓存。
### 如何看待缓存的使用（本地缓存，集中式缓存），简述本地缓存和集中式缓存和优缺点。
### 本地缓存在并发使用时的注意事项。

## 搜索

### elasticsearch了解多少，说说你们公司es的集群架构，索引数据大小，分片有多少，以及一些调优手段 。elasticsearch的倒排索引是什么。
### elasticsearch 索引数据多了怎么办，如何调优，部署。
### elasticsearch是如何实现master选举的。
### 详细描述一下Elasticsearch索引文档的过程。
### 详细描述一下Elasticsearch搜索的过程。
### Elasticsearch在部署时，对Linux的设置有哪些优化方法？
### lucence内部结构是什么。
