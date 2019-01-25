package cn.lvji.jcodes.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 系统工具类
 *
 * @author zhouzx
 */
public class SystemUtil {

    /**
     * 获取环境变量
     */
    public static String getEnv(String key) {
        Map map = System.getenv();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (entry.getKey().equals(key)) {
                return entry.getValue().toString();
            }
        }
        return null;
    }

}
