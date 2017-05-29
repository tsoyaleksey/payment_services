package com.epam.payments.dao.api;

import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.PaymentHistory;
import com.epam.payments.models.User;

import java.util.List;

/**
 * {@code PaymentHistoryDao} interface extends {@link Dao} is designed to
 * implements this all methods for CRUD operations and operations
 * with user's payment history.
 *
 * @author Aleksey Tsoy
 */
public interface PaymentHistoryDao extends Dao<PaymentHistory>  {
    /**
     * Method allows get user's payments history records
     *
     * @return list of payments history
     * @throws DaoException
     */
    List<PaymentHistory> getAllPaymentHistoryRecordsByUser(User user) throws DaoException;
}