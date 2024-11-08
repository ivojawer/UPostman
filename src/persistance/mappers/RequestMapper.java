package persistance.mappers;

import domain.Request;
import persistance.JDBCRowMapper;
import persistance.PersistanceException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestMapper implements JDBCRowMapper<Request> {
    @Override
    public Request mapRow(ResultSet rs) throws PersistanceException {
        try{
            Request request = new Request(rs.getInt("id"), rs.getString("name"));
            //ToDo hydrate parameters
            return request;
        }
        catch (SQLException e) {
            throw new PersistanceException(e);
        }
    }
}
