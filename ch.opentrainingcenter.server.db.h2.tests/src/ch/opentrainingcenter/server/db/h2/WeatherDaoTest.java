package ch.opentrainingcenter.server.db.h2;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.opentrainingcenter.server.integration.dao.WeatherDao;
import ch.opentrainingcenter.transfer.IWeather;

@SuppressWarnings("nls")
public class WeatherDaoTest extends DatabaseTestBase {

    private WeatherDao weatherDao;

    @Before
    public void setUp() {
        weatherDao = new WeatherDao(connectionConfig);
    }

    @Test
    public void testGetWeather() {
        final List<IWeather> result = weatherDao.getAllWeather();
        assertTrue("Einige Wetter sind vorhanden", 3 <= result.size());
    }

}
