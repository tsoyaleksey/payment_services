package com.epam.payments.dao.impl;

import static com.epam.payments.dao.api.DaoConstants.*;
import com.epam.payments.dao.api.BaseDao;
import com.epam.payments.dao.api.RegionDao;
import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.Provider;
import com.epam.payments.models.Region;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code RegionDaoImpl} implements {@link RegionDao} is a Dao layer.
 * This class is designed to handle SQL queries through Java-class.
 *
 * @author Aleksey Tsoy
 */
public class RegionDaoImpl extends BaseDao implements RegionDao {
    private static final Logger log = Logger.getLogger(RegionDaoImpl.class);

    private static final String CREATE = "INSERT INTO regions VALUES (id, ?)";

    private static final String FIND_BY_ID = "SELECT * FROM regions WHERE id = ?";

    private static final String UPDATE = "UPDATE regions SET region_name = ? WHERE id = ?";

    private static final String GET_ALL_REGIONS = "SELECT * FROM regions";

    private static final String GET_ALL_REGIONS_BY_PROVIDER_ID =
            "SELECT regions.id,region_name FROM regions JOIN providers_regions ON regions.id=providers_regions.region_id WHERE provider_id=?";

    private static final String FIND_REGION_AND_PROVIDER =
            "SELECT region_id, region_name FROM providers_regions JOIN regions ON providers_regions.region_id = regions.id WHERE provider_id = ? AND region_id = ?";

    @Override
    public Region create(Region region) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_ONE, region.getName());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    region.setId(resultSet.getInt(INDEX_ONE));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot create a new region ", e);
        }
        return region;
    }

    @Override
    public Region findById(int id) throws DaoException {
        Region region = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_ONE, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    region = getDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find region by id ", e);
        }
        return region;
    }

    @Override
    public void update(Region region) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
            statement.setString(INDEX_ONE, region.getName());
            statement.setInt(INDEX_TWO, region.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update region ", e);
        }
    }

    @Override
    public void delete(Region region) throws DaoException {
    }

    @Override
    public List<Region> getAllRegions() throws DaoException {
        List<Region> regionList = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(GET_ALL_REGIONS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    regionList.add(getDataFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot get all regions ", e);
        }
        return regionList;
    }

    @Override
    public List<Region> getAllRegionsByProviderId(int id) {
        List<Region> regions = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(GET_ALL_REGIONS_BY_PROVIDER_ID)) {
            statement.setInt(INDEX_ONE, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    regions.add(getDataFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot get all regions by provider id ", e);
        }
        return regions;
    }

    @Override
    public Region getByProviderAndRegion(Provider provider, Region region) throws DaoException {
        Region foundRegion = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_REGION_AND_PROVIDER)) {
            statement.setInt(INDEX_ONE, provider.getId());
            statement.setInt(INDEX_TWO, region.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foundRegion = getDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find provider and region ", e);
        }
        return foundRegion;
    }

    @Override
    public Region getDataFromResultSet(ResultSet resultSet) {
        Region region = new Region();
        try {
            region.setId(resultSet.getInt(INDEX_ONE));
            region.setName(resultSet.getString(INDEX_TWO));
        } catch (SQLException e) {
            log.error("Cannot get region from ResultSet ", e);
        }
        return region;
    }
}