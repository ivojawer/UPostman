package persistance;

import domain.Request;

import java.util.List;

public interface RequestDao {
    public void save(Request request) throws PersistanceException;
    public Request findById(Integer id) throws PersistanceException;

    List<Request> findFavorites() throws PersistanceException;
}
