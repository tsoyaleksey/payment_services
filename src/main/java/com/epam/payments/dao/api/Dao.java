package com.epam.payments.dao.api;

import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.Entity;

import java.sql.ResultSet;

/**
 * Interface {@code Dao} is designed to CRUD operations.
 *
 * @param <T> extends {@link Entity} - type of entity
 */
public interface Dao<T extends Entity> {

    /**
     * Method creates new record of entity in database
     *
     * @param entity is need to create
     * @return entity
     * @throws DaoException if entity does not have a sufficient information
     */
    T create(T entity) throws DaoException;

    /**
     * Method finds record by id and picks current entity
     *
     * @param id for finding type of object in database
     * @return picked entity
     * @throws DaoException if have any problems with database
     */
    T findById(int id) throws DaoException;

    /**
     * Method updates objects record in database
     * or create new record in case if does not find
     * record with same id
     *
     * @param entity must be updated
     * @throws DaoException if have any problems with database
     */
    void update(T entity) throws DaoException;

    /**
     * Method deletes the record from database
     * @param entity
     * @throws DaoException if have any problems with database
     */
    void delete(T entity) throws DaoException;

    /**
     * Method allows get data from ResultSet without copy-pasting code.
     *
     * @param resultSet - data which need to get from database
     * @return entity from database
     * @throws DaoException
     */
    T getDataFromResultSet(ResultSet resultSet);
}
