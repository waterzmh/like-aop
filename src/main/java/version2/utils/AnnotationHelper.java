package version2.utils;

import java.lang.reflect.Method;

/**
 * @description:注解帮助类，自定义注解新增了优先级，用对象保存有更好的扩展性
 * @author: zmh
 * @createtime: 2018/5/4
 */
public class AnnotationHelper{
    private Method method;
    private Integer priority;

    public AnnotationHelper(Method method, Integer priority) {
        this.method = method;
        this.priority = priority;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

}
