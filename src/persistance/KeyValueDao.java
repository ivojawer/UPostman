package persistance;

import domain.KeyValueEntity;

public interface KeyValueDao<T extends KeyValueEntity> {
    public void save(T entity, Integer requestId) throws PersistanceException;
}
