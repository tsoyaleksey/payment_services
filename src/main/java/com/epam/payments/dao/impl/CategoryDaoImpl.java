package com.epam.payments.dao.impl;

import static com.epam.payments.dao.api.DaoConstants.*;
import com.epam.payments.dao.api.BaseDao;
import com.epam.payments.dao.api.CategoryDao;
import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.Category;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code RegionDaoImpl} implements {@link CategoryDao} is a Dao layer.
 * This class is designed to handle SQL queries through Java-class.
 *
 * @author Aleksey Tsoy
 */
public class CategoryDaoImpl extends BaseDao implements CategoryDao {
    private static final Logger log = Logger.getLogger(CategoryDaoImpl.class);

    private static final String CREATE      = "INSERT INTO categories VALUES (id, ?)";

    private static final String FIND_BY_ID  = "SELECT * FROM categories WHERE id = ?";

    private static final String UPDATE      = "UPDATE categories SET category_name = ? WHERE id = ?";

    private static final String GET_ALL_CATEGORIES = "SELECT * FROM categories";

    @Override
    public Category create(Category category) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_ONE, category.getName());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    category.setId(resultSet.getInt(INDEX_ONE));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot create a new category ", e);
        }
        return category;
    }

    @Override
    public Category findById(int id) throws DaoException {
        Category category = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_ONE, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    category = getDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find category by id ", e);
        }
        return category;
    }

    @Override
    public void update(Category category) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
            statement.setString(INDEX_ONE, category.getName());
            statement.setInt(INDEX_TWO, category.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update category ", e);
        }
    }

    @Override
    public void delete(Category category) throws DaoException {
    }

    @Override
    public List<Category> getAllCategories() throws DaoException {
        List<Category> categoryList = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(GET_ALL_CATEGORIES)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    categoryList.add(getDataFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot get all categories ", e);
        }
        return categoryList;
    }

    @Override
    public Category getDataFromResultSet(ResultSet resultSet) {
        Category category = new Category();
        try {
            category.setId(resultSet.getInt(INDEX_ONE));
            category.setName(resultSet.getString(INDEX_TWO));
        } catch (SQLException e) {
            log.error("Cannot get category from ResultSet ", e);
        }
        return category;
    }
}