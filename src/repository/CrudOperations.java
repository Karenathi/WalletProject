package repository;

import java.util.List;

public interface CrudOperations<T> {

    // Get all
    List<T> findAll();

    // Get by id
    T findById(int id);

    // Update by id
    void updateById(int id, T updatedEntity);

}
