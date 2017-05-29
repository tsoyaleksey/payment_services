package com.epam.payments.dao.api;

import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.Category;

import java.util.List;

/**
 * {@code RegionDao} interface extends {@link Dao} is designed
 * for CRUD-operations and operations with categories.
 *
 * @author Aleksey Tsoy
 */
public interface CategoryDao extends Dao<Category> {

    /**
     * Method is designed to obtain all categories.
     *
     * @return list of categories
     * @throws DaoException
     */
    List<Category> getAllCategories() throws DaoException;
}
