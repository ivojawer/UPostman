package persistance;

import domain.Header;
import domain.Parameter;
import domain.Request;
import persistance.mappers.RequestMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RequestDaoJDBCImpl extends JDBCDao implements RequestDao {
    private final KeyValueDao<Header> headerDao;
    private final KeyValueDao<Parameter> parameterDao;

    public RequestDaoJDBCImpl(Connection connection,KeyValueDao<Header> headerDao,KeyValueDao<Parameter> parameterDao) {
        super(connection);
        this.headerDao = headerDao;
        this.parameterDao = parameterDao;
    }

    @Override
    public void save(Request request) throws PersistanceException {
        PreparedStatement result = this.executeSql(
                "INSERT INTO Request(path, method, favorite, body, last_executed) VALUES (?,?,?,?,?)",
                request.getPath(),
                request.getMethod().ordinal(),
                request.isFavorite(),
                request.getBody(),
                request.getLastSent()
        );

        try (ResultSet keys = result.getGeneratedKeys()){
            while (keys.next()){
                request.setId(keys.getInt(1));
            }
            result.close();
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }

        for(Header h : request.getHeaders()){
            headerDao.save(h, request.getId());
        }
        for(Parameter p : request.getParameters()){
            parameterDao.save(p, request.getId());
        }
    }

    @Override
    public Request findById(Integer id) throws PersistanceException {
        return this.findOne("SELECT * FROM Request WHERE id = ?", new RequestMapper(), id);
    }

    @Override
    public List<Request> findFavorites() throws PersistanceException {
        return this.findManyHydrated("SELECT * FROM Request WHERE favorite = true LIMIT 50");
    }

    @Override
    public List<Request> findHistory() throws PersistanceException {
        return this.findManyHydrated("SELECT * FROM Request WHERE last_executed IS NOT NULL ORDER BY last_executed DESC LIMIT 50");
    }

    private List<Request> findManyHydrated(String sql, Object... params) throws PersistanceException {
        List<Request> reqs = this.findMany(sql, new RequestMapper(), params);
        hydrateRelations(reqs);
        return reqs;
    }

    private void hydrateRelations(List<Request> requests) throws PersistanceException {
        for(Request r : requests){
            r.setParameters(parameterDao.findAll(r.getId()));
            r.setHeaders(headerDao.findAll(r.getId()));
        }
    }
}
