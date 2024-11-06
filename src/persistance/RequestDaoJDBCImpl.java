package persistance;

import domain.Request;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDaoJDBCImpl implements RequestDao {
    Connection connection;
    public RequestDaoJDBCImpl(Connection connection) {
        this.connection = connection;

    }
    @Override
    public void save(Request request) throws PersistanceException {
        if(request.isPersisted()){
            try {
                connection.createStatement().executeQuery("UPDATE request SET ");
            } catch (SQLException e) {
                throw new PersistanceException(e);
            }
        }
    }

    @Override
    public Request findById(Integer id) throws PersistanceException {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Request WHERE id = " + id);
            if(resultSet.next()){
                return this.rowToRequest(resultSet);
            } else {
                throw new PersistanceException("Request not found");
            }
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }
    }

    @Override
    public List<Request> findFavorites() throws PersistanceException {
        try{
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Request WHERE favorite = true");
            List<Request> requests = new ArrayList<>();
            while(resultSet.next()){
                requests.add(this.rowToRequest(resultSet));
            }
            return requests;
        } catch (SQLException e) {
            throw new PersistanceException(e);
        }
    }

    private Request rowToRequest(ResultSet resultSet) throws SQLException {
        Request request = new Request(resultSet.getInt("id"), resultSet.getString("name"));

        return request;
    }
}
