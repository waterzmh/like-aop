package version2;

/**
 * @description:自定义一些切面方法，注意:Name后面一定要跟着类全名，否则无法匹配
 * @author: zmh
 * @createtime: 2018/4/27
 */

public class WaterLog {

    @WaterAnnotation(Name = "version2.po.*",method = WaterAnnotation.METHOD.after)
    public void afterAction(){
        System.out.println("后置行为");
    }
    @WaterAnnotation(Name = "version2.po.*",method = WaterAnnotation.METHOD.before,priority = 6)
    public void beforeAction(){
        System.out.println("前置行为，优先级为6");
    }
    @WaterAnnotation(Name = "version2.po.*",method = WaterAnnotation.METHOD.before,priority = 5)
    public void beforeAction2(){
        System.out.println("前置行为,优先级为5");
    }

}
