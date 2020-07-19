package com.czarea.jcodes.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author zhouzx
 */
public class StringUtils {

    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat(PATTERN_DATETIME);

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

    /**
     * 加双引号
     *
     * @return str 字符串
     */
    public static String wrapperDoubleQuote(String str) {
        return "\"" + str + "\"";
    }


    public static String getCurrentTimestamp() {
        return DATETIME_FORMATTER.format(new Date());
    }

    /**
     * 首字母变小写
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 字符串为 null 或者为  "" 时返回 true
     */
    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim()) ? true : false;
    }

    /**
     * 字符串不为 null 而且不为  "" 时返回 true
     */
    public static boolean notBlank(String str) {
        return str == null || "".equals(str.trim()) ? false : true;
    }

    public static boolean notBlank(String... strings) {
        if (strings == null) {
            return false;
        }
        for (String str : strings) {
            if (str == null || "".equals(str.trim())) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNull(Object... paras) {
        if (paras == null) {
            return false;
        }
        for (Object obj : paras) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Convert a name in camelCase to an underscored name in lower case.
     * Any upper case letters are converted to lower case with a preceding underscore.
     *
     * @param name the string containing original name
     * @return the converted name
     */
    public static String toUnderscoreName(String name) {
        if (name == null) {
            return null;
        }

        String filteredName = name;
        if (filteredName.indexOf("_") >= 0 && filteredName.equals(filteredName.toUpperCase())) {
            filteredName = filteredName.toLowerCase();
        }
        if (filteredName.indexOf("_") == -1 && filteredName.equals(filteredName.toUpperCase())) {
            filteredName = filteredName.toLowerCase();
        }

        StringBuffer result = new StringBuffer();
        if (filteredName != null && filteredName.length() > 0) {
            result.append(filteredName.substring(0, 1).toLowerCase());
            for (int i = 1; i < filteredName.length(); i++) {
                String preChart = filteredName.substring(i - 1, i);
                String c = filteredName.substring(i, i + 1);
                if (c.equals("_")) {
                    result.append("_");
                    continue;
                }
                if (preChart.equals("_")) {
                    result.append(c.toLowerCase());
                    continue;
                }
                if (c.matches("\\d")) {
                    result.append(c);
                } else if (c.equals(c.toUpperCase())) {
                    result.append("_");
                    result.append(c.toLowerCase());
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }


    public static String makeAllWordFirstLetterUpperCase(String sqlName) {
        String[] strs = sqlName.toLowerCase().split("_");
        String result = "";
        String preStr = "";
        for (int i = 0; i < strs.length; i++) {
            if (preStr.length() == 1) {
                result += strs[i];
            } else {
                result += firstCharToUpperCase(strs[i]);
            }
            preStr = strs[i];
        }
        return result;
    }

    public static String toCable(String sqlName) {
        String[] strs = sqlName.toLowerCase().split("_");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            if (i == 0) {
                result.append(strs[i].toLowerCase());
            } else {
                result.append("-").append(strs[i].toLowerCase());
            }
        }
        return result.toString();
    }

    public static String toSlash(String sqlName) {
        String[] strs = sqlName.toLowerCase().split("_");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            if (i == 0) {
                result.append(strs[i].toLowerCase());
            } else {
                result.append("/").append(strs[i].toLowerCase());
            }
        }
        return result.toString();
    }


    public static boolean contains(String str, String... keywords) {
        if (str == null) {
            return false;
        }
        if (keywords == null) {
            throw new IllegalArgumentException("'keywords' must be not null");
        }

        for (String keyword : keywords) {
            if (str.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    public static String getJavaClassSimpleName(String clazz) {
        if (clazz == null) {
            return null;
        }
        if (clazz.lastIndexOf(".") >= 0) {
            return clazz.substring(clazz.lastIndexOf(".") + 1);
        }
        return clazz;
    }

    /**
     * 为空
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 不为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 转大写
     */
    public static String toUpperCase(String instr) {
        return instr == null ? instr : instr.toUpperCase();
    }

    /**
     * 转小写
     */
    public static String toLowerCase(String instr) {
        return instr == null ? instr : instr.toLowerCase();
    }


    /**
     * 首字母大写 ,其余不变
     */
    public static String toUpperCaseFirst(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return str;
        }
        String pre = String.valueOf(str.charAt(0));
        return str.replaceFirst(pre, pre.toUpperCase());
    }

    /**
     * 首字母小写 ,其余不变
     */
    public static String toLowerCaseFirst(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return str;
        }
        String pre = String.valueOf(str.charAt(0));
        return str.replaceFirst(pre, pre.toLowerCase());
    }

    /**
     * 不会抛NullPointerException 的trim() <br>
     * 传入null会返回null
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 过滤 ;当instr==null时返回长度为0的""; <br>
     * 与 nvl(...)系的区别在于只处理null ,不处理长度为0的"";
     */
    public static String nvl(String instr) {
        return nvl(instr, "");
    }

    /**
     * 过滤 ,把null和长度为0的""当成同一种情况处理; <br>
     * 当instr==null||"".equals(instr)时返回defaultValue ;其它情况返回 instr
     */
    public static String nvl(String instr, String defaultValue) {
        return instr == null || "".equals(instr) ? defaultValue : instr;
    }

    /**
     * 比较 str1 和 str2 如果都是 null 或者 str1.equals(str2) 返回 true 表示一样 ;
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 != null && str1.equals(str2)) {
            return true;
        }
        return false;
    }

    public static String apadLeft(double a, int b, int len) {
        return apadLeft(String.valueOf(a), String.valueOf(b), len);
    }

    public static String apadRight(double a, int b, int len) {
        return apadRight(String.valueOf(a), String.valueOf(b), len);
    }

    public static String apadLeft(String str, String str2, int len) {
        if (str == null || str.length() == len || str2 == null) {
            return str;
        }
        if (str.length() > len) {
            return str.substring(str.length() - len, len);
        }
        return apadpro(str, str2, len, true);
    }

    public static String apadRight(String str, String str2, int len) {
        if (str == null || str.length() == len || str2 == null) {
            return str;
        }
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return apadpro(str, str2, len, false);
    }

    private static String apadpro(String a, String b, int len, boolean appendleft) {
        int f = len - a.length();
        for (int i = 0; i < f; i++) {
            a = appendleft == true ? b + a : a + b;
        }
        return a;
    }


    /**
     * 清除字符串中所有的空格 ,传入null返回null
     */
    public static String clear(String str) {
        return clear(str, " ");
    }

    /**
     * 清除str中出现的所有str2字符序列 直到结果中再也找不出str2为止 str2 == null时 返回str
     *
     * @param str 原始字符串
     * @param str2 清除的目标
     */
    public static String clear(String str, String str2) {
        if (str == null) {
            return str;
        }
        if (str2 == null) {
            return str;
        }
        String reg = "(" + str2 + ")+";
        Pattern p = Pattern.compile(reg);
        while (p.matcher(str).find()) {
            str = str.replaceAll(reg, "");
        }
        return str;
    }

    /**
     * 如果str的长度超过了c则取c-sub.length长度,然后拼上sub结尾
     */
    public static String suojin(String str, int c, String sub) {
        if (isEmpty(str)) {
            return str;
        }
        if (str.length() <= c) {
            return str;
        }
        sub = nvl(sub);
        c = c - sub.length();
        c = c > str.length() ? 0 : c;
        str = str.substring(0, c);
        return str + sub;
    }

    /**
     * 如果str的长度超过了length,取前length位然后拼上...
     */
    public static String suojin(String str, int length) {
        return suojin(str, length, "…");
    }

    public static String replaceOnce(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, 1);
    }

    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = increase >= 0 ? increase : 0;
        increase *= max >= 0 ? max <= 64 ? max : 64 : 16;
        StringBuffer buf = new StringBuffer(text.length() + increase);
        do {
            if (end == -1) {
                break;
            }
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        } while (true);
        buf.append(text.substring(start));
        return buf.toString();
    }
}
