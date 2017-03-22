package cc.home.framework.helper;

import cc.home.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by cheng on 2017/3/22 0022.
 */
public class BeanHelper {

    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> aClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(aClass);
            BEAN_MAP.put(aClass.getClass(),obj);
        }
    }

    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<?> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw  new RuntimeException("can not get bean by class:"+ cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

}
