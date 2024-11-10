package persistance;

import domain.KeyValueEntity;

import java.util.List;

public interface KeyValueDao<T extends KeyValueEntity> {
    public void save(T entity, Integer requestId) throws PersistanceException;
    public List<T> findAll(Integer requestId) throws PersistanceException;
}
