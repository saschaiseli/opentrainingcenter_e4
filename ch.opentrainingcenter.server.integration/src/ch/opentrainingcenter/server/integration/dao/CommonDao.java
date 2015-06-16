package ch.opentrainingcenter.server.integration.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import ch.opentrainingcenter.server.integration.cache.AthleteCache;
import ch.opentrainingcenter.server.integration.cache.HealthCache;
import ch.opentrainingcenter.server.integration.cache.RouteCache;
import ch.opentrainingcenter.server.integration.cache.SchuhCache;
import ch.opentrainingcenter.server.integration.cache.TrainingCache;
import ch.opentrainingcenter.server.service.db.IDatabaseAccess;
import ch.opentrainingcenter.transfer.IAthlete;
import ch.opentrainingcenter.transfer.IHealth;
import ch.opentrainingcenter.transfer.IPlanungWoche;
import ch.opentrainingcenter.transfer.IRoute;
import ch.opentrainingcenter.transfer.IShoe;
import ch.opentrainingcenter.transfer.ITraining;
import ch.opentrainingcenter.transfer.IWeather;

/**
 * Implementation von {@link IDatabaseAccess}. Kapselt alle Datenbankzugriffe.
 * Hier könnte auch ein Cache zwischengeschaltet werden.
 *
 */
public class CommonDao implements IDatabaseAccess {
    private static final Logger LOGGER = Logger.getLogger(CommonDao.class);
    private final AthleteDao athleteDao;
    private final HealthDao healthDao;
    private final PlanungDao planungsDao;
    private final RouteDao routeDao;
    private final ShoeDao shoeDao;
    private final WeatherDao wetterDao;
    private final TrainingDao trainingDao;
    private final TrainingCache cache;

    /**
     * @param dao
     *            {@link IConnectionConfig}
     */
    public CommonDao(final IConnectionConfig dao) {
        athleteDao = new AthleteDao(dao);
        healthDao = new HealthDao(dao);
        planungsDao = new PlanungDao(dao);
        routeDao = new RouteDao(dao);
        trainingDao = new TrainingDao(dao);
        wetterDao = new WeatherDao(dao);
        shoeDao = new ShoeDao(dao);
        cache = TrainingCache.getInstance();
    }

    /**
     * @param athleteDao
     *            {@link AthleteDao}
     * @param healthDao
     *            {@link HealthDao}
     * @param planungsDao
     *            {@link PlanungDao}
     * @param routeDao
     *            {@link RouteDao}
     * @param wetterDao
     *            {@link WeatherDao}
     * @param trainingDao
     *            {@link TrainingDao}
     * @param shoeDao
     *            {@link ShoeDao}
     * @param cache
     *            {@link TrainingCache}
     */
    public CommonDao(final AthleteDao athleteDao, final HealthDao healthDao, final PlanungDao planungsDao, final RouteDao routeDao, final WeatherDao wetterDao,
            final TrainingDao trainingDao, final ShoeDao shoeDao, final TrainingCache cache) {
        this.athleteDao = athleteDao;
        this.healthDao = healthDao;
        this.planungsDao = planungsDao;
        this.routeDao = routeDao;
        this.wetterDao = wetterDao;
        this.trainingDao = trainingDao;
        this.shoeDao = shoeDao;
        this.cache = cache;
    }

    // -------------------------------TRAINING--------------------------------------------------
    @Override
    public final List<ITraining> getAllTrainings(final IAthlete athlete) {
        if (cache.isEmpty()) {
            cache.addAll(trainingDao.getAllTrainings(athlete));
            LOGGER.info(String.format("load %s records from database", cache.size())); //$NON-NLS-1$
        }
        return cache.getAll(athlete);
    }

    @Override
    public List<ITraining> getTrainingsByAthleteAndDate(final IAthlete athlete, final DateTime von, final DateTime bis) {
        LOGGER.info("Load records direct from database [getTrainingsByAthleteAndDate]"); //$NON-NLS-1$
        return trainingDao.getTrainingsByAthleteAndDate(athlete, von, bis);
    }

    @Override
    public final List<ITraining> getAllTrainingByRoute(final IAthlete athlete, final IRoute route) {
        LOGGER.info("Load records direct from database [getAllTrainingByRoute]"); //$NON-NLS-1$
        return trainingDao.getAllTrainingsByRoute(athlete, route);
    }

    @Override
    public final int saveOrUpdate(final ITraining training) {
        final int id = trainingDao.saveOrUpdate(training);
        cache.addAll(Arrays.asList(training));
        return id;
    }

    @Override
    public final void saveOrUpdateAll(final Collection<ITraining> trainings) {
        final List<ITraining> cachedTrainings = new ArrayList<>();
        for (final ITraining training : trainings) {
            trainingDao.saveOrUpdate(training);
            cachedTrainings.add(training);
        }
        if (!cachedTrainings.isEmpty()) {
            cache.addAll(cachedTrainings);
        }
    }

    @Override
    public final void updateTrainingType(final ITraining training, final int typeIndex) {
        trainingDao.updateTrainingType(training, typeIndex);
        final List<ITraining> models = new ArrayList<>();
        models.add(trainingDao.getTrainingByDate(training.getDatum()));
        cache.addAll(models);
    }

    @Override
    public final ITraining getTrainingById(final long datum) {
        ITraining training = cache.get(datum);
        if (training == null) {
            training = trainingDao.getTrainingByDate(datum);
            if (training != null) {
                final List<ITraining> models = new ArrayList<>();
                models.add(training);
                cache.addAll(models);
            }
        }
        return training;

    }

    @Override
    public final ITraining getNewestTraining(final IAthlete athlete) {
        return trainingDao.getNewestTraining(athlete);
    }

    @Override
    public int getTotalLaengeInMeter(final IShoe shoe) {
        final List<ITraining> allTrainings = getAllTrainings(shoe.getAthlete());
        int result = 0;
        for (final ITraining training : allTrainings) {
            if (shoe.equals(training.getShoe())) {
                result += training.getLaengeInMeter();
            }
        }
        return result;
    }

    @Override
    public final void removeTrainingByDate(final long datum) {
        trainingDao.removeTrainingByDate(datum);
        cache.remove(datum);
    }

    // -------------------------------ATHLETE--------------------------------------------------
    @Override
    public final List<IAthlete> getAllAthletes() {
        return athleteDao.getAllAthletes();
    }

    @Override
    public final IAthlete getAthlete(final int id) {
        return athleteDao.getAthlete(id);
    }

    @Override
    public final int save(final IAthlete athlete) {
        final int id = athleteDao.save(athlete);
        AthleteCache.getInstance().addAll(Arrays.asList(athlete));
        return id;
    }

    @Override
    public final IAthlete getAthlete(final String name) {
        return athleteDao.getAthlete(name);
    }

    // -------------------------------healthDao--------------------------------------------------
    @Override
    public final List<IHealth> getHealth(final IAthlete athlete) {
        return healthDao.getHealth(athlete);
    }

    @Override
    public final IHealth getHealth(final IAthlete athlete, final Date date) {
        return healthDao.getHealth(athlete, date);
    }

    @Override
    public final void removeHealth(final int id) {
        healthDao.remove(id);
        HealthCache.getInstance().remove(id);
    }

    @Override
    public final int saveOrUpdate(final IHealth health) {
        final int id = healthDao.saveOrUpdate(health);
        HealthCache.getInstance().addAll(Arrays.asList(health));
        return id;
    }

    // -------------------------------planungsDao--------------------------------------------------
    @Override
    public final List<IPlanungWoche> getPlanungsWoche(final IAthlete athlete) {
        return planungsDao.getPlanungsWoche(athlete);
    }

    @Override
    public final List<IPlanungWoche> getPlanungsWoche(final IAthlete athlete, final int jahr, final int kw) {
        return planungsDao.getPlanungsWoche(athlete, jahr, kw);
    }

    @Override
    public final void saveOrUpdate(final List<IPlanungWoche> planung) {
        planungsDao.saveOrUpdate(planung);
    }

    // -------------------------------routeDao--------------------------------------------------
    @Override
    public final List<IRoute> getRoute(final IAthlete athlete) {
        return routeDao.getRoute(athlete);
    }

    @Override
    public IRoute getRoute(final int idRoute) {
        return routeDao.getById(idRoute);
    }

    @Override
    public final IRoute getRoute(final String name, final IAthlete athlete) {
        return routeDao.getRoute(name, athlete);
    }

    @Override
    public final boolean existsRoute(final String name, final IAthlete athlete) {
        return routeDao.exists(name, athlete);
    }

    @Override
    public final void saveOrUpdate(final IRoute route) {
        routeDao.saveOrUpdate(route);
        RouteCache.getInstance().addAll(Arrays.asList(route));
    }

    @Override
    public void deleteRoute(final IRoute route) {
        routeDao.delete(route.getId());
        RouteCache.getInstance().remove(route.getName());
    }

    // -------------------------------wetterDao--------------------------------------------------
    @Override
    public final List<IWeather> getWeather() {
        return wetterDao.getAllWeather();
    }

    @Override
    public List<IShoe> getShoes(final IAthlete athlete) {
        return shoeDao.getShoes(athlete);
    }

    @Override
    public boolean existsSchuh(final IAthlete athlete, final String schuhName) {
        return shoeDao.exists(athlete, schuhName);
    }

    @Override
    public final void saveOrUpdate(final IShoe route) {
        shoeDao.saveOrUpdate(route);
        SchuhCache.getInstance().addAll(Arrays.asList(route));
    }

    @Override
    public void deleteShoe(final int id) {
        shoeDao.delete(id);
        SchuhCache.getInstance().remove(String.valueOf(id));
    }

}
