package persistance;

import java.util.List;

public interface RequestRelatedEntityDao<T> {
    public void save(T header, Integer requestId);
    public List<T> findByRequest(Integer id);
}
