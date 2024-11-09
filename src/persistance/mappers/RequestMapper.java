package persistance.mappers;

import domain.Request;
import persistance.JDBCRowMapper;
import persistance.PersistanceException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestMapper implements JDBCRowMapper<Request> {
    @Override
    public Request mapRow(ResultSet rs) throws PersistanceException {
            Request request = new Request();
            //ToDo hydrate parameters
            return request;
    }
}
