package ch.opentrainingcenter.server.db.h2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import ch.opentrainingcenter.server.integration.dao.AthleteDao;
import ch.opentrainingcenter.server.integration.dao.HealthDao;
import ch.opentrainingcenter.transfer.CommonTransferFactory;
import ch.opentrainingcenter.transfer.IAthlete;
import ch.opentrainingcenter.transfer.IHealth;

@SuppressWarnings("nls")
public class HealthDaoTest extends DatabaseTestBase {
    private HealthDao healthDao;
    private AthleteDao athleteDao;
    private IAthlete athlete;
    private Date now;

    @Before
    public void setUp() {
        healthDao = new HealthDao(connectionConfig);
        athleteDao = new AthleteDao(connectionConfig);
        athlete = CommonTransferFactory.createAthlete("healthdao", DateTime.now().toDate(), 220);
        athleteDao.save(athlete);
        now = DateTime.now().toDate();
    }

    @Test
    public void testSave() {

        final IHealth health = CommonTransferFactory.createHealth(athlete, 100.0, 200, now);
        healthDao.saveOrUpdate(health);

        final List<IHealth> result = healthDao.getHealth(athlete);
        assertNotNull(result);
        assertTrue(1 == result.size());
    }

    @Test
    public void testGetHealth() {
        final IHealth health = CommonTransferFactory.createHealth(athlete, 201.0, 201, now);
        healthDao.saveOrUpdate(health);

        health.setWeight(101.0);
        health.setCardio(20);
        healthDao.saveOrUpdate(health);

        final IHealth result = healthDao.getHealth(athlete, now);
        assertNotNull(result);
        assertEquals("Resultat muss überschreiben sein. ", 101.0, result.getWeight(), 0.00001);
        assertEquals("Resultat muss überschreiben sein. ", 20, result.getCardio().intValue());
    }

    @Test
    public void testRemove() {

        final IHealth health = CommonTransferFactory.createHealth(athlete, 123.0, 200, now);
        final int id = healthDao.saveOrUpdate(health);
        healthDao.remove(id);

        final IHealth result = healthDao.getHealth(athlete, now);
        assertNull(result);
    }
}
