package simple;

import java.lang.annotation.*;

/**
 * @description:
 * @author: zmh
 * @createtime: 2018/4/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface WaterAop {
    enum METHOD{before,after,afterthrowing}
    METHOD method() default METHOD.after;
    String Name() default "类全名";
}
