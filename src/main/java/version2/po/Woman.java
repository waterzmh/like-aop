package version2.po;

import version2.Person;

/**
 * @description:
 * @author: zmh
 * @createtime: 2018/5/4
 */

public class Woman implements Person {
    @Override
    public void say() {
        System.out.println("女人say:....");
    }
    @Override
    public void jump(){
        System.out.println("女人jump....");
    }
}
