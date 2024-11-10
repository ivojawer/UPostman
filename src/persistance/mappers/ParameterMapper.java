package persistance.mappers;

import domain.Header;
import domain.Parameter;
import persistance.JDBCRowMapper;
import persistance.PersistanceException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParameterMapper implements JDBCRowMapper<Parameter> {
    @Override
    public Parameter mapRow(ResultSet rs) throws PersistanceException {
        try {
            return new Parameter(rs.getString(3), rs.getString(4));
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }
    }
}
