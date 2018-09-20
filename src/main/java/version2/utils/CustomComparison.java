package version2.utils;

import java.util.Comparator;

/**
 * @description:自定义比较类
 * @author: zmh
 * @createtime: 2018/5/4
 */

public class CustomComparison implements Comparator<AnnotationHelper> {
    @Override
    public int compare(AnnotationHelper o1, AnnotationHelper o2) {
        return o2.getPriority()-o1.getPriority();
    }
}
