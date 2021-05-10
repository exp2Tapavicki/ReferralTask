package referral.task.service;

import java.util.List;

public interface ServiceInterface<T> {
    T findById(long id);

    T save(T object);

    void update(T object);

    void delete(Long id, Long ordinalNumber);

    List<T> findAll();

}
