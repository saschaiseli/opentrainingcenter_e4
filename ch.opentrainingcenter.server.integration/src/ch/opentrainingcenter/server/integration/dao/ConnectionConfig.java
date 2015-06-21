package ch.opentrainingcenter.server.integration.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ch.opentrainingcenter.common.assertions.Assertions;
import ch.opentrainingcenter.server.integration.USAGE;
import ch.opentrainingcenter.server.service.db.DatabaseConnectionConfiguration;

@SuppressWarnings("nls")
public class ConnectionConfig implements IConnectionConfig {

    private static final Logger LOG = Logger.getLogger(ConnectionConfig.class);

    private static final String HIBERNATE_POOL_SIZE = "hibernate.pool_size";
    private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private Session session;
    private final SessionFactory sessionFactory;
    private final USAGE usage;
    private final DatabaseConnectionConfiguration config;

    public ConnectionConfig(final USAGE usage, final DatabaseConnectionConfiguration config) {
        this(usage, config, new org.hibernate.cfg.Configuration());
    }

    public ConnectionConfig(final USAGE usage, final DatabaseConnectionConfiguration config, final org.hibernate.cfg.Configuration configuration) {
        Assertions.notNull(config, "Datenbankkonfiguration darf nicht null sein");
        this.usage = usage;
        this.config = config;
        configuration.setProperties(config.getProperties());
        configuration.setProperty("current_session_context_class", "thread");
        configuration.setProperty("cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        configuration.setProperty(HIBERNATE_SHOW_SQL, String.valueOf(usage.isShowSql()));
        configuration.setProperty(HIBERNATE_FORMAT_SQL, String.valueOf(usage.isFormatSql()));
        configuration.setProperty("hibernate.connection.pool_size", String.valueOf(10));
        configuration.addResource("Planungwoche.hbm.xml", this.getClass().getClassLoader());
        configuration.addResource("Athlete.hbm.xml", this.getClass().getClassLoader());
        configuration.addResource("Health.hbm.xml", this.getClass().getClassLoader());
        configuration.addResource("Weather.hbm.xml", this.getClass().getClassLoader());
        configuration.addResource("Training.hbm.xml", this.getClass().getClassLoader());
        configuration.addResource("Shoe.hbm.xml", this.getClass().getClassLoader());
        configuration.addResource("Tracktrainingproperty.hbm.xml", this.getClass().getClassLoader());
        configuration.addResource("Streckenpunkte.hbm.xml", this.getClass().getClassLoader());
        configuration.addResource("Route.hbm.xml", this.getClass().getClassLoader());
        configuration.addResource("LapInfo.hbm.xml", this.getClass().getClassLoader());
        LOG.info(String.format("Hibernate Config: show_sql=%s", configuration.getProperty(HIBERNATE_SHOW_SQL)));
        LOG.info(String.format("Hibernate Config: format_sql=%s", configuration.getProperty(HIBERNATE_FORMAT_SQL)));
        LOG.info(String.format("Hibernate Config: pool_size=%s", configuration.getProperty(HIBERNATE_POOL_SIZE)));
        sessionFactory = configuration.buildSessionFactory();
        LOG.info(String.format("SessionFactory: %s", sessionFactory));
    }

    @Override
    public Transaction begin() {
        final Session s = getSession();
        LOG.info("Begin transaction Sessionist: " + s);
        return s.beginTransaction();

    }

    @Override
    public void close() {
        getSession().close();
    }

    @Override
    public void commit() {
        getSession().getTransaction().commit();
    }

    @Override
    public Session getSession() {
        if (session == null || !session.isOpen()) {
            session = sessionFactory.openSession();
            LOG.info("Session geöffnet: " + session);
        }
        return session;
    }

    @Override
    public USAGE getUsage() {
        return usage;
    }

    @Override
    public void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (final HibernateException e) {
            LOG.error(e);
        }
        try {
            getSession().close();
        } catch (final HibernateException e) {
            LOG.error(e);
        }
    }

    @Override
    public DatabaseConnectionConfiguration getConfig() {
        return config;
    }
}
