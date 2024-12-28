
【步骤总结】

1、得到 SpringApplicationRunListeners 事件发布器
    发布 Application Starting 事件 1️⃣

2、封装启动 args

3、准备 Environment 添加命令行参数

4、ConfigurationPropertySources 处理
    发布 Application Environment 已准备事件 2️⃣

5、通过 EnvironmentPostProcessorApplicationListener 进行 env 后处理
    application.properties 由 StandardConfigDataLocationResolver 解析

6、绑定 spring.main 到 SpringApplication 对象

7、打印 Banner

8、创建容器

9、准备容器
    发布 Application Context 已初始化事件 3️⃣

10、加载 Bean 定义
    发布 Application Prepared 事件 4️⃣

11、refresh 容器
    发布 Application Started 事件 5️⃣

12、执行 Runner （ApplicationRunner、CommandLineRunner 接口）
    发布 Application Ready 事件 6️⃣
    这其中有异常，发布 Application Failed 事件 7️⃣

