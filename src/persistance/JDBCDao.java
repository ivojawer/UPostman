package persistance;

import domain.Request;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class JDBCDao {
    Connection connection;

    public JDBCDao(Connection connection) {
        this.connection = connection;
    }

    // aca deje todos los metodos como protected porque no quiero que cualquiera ejecute SQL desde afuera
    protected PreparedStatement executeSql(String sql, Object... params) throws PersistanceException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for(int i = 1; i <= params.length; i++) {
                if(params[i-1] instanceof Integer) preparedStatement.setInt(i, (Integer)params[i-1]);
                else if(params[i-1] == null) preparedStatement.setObject(i, null);
                else if(params[i-1] instanceof String) preparedStatement.setString(i, (String)params[i-1]);
                else if(params[i-1] instanceof Double) preparedStatement.setDouble(i, (Double)params[i-1]);
                else if(params[i-1] instanceof Float) preparedStatement.setFloat(i, (Float)params[i-1]);
                else if(params[i-1] instanceof Long) preparedStatement.setLong(i, (Long)params[i-1]);
                else if(params[i-1] instanceof Boolean) preparedStatement.setBoolean(i, (Boolean)params[i-1]);
                else if(params[i-1] instanceof LocalDateTime) preparedStatement.setTimestamp(i, new Timestamp(((LocalDateTime) params[i-1]).toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
                else throw new PersistanceException("Invalid parameter type");
            }
            preparedStatement.execute();
            return preparedStatement;
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }

    }

    public <T> T findOne(String sql, JDBCRowMapper<T> mapper, Object... params) throws PersistanceException {
        PreparedStatement res = this.executeSql(sql, params);
        try {
            ResultSet rs = res.getResultSet();
            T result = null;
            if(rs.next()){
                result = mapper.mapRow(rs);
            }
            res.close();
            if(result == null) throw new PersistanceException("No data found");
            return result;
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }
    }

    protected<T> List<T> findMany(String sql, JDBCRowMapper<T> mapper, Object... params) throws PersistanceException {
        try{
            PreparedStatement res = this.executeSql(sql, params);
            ResultSet rs = res.getResultSet();
            List<T> result = new ArrayList<>();
            while(rs.next()){
                result.add(mapper.mapRow(rs));
            }
            res.close();
            return result;
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }
    }
}
