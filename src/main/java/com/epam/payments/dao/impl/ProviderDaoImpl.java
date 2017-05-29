package com.epam.payments.dao.impl;

import static com.epam.payments.dao.api.DaoConstants.*;
import com.epam.payments.dao.api.BaseDao;
import com.epam.payments.dao.api.ProviderDao;
import com.epam.payments.dao.exception.DaoException;
import com.epam.payments.models.Category;
import com.epam.payments.models.Provider;
import com.epam.payments.models.Region;
import com.epam.payments.services.ManagementService;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code ProviderDaoImpl} implements {@link ProviderDao} is a Dao layer.
 * This class is designed to handle SQL queries through Java-class.
 *
 * @author Aleksey Tsoy
 */
public class ProviderDaoImpl extends BaseDao implements ProviderDao {
    private static final Logger log = Logger.getLogger(ProviderDaoImpl.class);

    private static final String CREATE = "INSERT INTO providers(provider_name,logotype,category_id) VALUES (?,?,?)";

    private static final String FIND_BY_ID = "SELECT * FROM providers WHERE id = ?";

    private static final String UPDATE = "UPDATE providers SET provider_name = ?, category_id = ? WHERE id = ?";

    private static final String UPDATE_LOGO = "UPDATE providers SET logotype=? WHERE providers.id = ?";

    private static final String SET_REGION_TO_PROVIDER = "INSERT INTO providers_regions(provider_id, region_id) VALUES (?,?)";

    private static final String GET_ALL_PROVIDERS =
            "SELECT providers.id,provider_name,logotype,categories.id FROM providers JOIN categories ON providers.category_id = categories.id";

    private static final String GET_ALL_PROVIDERS_BY_REGION_ID =
            "SELECT providers.id,provider_name,logotype,category_id FROM providers JOIN providers_regions ON providers.id=providers_regions.provider_id WHERE region_id=?";

    @Override
    public Provider create(Provider provider) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(CREATE)) {
            statement.setString(INDEX_ONE, provider.getName());
            statement.setBlob(INDEX_TWO, provider.getLogotype());
            statement.setInt(INDEX_THREE, provider.getCategory().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot create new provider ", e);
        }
        return provider;
    }

    @Override
    public Provider findById(int id) throws DaoException {
        Provider provider = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_ONE, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    provider = getDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find provider by id ", e);
        }
        return provider;
    }

    @Override
    public void update(Provider provider) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
            statement.setString(INDEX_ONE, provider.getName());
            statement.setInt(INDEX_TWO, provider.getCategory().getId());
            statement.setInt(INDEX_THREE, provider.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update provider ", e);
        }
    }

    @Override
    public void delete(Provider provider) throws DaoException {
    }

    @Override
    public void updateLogo(Provider provider) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_LOGO)) {
            statement.setBinaryStream(INDEX_ONE, provider.getLogotype());
            statement.setInt(INDEX_TWO, provider.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update logotype ", e);
        }
    }

    @Override
    public List<Provider> getAllProviders() throws DaoException {
        List<Provider> providers = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(GET_ALL_PROVIDERS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    providers.add(getDataFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot get all providers ", e);
        }

        return providers;
    }

    @Override
    public List<Provider> getAllProvidersByRegion(int regionId) throws DaoException {
        List<Provider> providers = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(GET_ALL_PROVIDERS_BY_REGION_ID)) {
            statement.setInt(INDEX_ONE, regionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    providers.add(getDataFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot get all providers by region id ", e);
        }
        return providers;
    }

    @Override
    public void setRegionAndProvider(Region region, Provider provider) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(SET_REGION_TO_PROVIDER)) {
            statement.setInt(INDEX_ONE, provider.getId());
            statement.setInt(INDEX_TWO, region.getId());
            statement.execute();
        } catch (SQLException e) {
            log.error("Cannot set region to provider ", e);
        }
    }

    @Override
    public Provider getDataFromResultSet(ResultSet resultSet) {
        ManagementService service = new ManagementService();
        Provider provider = new Provider();
        try {
            provider.setId(resultSet.getInt(INDEX_ONE));
            provider.setName(resultSet.getString(INDEX_TWO));
            provider.setLogotype(resultSet.getBinaryStream(INDEX_THREE));
            Category category = service.findCategoryById(resultSet.getInt(INDEX_FOUR));
            provider.setCategory(category);
        } catch (SQLException e) {
            log.error("Cannot get provider from resultSet ", e);
        }
        return provider;
    }
}