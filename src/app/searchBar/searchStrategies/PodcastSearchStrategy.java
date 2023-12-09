package app.searchBar.searchStrategies;

import app.Admin;
import app.audio.LibraryEntry;
import app.searchBar.FilterUtils;
import app.searchBar.Filters;
import app.searchBar.SearchBarV2;

import java.util.ArrayList;
import java.util.List;

/**
 * search strategy for podcasts
 */
public class PodcastSearchStrategy implements SearchStrategy<LibraryEntry> {
    /**
     * This method is used to search for the given filters
     * @param filters the filters
     * @return the results
     */
    @Override
    public List<LibraryEntry> search(final Filters filters, final List<String> names,
                                     final String user) {
        List<LibraryEntry> entries = new ArrayList<>(Admin.getPodcasts());

        if (filters.getName() != null) {
            entries = FilterUtils.filterByName(entries, filters.getName());
        }

        if (filters.getOwner() != null) {
            entries = FilterUtils.filterByOwner(entries, filters.getOwner());
        }

        if (entries.size() > SearchBarV2.MAX_RESULTS) {
            entries = entries.subList(0, SearchBarV2.MAX_RESULTS);
        }

        for (LibraryEntry entry : entries) {
            names.add(entry.getName());
        }
        return entries;
    }
}
