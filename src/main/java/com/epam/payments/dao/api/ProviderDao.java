package com.epam.payments.dao.api;

import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.Provider;
import com.epam.payments.models.Region;

import java.util.List;

/**
 * {@code ProviderDao} interface extends {@link Dao} is designed to
 * implements this all methods for CRUD operations and operations
 * with providers data.
 *
 * @author Aleksey Tsoy
 */
public interface ProviderDao extends Dao<Provider> {

    /**
     * Method is designed to update logotype
     * independently of other fields.
     *
     * @param provider
     * @throws DaoException
     */
    void updateLogo(Provider provider) throws DaoException;

    /**
     * Method is designed to obtain all providers.
     *
     * @return list of providers
     * @throws DaoException
     */
    List<Provider> getAllProviders() throws DaoException;

    /**
     * Method is designed to obtain all providers
     * by region id. This is a relation between
     * providers table and providers_regions table.
     *
     * @param regionId which is need to find
     * @return list of providers
     * @throws DaoException
     */
    List<Provider> getAllProvidersByRegion(int regionId) throws DaoException;

    /**
     * Method is designed to set data into
     * providers_regions table.
     *
     * @param region which is need to set
     * @param provider which is need to set
     * @throws DaoException
     */
    void setRegionAndProvider(Region region, Provider provider) throws DaoException;
}