package simple;

/**
 * @description:
 * @author: zmh
 * @createtime: 2018/4/27
 */

public class Test {
    @org.junit.Test
    public void waterAop(){
        Person person=new Man();
        Person proxyPerson=(Person) new ProxyFactory(person,new WaterLog()).getProxyInstance();
        proxyPerson.say();
        proxyPerson.jump();
    }
}
