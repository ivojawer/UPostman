package persistance;

import domain.Request;
import persistance.mappers.RequestMapper;
import java.sql.Connection;
import java.util.List;

public class RequestDaoJDBCImpl extends JDBCDao implements RequestDao {
    public RequestDaoJDBCImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void save(Request request) throws PersistanceException {
        if(request.isPersisted()){
            this.executeSql("UPDATE request SET ");
        }
    }

    @Override
    public Request findById(Integer id) throws PersistanceException {
        return this.findOne("SELECT * FROM Request WHERE id = ?", new RequestMapper(), id);
    }

    @Override
    public List<Request> findFavorites() throws PersistanceException {
        return this.findMany("SELECT * FROM Request WHERE favorite = true", new RequestMapper());
    }
}
