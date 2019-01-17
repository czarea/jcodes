package cn.lvji.codes.util;


/**
 * 工具类
 *
 * @author zhouzx
 */
public class StringUtils {
    /**
     * 是否为空
     *
     * @param str 字符串
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * 加单引号
     *
     * @return str 字符串
     */
    public static String wrapperSingleQuote(String str) {
        return "'" + str + "'";
    }
}
