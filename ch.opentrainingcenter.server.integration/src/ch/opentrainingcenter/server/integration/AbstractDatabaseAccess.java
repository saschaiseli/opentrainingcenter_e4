package ch.opentrainingcenter.server.integration;

import ch.opentrainingcenter.server.integration.dao.CommonDao;
import ch.opentrainingcenter.server.integration.dao.ConnectionConfig;
import ch.opentrainingcenter.server.integration.dao.DatabaseCreator;
import ch.opentrainingcenter.server.integration.dao.IConnectionConfig;
import ch.opentrainingcenter.server.service.db.DatabaseConnectionConfiguration;
import ch.opentrainingcenter.server.service.db.DbConnection;
import ch.opentrainingcenter.server.service.db.IDatabaseAccess;
import ch.opentrainingcenter.server.service.db.IDatabaseConnection;

/**
 * Delegiert die DB Access an die entsprechenden DAOs
 */
public abstract class AbstractDatabaseAccess implements IDatabaseConnection {

    private boolean developing;
    private DatabaseConnectionConfiguration config;
    private CommonDao commonDao;
    private DatabaseCreator databaseCreator;
    private ConnectionConfig connectionConfig;

    protected void createDaos(final IConnectionConfig dao) {
        databaseCreator = new DatabaseCreator(dao);
        commonDao = new CommonDao(dao);
    }

    @Override
    public void init() {
        if (isDeveloping()) {
            this.connectionConfig = new ConnectionConfig(USAGE.DEVELOPING, getConfig());
        } else {
            this.connectionConfig = new ConnectionConfig(USAGE.PRODUCTION, getConfig());
        }
        createDaos(connectionConfig);
    }

    @Override
    public final DbConnection getDbConnection() {
        return config.getDbConnection();
    }

    @Override
    public final void setConfig(final DatabaseConnectionConfiguration config) {
        this.config = config;
    }

    public final DatabaseConnectionConfiguration getConfig() {
        return config;
    }

    @Override
    public final void setDeveloping(final boolean developing) {
        this.developing = developing;
    }

    public final boolean isDeveloping() {
        return developing;
    }

    @Override
    public IDatabaseAccess getDataAccess() {
        return getCommonDao();
    }

    public final CommonDao getCommonDao() {
        return commonDao;
    }

    public final DatabaseCreator getDatabaseCreator() {
        return databaseCreator;
    }

}
