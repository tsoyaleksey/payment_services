package com.epam.payments.dao.impl;

import static com.epam.payments.dao.api.DaoConstants.*;
import com.epam.payments.dao.api.BaseDao;
import com.epam.payments.dao.api.UserDao;
import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.Region;
import com.epam.payments.models.User;
import com.epam.payments.models.UserRole;
import com.epam.payments.models.Wallet;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * {@code UserDaoImpl} implements {@link UserDao} is a Dao layer.
 * This class is designed to handle SQL queries through Java-class.
 *
 * @author Aleksey Tsoy
 */
public class UserDaoImpl extends BaseDao implements UserDao {
    private static final Logger log = Logger.getLogger(UserDaoImpl.class);

    private static final String CREATE =
            "INSERT INTO users (id, phone_number, user_name, date_of_birth, password, wallet_id) VALUES (id, ?, ?, ?, ?, ?)";

    private static final String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";

    private static final String UPDATE =
            "UPDATE users SET phone_number = ?, user_name = ?, date_of_birth = ?, password = ? WHERE id = ?";

    private static final String FIND_USER_BY_NUMBER = "SELECT * FROM users WHERE phone_number = ?";

    private static final String GET_USER_BY_NUMBER_AND_PASSWORD =
            "SELECT * FROM users WHERE phone_number = ? AND password = ?";

    private static final String GET_USER_ROLE =
            "SELECT role_id, role_name FROM users JOIN roles ON users.role_id = roles.id WHERE users.id=?";

    private static final String GET_USER_WALLET =
            "SELECT wallet_id, balance FROM users JOIN wallets ON users.wallet_id = wallets.id WHERE users.id=?";

    private static final String GET_USER_REGION =
            "SELECT region_id, region_name FROM users JOIN regions ON users.region_id = regions.id WHERE users.id=?";

    private static final String UPDATE_USER_REGION = "UPDATE users SET region_id = ? WHERE id = ?";

    private static final String UPDATE_USER_PASSWORD = "UPDATE users SET password = ? WHERE id = ?";

    private static final String UPDATE_USER_NUMBER = "UPDATE users SET phone_number = ? WHERE id = ?";



    @Override
    public User create(User user) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_ONE, user.getPhone());
            statement.setString(INDEX_TWO, user.getName());
            statement.setDate(INDEX_THREE, Date.valueOf(user.getDateOfBirth()));
            statement.setString(INDEX_FOUR, user.getPassword());
            statement.setInt(INDEX_FIVE, user.getWallet().getId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    user.setId(resultSet.getInt(INDEX_ONE));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot create a new user ", e);
        }
        return user;
    }

    @Override
    public User findById(int id) throws DaoException {
        User user = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_ONE, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = getDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find user by id ", e);
        }
        return user;
    }

    @Override
    public void update(User user) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
            statement.setString(INDEX_ONE, user.getPhone());
            statement.setString(INDEX_TWO, user.getName());
            statement.setDate(INDEX_THREE, Date.valueOf(user.getDateOfBirth()));
            statement.setString(INDEX_FOUR, user.getPassword());
            statement.setInt(INDEX_FIVE, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot create statement for updating user ", e);
        }
    }

    @Override
    public void delete(User user) throws DaoException {
    }

    @Override
    public User getUserByNumber(String number) throws DaoException {
        User user = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_USER_BY_NUMBER)) {
            statement.setString(INDEX_ONE, number);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = getDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find user by number: " + number, e);
        }
        return user;
    }

    @Override
    public User getUserByNumberAndPassword(String number, String password) throws DaoException {
        User user = null;
        try (PreparedStatement statement = getConnection().prepareStatement(GET_USER_BY_NUMBER_AND_PASSWORD)) {
            statement.setString(INDEX_ONE, number);
            statement.setString(INDEX_TWO, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = getDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot get user by login and password ", e);
        }
        return user;
    }

    @Override
    public UserRole getUserRole(User user) throws DaoException {
        UserRole userRole = new UserRole();
        try (PreparedStatement statement = getConnection().prepareStatement(GET_USER_ROLE)) {
            statement.setInt(INDEX_ONE, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userRole.setId(resultSet.getInt(INDEX_ONE));
                    userRole.setName(resultSet.getString(INDEX_TWO));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot get user role ", e);
        }
        return userRole;
    }

    @Override
    public Wallet getUserWallet(User user) throws DaoException {
        Wallet wallet = new Wallet();
        try (PreparedStatement statement = getConnection().prepareStatement(GET_USER_WALLET)) {
            statement.setInt(INDEX_ONE, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    wallet.setId(resultSet.getInt(INDEX_ONE));
                    wallet.setBalance(resultSet.getDouble(INDEX_TWO));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot get user's wallet ", e);
        }
        return wallet;
    }

    @Override
    public Region getUserRegion(User user) throws DaoException {
        Region region = new Region();
        try (PreparedStatement statement = getConnection().prepareStatement(GET_USER_REGION)) {
            statement.setInt(INDEX_ONE, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    region.setId(resultSet.getInt(INDEX_ONE));
                    region.setName(resultSet.getString(INDEX_TWO));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot get user region ", e);
        }
        return region;
    }

    @Override
    public void updateUserRegion(User user) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_USER_REGION)) {
            statement.setInt(INDEX_ONE, user.getRegion().getId());
            statement.setInt(INDEX_TWO, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update user region ", e);
        }
    }

    @Override
    public void updatePassword(User user) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_USER_PASSWORD)) {
            statement.setString(INDEX_ONE, user.getPassword());
            statement.setInt(INDEX_TWO, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update user password ", e);
        }
    }

    @Override
    public User getDataFromResultSet(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getInt(INDEX_ONE));
            user.setPhone(resultSet.getString(INDEX_TWO));
            user.setName(resultSet.getString(INDEX_THREE));
            user.setDateOfBirth(LocalDate.from(resultSet.getDate(INDEX_FOUR).toLocalDate()));
            user.setPassword(resultSet.getString(INDEX_FIVE));
        } catch (SQLException e) {
            log.error("Cannot get user from resultSet ", e);
        }
        return user;
    }
}
