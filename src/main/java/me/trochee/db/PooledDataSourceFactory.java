package me.trochee.db;

import org.apache.tomcat.dbcp.dbcp.DriverManagerConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolableConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolingDataSource;
import org.apache.tomcat.dbcp.pool.impl.GenericObjectPool;
import org.joda.time.Duration;

import java.util.Properties;

public class PooledDataSourceFactory {

    public static PooledDataSource buildDataSource(Properties properties) throws ClassNotFoundException {
        final GenericObjectPool pool = buildPool(properties);
        Class.forName(properties.getProperty("driver"));
        final DriverManagerConnectionFactory factory = new DriverManagerConnectionFactory(properties.getProperty("url"), properties);
        final PoolableConnectionFactory connectionFactory = new PoolableConnectionFactory(factory, pool, null,
                properties.getProperty("validationQuery", "SELECT 1"), false, true);

        connectionFactory.setPool(pool);

        return new PooledDataSource(new PoolingDataSource(pool), pool);
    }

    private static GenericObjectPool buildPool(Properties properties) {
        final GenericObjectPool pool = new GenericObjectPool(null);
        pool.setMaxWait(millis(properties, "maxWaitForConnection", "PT1.0S"));
        pool.setMinIdle(integer(properties, "minSize", "1"));
        pool.setMaxActive(integer(properties, "maxSize", "1"));
        pool.setMaxIdle(integer(properties, "maxIdle", "1"));
        pool.setTestWhileIdle(bool(properties, "testWhileIdle", "false"));
        pool.setTimeBetweenEvictionRunsMillis(millis(properties, "timeBetweenEvictionRunsMillis", "PT60.0S"));
        pool.setMinEvictableIdleTimeMillis(millis(properties, "testWhileIdle", "PT60.0S"));
        pool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
        return pool;
    }

    private static boolean bool(Properties properties, String propertyName, String defaultValue) {
        return Boolean.parseBoolean(properties.getProperty(propertyName, defaultValue));
    }

    private static long millis(Properties properties, String propertyName, String defaultValue) {
        return Duration.parse(properties.getProperty(propertyName, defaultValue)).getMillis();
    }

    private static int integer(Properties properties, String propertyName, String defaultValue) {
        return Integer.parseInt(properties.getProperty(propertyName, defaultValue));
    }
}
