package version2;

import java.lang.annotation.*;

/**
 * @description:
 * @author: zmh
 * @createtime: 2018/4/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface WaterAnnotation {
    enum METHOD{before,after}
    METHOD method() default METHOD.after;
    String Name() default "类全名";
    //优先级 数字越大优先级越高
    int priority() default 999;
}
