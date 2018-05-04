package version1;

/**
 * @description:
 * @author: zmh
 * @createtime: 2018/4/27
 */

public class Man implements Person {
    @Override
    public void say() {
        System.out.println("男人say:....");
    }
    public void jump(){
        System.out.println("男人jump....");
    }
}
