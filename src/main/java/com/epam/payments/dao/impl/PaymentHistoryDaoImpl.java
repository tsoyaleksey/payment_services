package com.epam.payments.dao.impl;

import static com.epam.payments.dao.api.DaoConstants.*;
import com.epam.payments.dao.api.BaseDao;
import com.epam.payments.dao.api.PaymentHistoryDao;
import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.PaymentHistory;
import com.epam.payments.models.Provider;
import com.epam.payments.models.User;
import com.epam.payments.services.ProviderService;
import com.epam.payments.services.UserService;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code PaymentHistoryDaoImpl} implements {@link PaymentHistoryDao} is a Dao layer.
 * This class is designed to handle SQL queries through Java-class.
 *
 * @author Aleksey Tsoy
 */
public class PaymentHistoryDaoImpl extends BaseDao implements PaymentHistoryDao {
    private static final Logger log = Logger.getLogger(PaymentHistoryDaoImpl.class);

    private static final String CREATE =
            "INSERT INTO payment_history(id,payment_history.number,payment_history.sum,payment_history.date,payment_history.user_id,payment_history.provider_id) VALUES (id,?,?,?,?,?)";

    private static final String FIND_BY_ID = "SELECT * FROM payment_history WHERE id=?";

    private static final String GET_ALL_PAYMENT_HISTORY_RECORDS_BY_USER = "SELECT * FROM payment_history WHERE user_id = ?";

    @Override
    public PaymentHistory create(PaymentHistory paymentHistory) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_ONE, paymentHistory.getNumber());
            statement.setDouble(INDEX_TWO, paymentHistory.getSum());
            statement.setTimestamp(INDEX_THREE, Timestamp.valueOf(paymentHistory.getDateTime()));
            statement.setInt(INDEX_FOUR, paymentHistory.getUser().getId());
            statement.setInt(INDEX_FIVE, paymentHistory.getProvider().getId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    paymentHistory.setId(resultSet.getInt(INDEX_ONE));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot create a new pay history ", e);
        }
        return paymentHistory;
    }

    @Override
    public PaymentHistory findById(int id) throws DaoException {
        PaymentHistory paymentHistory = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_ONE, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    paymentHistory = getDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find payment history by id ", e);
        }
        return paymentHistory;
    }

    @Override
    public void update(PaymentHistory paymentHistory) throws DaoException {
    }

    @Override
    public void delete(PaymentHistory paymentHistorya) throws DaoException {
    }

    @Override
    public List<PaymentHistory> getAllPaymentHistoryRecordsByUser(User user) throws DaoException {
        List<PaymentHistory> listOfRecords = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(GET_ALL_PAYMENT_HISTORY_RECORDS_BY_USER)) {
            statement.setInt(INDEX_ONE, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    listOfRecords.add(getDataFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot get all payment history records by user", e);
        }
        return listOfRecords;
    }

    @Override
    public PaymentHistory getDataFromResultSet(ResultSet resultSet) {
        PaymentHistory paymentHistory    = new PaymentHistory();
        UserService userService = new UserService();
        ProviderService providerService = new ProviderService();
        try {
            paymentHistory.setId(resultSet.getInt(INDEX_ONE));
            paymentHistory.setNumber(resultSet.getString(INDEX_TWO));
            paymentHistory.setSum(resultSet.getDouble(INDEX_THREE));
            paymentHistory.setDateTime(resultSet.getTimestamp(INDEX_FOUR).toLocalDateTime());
            User user = userService.findUserById(resultSet.getInt(INDEX_FIVE));
            Provider provider = providerService.findProviderById(resultSet.getInt(INDEX_SIX));
            paymentHistory.setUser(user);
            paymentHistory.setProvider(provider);
        } catch (SQLException e) {
            log.error("Cannot get pay history from resultset ", e);
        }

        return paymentHistory;
    }
}