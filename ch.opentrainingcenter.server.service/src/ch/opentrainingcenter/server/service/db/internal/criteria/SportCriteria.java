package ch.opentrainingcenter.server.service.db.internal.criteria;

import java.util.Set;

import ch.opentrainingcenter.server.service.assertions.Assertions;
import ch.opentrainingcenter.server.service.db.ISearchCriteria;
import ch.opentrainingcenter.transfer.ITraining;
import ch.opentrainingcenter.transfer.Sport;

public class SportCriteria implements ISearchCriteria {

    private final Set<Sport> sports;

    public SportCriteria(final Set<Sport> sports) {
        Assertions.notNull(sports);
        this.sports = sports;
    }

    @Override
    public boolean matches(final ITraining training) {
        return sports.contains(training.getSport());
    }

}
