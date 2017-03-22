package cc.home.framework.helper;

import cc.home.framework.annotation.Controller;
import cc.home.framework.annotation.Service;
import cc.home.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cheng on 2017/3/22 0022.
 */
public class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }


    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet=new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(Service.class)){
                classSet.add(aClass);
            }
        }
        return classSet;
    }

    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet=new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(Controller.class)){
                classSet.add(aClass);
            }
        }
        return classSet;
    }

    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> classSet=new HashSet<>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }


}
