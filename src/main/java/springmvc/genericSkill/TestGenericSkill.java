package springmvc.genericSkill;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.ResolvableType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
public class TestGenericSkill {

    //获取 BaseDao 子类泛型参数：spring api 方便一些
    public static void main(String[] args) {

        // 1. java api
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");

        // 带有泛型信息的父类信息
        Type teacherDaoType = TeacherDao.class.getGenericSuperclass();
        System.out.println("TeacherDao type: " + teacherDaoType);
        System.out.println("TeacherDao type class: " + teacherDaoType.getClass());

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");

        Type employeeDaoType = EmployeeDao.class.getGenericSuperclass();
        System.out.println("EmployeeDao type: " + employeeDaoType);
        System.out.println("EmployeeDao type class: " + employeeDaoType.getClass());

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");

        // 有泛型参数的 Type 对象才是 ParameterizedType 类型
        if (teacherDaoType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) teacherDaoType;
            System.out.println(parameterizedType.getActualTypeArguments()[0]);
        }

        // 2. spring api 1
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
        Class<?> t = GenericTypeResolver.resolveTypeArgument(TeacherDao.class, BaseDao.class);
        System.out.println(t);

        // 3. spring api 2
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(ResolvableType.forClass(StudentDao.class).getSuperType().getGeneric().resolve());
    }


}
