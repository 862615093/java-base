

* 掌握五种单例模式的实现方式
* 理解为何 DCL 实现时要使用 volatile 修饰静态变量
* 了解 jdk 中用到单例的场景 (单例模式一般在项目中不会用到，只会在jdk库里看到)



**JDK 中单例的体现**

    * Runtime 体现了饿汉式单例

    * Console 体现了双检锁懒汉式单例

    * Collections 中的 EmptyNavigableSet 内部类懒汉式单例

    * ReverseComparator.REVERSE_ORDER 内部类懒汉式单例

    * Comparators.NaturalOrderComparator.INSTANCE 枚举饿汉式单例