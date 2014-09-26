package me.trochee.db;

import com.codahale.metrics.health.HealthCheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatbaseHealthCheck extends HealthCheck {

    private final PooledDataSource pooledDataSource;
    private final String query;

    public DatbaseHealthCheck(PooledDataSource pooledDataSource, String query) {
        this.pooledDataSource = pooledDataSource;
        this.query = query;
    }

    @Override
    protected Result check() throws Exception {
        Result result;
        try(Connection connection = pooledDataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){
            if(rs.next()){
                result = Result.healthy();
            }else{
                result = Result.unhealthy("test query returned no rows");
            }
        }catch (SQLException e){
            result = Result.unhealthy("error querying database", e);
        }
        return result;
    }
}
