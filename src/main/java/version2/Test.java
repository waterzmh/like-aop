package version2;

import version2.po.Man;
import version2.po.Woman;

/**
 * @description:
 * @author: zmh
 * @createtime: 2018/4/27
 */

public class Test {
    @org.junit.Test
    public void test1(){
        Person person=new Man();
        Person proxyPerson=(Person) new ProxyFactory(person,new WaterLog()).getProxyInstance();
        proxyPerson.say();
        proxyPerson.jump();
    }
    @org.junit.Test
    public void test2(){
        Person person=new Woman();
        Person proxyPerson=(Person) new ProxyFactory(person,new WaterLog()).getProxyInstance();
        proxyPerson.say();
        proxyPerson.jump();
    }

}
