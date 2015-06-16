package ch.opentrainingcenter.server.integration.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ch.opentrainingcenter.server.integration.USAGE;
import ch.opentrainingcenter.server.service.db.DatabaseConnectionConfiguration;

public interface IConnectionConfig {

    Transaction begin();

    void close();

    void commit();

    Session getSession();

    USAGE getUsage();

    void rollback();

    DatabaseConnectionConfiguration getConfig();

}