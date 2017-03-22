package cc.home.framework;

import cc.home.framework.helper.BeanHelper;
import cc.home.framework.helper.ClassHelper;
import cc.home.framework.helper.ControllerHelper;
import cc.home.framework.helper.IocHelper;
import cc.home.framework.util.ClassUtil;

/**
 * Created by cheng on 2017/3/22 0022.
 */
public final class HelperLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(),true);
        }

    }
}
