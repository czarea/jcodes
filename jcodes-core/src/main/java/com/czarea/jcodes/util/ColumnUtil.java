package com.czarea.jcodes.util;

import java.sql.Types;
import java.util.HashMap;

/**
 * 数据库字段类型和java类型转换工具类
 *
 * @author zhouzx
 */
public class ColumnUtil {

    private final static HashMap<Integer, Object> TYPE_MAP_OBJECT = new HashMap<Integer, Object>();
    private final static HashMap<Integer, Object> TYPE_MAP = new HashMap<Integer, Object>();

    public static boolean isFloatNumber(String javaType) {
        if (javaType.endsWith("Float") || javaType.endsWith("Double")) {
            return true;
        }
        if (javaType.endsWith("float") || javaType.endsWith("double")
            || javaType.endsWith("BigDecimal") || javaType.endsWith("BigInteger")) {
            return true;
        }
        return false;
    }

    public static boolean isIntegerNumber(String javaType) {
        if (javaType.endsWith("Long") || javaType.endsWith("Integer")
            || javaType.endsWith("Short") || javaType.endsWith("Byte")) {
            return true;
        }
        if (javaType.endsWith("long") || javaType.endsWith("int") || javaType
            .endsWith("short") || javaType.endsWith("byte")) {
            return true;
        }
        return false;
    }

    public static boolean isDate(String javaType) {
        if (javaType.endsWith("Date") || javaType.endsWith("Timestamp")
            || javaType.endsWith("Time")) {
            return true;
        }
        return false;
    }

    public static boolean isTimeStamp(String javaType) {
        if (javaType.endsWith("Timestamp")) {
            return true;
        }
        return false;
    }

    public static boolean isString(String javaType) {
        if (javaType.endsWith("String")) {
            return true;
        }
        return false;
    }

    public static String getPreferredJavaTypeObject(int sqlType, int size,
        int decimalDigits) {
        if ((sqlType == Types.DECIMAL || sqlType == Types.NUMERIC)
            && decimalDigits == 0) {
            if (size == 1) {
                return "Integer";
            } else if (size < 3) {
                return "Byte";
            } else if (size < 5) {
                return "Short";
            } else if (size < 10) {
                return "Integer";
            } else if (size < 19) {
                return "Long";
            } else {
                return "Double";
            }
        }
        String result = (String) TYPE_MAP_OBJECT.get(sqlType);
        if (result == null) {
            result = "java.lang.Object";
        }
        return result;
    }

    public static String getPreferredJavaType(int sqlType, int size,
        int decimalDigits) {
        if ((sqlType == Types.DECIMAL || sqlType == Types.NUMERIC)
            && decimalDigits == 0) {
            if (size <= 10) {
                return "Integer";
            } else if (size <= 20) {
                return "Long";
            } else {
                return "Double";
            }
        }
        String result = (String) TYPE_MAP.get(sqlType);
        if (result == null) {
            result = "Object";
        }
        return result;
    }

    static {
        TYPE_MAP.put(Types.TINYINT, "Integer");
        TYPE_MAP.put(Types.SMALLINT, "Integer");
        TYPE_MAP.put(Types.INTEGER, "Integer");
        TYPE_MAP.put(Types.BIGINT, "Long");
        TYPE_MAP.put(Types.REAL, "Float");
        TYPE_MAP.put(Types.FLOAT, "Double");
        TYPE_MAP.put(Types.DOUBLE, "Double");
        TYPE_MAP.put(Types.DECIMAL, "Double");
        TYPE_MAP.put(Types.NUMERIC, "java.math.BigDecimal");
        TYPE_MAP.put(Types.BIT, "Integer");
        TYPE_MAP.put(Types.BOOLEAN, "Boolean");
        TYPE_MAP.put(Types.CHAR, "String");
        TYPE_MAP.put(Types.VARCHAR, "String");
        TYPE_MAP.put(Types.LONGVARCHAR, "String");
        TYPE_MAP.put(Types.BINARY, "byte[]");
        TYPE_MAP.put(Types.VARBINARY, "byte[]");
        TYPE_MAP.put(Types.LONGVARBINARY, "byte[]");
        TYPE_MAP.put(Types.DATE, "Date");
        TYPE_MAP.put(Types.TIME, "Date");
        TYPE_MAP.put(Types.TIMESTAMP, "Date");
        TYPE_MAP.put(Types.CLOB, "Clob");
        TYPE_MAP.put(Types.BLOB, "Blob");
        TYPE_MAP.put(Types.ARRAY, "Array");
        TYPE_MAP.put(Types.REF, "Ref");
        TYPE_MAP.put(Types.STRUCT, "Object");
        TYPE_MAP.put(Types.JAVA_OBJECT, "Object");

        TYPE_MAP_OBJECT.put(Types.TINYINT, "Byte");
        TYPE_MAP_OBJECT.put(Types.SMALLINT, "Short");
        TYPE_MAP_OBJECT.put(Types.INTEGER, "Integer");
        TYPE_MAP_OBJECT.put(Types.BIGINT, "Long");
        TYPE_MAP_OBJECT.put(Types.REAL, "Float");
        TYPE_MAP_OBJECT.put(Types.FLOAT, "Double");
        TYPE_MAP_OBJECT.put(Types.DOUBLE, "Double");
        TYPE_MAP_OBJECT.put(Types.DECIMAL, "Double");
        TYPE_MAP_OBJECT.put(Types.NUMERIC, "java.math.BigDecimal");
        TYPE_MAP_OBJECT.put(Types.BIT, "Boolean");
        TYPE_MAP_OBJECT.put(Types.BOOLEAN, "Boolean");
        TYPE_MAP_OBJECT.put(Types.CHAR, "String");
        TYPE_MAP_OBJECT.put(Types.VARCHAR, "String");
        TYPE_MAP_OBJECT.put(Types.LONGVARCHAR, "String");
        TYPE_MAP_OBJECT.put(Types.BINARY, "byte[]");
        TYPE_MAP_OBJECT.put(Types.VARBINARY, "byte[]");
        TYPE_MAP_OBJECT.put(Types.LONGVARBINARY, "byte[]");
        TYPE_MAP_OBJECT.put(Types.DATE, "java.util.Date");
        TYPE_MAP_OBJECT.put(Types.TIME, "java.util.Date");
        TYPE_MAP_OBJECT.put(Types.TIMESTAMP, "java.util.Date");
        TYPE_MAP_OBJECT.put(Types.CLOB, "java.sql.Clob");
        TYPE_MAP_OBJECT.put(Types.BLOB, "java.sql.Blob");
        TYPE_MAP_OBJECT.put(Types.ARRAY, "java.sql.Array");
        TYPE_MAP_OBJECT.put(Types.REF, "java.sql.Ref");
        TYPE_MAP_OBJECT.put(Types.STRUCT, "Object");
        TYPE_MAP_OBJECT.put(Types.JAVA_OBJECT, "Object");
    }

}
