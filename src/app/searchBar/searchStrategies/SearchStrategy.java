package app.searchBar.searchStrategies;

import app.searchBar.Filters;
import java.util.List;

/**
 * This interface is used to search for the given filters
 * @param <T> the type of the search: LibraryEntry or User
 */
public interface SearchStrategy<T> {
    /**
     * This method is used to search for the given filters
     * @param filters the filters
     * @return the results
     */
    List<T> search(Filters filters, List<String> names, String user);
}
