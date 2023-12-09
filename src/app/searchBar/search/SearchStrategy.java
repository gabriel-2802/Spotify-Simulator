package app.searchBar.search;

import app.searchBar.Filters;
import app.searchBar.SearchBar;

import java.util.List;

/**
 * SearchStrategy interface
 */
public interface SearchStrategy {
    /**
     * searches for the given filters
     * @param filters the filters
     * @return list of results as strings
     */
    List<String> search(final Filters filters);
}
