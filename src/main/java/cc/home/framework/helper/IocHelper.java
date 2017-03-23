package cc.home.framework.helper;

import cc.home.framework.annotation.Inject;
import cc.home.framework.helper.BeanHelper;
import cc.home.framework.util.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by cheng on 2017/3/22 0022.
 */
public class IocHelper {

    static {
        Map<Class<?> ,Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)){
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)){
                    for (Field beanFiled : beanFields) {
                        if (beanFiled.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass = beanFiled.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null){
                                ReflectionUtil.setField(beanInstance,beanFiled,beanFieldInstance);
                            }
                        }
                    }
                }
            }

        }
    }
}
