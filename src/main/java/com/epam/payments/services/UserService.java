package com.epam.payments.services;

import com.epam.payments.dao.api.DaoFactory;
import com.epam.payments.dao.api.PaymentHistoryDao;
import com.epam.payments.dao.api.UserDao;
import com.epam.payments.dao.api.WalletDao;
import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.dao.impl.PaymentHistoryDaoImpl;
import com.epam.payments.dao.impl.UserDaoImpl;
import com.epam.payments.dao.impl.WalletDaoImpl;
import com.epam.payments.models.*;
import com.epam.payments.services.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class);

    public User registerUser(User user) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao     = daoFactory.getDao(UserDaoImpl.class);
                WalletDao walletDao = daoFactory.getDao(WalletDaoImpl.class);
                daoFactory.startTransaction();
                Wallet registeredWallet = walletDao.create(new Wallet());
                user.setWallet(registeredWallet);
                user = userDao.create(user);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot register a new user ", e);
            }
        }
        return user;
    }

    public boolean checkNumberAvailability(String number) {
        boolean result = true;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDaoImpl.class);
                daoFactory.startTransaction();
                User user = userDao.getUserByNumber(number);
                if (user != null) {
                    result = false;
                }
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot check number availability ", e);
            }
        }
        return result;
    }

    public User findUserById(int userId) {
        User user = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDaoImpl.class);
                user = userDao.findById(userId);
                daoFactory.startTransaction();
                if (user != null) {
                    Wallet wallet       = userDao.getUserWallet(user);
                    UserRole role       = userDao.getUserRole(user);
                    Region region       = userDao.getUserRegion(user);
                    user.setWallet(wallet);
                    user.setRole(role);
                    user.setRegion(region);
                }
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot find user by id ", e);
            }
        }
        return user;
    }

    public User findUserByNumberAndPassword(String number, String password) {
        User user = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDaoImpl.class);
                user = userDao.getUserByNumberAndPassword(number, password);
                daoFactory.startTransaction();
                if (user != null) {
                    Wallet wallet       = userDao.getUserWallet(user);
                    UserRole role       = userDao.getUserRole(user);
                    Region region       = userDao.getUserRegion(user);
                    user.setWallet(wallet);
                    user.setRole(role);
                    user.setRegion(region);
                }
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot find user ", e);
            }
        }
        return user;
    }

    public User findUserByNumber(String number) {
        User user = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDaoImpl.class);
                user = userDao.getUserByNumber(number);
                daoFactory.startTransaction();
                Wallet wallet = userDao.getUserWallet(user);
                UserRole role = userDao.getUserRole(user);
                Region region = userDao.getUserRegion(user);
                user.setWallet(wallet);
                user.setRole(role);
                user.setRegion(region);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot find user by number ", e);
            }
        }
        return user;
    }

    public void updateUserPassword(User user) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDaoImpl.class);
                daoFactory.startTransaction();
                userDao.updatePassword(user);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot update user password ", e);
            }
        }
    }

    public void toTopUpBalance(User user, double balance) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao         = daoFactory.getDao(UserDaoImpl.class);
                WalletDao walletDao     = daoFactory.getDao(WalletDaoImpl.class);
                daoFactory.startTransaction();
                Wallet wallet = userDao.getUserWallet(user);
                wallet.setId(user.getWallet().getId());
                wallet.setBalance(wallet.getBalance() + balance);
                walletDao.update(wallet);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot top up user's balance ", e);
            }
        }
    }

    public void subtractFromBalance(User user, double balance) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao     = daoFactory.getDao(UserDaoImpl.class);
                WalletDao walletDao = daoFactory.getDao(WalletDaoImpl.class);
                daoFactory.startTransaction();
                Wallet wallet = userDao.getUserWallet(user);
                wallet.setId(user.getWallet().getId());
                wallet.setBalance(wallet.getBalance() - balance);
                walletDao.update(wallet);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot to do transaction with balance ", e);
            }
        }
    }

    public void setNewRegionForUser(User user) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDaoImpl.class);
                daoFactory.startTransaction();
                userDao.updateUserRegion(user);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot set a new region for user ", e);
            }
        }
    }

    public PaymentHistory createNewRecordInPaymentHistory(PaymentHistory paymentHistory) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                PaymentHistoryDao paymentHistoryDao = daoFactory.getDao(PaymentHistoryDaoImpl.class);
                daoFactory.startTransaction();
                paymentHistory = paymentHistoryDao.create(paymentHistory);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot create a new record in payment history ", e);
            }
        }
        return paymentHistory;
    }

    public List<PaymentHistory> findAllPaymentHistoryRecordsByUser(User user) {
        List<PaymentHistory> listOfRecords = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                PaymentHistoryDao paymentHistoryDao = daoFactory.getDao(PaymentHistoryDaoImpl.class);
                listOfRecords = paymentHistoryDao.getAllPaymentHistoryRecordsByUser(user);
            } catch (DaoException e) {
                log.error("Cannot find all user payment history records ", e);
            }
        }
        return listOfRecords;
    }
}