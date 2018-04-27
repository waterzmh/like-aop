package simple;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: zmh
 * @createtime: 2018/4/27
 */

public class ProxyFactory {
    // 维持一个实现接口的被代理的对象,后面改为对象组,由浅入深
    private Person person;
    private WaterLog waterLog;
    private Method beforeMethod=null,afterMethod=null;
    public ProxyFactory(Person person,WaterLog waterLog){
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
                (Object proxyObj, Method method, Object[] args)->{
                    //如果没有传入aop 直接返回空
                    if(waterLog==null){
                        return null;
                    }
                    Class aop=waterLog.getClass();
                    Class c = person.getClass();
                    // 获取aop类的方法的注解并赋给自定义的一些变量，下面根据这些变量是否有值来确定是否有注解
                    getAnnotation(aop,c);
                    if(beforeMethod!=null){
                        beforeMethod.invoke(waterLog);
                    }
                    // 代理对象执行方法并且获得返回值
                    Object returnValue=method.invoke(person,args);
                    if(afterMethod!=null){
                        afterMethod.invoke(waterLog);
                    }
                    return returnValue;
                }
        );
    }
    private void getAnnotation(Class aop,Class proxy){
        //如果有AOP的类
        if(waterLog!=null){
            // 获取切面类所有的方法
            Method[] methodsAOP=aop.getMethods();
            // 如果切入的日志类的方法不为空
            if(methodsAOP!=null){
                for(Method logMethod:methodsAOP){
                    // 取得WaterLog类的方法上WaterAOP注解
                    WaterAop waterAOP=logMethod.getAnnotation(WaterAop.class);
                    if(waterAOP!=null) {
                        // 如果AOP上的注解与传入的类名一致
                        if (proxy.toString().substring(6).equals(waterAOP.Name())) {
                            if (waterAOP.method() == WaterAop.METHOD.before) {
                                // 赋值 ,后面再执行
                                beforeMethod=logMethod;
                            }else if(waterAOP.method() == WaterAop.METHOD.after){
                                afterMethod=logMethod;
                            }
                        }
                    }
                }
            }
        }
    }

}
