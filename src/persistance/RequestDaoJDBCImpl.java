package persistance;

import domain.Header;
import domain.KeyValueEntity;
import domain.Parameter;
import domain.Request;
import persistance.mappers.RequestMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RequestDaoJDBCImpl extends JDBCDao implements RequestDao {
    KeyValueDao<Header> headerDao;
    KeyValueDao<Parameter> parameterDao;

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
        return this.findMany("SELECT * FROM Request WHERE favorite = true", new RequestMapper());
    }
}
