package ch.opentrainingcenter.server.integration.cache;

import ch.opentrainingcenter.transfer.IRoute;
import ch.opentrainingcenter.transfer.ITraining;
import ch.opentrainingcenter.transfer.IWeather;

public interface Cache extends ICache<Long, ITraining> {

    /**
     * Synchronisiert das Wetter und die Notes
     *
     *
     * @param activityId
     */
    void update(final Long activityId, final String note, final IWeather weather, final IRoute route);

    void resetCache();

}