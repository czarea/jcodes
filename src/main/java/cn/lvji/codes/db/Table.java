package cn.lvji.codes.db;

import cn.lvji.codes.config.Config;
import cn.lvji.codes.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 数据表
 *
 * @author zhouzx
 */
public class Table implements java.io.Serializable, Cloneable {
    private String name;
    private String comment;
    private List<Column> columns;

    /**
     * 搜索列
     */
    private LinkedHashSet<Column> searchColumns = new LinkedHashSet<Column>();

    /**
     * 主键
     */
    private List<String> pkColumns = new ArrayList<>();

    private List<String> indexColumns = new ArrayList<>();

    private List<String> uniqueColumns = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<String> getPkColumns() {
        return pkColumns;
    }

    public void setPkColumns(List<String> pkColumns) {
        this.pkColumns = pkColumns;
    }

    public List<String> getIndexColumns() {
        return indexColumns;
    }

    public void setIndexColumns(List<String> indexColumns) {
        this.indexColumns = indexColumns;
    }

    public List<String> getUniqueColumns() {
        return uniqueColumns;
    }

    public void setUniqueColumns(List<String> uniqueColumns) {
        this.uniqueColumns = uniqueColumns;
    }

    /**
     * 获取多少列
     *
     * @return
     */
    public int getColumnCount() {
        return columns.size();
    }


    /**
     * 获取多少列
     *
     * @return
     */
    public int getHalfColumnCount() {
        if (columns.size() % 2 == 0) {
            return (columns.size() - 1) / 2;
        } else {
            return (columns.size() - 1) / 2 + 1;
        }
    }

    /**
     * 得到是主键的全部column
     *
     * @return
     */
    public List<String> getPkList() {
        return pkColumns;
    }

    /**
     * 根据sqlName得到的类名称，示例值: UserInfo
     *
     * @return
     */
    public String getClassName() {
        return StringUtils.makeAllWordFirstLetterUpperCase(StringUtils.toUnderscoreName(name.replaceFirst(Config.getTablePrefixes(), "")));
    }

    public String getTableName() {
        return name;
    }

    /**
     * 数据库中表的表备注
     */
    public String getRemarks() {
        if (StringUtils.isEmpty(comment)) {
            return "";
        } else {
            return comment.replaceAll("表", "");
        }
    }

    public LinkedHashSet<Column> getSearchColumns() {
        return searchColumns;
    }

    public void setSearchColumns(LinkedHashSet<Column> searchColumns) {
        this.searchColumns = searchColumns;
    }
}
