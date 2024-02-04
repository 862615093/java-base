package com.ww.frame.springboot.importAnnotation;



import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;

//@Import({TestBean1.class}) //1.声明一个bean
//@Import({TestConfig.class}) //2.导入@Configuration注解的配置类
//@Import({TestImportSelector.class}) //3.导入ImportSelector的实现类
//@Import({TestImportBeanDefinitionRegistrar.class}) //4.导入ImportBeanDefinitionRegistrar的实现类
//@Configuration
public class AppConfig {

}

class TestImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(TestBean4.class);
        registry.registerBeanDefinition("TestBean4", rootBeanDefinition);
    }
}

class TestImportSelector implements ImportSelector {
    @NotNull
    @Override
    public String[] selectImports(@NotNull AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.ww.frame.springboot.importAnnotation.TestBean3"};
    }
}

@Configuration
class TestConfig {
    @Bean(name = "bean2")
    public TestBean3 getTestBean2(){
        return new TestBean3();
    }
}

class TestBean4 {
}

class TestBean3 {
}

class TestBean2 {
}

class TestBean1 {
}