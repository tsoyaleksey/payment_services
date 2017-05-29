package com.epam.payments.services;

import com.epam.payments.dao.api.CategoryDao;
import com.epam.payments.dao.api.DaoFactory;
import com.epam.payments.dao.api.RegionDao;
import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.dao.impl.CategoryDaoImpl;
import com.epam.payments.dao.impl.RegionDaoImpl;
import com.epam.payments.models.Category;
import com.epam.payments.models.Region;
import com.epam.payments.services.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class ManagementService {
    private static final Logger log = Logger.getLogger(ManagementService.class);

    public List<Category> getListOfCategories() {
        List<Category> categories = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                CategoryDao categoryDao = daoFactory.getDao(CategoryDaoImpl.class);
                categories = categoryDao.getAllCategories();
            } catch (DaoException e) {
                log.error("Cannot get all categories ", e);
            }
        }
        return categories;
    }

    public void updateCategory(Category category) {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                CategoryDao categoryDao = daoFactory.getDao(CategoryDaoImpl.class);
                daoFactory.startTransaction();
                categoryDao.update(category);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot update category ", e);
            }
        }
    }

    public Category createNewCategory(Category category) throws ServiceException {
        Category newCategory = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                CategoryDao categoryDao = daoFactory.getDao(CategoryDaoImpl.class);
                daoFactory.startTransaction();
                newCategory = categoryDao.create(category);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot create new category ", e);
            }
        }
        return newCategory;
    }

    public Category findCategoryById(int id) {
        Category category = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                CategoryDao categoryDao = daoFactory.getDao(CategoryDaoImpl.class);
                category = categoryDao.findById(id);
            } catch (DaoException e) {
                log.error("Cannot find category by id ", e);
            }
        }
        return category;
    }

    public List<Region> getListOfRegions() {
        List<Region> regions = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                RegionDao regionDao = daoFactory.getDao(RegionDaoImpl.class);
                regions = regionDao.getAllRegions();
            } catch (DaoException e) {
                log.error("Cannot get all regions ", e);
            }
        }
        return regions;
    }

    public Region findRegionById(int id) {
        Region region = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                RegionDao regionDao = daoFactory.getDao(RegionDaoImpl.class);
                region = regionDao.findById(id);
            } catch (DaoException e) {
                log.error("Cannot find region by id ", e);
            }
        }
        return region;
    }

    public Region createNewRegion(Region region) throws ServiceException {
        Region newRegion = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                RegionDao regionDao = daoFactory.getDao(RegionDaoImpl.class);
                daoFactory.startTransaction();
                newRegion = regionDao.create(region);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot create new region ", e);
            }
        }
        return newRegion;
    }

    public void updateRegion(Region region) {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                RegionDao regionDao = daoFactory.getDao(RegionDaoImpl.class);
                daoFactory.startTransaction();
                regionDao.update(region);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot update region ", e);
            }
        }
    }
}
