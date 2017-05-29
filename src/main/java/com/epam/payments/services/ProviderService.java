package com.epam.payments.services;

import com.epam.payments.dao.api.DaoFactory;
import com.epam.payments.dao.api.ProviderDao;
import com.epam.payments.dao.api.RegionDao;
import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.dao.impl.ProviderDaoImpl;
import com.epam.payments.dao.impl.RegionDaoImpl;
import com.epam.payments.models.Provider;
import com.epam.payments.models.Region;
import com.epam.payments.services.exception.ServiceException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class ProviderService {
    private static final Logger log = Logger.getLogger(ProviderService.class);

    public List<Provider> getListOfProviders() {
        List<Provider> providers = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                ProviderDao providerDao = daoFactory.getDao(ProviderDaoImpl.class);
                providers = providerDao.getAllProviders();
            } catch (DaoException e) {
                log.error("Cannot get all providers", e);
            }
        }
        return providers;
    }

    public Provider findProviderById(int id) {
        Provider provider = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                ProviderDao providerDao = daoFactory.getDao(ProviderDaoImpl.class);
                provider = providerDao.findById(id);
            } catch (DaoException e) {
                log.error("Cannot find providers by id ", e);
            }
        }
        return provider;
    }

    public List<Provider> findAllProvidersByRegion(int regionId) {
        List<Provider> providers = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                ProviderDao providerDao = daoFactory.getDao(ProviderDaoImpl.class);
                providers = providerDao.getAllProvidersByRegion(regionId);
            } catch (DaoException e) {
                log.error("Cannot find all providers by region ", e);
            }
        }
        return providers;
    }

    public Region findByProviderAndRegion(Provider provider, Region region) {
        Region foundRegion = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                RegionDao regionDao = daoFactory.getDao(RegionDaoImpl.class);
                foundRegion = regionDao.getByProviderAndRegion(provider, region);
            } catch (DaoException e) {
                log.error("Cannot find by provider and region ", e);
            }
        }
        return foundRegion;
    }

    public List<Region> findAllRegionsByProvider(int providerId) {
        List<Region> regions = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                RegionDao regionDao = daoFactory.getDao(RegionDaoImpl.class);
                regions = regionDao.getAllRegionsByProviderId(providerId);
            } catch (DaoException e) {
                log.error("Cannot find all regions by provider ", e);
            }
        }
        return regions;
    }

    public Provider createNewProvider(Provider provider) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                ProviderDao providerDao = daoFactory.getDao(ProviderDaoImpl.class);
                daoFactory.startTransaction();
                provider = providerDao.create(provider);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot create new provider ", e);
            }
        }
        return provider;
    }

    public void updateProvider(Provider provider, List<Region> regions) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                ProviderDao providerDao = daoFactory.getDao(ProviderDaoImpl.class);
                daoFactory.startTransaction();
                providerDao.update(provider);
                if (provider.getLogotype().available() != 0) {
                    providerDao.updateLogo(provider);
                }
                for (Region region : regions) {
                    providerDao.setRegionAndProvider(region, provider);
                }
                daoFactory.commitTransaction();
            } catch (DaoException | IOException e) {
                daoFactory.rollbackTransaction();
                log.error("Cannot update provider ", e);
            }
        }
    }
}