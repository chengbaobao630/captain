package cc.home.framework;

import cc.home.framework.helper.*;
import cc.home.framework.util.ClassUtil;

/**
 * Created by cheng on 2017/3/22 0022.
 */
public final class HelperLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(),true);
        }

    }
}
