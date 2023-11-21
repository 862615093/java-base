package com.ww.streamStudy.stream;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java8中有两大最为重要的改变。第一个是 Lambda 表达式；另外一个则是 Stream API(java.util.stream.*)。
 * Stream 是 Java8 中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。
 * 使用Stream API 对集合数据进行操作，就类似于使用 SQL 执行的数 据库查询。也可以使用 Stream API 来并行执行操作。简而言之，
 * Stream API 提供了一种高效且易于使用的处理数据的方式。
 * <p>
 * <p>
 * 流(Stream) 到底是什么呢？ //是数据渠道，用于操作数据源（集合，数组等）所生成的元素序列。(集合->数据(内存), stream->计算(cpu))
 * 是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。
 * “集合讲的是数据，流讲的是计算！”
 * 注意：
 * ①Stream 自己不会存储元素。
 * ②Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream。
 * ③Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。
 * <p>
 * <p>
 * Stream 的操作三个步骤
 * 1.创建 Stream
 * 一个数据源（如：集合、数组），获取一个流
 * 2.中间操作
 * 一个中间操作链，对数据源的数据进行处理
 * 3.终止操作(终端操作)
 * 一个终止操作，执行中间操作链，并产生结果
 * <p>
 * <p>
 * Java8 中的 Collection 接口被扩展，提供了
 * 两个获取流的方法：
 * 1.default Stream<E> stream() : 返回一个顺序流
 * 2.default Stream<E> parallelStream() : 返回一个并行流
 * <p>
 * <p>
 * Java8 中的 Arrays 的静态方法 stream() 可
 * 以获取数组流：
 * 1.static <T> Stream<T> stream(T[] array): 返回一个流
 * <p>
 * <p>
 * 由值创建流
 * 可以使用静态方法 Stream.of(), 通过显示值
 * 创建一个流。它可以接收任意数量的参数。
 * 1.public static<T> Stream<T> of(T... values) : 返回一个流
 * <p>
 * <p>
 * 由函数创建流：创建无限流
 * 可以使用静态方法 Stream.iterate() 和 Stream.generate(), 创建无限流。
 * 1.迭代
 * public static<T> Stream<T> iterate(final T seed, final
 * UnaryOperator<T> f)
 * 2.生成
 * public static<T> Stream<T> generate(Supplier<T> s) :
 */
public class StreamApiTest {

    static {

    }

    //    创建 Stream
    @Test
    public void test1() {
        //1.Collection 提供了两个方法  stream() 与 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); //获取一个顺序流
        Stream<String> parallelStream = list.parallelStream(); //获取一个并行流

        //2. 通过 Arrays 中的 stream() 获取一个数组流
        Integer[] nums = new Integer[10];
        Stream<Integer> stream1 = Arrays.stream(nums);

        //3. 通过 Stream 类中静态方法 of()
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5, 6);

        //4. 创建无限流
        //迭代
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(10);
        stream3.forEach(System.out::println);

        //生成
        Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
        stream4.forEach(System.out::println);
    }


    public List<Employee> getEmps() {
        return Arrays.asList(
                new Employee(101, "张三", 18, 9999.99),
                new Employee(102, "李四", 59, 6666.66),
                new Employee(103, "王五", 28, 3333.33),
//                new Employee(104, "赵六", 8, 7777.77),
//                new Employee(104, "赵六", 8, 7777.77),
                new Employee(104, "赵六", 8, 7777.77),
                new Employee(105, "田七", 38, 7777.77));
    }


    /* stream中间操作 */

//    筛选与切片
//    1.filter(Predicate p) 接收 Lambda ， 从流中排除某些元素。 true-> 不排除，元素返回  false -> 排出，元素不返回
//    2.distinct() 筛选，通过流所生成元素的 hashCode() 和 equals() 去 除重复元素
//    3.limit(long maxSize) 截断流，使其元素不超过给定数量。
//    4.skip(long n) 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补

    @Test
    public void filterTest() {
        List<Employee> emps = getEmps();
        Object[] objects = emps.stream().filter(new Predicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return true;
            }
        }).toArray();
        System.out.println(Arrays.toString(objects));
    }

    @Test
    public void distinctTest() {
        List<Employee> emps = getEmps();
        Object[] objects = emps.stream().distinct().toArray();
        System.out.println(Arrays.toString(objects));
    }

    @Test
    public void limitTest() {
        List<Employee> emps = getEmps();
        Object[] objects = emps.stream().limit(2).toArray();
        System.out.println(Arrays.toString(objects));
    }

    @Test
    public void skipTest() {
        List<Employee> emps = getEmps();
        Object[] objects = emps.stream().skip(1).toArray();
        System.out.println(Arrays.toString(objects));
    }


//    映射
//    1.map(Function f) 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
//    2.mapToDouble(ToDoubleFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。
//    3.mapToInt(ToIntFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 IntStream。
//    4.mapToLong(ToLongFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 LongStream。
//    5.flatMap(Function f) 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流

    @Test
    public void mapTest() {
        List<Employee> emps = getEmps();
//        Object[] objects = emps.stream().map(new Function<Employee, Object>() {
//            @Override
//            public Object apply(Employee employee) {
//                return 1;
//            }
//        }).toArray();
        Object[] objects = emps.stream().map(employee -> {
            return 1;
        }).toArray();
        System.out.println(Arrays.toString(objects));
    }

    @Test
    public void mapToIntTest() {
        List<Employee> emps = getEmps();
        int[] ints = emps.stream().mapToInt(new ToIntFunction<Employee>() {
            @Override
            public int applyAsInt(Employee value) {
                return 0;
            }
        }).toArray();
        System.out.println(Arrays.toString(ints));
    }

    @Test
    public void flatMapTest() {
        List<String> strList = Arrays.asList("aa", "bb", "cc");

        System.out.println("--------------------map----------------------");
        Stream<Stream<Character>> streamStream = strList.stream()
                .map(new Function<String, Stream<Character>>() {
                    @Override
                    public Stream<Character> apply(String s) {
                        return filterCharacter(s);
                    }
                });
        streamStream.forEach(e -> e.forEach(System.out::println));
        System.out.println("-------------------flatMap-----------------------");
        Stream<Character> characterStream = strList.stream()
                .flatMap(new Function<String, Stream<? extends Character>>() {
                    @Override
                    public Stream<? extends Character> apply(String s) {
                        return filterCharacter(s);
                    }
                });
        System.out.println(characterStream);
//        characterStream.forEach(new Consumer<Character>() {
//            @Override
//            public void accept(Character character) {
//                System.out.println(character);
//            }
//        });
        characterStream.forEach(System.out::println);
    }

    //将字符串 转换成 字符流
    public static Stream<Character> filterCharacter(String str) {
        ArrayList<Character> list = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

    //    排序
//    1.sorted() 产生一个新流，其中按自然顺序排序
//    2.sorted(Comparator comp) 产生一个新流，其中按比较器顺序排序
    @Test
    public void sortedTest() {
        List<Employee> emps = getEmps();
        emps.stream().sorted(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o2.getAge() - o1.getAge();
            }
        }).forEach(new Consumer<Employee>() {
            @Override
            public void accept(Employee employee) {
                System.out.println(employee);
            }
        });
    }

    /* Stream 的终止操作 */

//    查找与匹配
//    anyMatch(Predicate p) 检查是否至少匹配一个元素
//    findAny() 返回当前流中的任意元素
//    allMatch(Predicate p) 检查是否匹配所有元素
//    noneMatch(Predicate p) 检查是否没有匹配所有元素
//    findFirst() 返回第一个元素


    @Test
    public void anyMatchTest() {
        List<Employee> emps = getEmps();
        boolean b = emps.stream().anyMatch(employee -> {
            System.out.println(employee);
            if (employee.getAge() > 100) {
                return true;
            }
            return false;
        });
        System.out.println(b);
    }


    @Test
    public void noneMatchTest() {
        List<Employee> emps = getEmps();
        boolean b = emps.stream().noneMatch(employee -> {
            System.out.println(employee);
            if (employee.getAge() > 100) {
                return true;
            }
            return false;
        });
        System.out.println(b);
    }

    @Test
    public void findAnyTest() {
        List<Employee> emps = getEmps();
        Optional<Employee> any = emps.stream().findAny();
        System.out.println(any.get());
    }

    @Test
    public void findFirstTest() {
        List<Employee> emps = getEmps();
        Optional<Employee> first = emps.stream().findFirst();
        System.out.println(first.get());
    }

//    count() 返回流中元素总数
//    max(Comparator c) 返回流中最大值
//    min(Comparator c) 返回流中最小值
//    forEach(Consumer c) 内部迭代(使用 Collection 接口需要用户去做迭 代，称为外部迭代。相反，Stream API 使用内部迭代——它帮你把迭代做了)
    @Test
    public void maxTest() {
        List<Employee> emps = getEmps();
        Optional<Employee> max = emps.stream().max(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return (int) (o1.getSalary() - o2.getSalary());
            }
        });
        System.out.println(max.get());
    }


//    归约
//    reduce(T iden, BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。 返回 T
//    reduce(BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。  返回 Optional<T>

    @Test
    public void reduceTest() {
//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
//
//        Integer reduce = list.stream().reduce(10, new BinaryOperator<Integer>() {
//            @Override
//            public Integer apply(Integer i1, Integer i2) {
//                return i1 + i2;
//            }
//        });
//        System.out.println(reduce);


//        Integer sum = list.stream().reduce(0, (x, y) -> x + y);
//
//        System.out.println(sum);
//
        System.out.println("----------------------------------------");

        List<Employee> emps = getEmps();
        Optional<Double> op = emps.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(op.get());
    }

//    收集
//    collect(Collector c) 将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
//    1.toList List<T> 把流中元素收集到List
//    List<Employee> emps= list.stream().collect(Collectors.toList());
//    2.toSet Set<T> 把流中元素收集到Set
//    Set<Employee> emps= list.stream().collect(Collectors.toSet());
//    3.toCollection Collection<T> 把流中元素收集到创建的集合
//    Collection<Employee>emps=list.stream().collect(Collectors.toCollection(ArrayList::new));


//    4.counting Long 计算流中元素的个数
//    long count = list.stream().collect(Collectors.counting());
//    5.summingInt Integer 对流中元素的整数属性求和
//    inttotal=list.stream().collect(Collectors.summingInt(Employee::getSalary));
//    6.averagingInt Double 计算流中元素Integer属性的平均值
//    doubleavg= list.stream().collect(Collectors.averagingInt(Employee::getSalary));
//    7.summarizingInt IntSummaryStatistics 收集流中Integer属性的统计值。
//    如：平均值 IntSummaryStatisticsiss= list.stream().collect(Collectors.summarizingInt(Employee::getSalary));


//    8.joining String 连接流中每个字符串
//    String str= list.stream().map(Employee::getName).collect(Collectors.joining());
//    9.maxBy Optional<T> 根据比较器选择最大值
//    Optional<Emp>max= list.stream().collect(Collectors.maxBy(comparingInt(Employee::getSalary)));
//    10.minBy Optional<T> 根据比较器选择最小值
//    Optional<Emp> min = list.stream().collect(Collectors.minBy(comparingInt(Employee::getSalary)));
//    11.reducing 归约产生的类型 从一个作为累加器的初始值开始，利用BinaryOperator与流中元素逐个结合，从而归约成单个值
//    inttotal=list.stream().collect(Collectors.reducing(0, Employee::getSalar, Integer::sum));


//    12.collectingAndThen 转换函数返回的类型 包裹另一个收集器，对其结果转换函数
//    inthow= list.stream().collect(Collectors.collectingAndThen(Collectors.toList(), List::size));
//    13.groupingBy Map<K, List<T>> 根据某属性值对流分组，属性为K，结果为V
//    Map<Emp.Status, List<Emp>> map= list.stream().collect(Collectors.groupingBy(Employee::getStatus));
//    14.partitioningBy Map<Boolean, List<T>> 根据true或false进行分区
//    Map<Boolean,List<Emp>>vd= list.stream().collect(Collectors.partitioningBy(Employee::getManage));


    @Test
    public void calcTest(){
        List<Employee> emps = getEmps();
        //1.集合总数
        Long count = emps.stream()
                .collect(Collectors.counting());
        System.out.println(count);
        System.out.println("-----------------------------");
        //2.平均值
        Double avg = emps.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);
        System.out.println("-----------------------------");
        //3.总和
        Integer sum = emps.stream()
                .collect(Collectors.summingInt(Employee::getAge));
        System.out.println(sum);
        System.out.println("-----------------------------");
        //4.最大值
        Optional<Employee> max = emps.stream()
                .collect(Collectors
                        .maxBy((e1, e2) -> e1.getAge() - e2.getAge()));
        System.out.println(max.get());
        System.out.println("-----------------------------");
        //5.最小值
        Optional<Employee> min = emps.stream()
                .collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(min.get());
    }

    @Test
    public void joinTest() {
        List<Employee> emps = getEmps();
        String collect = emps.stream().map(Employee::getName).collect(Collectors.joining("," , "----", "----")); // ----张三,李四,王五,赵六,田七----
//        String collect = emps.stream().map(new Function<Employee, String>() {
//            @Override
//            public String apply(Employee employee) {
//                return employee.getName();
//            }
//        }).collect(Collectors.joining());
        System.out.println(collect);
    }

    //分组
    @Test
    public void groupingByTest(){
        List<Employee> emps = getEmps();
        Map<Double, List<Employee>> collect = emps.stream()
                .collect(Collectors.groupingBy(e -> {
                    return e.getSalary();
                }));

        System.out.println(collect);
    }

    //多级分组
    @Test
    public void groupingByTest2(){
        List<Employee> emps = getEmps();
        Map<Double, Map<String, List<Employee>>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getSalary, Collectors.groupingBy((e) -> {
                    if(e.getAge() >= 60)
                        return "老年";
                    else if(e.getAge() >= 35)
                        return "中年";
                    else
                        return "成年";
                })));
        System.out.println(map);
    }

    //分区
    @Test
    public void partitioningByTest(){
        List<Employee> emps = getEmps();
        Map<Boolean, List<Employee>> map = emps.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
        System.out.println(map);
    }

}
