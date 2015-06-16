package ch.opentrainingcenter.server.integration.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ch.opentrainingcenter.transfer.IAthlete;
import ch.opentrainingcenter.transfer.IHealth;

public class HealthDao {
    private static final Logger LOG = Logger.getLogger(HealthDao.class);

    private final IConnectionConfig dao;

    public HealthDao(final IConnectionConfig dao) {
        this.dao = dao;
    }

    public int saveOrUpdate(final IHealth health) {
        LOG.info("save daily health: " + health); //$NON-NLS-1$
        final Session session = dao.getSession();
        dao.begin();
        session.saveOrUpdate(health);
        dao.commit();
        session.flush();
        return health.getId();
    }

    /**
     * gibt gesundheitszustand zurück. wenn nichts gefunden wird. wird null
     * zurückgegeben.
     */
    public IHealth getHealth(final IAthlete athlete, final Date date) {
        LOG.info("load health from: " + athlete); //$NON-NLS-1$
        final Session session = dao.getSession();
        dao.begin();
        final Criteria criteria = session.createCriteria(IHealth.class);
        criteria.add(Restrictions.eq("athlete", athlete)); //$NON-NLS-1$
        criteria.add(Restrictions.eq("dateofmeasure", date)); //$NON-NLS-1$
        IHealth health = null;
        @SuppressWarnings("unchecked")
        final List<IHealth> healthRecords = criteria.list();
        if (notEmpty(healthRecords)) {
            health = healthRecords.get(0);
        }
        dao.commit();
        session.flush();
        return health;
    }

    private static boolean notEmpty(final List<?> records) {
        return records != null && !records.isEmpty();
    }

    public List<IHealth> getHealth(final IAthlete athlete) {
        final Session session = dao.getSession();
        dao.begin();
        final Criteria criteria = session.createCriteria(IHealth.class);
        criteria.add(Restrictions.eq("athlete", athlete)); //$NON-NLS-1$

        @SuppressWarnings("unchecked")
        final List<IHealth> healthRecords = criteria.list();
        dao.commit();
        session.flush();
        return healthRecords;
    }

    public void remove(final int id) {
        final Session session = dao.getSession();
        dao.begin();
        final Query query = session.createQuery("delete Health where id=:id"); //$NON-NLS-1$
        query.setParameter("id", id); //$NON-NLS-1$
        query.executeUpdate();
        dao.commit();
        session.flush();
    }
}
