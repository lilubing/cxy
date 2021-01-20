package com.llb.cxy.configuration;

import java.lang.reflect.Method;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.llb.cxy.core.exception.LlbPathVariableCheckException;
import org.reflections.Reflections;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.config
 * @Description: PathVariable检查
 * @ClassName: PathVariableCheck
 * @date 2021-01-20 上午8:54
 * @ProjectName cxy
 * @Version V1.0
 */
@Configuration
public class PathVariableCheck {

    private static String exceptionContent = " Disallow the use of pathVariables";
    /**
     *
     * 禁用原因 https://www.cnblogs.com/aboruo/p/10575105.html
     * PathVariableCheck检查
     * @Author LiLuBing
     * @Date 2021-01-20 09:17
     * @Param  * @param
     * @return
     **/
    @PostConstruct
    public void pathVariableCheck() {
        // 扫包
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                // 指定路径URL
                .forPackages("com.llb.cxy.interfaces.facade")
                // 添加方法参数扫描工具
                .addScanners(new MethodParameterScanner())
        );
        /**
         * 获取所有 PathVariable 方法参数注解
         * @Author LiLuBing
         * @Date 2021-01-20 09:38
         * @Param  * @param 
         * @return 
         **/
        Set<Method> pathParamMethods =reflections.getMethodsWithAnyParamAnnotated(PathVariable.class);

        if(!pathParamMethods.isEmpty()) {
            pathParamMethods.forEach(method -> {
                // 禁用原因 https://www.cnblogs.com/aboruo/p/10575105.html
                throw new LlbPathVariableCheckException(method.getDeclaringClass().toString() + "." + method.getName() + exceptionContent);
            });
        }
    }

    /**
     * // 扫包
     *         Reflections reflections = new Reflections(new ConfigurationBuilder()
     *                 .forPackages("com.boothsun.reflections") // 指定路径URL
     *                 .addScanners(new SubTypesScanner()) // 添加子类扫描工具
     *                 .addScanners(new FieldAnnotationsScanner()) // 添加 属性注解扫描工具
     *                 .addScanners(new MethodAnnotationsScanner() ) // 添加 方法注解扫描工具
     *                 .addScanners(new MethodParameterScanner() ) // 添加方法参数扫描工具
     *                 );
     *
     *         // 反射出子类
     *         Set<Class<? extends ISayHello>> set = reflections.getSubTypesOf( ISayHello.class ) ;
     *         System.out.println("getSubTypesOf:" + set);
     *
     *         // 反射出带有指定注解的类
     *         Set<Class<?>> ss = reflections.getTypesAnnotatedWith( MyAnnotation.class );
     *         System.out.println("getTypesAnnotatedWith:" + ss);
     *
     *         // 获取带有特定注解对应的方法
     *         Set<Method> methods = reflections.getMethodsAnnotatedWith( MyMethodAnnotation.class ) ;
     *         System.out.println("getMethodsAnnotatedWith:" + methods);
     *
     *         // 获取带有特定注解对应的字段
     *         Set<Field> fields = reflections.getFieldsAnnotatedWith( Autowired.class ) ;
     *         System.out.println("getFieldsAnnotatedWith:" + fields);
     *
     *         // 获取特定参数对应的方法
     *         Set<Method> someMethods = reflections.getMethodsMatchParams(long.class, int.class);
     *         System.out.println("getMethodsMatchParams:" + someMethods);
     *
     *         Set<Method> voidMethods = reflections.getMethodsReturn(void.class);
     *         System.out.println( "getMethodsReturn:" + voidMethods);
     *
     *         Set<Method> pathParamMethods =reflections.getMethodsWithAnyParamAnnotated( PathParam.class);
     *         System.out.println("getMethodsWithAnyParamAnnotated:" + pathParamMethods);
     **/
}
