package se;

public class Equals {

//    ==和equals区别
//    若为基本数据类型,==号比较的是值是否相同,若为引用数据类型,比较的则是对象的地址值是否相同
//    equals()比较的是两个对象的内容是否相等(equals不能用于基本数据类型,如果没有对equals进行重写,则比较的是引用类型的变量所指的地址值)
//
//    判断int和integer是否相同
//    int和int之间,用==比较,肯定为true,基本数据类型没有equals方法
//    int和integer比较,Integer会自动拆箱(调用intVale()),==和equals结果都为true
//    int和new Integer之间比较,Integer会自动拆箱,调用intValue方法,所以==和equals结果都为true
//
//    Integer和Integer之间
//    1)Integer和Integer 直接比较:
//    会进行自动装箱,所以当值在[-128,127]时,在这个区间内赋值不会创建新的对象,而是直接从缓存中获取已经创建好的Integer对象. 而当大于这个区间的时候,就会创建新的对象;
//    所以在进行==比较的时候,在[-127,128]区间内的值比较结果为true;大于该区间的值比较结果为false;
//    2)当Integer与Integer进行equals比较时,由于Integer重写了equals方法,比较的是内容,所以结果为true;
//    3)Integer和new Integer比较时:
//      会进行自动装箱,new integer会创建新的对象,存储在堆中,而Integer在[-127,128]中是从缓存中取,不在这个范围重新new;
//      所以Integer和new Integer进行 == 比较内存地址无论如何结果都为false,若进行equals比较,结果为true
//    4)new Integer 和 new Integer之间比较:
//    进行 == 比较的时候结果为false,进行equals比较的时候结果为true

    public static void main(String[] args) {
        int a1 = 10;
        int b1 = 10;
        Integer a2 = 10;
        Integer b2 = 10;
        Integer a3 = new Integer(10);
        Integer b3 = new Integer(10);
        Integer a4 = 200;
        Integer b4 = 200;
        int b5 = 10;
        int b6 = 200;
        Integer a7 = 250;
        Integer b7 = new Integer(250);
        Integer a8 = 20;
        Integer b8 = new Integer(20);
        Integer a9 = 20;
        Integer b9 = 20;
        Integer a10 = 200;
        Integer b10 = 200;

        System.out.println(a1 == b1); //true
        System.out.println(a2 == b2); //true
        System.out.println(a3 == b3); //false
        System.out.println(a4 == b4); //false

        System.out.println("===================自动拆箱=====================");
//        public int intValue() {
//            return value;
//        }

        System.out.println(a2 == b5); //true  自动拆箱 比较值 --> a2.intValue();
        System.out.println(a4 == b6); //true  自动拆箱 比较值

        System.out.println("==================自动装箱======================");
//        public static Integer valueOf(int i) {
//            if (i >= Integer.IntegerCache.low && i <= Integer.IntegerCache.high)
//                return Integer.IntegerCache.cache[i + (-Integer.IntegerCache.low)];
//            return new Integer(i);
//        }

//        自动装箱，等价于 Integer a7 = Integer.valueOf(250) 。
//        因此，a7 无论是否直接使用的是缓存中的对象。而对于Integer b7 = new Integer(250) 会直接创建新的对象。
//        == 对于引用类型比较的是内存地址，无论如何都是false
        System.out.println(a7 == b7); //false
        System.out.println(a8 == b8); //false
//        自动装箱，等价于 Integer a9 = Integer.valueOf(20) 。
//        == 对于引用类型比较的是内存地址，因此，a9, b9 判断是否从缓存中取 主要看是否直接使用的是缓存中的对象。
        System.out.println(a9 == b9); //true
        System.out.println(a10 == b10); //false

        System.out.println("========================================");
        System.out.println(a2.equals(10)); //true
        System.out.println(a2.equals(b2)); //true
        System.out.println(a3.equals(b3)); //true

    }
}
