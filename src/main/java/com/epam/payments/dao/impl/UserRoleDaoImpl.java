package com.epam.payments.dao.impl;

import static com.epam.payments.dao.api.DaoConstants.*;
import com.epam.payments.dao.api.BaseDao;
import com.epam.payments.dao.api.UserRoleDao;
import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.UserRole;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@code WalletDaoImpl} extends {@link BaseDao}
 * and implements {@link UserRoleDao}.
 * This class is designed to perform CRUD operations above user role.
 *
 * @author Aleksey Tsoy
 */
public class UserRoleDaoImpl extends BaseDao implements UserRoleDao {
    private static final Logger log = Logger.getLogger(RegionDaoImpl.class);

    private static final String CREATE      = "INSERT INTO roles VALUES (id, ?)";

    private static final String FIND_BY_ID  = "SELECT * FROM roles WHERE id = ?";

    private static final String UPDATE      = "UPDATE roles SET role_name = ? WHERE id = ?";

    @Override
    public UserRole create(UserRole userRole) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_ONE, userRole.getName());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    userRole.setId(resultSet.getInt(INDEX_ONE));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot create a new user role ", e);
        }
        return userRole;
    }

    @Override
    public UserRole findById(int id) throws DaoException {
        UserRole userRole = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_ONE, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userRole = getDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find role by id ", e);
        }
        return userRole;
    }

    @Override
    public void update(UserRole userRole) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
            statement.setString(INDEX_ONE, userRole.getName());
            statement.setInt(INDEX_TWO, userRole.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update userRole ", e);
        }
    }

    @Override
    public void delete(UserRole userRole) throws DaoException {
    }

    @Override
    public UserRole getDataFromResultSet(ResultSet resultSet) {
        UserRole userRole = new UserRole();
        try {
            userRole.setId(resultSet.getInt(INDEX_ONE));
            userRole.setName(resultSet.getString(INDEX_TWO));
        } catch (SQLException e) {
            log.error("Cannot get user role from ResultSet ", e);
        }
        return userRole;
    }
}
