package com.epam.payments.dao.api;

import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.Provider;
import com.epam.payments.models.Region;

import java.util.List;

/**
 * {@code RegionDao} interface extends {@link Dao} is designed
 * for CRUD-operations and operations with user's data.
 *
 * @author Aleksey Tsoy
 */
public interface RegionDao extends Dao<Region> {

    /**
     * Method is designed to obtain all regions.
     *
     * @return list of regions
     * @throws DaoException
     */
    List<Region> getAllRegions() throws DaoException;

    /**
     * Method is designed to get regions from
     * providers_regions table by provider id.
     *
     * @param id of provider
     * @return list of regions
     */
    List<Region> getAllRegionsByProviderId(int id);

    /**
     * Method is designed to get region by current provider
     * from providers_regions table
     *
     * @param provider
     * @param region
     * @return
     * @throws DaoException
     */
    Region getByProviderAndRegion(Provider provider, Region region) throws DaoException;
}
