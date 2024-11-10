package persistance.mappers;

import domain.Request;
import domain.RequestMethod;
import persistance.JDBCRowMapper;
import persistance.PersistanceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RequestMapper implements JDBCRowMapper<Request> {
    @Override
    public Request mapRow(ResultSet rs) throws PersistanceException {
        Request request = new Request();
        try {
            request.setId(rs.getInt(1));
            request.setPath(rs.getString(2));
            request.setMethod(RequestMethod.values()[rs.getInt(3)]);
            request.setBody(rs.getString(4));
            request.setFavorite(rs.getBoolean(5));
            Timestamp lastSent = rs.getTimestamp(6);
            if(lastSent != null) {
                request.setLastSent(lastSent.toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }
        return request;
    }
}
