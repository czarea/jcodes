package com.czarea.jcodes.db;

import com.czarea.jcodes.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 表信息提取器
 *
 * @author zhouzx
 */
public class TableExtractor {

    private static final CodesDataSource dataSource = CodesDataSource.getInstance();

    public static Table getTable(String table) {
        Table result = new Table();
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        try {
            conn = dataSource.getConnection();
            String cataLog = conn.getCatalog();
            dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(cataLog, cataLog, table, new String[]{"TABLE"});
            ResultSet indexResultSet = dbmd.getIndexInfo(null, cataLog, table, false, true);
            getTableIndexs(result, indexResultSet);

            // 获取信息
            List<String> primaryKeys = getTablePrimaryKeys(cataLog, table, conn);
            result.setPkColumns(primaryKeys);

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                result.setName(tableName);
                String remarks = resultSet.getString("REMARKS");
                if (StringUtils.isEmpty(remarks) && dataSource.getJdbcUrl()
                    .contains("oracle")) {
                    remarks = getOracleTableComments(table, conn);
                }

                result.setComment(remarks);

                if (tableName.equals(table)) {
                    ResultSet columnResultSet = conn.getMetaData().getColumns(cataLog, getSchema(conn), tableName.toUpperCase(), "%");
                    getTableColumns(result, columnResultSet, conn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static List<String> getTablePrimaryKeys(String catalog,
        String table, Connection conn) throws SQLException {
        List<String> primaryKeys = new LinkedList<>();
        ResultSet primaryKeyRs = conn.getMetaData().getPrimaryKeys(null, catalog, table);
        while (primaryKeyRs.next()) {
            String columnName = primaryKeyRs.getString("COLUMN_NAME");
            primaryKeys.add(columnName);
        }
        primaryKeyRs.close();
        return primaryKeys;
    }

    /**
     * 获取数据库中所有表名称
     *
     * @return 所有数据表
     */
    public static List<String> getAllTables(String catalog) {
        if (StringUtils.isEmpty(catalog)) {
            return null;
        }
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet tables = databaseMetaData.getTables(catalog, null, "%", null);
            List<String> tablesList = new ArrayList<>();
            while (tables.next()) {
                tablesList.add(tables.getString("TABLE_NAME"));
            }
            return tablesList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取表字段信息
     */
    private static void getTableColumns(Table result, ResultSet columnResultSet,
        Connection conn) throws SQLException {
        List<Column> columns = new ArrayList<>();
        while (columnResultSet.next()) {
            int sqlType = columnResultSet.getInt("DATA_TYPE");
            String sqlTypeName = columnResultSet.getString("TYPE_NAME");
            String columnName = columnResultSet.getString("COLUMN_NAME");
            String columnDefaultValue = columnResultSet.getString("COLUMN_DEF");
            String columnRemarks = columnResultSet.getString("REMARKS");
            if (columnRemarks == null && dataSource.getJdbcUrl().contains("oracle")) {
                columnRemarks = getOracleColumnComments(result.getName(), columnName, conn);
            }
            int size = columnResultSet.getInt("COLUMN_SIZE");
            int decimalDigits = columnResultSet.getInt("DECIMAL_DIGITS");
            boolean isPk = result.getPkColumns().contains(columnName);
            boolean isNullable = (DatabaseMetaData.columnNullable == columnResultSet.getInt("NULLABLE"));
            boolean isIndexed = result.getIndexColumns().contains(columnName);
            boolean isUnique = result.getUniqueColumns().contains(columnName);
            Column column = new Column(result, sqlType, sqlTypeName, columnName, size, decimalDigits, isPk, isNullable, isIndexed, isUnique,
                columnDefaultValue, columnRemarks);
            columns.add(column);
        }
        result.setColumns(columns);
    }


    /**
     * 获取表索引字段信息
     */
    private static void getTableIndexs(Table result, ResultSet indexResultSet)
        throws SQLException {
        while (indexResultSet.next()) {
            String columnName = indexResultSet.getString("COLUMN_NAME");
            if (columnName == null) {
                continue;
            }
            result.getIndexColumns().add(columnName);
            String indexName = indexResultSet.getString("INDEX_NAME");
            boolean nonUnique = indexResultSet.getBoolean("NON_UNIQUE");
            if (indexName == null) {
                continue;
            }
            // 唯一键记录
            if (!nonUnique) {
                result.getUniqueColumns().add(columnName);
            }
        }
    }

    private static String getOracleTableComments(String table, Connection conn)
        throws SQLException {
        String sql = "SELECT comments FROM user_tab_comments WHERE table_name='" + table + "'";
        return simpleQuery(conn, sql);
    }

    private static String getOracleColumnComments(String table, String column,
        Connection conn) throws SQLException {
        String sql = "SELECT comments FROM user_col_comments WHERE table_name='"
            + table + "' AND column_name = '" + column + "'";
        return simpleQuery(conn, sql);
    }


    private static String simpleQuery(Connection connection, String sql)
        throws SQLException {
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.execute(sql);
        ResultSet rs = pst.executeQuery();
        return rs.getString(0);
    }

    private static String getSchema(Connection conn) throws Exception {
        String schema;
        schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
        return schema.toUpperCase();

    }
}
