package persistance;

import domain.Request;

import java.util.List;

public interface FavoriteRequestDao {
    public void save(Request request);
    public List<Request> findAll();
    public void delete(Request request);
}
