package cn.lvji.codes.db;

import cn.lvji.codes.util.ColumnUtil;
import cn.lvji.codes.util.StringUtils;

/**
 * 表字段
 *
 * @author zhouzx
 */
public class Column {
    private static final long serialVersionUID = 1L;

    /**
     * Reference to the containing table
     */
    private Table table;

    /**
     * The java.sql.Types type
     */
    private int sqlType;

    /**
     * The sql typename. provided by JDBC driver
     */
    private String sqlTypeName;

    /**
     * The name of the column
     */
    private String columnName;

    /**
     * True if the column is a primary key
     */
    private boolean isPk;

    /**
     * 大小
     */
    private int size;

    /**
     * Describe the column
     */
    private int decimalDigits;

    /**
     * True if the column is nullable
     */
    private boolean isNullable;

    /**
     * True if the column is indexed
     */
    private boolean isIndexed;

    /**
     * True if the column is unique
     */
    private boolean isUnique;

    /**
     * Null if the DB reports no default value
     */
    private String defaultValue;

    /**
     * The comments of column
     */
    private String remarks;


    /**
     * @param table
     * @param sqlType
     * @param sqlTypeName
     * @param columnName
     * @param size
     * @param decimalDigits
     * @param isPk
     * @param isNullable
     * @param isIndexed
     * @param isUnique
     * @param defaultValue
     * @param remarks
     */
    public Column(Table table, int sqlType, String sqlTypeName, String columnName, int size, int decimalDigits,
                  boolean isPk, boolean isNullable, boolean isIndexed, boolean isUnique, String defaultValue, String remarks) {
        if (columnName == null) {
            throw new NullPointerException();
        }
        this.table = table;
        this.sqlType = sqlType;
        this.columnName = columnName;
        this.sqlTypeName = sqlTypeName;
        this.size = size;
        this.decimalDigits = decimalDigits;
        this.isPk = isPk;
        this.isNullable = isNullable;
        this.isIndexed = isIndexed;
        this.isUnique = isUnique;
        this.defaultValue = defaultValue;
        this.remarks = remarks;
    }

    public Column(Column c) {
        this(c.getTable(), c.getSqlType(), c.getSqlTypeName(), c.getColumnName(), c.getSize(), c.getDecimalDigits(), c
                .isPk(), c.isNullable(), c.isIndexed(), c.isUnique(), c.getDefaultValue(), c.getRemarks());
    }


    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public int getSqlType() {
        return sqlType;
    }

    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlTypeName() {
        return sqlTypeName;
    }

    public void setSqlTypeName(String sqlTypeName) {
        this.sqlTypeName = sqlTypeName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isPk() {
        return isPk;
    }

    public void setPk(boolean pk) {
        isPk = pk;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public boolean isIndexed() {
        return isIndexed;
    }

    public void setIndexed(boolean indexed) {
        isIndexed = indexed;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    /**
     * 得到对应的javaType,如java.lang.String,
     *
     * @return
     */
    public String getJavaType() {
        return ColumnUtil.getPreferredJavaType(getSqlType(), getSize(), getDecimalDigits());
    }

    /**
     * 根据列名，根据sqlName计算得出，示例值： BirthDate
     **/
    public String getColumnJavaName() {
        return StringUtils.makeAllWordFirstLetterUpperCase(StringUtils.toUnderscoreName(getColumnName()));
    }

    public boolean getCreateInfo() {
        boolean value = getColumnJavaName().toLowerCase().equals("createtime") || getColumnJavaName().toLowerCase().equals("updatetime");
        return value;
    }
}
