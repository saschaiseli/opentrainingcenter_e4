package ch.opentrainingcenter.server.integration.cache;

import java.util.Comparator;
import java.util.List;

import ch.opentrainingcenter.transfer.IRoute;

public class RouteCache extends AbstractCache<String, IRoute> {

    private static final RouteCache INSTANCE = new RouteCache();

    private RouteCache() {
        // do not create instance. this cache is a singleton
    }

    public static RouteCache getInstance() {
        return INSTANCE;
    }

    @Override
    public String getKey(final IRoute route) {
        return route.getName();
    }

    @Override
    public List<IRoute> getAll() {
        return super.getSortedElements(new RouteComparator());
    }

    @Override
    public String toString() {
        return "Cache: Anzahl Elemente: " + size(); //$NON-NLS-1$
    }

    @Override
    public String getName() {
        return "Route"; //$NON-NLS-1$
    }

    class RouteComparator implements Comparator<IRoute> {

        @Override
        public int compare(final IRoute o1, final IRoute o2) {
            return o2.getName().compareTo(o1.getName());
        }
    }
}
