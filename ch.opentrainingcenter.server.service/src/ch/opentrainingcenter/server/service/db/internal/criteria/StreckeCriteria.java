package ch.opentrainingcenter.server.service.db.internal.criteria;

import ch.opentrainingcenter.server.service.db.ISearchCriteria;
import ch.opentrainingcenter.transfer.IRoute;
import ch.opentrainingcenter.transfer.ITraining;

public class StreckeCriteria implements ISearchCriteria {

    private final int referenzTrainingId;

    public StreckeCriteria(final int referenzTrainingId) {
        this.referenzTrainingId = referenzTrainingId;
    }

    @Override
    public boolean matches(final ITraining training) {
        if (referenzTrainingId <= 0) {
            return true;
        }
        final IRoute route = training.getRoute();
        return route != null && referenzTrainingId == route.getId();
    }

}
