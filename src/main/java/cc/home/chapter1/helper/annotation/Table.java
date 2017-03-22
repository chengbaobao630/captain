package cc.home.chapter1.helper.annotation;

import java.lang.annotation.*;

/**
 * Created by cheng on 2016/9/5 0005.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

    String name() default  "";

}
