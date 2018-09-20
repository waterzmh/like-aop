package version2;

import version2.utils.AnnotationHelper;
import version2.utils.CustomComparison;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: zmh
 * @createtime: 2018/4/27
 */

public class ProxyFactory{
    // 维持一个实现接口的被代理的对象,后面改为对象组,由浅入深
    private Person person;
    private WaterLog waterLog;
    private List<AnnotationHelper> beforeAnnotationHelper;
    private List<AnnotationHelper> afterAnnotationHelper;
    public ProxyFactory(Person person, WaterLog waterLog){
        this.person=person;
        this.waterLog=waterLog;
    }
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
//                第一个参数就是代理者，如果你想对代理者做一些操作可以使用这个参数；
//                第二个就是被执行的方法，
//                第三个是执行该方法所需的参数。
                (proxyObj,method,args)->{
                    //如果没有传入aop 直接返回空
                    if(waterLog==null){
                        return null;
                    }
                    beforeAnnotationHelper=new ArrayList<>();
                    afterAnnotationHelper=new ArrayList<>();
                    Class aop=waterLog.getClass();
                    Class c = person.getClass();
                    // 获取aop类的方法的注解并赋给自定义的一些变量，下面根据这些变量是否有值来确定是否有注解
                    getAnnotation(aop,c);
                    executeAnnotationMethod(beforeAnnotationHelper);
                    // 代理对象执行方法并且获得返回值
                    Object returnValue=method.invoke(person,args);
                    executeAnnotationMethod(afterAnnotationHelper);
                    return returnValue;
                }
        );
    }

    /**
     * 获取aop类的方法的注解并赋给自定义的一些变量，下面根据这些变量是否有值来确定是否有注解
     * @param aop 切面的类对象
     * @param proxy 需要被切面的类对象
     */
    private void getAnnotation(Class aop,Class proxy){
        // 获取切面类所有的方法
        Method[] logMethods=aop.getMethods();
        // 如果切入的日志类的方法不为空
        if(logMethods!=null){
            //遍历日志类的方法
            for(Method logMethod:logMethods){
                //取得WaterLog类的方法上WaterAnnotation注解
                WaterAnnotation waterAnnotation=logMethod.getAnnotation(WaterAnnotation.class);
                if(waterAnnotation!=null) {
                    //创建正则表达式匹配aop方法注解的要切入的类
                    String regEx=waterAnnotation.Name();
                    Pattern pattern=Pattern.compile(regEx);
                    // 如果AOP上的注解与传入的类名一致,这里6表示去掉匹配名字中开头的"class "部分。
                    if (pattern.matcher(proxy.toString().substring(6)).find()) {
                        if (waterAnnotation.method() == WaterAnnotation.METHOD.before){
                            // 加入到前置方法数组中,这里多一层Map是为了记录优先级
                            beforeAnnotationHelper.add(new AnnotationHelper(logMethod,waterAnnotation.priority()));
                        }else if(waterAnnotation.method() == WaterAnnotation.METHOD.after){
                            // 加入到后置方法数组中,这里多一层Map是为了记录优先级
                            afterAnnotationHelper.add(new AnnotationHelper(logMethod,waterAnnotation.priority()));
                        }
                    }
                }
            }
            CustomComparison customComparison=new CustomComparison();
            //根据优先级排个序
            Collections.sort(beforeAnnotationHelper,customComparison);
            Collections.sort(afterAnnotationHelper,customComparison);
        }
    }

    /**
     * 执行注解方法
     *
     */
    private void executeAnnotationMethod(List<AnnotationHelper> annotationHelpers) {
        if(annotationHelpers.size()>0){
            annotationHelpers.forEach(annotationHelper -> {
                try {
                    annotationHelper.getMethod().invoke(waterLog);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
