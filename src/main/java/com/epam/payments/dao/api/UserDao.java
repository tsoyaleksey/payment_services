package com.epam.payments.dao.api;

import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.Region;
import com.epam.payments.models.User;
import com.epam.payments.models.UserRole;
import com.epam.payments.models.Wallet;

/**
 * {@code UserDao} interface extends {@link Dao} is designed to
 * implements this all methods for CRUD operations and operations
 * with user's data.
 *
 * @author Aleksey Tsoy
 */
public interface UserDao extends Dao<User> {

    /**
     * Method is designed to finding user by number(login)
     * is needed for Admin operations
     *
     * @param number - is user's login need to find
     * @return user with set number
     * @throws DaoException if have any problems with database
     */
    User getUserByNumber(String number) throws DaoException;

    /**
     * Method is designed to getting user by number(login) and password.
     * This is necessary to verify the validity of user data
     *
     * @param number - is user login
     * @param password - is user password
     * @return user with set data
     * @throws DaoException if have any problems with database
     */
    User getUserByNumberAndPassword(String number, String password) throws DaoException;

    /**
     * Method is designed to getting user role
     *
     * @param user - whose role need to get
     * @return user role
     * @throws DaoException if have any problems with database
     */
    UserRole getUserRole(User user) throws DaoException;

    /**
     * Method is designed to getting user wallet
     * and balance on it
     *
     * @param user - whose balance need to get
     * @return user with set parameters
     * @throws DaoException if have any problems with database
     */
    Wallet getUserWallet(User user) throws DaoException;

    /**
     * Method is designed to getting current user region.
     *
     * @param user - whose region need to get
     * @return current user region
     * @throws DaoException if have any problems with database
     */
    Region getUserRegion(User user) throws DaoException;

    /**
     * Method is designed to updating user region
     *
     * @param user - whose region need to update
     * @throws DaoException if have any problems with database
     */
    void updateUserRegion(User user) throws DaoException;

    /**
     * Method is designed to updating user password
     *
     * @param user - whose password need to update
     * @throws DaoException if have any problems with database
     */
    void updatePassword(User user) throws DaoException;
}
