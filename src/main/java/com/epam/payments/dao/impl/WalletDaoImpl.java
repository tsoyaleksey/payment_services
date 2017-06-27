package com.epam.payments.dao.impl;

import static com.epam.payments.dao.api.DaoConstants.*;
import com.epam.payments.dao.api.BaseDao;
import com.epam.payments.dao.api.WalletDao;
import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.Wallet;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@code WalletDaoImpl} extends {@link BaseDao}
 * and implements {@link WalletDao}.
 * This class is designed to perform CRUD operations above user's wallet.
 *
 * @author Aleksey Tsoy
 */
public class WalletDaoImpl extends BaseDao implements WalletDao {
    private static final Logger log = Logger.getLogger(WalletDaoImpl.class);

    private static final String CREATE      = "INSERT INTO wallets VALUES (id, ?)";

    private static final String FIND_BY_ID  = "SELECT * FROM wallets WHERE id = ?";

    private static final String UPDATE      = "UPDATE wallets SET balance = ? WHERE id = ?";

    @Override
    public Wallet create(Wallet wallet) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(INDEX_ONE, wallet.getBalance());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    wallet.setId(resultSet.getInt(INDEX_ONE));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot create a wallet for user ", e);
        }
        return wallet;
    }

    @Override
    public Wallet findById(int id) throws DaoException {
        Wallet wallet = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_ONE, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    wallet = getDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find wallet by id ", e);
        }
        return wallet;
    }

    @Override
    public void update(Wallet wallet) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
            statement.setDouble(INDEX_ONE, wallet.getBalance());
            statement.setInt(INDEX_TWO, wallet.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update user wallet ", e);
        }
    }

    @Override
    public void delete(Wallet wallet) throws DaoException {
    }

    @Override
    public Wallet getDataFromResultSet(ResultSet resultSet) {
        Wallet wallet = new Wallet();
        try {
            wallet.setId(resultSet.getInt(INDEX_ONE));
            wallet.setBalance(resultSet.getDouble(INDEX_TWO));
        } catch (SQLException e) {
            log.error("Cannot get user's wallet ", e);
        }
        return wallet;
    }
}
