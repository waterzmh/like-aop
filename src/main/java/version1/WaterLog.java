package version1;

/**
 * @description:自定义一些切面方法，注意:Name后面一定要跟着类全名，否则无法匹配
 * @author: zmh
 * @createtime: 2018/4/27
 */

public class WaterLog {

    @WaterAnnotation(Name = "version1.Man",method = WaterAnnotation.METHOD.after)
    public void afterAction(){
        System.out.println("后置行为");
    }
    @WaterAnnotation(Name = "version1.Man",method = WaterAnnotation.METHOD.before)
    public void beforeAction(){
        System.out.println("前置行为");
    }

}
