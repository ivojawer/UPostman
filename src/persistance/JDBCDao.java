package persistance;

import domain.Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class JDBCDao {
    Connection connection;

    public JDBCDao(Connection connection) {
        this.connection = connection;
    }

    // aca deje todos los metodos como protected porque no quiero que cualquiera ejecute SQL desde afuera
    protected ResultSet executeSql(String sql, Object... params) throws PersistanceException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            for(int i = 1; i <= params.length; i++) {
                if(params[i] instanceof Integer) preparedStatement.setInt(i, (Integer)params[i-1]);
                else if(params[i] instanceof String) preparedStatement.setString(i, (String)params[i-1]);
                else if(params[i] instanceof Double) preparedStatement.setDouble(i, (Double)params[i-1]);
                else if(params[i] instanceof Float) preparedStatement.setFloat(i, (Float)params[i-1]);
                else if(params[i] instanceof Long) preparedStatement.setLong(i, (Long)params[i-1]);
                else if(params[i] instanceof Boolean) preparedStatement.setBoolean(i, (Boolean)params[i-1]);
                else throw new PersistanceException("Invalid parameter type");
            }
            preparedStatement.execute();
            return preparedStatement.getResultSet();
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }

    }

    public <T> T findOne(String sql, JDBCRowMapper<T> mapper, Object... params) throws PersistanceException {
        try{
            ResultSet resultSet = this.executeSql(sql, params);
            if(resultSet.next()){
                return mapper.mapRow(resultSet);
            }
            throw new PersistanceException("No data found");
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }

    }

    protected<T> List<T> findMany(String sql, JDBCRowMapper<T> mapper, Object... params) throws PersistanceException {
        try{
            ResultSet resultSet = this.executeSql(sql, params);
            List<T> result = new ArrayList<>();
            while(resultSet.next()){
                result.add(mapper.mapRow(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }
    }
}
