package persistance;

import domain.KeyValueEntity;

import java.sql.Connection;
import java.util.List;

public class KeyValueJDBCImplDao<T extends KeyValueEntity> extends JDBCDao implements KeyValueDao<T> {
    String keyColumn;
    String valueColumn;
    String table;

    public KeyValueJDBCImplDao(Connection connection, String valueColumn, String keyColumn, String table){
        super(connection);
        this.valueColumn = valueColumn;
        this.keyColumn = keyColumn;
        this.table = table;
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

//    public List<T> findByRequest(Integer id){
//        //ToDo ?
//        return null;
//    }
}
