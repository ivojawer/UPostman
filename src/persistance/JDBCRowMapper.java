package persistance;

import java.sql.ResultSet;

public interface JDBCRowMapper<T> {
    public T mapRow(ResultSet rs) throws PersistanceException;
}
