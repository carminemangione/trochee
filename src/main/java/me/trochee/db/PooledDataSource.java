package me.trochee.db;

import io.dropwizard.lifecycle.Managed;
import org.apache.tomcat.dbcp.dbcp.PoolingDataSource;
import org.apache.tomcat.dbcp.pool.ObjectPool;
import org.apache.tomcat.dbcp.pool.impl.GenericObjectPool;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class PooledDataSource implements DataSource, AutoCloseable, Managed {

    private final PoolingDataSource dataSource;
    private final GenericObjectPool pool;

    public PooledDataSource(PoolingDataSource dataSource, GenericObjectPool pool) {
        this.dataSource = dataSource;
        this.pool = pool;
    }

    @Override
    public void close() throws Exception {
        pool.close();
    }

    public void setPool(ObjectPool pool) throws IllegalStateException, NullPointerException {
        dataSource.setPool(pool);
    }

    public boolean isAccessToUnderlyingConnectionAllowed() {
        return dataSource.isAccessToUnderlyingConnectionAllowed();
    }

    public void setAccessToUnderlyingConnectionAllowed(boolean allow) {
        dataSource.setAccessToUnderlyingConnectionAllowed(allow);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return dataSource.isWrapperFor(iface);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return dataSource.unwrap(iface);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection(String uname, String passwd) throws SQLException {
        return dataSource.getConnection(uname, passwd);
    }

    @Override
    public PrintWriter getLogWriter() {
        return dataSource.getLogWriter();
    }

    @Override
    public int getLoginTimeout() {
        return dataSource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setLoginTimeout(int seconds) {
        dataSource.setLoginTimeout(seconds);
    }

    @Override
    public void setLogWriter(PrintWriter out) {
        dataSource.setLogWriter(out);
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
          close();
    }
}
