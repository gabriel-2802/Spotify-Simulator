package app.searchBar.searchStrategies;

import app.Admin;
import app.searchBar.FilterUtils;
import app.searchBar.Filters;
import app.searchBar.SearchBarV2;
import app.user.User;

import java.util.List;
/**
 * This class is used to search for hosts
 */

public class HostSearchStrategy implements SearchStrategy<User> {
    /**
     * This method is used to search for the given filters
     * @param filters the filters
     * @return the results
     */
    @Override
    public List<User> search(final Filters filters, final List<String> names,
                             final String user) {
        Admin admin = Admin.getInstance();
        List<User> entries = FilterUtils.filterHosts(admin.getHosts(), filters.getName());

        if (entries.size() > SearchBarV2.MAX_RESULTS) {
            entries = entries.subList(0, SearchBarV2.MAX_RESULTS);
        }

        for (User entry : entries) {
            names.add(entry.getUsername());
        }

        return entries;
    }
}
