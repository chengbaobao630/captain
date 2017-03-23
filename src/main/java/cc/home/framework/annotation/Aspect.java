package cc.home.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by cheng on 2017/3/23 0023.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
