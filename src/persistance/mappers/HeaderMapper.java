package persistance.mappers;

import domain.Header;
import domain.Request;
import domain.RequestMethod;
import persistance.JDBCRowMapper;
import persistance.PersistanceException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HeaderMapper implements JDBCRowMapper<Header> {
    @Override
    public Header mapRow(ResultSet rs) throws PersistanceException {
        try {
            return new Header(rs.getString(3), rs.getString(4));
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }
    }
}
