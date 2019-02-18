package cn.lvji.jcodes.db;

import cn.lvji.jcodes.model.Codes;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * jdbc数据库连接池，简单实现
 *
 * @author zhouzx
 */
public class CodesDataSource implements DataSource {
    public final static int DEFAULT_INITIAL_SIZE = 1;
    public final static int DEFAULT_MAX_SIZE = 8;

    private PrintWriter printWriter = new PrintWriter(System.out);
    private static LinkedList<Connection> connections;
    protected volatile boolean inited = false;
    protected volatile String username;
    protected volatile String password;
    protected volatile String jdbcUrl;
    protected volatile String driverClass;
    protected volatile int initialSize = DEFAULT_INITIAL_SIZE;
    protected volatile int maxSize = DEFAULT_MAX_SIZE;


    private static class SingletonInstance {
        private static final CodesDataSource INSTANCE = new CodesDataSource();
    }

    public static CodesDataSource getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public void init(Codes config) {
        if (inited) {
            return;
        }
        setUsername(config.getDb().getUserName());
        setPassword(config.getDb().getPassword());
        setJdbcUrl(config.getDb().getUrl());
        setDriverClass(config.getDb().getDriverClass());

        connections = new LinkedList<>();
        for (int i = 0; i < initialSize; i++) {
            Connection connection = getDirectConnection();
            Connection connectionProxy = proxy(connection);
            connections.add(connectionProxy);
        }
        inited = true;
    }


    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return printWriter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.printWriter = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    /**
     * TODO 动态代理模式，可优化
     *
     * @return
     * @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        if (!connections.isEmpty()) {
            Connection connection = connections.removeFirst();
            if (connection instanceof Proxy) {
                return connection;
            } else {
                return proxy(connection);
            }
        } else {
            if (connections.size() < maxSize) {
                Connection connection = getDirectConnection();
                Connection connectionProxy = proxy(connection);
                return connectionProxy;
            } else {
                throw new RuntimeException("connnection pool  is busying......");
            }
        }
    }

    private Connection proxy(Connection connection) {
        Connection connectionProxy = null;
        connectionProxy = (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(), connection.getClass().getInterfaces(), (proxy, method, args) -> {
            if ("close".equals(method.getName())) {
                boolean add = connections.add(connection);
                return add;
            } else {
                return method.invoke(connection, args);
            }
        });
        return connectionProxy;
    }

    private class ConnectionHolder {
        private Connection connection;

    }

    /**
     * 获取数据库连接
     */
    private Connection getDirectConnection() {
        Connection conn;
        try {
            Properties props = new Properties();
            props.put("remarksReporting", "true");
            props.put("user", getUsername());
            props.put("password", getPassword());
            props.setProperty("remarks", "true");
            props.setProperty("useInformationSchema", "true");
            Class.forName(getDriverClass());
            conn = DriverManager.getConnection(getJdbcUrl(), props);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface == null) {
            return null;
        }
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface != null && iface.isInstance(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public boolean isInited() {
        return inited;
    }

    public void setInited(boolean inited) {
        this.inited = inited;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
