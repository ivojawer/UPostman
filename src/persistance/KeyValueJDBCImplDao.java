package persistance;

import domain.KeyValueEntity;

import java.sql.Connection;
import java.util.List;

public class KeyValueJDBCImplDao<T extends KeyValueEntity> extends JDBCDao implements KeyValueDao<T> {
    private final String keyColumn;
    private final String valueColumn;
    private final String table;
    private final JDBCRowMapper<T> mapper;

    public KeyValueJDBCImplDao(Connection connection, String valueColumn, String keyColumn, String table, JDBCRowMapper<T> mapper) {
        super(connection);
        this.valueColumn = valueColumn;
        this.keyColumn = keyColumn;
        this.table = table;
        this.mapper = mapper;
    }

    public void save(T entity, Integer requestId) throws PersistanceException {
        String columns = keyColumn.concat( ", request_id, ").concat(valueColumn);
        this.executeSql(
            "INSERT INTO " + table + "(" +columns + ") VALUES (?,?,?)",
                entity.getKey(),
                requestId,
                entity.getValue()
        );
    }

    @Override
    public List<T> findAll(Integer requestId) throws PersistanceException {
        return findMany("SELECT * FROM " + table + " WHERE request_id = ?", mapper, requestId);
    }
}
