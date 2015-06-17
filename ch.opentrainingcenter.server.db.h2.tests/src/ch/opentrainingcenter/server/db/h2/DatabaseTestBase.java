package ch.opentrainingcenter.server.db.h2;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import ch.opentrainingcenter.server.integration.USAGE;
import ch.opentrainingcenter.server.integration.dao.CommonDao;
import ch.opentrainingcenter.server.integration.dao.ConnectionConfig;
import ch.opentrainingcenter.server.integration.dao.IConnectionConfig;
import ch.opentrainingcenter.server.service.db.DatabaseConnectionConfiguration;
import ch.opentrainingcenter.server.service.db.DbConnection;
import ch.opentrainingcenter.server.service.db.SqlException;

/**
 * Basis für Datebank Tests. Die Tests werden gegen eine h2 db gemacht.
 *
 * @author sascha
 *
 */
@SuppressWarnings("nls")
public class DatabaseTestBase {

    private static final String USER = "sa";
    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:file:~/.otc_junit/";
    private static final String DIALECT = "org.hibernate.dialect.H2Dialect";

    protected static IConnectionConfig connectionConfig = null;
    protected static DatabaseAccess dataConnection;
    protected static CommonDao dataAccess;

    @BeforeClass
    public static void createDb() throws SqlException {
        connectionConfig = new ConnectionConfig(USAGE.TEST, new DatabaseConnectionConfiguration(new DbConnection(DRIVER, DIALECT, URL + USAGE.TEST.getDbName(),
                USER, "")));
        dataConnection = new DatabaseAccess(connectionConfig);
        dataConnection.createDatabase();

        dataAccess = new CommonDao(connectionConfig);

    }

    @After
    public void tearDown() throws SqlException {
        final Session session = connectionConfig.getSession();
        connectionConfig.begin();
        session.createSQLQuery("delete from ROUTE").executeUpdate();
        session.createSQLQuery("delete from TRACKTRAININGPROPERTY").executeUpdate();
        session.createSQLQuery("delete from TRAINING").executeUpdate();
        session.createSQLQuery("delete from SHOES").executeUpdate();
        session.createSQLQuery("delete from STRECKENPUNKTE").executeUpdate();
        session.createSQLQuery("delete from HEALTH").executeUpdate();
        session.createSQLQuery("delete from PLANUNGWOCHE").executeUpdate();
        session.createSQLQuery("delete from ATHLETE WHERE id>0").executeUpdate();
        connectionConfig.commit();
        session.flush();
    }

    @AfterClass
    public static void afterClass() {
        truncateDb();
    }

    protected static void truncateDb() {
        try {
            final Session session = connectionConfig.getSession();
            connectionConfig.begin();

            final Query query = session.createSQLQuery("drop all objects;");
            query.executeUpdate();

            connectionConfig.commit();
            session.flush();
        } catch (final Exception e) {
            connectionConfig.rollback();
        }
    }
}
